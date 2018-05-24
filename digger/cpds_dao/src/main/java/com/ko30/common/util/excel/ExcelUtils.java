package com.ko30.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("all")
public class ExcelUtils {
	
	/**
	 * 解析properties
	 * 
	 * @param url peoperties地址(带斜线)
	 * @param title peoperties中所取的key
	 * @return  value的字符数组
	 */
	public static String[] getProperties(String url,String title){
		Properties prop = new Properties();   
        InputStream in=Object.class.getResourceAsStream(url);
		
			try {
				prop.load(in);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String[] titles = prop.getProperty(title).split(",");
		return titles;
	}
	
	/**
	 * 解析xml
	 * @param url xml 地址(最好绝对路径 )
	 * @param title xml中所取的子节点(用于分类)
	 * @return 字符数组
	 */
	public static String[] getXml(String url, String node) {
		String[] titles = null;
		// 获取document
		try {
			Document doc = new SAXReader().read(url);// 获取document
			Element rootEle = doc.getRootElement();// 获取根节点
			Element element = rootEle.element(node);// //获取node 子节点
			List<Element> list = element.elements("title");
			int size = list.size();
			int i = 0;
			titles = new String[size];
			for (Element element2 : list) {
				String text = element2.getText();
//				System.out.println(text);
				titles[i] = text;
				/* System.out.println(titles[i]); */
				i++;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return titles;

	}
	
}
