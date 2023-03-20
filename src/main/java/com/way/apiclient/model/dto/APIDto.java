package com.way.apiclient.model.dto;

import lombok.Data;

/**
 * @author Way
 */
@Data
public class APIDto {

    private String  accessKey;
    private String  secretKey;
    private String  nonce;
    private String  timestamp;
    private String  sign;
    private Object  data;
}
