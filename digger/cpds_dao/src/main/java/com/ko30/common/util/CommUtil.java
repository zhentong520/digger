package com.ko30.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.safety.Whitelist;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CommUtil {

	// 初始化fastjson配置，为提高性能
	public static ParserConfig pc = new ParserConfig();
	public static SerializeConfig sc = new SerializeConfig();
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final Whitelist user_content_filter = Whitelist.relaxed();
	static int totalFolder = 0;
	static int totalFile = 0;
	static int pageSize = 12;

	static {
		user_content_filter.addTags(new String[] { "embed", "object", "param",
				"span", "div", "font" });
		user_content_filter.addAttributes(":all", new String[] { "style",
				"class", "id", "name" });
		user_content_filter.addAttributes("object", new String[] { "width",
				"height", "classid", "codebase" });
		user_content_filter.addAttributes("param", new String[] { "name",
				"value" });
		user_content_filter.addAttributes("embed",
				new String[] { "src", "quality", "width", "height",
						"allowFullScreen", "allowScriptAccess", "flashvars",
						"name", "type", "pluginspage" });
	}

	private static CommUtil instance = new CommUtil();

	public static CommUtil getInstance() {
		return instance;
	}

	public static String first2low(String str) {
		String s = "";
		s = str.substring(0, 1).toLowerCase() + str.substring(1);
		return s;
	}

	public static String first2upper(String str) {
		String s = "";
		s = str.substring(0, 1).toUpperCase() + str.substring(1);
		return s;
	}

	public static List<String> str2list(String s) throws IOException {
		List list = new ArrayList();
		if ((s != null) && (!s.equals(""))) {
			StringReader fr = new StringReader(s);
			BufferedReader br = new BufferedReader(fr);
			String aline = "";
			while ((aline = br.readLine()) != null) {
				list.add(aline);
			}
		}
		return list;
	}

	public static Date formatDate(String s) {
		Date d = null;
		try {
			d = dateFormat.parse(s);
		} catch (Exception localException) {
		}
		return d;
	}

	public static Date formatDatePlus(String s) {
		Date date = formatDate(s);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 功能描述：返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 功能描述：日期相加
	 * 
	 * @param date
	 *            Date 日期
	 * @param day
	 *            int 天数
	 * @return 返回相加后的日期/
	 */
	public static Date addDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static Date formatDatePlus1(String s) {
		return formatDate(s + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date formatDatePlus2(String s) {
		return formatDate(s + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date formatDate(String s, String format) {
		Date d = null;
		try {
			SimpleDateFormat dFormat = new SimpleDateFormat(format);
			d = dFormat.parse(s);
		} catch (Exception localException) {
		}
		return d;
	}

	public static String formatTime(String format, Object v) {
		if (v == null)
			return null;
		if (v.equals(""))
			return "";
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(v);
	}

	public static String formatLongDate(Object v) {
		if ((v == null) || (v.equals("")))
			return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(v);
	}

	public static String formatShortDate(Object v) {
		if (v == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(v);
	}

	public static String formatMonthDate(Object v) {
		if (v == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		return df.format(v);
	}

	public static String decode(String s) {
		String ret = s;
		try {
			ret = URLDecoder.decode(s.trim(), "UTF-8");
		} catch (Exception localException) {
		}
		return ret;
	}

	public static String encode(String s) {
		String ret = s;
		try {
			ret = URLEncoder.encode(s.trim(), "UTF-8");
		} catch (Exception localException) {
		}
		return ret;
	}

	public static String convert(String str, String coding) {
		String newStr = "";
		if (str != null)
			try {
				newStr = new String(str.getBytes("ISO-8859-1"), coding);
			} catch (Exception e) {
				return newStr;
			}
		return newStr;
	}

	public static boolean isImg(String extend) {
		boolean ret = false;
		List<String> list = new ArrayList();
		list.add("jpg");
		list.add("jpeg");
		list.add("bmp");
		list.add("gif");
		list.add("png");
		list.add("tif");
		for (String s : list) {
			if (s.equals(extend))
				ret = true;
		}
		return ret;
	}

	public static final void waterMarkWithImage(String pressImg,
			String targetImg, int pos, float alpha) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, 1);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);

			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			g.setComposite(AlphaComposite.getInstance(10, alpha / 100.0F));
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			int x = 0;
			int y = 0;

			if (pos == 2) {
				x = (width - width_biao) / 2;
				y = 0;
			}
			if (pos == 3) {
				x = width - width_biao;
				y = 0;
			}
			if (pos == 4) {
				x = width - width_biao;
				y = (height - height_biao) / 2;
			}
			if (pos == 5) {
				x = width - width_biao;
				y = height - height_biao;
			}
			if (pos == 6) {
				x = (width - width_biao) / 2;
				y = height - height_biao;
			}
			if (pos == 7) {
				x = 0;
				y = height - height_biao;
			}
			if (pos == 8) {
				x = 0;
				y = (height - height_biao) / 2;
			}
			if (pos == 9) {
				x = (width - width_biao) / 2;
				y = (height - height_biao) / 2;
			}
			g.drawImage(src_biao, x, y, width_biao, height_biao, null);

			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean waterMarkWithText(String filePath, String outPath,
			String text, String markContentColor, Font font, int pos,
			float qualNum) {
		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height, 1);
		Graphics2D g = bimage.createGraphics();
		if (font == null) {
			font = new Font("黑体", 1, 30);
			g.setFont(font);
		} else {
			g.setFont(font);
		}
		g.setColor(getColor(markContentColor));
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		FontMetrics metrics = new FontMetrics(font) {
		};
		Rectangle2D bounds = metrics.getStringBounds(text, null);
		int widthInPixels = (int) bounds.getWidth();
		int heightInPixels = (int) bounds.getHeight();
		int left = 0;
		int top = heightInPixels;

		if (pos == 2) {
			left = width / 2;
			top = heightInPixels;
		}
		if (pos == 3) {
			left = width - widthInPixels;
			top = heightInPixels;
		}
		if (pos == 4) {
			left = width - widthInPixels;
			top = height / 2;
		}
		if (pos == 5) {
			left = width - widthInPixels;
			top = height - heightInPixels;
		}
		if (pos == 6) {
			left = width / 2;
			top = height - heightInPixels;
		}
		if (pos == 7) {
			left = 0;
			top = height - heightInPixels;
		}
		if (pos == 8) {
			left = 0;
			top = height / 2;
		}
		if (pos == 9) {
			left = width / 2;
			top = height / 2;
		}
		g.drawString(text, left, top);
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(outPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(qualNum, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean createFolder(String folderPath) {
		boolean ret = true;
		try {
			File myFilePath = new File(folderPath);
			if ((!myFilePath.exists()) && (!myFilePath.isDirectory())) {
				ret = myFilePath.mkdirs();
				if (!ret)
					System.out.println("创建文件夹出错");
			}
		} catch (Exception e) {
			System.out.println("创建文件夹出错");
			ret = false;
		}
		return ret;
	}

	public static List toRowChildList(List list, int perNum) {
		List l = new ArrayList();
		if (list == null) {
			return l;
		}

		for (int i = 0; i < list.size(); i += perNum) {
			List cList = new ArrayList();
			for (int j = 0; j < perNum; j++)
				if (i + j < list.size())
					cList.add(list.get(i + j));
			l.add(cList);
		}
		return l;
	}

	public static List copyList(List list, int begin, int end) {
		List l = new ArrayList();
		if (list == null)
			return l;
		if (end > list.size())
			end = list.size();
		for (int i = begin; i < end; i++) {
			l.add(list.get(i));
		}
		return l;
	}

	public static boolean isNotNull(Object obj) {
		if ((obj != null) && (!obj.toString().equals(""))) {
			return true;
		}
		return false;
	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];

				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错 ");
			e.printStackTrace();
		}
	}

	public static boolean deleteFolder(String path) {
		boolean flag = false;
		File file = new File(path);

		if (!file.exists()) {
			return flag;
		}

		if (file.isFile()) {
			return deleteFile(path);
		}
		return deleteDirectory(path);
	}

	public static boolean deleteFile(String path) {
		boolean flag = false;
		File file = new File(path);

		if ((file.isFile()) && (file.exists())) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	public static boolean deleteDirectory(String path) {
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File dirFile = new File(path);

		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		boolean flag = true;

		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		if (dirFile.delete()) {
			return true;
		}
		return false;
	}

	public static String showPageStaticHtml(String url, int currentPage,
			int pages) {
		String s = "";
		if (pages > 0) {
			if (currentPage >= 1) {
				s = s + "<a href='" + url + "_1.htm'>首页</a> ";
				if (currentPage > 1) {
					s = s + "<a href='" + url + "_" + (currentPage - 1)
							+ ".htm'>上一页</a> ";
				}
			}
			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
				s = s + "第　";
				int i = beginPage;
				for (int j = 0; (i <= pages) && (j < 6); j++) {
					if (i == currentPage)
						s = s + "<a class='this' href='" + url + "_" + i
								+ ".htm'>" + i + "</a> ";
					else
						s = s + "<a href='" + url + "_" + i + ".htm'>" + i
								+ "</a> ";
					i++;
				}

				s = s + "页　";
			}
			if (currentPage <= pages) {
				if (currentPage < pages) {
					s = s + "<a href='" + url + "_" + (currentPage + 1)
							+ ".htm'>下一页</a> ";
				}
				s = s + "<a href='" + url + "_" + pages + ".htm'>末页</a> ";
			}
		}
		return s;
	}

	public static String showPageHtml(String url, String params,
			int currentPage, int pages) {
		String s = "";
		if (pages > 0) {
			if (currentPage >= 1) {
				s = s + "<a href='" + url + "?currentPage=1" + params
						+ "'>首页</a> ";
				if (currentPage > 1) {
					s = s + "<a href='" + url + "?currentPage="
							+ (currentPage - 1) + params + "'>上一页</a> ";
				}
			}
			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
				s = s + "第　";
				int i = beginPage;
				for (int j = 0; (i <= pages) && (j < 6); j++) {
					if (i == currentPage)
						s = s + "<a class='this' href='" + url
								+ "?currentPage=" + i + params + "'>" + i
								+ "</a> ";
					else
						s = s + "<a href='" + url + "?currentPage=" + i
								+ params + "'>" + i + "</a> ";
					i++;
				}

				s = s + "页　";
			}
			if (currentPage <= pages) {
				if (currentPage < pages) {
					s = s + "<a href='" + url + "?currentPage="
							+ (currentPage + 1) + params + "'>下一页</a> ";
				}
				s = s + "<a href='" + url + "?currentPage=" + pages + params
						+ "'>末页</a> ";
			}
		}

		return s;
	}

	public static String showPageFormHtml(int currentPage, int pages) {
		String s = "";
		if (pages > 0) {
			if (currentPage >= 1) {
				s = s
						+ "<a href='javascript:void(0);' onclick='return gotoPage(1)'>首页</a> ";
				if (currentPage > 1) {
					s = s
							+ "<a href='javascript:void(0);' onclick='return gotoPage("
							+ (currentPage - 1) + ")'>上一页</a> ";
				}
			}
			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
				s = s + "第　";
				int i = beginPage;
				for (int j = 0; (i <= pages) && (j < 6); j++) {
					if (i == currentPage)
						s = s
								+ "<a class='this' href='javascript:void(0);' onclick='return gotoPage("
								+ i + ")'>" + i + "</a> ";
					else
						s = s
								+ "<a href='javascript:void(0);' onclick='return gotoPage("
								+ i + ")'>" + i + "</a> ";
					i++;
				}

				s = s + "页　";
			}
			if (currentPage <= pages) {
				if (currentPage < pages) {
					s = s
							+ "<a href='javascript:void(0);' onclick='return gotoPage("
							+ (currentPage + 1) + ")'>下一页</a> ";
				}
				s = s
						+ "<a href='javascript:void(0);' onclick='return gotoPage("
						+ pages + ")'>末页</a> ";
			}
		}

		return s;
	}

	public static String showPageAjaxHtml(String url, String params,
			int currentPage, int pages) {
		String s = "";
		if (pages > 0) {
			String address = url + "?1=1" + params;
			if (currentPage >= 1) {
				s = s
						+ "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address + "\",1,this)'>首页</a> ";
				s = s
						+ "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address + "\"," + (currentPage - 1)
						+ ",this)'>上一页</a> ";
			}

			int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
			if (beginPage <= pages) {
				s = s + "第　";
				int i = beginPage;
				for (int j = 0; (i <= pages) && (j < 6); j++) {
					if (i == currentPage)
						s = s
								+ "<a class='this' href='javascript:void(0);' onclick='return ajaxPage(\""
								+ address + "\"," + i + ",this)'>" + i
								+ "</a> ";
					else
						s = s
								+ "<a href='javascript:void(0);' onclick='return ajaxPage(\""
								+ address + "\"," + i + ",this)'>" + i
								+ "</a> ";
					i++;
				}

				s = s + "页　";
			}
			if (currentPage <= pages) {
				s = s
						+ "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address + "\"," + (currentPage + 1)
						+ ",this)'>下一页</a> ";
				s = s
						+ "<a href='javascript:void(0);' onclick='return ajaxPage(\""
						+ address + "\"," + pages + ",this)'>末页</a> ";
			}
		}

		return s;
	}

	public static char randomChar() {
		char[] chars = { 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f',
				'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l',
				'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r',
				'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x',
				'X', 'y', 'Y', 'z', 'Z' };
		int index = (int) (Math.random() * 52.0D) - 1;
		if (index < 0) {
			index = 0;
		}
		return chars[index];
	}

	public static String[] splitByChar(String s, String c) {
		String[] list = s.split(c);
		return list;
	}

	public static Object requestByParam(HttpServletRequest request, String param) {
		if (!request.getParameter(param).equals("")) {
			return request.getParameter(param);
		}
		return null;
	}

	public static String substring(String s, int maxLength) {
		if (!StringUtils.isBlank(s))
			return s;
		if (s.length() <= maxLength) {
			return s;
		}
		return s.substring(0, maxLength) + "...";
	}

	public static String substringfrom(String s, String from) {
		if (s.indexOf(from) < 0)
			return "";
		return s.substring(s.indexOf(from) + from.length());
	}

	public static int null2Int(Object s) {
		int v = 0;
		if (s != null)
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	public static float null2Float(Object s) {
		float v = 0.0F;
		if (s != null)
			try {
				v = Float.parseFloat(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	public static double null2Double(Object s) {
		double v = 0.0D;
		if (s != null)
			try {
				v = Double.parseDouble(null2String(s));
			} catch (Exception localException) {
			}
		return v;
	}

	public static boolean null2Boolean(Object s) {
		boolean v = false;
		if (s != null)
			try {
				v = Boolean.parseBoolean(s.toString());
			} catch (Exception localException) {
			}
		return v;
	}

	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}

	public static Long null2Long(Object s) {
		Long v = Long.valueOf(-1L);
		if (s != null)
			try {
				v = Long.valueOf(Long.parseLong(s.toString()));
			} catch (Exception localException) {
			}
		return v;
	}

	public static String getTimeInfo(long time) {
		int hour = (int) time / 3600000;
		long balance = time - hour * 1000 * 60 * 60;
		int minute = (int) balance / 60000;
		balance -= minute * 1000 * 60;
		int seconds = (int) balance / 1000;
		String ret = "";
		if (hour > 0)
			ret = ret + hour + "小时";
		if (minute > 0)
			ret = ret + minute + "分";
		else if ((minute <= 0) && (seconds > 0))
			ret = ret + "零";
		if (seconds > 0)
			ret = ret + seconds + "秒";
		return ret;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("x-real-ip");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getIp(HttpServletRequest request) {
		try {
			if (request != null) {
				return CommUtil.getIpAddr(request);
			}
		} catch (Exception e) {
			// ignore 如unit test
		}

		return "unknown";
	}

	/*
	public static String getArea(HttpServletRequest request) {
		String current_city = "";
		String current_ip = CommUtil.getInstance().getIp(request);
		try {
			current_city = ip2addr(current_ip);
			// current_city =
			// IPSeeker.getInstance().getIPLocation(current_ip).getCountry();
		} catch (Exception e) {
		}
		return current_city;
	}
	*/

	public static int indexOf(String s, String sub) {
		return s.trim().indexOf(sub.trim());
	}

	public static Map cal_time_space(Date begin, Date end) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l = end.getTime() - begin.getTime();
		long day = l / 86400000L;
		long hour = l / 3600000L - day * 24L;
		long min = l / 60000L - day * 24L * 60L - hour * 60L;
		long second = l / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L
				- min * 60L;
		Map map = new HashMap();
		map.put("day", Long.valueOf(day));
		map.put("hour", Long.valueOf(hour));
		map.put("min", Long.valueOf(min));
		map.put("second", Long.valueOf(second));
		return map;
	}

	public static final String randomString(int length) {
		char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();
		if (length < 1) {
			return "";
		}
		Random randGen = new Random();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = "0123456789".toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	public static long getDateDistance(String time1, String time2) {
		long quot = 0L;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000L / 60L / 60L / 24L;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	public static double div(Object a, Object b) {
		double ret = 0.0D;
		if ((!null2String(a).equals("")) && (!null2String(b).equals(""))) {
			BigDecimal e = new BigDecimal(null2String(a));
			BigDecimal f = new BigDecimal(null2String(b));
			if (null2Double(f) > 0.0D)
				ret = e.divide(f, 3, 1).doubleValue();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double subtract(Object a, Object b) {
		double ret = 0.0D;
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		ret = e.subtract(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double subtract(Object a, Object b, Object c, Object d) {
		double ret = 0.0D;
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		BigDecimal g = new BigDecimal(null2Double(c));
		BigDecimal h = new BigDecimal(null2Double(d));
		ret = e.subtract(f).subtract(g).subtract(h).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double add(Object a, Object b) {
		double ret = 0.0D;
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		ret = e.add(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double mul(Object a, Object b) {
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		double ret = e.multiply(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double formatMoney(Object money) throws Exception {
		try {
			if (money == null) {
				return 0.00d;
			}
			DecimalFormat df = new DecimalFormat("0.00");
			return Double.valueOf(df.format(money)).doubleValue();
		} catch (Exception e) {
			System.out.println("formatMoney error : " + money);
			e.printStackTrace();
			throw e;
		}
	}

	public static int M2byte(float m) {
		float a = m * 1024.0F * 1024.0F;
		return (int) a;
	}

	public static boolean convertIntToBoolean(int intValue) {
		return intValue != 0;
	}

	public static String getURL(HttpServletRequest request) {
		String contextPath = request.getContextPath().equals("/") ? ""
				: request.getContextPath();
		String url = "http://" + request.getServerName();
		if (null2Int(Integer.valueOf(request.getServerPort())) != 80)
			url = url + ":"
					+ null2Int(Integer.valueOf(request.getServerPort()))
					+ contextPath;
		else {
			url = url + contextPath;
		}
		return url;
	}

	public static String filterHTML(String content) {
		Whitelist whiteList = new Whitelist();
		String s = Jsoup.clean(content, user_content_filter);
		return s;
	}

	public static int parseDate(String type, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type.equals("y")) {
			return cal.get(1);
		}
		if (type.equals("M")) {
			return cal.get(2) + 1;
		}
		if (type.equals("d")) {
			return cal.get(5);
		}
		if (type.equals("H")) {
			return cal.get(11);
		}
		if (type.equals("m")) {
			return cal.get(12);
		}
		if (type.equals("s")) {
			return cal.get(13);
		}
		return 0;
	}

	public static int[] readImgWH(String imgurl) {
		boolean b = false;
		try {
			URL url = new URL(imgurl);

			BufferedInputStream bis = new BufferedInputStream(url.openStream());

			byte[] bytes = new byte[100];

			OutputStream bos = new FileOutputStream(new File("thetempimg.gif"));
			int len;
			while ((len = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			bis.close();
			bos.flush();
			bos.close();

			b = true;
		} catch (Exception e) {
			b = false;
		}
		int[] a = new int[2];
		if (b) {
			File file = new File("thetempimg.gif");
			BufferedImage bi = null;
			boolean imgwrong = false;
			try {
				bi = ImageIO.read(file);
				try {
					int i = bi.getType();
					imgwrong = true;
				} catch (Exception e) {
					imgwrong = false;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (imgwrong) {
				a[0] = bi.getWidth();
				a[1] = bi.getHeight();
			} else {
				a = null;
			}

			file.delete();
		} else {
			a = null;
		}
		return a;
	}

	public static boolean fileExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static int splitLength(String s, String c) {
		int v = 0;
		if (!s.trim().equals("")) {
			v = s.split(c).length;
		}
		return v;
	}

	public static double fileSize(File folder) {
		totalFolder += 1;

		long foldersize = 0L;
		File[] filelist = folder.listFiles();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isDirectory()) {
				foldersize = (long) (foldersize + fileSize(filelist[i]));
			} else {
				totalFile += 1;
				foldersize += filelist[i].length();
			}
		}
		return div(Long.valueOf(foldersize), Integer.valueOf(1024));
	}

	public static int fileCount(File file) {
		if (file == null) {
			return 0;
		}
		if (!file.isDirectory()) {
			return 1;
		}
		int fileCount = 0;
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				fileCount++;
			} else if (f.isDirectory()) {
				fileCount++;
				fileCount += fileCount(file);
			}
		}
		return fileCount;
	}

	public static String get_all_url(HttpServletRequest request) {
		String query_url = request.getRequestURI();
		if ((request.getQueryString() != null)
				&& (!request.getQueryString().equals(""))) {
			query_url = query_url + "?" + request.getQueryString();
		}
		return query_url;
	}

	public static Color getColor(String color) {
		if (color.charAt(0) == '#') {
			color = color.substring(1);
		}
		if (color.length() != 6)
			return null;
		try {
			int r = Integer.parseInt(color.substring(0, 2), 16);
			int g = Integer.parseInt(color.substring(2, 4), 16);
			int b = Integer.parseInt(color.substring(4), 16);
			return new Color(r, g, b);
		} catch (NumberFormatException nfe) {
		}
		return null;
	}

	public static Set<Integer> randomInt(int a, int length) {
		Set list = new TreeSet();
		int size = length;
		if (length > a) {
			size = a;
		}
		while (list.size() < size) {
			Random random = new Random();
			int b = random.nextInt(a);
			list.add(Integer.valueOf(b));
		}
		return list;
	}

	public static Double formatDouble(Object obj, int len) {
		Double ret = Double.valueOf(0.0D);
		String format = "0.0";
		for (int i = 1; i < len; i++) {
			format = format + "0";
		}
		DecimalFormat df = new DecimalFormat(format);
		return Double.valueOf(df.format(obj));
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
				|| (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
			return true;
		}
		return false;
	}

	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0.0F;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count += 1.0F;
					System.out.print(c);
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4D) {
			return true;
		}
		return false;
	}

	public static String trimSpaces(String IP) {
		while (IP.startsWith(" ")) {
			IP = IP.substring(1, IP.length()).trim();
		}
		while (IP.endsWith(" ")) {
			IP = IP.substring(0, IP.length() - 1).trim();
		}
		return IP;
	}

	public static boolean isIp(String IP) {
		boolean b = false;
		IP = trimSpaces(IP);
		if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String[] s = IP.split("\\.");
			if ((Integer.parseInt(s[0]) < 255)
					&& (Integer.parseInt(s[1]) < 255)
					&& (Integer.parseInt(s[2]) < 255)
					&& (Integer.parseInt(s[3]) < 255))
				b = true;
		}
		return b;
	}

	public static String generic_domain(HttpServletRequest request) {
		String system_domain = "localhost";
		String serverName = request.getServerName();
		if (isIp(serverName))
			system_domain = serverName;
		else {
			system_domain = serverName.substring(serverName.indexOf(".") + 1);
		}

		return system_domain;
	}

	// 隐藏用户名称
	public static String hidename(String name) {
		String part1 = name.substring(0, 3);
		String part2 = "****";
		String part3 = name.substring(7);
		String part = part1.concat(part2).concat(part3);
		return part;
	}

	public static void clearCookie(HttpServletResponse response) {
		// Cookie cookie = new Cookie("JSESSIONID","");
		// cookie.setMaxAge(0);
		// cookie.setPath("/supplier");
		// response.addCookie(cookie);
		//
		// cookie = new Cookie("JSESSIONID","");
		// cookie.setMaxAge(0);
		// cookie.setPath("/supplier/");
		// response.addCookie(cookie);
		// cookie = new Cookie("JSESSIONID","");
		// cookie.setMaxAge(0);
		// cookie.setPath("/user/");
		// response.addCookie(cookie);
		// cookie = new Cookie("JSESSIONID","");
		// cookie.setMaxAge(0);
		// cookie.setPath("/user");
		// response.addCookie(cookie);
		// cookie = new Cookie("JSESSIONID","");
		// cookie.setMaxAge(0);
		// cookie.setPath("//");
		// response.addCookie(cookie);
	}

	/*
	public static String ip2addr(String ip) throws Exception {
		if ("localhost".equals(ip) || "127.0.0.1".equals(ip)
				|| "0:0:0:0:0:0:0:1".equals(ip)) {
			return "局域网";
		}
		boolean flag = true;
		if (flag) {
			NamedCache blockingCache = CacheFactory.getCache("IncludeCache");
			String content = (String) blockingCache.get(ip);
			if (content != null) {
				return content;
			}
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String html = "";

		try {
			HttpGet httpget = null;
			// 创建HttpGet对象
			httpget = new HttpGet(
					"http://api.map.baidu.com/location/ip?ak=Q4haDC6GHi6dkX2SOIIwwqGb&ip="
							+ ip + "&coor=bd09ll");
			// 使用execute方法发送HTTPGET请求，并返回HttpResponse对象
			HttpResponse response = httpclient.execute(httpget);
			// 使用getEntity方法获得返回结果
			HttpEntity entity = response.getEntity();
			// 读取response响应内容
			html = EntityUtils.toString(entity, "GB2312");
			// 关闭底层流
			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.close();
		}
		/**
		 * 利用Parser解析HTML，将标签 <li>下的内容存储到nodeList里，并获取第一个 <li>
		 * 下的内容，用split分割后获取最终的结果是 日本
		 */
	/*
		JSONObject jobj = JSON.parseObject(html);
		String address = jobj.getJSONObject("content").getString("address");
		if (flag) {
			NamedCache blockingCache = CacheFactory.getCache("IncludeCache");
			blockingCache.put(ip, address);
		}
		return address;

	}
	*/
	
	/*
	public static String ip2addr138(String ip) throws Exception {
		if ("localhost".equals(ip) || "127.0.0.1".equals(ip)
				|| "0:0:0:0:0:0:0:1".equals(ip)) {
			return "局域网";
		}
		boolean flag = false;
		if (flag) {
			NamedCache blockingCache = CacheFactory.getCache("IncludeCache");
			String content = (String) blockingCache.get(ip);
			if (content != null) {
				return content;
			}
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String html = "";

		try {
			HttpGet httpget = null;
			// 创建HttpGet对象
			httpget = new HttpGet("http://www.ip138.com/ips138.asp?ip=" + ip
					+ "&action=2");
			// 使用execute方法发送HTTPGET请求，并返回HttpResponse对象
			HttpResponse response = httpclient.execute(httpget);
			// 使用getEntity方法获得返回结果
			HttpEntity entity = response.getEntity();
			// 读取response响应内容
			html = EntityUtils.toString(entity, "GB2312");
			// 关闭底层流
			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.close();
		}
		/**
		 * 利用Parser解析HTML，将标签 <li>下的内容存储到nodeList里，并获取第一个 <li>
		 * 下的内容，用split分割后获取最终的结果是 日本
		 */
	/*
		Parser myParser = Parser.createParser(html, "gb2312");
		NodeFilter filter = new TagNameFilter("li");

		NodeList nodeList = myParser.parse(filter);
		// System.out.println(nodeList);
		String result = nodeList.elementAt(0).toPlainTextString();
		// System.out.println(result);
		String address = result.split("：")[1];
		String[] res = address.split("\\ ");
		if (res != null && res.length > 1) {
			address = res[0];
		}
		if (flag) {
			NamedCache blockingCache = CacheFactory.getCache("IncludeCache");
			blockingCache.put(ip, address);
		}
		return address;

	}
	*/

	public static String getCurrentIp() {
		String getip = "http://members.3322.org/dyndns/getip";
		try {
			URL url = new URL(getip);
			URLConnection con = url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			String type = URLConnection.guessContentTypeFromStream(urlStream);
			String charSet = null;
			if (type == null) {
				type = con.getContentType();
			}
			if ((type == null) || (type.trim().length() == 0)) {
				return "";
			}
			if (type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);
			byte[] b = new byte[10000];
			int numRead = urlStream.read(b);
			String content = new String(b, 0, numRead, charSet);
			while (numRead != -1) {
				numRead = urlStream.read(b);
				if (numRead != -1) {
					String newContent = new String(b, 0, numRead, charSet);
					content = content + newContent;
				}
			}
			// System.out.println(content.trim());
			urlStream.close();
			return content.trim();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取时间段 早上 上午 中午 下午 晚上
	 * 
	 * @return
	 */
	public static String TimeQuantum() {
		String hint = "早上";
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 6 && hour < 8) {
			hint = "早上";
		} else if (hour >= 8 && hour < 11) {
			hint = "上午";
		} else if (hour >= 11 && hour < 13) {
			hint = "中午";
		} else if (hour >= 13 && hour < 18) {
			hint = "下午";
		} else {
			hint = "晚上";
		}
		return hint;
	}

	/**
	 * 获取本周1的日期
	 * 
	 * @return
	 */
	public static Date getNowWeekBegin() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		return monday;
	}

	/**
	 * 获取本月1号的日期
	 * 
	 * @return
	 */
	public static Date getNowMonthBegin() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		int dayOfMonth = cd.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfMonth;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		return monday;
	}

	/**
	 * 获取7天
	 * 
	 * @return
	 */
	public static Date getSevenDays() {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - 7);
		return cal.getTime();
	}

	/**
	 * 获取计算量的时间
	 * 
	 * @return
	 */
	public static Date getMothDay(int add) {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear + add);
		return cal.getTime();
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return 返回值为：1 前面时间大于后面时间 -1 后面时间大于等于前面时间 0 前后时间相等
	 */
	public static String compareDate(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date dt1 = df.parse(str1);
			Date dt2 = df.parse(str2);
			if (dt1.getTime() > dt2.getTime()) {
				return "1";
			} else if (dt1.getTime() < dt2.getTime()) {
				return "-1";
			} else {
				return "0";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx时xx分xx秒
	 */
	public static String getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		String returnStr = "";
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = 0;
			}
			returnStr = returnStr
					+ String.format("%02d", diff / (24 * 60 * 60 * 1000))
					+ "天\n";
			returnStr = returnStr
					+ String.format("%02d",
							(diff / (60 * 60 * 1000) - day * 24)) + "时\n";
			returnStr = returnStr
					+ String.format("%02d", ((diff / (60 * 1000)) - day * 24
							* 60 - hour * 60)) + "分\n";
			returnStr = returnStr
					+ String.format("%02d", (diff / 1000 - day * 24 * 60 * 60
							- hour * 60 * 60 - min * 60)) + "秒";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	/**
	 * @param date1
	 *            需要比较的时间 不能为空(null),需要正确的日期格式
	 * @param date2
	 *            被比较的时间 为空(null)则为当前时间
	 * @param stype
	 *            返回值类型 0为多少天，1为多少个月，2为多少年
	 * @return
	 */
	public static int compareDate(String date1, String date2, int stype) {
		int n = 0;
		// String[] u = {"天","月","年"};
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

		date2 = StringUtil.isBlank(date2) ? getCurrentDate() : date2;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1));
			c2.setTime(df.parse(date2));
		} catch (Exception e3) {
			System.out.println("wrong occured");
		}
		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}

		n = n - 1;

		if (stype == 2) {
			n = (int) n / 365;
		}
		return n;
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		return simple.format(date);
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate(String format) {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat simple = new SimpleDateFormat(format);
		return simple.format(date);
	}

	/**
	 * 获取当前星期
	 * 
	 * @return
	 */
	public static String getWeek() {
		Calendar cal = Calendar.getInstance();
		int val = cal.get(Calendar.DAY_OF_WEEK) - 1;
		switch (val) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		default:
			return "";
		}
	}

	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(((Number) value).doubleValue());
			} else {
				throw new ClassCastException("Not possible to coerce [" + value
						+ "] from class " + value.getClass()
						+ " into a BigDecimal.");
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		DateFormat df = DateFormat.getDateInstance();
		Date d = CommUtil.getNowWeekBegin();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(CommUtil.formatLongDate(new Date()));

		Date d1 = CommUtil.getMothDay(-1);
		System.out.println(simple.format(d1));
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPageSize(int pageSize) {
		CommUtil.pageSize = pageSize;
	}

	public static <T> T getDefault(T value, T defaultValue) {
		if (null == value) {
			value = defaultValue;
		} else if (value instanceof String) {
			String str = (String) value;
			if ("".equals(str.trim())) {
				value = defaultValue;
			}
		}

		return value;
	}

	/**
	 * 
	 * @Title: format2Decimal
	 * @Description: 格式化两位数
	 * @param @param amount
	 * @param @return 设定文件
	 * @return BigDecimal 返回类型
	 * @date 2016年6月23日 下午8:25:57
	 * @throws
	 */
	public static BigDecimal format2Decimal(BigDecimal amount) {
		return new BigDecimal(new DecimalFormat("0.00").format(amount));
	}

	public static <T> T sqlResultToClass(Class<T> type, Object[] tuple,
			LinkedHashMap<String, Class<?>> colums) {
		try {
			Constructor<T> ctor = type.getConstructor();
			T obj = ctor.newInstance();
			String methodName = null;
			int index = 0;
			for (Iterator<Map.Entry<String, Class<?>>> it = colums.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, Class<?>> entry = (Map.Entry<String, Class<?>>) it
						.next();
				methodName = "set" + upperFirstChar(entry.getKey());
				Method method = type.getMethod(methodName, entry.getValue());
				if (method != null) {
					method.invoke(obj, tuple[index]);
				}
				index++;
			}
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String upperFirstChar(String name) {
		String firstChar = name.substring(0, 1);
		return firstChar.toUpperCase() + name.substring(1, name.length());
	}

	public static <T> List<T> sqlResultToClass(Class<T> type,
			List<Object[]> records, LinkedHashMap<String, Class<?>> colums) {
		List<T> result = new LinkedList<>();
		for (Object[] record : records) {
			result.add(sqlResultToClass(type, record, colums));
		}
		return result;
	}

	public String WRAP_DRAW = "draw";
	public String WRAP_START = "start";
	public String WRAP_LENGTH = "length";
	public String WRAP_CURRENT_PAGE = "currentPage";
	public String ORDER_BY = "orderBy";
	public String ORDER_TYPE = "orderType";

	/**
	 * 根据每星期中的天数得到星期几
	 */
	public static String getWeekDayStr(Integer day) {
		StringBuffer messageSb = new StringBuffer("周");
		switch (day) {
		case 1:
			messageSb.append("一");
			break;
		case 2:
			messageSb.append("二");
			break;
		case 3:
			messageSb.append("三");
			break;
		case 4:
			messageSb.append("四");
			break;
		case 5:
			messageSb.append("五");
			break;
		case 6:
			messageSb.append("六");
			break;
		case 7:
			messageSb.append("日");
			break;
		default:
			// 在无法识别的星期前时，默认礼拜一
			messageSb.append("一");
			break;
		}
		return messageSb.toString();
	}

	public static String getDistanceTime_Fix(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		StringBuffer returnStr = new StringBuffer();
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = 0;
			}

			day = diff / (24 * 60 * 60 * 1000);
			hour = diff / (60 * 60 * 1000) - day * 24;
			min = diff / (60 * 1000) - day * 24 * 60 - hour * 60;
			sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
			if (hour > 0 || min > 0 || sec > 0) {
				day += 1;
			}

			returnStr.append(day).append("天");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnStr.toString();
	}

	@SuppressWarnings("finally")
	public static int getMonthByDate(Date date) {
		SimpleDateFormat sdf2Month = new SimpleDateFormat("MM");
		int mon = 0;
		String month = sdf2Month.format(date);
		if ("01".equals(month)) {
			mon = 1;
		}

		if ("02".equals(month)) {
			mon = 2;
		}

		if ("03".equals(month)) {
			mon = 3;
		}

		if ("04".equals(month)) {
			mon = 4;
		}

		if ("05".equals(month)) {
			mon = 5;
		}

		if ("06".equals(month)) {
			mon = 6;
		}

		if ("07".equals(month)) {
			mon = 7;
		}

		if ("08".equals(month)) {
			mon = 8;
		}

		if ("09".equals(month)) {
			mon = 9;
		}

		if ("10".equals(month)) {
			mon = 10;
		}

		if ("11".equals(month)) {
			mon = 11;
		}

		if ("12".equals(month)) {
			mon = 12;
		}
		return mon;
	}

	public static String fillWithZeroInEnd(String source, int len) {
		if (source == null) {
			source = "";
		}
		int left = len - source.length();
		for (int i = 0; i < left; i++) {
			source = source + "0";
		}
		return source;
	}

	/**
	 * 
	 * @Title: isNumber @Description: 检查字符串，是否可以转换成数字 @param @param str @param @return
	 *         设定文件 @return boolean 返回类型 @throws
	 */
	public static boolean isNumber(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			} else {
				buf.append("*");

			}
		}
		return buf.toString();
	}
	
    /**
     * 
    * @Title: getPinYinHeadChar 
    * @Description: 获取汉字对应的拼音首写字母
    * @param @param str
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert = convert + pinyinArray[0].charAt(0);
			} else {
				convert = convert + word;
			}
		}
		return convert;
	}                                                                

	/**
	 * 
	* @Title: getNumberFromHanShuzi 
	* @Description: 将汉字中的小写数字转换成阿拉伯数字 
	* @param @param str
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getWeekDayFromHanShuzi(String str) {

		for (int i = 0; i < str.length(); i++) {
			if (str.contains("一")) {
				str = str.replace("一", "1");
			} else if (str.contains("二")) {
				str = str.replace("二", "2");
			} else if (str.contains("三")) {
				str = str.replace("三", "3");
			} else if (str.contains("四")) {
				str = str.replace("四", "4");
			} else if (str.contains("五")) {
				str = str.replace("五", "5");
			} else if (str.contains("六")) {
				str = str.replace("六", "6");
			} else if (str.contains("七") || str.contains("日")) {
				str = str.replace("七", "0");
				str = str.replace("日", "0");
			}
		}
		return str;
	}
	
	/**
	 * 
	* @Title: getLocalDayInWeek 
	* @Description: 返回当前日期的本地的星期数
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int getLocalDayInWeek() {

		Calendar cal = Calendar.getInstance();
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		weekDay = weekDay - 1;
		return weekDay;
	}
	
	
	/**
	 * 
	* @Title: getRequestParamContent 
	* @Description: 获取 request 中的请求参数内容
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getRequestParamContent(HttpServletRequest request) {
		StringBuffer jsonSb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jsonSb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonSb.toString();
	}
	

	/**
	 * 
	 * 将澳洲彩中的时间转换成本地时间， 当地时间 比本地时间早2.5个小时（即150分钟）
	 */
	public static Date convertAozhouTime2Local(Date dateParam) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateParam);
		cal.add(Calendar.MINUTE, -150);
		return cal.getTime();
	}
	
	
}