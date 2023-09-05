package com.dxm.dxmcharge.logic.network;

import androidx.annotation.NonNull;


import java.io.IOException;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器，修改请求header
 */
public class RequestInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request(); // 获取旧连接
        Request.Builder requestBuilder = oldRequest.newBuilder(); // 建立新的构建者
        // 将旧请求的请求方法和请求体设置到新请求中
        requestBuilder.method(oldRequest.method(), oldRequest.body());
        // 获取旧请求的头
        Headers headers = oldRequest.headers();
        Set<String> names = headers.names();
        for (String name : names) {
            String value = headers.get(name);
            // 将旧请求的头设置到新请求中
            requestBuilder.header(name, value);
        }
        // 添加额外的自定义公共请求头
//        requestBuilder.header("language", CommentUtils.getLanguage());
        // 建立新请求连接
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }
}
