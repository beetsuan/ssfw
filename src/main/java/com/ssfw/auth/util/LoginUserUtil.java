package com.ssfw.auth.util;

import com.ssfw.auth.contant.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author a
 */
public class LoginUserUtil {

	final static Logger log = LoggerFactory.getLogger(LoginUserUtil.class);


	/**
	 * 取得当前用户的主键ID;
	 *
	 * @return 当前用户的主键ID
	 */
	public static Integer getTenantId(){

		HttpSession session = getSession();
		if (null!=session) {
			Object object =session.getAttribute(UserConstants.SESSION_TENANT_ID);
			if (null == object){
				return null;
			}
			return Integer.parseInt(object.toString());
		}
		return  null;
	}

	/**
	 * 取得登录用户ID
	 *
	 * @return 用户ID
	 */
	public static Long getLoginUserId(){
		HttpSession session = getSession();
		if (null!=session) {
			Object object =session.getAttribute(UserConstants.SESSION_USER_ID);
			if (null == object){
				return null;
			}
			return Long.parseLong(object.toString());
		}
		return  null;
	}

	/**
	 * 取得登录用户的登入名;
	 *
	 * @return 登入名
	 */
	public static String getLoginUsername(){

		HttpSession session = getSession();
		if (null!=session) {
			Object object =session.getAttribute(UserConstants.SESSION_USER_NAME);
			return null==object?null:object.toString();
		}
		return  null;
	}

	/**
	 * 取得登录的用户昵称
	 *
	 * @return 用户昵称
	 */
	public static String getLoginNickname(){
		HttpSession session = getSession();
		if (null!=session) {
			Object object =session.getAttribute(UserConstants.SESSION_NICK_NAME);
			if (null == object){
				return null;
			}
			return object.toString();
		}
		return  null;
	}

	/**
	 * 取得登录用户的角色名称;
	 *
	 * @return 用户的角色名称 0个或多个
	 */
	public static String getLoginUserRoleName(){

		return  "";
	}

	/**
	 * 判断当前用户是否为系统管理员
	 *
	 * @return true系统管理员 false非系统管理员
	 */
	public static boolean isSysAdmin(){

        return isSysAdmin(getLoginUserId());
    }

	/**
	 * 判断指定用户ID是否为系统管理员ID
	 *
	 * @return true系统管理员 false非系统管理员
	 */
	public static boolean isSysAdmin(Number userId){

		if (null == userId){
			return false;
		}
		return Objects.equals(userId.intValue(),1);
	}

	/**
	 * 判断当前用户是否为系统管理员
	 *
	 * @return true系统管理员 false非系统管理员
	 */
	public static Object getUser(){

		HttpSession session= getSession();
		if (null!=session) {
			return session.getAttribute(UserConstants.SESSION_USER_OBJECT);
		}
		return null;
	}



	public static String isBeginnerMode() {

		if (null!=getSession()) {
			Object object =getSession().getAttribute(UserConstants.SESSION_BEGINNER_MODE);
			return null==object?null:object.toString();
		}
		return null;
	}


	public static HttpSession getSession(){

		HttpServletRequest request = getRequest();
		if (null != request){
			return request.getSession(false);
		}
		return null;
	}

	public static HttpServletRequest getRequest(){

		try {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			return ((ServletRequestAttributes) requestAttributes).getRequest();
		} catch (Exception e) {
			log.error("getRequest throw Exception:{}",e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getRequestAttribute(String key) {

		HttpServletRequest request = getRequest();
		if (null == request){
			return null;
		}
		return (T) request.getAttribute(key);
	}

	public static void setRequestAttribute(String key, Object object) {

		HttpServletRequest request = getRequest();
		if (null == request){
			return;
		}
		request.setAttribute(key, object);
	}


	public static boolean isLogin() {
		return null != LoginUserUtil.getLoginUserId();
	}

}
