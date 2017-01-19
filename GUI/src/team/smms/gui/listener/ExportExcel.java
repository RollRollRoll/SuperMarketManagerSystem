package team.smms.gui.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcel {

	FileOutputStream fos;
	JFileChooser jfc = new JFileChooser();
	DefaultTableModel tablemodel;

	public ExportExcel(DefaultTableModel tablemodel) {
		this.tablemodel=tablemodel;
		jfc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return (file.getName().endsWith(".xls"));
			}

			public String getDescription() {
				return ".xls";
			}
		});

		jfc.showSaveDialog(null);
		File file = jfc.getSelectedFile();
		try {
			this.fos = new FileOutputStream(file + ".xls");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public void export() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet hs = wb.createSheet();
		int row = tablemodel.getRowCount();
		int cloumn = tablemodel.getColumnCount();
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		style1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 15);
		font1.setBoldweight((short) 700);
		style1.setFont(font);

		for (int i = 0; i < row + 1; i++) {
			HSSFRow hr = hs.createRow(i);
			for (int j = 0; j < cloumn; j++) {
				int size=0;
				if (i == 0) {
					String value = tablemodel.getColumnName(j);
					size=value.length();
					hs.autoSizeColumn((short) j);
					HSSFRichTextString srts = new HSSFRichTextString(value);
					HSSFCell hc = hr.createCell((short) j);
					hc.setCellStyle(style1);
					hc.setCellValue(srts);
				} 
				else {
					if (tablemodel.getValueAt(i - 1, j) != null) {
						String value = tablemodel.getValueAt(i - 1, j).toString();
						if(value.length()>size){
							hs.autoSizeColumn((short) j);
							size=value.length();
						}
						HSSFRichTextString srts = new HSSFRichTextString(value);
						HSSFCell hc = hr.createCell((short) j);
						hc.setCellStyle(style);

						if (value.equals("") || value == null) {
							hc.setCellValue(new HSSFRichTextString(""));
						} else {
							hc.setCellValue(srts);
						}
					}
				}
			}
		}

		try {
			wb.write(fos);
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
