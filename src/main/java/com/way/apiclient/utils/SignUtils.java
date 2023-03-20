package com.way.apiclient.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.way.apiclient.model.dto.APIDto;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author Way
 */
public class SignUtils {
    private static final Long DELAY = (long) (1000 * 60 * 5);

    public static String getSign(APIDto dto) {
        String accessKey = dto.getAccessKey();
        String nonce = dto.getNonce();
        String timestamp = dto.getTimestamp();
        String secretKey = dto.getSecretKey();
        if (StringUtils.isAnyBlank(accessKey,nonce,timestamp,secretKey)){
            throw  new RuntimeException("参数错误");
        }

        Digester digester = new Digester(DigestAlgorithm.SHA256);

        //判断时间是否超时
        long time = Long.parseLong(timestamp);
        if (System.currentTimeMillis() - time > DELAY) {
            throw new RuntimeException("超时");
        }

        String s = digester.digestHex(accessKey + timestamp +secretKey +nonce);

        return s;
    }
    public static String getSign(HashMap<String, String> map, String appSecret, Object body) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        return digester.digestHex(map.toString() + "." + appSecret + "." + body);
    }
}
