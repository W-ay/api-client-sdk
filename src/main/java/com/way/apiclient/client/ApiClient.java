package com.way.apiclient.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.way.apiclient.model.User;
import com.way.apiclient.utils.SignUtils;

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


    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;

    }

    public String getNameByGet(String username) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        String result = HttpUtil.get("http://localhost:8123/api/name", map);
        return result;
    }

    public String getNameByPost(String username) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        String result = HttpUtil.post("http://localhost:8123/api/name", map);
        return result;
    }

    public String getNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post("http://localhost:8123/api/name/json")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        return response.body();
    }

    public HashMap<String, String> getHeaderMap(String body) {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        String nonce = HttpUtil.get("http://localhost:8123/api/name/nonce");
        headerMap.put("nonce", nonce);
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        headerMap.put("sign", SignUtils.getSign(headerMap, secretKey, body));
        return headerMap;
    }



    public static void main(String[] args) {
        ApiClient client = new ApiClient("123", "");
        User user = new User();
        user.setUsername("测试222");
        System.out.println(client.getNameByPost(user));
    }
}
