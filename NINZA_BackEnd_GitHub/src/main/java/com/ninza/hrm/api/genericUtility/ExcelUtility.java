package com.ninza.hrm.api.genericUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	String excelTestDataPath = "";
	
	public String getDataFromExcel(String sheetName, int rowNo, int celNo) throws Throwable {
		FileInputStream fis = new FileInputStream(excelTestDataPath);
		Workbook wb = WorkbookFactory.create(fis);
		String data = wb.getSheet(sheetName).getRow(rowNo).getCell(celNo).getStringCellValue();
		return data;
	}

	public int getRowCount(String sheetName) throws Throwable {
		FileInputStream fis = new FileInputStream(excelTestDataPath);
		Workbook wb = WorkbookFactory.create(fis);
		int rowNo = wb.getSheet(sheetName).getLastRowNum();
		return rowNo;
	}

	public void setDataIntoExcel(String sheetName, int rowNo, int celNo, String data) throws Throwable {
		FileInputStream fis = new FileInputStream(excelTestDataPath);
		Workbook wb = WorkbookFactory.create(fis);
		Cell cel = wb.getSheet(sheetName).getRow(rowNo).getCell(celNo);
		cel.setCellValue(data);
		FileOutputStream fos = new FileOutputStream(excelTestDataPath);
		wb.write(fos);
		wb.close();
	}
//
//	public Object[][] dataProvider(String sheetName) throws Throwable, IOException {
//		FileInputStream fi = new FileInputStream(excelTestDataPath);
//		Workbook wb = WorkbookFactory.create(fi);
//		Sheet sh = wb.getSheet(sheetName);
//		int lastRow = sh.getLastRowNum() + 1;
//		short lastCell = sh.getRow(0).getLastCellNum();
//		Object[][] obj = new Object[lastRow][lastCell];
//		try {
//			for (int i = 0; i < lastRow; i++) {
//				for (int j = 0; j < lastCell; j++) {
//					obj[i][j] = sh.getRow(i).getCell(j).getStringCellValue();
//				}
//			}
//		} catch (Exception e) {
//		}
//		return obj;
//	}
}