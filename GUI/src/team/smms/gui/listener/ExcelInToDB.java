package team.smms.gui.listener;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExcelInToDB {

    private static Connection conn;

    public ExcelInToDB(Connection conn, String tableName) {
        this.conn = conn;
        insertData(tableName);
    }

    public static void insertData(String tbName) {
        try {

            String dir = "";
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    return (file.getName().endsWith(".xls"));
                }

                public String getDescription() {
                    return ".xls";
                }
            });
            int a = jfc.showOpenDialog(new JDialog());
            if (a == JFileChooser.APPROVE_OPTION) {
                dir = jfc.getSelectedFile().getAbsolutePath().trim();
                dir = dir.replaceAll("\\\\", "/");
            }

            //casilin:插入数据，先从excel中读取数据
            InputStream is = new FileInputStream(dir);
            ExcelReader excelReader = new ExcelReader();
            String[] colName = excelReader.readExcelTitle(is);

            //开始建立插入的sql语句,每一次插入的开头都是不变的,都是字段名
            StringBuffer sqlBegin = new StringBuffer("insert into " + tbName + " values(");


            //下面读取字段内容
            POIFSFileSystem fs;
            HSSFWorkbook wb;
            HSSFSheet sheet;
            HSSFRow row;

            is = new FileInputStream(dir);
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);

            //得到总行数
            int rowNum = sheet.getLastRowNum();
            row = sheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
            //正文内容应该从第二行开始,第一行为表头的标题
            String sql = new String(sqlBegin);
            String temp;
            for (int i = 1; i <= rowNum; i++) {
                row = sheet.getRow(i);
                int j = 0;
                while (j < colNum) {
                    temp = excelReader.getStringCellValue(row.getCell((short) j)).trim();
                    System.out.println(temp);
                    sql = sql + temp;
                    if (j != colNum - 1) {
                        sql = sql + ",";
                    }
                    j++;
                }
                sql = sql + ")";
                System.out.println(sql.toString());
                PreparedStatement ps = conn.prepareStatement(sql.toString());
                ps.executeUpdate();
                ps.close();
                sql = "";
                sql = sqlBegin.toString();
            }
            JOptionPane.showMessageDialog(null, "导入成功", null, JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "导入失败", null, JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "导入失败", null, JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "导入失败", null, JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }
    }
}

class ExcelReader {
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    /**
     * 读取Excel表格表头的内容
     *
     * @param
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        //标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getTitleValue(row.getCell((short) i));
        }
        return title;
    }


    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容，若为字符串的要加单引号
     */
    public String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = "'" + cell.getStringCellValue() + "'";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "''";
                break;
            default:
                strCell = "''";
                break;
        }
        if (strCell.equals("''") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    public String getTitleValue(HSSFCell cell) {
        String strCell = cell.getStringCellValue();
        return strCell;
    }

}