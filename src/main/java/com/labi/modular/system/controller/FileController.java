package com.labi.modular.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.labi.config.properties.CloudProperties;
import com.labi.core.util.COSUtil;
import com.labi.core.util.FileUtil;
import com.labi.core.util.SpringContextHolder;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件上传
 */
@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 上传图片
     *
     * @param file
     * @param destDir
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/uploadimg/{destDir}", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile file, @PathVariable String destDir) throws Exception {
        return FileUtil.uploadImg(file, destDir);
    }

    /**
     * 上传视频
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/uploadvideo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadVideo(@RequestPart("file") MultipartFile file) throws Exception {
        return FileUtil.uploadVideo(file);
    }

    @RequestMapping("/toUpload.do")
    public String toUpload() {
        return "upload";
    }

    @RequestMapping("/download/{fileName}")
    public String download(@PathVariable("fileName") String fileName, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + fileName);
        try {
//        	OSSOperate oss=new OSSOperate();
//            InputStream inputStream = oss.getObject("kunlun-game", fileName);

            String realPath = request.getSession().getServletContext().getRealPath("/");
            String localPathDown = realPath + "/tmpFile/" + fileName;
            File downFile = new File(localPathDown);
            COSClient cosClient = COSUtil.initFile();
            CloudProperties cloudProperties = SpringContextHolder.getBean(CloudProperties.class);
            GetObjectRequest getObjectRequest = new GetObjectRequest(cloudProperties.getBucketFile(), fileName);
            ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);

            OutputStream os = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(downFile);//
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();

            deleteFile(localPathDown);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  返回值要注意，要不然就出现下面这句错误！
        //java+getOutputStream() has already been called for this response
        return null;
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

}