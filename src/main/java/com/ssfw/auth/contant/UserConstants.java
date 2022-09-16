package com.ssfw.auth.contant;


/**
 * 用户常量
 * 
 * @author 胡勇
 * 
 */
public class UserConstants {

	/** 用户登录名 */
	public final static String LOGIN_USERNAME = "username";
	/** 用户登录密码 */
	public final static String LOGIN_PASSWORD = "password";

	/** 租户id session */
	public final static String SESSION_TENANT_ID = "currentSessionTenantID";

	/** 用户登录名 session */
	public final static String SESSION_USER_ID = "currentSessionUserID";

	/** 用户name session */
	public final static String SESSION_USER_NAME = "currentSessionUserName";
	
	/** 用户组织name session */
	public final static String SESSION_NICK_NAME = "currentSessionNickName";

	/**是否为新手模式  session */
	public final static String SESSION_BEGINNER_MODE="H8B8B969137B634A10C";

	/** 用户session 有权限（或者无权限）的资源code*/
	public final static String SESSION_USER_RES_CODE = "currentSessionUserResCode";

	/** 用户session 有权限（或者无权限）的菜单列表未过滤了隐藏菜单*/
	public final static String SESSION_USER_MENU = "currentSessionUserMenu";

	/** 用户session 有权限（或者无权限）的菜单列表 过滤了隐藏菜单 */
	public final static String SESSION_USER_MENU_LIST = "currentSessionUserMenuList";

	/** 用户session 有权限（或者无权限）的菜单菜单树 过滤了隐藏菜单 */
	public final static String SESSION_USER_MENU_TREE = "currentSessionUserMenuTree";

	/** 用户角色 集合*/
	public final static String SESSION_USER_ROLE = "currentSessionUserRole";

	/**当前用户对象 com.inmr.sp.security.model.SecurityUser */
	public final static String SESSION_USER_OBJECT = "currentSessionUserObject";

	/**当前用户对象 登入的signId */
	public final static String SESSION_SIGN_ID = "currentSessionSignId";

}
