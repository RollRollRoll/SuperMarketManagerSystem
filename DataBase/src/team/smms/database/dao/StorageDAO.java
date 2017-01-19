package team.smms.database.dao;

import team.smms.database.daointerface.DAOInterface;
import team.smms.database.tableclass.Storage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/23.
 */
public class StorageDAO implements DAOInterface<Storage> {
    private Connection con = null;
    private java.sql.PreparedStatement stat = null;

    public StorageDAO(Connection con) {
        this.con = con;
    }

    private List<Storage> createList(ResultSet rs) throws SQLException {
        List<Storage> list = new ArrayList<Storage>();
        while (rs.next()) {
            Storage temp = new Storage();
            temp.setStorageID(rs.getString(1));
            temp.setCommodityID(rs.getString(2));
            temp.setNumber(rs.getInt(3));
            temp.setInPrice(rs.getFloat(4));
            temp.setInDate(rs.getString(5));
            temp.setOperator(rs.getString(6));
            list.add(temp);
        }
        return list;
    }

    private String createSQL(String[] memberName, String[] matchInformation, int mark) {
        int lengthOfMatchInformation = matchInformation.length;
        String sql = new String();
        int index = 0;
        if (mark == 1) {
            for (String m : memberName) {
                if (m.equals("storageid")) {
                    sql = "storageid = '" + matchInformation[index] + "'";
                    break;
                } else if (m.equals("inprice")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(inprice >= " + matchInformation[index] + ")";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(inprice <= " + matchInformation[index + 1] + ")";
                        if (index + 1 != lengthOfMatchInformation - 1) {
                            sql += " AND ";
                        }
                    }
                    index += 2;
                } else if (m.equals("indate")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(indate >= '" + matchInformation[index] + "')";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(indate <= '" + matchInformation[index + 1] + "')";
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
                if (m.equals("inprice")) {
                    sql += "inprice = " + matchInformation[index++];
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("number")) {
                    sql += "number = " + matchInformation[index++];
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else {
                    sql += m + " = '" + matchInformation[index++] + "'";
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
    public List<Storage> findAll() throws SQLException {
        String sql = "SELECT * FROM storage;";
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public List<Storage> find(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "SELECT * FROM storage" + createSQL(memberName, matchInformation, 1);
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public boolean delete(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "DELETE FROM storage" + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate(sql);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean insert(Storage storage) throws SQLException {
        String sql = "INSERT INTO storage(storageid,commodityid,number,inprice,indate,operator) VALUES(?,?,?,?,?,?);";
        stat = con.prepareStatement(sql);
        stat.setString(1, storage.getStorageID());
        stat.setString(2, storage.getCommodityID());
        stat.setInt(3, storage.getNumber());
        stat.setFloat(4, storage.getInPrice());
        stat.setString(5, storage.getInDate());
        stat.setString(6, storage.getOperator());
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(String[] memberName, String[] updateInformation, String key) throws SQLException {
        String sql = "UPDATE storage SET " + createSQL(memberName, updateInformation, 0) + " WHERE storageid = '" + key + "';";
        ;
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

}