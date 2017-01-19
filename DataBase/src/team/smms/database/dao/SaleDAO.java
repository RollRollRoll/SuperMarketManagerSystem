package team.smms.database.dao;

import team.smms.database.daointerface.DAOInterface;
import team.smms.database.tableclass.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO implements DAOInterface<Sale> {
    private Connection con = null;
    private PreparedStatement stat = null;

    public SaleDAO(Connection con) {
        this.con = con;
    }

    private List<Sale> createList(ResultSet rs) throws SQLException {
        List<Sale> list = new ArrayList<Sale>();
        while (rs.next()) {
            String saleId = rs.getString(1);
            String commodityId = rs.getString(2);
            String sellTime = rs.getString(3);
            int number = rs.getInt(4);
            float money = rs.getInt(5);
            String operator = rs.getString(6);
            Sale temp = new Sale();
            temp.setSaleId(saleId);
            temp.setCommodityId(commodityId);
            temp.setSellTime(sellTime);
            temp.setNumber(number);
            temp.setMoney(money);
            temp.setOperator(operator);
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
                if (m.equals("saleid")) {
                    sql = "saleId = '" + matchInformation[index] + "'";
                    break;
                } else if (m.equals("selltime")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(sellTime >= '" + matchInformation[index] + "')";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(sellTime <= '" + matchInformation[index + 1] + "')";
                        if (index + 1 != lengthOfMatchInformation - 1) {
                            sql += " AND ";
                        }
                    }
                    index += 2;
                } else if (m.equals("commodityname")) {
                    sql += "commodityid in (select commodityid from commodity where commodityname like '%" + matchInformation[index++] + "%')";
                    if (index != lengthOfMatchInformation) {
                        sql += "AND";
                    }
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
                return " where " + sql + ';';
            }
        } else {
            for (String m : memberName) {
                if (m.equals("selltime")) {
                    sql += "selltime = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("operator")) {
                    sql += "operator = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("commodityid")) {
                    sql += "commodityid = '" + matchInformation[index++] + "'";
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

    public List<Sale> findAll() throws SQLException {
        String sql = "SELECT * FROM sale;";
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }


    public List<Sale> find(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "SELECT * FROM sale " + createSQL(memberName, matchInformation, 1);
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    public boolean insert(Sale sale) throws SQLException {
        String sql = "INSERT INTO sale(saleId,commodityId,sellTime,number,money,operator) VALUES(?,?,?,?,?,?);";
        stat = con.prepareStatement(sql);
        stat.setString(1, sale.getSaleId());
        stat.setString(2, sale.getCommodityId());
        stat.setString(3, sale.getSellTime());
        stat.setInt(4, sale.getNumber());
        stat.setFloat(5, sale.getMoney());
        stat.setString(6, sale.getOperator());
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "DELETE FROM sale " + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate(sql);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update(String[] memberName, String[] updateInformation, String key) throws SQLException {
        String sql = "UPDATE sale SET " + createSQL(memberName, updateInformation, 0) + " WHERE saleid = '" + key + "';";
        ;
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
