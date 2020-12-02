package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.List;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.VirtualMachineConfigSpec;

public class ConfigVmVncTask extends BaseTask<Object> {

    /**
     * 虚机标识
     */
    private String vm;

    private int vncport;

    public ConfigVmVncTask(VsphereClient client, String vm, int vncport) {
        super(client);
        this.vm = vm;
        this.vncport = vncport;
    }

    @Override
    public Object doTask() throws Exception {
        if (vncport < 5900) {
            throw new IllegalArgumentException("vnc port number " + vncport + " invalid");
        }
        ManagedObjectReference vmMOR = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        if (vmMOR != null) {
            VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
            List<OptionValue> configs = vmConfigSpec.getExtraConfig();
            OptionValue val = new OptionValue();
            val.setKey("RemoteDisplay.vnc.enabled");
            val.setValue("TRUE");
            configs.add(val);
            val = new OptionValue();
            val.setKey("RemoteDisplay.vnc.port");
            val.setValue(vncport + "");
            configs.add(val);
            vimPort.reconfigVMTask(vmMOR, vmConfigSpec);
        } else {
//            logger.warn("Vcenter VM instance [{}] not found.", vm);
        }

        return null;
    }

}
