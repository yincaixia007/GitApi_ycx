package net.kitty.lesson.helloapi.util;

import java.io.InputStream;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelDataUtil {

	public static Object[][] readData(String dataFile, String sheetName) {
		return read(dataFile, sheetName, 0, 0, 0, 0);
	}

	public static Object[][] readData(String dataFile, String sheetName, int beginRowNum) {
		return read(dataFile, sheetName, beginRowNum, 0, 0, 0);
	}

	public static Object[][] readData(String dataFile, String sheetName, int beginRowNum,
			int beginColumnNum) {
		return read(dataFile, sheetName, beginRowNum, 0, beginColumnNum, 0);
	}

	public static Object[][] readData(String dataFile, String sheetName, int beginRowNum,
			int rowOffset, int beginColumnNum, int columnOffset) {
		return read(dataFile, sheetName, beginRowNum, rowOffset, beginColumnNum, columnOffset);
	}

	public static Object[][] read(String dataFile, String sheetName, int beginRowNum, int rowOffset,
			int beginColumnNum, int columnOffset) {
		InputStream is = null;
		try {
			is = PropertyUtil.class.getResourceAsStream(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Workbook wb = null;
		Object[][] data = null;
		try {
			wb = Workbook.getWorkbook(is);
			Sheet sheet = wb.getSheet(sheetName);
			if (sheet == null)
				return null;
			int rows = sheet.getRows();
			int cols = sheet.getColumns();
			if (rowOffset == 0) {
				rowOffset = rows - beginRowNum;
			} else if (rows < (beginRowNum + rowOffset)) {
				rowOffset = rows - beginRowNum;
			}
			if (columnOffset == 0) {
				columnOffset = cols - beginColumnNum;
			} else if (cols < (beginColumnNum + columnOffset)) {
				columnOffset = cols - beginColumnNum;
			}
			data = new Object[rowOffset][columnOffset];

			for (int i = beginRowNum; i < beginRowNum + rowOffset; i++) {
				for (int j = beginColumnNum; j < beginColumnNum + columnOffset; j++) {
					// 获取单元格数据 getCell(col,row);
					Cell cell = sheet.getCell(j, i);
					if (cell != null) {
						String celldata = cell.getContents().trim();
						data[i - beginRowNum][j - beginColumnNum] = celldata;
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return data;
	}

	public static void main(String[] args) {
		Object[][] data = ExcelDataUtil.read("/excel/userInfo.xls", "user", 0, 0, 0, 0);
		for (Object[] d : data) {
			System.out.println(d[0] + "\t" + d[1] + "\t" + d[2]);
		}
	}
}
