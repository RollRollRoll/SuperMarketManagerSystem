package team.smms.database.dao;

import team.smms.database.daointerface.DAOInterface;
import team.smms.database.tableclass.Commodity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommodityDAO implements DAOInterface<Commodity> {
    private Connection con = null;
    private PreparedStatement stat = null;

    public CommodityDAO(Connection con) {
        this.con = con;
    }

    private List<Commodity> createList(ResultSet rs) throws SQLException {
        List<Commodity> list = new ArrayList<Commodity>();
        while (rs.next()) {
            String commodityID = rs.getString(1);
            String commodityName = rs.getString(2);
            String stockID = rs.getString(3);
            String produceDate = rs.getString(4);
            int storageTime = rs.getInt(5);
            String unit = rs.getString(6);
            String providerID = rs.getString(7);
            InputStream pic = rs.getBinaryStream(8);

            Commodity temp = new Commodity();
            temp.setCommodityID(commodityID);
            temp.setCommodityName(commodityName);
            temp.setStockID(stockID);
            temp.setProduceDate(produceDate);
            temp.setStorageTime(storageTime);
            temp.setUnit(unit);
            temp.setProviderID(providerID);
            temp.setPic(pic);
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
                if (m.equals("commodityid")) {
                    sql = "commodityid = '" + matchInformation[index] + "'";
                    break;
                } else if (m.equals("producedate")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(producedate >= '" + matchInformation[index] + "')";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(producedate <= '" + matchInformation[index + 1] + "')";
                        if (index + 1 != lengthOfMatchInformation - 1) {
                            sql += " AND ";
                        }
                    }
                    index += 2;
                } else if (m.equals("storagetime")) {
                    if (!matchInformation[index].equals("")) {
                        sql += "(storagetime >= '" + matchInformation[index] + "')";
                        if (index != lengthOfMatchInformation - 2 || !matchInformation[index + 1].equals("")) {
                            sql += " AND ";
                        }
                    }
                    if (!matchInformation[index + 1].equals("")) {
                        sql += "(storagetime <= '" + matchInformation[index + 1] + "')";
                        if (index + 1 != lengthOfMatchInformation - 1) {
                            sql += " AND ";
                        }
                    }
                    index += 2;
                } else if (m.equals("providername")) {
                    sql += "providerid in (select providerid from provider where providername like '%" + matchInformation[index++] + "%')";
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
                if (m.equals("commodityname")) {
                    sql += "commodityname = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("stockid")) {
                    sql += "stockid = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("unit")) {
                    sql += "unit = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("providerid")) {
                    sql += "providerid = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation &&matchInformation[index]!=null) {
                        sql += " , ";
                    }
                } else if (m.equals("producedate")) {
                    sql += "producedate = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("storagetime")) {
                    sql += "storagetime = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("pic")) {
                    if (matchInformation[index]!=null) {
                        try {
                            FileInputStream f = new FileInputStream(new File(matchInformation[index++]));
                            sql += ",pic ='" + f.toString() + "'";
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
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
    public List<Commodity> findAll() throws SQLException {
        String sql = "SELECT * FROM commodity ;";
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public List<Commodity> find(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "SELECT * FROM commodity" + createSQL(memberName, matchInformation, 1);
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public boolean insert(Commodity commodity) throws SQLException {
        String sql = "INSERT INTO commodity(commodityid,commodityname,stockid,producedate,storagetime,unit,providerid,pic) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        stat = con.prepareStatement(sql);
        stat.setString(1, commodity.getCommodityID());
        stat.setString(2, commodity.getCommodityName());
        stat.setString(3, commodity.getStockID());
        stat.setString(4, commodity.getProduceDate());
        stat.setInt(5, commodity.getStorageTime());
        stat.setString(6, commodity.getUnit());
        stat.setString(7, commodity.getProviderID());
        stat.setBinaryStream(8, commodity.getPic());
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "DELETE FROM commodity" + createSQL(memberName, matchInformation, 1);
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
    public boolean update(String[] memberName, String[] updateInformation, String key) throws SQLException {
        String sql = "UPDATE commodity SET " + createSQL(memberName, updateInformation, 0) + " WHERE commodityid = '" + key + "';";
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
