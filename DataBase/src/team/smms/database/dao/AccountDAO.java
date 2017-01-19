package team.smms.database.dao;

import team.smms.database.daointerface.DAOInterface;
import team.smms.database.tableclass.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAOInterface<Account> {
    private Connection con = null;
    private PreparedStatement stat = null;

    public AccountDAO(Connection con) {
        this.con = con;
    }

    private List<Account> createList(ResultSet rs) throws SQLException {
        List<Account> list = new ArrayList<Account>();
        while (rs.next()) {
            String managerID = rs.getString(1);
            String accountPassword = rs.getString(2);
            String managerName = rs.getString(3);
            String mobilePhone = rs.getString(4);
            String phone = rs.getString(5);
            String mangerEmail = rs.getString(6);
            int accountType = rs.getInt(7);
            int accountState = rs.getInt(8);
            String timeOfError = rs.getString(9);
            int countOfError = rs.getInt(10);
            float earning = rs.getFloat(11);
            float profit = rs.getFloat(12);


            Account temp = new Account();
            temp.setManagerID(managerID);
            temp.setAccountPassword(accountPassword);
            temp.setManagerName(managerName);
            temp.setMobilePhone(mobilePhone);
            temp.setPhone(phone);
            temp.setMangerEmail(mangerEmail);
            temp.setAccountType(accountType);
            temp.setAccountState(accountState);
            temp.setTimeOfError(timeOfError);
            temp.setCountOfError(countOfError);
            temp.setEarning(earning);
            temp.setProfit(profit);
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
                if (m.equals("managerID") && !matchInformation[index].equals("")) {
                    sql = "managerID = '" + matchInformation[index] + "'";
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
                if (m.equals("managerName")) {
                    sql += "managerName = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("mobilePhone")) {
                    sql += "mobilePhone = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("phone")) {
                    sql += "phone = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("managerEmail")) {
                    sql += "managerEmail = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("managerID")) {
                    sql += "managerID = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("accountPassword")) {
                    sql += "accountPassword = '" + matchInformation[index++] + "'";
                    if (index != lengthOfMatchInformation) {
                        sql += " , ";
                    }
                } else if (m.equals("timeOfError")) {
                    sql += "timeoferror = '" + matchInformation[index++] + "'";
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
    public List<Account> findAll() throws SQLException {
        String sql = "SELECT * FROM Account;";
        stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public List<Account> find(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "SELECT * FROM Account" + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        System.out.println(sql);
        ResultSet rs = stat.executeQuery();
        return createList(rs);
    }

    @Override
    public boolean insert(Account account) throws SQLException {
        String sql = "INSERT INTO Account(managerID,accountPassword,managerName,mobilePhone,phone,managerEmail,accountType,accountState,timeOfError,countOfError,earning,profit) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        stat = con.prepareStatement(sql);
        stat.setString(1, account.getManagerID());
        stat.setString(2, account.getAccountPassword());
        stat.setString(3, account.getManagerName());
        stat.setString(4, account.getMobilePhone());
        stat.setString(5, account.getPhone());
        stat.setString(6, account.getMangerEmail());
        stat.setInt(7, account.getAccountType());
        stat.setInt(8, account.getAccountState());
        stat.setString(9, account.getTimeOfError());
        stat.setInt(10, account.getCountOfError());
        stat.setFloat(11,account.getEarning());
        stat.setFloat(12,account.getProfit());
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String[] memberName, String[] matchInformation) throws SQLException {
        String sql = "DELETE FROM Account" + createSQL(memberName, matchInformation, 1);
        stat = con.prepareStatement(sql);
        System.out.println(sql);
        int flag = stat.executeUpdate(sql);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(String[] memberName, String[] updateInformation, String key) throws SQLException {
        String sql = "UPDATE Account SET " + createSQL(memberName, updateInformation, 0) + " WHERE managerID = '"
                + key + "';";
        System.out.println(sql);
        stat = con.prepareStatement(sql);
        int flag = stat.executeUpdate();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getMaxManagerID() throws SQLException {
        String sql = "select max(managerID) from account;";
        stat = con.prepareStatement(sql);
        System.out.println(sql);
        ResultSet rs = stat.executeQuery();
        rs.next();
        return rs.getString(1);
    }
}
