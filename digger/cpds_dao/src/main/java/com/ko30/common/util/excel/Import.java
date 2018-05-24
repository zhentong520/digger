package com.ko30.common.util.excel;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excel文件操作工具类-读写
 * @author lmk
 *
 */
public class Import {
	public List<List<String>> readExcel(FileInputStream fileInputStream) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			// 同时支持Excel 2003、2007
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			// 遍历每个Sheet
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
				// 遍历每一行（跳过第一行，因为第一行为标题）
				for (int r = 1; r < rowCount; r++) {
					List<String> l = new ArrayList<String>();
					Row row = sheet.getRow(r);
					int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
					// 遍历每一列
					for (int c = 0; c < cellCount; c++) {
						Cell cell = row.getCell(c);
						int cellType = cell.getCellType();
						String cellValue = null;
						switch (cellType) {
						case Cell.CELL_TYPE_STRING: // 文本
							cellValue = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC: // 数字、日期
							if (DateUtil.isCellDateFormatted(cell)) {
								cellValue = fmt.format(cell.getDateCellValue()); // 日期型
							} else {
								cellValue = String.valueOf(cell
										.getNumericCellValue()); // 数字
								//如果小数点后只有一位数，且这位数是0，则去掉“.0”
								String str = cellValue.replaceAll(cellValue.substring(0,cellValue.indexOf("."))+".", "");
								if(Integer.parseInt(str)==0){
									cellValue = cellValue.substring(0,cellValue.indexOf("."));
								}
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN: // 布尔型
							cellValue = String.valueOf(cell
									.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_BLANK: // 空白
							cellValue = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_ERROR: // 错误
							cellValue = "错误";
							break;
						case Cell.CELL_TYPE_FORMULA: // 公式
							cellValue = "错误";
							break;
						default:
							cellValue = "错误";
						}
						l.add(cellValue);
					}
					list.add(l);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
