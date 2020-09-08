package com.labi.core.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class HtmlUtils {

	public static int  upload(String content,String title, String destDir,HttpServletRequest request) throws Exception{
		StringBuilder sb=new StringBuilder("<html><head><meta http-equiv='content-type' content='text/html; charset=utf-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><style type='text/css'>img{width:100%;}table{border-collapse:collapse;td}td{border: 1px solid #000;}</style></head><body>");
		sb.append(content);
		sb.append("</body></html>");
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String html=new String(sb.toString().getBytes("utf-8"),"utf-8");
			FileOutputStream fos = new FileOutputStream(realPath+"/htmlFile/"+title+".html");
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(html);
			out.close();
			fos.close();
			FileUtil.uploadHtml(new File(realPath+"/htmlFile/"+title+".html"),destDir);
	        deleteFile(realPath+"/htmlFile/"+title+".html");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
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