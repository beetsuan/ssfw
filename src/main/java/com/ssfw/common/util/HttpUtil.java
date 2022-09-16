package com.ssfw.common.util;

import okhttp3.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http工具类
 * @date 2019/6/11 15:33 <br>
 * @author <a href="a@hotmail.com">a</a>
 */
public class HttpUtil {



    public static final int CONNECT_TIMEOUT_SECONDS = 30;


    private final static String[] IP_HEADER_NAMES = new String[]{"x-real-ip","x-forwarded-for","Proxy-Client-IP","WL-Proxy-Client-IP"};

    /**
     * 是否为ajax请求
     * @param request HttpServletRequest
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("x-requested-with");
        return (
                "XMLHttpRequest".equalsIgnoreCase(header) ||
                        "Xml-Http-Request".equalsIgnoreCase(header)
        );
    }


    /**
     * 取得Ip;
     *
     * @param request HttpServletRequest
     * @return Ip
     */
    public static String getClientIpAddress(HttpServletRequest request){

        String ip = null;
        final String unknown = "unknown";
        //先读代理IP
        for (String name : IP_HEADER_NAMES) {
            ip = request.getHeader(name);
            if (StringUtil.isNotNull(ip) && !ip.equalsIgnoreCase(unknown)) {
                return ip;
            }
        }
        if (StringUtil.isNull(ip) || ip.equalsIgnoreCase(unknown)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取多网卡的IP地址
     * @return IP集合
     */
    public static List<InetAddress> getInetAddresses() throws SocketException {

        List<InetAddress> ipList = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                //回路地址，如127.0.0.1
                if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                    //非链接和回路真实ip
                    ipList.add(inetAddress);
                }
            }
        }
        return ipList;
    }

    public static byte[] post(@NotNull String url, Map<String, String> params) throws IOException {
        return post(url,params,null,0);
    }

    public static byte[] post(@NotNull String url,Map<String, String> params,Map<String, String> headers,int timeoutSeconds) throws IOException {

        FormBody.Builder form = new FormBody.Builder();
        if (null!=params){
            params.keySet().forEach(key -> form.add(key, params.get(key)));
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(form.build());

        return execute(requestBuilder,headers,timeoutSeconds);
    }



    public static byte[] put(@NotNull String url,Map<String, String> params,Map<String, String> headers,int timeoutSeconds) throws IOException {

        FormBody.Builder form = new FormBody.Builder();
        if (null!=params){
            params.keySet().forEach(key -> form.add(key, params.get(key)));
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .put(form.build());

        return execute(requestBuilder,headers,timeoutSeconds);
    }

    public static byte[] get(@NotNull String url) throws IOException{
        return get(url,null,0);
    }


    public static byte[] get(@NotNull String url,Map<String, String> headers,int timeoutSeconds) throws IOException {

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get();

        return execute(requestBuilder,headers,timeoutSeconds);

    }

    public static byte[] postJson(@NotNull String url, @NotNull String json) throws IOException{
        return postJson(url,json,null,0);
    }

    public static byte[] postJson(@NotNull String url, @NotNull String json, Map<String, String> headers, int timeoutSeconds) throws IOException {

        RequestBody jsonBody = FormBody.create(json,MediaType.parse("application/json; charset=utf-8"));
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(jsonBody);

        return execute(requestBuilder,headers,timeoutSeconds);
    }

    /**
     * 执行请求
     * @param requestBuilder Request.Builder
     * @param headers 请求头
     * @param timeoutSeconds 超时
     * @return 响应
     */
    private static byte[] execute(Request.Builder requestBuilder,Map<String, String> headers,int timeoutSeconds) throws IOException{

        timeoutSeconds = 0==timeoutSeconds?CONNECT_TIMEOUT_SECONDS:timeoutSeconds;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutSeconds,TimeUnit.SECONDS).build();

        if (null != headers){
            requestBuilder = requestBuilder.headers(Headers.of(headers));
        }
        ResponseBody responseBody = client.newCall(requestBuilder.build()).execute().body();
        if(null!=responseBody){
            return responseBody.bytes();
        }
        return null;
    }

    /**
     * 打印Header
     * @param request HttpServletRequest
     */
    public static void printHeader(HttpServletRequest request) {

        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            System.out.println("header: "+name+" = "+request.getHeader(name));
        }

        System.out.println("request: contextPath: "+request.getContextPath());
        System.out.println("request: servletPath: "+request.getServletPath());
    }
}
