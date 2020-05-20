package com.lixy.boothigh.utils;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * http客户端工具类
 *
 * @param
 * @author silent
 * @date 2018/8/16
 * @return
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 连接超时时间
     */
    private static final long CONNECT_TIMEOUT = 30;

    /**
     * 读取数据超时时间
     */
    private static final long READ_TIMEOUT = 30;


    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).build();


    /**
     * POST请求
     *
     * @param url       请求地址
     * @param reqParams 请求地址附属参数
     * @param reqBody   请求体，如对象
     * @return 响应体字符串
     * @author silent
     * @date 2018/8/16
     */
    public static String post(String url, Map<String, String> reqParams, String reqBody) {

        if (StringUtils.isBlank(url)) {
            LOGGER.warn("post请求url为空");
            return null;
        }

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        /**
         * 组装url
         */
        url = assembleReqUrl(url, reqParams);

        if (null == reqBody) {
            reqBody = "";
        }

        RequestBody requestBody = RequestBody.create(mediaType, reqBody);
        Request request = new Request
                .Builder()
                .post(requestBody)
                .url(url)
                .build();

        try (Response response =  OKHttpClientBuilder.buildOKHttpClient()
                .build().newCall(request).execute()) {

            int code = response.code();
            if (HttpStatus.OK.value() != code && HttpStatus.NO_CONTENT.value() != code) {
                LOGGER.warn("响应状态异常, url:{}, reqBody:{}, status:{}", url, reqBody, code);
                return null;
            }

            ResponseBody body = response.body();
            if (null == body) {
                LOGGER.warn("响应体为空, url:{}, reqBody:{}, status:{}", url, reqBody, code);
                return null;
            }

            String respBody = body.string();
            if (StringUtils.isBlank(respBody)) {
                return null;
            }
            return respBody;
        } catch (IOException e) {
            LOGGER.error("post请求失败, url: {}, reqBody: {}", url, reqBody, e);
            return null;
        }
    }


    /**
     * POST请求(异步)
     *
     * @param url       请求地址
     * @param reqParams 请求地址附属参数
     * @param reqBody   请求体
     * @return future字符串
     * @author silent
     * @date 2018/8/17
     */
    public static FutureWrapper<String> postAsync(String url, Map<String, String> reqParams, String reqBody) {
        FutureWrapper<String> future = ThreadPoolUtils.submitToIoPool(() -> post(url, reqParams, reqBody));
        return future;
    }


    /**
     * GET请求
     * @author MR LIS
     * @date 2018/11/28 9:41　　
     * @param url
     * @param reqParams
     * @return
     */
    public static String get(String url,Map<String, String> reqParams) {
        if (StringUtils.isBlank(url)) {
            LOGGER.warn("get请求url为空");
            return null;
        }

        url = assembleReqUrl(url, reqParams);

        Request request = new Request
                .Builder()
                .get()
                .url(url)
                .build();

        try (Response response =  OKHttpClientBuilder.buildOKHttpClient()
                .build().newCall(request).execute()) {

            int code = response.code();
            if (HttpStatus.OK.value() != code && HttpStatus.NO_CONTENT.value() != code) {
                LOGGER.warn("响应状态异常, url:{}, status:{},message:{}", url, code,response.message());
                return null;
            }

            ResponseBody body = response.body();
            if (null == body) {
                LOGGER.warn("响应体为空, url:{}, status:{}", url, code);
                return null;
            }

            String respBody = body.string();
            if (StringUtils.isBlank(respBody)) {
                return null;
            }
            return respBody;
        } catch (IOException e) {
            LOGGER.error("post请求失败, url: {}, exception: {}", url, e);
            return null;
        }

    }

    /**
     * 组装url
     * @param url
     * @param reqParams
     * @return
     */
    private static String assembleReqUrl(String url, Map<String, String> reqParams) {
        if (null != reqParams && !reqParams.isEmpty()) {
            StringBuilder urlSb = new StringBuilder(url).append("?");
            for (Map.Entry<String, String> entry : reqParams.entrySet()) {
                String value = entry.getValue();
                if (null == value) {
                    value = "";
                }
                urlSb.append(entry.getKey()).append("=").append(value);
            }
            url = urlSb.toString();
        }
        return url;
    }

}
