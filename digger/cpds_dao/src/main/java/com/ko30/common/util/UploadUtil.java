package com.ko30.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import org.apache.commons.io.FileUtils;

public class UploadUtil {
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");



	

	/**
	 * 上传
	 * 
	 * @param file
	 *            上传文件
	 * @param fileName
	 *            文件名称
	 * @param uploadUrl
	 *            文件地址
	 * @param xianzhi
	 *            上传限制 比如.jpg .png 数组 可为空
	 */
	public static void fileUpLoad(File file, String fileName,
			String targetDirectory, String[] uploadType) {
		if (fileName == null) {
			throw new RuntimeException("未获取到文件");
		}
	
		boolean result = false;
		// 判断上传文件类型是否合法
		for (String str : uploadType) {

			if (fileName.trim().toLowerCase().endsWith(str.toLowerCase())) {
				result = true;
			}
		}
		if (!result) {
			throw new RuntimeException("文件格式错误");
		}
		// 判断有没有该路径 没有则创建
		
		File savefile = new File(new File(targetDirectory), fileName);
		if (!savefile.getParentFile().exists())
			savefile.getParentFile().mkdirs();
		if(	 Long.valueOf(bundle.getString("uploadFileMaxSize"))!=null&& Long.valueOf(bundle.getString("uploadFileMaxSize"))>0){
			if(	file.length()> Long.valueOf(bundle.getString("uploadFileMaxSize"))){
				throw new RuntimeException("文件大小超过上传限制");
			}
		}
		try {
			 Long.valueOf(bundle.getString("uploadFileMaxSize"));
			FileUtils.copyFile(file, savefile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("上传文件出错 error:"+e.getMessage());

		}

	}
	
		
	public static  String[] getUploadFileExts(){
		if(bundle.getString("uploadFileExts")!=null&&!bundle.getString("uploadFileExts").equals("")){
			return bundle.getString("uploadFileExts").split(",");
		}
		return new String[]{"txt","rar","zip","doc","docx","xls","xlsx","jpg","jpeg","gif","png","swf","wmv","avi","wma","mp3","mid","apk"};
		
	}
	
}
