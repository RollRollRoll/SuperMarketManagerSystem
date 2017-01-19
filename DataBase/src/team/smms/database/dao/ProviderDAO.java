package team.smms.database.dao;

import team.smms.database.daointerface.DAOInterface;
import team.smms.database.tableclass.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/22.
 */


public class ProviderDAO implements DAOInterface<Provider> {
    private Connection con = null;
    private PreparedStatement stat = null;

    public ProviderDAO(Connection con) {
        this.con = con;
    }

    private List<Provider> createList(ResultSet rs) throws SQLException {
        List<Provider> list = new ArrayList<Provider>();
        while (rs.next()) {
            String prividerID = rs.getString(1);
            String providerNaume = rs.getString(2);
            String providerAddress = rs.getString(3);
            String phone = rs.getString(4);

            Provider temp = new Provider();
            temp.setProviderID(prividerID);
            temp.setProviderName(providerNaume);
            temp.setProviderAddress(providerAddress);
            temp.setPhone(phone);
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
                if (m.equals("providerid")) {
                    sql = "providerid = '" + matchInformation[index] + "'";
                    break;
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
                if (m.equals("providerid")) {
                    sql += "providerid = " + matchInformation[index++];
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
    public List<Provider> findAll() throws SQLException {
        String sql = "SELECT * FROM provider;";
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public List<Provider> find(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "SELECT * FROM provider" + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public boolean delete(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "DELETE FROM provider" + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate(sql);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean insert(Provider provider) throws SQLException {
        String sql = "INSERT INTO provider(providerid,providername,provideraddress,phone) VALUES(?,?,?,?);";
        stat = con.prepareStatement(sql);
        stat.setString(1, provider.getProviderID());
        stat.setString(2, provider.getProviderName());
        stat.setString(3, provider.getProviderAddress());
        stat.setString(4, provider.getPhone());
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(String[] memberName, String[] updateInformation, String key) throws SQLException {
        String sql = "UPDATE provider SET " + createSQL(memberName, updateInformation, 0) + " WHERE providerid = '" + key + "';";;
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }


}
