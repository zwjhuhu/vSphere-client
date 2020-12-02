package cn.com.skywin.cloudadmin.vsphere.task;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.GregorianCalendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.soap.SOAPFaultException;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ArrayOfByte;
import com.vmware.vim25.GuestFileAttributes;
import com.vmware.vim25.GuestPosixFileAttributes;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.NamePasswordAuthentication;
import com.vmware.vim25.VirtualMachinePowerState;

import cn.com.skywin.cloudadmin.commons.constants.SystemErrorCodes;
import cn.com.skywin.cloudadmin.exceptions.VmOperException;

/**
 * 通过虚机内部的vmware tools 上传文件到虚机内部
 * @author zwj
 * @date 2020/11/30
 */
public class UploadGuestFileTask extends BaseTask<Object> {

    private Boolean overwrite = Boolean.TRUE;

    String guestFilePath;
    String localFilePath;
    String vm;
    String guestUsername;
    String guestPassword;

    private VirtualMachinePowerState powerState;

    private ManagedObjectReference hostMOR;
    private long fileSize;
    private X509Certificate x509CertificateToTrust = null;

    public UploadGuestFileTask(VsphereClient client, String guestFilePath, String localFilePath, String vm,
        String guestUsername, String guestPassword) {
        super(client);
        this.guestFilePath = guestFilePath;
        this.localFilePath = localFilePath;
        this.vm = vm;
        this.guestUsername = guestUsername;
        this.guestPassword = guestPassword;
    }

    private void uploadData(String urlString, String fileName) throws IOException {
        HttpURLConnection conn = null;
        URL urlSt = new URL(urlString);
        conn = (HttpURLConnection)urlSt.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Length", Long.toString(fileSize));

        OutputStream out = null;
        InputStream in = null;
        try {
            out = conn.getOutputStream();
            in = new FileInputStream(fileName);
            byte[] buf = new byte[102400];
            int len = 0;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    // ignore
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        int returnErrorCode = conn.getResponseCode();
        conn.disconnect();
        if (HttpsURLConnection.HTTP_OK != returnErrorCode) {
            throw new IllegalStateException("File Upload is unsuccessful," + "maybe can't connect to url " + urlString);
        }
    }

    @Override
    public Object doTask() throws Exception {

        ManagedObjectReference vmMOR = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        if (vmMOR != null) {
            powerState = (VirtualMachinePowerState)getMOREFs.entityProps(vmMOR, new String[] {"runtime.powerState"})
                .get("runtime.powerState");
            
            if (!powerState.equals(VirtualMachinePowerState.POWERED_ON)) {
                throw new VmOperException(SystemErrorCodes.VSPHERE_CLIENT_ERROR,
                    "Vcenter VM instance [" + vm + "] needs to be powered on");
            }
        } else {
            throw new VmOperException(SystemErrorCodes.VSPHERE_CLIENT_ERROR,
                "Vcenter VM instance [" + vm + "] not found.");
        }

        String[] opts = new String[] {"guest.guestOperationsReady"};
        String[] opt = new String[] {"guest.guestOperationsReady"};
        Object[] results = waitForValues.wait(vmMOR, opts, opt, new Object[][] {new Object[] {true}});

        ManagedObjectReference guestOpManger = serviceContent.getGuestOperationsManager();
        ManagedObjectReference fileManagerRef = (ManagedObjectReference)getMOREFs
            .entityProps(guestOpManger, new String[] {"fileManager"}).get("fileManager");
        NamePasswordAuthentication auth = new NamePasswordAuthentication();
        auth.setUsername(guestUsername);
        auth.setPassword(guestPassword);
        auth.setInteractiveSession(false);

        GuestFileAttributes guestFileAttributes = null;
        // if (guestType.equalsIgnoreCase("windows")) {
        // guestFileAttributes = new GuestWindowsFileAttributes();
        // } else {
        guestFileAttributes = new GuestPosixFileAttributes();
        // }

        guestFileAttributes
            .setAccessTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));

        guestFileAttributes
            .setModificationTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));

        File file = new File(localFilePath);
        if (!file.exists()) {
            throw new IllegalStateException("Error finding the file: " + localFilePath);
        }

        if (file.isDirectory()) {
            throw new IllegalStateException("Local file path " + localFilePath + " is a directory");
        }

        fileSize = file.length();
//        logger.info("Size of the file is {}", fileSize);
//        logger.info("Executing UploadFile guest operation");
        String fileUploadUrl;
        try {
            fileUploadUrl = vimPort.initiateFileTransferToGuest(fileManagerRef, vmMOR, auth, guestFilePath,
                guestFileAttributes, fileSize, overwrite);
        } catch (SOAPFaultException sfe) {
//            logger.info(sfe.getMessage());
            if (sfe.getMessage().contains(guestFilePath.replaceAll("//", "/") + " already exists")) {
//                logger.info("To overwrite the File use --overwrite option");
            }
            return null;
        }

        URL tempUrlObject = new URL(connection.getUrl());
        fileUploadUrl = fileUploadUrl.replaceAll("\\*", tempUrlObject.getHost());
//        logger.info("Uploading the file to {}", fileUploadUrl);

        if (hostMOR != null) {
            opts = new String[] {"config.certificate"};
            opt = new String[] {"config.certificate"};
            results = waitForValues.wait(hostMOR, opts, opt, null);
            List<Byte> certificate = ((ArrayOfByte)results[0]).getByte();
            byte[] certificateBytes = new byte[certificate.size()];
            int index = 0;
            for (Byte b : certificate) {
                certificateBytes[index++] = b.byteValue();
            }

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            x509CertificateToTrust =
                (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(certificateBytes));
//            logger.info("Certificate of the host is successfully retrieved");
        }

        uploadData(fileUploadUrl, localFilePath);
        return null;
    }

}
