package com.way.apiclient.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.HashMap;

/**
 * @author Way
 */
public class SignUtils {
    private static final Long DELAY = (long) (1000 * 60 * 5);

    public static String getSign(HashMap<String, String> map, String secretKey, Object body) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        return digester.digestHex(map.toString() + "." + secretKey + "." + body);
    }
}
