package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.List;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.VirtualMachineConfigSpec;

public class ConfigVmVncPasswordTask extends BaseTask<Object> {

    /**
     * 虚机标识
     */
    private String vm;

    /**
     * 需要设置的vnc访问密码，如果是取消就传入""或者null
     */
    private String vncpassword;

    public ConfigVmVncPasswordTask(VsphereClient client, String vm, String vncpassword) {
        super(client);
        this.vm = vm;
        this.vncpassword = vncpassword;
    }

    @Override
    public Object doTask() throws Exception {
        if(vncpassword==null) {
            vncpassword = "";
        }
        ManagedObjectReference vmMOR = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        if (vmMOR != null) {
            VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
            List<OptionValue> configs = vmConfigSpec.getExtraConfig();
            OptionValue val = new OptionValue();
            val.setKey("RemoteDisplay.vnc.password");
            val.setValue(vncpassword);
            configs.add(val);
//            logger.info("try set vm {} vncpassword {}",vm,vncpassword);
            vimPort.reconfigVMTask(vmMOR, vmConfigSpec);
        } else {
//            logger.warn("Vcenter VM instance [{}] not found.", vm);
        }

        return null;
    }

}
