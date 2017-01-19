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

            //casilin:�������ݣ��ȴ�excel�ж�ȡ����
            InputStream is = new FileInputStream(dir);
            ExcelReader excelReader = new ExcelReader();
            String[] colName = excelReader.readExcelTitle(is);

            //��ʼ���������sql���,ÿһ�β���Ŀ�ͷ���ǲ����,�����ֶ���
            StringBuffer sqlBegin = new StringBuffer("insert into " + tbName + " values(");


            //�����ȡ�ֶ�����
            POIFSFileSystem fs;
            HSSFWorkbook wb;
            HSSFSheet sheet;
            HSSFRow row;

            is = new FileInputStream(dir);
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);

            //�õ�������
            int rowNum = sheet.getLastRowNum();
            row = sheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
            //��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���
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
            JOptionPane.showMessageDialog(null, "����ɹ�", null, JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "����ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "����ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "����ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
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
     * ��ȡExcel����ͷ������
     *
     * @param
     * @return String ��ͷ���ݵ�����
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
        //����������
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getTitleValue(row.getCell((short) i));
        }
        return title;
    }


    /**
     * ��ȡ��Ԫ����������Ϊ�ַ������͵�����
     *
     * @param cell Excel��Ԫ��
     * @return String ��Ԫ���������ݣ���Ϊ�ַ�����Ҫ�ӵ�����
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