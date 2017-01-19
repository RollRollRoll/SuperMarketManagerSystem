package team.smms.database.dao;

import team.smms.database.daointerface.DAOInterface;
import team.smms.database.tableclass.ReturnSale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnSaleDAO implements DAOInterface<ReturnSale> {
    private Connection con = null;
    private PreparedStatement stat = null;

    public ReturnSaleDAO(Connection con) {
        this.con = con;
    }

    private List<ReturnSale> createList(ResultSet rs) throws SQLException {
        List<ReturnSale> list = new ArrayList<ReturnSale>();
        while (rs.next()) {
            String rsaleID = rs.getString(1);
            String commodityID = rs.getString(2);
            int number = rs.getInt(3);
            float money = rs.getFloat(4);
            String returnTime = rs.getString(5);
            String operator = rs.getString(6);

            ReturnSale temp = new ReturnSale();
            temp.setRsaleID(rsaleID);
            temp.setCommodityID(commodityID);
            temp.setNumber(number);
            temp.setMoney(money);
            temp.setReturnTime(returnTime);
            temp.setOperator(operator);
            list.add(temp);
        }
        return list;
    }


    public String createSQL(String[] memberName, String[] matchInformation, int mark) {
        int lengthOfMatchInformation = matchInformation.length;
        String sql = new String("");
        int index = 0;
        if (mark == 1) {
            for (String m : memberName) {
                if (m.equals("rasleid")) {
                    sql = "rsaleID = '" + matchInformation[index] + "'";
                    break;
                } else if (m.equals("returntime")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(returntime >= '" + matchInformation[index] + "')";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(returntime <= '" + matchInformation[index + 1] + "')";
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
                return " WHERE " + sql + ';';
            }
        } else {
            for (String m : memberName) {
                if (m.equals("returntime")) {
                    sql += "returntime = '" + matchInformation[index++] + "'";
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

    @Override
    public List<ReturnSale> findAll() throws SQLException {
        String sql = "SELECT * FROM ReturnSale;";
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public List<ReturnSale> find(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "SELECT * FROM ReturnSale" + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return this.createList(rs);
    }

    @Override
    public boolean delete(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "DELETE FROM ReturnSale" + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate(sql);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean insert(ReturnSale returnSale) throws SQLException {
        String sql = "INSERT INTO ReturnSale(rsaleID,commodityID,number,money,returnTime,operator) "
                + "VALUES(?,?,?,?,?,?);";
        stat = con.prepareStatement(sql);
        stat.setString(1, returnSale.getRsaleID());
        stat.setString(2, returnSale.getCommodityID());
        stat.setInt(3, returnSale.getNumber());
        stat.setFloat(4, returnSale.getMoney());
        stat.setString(5, returnSale.getReturnTime());
        stat.setString(6, returnSale.getOperator());
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(String[] memberName, String[] updateInformation, String key) throws SQLException {
        String sql = "UPDATE ReturnSale SET " + createSQL(memberName, updateInformation, 0) + " WHERE rsaleID = '" + key + "';";
        ;
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate(sql);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

}
