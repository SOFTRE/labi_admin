package com.labi.core.ueditor.upload;

import com.labi.config.properties.CloudProperties;
import com.labi.core.ueditor.PathFormat;
import com.labi.core.ueditor.define.AppInfo;
import com.labi.core.ueditor.define.BaseState;
import com.labi.core.ueditor.define.FileType;
import com.labi.core.ueditor.define.State;
import com.labi.core.util.COSUtil;

import java.util.Map;

import com.labi.core.util.SpringContextHolder;
import org.apache.commons.codec.binary.Base64;


public final class Base64Uploader {

	public static State save(String content, Map<String, Object> conf) {
		
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		
		savePath = savePath + suffix;
		String physicalPath = (String) conf.get("rootPath") + savePath;

		State storageState = StorageManager.saveBinaryFile(data, physicalPath);

		if (storageState.isSuccess()) {
			String url=PathFormat.format(savePath);
            CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
			storageState.putInfo("url", cloudProperties.getBucketImgUrl() + url.substring(url.lastIndexOf("/")+1, url.length()));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}