package com.labi.core.util;

import com.labi.config.properties.CloudProperties;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 * @author lyr
 * @date 2018/5/18 0018
 */
public class CloudSignatureUtil {

    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String CONTENT_CHARSET = "UTF-8";

    public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }

    public static String getUploadSignature() throws Exception {
        CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
        String strSign = "";
        String contextStr = "";
        long currentTime = System.currentTimeMillis() / 1000;
        long signValidDuration =  3600 * 24 * 2;
        long endTime = (currentTime + signValidDuration);
        Integer random = new Random().nextInt(java.lang.Integer.MAX_VALUE);
        contextStr += "secretId=" + java.net.URLEncoder.encode(cloudProperties.getSecretId(), "utf8");
        contextStr += "&currentTimeStamp=" + currentTime;
        contextStr += "&expireTime=" + endTime;
        contextStr += "&random=" + random;

        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(cloudProperties.getSecretKey().getBytes(CONTENT_CHARSET), mac.getAlgorithm());
            mac.init(secretKey);

            byte[] hash = mac.doFinal(contextStr.getBytes(CONTENT_CHARSET));
            byte[] sigBuf = byteMerger(hash, contextStr.getBytes("utf8"));
            strSign = new String(new BASE64Encoder().encode(sigBuf).getBytes());
            strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
        } catch (Exception e) {
            throw e;
        }
        return strSign;
    }
}
