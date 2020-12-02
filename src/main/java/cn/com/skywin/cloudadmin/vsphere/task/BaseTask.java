package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.connection.BasicConnection;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.vim25.InvalidCollectorVersionFaultMsg;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.LocalizedMethodFault;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.TaskInfoState;

import cn.com.skywin.cloudadmin.commons.constants.SystemErrorCodes;
import cn.com.skywin.cloudadmin.exceptions.VmOperException;

/**
 * 是一个基类，用来的代表vcenter的任务，抽取公用参数
 * 
 * @author zwj
 * @date 2020/11/23
 */
public abstract class BaseTask<T> extends ConnectedVimServiceBase {
    
    public static final String TYPE_VIRTUALMACHINE = "VirtualMachine";
    
    public static final String TYPE_DATACENTER = "Datacenter";
    
    public static final String TYPE_DATASTORE = "Datastore";
    
    public static final String TYPE_NETWORK = "Network";
    
    public static final String TYPE_HOSTSYSTEM = "HostSystem";
    
//    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    public static void trustAllHttpsCertificates() throws Exception {
        
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                                   + session.getPeerHost());
                return true;
            }
        };
        
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
            throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
            throws java.security.cert.CertificateException {
            return;
        }
    }
    
    static {
        try {
            trustAllHttpsCertificates();
        }catch (Exception e) {
            //ignore
        }
    }

    /**
     * 连接sdk的url
     */
    private String sdkurl;
    
    /**
     * vcetner 域名/IP
     */
    private String hostname;

    /**
     * 端口
     */
    private int port;

    /**
     * 是否https
     */
    private boolean https;

    /**
     * vcenter登录用户
     */
    private String username;

    /**
     * vcenter登录密码
     */
    private String password;

    /**
     * vcenter的版本，先预留
     */
    private String version;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected BaseTask(String hostname, int port, boolean https, String username, String password) {
        init(hostname, port, https, username, password);
    }
    
    /**
     * 为了兼容博士接口设置
     * @param client
     */
    public BaseTask(VsphereClient client) {
        String url = client.getUrl();
        while(url.endsWith("/")) {
            url = url.substring(0,url.length()-1);
        }
        this.https = url.startsWith("https");
        int start = url.indexOf("//");
        int end = url.lastIndexOf(':');
        String h = null;
        int p = 0;
        if(end<start) {
            int last = url.lastIndexOf('/');
            h = url.substring(last+1);
        }else {
            h = url.substring(start+2, end);
            p = Integer.parseInt(url.substring(end+1));
        }
        init(h, p, 
            url.startsWith("https"), client.getUsername(), client.getPassword());
    }
    
    private void init(String hostname, int port, boolean https, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.https = https;
        this.username = username;
        this.password = password;
        StringBuilder sb = new StringBuilder();
        if(https) {
            sb.append("https://");
        }else {
            sb.append("http://");
        }
        sb.append(hostname);
        if(port>0) {
            sb.append(":").append(port);
        }
        sb.append("/sdk/vimService");
        this.sdkurl = sb.toString();
    }

    public T process() throws VmOperException {
        try {
            connection = new BasicConnection();
            connection.setUrl(sdkurl);
            connection.setUsername(username);
            connection.setPassword(password);
            this.connect();
            return doTask();
        }catch (Exception e) {
//            logger.error("vcenter task error",e);
            if(e instanceof VmOperException) {
                throw (VmOperException)e;
            }else {
                throw new VmOperException(SystemErrorCodes.VSPHERE_CLIENT_ERROR,
                    e.getMessage(),
                    e.getCause());
            }
        }finally {
            if(connection!=null) {
                try {
                    this.disconnect();
                }catch (Exception e) {
//                    logger.error(e.getMessage(),e);
                }
            }
        }
        

    }

    /**
     * 留给子类实现具体的操作，如果有问题，抛出异常
     * 
     * @throws Exception
     */
    protected abstract T doTask() throws Exception;
    
    /**
     * This method returns a boolean value specifying whether the Task is succeeded or failed.
     *
     * @param task
     *            ManagedObjectReference representing the Task.
     * @return boolean value representing the Task result.
     * @throws InvalidCollectorVersionFaultMsg
     *
     * @throws RuntimeFaultFaultMsg
     * @throws InvalidPropertyFaultMsg
     */
    protected boolean getTaskResultAfterDone(ManagedObjectReference task)
        throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg, InvalidCollectorVersionFaultMsg {

        boolean retVal = false;

        // info has a property - state for state of the task
        Object[] result = waitForValues.wait(task, new String[] {"info.state", "info.error"}, new String[] {"state"},
            new Object[][] {new Object[] {TaskInfoState.SUCCESS, TaskInfoState.ERROR}});

        if (result[0].equals(TaskInfoState.SUCCESS)) {
            retVal = true;
        }
        if (result[1] instanceof LocalizedMethodFault) {
            throw new RuntimeException(((LocalizedMethodFault)result[1]).getLocalizedMessage());
        }
        return retVal;
    }
    
    /**
     * 通过id，找到对应类型的名字和管理对象
     * @param type
     * @param id
     * @return 第一个值返回name，第二个值返回管理对象
     * @throws InvalidPropertyFaultMsg 
     * @throws RuntimeFaultFaultMsg 
     */
    protected Object[] findTypeNameAndReferenceById(String type,String id) 
        throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        if(type==null||id==null) {
            return null;
        }
        Map<String, ManagedObjectReference> refs =
            getMOREFs.inFolderByType(serviceContent.getRootFolder(), type);
        if(refs==null||refs.isEmpty()) {
            return null;
        }
        for(String name:refs.keySet()) {
            ManagedObjectReference ref = refs.get(name);
            if(ref!=null&&id.equals(ref.getValue())) {
                return new Object[] {name,ref};
            }
        }
        return null;
    }
    
    /**
     * 通过id，找到对应类型的管理对象
     * @param type
     * @param id
     * @return 
     * @throws InvalidPropertyFaultMsg 
     * @throws RuntimeFaultFaultMsg 
     */
    protected ManagedObjectReference findTypeReferenceById(String type,String id) 
        throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg {
        Object[] ret = findTypeNameAndReferenceById(type, id);
        if(ret!=null) {
            return (ManagedObjectReference)ret[1];
        }
        return null;
    }

}
