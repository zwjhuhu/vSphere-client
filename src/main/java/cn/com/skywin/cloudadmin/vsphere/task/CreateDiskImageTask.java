package cn.com.skywin.cloudadmin.vsphere.task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FilenameUtils;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.connection.KeepAlive;
import com.vmware.vim25.ManagedObjectReference;

/**
 * 创建vcenter中的云盘镜像<br/>
 * <p>
 * 步骤：<br/>
 * 上传vmdk文件到vcenter的数据存储<br/>
 * </p>
 * <p>
 * <b>如果回滚删除，需要执行{@link DeleteFileTask}</b>
 * </p>
 * 
 * @author zwj
 * @date 2020/11/28
 */
public class CreateDiskImageTask extends BaseTask<String> {

    /**
     * 镜像的数据库据id
     */
    private String mirrorId;

    /**
     * 数据中心标识
     */
    private String datacenter;

    /**
     * 数据存储的标识
     */
    private String datastore;

    /**
     * 需要上传的文件路径，一般使用vmdk结尾的文件
     */
    private String localPath;
    
    /**
     * 镜像上传之后的文件位置
     */
    private String path;
    
    private String datacenterName;
    
    private String datastoreName;

    public CreateDiskImageTask(VsphereClient client, String mirrorId, String datacenter,
        String datastore, String localPath) {
        super(client);
        this.mirrorId = mirrorId;
        this.datacenter = datacenter;
        this.datastore = datastore;
        this.localPath = localPath;
    }

    @Override
    public String doTask() throws Exception {
        putVMDKFile();
        return path;
    }

    private void customValidation() throws Exception {

        if (localPath == null || !new File(localPath).exists()) {
            throw new IllegalArgumentException("Specified localPath [" + localPath + "] not exists");
        }

        if (datacenter != null && datacenter.length() != 0 && datastore != null && datastore.length() != 0) {
            Object[] ret = findTypeNameAndReferenceById(TYPE_DATACENTER, datacenter);
            ManagedObjectReference dcmor = null;
            if(ret!=null) {
                datacenterName = (String)ret[0];
                dcmor = (ManagedObjectReference)ret[1];
            }
            if (dcmor != null) {
                ret = findTypeNameAndReferenceById(TYPE_DATASTORE, datastore);
                ManagedObjectReference dsmor = null;
                if(ret!=null) {
                    datastoreName = (String)ret[0];
                    dsmor = (ManagedObjectReference)ret[1];
                }
                if (dsmor == null) {
                    throw new IllegalArgumentException("Specified Datastore [" + datastore
                        + "] was not found in specified Datacenter [" + datacenter + "]");
                }
            } else {
                throw new IllegalArgumentException("Specified Datacenter [" + datacenter + "] not Found");
            }
        } else {
            throw new IllegalArgumentException("Datacenter or Datastore not specified");
        }

    }

    @SuppressWarnings("unchecked")
    private void putVMFile(final String remoteFilePath, final File localFile) throws Exception {
        final String url = connection.getUrl();
        final String serviceUrl = url.substring(0, url.lastIndexOf("sdk") - 1);
        String httpUrl = serviceUrl + "/folder/" + remoteFilePath + "?dcPath=" + datacenterName + "&dsName=" + datastoreName;
        httpUrl = httpUrl.replaceAll("\\ ", "%20");
//        logger.info("Putting VM File to url {}", httpUrl);

        final URL fileURL;
        HttpURLConnection conn = null;
        try {
            fileURL = new URL(httpUrl);
            conn = (HttpURLConnection)fileURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setAllowUserInteraction(true);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Maintain session
        final List<String> cookies = (List<String>)headers.get("Set-cookie");
        String cookieValue = (cookies != null) ? cookies.get(0) : "";
        final StringTokenizer tokenizer = new StringTokenizer(cookieValue, ";");
        cookieValue = tokenizer.nextToken();
        final String path = "$" + tokenizer.nextToken();
        final String cookie = "$Version=\"1\"; " + cookieValue + "; " + path;

        // set the cookie in the new request header
        final Map<String, List<String>> map = new HashMap<>();
        map.put("Cookie", Collections.singletonList(cookie));
        ((BindingProvider)vimPort).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, map);

        conn.setRequestProperty("Cookie", cookie);
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Length", "1024");
        long fileLen = localFile.length();
//        logger.info("File size is: {}", fileLen);

        // setChunkedStreamingMode to -1 turns off chunked mode
        // setChunkedStreamingMode to 0 asks for system default
        // NOTE:
        // larger values mean faster connections at the
        // expense of more heap consumption.
        conn.setChunkedStreamingMode(0);

        OutputStream out = null;
        InputStream in = null;
        try {
            out = conn.getOutputStream();
            in = new BufferedInputStream(new FileInputStream(localFile));
            int bufLen = 9 * 1024;
            byte[] buf = new byte[bufLen];
            byte[] tmp = null;
            int len = 0;
            // this can take a very long time, so we do a keep-alive here.
            Thread keepAlive = KeepAlive.keepAlive(vimPort, getServiceInstanceReference());
            keepAlive.start();
            // final String[] spinner = new String[] {"\u0008/", "\u0008-", "\u0008\\", "\u0008|"};
            // logger.info(".");
            // int i = 0;
            while ((len = in.read(buf, 0, bufLen)) != -1) {
                tmp = new byte[len];
                System.arraycopy(buf, 0, tmp, 0, len);
                out.write(tmp, 0, len);
                // logger.info("%s", spinner[i++ % spinner.length]);
            }
            // logger.info("\u0008");
            keepAlive.interrupt();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                // ignore
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                // ignore
            }
            try {
                conn.getResponseCode();
            } catch (Exception e) {
                throw e;
            } finally {
                conn.disconnect();
            }
        }

    }
    
    private void putVMDKFile() throws Exception {
        customValidation();
        String basename = FilenameUtils.getBaseName(localPath);
        String remotePath = "images/" + mirrorId + "/" + basename + ".vmdk";
        putVMFile(remotePath, new File(localPath));
        path = "[" + datastoreName + "] " + remotePath;
    }
}
