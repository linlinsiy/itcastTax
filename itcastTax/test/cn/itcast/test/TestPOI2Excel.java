package cn.itcast.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOI2Excel {

	@SuppressWarnings("resource")
	@Test
	public void write03Excel() throws IOException {
		//1、创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//2、创建工作表
		HSSFSheet sheet = workbook.createSheet("hello");
		//3、创建行,从0开始
		HSSFRow row = sheet.createRow(2);
		//4、创建单元格，从0开始
		HSSFCell cell = row.createCell(2);
		cell.setCellValue("Hello World");
		
		String filePath = "E:\\files\\测试.xls";
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();		
	}

	@Test
	public void read03Excel() throws IOException {
		String fileName = "E:\\files\\测试.xls";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		//1、读取工作簿
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		//2、读取工作表
		HSSFSheet sheet = workbook.getSheetAt(0);
		//3、读取行,从0开始
		HSSFRow row = sheet.getRow(3);
		//4、读取单元格，从1开始
		HSSFCell cell = row.getCell(3);
		System.out.println("第四行第四列："+cell.getStringCellValue());

		workbook.close();
		fileInputStream.close();	
	}
	
	@Test
	public void write07Excel() throws IOException {
		//1、创建工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//2、创建工作表
		XSSFSheet sheet = workbook.createSheet("hello");
		//3、创建行,从0开始
		XSSFRow row = sheet.createRow(2);
		//4、创建单元格，从0开始
		XSSFCell cell = row.createCell(2);
		cell.setCellValue("Hello World");
		
		String filePath = "E:\\files\\测试.xlsx";
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();		
	}
	
	@Test
	public void read07Excel() throws IOException {
		String fileName = "E:\\files\\测试.xlsx";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		//1、读取工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		//2、读取工作表
		XSSFSheet sheet = workbook.getSheetAt(0);
		//3、读取行,从0开始
		XSSFRow row = sheet.getRow(2);
		//4、读取单元格，从1开始
		XSSFCell cell = row.getCell(2);
		System.out.println("第四行第四列："+cell.getStringCellValue());

		workbook.close();
		fileInputStream.close();	
	}
	@Test
	public void testExcelStyle() throws Exception {
		//1、创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//1.1 创建合并单元格对象，收尾都包含
		CellRangeAddress cellRangeAddress = new CellRangeAddress(2, 2, 2, 4);
		//1.2 创建单元格样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)16);
		
		style.setFont(font);
		
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置填充模式
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
//		style.setFillForegroundColor(HSSFColor.YELLOW.index);//绿色
		//2、创建工作表
		HSSFSheet sheet = workbook.createSheet("hello");
		//2.1 加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//3、创建行,从0开始
		HSSFRow row = sheet.createRow(2);
		//4、创建单元格，从0开始
		HSSFCell cell = row.createCell(2);
		//4.1 加载单元格样式
		cell.setCellStyle(style);
		cell.setCellValue("Hello World");
		
		String filePath = "E:\\files\\测试.xls";
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();	
	}
}
