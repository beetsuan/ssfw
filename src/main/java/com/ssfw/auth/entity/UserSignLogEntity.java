package com.ssfw.auth.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssfw.auth.util.UserAgentAnalyzer;
import com.ssfw.common.framework.entity.TenantFacade;
import com.ssfw.common.util.HttpUtil;
import com.ssfw.common.util.IpLocationUtil;
import com.ssfw.common.util.LocalDateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import nl.basjes.parse.useragent.UserAgent;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author a
 * @date 2016/11/15 21:05
 * Description:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("com_sign_log")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class UserSignLogEntity implements TenantFacade<UserSignLogEntity>, Serializable {

    public static final int LOG_IN = 1;
    public static final int SIGN_UP = 2;
    public static final int USER_TYPE_DEFAULT = 1;

    /**
     * 记录ID
     */
    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    private Long logId;

    /**
     * 登录人ID
     */
    private Long userId;

    /**session id*/
    private String sessionId;
    /**密码是否是加密*/
    private Integer isEncrypted;
    /**是否为ajax请求*/
    private Integer isAjax;
    /**是否在线 0.不在线 1.在线*/
    private Integer isOnline;
    /**操作时间*/
    private LocalDateTime operDate;

    /**1.登入 2.注册*/
    private Integer signType;
    /**登录用户类型*/
    private Integer userType;
    /**是否操作失败 1.失败 0.成功*/
    private Integer isFailed;
    /**失败的原因*/
    private String failureReason;
    /**浏览器名称*/
    private String browserName;
    /**浏览器版本*/
    private String browserVersion;
    /**操作系统名称*/
    private String osName;
    /**操作系统名称*/
    private String osType;
    /**操作系统厂商*/
    private String osManufactirerName;
    /**设备类型*/
    private String deviceType;
    /**设备ID 1*/
    private String deviceId01;
    /**设备ID 2*/
    private String deviceId02;
    /**设备ID 3*/
    private String deviceId03;
    /**客户端主机名称*/
    private String remoteHost;

    /**客户端IP归属地-国家*/
    private String ipCountry;

    /**客户端IP归属地-地区*/
    private String ipRegion;

    /**客户端IP归属地-城市*/
    private String ipCity;

    /**客户端IP归属地-县镇*/
    private String ipCounty;

    /**客户端IP-服务提供商*/
    private String ipIsp;

    /**通信协议名称 如http、https*/
    private String scheme;

    /**通信协议版本 如HTTP1.1、HTTP2.0*/
    private String protocol;

    /**登录人*/
    private String username;

    /**
     * 租户id
     */
    private Integer tenantId;


    public static UserSignLogEntity login(HttpServletRequest request){
        UserSignLogEntity entity = new UserSignLogEntity(request);
        entity.setSignType(LOG_IN);
        return entity;
    }

    public static UserSignLogEntity signUp(HttpServletRequest request){
        UserSignLogEntity entity = new UserSignLogEntity(request);
        entity.setSignType(SIGN_UP);
        return entity;
    }

    public UserSignLogEntity(HttpServletRequest request) {
        this(request, null);
    }

    public UserSignLogEntity(HttpServletRequest request, String failureReason) {
        super();
        this.scheme = request.getScheme();
        this.isAjax= HttpUtil.isAjaxRequest(request)?1:0;
        this.isEncrypted = 1;
        this.isFailed = 0;
        this.isOnline = 1;
        this.operDate = LocalDateUtil.nowLocalDateTime();
        this.setFailureReason(failureReason);

        String platformVersion = request.getHeader("platform-version");

        String userAgentString = request.getHeader("User-Agent");
        UserAgent.ImmutableUserAgent agent = UserAgentAnalyzer.UUA.parse(userAgentString);

        try {
            this.protocol = request.getProtocol();
            this.browserName = agent.getValue("AgentName");
            this.browserVersion = agent.getValue("AgentVersion");
            this.osManufactirerName = agent.getValue("DeviceName");

            this.osType = agent.getValue("OperatingSystemName");
            this.osName = null==platformVersion ? agent.getValue("OperatingSystemNameVersion"): "Windows "+platformVersion;
            if (this.osName.contains("?")){
                this.osName = this.osName.replace("?","");
            }
            if (userAgentString.contains("x64")){
                this.osName+=" x64";
            }else if (userAgentString.contains("x32")){
                this.osName+=" x32";
            }
            //IP地址
            this.remoteHost = HttpUtil.getClientIpAddress(request);
        } catch (Exception e) {
            log.error("Parse userAgent",e);
        }
    }

    public void ipLookup(){

        //是否解析IP地址归属地
        String[] ipLocation = IpLocationUtil.parse(getRemoteHost());
        this.ipCountry = ipLocation[1];
        this.ipRegion = ipLocation[2];
        if(null!=this.ipRegion) {
            this.ipRegion = this.ipRegion.replace("省","");
        }
        this.ipCity = ipLocation[3];
        if(null!=this.ipCity&&this.ipCity.length()>2&&this.ipCity.endsWith("市")) {
            this.ipCity = this.ipCity.replace("市","");
        }
        this.ipCounty = ipLocation[4];
        this.ipIsp = ipLocation[5];
    }

    public void setFailureReason(String failureReason) {
        if (StringUtils.isNotEmpty(failureReason)){
            setIsFailed(1);
        }
        this.failureReason = failureReason;
    }
}
