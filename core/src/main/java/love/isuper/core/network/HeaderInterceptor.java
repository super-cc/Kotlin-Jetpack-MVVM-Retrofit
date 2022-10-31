package love.isuper.core.network;

import love.isuper.core.network.NetworkManager;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by shichao on 2022/7/22.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Headers headers = oldRequest.headers();
        Headers.Builder builder = headers.newBuilder();
        for (Map.Entry<String, String> header : getHeaders().entrySet()) {
            if (builder.get(header.getKey()) == null) {
                builder.add(header.getKey(), header.getValue());
            }
        }
        Request request = oldRequest.newBuilder()
                .headers(builder.build())//注入头信息
                .build();
        return chain.proceed(request);
    }

    /**
     * 获取头信息
     */
    private Map<String, String> getHeaders() {
        return NetworkManager.Companion.getInstance().getHeaders();
    }

}