package cn.com.skywin.cloudadmin.vsphere.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.VirtualMachineConfigInfo;

import cn.com.skywin.cloudadmin.api.vsphere.dto.VmExInfoDto;

/**
 * 获取软件所接口不能获取到的信息
 * 
 * @author zwj
 * @date 2020/11/28
 */
public class FindVmInfosByNameExTask extends BaseTask<VmExInfoDto> {

    /**
     * 虚机的标识
     */
    private String vm;

    public FindVmInfosByNameExTask(VsphereClient client, String vm) {
        super(client);
        this.vm = vm;
    }

    @Override
    public VmExInfoDto doTask() throws Exception {
        VmExInfoDto dto = null;
        ManagedObjectReference vmMOR = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        if (vmMOR != null) {
            Map<String, Object> objs = getMOREFs.entityProps(vmMOR, new String[] {"guest", "config"});
            GuestInfo guest = (GuestInfo)objs.get("guest");
            VirtualMachineConfigInfo config = (VirtualMachineConfigInfo)objs.get("config");
            dto = new VmExInfoDto();
            if (guest != null) {
                dto.setDefIp(guest.getIpAddress());
                if (guest.getNet() != null) {
                    Map<String, List<String>> map = dto.getIps();
                    for (com.vmware.vim25.GuestNicInfo net : guest.getNet()) {
                        String mac = net.getMacAddress();
                        if (StringUtils.isNotBlank(mac)) {
                            List<String> ips = net.getIpAddress();
                            if (ips == null) {
                                ips = new ArrayList<>(0);
                            } else {
                                if (dto.getDefIp() != null && ips.contains(dto.getDefIp())) {
                                    dto.setDefMac(mac);
                                }
                            }
                            map.putIfAbsent(mac, ips);
                        }
                    }
                }
            }
            if (config != null) {
                List<OptionValue> ops = config.getExtraConfig();
                if (ops != null && !ops.isEmpty()) {
                    for (OptionValue op : ops) {
                        if ("RemoteDisplay.vnc.port".equals(op.getKey()) && op.getValue() != null) {
                            int vncport = NumberUtils.toInt(op.getValue().toString(), 0);
                            dto.setVncport(vncport);
                        } else if ("RemoteDisplay.vnc.password".equals(op.getKey()) && op.getValue() != null) {
                            dto.setVncpassword(op.getValue().toString());
                        }
                    }
                }
            }
        } else {
//            logger.warn("Vcenter VM instance [{}] not found.", vm);
        }

        return dto;
    }

}
