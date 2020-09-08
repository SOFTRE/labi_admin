package com.labi.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云配置
 *
 * @author lyr
 * @date 2018年5月17日 09:27:52
 */
@Configuration
@ConfigurationProperties(prefix = CloudProperties.CLOUD_PREFIX)
public class CloudProperties {

    public static final String CLOUD_PREFIX = "cloud";

    private String appId;
    private String secretId;
    private String secretKey;
    //file文件存储bucket
    private String bucketFile;
    private String bucketFileUrl;
    private String bucketFileRegion;

    //img上传存储bucket
    private String bucketImg;
    private String bucketImgUrl;
    private String bucketImgRegion;

    //video上传存储bucket
    private String bucketVideo;
    private String bucketVideoUrl;
    private String bucketVideoRegion;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketFile() {
        return bucketFile;
    }

    public void setBucketFile(String bucketFile) {
        this.bucketFile = bucketFile;
    }

    public String getBucketFileUrl() {
        return bucketFileUrl;
    }

    public void setBucketFileUrl(String bucketFileUrl) {
        this.bucketFileUrl = bucketFileUrl;
    }

    public String getBucketFileRegion() {
        return bucketFileRegion;
    }

    public void setBucketFileRegion(String bucketFileRegion) {
        this.bucketFileRegion = bucketFileRegion;
    }

    public String getBucketImg() {
        return bucketImg;
    }

    public void setBucketImg(String bucketImg) {
        this.bucketImg = bucketImg;
    }

    public String getBucketImgUrl() {
        return bucketImgUrl;
    }

    public void setBucketImgUrl(String bucketImgUrl) {
        this.bucketImgUrl = bucketImgUrl;
    }

    public String getBucketImgRegion() {
        return bucketImgRegion;
    }

    public void setBucketImgRegion(String bucketImgRegion) {
        this.bucketImgRegion = bucketImgRegion;
    }

    public String getBucketVideo() {
        return bucketVideo;
    }

    public void setBucketVideo(String bucketVideo) {
        this.bucketVideo = bucketVideo;
    }

    public String getBucketVideoUrl() {
        return bucketVideoUrl;
    }

    public void setBucketVideoUrl(String bucketVideoUrl) {
        this.bucketVideoUrl = bucketVideoUrl;
    }

    public String getBucketVideoRegion() {
        return bucketVideoRegion;
    }

    public void setBucketVideoRegion(String bucketVideoRegion) {
        this.bucketVideoRegion = bucketVideoRegion;
    }
}
