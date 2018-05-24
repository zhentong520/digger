package com.ko30.common.util;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;

/**
 * 上传控制器
 * @author lmk
 *
 */
@SuppressWarnings("all")
public class FileUpload {
	
	public static final String IMAGE = "image";
	public static final String FLASH = "flash";
	public static final String MEDIA = "media";
	public static final String FIlE = "file";
	
	private static final JSONObject objectMapper = new JSONObject();

	private static Logger logger=Logger.getLogger(FileUpload.class);
	/**
	 * 添加、修改上传
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String file_upload(HttpServletRequest request,String identifier) throws Exception {
		String url = "";
		ServletContext application = request.getSession().getServletContext();
		//读取imgPath配置文件
		ResourceBundle rb = ResourceBundle.getBundle ( "imgPath" , Locale.getDefault ());
//		String savePath = application.getRealPath("/") + "upload/";
		String savePath = rb.getString( "imgRealPath" ); //硬盘存放路径
		// 文件保存目录URL
//		String saveUrl = request.getContextPath() + "/upload/";
		String saveUrl = rb.getString( "imgPath" ); //相对路径

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		HashMap<String, String> map = new HashMap<String, String>();
		map = getType(identifier);
		extMap.putAll(map);

		// 最大文件大小
		long maxSize = 20971520;

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new RuntimeException("请选择文件。");
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			throw new RuntimeException("上传目录不存在。");
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			throw new RuntimeException("上传目录没有写权限。");
		}

		String dirName = null;
		
		if (IMAGE.equals(identifier)) {
			dirName = "image";
		}else if(FLASH.equals(identifier)){
			dirName = "flash";
		}else if(MEDIA.equals(identifier)){
			dirName = "media";
		}else if(FIlE.equals(identifier)){
			dirName = "file";
		}
		if (!extMap.containsKey(dirName)) {
			throw new RuntimeException("目录名不正确。");
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> ite = multiRequest.getFileNames();
			while (ite.hasNext()) {
				MultipartFile file = multiRequest.getFile(ite.next());
				if (file != null) {
					String fileName = file.getOriginalFilename();// 获取上传文件名
					// 检查文件大小
					if (file.getSize() > maxSize) {
						throw new RuntimeException("上传文件大小超过限制。");
					}
					//检查扩展名
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.<String> asList(extMap.get(dirName).split(","))
							.contains(fileExt)) {
						throw new RuntimeException("上传文件扩展名是不允许的扩展名。\n只允许"
										+ extMap.get(dirName) + "格式。");
					}
					
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_"
							+ new Random().nextInt(1000) + "." + fileExt;
					try {
						File uploadedFile = new File(savePath, newFileName);
						file.transferTo(uploadedFile);// 将上传文件写到服务器上指定的文件夹
					} catch (Exception e) {
						throw new RuntimeException("上传文件失败。");
					}
					Map<String, Object> msg = new HashMap<String, Object>();
					msg.put("error", 0);
					msg.put("url", saveUrl + newFileName);
					url = saveUrl + newFileName;
					logger.debug(objectMapper.toJSONString(msg));
				}
			}
		}
		return url;
	}

	private static HashMap<String, String> getType(String identifier) {
		HashMap<String, String> extMap = new HashMap<String, String>();
		ResourceBundle rb = ResourceBundle.getBundle("imgPath",
				Locale.getDefault());
		// String savePath = application.getRealPath("/") + "upload/";
		String savePath = rb.getString("imgRealPath"); // 硬盘存放路径
		if (identifier.equals(IMAGE)) {
			extMap.put(IMAGE, rb.getString(IMAGE));
		} else if (identifier.equals(FLASH)) {
			extMap.put(FLASH, rb.getString(FLASH));
		} else if (identifier.equals(MEDIA)) {
			extMap.put(MEDIA, rb.getString(MEDIA));
		} else if (identifier.equals(FIlE)) {
			extMap.put(FIlE, rb.getString(FIlE));
		}
		return extMap;
	}
}
