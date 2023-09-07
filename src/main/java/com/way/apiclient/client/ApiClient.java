package com.way.apiclient.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.way.apiclient.utils.SignUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author Way
 */
public class ApiClient {
    /**
     * AppID
     */
    private String accessKey;
    /**
     * 密钥
     */
    private String secretKey;

    private static final String HOST = "http://localhost:8090";

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;

    }

    public HashMap<String, String> getHeaderMap(String body) {
        HashMap<String, String> headerMap = new HashMap<>();
        String nonce = HttpUtil.get(HOST + "/api2/name/nonce");
        headerMap.put("accessKey", accessKey);
        headerMap.put("nonce", nonce);
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        headerMap.put("sign", SignUtils.getSign(headerMap, secretKey, null));
        return headerMap;
    }

    public String invokeInterface(String url, String params, String method) {
        //todo 修改签名认证算法
        String resp = null;
        if (StringUtils.equalsIgnoreCase(method, "GET")) {
            resp = HttpRequest.get(url + "?" + params)
                    .header("accessKey", accessKey)
                    .header("secretKey", secretKey)
                    .execute()
                    .body();
        } else if (StringUtils.equalsIgnoreCase(method, "POST")) {
            resp = HttpRequest.post(url)
                    .header("accessKey", accessKey)
                    .header("secretKey", secretKey)
                    .body(params)
                    .execute()
                    .body();
        }else {
            throw new RuntimeException("使用了不支持的请求方法");
        }
        return resp;
    }

    public static void main(String[] args) {
//        ApiClient client = new ApiClient("125714589eee67b248f2842e9c4b1c97", "c98def8f894170787f34038a1a02958c");
//        User user = new User();
//        user.setUsername("测试222");
//        System.out.println(client.getNameByPost(user));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", "125714589eee67b248f2842e9c4b1c97");
        hashMap.put("nonce", "704013");
        hashMap.put("timestamp", "1681006325761");
        System.out.println(System.currentTimeMillis());
        String sign = SignUtils.getSign(hashMap, "c98def8f894170787f34038a1a02958c", null);
        System.out.println(sign);
    }
}
