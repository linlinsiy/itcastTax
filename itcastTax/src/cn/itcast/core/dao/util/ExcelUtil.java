package cn.itcast.core.dao.util;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.nsfw.user.entity.User;

public class ExcelUtil {
	/**
	 * 导出用户列表到excel
	 * @param userList 用户列表
	 * @param outputStream 输出流
	 */
	public static void exportExcel(List<User> userList,
			ServletOutputStream outputStream) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
			HSSFCellStyle style1 = createCellStyle(workbook, (short)16);
			HSSFCellStyle style2 = createCellStyle(workbook, (short)13);
			HSSFSheet sheet = workbook.createSheet("用户列表");
			sheet.addMergedRegion(cellRangeAddress);
			sheet.setDefaultColumnWidth(15);
			
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellStyle(style1);
			cell1.setCellValue("用户列表");
			
			
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {"用户名","账号","所属部门","性别","邮箱"};
			for (int i = 0; i < titles.length; i++) {		
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			
			if(userList!=null&&userList.size()>0){
				for (int j = 0; j < userList.size(); j++) {				
					HSSFRow row = sheet.createRow(j+2);
					row.createCell(0).setCellValue(userList.get(j).getName());
					row.createCell(1).setCellValue(userList.get(j).getAccount());
					row.createCell(2).setCellValue(userList.get(j).getDept());
					row.createCell(3).setCellValue(userList.get(j).isGender()?"男":"女");
					row.createCell(4).setCellValue(userList.get(j).getEmail());				
				}
			}
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	/**
	 * 创建单元格样式
	 * @param workbook 工作簿
	 * @param fontSize 字体大小
	 * @return 单元格样式
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints(fontSize);
		style.setFont(font);
		return style;
	}
	
}
