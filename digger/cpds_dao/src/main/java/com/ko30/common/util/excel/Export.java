package com.ko30.common.util.excel;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 反射报表导出
 * @author lmk
 *
 */
public class Export<T> {
	
	/**
	 * @param identifier 报表导出的唯一标识
	 * @param list 要导出的数据
	 * @param response spring管理的response
	 * @return
	 * @throws Exception
	 */
	public boolean excelExport(String identifier,List<T> list,HttpServletResponse response) throws Exception{
		boolean is;
		String[] titles = ExcelUtils.getXml(this.getClass().getClassLoader().getResource("").getPath()+"/Excel.xml",identifier);
		ArrayList<String> filedName = new ArrayList<String>();
		for (int i = 1; i < titles.length; i++){//排除第一个，第一个为表头
			String title = titles[i];
			filedName.add(title);
		}
		ArrayList<ArrayList<Object>> filedData = new ArrayList<ArrayList<Object>>();
		for (int i = 0; i < list.size(); i++){
			ArrayList<Object> l = new ArrayList<Object>();// 把每一条数据变成一个ArrayList集合
			Class<? extends Object> c = list.get(i).getClass(); //获取对应的javabean
			Field field[] = c.getDeclaredFields();  
		    for (Field f : field) {
		    	Object v = invokeMethod(list.get(i), f.getName(), null);
				if (v != null || "".equals(v)){
					l.add(v);
				}
//		    	System.out.println(f.getName() + "\t" + v + "\t" + f.getType());  
		 	}  
			filedData.add(l);// 把每一个ArrayList添加到总的ArrayList中
		}
		
		// 保持Excel文件名
		String filename = titles[0];
		try {
			OutputStream out = response.getOutputStream();// 获取输出流
			response.reset();// 重置输出流
			response.setContentType("application/vnd.ms-excel:charset=utf-8");// 设置导出Excel报表的导出形式
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ new String(filename.getBytes(), "iso8859-1")//指定下载的文件名
							+ ".xls");//// 设置文件名称信息 
			ExcelFileGenerator generator = new ExcelFileGenerator(filedName,filedData); // 导出数据
			generator.expordExcel(out); // 调用导出方法
			out.flush(); // 刷新输出流
			if (out != null) // 关闭输出流
				out.close();
			is = true;
		} catch (Exception e) {
			is = false;
			e.printStackTrace();
		}
		return is;
	}
	
	/**
	 * 反射获取当前对象里的属性值  
	 * @param o
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	private static Object invokeMethod(Object obj, String methodName,
			Object[] args) throws Exception {
		Class<? extends Object> o = obj.getClass();
		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			method = o.getMethod("get" + methodName);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			return " 不能发现 'get" + methodName + "' 方法";
		}
		return method.invoke(obj);
	}
	
}
