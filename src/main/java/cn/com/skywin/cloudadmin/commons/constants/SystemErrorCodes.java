package cn.com.skywin.cloudadmin.commons.constants;

/**
 * @ClassName SystemErrorCodes
 * @Description
 * @Author zwj
 * @Date 2019/7/16 15:03
 * @Version 1.0
 *
 *          系统错误码 5位数字，前两位标记类别，后面的三位表示具体的错误 00001-00999 预留给公用的错误
 */
public final class SystemErrorCodes {
	private SystemErrorCodes() {

	}

	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;
	
	/**
	 * 数据库记录不存在
	 */
	public static final int DATA_NOT_EXISTS = 100;
	

	public static final int BAD_REQUEST = 400;

	public static final int UN_AUTHORIZED = 401;

	public static final int SC_FORBIDDEN = 403;

	public static final int NOT_FOUND = 404;

	public static final int METHOD_NOT_SUPPORTED = 405;

	public static final int PARAM_MISS = 406;

	public static final int PARAM_TYPE_ERROR = 407;

	public static final int PARAM_VALID_ERROR = 408;

	public static final int PARAM_BIND_ERROR = 409;

	public static final int MSG_NOT_READABLE = 410;



	/**
	 * 未知错误
	 */
	public static final int INTERNAL_ERROR = 500;
	
	
	/**
	 * 网络异常
	 */
	public static final int NETWORK_EXCEPTION = 600;

	/**
	 * k8s客户端异常
	 */
	public static final int KUBE_CLIENT_ERROR = 601;
	
	 /**
     * vsphere客户端异常
     */
    public static final int VSPHERE_CLIENT_ERROR = 602;

    
    /**
    * ipmi客户端异常
    */
   public static final int IPMI_CLIENT_ERROR = 603;
	
	public static class VM {

		/**
		 * 虚拟机已存在
		 */
		public static final int ALREAY_EXISTS = 1001;

		/**
		 * 无法满足的分配策略
		 */
		public static final int ALLOCATORSTRATEGY_NOT_SATISFY = 1002;

		/**
		 * 无法获取物理机cpu负载数据
		 */
		public static final int NOT_FIND_HOST_CPUUSAGE = 1003;

		/**
		 * 无法获取物理机内存负载数据
		 */
		public static final int NOT_FIND_HOST_MEMORYUSAGE = 1004;
		
	     /**
         * 分配物理机失败，亲和组强制约束
         */
        public static final int HOST_NOT_AVAIL_AFFINITYGROUP_FORCE = 1005;
        
        /**
        * 分配物理机失败，没有启用或就绪的物理机
        */
       public static final int HOST_NOT_AVAIL_NOTENABLE = 1006;
       
       /**
       * 分配物理机失败，存储状态异常或空间不足
       */
      public static final int HOST_NOT_AVAIL_SOTRAGE_NOT_AVAIL = 1007;
      
      /**
      * 分配物理机失败，镜像不存在或不可用
      */
     public static final int HOST_NOT_AVAIL_IMAGE_NOT_AVAIL = 1008;
     
     /**
     * 分配物理机失败，镜像存储不存在或不可用
     */
    public static final int HOST_NOT_AVAIL_IMAGE_SOTRAGE_NOT_AVAIL = 1009;
      
      /**
      * 分配物理机失败，二层网络不可用
      */
      public static final int HOST_NOT_AVAIL_L2NETWORK = 1010;
      
      /**
      * 分配物理机失败，三层网络不可用
      */
      public static final int HOST_NOT_AVAIL_L3NETWORK = 1011;
		
	}
	
	public static class VMDISK {

		/**
		 * 虚拟硬盘已存在
		 */
		public static final int ALREAY_EXISTS = 2001;
				
		/**
		 * 主存储状态异常或容量不足
		 */
		public static final int PRIMARY_SOTRAGE_NOT_AVAIL=2002;
		
	      /**
         * 镜像不可用
         */
        public static final int IMAGE_NOT_AVAIL=2003;
        
        /**
         * 镜像存储不可用
         */
        public static final int IMAGE_SOTRAGE_NOT_AVAIL=2004;
        
        /**
         * 快照数量超过限制
         */
        public static final int SNAPSHOT_COUNT_LIMIT = 2101;

	}
	
	public static class VROUTER {
		/**
		 * agent 返回失败
		 */
		public static final int AGENT_RET_ERROR = 8001;
	}

}
