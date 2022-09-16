package com.ssfw.common.util;

import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Https工具类<br>
 * @Project: SP <br>
 * @date 2012/1/12 9:24 <br>
 * @author <a href="a@hotmail.com">a</a>
 */
public class HttpsUtil {


    public static final int CONNECT_TIMEOUT_SECONDS = 30;


    public static byte[] post(@NotNull String url, Map<String, String> params) throws IOException {
        return post(url,params,null,0);
    }

    public static byte[] post(@NotNull String url,Map<String, String> params,Map<String, String> headers,int timeoutSeconds) throws IOException {

        FormBody.Builder form = new FormBody.Builder();
        if (null!=params){
            for (String key : params.keySet()) {
                form.add(key,params.get(key));
            }
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(form.build());

        return execute(requestBuilder,headers,timeoutSeconds);
    }



    public static byte[] put(@NotNull String url,Map<String, String> params,Map<String, String> headers,int timeoutSeconds) throws IOException {

        FormBody.Builder form = new FormBody.Builder();
        if (null!=params){
            for (String key : params.keySet()) {
                form.add(key,params.get(key));
            }
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

        RequestBody jsonBody = FormBody.create(json, MediaType.parse("application/json; charset=utf-8"));
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

        try {
            //ssl verifier
            KeyStore trustStore;
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactoryImpl ssl = new SSLSocketFactoryImpl(KeyStore.getInstance(KeyStore.getDefaultType()));

            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(ssl.getSSLContext().getSocketFactory(), ssl.getTrustManager())
                    .hostnameVerifier((hostname, session) -> true)
                    .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                    .readTimeout(timeoutSeconds,TimeUnit.SECONDS)
                    .build();

            if (null != headers){
                requestBuilder = requestBuilder.headers(Headers.of(headers));
            }
            ResponseBody responseBody = client.newCall(requestBuilder.build()).execute().body();
            if(null!=responseBody){
                return responseBody.bytes();
            }
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static class SSLSocketFactoryImpl extends SSLSocketFactory {

        private final SSLContext sslContext = SSLContext.getInstance("SSL");
        private TrustManager trustManager;


        public SSLContext getSSLContext() {
            return sslContext;
        }

        public X509TrustManager getTrustManager() {
            return (X509TrustManager)trustManager;
        }

        public SSLSocketFactoryImpl(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException {
            trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    //注意这里不能返回null，否则会报错,如下面错误[1]
                    return new X509Certificate[0];
                }
            };

            sslContext.init(null, new TrustManager[]{trustManager}, null);
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return new String[0];
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return new String[0];
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

        @Override
        public Socket createSocket(Socket socket, String host, int post, boolean autoClose) throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, post, autoClose);
        }

        @Override
        public Socket createSocket(String s, int i) {
            return null;
        }

        @Override
        public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) {
            return null;
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int i) {
            return null;
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) {
            return null;
        }
    }
}
