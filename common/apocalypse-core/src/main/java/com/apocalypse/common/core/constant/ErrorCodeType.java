package com.apocalypse.common.core.constant;

/**
 * @author <a href="kaihuijing@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/7/16
 */
public class ErrorCodeType {

    /**
     * 用户错误，包括用户输入参数错误、用户客户端版本错误、用户设备不支持等
     */
    public static final String USER = "U";

    /**
     * 系统故障，未知的系统错误
     */
    public static final String SYSTEM = "S";

    /**
     * 系统调用三方出错
     */
    public static final String THIRD = "T";

    /**
     * 业务处理出错
     */
    public static final String BUSINESS = "B";

    /**
     * 网络错误
     */
    public static final String NETWORK = "N";

    /**
     * 数据库错误
     */
    public static final String DATABASE = "D";



}
