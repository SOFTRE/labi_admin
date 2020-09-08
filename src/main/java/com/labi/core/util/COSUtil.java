package com.labi.core.util;

import com.labi.config.properties.CloudProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

/**
 * 腾讯云COS工具类
 * @author lyr
 * @date 2018年5月16日 17:21:42
 */
public class COSUtil {

	/**
	 * 初始化文件上传客户端
	 * @return
	 */
	public static COSClient initFile(){

		CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
		// 1 初始化用户身份信息(secretId, secretKey)
		COSCredentials cred = new BasicCOSCredentials(cloudProperties.getSecretId(), cloudProperties.getSecretKey());
		// 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
		ClientConfig clientConfig = new ClientConfig(new Region(cloudProperties.getBucketFileRegion()));
		// 3 生成cos客户端
		return new COSClient(cred, clientConfig);
	}

	/**
	 * 初始化图片上传客户端
	 * @return
	 */
	public static COSClient initImg(){
		CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
		// 1 初始化用户身份信息(secretId, secretKey)
		COSCredentials cred = new BasicCOSCredentials(cloudProperties.getSecretId(), cloudProperties.getSecretKey());
		// 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
		ClientConfig clientConfig = new ClientConfig(new Region(cloudProperties.getBucketImgRegion()));
		// 3 生成cos客户端
		return new COSClient(cred, clientConfig);
	}

	/**
	 * 初始化视频上传客户端
	 * @return
	 */
	public static COSClient initVideo(){
		CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
		// 1 初始化用户身份信息(secretId, secretKey)
		COSCredentials cred = new BasicCOSCredentials(cloudProperties.getSecretId(), cloudProperties.getSecretKey());
		// 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
		ClientConfig clientConfig = new ClientConfig(new Region(cloudProperties.getBucketVideoRegion()));
		// 3 生成cos客户端
		return new COSClient(cred, clientConfig);
	}
}