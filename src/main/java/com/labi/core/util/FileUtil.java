package com.labi.core.util;

import com.alibaba.fastjson.JSONObject;
import com.labi.common.exception.BizExceptionEnum;
import com.labi.common.exception.BussinessException;
import com.labi.config.properties.CloudProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传
 *
 * @author lyr
 * @date 2018年5月16日 17:21:42
 */
public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);
    private static final String ALLOW_SUFFIX = "jpg,png,gif,jpeg,xls,xlsx";// 允许文件格式
    private static final long ALLOW_SIZE = 10L * 1024 * 1024;// 允许文件大小
    private static final long VIDEO_ALLOW_SIZE = 1024 * 1024 * 1024;// 允许文件大小
    private static final String VIDEO_ALLOW_SUFFIX = "flv,swf,mkv,avi,rm,rmvb,mpeg,mpg,ogg,ogv,mov,wmv,mp4,webm,mp3,wav,mid";// 允许文件格式


    /**
     * 功能：重新命名文件
     *
     * @return
     */
    private static String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }

    /**
     * 功能：图片上传
     *
     * @param destDir
     * @throws Exception
     */
    public static String uploadImg(MultipartFile file, String destDir) throws Exception {

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int length = ALLOW_SUFFIX.indexOf(suffix.toLowerCase());
        if (length == -1) {
            throw new Exception("请上传允许格式的文件");
        }
        if (file.getSize() > ALLOW_SIZE) {
            throw new Exception("您上传的文件大小已经超出范围");
        }
        String fileNameNew = getFileNameNew() + "." + suffix;
        COSClient cosClient = COSUtil.initImg();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(file.getSize());
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        objectMetadata.setContentType("text/html; charset=utf-8");
        CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);

        PutObjectRequest putObjectRequest = new PutObjectRequest(cloudProperties.getBucketImg(), destDir + "/" + fileNameNew, file.getInputStream(), objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println("upload file ret:" + putObjectResult);

//		Map<String, Object> map = new HashMap<>();
//		map.put("url", fileNameNew);
//		map.put("size", file.getSize());
        // 关闭客户端
        cosClient.shutdown();
        return destDir + "/" + fileNameNew;
    }

    /**
     * 视频上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static JSONObject uploadVideo(MultipartFile file) throws Exception {
        String destDir = "udeitor";
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int length = VIDEO_ALLOW_SUFFIX.indexOf(suffix.toLowerCase());
        if (length == -1) {
            throw new Exception("请上传允许格式的文件");
        }
        if (file.getSize() > VIDEO_ALLOW_SIZE) {
            throw new Exception("您上传的文件大小已经超出范围");
        }
        String fileNameNew = getFileNameNew() + "." + suffix;
        COSClient cosClient = COSUtil.initVideo();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(file.getSize());
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        objectMetadata.setContentType("video/" + suffix);

        CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);

        PutObjectRequest putObjectRequest = new PutObjectRequest(cloudProperties.getBucketVideo(), destDir + "/" + fileNameNew, file.getInputStream(), objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println("upload file ret:" + putObjectResult);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", destDir + "/" + fileNameNew);
        jsonObject.put("size", file.getSize());
        jsonObject.put("original", fileNameNew);
        jsonObject.put("state", "SUCCESS");
        jsonObject.put("title", fileNameNew);
        jsonObject.put("type", "." + suffix);
        // 关闭客户端
        cosClient.shutdown();
        return jsonObject;
    }

    /**
     * 功能：文件上传
     *
     * @param destDir
     * @throws Exception
     */
    public static String uploadHtml(File file, String destDir) throws Exception {
        if (file.length() > ALLOW_SIZE) {
            throw new Exception("您上传的文件大小已经超出范围");
        }
        String fileNameNew = file.getName();
        COSClient cosClient = COSUtil.initFile();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(file.length());
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        objectMetadata.setContentType("image/jpeg");

        CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
        FileInputStream fileInputStream = new FileInputStream(file);
        PutObjectRequest putObjectRequest = new PutObjectRequest(cloudProperties.getBucketFile(), destDir + "/" + fileNameNew, fileInputStream, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println("upload file ret:" + putObjectResult);
//		Map<String, Object> map = new HashMap<>();
//		map.put("url", fileNameNew);
//		map.put("size", file.getSize());
        // 关闭客户端
        cosClient.shutdown();
        return destDir + "/" + fileNameNew;
    }
    /**
     * 功能：图片上传
     *
     * @param destDir
     * @throws Exception
     */
//	public static String uploadImgByStream(InputStream is, String path, String destDir) throws Exception {
//
//		String suffix = path.substring(path.lastIndexOf(".") + 1);
//		int length = ALLOW_SUFFIX.indexOf(suffix.toLowerCase());
//		if (length == -1) {
//			throw new Exception("请上传允许格式的文件");
//		}
//		String fileNameNew = getFileNameNew() + "." + suffix;
//		COSClient cosClient = COSUtil.initImg();
//		ObjectMetadata objectMetadata = new ObjectMetadata();
//		// 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
//		objectMetadata.setContentLength(file.getSize());
//		// 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
//		objectMetadata.setContentType("image/jpeg");
//
//		CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
//
//		PutObjectRequest putObjectRequest = new PutObjectRequest(cloudProperties.getBucketImg(), destDir+"/"+ fileNameNew, is,objectMetadata);
//		PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
//		System.out.println("upload file ret:" + putObjectResult);
//
////		Map<String, Object> map = new HashMap<>();
////		map.put("url", fileNameNew);
////		map.put("size", file.getSize());
//		// 关闭客户端
//		cosClient.shutdown();
//		return destDir + "/" + fileNameNew;
//	}

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new BussinessException(BizExceptionEnum.FILE_NOT_FOUND);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
            }
        }
    }
}