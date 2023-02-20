/*
 * <b>文件名</b>：ShiroConstant.java
 *
 * 文件描述：
 *
 *
 * 2017年11月17日  下午1:36:42
 */

package com.itheima.shiro.constant;


/**
 * <b>类名：</b>ShiroConstant.java<br>
 * <p><b>标题：</b>意真（上海）金融软件2.0</p>
 * <p><b>描述：</b>意真（上海）金融统一构建系统</p>
 * <p><b>版权声明：</b>Copyright (c) 2017</p>
 * <p><b>公司：</b>意真（上海）金融信息服务有限公司 </p>
 *
 * @author <font color='blue'>束文奇</font>
 * @version 1.0.1
 * @date 2017年11月17日 下午1:36:42
 * @Description shiro的常量类
 */

public class ShiroConstant {

    /**
     * 后台系统
     **/
    public static final String PLATFORM_MGT = "platform_mgt";

    public static final Integer NO_LOGIN_CODE = 1;

    public static final String NO_LOGIN_MESSAGE = "请先进行登录";

    public static final Integer LOGIN_SUCCESS_CODE = 2;

    public static final String LOGIN_SUCCESS_MESSAGE = "登录成功";

    public static final Integer LOGIN_FAILURE_CODE = 3;

    public static final String LOGIN_FAILURE_MESSAGE = "登录失败";

    public static final Integer NO_AUTH_CODE = 5;

    public static final String NO_AUTH_MESSAGE = "权限不足";

    public static final Integer NO_ROLE_CODE = 6;

    public static final String NO_ROLE_MESSAGE = "用户角色不符";

}
