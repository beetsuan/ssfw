package com.ssfw.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * IP定位解析
 * @author a
 * @date 2022/7/20
 * @since 2.7.1
 */
@Slf4j
public class IpLocationUtil {


    /**
     * 取得Ip定位
     *
     * @param ip IP地址
     * @return ["IP地址","国家","地区","城市","县镇","ISP"]
     */
    public static String[] parse(String ip){

        String[] results = new String[6];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
                .url("https://restapi.amap.com/v3/ip?key=&ip="+ip)
                .get().build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            String responseText;
            if(null!=responseBody){
                byte[] bytes = responseBody.bytes();
                responseText = new String(bytes, StandardCharsets.UTF_8);
                if(StringUtils.isNotBlank(responseText)){
                    results[0] = ip;
                    results[1] = null;
                    Map<String, Object> map = JsonUtil.string2Map(responseText);
                    if (null == map){
                        log.warn("JsonUtil.string2Map map is null");
                        return results;
                    }
                    Object province = map.get("province");
                    if (null!=province){
                        results[2] = province.toString();
                        if(results[2].length()>2&&results[2].endsWith("省")) {
                            results[2] = results[2].substring(0,results[2].length()-1);
                        }
                    }
                    Object city = map.get("city");
                    if (null!=city){
                        results[3] = city+"";
                        if(results[3].length()>2&&results[3].endsWith("市")) {
                            results[3] = results[3].substring(0,results[3].length()-1);
                        }
                    }
                    results[4] = null;
                    results[5] = null;
                }else{
                    log.warn("获取IP地址归属地信息[amap]失败,responseText is empty.");
                }
            }
        } catch (Exception e) {
            log.error("获取IP地址归属地信息[amap]失败,IP:"+ip+" "+e.getMessage());
        }
        return results;
    }

}
