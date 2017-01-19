package team.smms.database.dao;

import team.smms.database.daointerface.DAOInterface;
import team.smms.database.tableclass.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kgfan on 2016/12/13.
 */
public class StockDAO implements DAOInterface<Stock> {
    private Connection con = null;
    private PreparedStatement stat = null;

    public StockDAO(Connection con) {
        this.con = con;
    }


    private List<Stock> createList(ResultSet rs) throws SQLException {
        List<Stock> list = new ArrayList<Stock>();
        while (rs.next()) {
            String stockID = rs.getString(1);
            String commodityName = rs.getString(2);
            float saleprice = rs.getFloat(3);
            int number = rs.getInt(4);
            int downLimit = rs.getInt(5);

            Stock temp = new Stock();
            temp.setStockID(stockID);
            temp.setCommodityName(commodityName);
            temp.setSaleprice(saleprice);
            temp.setNumber(number);
            temp.setDownLimit(downLimit);
            list.add(temp);
        }
        return list;
    }

    public String createSQL(String[] memberName, String[] matchInformation, int mark) {
        int lengthOfMatchInformation = matchInformation.length;
        String sql = new String();
        int index = 0;
        if (mark == 1) {
            for (String m : memberName) {
                if (m.equals("stockid") && !matchInformation[index].equals("")) {
                    sql = "stockid = '" + matchInformation[index] + "'";
                    break;
                } else if (m.equals("saleprice")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(saleprice >= '" + matchInformation[index] + "')";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(saleprice <= '" + matchInformation[index + 1] + "')";
                        if (index + 1 != lengthOfMatchInformation - 1) {
                            sql += " AND ";
                        }
                    }
                    index += 2;
                } else if (m.equals("number")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(number >= '" + matchInformation[index] + "')";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(number <= '" + matchInformation[index + 1] + "')";
                        if (index + 1 != lengthOfMatchInformation - 1) {
                            sql += " AND ";
                        }
                    }
                    index += 2;
                } else {
                    sql += "(" + m + " like '%" + matchInformation[index++] + "%')";
                    if (index != lengthOfMatchInformation) {
                        sql += " AND ";
                    }
                }
            }
            if (sql.equals("")) {
                return new String(";");
            } else {
                return " WHERE " + sql + ';';
            }
        } else {
            for (String m : memberName) {
                if (m.equals("commodityname")) {
                    sql += "commodityName = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else {
                    sql += m + " = " + matchInformation[index++];
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                }
            }
            if (sql.equals("")) {
                return new String("");
            } else {
                return sql;
            }
        }
    }

    @Override
    public List<Stock> findAll() throws SQLException {
        String sql = "SELECT * FROM stock ;";
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public List<Stock> find(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "SELECT * FROM stock" + createSQL(memberName, matchInformation, 1);
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public boolean delete(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "DELETE FROM stock" + createSQL(memberName, matchInformation, 1);
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate(sql);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean insert(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock(stockid,commodityName,saleprice,number,downlimit) VALUES(?,?,?,?,?);";
        stat = con.prepareStatement(sql);
        stat.setString(1, stock.getStockID());
        stat.setString(2, stock.getCommodityName());
        stat.setFloat(3, stock.getSaleprice());
        stat.setInt(4, stock.getNumber());
        stat.setInt(5, stock.getDownLimit());
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(String[] memberName, String[] updateInformation, String key) throws SQLException {
        String sql = "UPDATE stock SET " + createSQL(memberName, updateInformation, 0) + " WHERE stockid = '" + key + "';";
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
}
