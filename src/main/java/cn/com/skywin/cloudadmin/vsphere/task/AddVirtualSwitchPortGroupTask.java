package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.List;
import java.util.Map;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ArrayOfManagedObjectReference;
import com.vmware.vim25.HostConfigManager;
import com.vmware.vim25.HostNetworkPolicy;
import com.vmware.vim25.HostPortGroupSpec;
import com.vmware.vim25.ManagedObjectReference;

/**
 * 在主机上添加标准端口组
 * 
 * @author zwj
 * @date 2020/12/03
 */
public class AddVirtualSwitchPortGroupTask extends BaseTask<String> {

    /**
     * 需要处理的物理机标识
     */
    private String host;

    /**
     * 端口组的名称
     */
    private String portgroupname;

    /**
     * vswitch的名字
     */
    private String switchName;

    private int vlanId;

    private ManagedObjectReference hostmor = null;

    public AddVirtualSwitchPortGroupTask(VsphereClient client, String host, String portgroupname, String switchName,
        int vlanId) {
        super(client);
        this.host = host;
        this.portgroupname = portgroupname;
        this.switchName = switchName;
        this.vlanId = vlanId;
    }

    private void customValidation() throws Exception {

        if (vlanId < 0 || vlanId > 4094) {
            throw new IllegalArgumentException("vlanId [" + vlanId + "] not valid");
        }

        Map<String, ManagedObjectReference> hostList =
            getMOREFs.inFolderByType(serviceContent.getRootFolder(), "HostSystem");
        for (ManagedObjectReference ref : hostList.values()) {
            if (ref.getValue().equals(host)) {
                hostmor = ref;
            }
        }
        if (hostmor == null) {
            throw new IllegalArgumentException("Host [" + host + "] was not found");
        }
    }

    private void addVirtualSwitchPortGroup() throws Exception {

        HostConfigManager configMgr =
            (HostConfigManager)getMOREFs.entityProps(hostmor, new String[] {"configManager"}).get("configManager");
        ManagedObjectReference nwSystem = configMgr.getNetworkSystem();
        HostPortGroupSpec portgrp = new HostPortGroupSpec();
        if (vlanId > 0) {
            portgrp.setVlanId(vlanId);
        }
        portgrp.setName(portgroupname);
        portgrp.setVswitchName(switchName);
        portgrp.setPolicy(new HostNetworkPolicy());
        vimPort.addPortGroup(nwSystem, portgrp);
    }

    @Override
    public String doTask() throws Exception {
        customValidation();
        addVirtualSwitchPortGroup();
        // 这个接口没有task返回，只能一直等到查询到对应的数据在返回了
        for (int i = 0; i < 10; i++) {
            List<ManagedObjectReference> nets =
                ((ArrayOfManagedObjectReference)getMOREFs.entityProps(hostmor, new String[] {"network"}).get("network"))
                    .getManagedObjectReference();
            for (ManagedObjectReference net : nets) {
                String name = (String)getMOREFs.entityProps(net, new String[] {"name"}).get("name");
                if (portgroupname.equals(name)) {
                    return net.getValue();
                }
            }
            try {
                Thread.sleep(3000L);
            }catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

}
