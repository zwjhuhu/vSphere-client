package cn.com.skywin.cloudadmin.vsphere.task;

import com.github.kubesys.vsphere.VsphereClient;
import com.vmware.vim25.ManagedObjectReference;

/**
 * 物理删除虚机或者模板，不能恢复！<br/>
 * 这个删除是彻底删除包括所有的虚机硬盘文件都会完全的删除<br>
 * 如果是删除虚机模板，还需要手动删除模板本来使用的虚拟硬盘文件
 * 
 * @author zwj
 * @date 2020/11/28
 */
public class DestroyVMOrImageTask extends BaseTask<Object> {

    /**
     * 虚机标识
     */
    private String vm = "";

    public DestroyVMOrImageTask(VsphereClient client, String vm) {
        super(client);
        this.vm = vm;
    }

    @Override
    public Object doTask() throws Exception {
        destroyVM();
        return null;
    }

    private void destroyVM() throws Exception {
        ManagedObjectReference refvm = findTypeReferenceById(TYPE_VIRTUALMACHINE, vm);
        if (refvm == null) {
            //虚拟不存在，直接返回
            return;
        }
        ManagedObjectReference reftask = vimPort.destroyTask(refvm);
        if (!getTaskResultAfterDone(reftask)) {
            throw new IllegalStateException("Some Exception While Destory The VM [" + vm + "]");
        }
    }
}
