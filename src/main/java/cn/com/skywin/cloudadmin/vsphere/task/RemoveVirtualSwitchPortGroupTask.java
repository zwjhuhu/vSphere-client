package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.List;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ArrayOfManagedObjectReference;
import com.vmware.vim25.HostConfigManager;
import com.vmware.vim25.ManagedObjectReference;

/**
 * 在主机上删除标准端口组
 * 
 * @author zwj
 * @date 2020/12/03
 */
public class RemoveVirtualSwitchPortGroupTask extends BaseTask<Object> {

    /**
     * 网络的标识
     */
    private String network;

    private ManagedObjectReference netref = null;

    public RemoveVirtualSwitchPortGroupTask(VsphereClient client, String network) {
        super(client);
        this.network = network;
    }

    private void customValidation() throws Exception {

        netref = findTypeReferenceById(TYPE_NETWORK, network);
        if (netref == null) {
            throw new IllegalArgumentException("Network [" + network + "] was not found");
        }

    }

    private void removeVirtualSwitchPortGroup() throws Exception {

        List<ManagedObjectReference> hostrefs =
            ((ArrayOfManagedObjectReference)getMOREFs.entityProps(netref, new String[] {"host"}).get("host"))
                .getManagedObjectReference();
        if (hostrefs.isEmpty()) {
            return;
        }
        for (ManagedObjectReference ref : hostrefs) {
            HostConfigManager configMgr =
                (HostConfigManager)getMOREFs.entityProps(ref, new String[] {"configManager"}).get("configManager");
            ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
            String name = null;
            List<ManagedObjectReference> nets =
                ((ArrayOfManagedObjectReference)getMOREFs.entityProps(ref, new String[] {"network"}).get("network"))
                    .getManagedObjectReference();
            for (ManagedObjectReference net : nets) {
                if (net.getValue().equals(network)) {
                    name = (String)getMOREFs.entityProps(net, new String[] {"name"}).get("name");
                    break;
                }
            }
            if (name != null) {
                vimPort.removePortGroup(nwSystem, name);
            }
        }
    }

    @Override
    public Object doTask() throws Exception {
        customValidation();
        removeVirtualSwitchPortGroup();
        return null;
    }

}
