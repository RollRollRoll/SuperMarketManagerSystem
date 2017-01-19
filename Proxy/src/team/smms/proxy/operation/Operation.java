package team.smms.proxy.operation;

import team.smms.database.dao.AccountDAO;
import team.smms.database.databaseconnection.DataBaseConnection;
import team.smms.database.factory.FactoryDAO;
import team.smms.database.tableclass.*;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by admin on 2016/7/25.
 */
public class Operation {

    Connection con;
    MySQLUser user;

    public Connection getCon() {
        return con;
    }

    public Operation(String accountName) throws ClassNotFoundException, SQLException {
        if (accountName.equals("root")) {
            con = new DataBaseConnection("root", "123456").getConnection();
        }
    }

    public Operation(String user, String password) {
        try {
            con = new DataBaseConnection(user, password).getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUser() throws SQLException {
        String name = con.getMetaData().getUserName();
        return name;
    }

    public String getUserNmae() throws SQLException {
        String[] name = con.getMetaData().getUserName().split("@");
        return name[0];
    }

    public String buildKey(String tableName) {
        Calendar c = new GregorianCalendar();
        String key = "" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DATE) + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND);
        if (tableName.equals("Stock")) {
            key = '1' + key;
        } else if (tableName.equals("Commodity")) {
            key = "2" + key;
        } else if (tableName.equals("Provider")) {
            key = "3" + key;
        } else if (tableName.equals("Sale")) {
            key = "4" + key;
        } else if (tableName.equals("ReturnSale")) {
            key = "5" + key;
        } else if (tableName.equals("ReturnCommodity")) {
            key = "6" + key;
        } else if (tableName.equals("Storage")) {
            key = "7" + key;
        }
        return key;
    }

    public <E> boolean add(String tableName, E o) throws SQLException, ClassNotFoundException {
        boolean flag = FactoryDAO.getInstance(tableName, this.con).insert(o);
        return flag;
    }

    public boolean delete(String tableName, String[] memberName, String[] matchInfromation) throws SQLException, ClassNotFoundException {
        boolean flag = FactoryDAO.getInstance(tableName, this.con).delete(memberName, matchInfromation);
        return flag;
    }

    public boolean change(String tableName, String memberName[], String updateInformation[], String key) throws SQLException, ClassNotFoundException {
        boolean flag = FactoryDAO.getInstance(tableName, this.con).update(memberName, updateInformation, key);
        return flag;
    }

    public <E> List<E> search(String tableName, String[] memberName, String[] matchInfromation) throws SQLException, ClassNotFoundException {
        List<E> list = FactoryDAO.getInstance(tableName, this.con).find(memberName, matchInfromation);
        return list;
    }

    public <E> List<E> searchAll(String tableName) throws SQLException, ClassNotFoundException {
        List<E> list = FactoryDAO.getInstance(tableName, this.con).findAll();
        return list;
    }

    //Account
    public DefaultTableModel searchAccount(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<Account> list = FactoryDAO.getInstance("Account", this.con).find((String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<Account> listIterator = list.listIterator();
        String[] columnNames = {"用户ID", "登录密码", "用户姓名", "手机号码", "固定电话", "电子邮件", "登录类型", "登录状态", "登录错误时间", "登录错误次数"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Account temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getManagerID(), temp.getAccountPassword(), temp.getManagerName(), temp.getMobilePhone(), temp.getPhone(), temp.getMangerEmail(), temp.getAccountType(), temp.getAccountState(), temp.getTimeOfError(), temp.getCountOfError()});
        }
        return tableModel;
    }

    public boolean addAccount(String[] memberName, String[] matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        Account o = new Account();
        o.setManagerID(matchInformation[0]);
        o.setAccountPassword(matchInformation[1]);
        o.setManagerName(matchInformation[2]);
        o.setMobilePhone(matchInformation[3]);
        o.setPhone(matchInformation[4]);
        o.setMangerEmail(matchInformation[5]);
        o.setAccountType(Integer.parseInt(matchInformation[6]));
        o.setAccountState(Integer.parseInt(matchInformation[7]));
        o.setTimeOfError(matchInformation[8]);
        o.setCountOfError(Integer.parseInt(matchInformation[9]));
        flag = FactoryDAO.getInstance("Account", this.con).insert(o);
        return flag;
    }

    public boolean deleteAccount(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.delete("Account", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        return flag;
    }

    public boolean changeAccount(String[] memberName, String[] updateInformation, String key) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.change("Account", memberName, updateInformation, key);
        return flag;
    }

    public String getMaxManagerID() throws ClassNotFoundException, SQLException {
        AccountDAO accountDAO = (AccountDAO) FactoryDAO.getInstance("Account", this.con);
        return accountDAO.getMaxManagerID();
    }

    public void changeAuthority(int flag, MySQLUser user) throws SQLException {
        String sql = "";
        PreparedStatement stat;
        if (flag == 1) {
            sql = "drop user if exists '" + user.getUserName() + "'@'localhost';";
            stat = con.prepareStatement(sql);
            stat.executeUpdate();
        } else {
            sql = "create user if  not exists '" + user.getUserName() + "'@'localhost' identified by '" + user.getPassword() + "';";
            stat = con.prepareStatement(sql);
            stat.executeUpdate();
            int type = 4;
            if ((type = user.getAccountType()) == 1 || type == 2) {
                sql = "grant select,insert,update,delete on supermarket.* to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
            } else if ((type = user.getAccountType()) == 3) {
                sql = "grant select on supermarket.commodity to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select on supermarket.provider to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select on supermarket.`storage` to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select,insert,update,delete on supermarket.returnsale to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select,insert,update,delete on supermarket.sale to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
            } else if ((type = user.getAccountType()) == 4) {
                sql = "grant select on supermarket.sale to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select on supermarket.returnsale to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select,insert,update,delete on supermarket.commodity to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select,insert,update,delete on supermarket.provider to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
                sql = "grant select,insert,update,delete on supermarket.`storage` to '" + user.getUserName() + "'@'localhost';";
                stat = con.prepareStatement(sql);
                stat.executeUpdate();
            }
        }
    }

    //Commodity
    public DefaultTableModel searchCommodity(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<Commodity> list = this.<Commodity>search("Commodity", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<Commodity> listIterator = list.listIterator();
        String[] columnNames = {"商品批号", "商品名称", "库存编号", "生产日期", "有效期", "单位", "供应商编号"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Commodity temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getCommodityID(), temp.getCommodityName(), temp.getStockID(), temp.getProduceDate(), temp.getStorageTime(), temp.getUnit(), temp.getProviderID()});
        }
        return tableModel;
    }

    public DefaultTableModel searchAllCommodity() throws SQLException, ClassNotFoundException {
        List<Commodity> list = this.<Commodity>searchAll("Commodity");
        ListIterator<Commodity> listIterator = list.listIterator();
        String[] columnNames = {"商品批号", "商品名称", "库存编号", "生产日期", "有效期", "单位", "供应商编号"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Commodity temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getCommodityID(), temp.getCommodityName(), temp.getStockID(), temp.getProduceDate(), temp.getStorageTime(), temp.getUnit(), temp.getProviderID()});
        }
        return tableModel;
    }

    public InputStream searchPic(String commodityID) {
        List<Commodity> list = new ArrayList<Commodity>(1);
        try {
            list = this.<Commodity>search("Commodity", new String[]{"commodityid"}, new String[]{commodityID});
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list.get(0).getPic();

    }

    public boolean addCommodity(String[] memberName, String[] matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        Commodity o = new Commodity();
        o.setCommodityID(matchInformation[0]);
        o.setCommodityName(matchInformation[1]);
        o.setStockID(matchInformation[2]);
        o.setProduceDate(matchInformation[3]);
        o.setStorageTime(Integer.parseInt(matchInformation[4]));
        o.setUnit(matchInformation[5]);
        o.setProviderID(matchInformation[6]);
        try {
            o.setPic(new FileInputStream(new File(matchInformation[7])));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            o.setPic(null);
        }
        flag = this.<Commodity>add("Commodity", o);
        return flag;
    }

    public boolean deleteCommodity(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.delete("Commodity", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        return flag;
    }

    public boolean changeCommodity(String memberName[], String updateInformation[], String key) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.change("Commodity", memberName, updateInformation, key);
        return flag;
    }

    //Provider
    public DefaultTableModel searchProvider(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<Provider> list = this.<Provider>search("Provider", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<Provider> listIterator = list.listIterator();
        String[] columnNames = {"供应商编号", "供应商名称", "供应商地址", "供应商电话"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Provider temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getProviderID(), temp.getProviderName(), temp.getProviderAddress(), temp.getPhone()});
        }
        return tableModel;
    }

    public DefaultTableModel searchAllProvider() throws SQLException, ClassNotFoundException {
        List<Provider> list = this.<Provider>searchAll("Provider");
        ListIterator<Provider> listIterator = list.listIterator();
        String[] columnNames = {"供应商编号", "供应商名称", "供应商地址", "供应商电话"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Provider temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getProviderID(), temp.getProviderName(), temp.getProviderAddress(), temp.getPhone()});
        }
        return tableModel;
    }

    public boolean addProvider(String[] memberName, String[] matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        Provider o = new Provider();
        o.setProviderID(matchInformation[0]);
        o.setProviderName(matchInformation[1]);
        o.setProviderAddress(matchInformation[2]);
        o.setPhone(matchInformation[3]);
        flag = this.<Provider>add("Provider", o);
        return flag;
    }

    public boolean deleteProvider(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.delete("Provider", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        return flag;
    }

    public boolean changeProvider(String[] memberName, String[] updateInformation, String key) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.change("Provider", memberName, updateInformation, key);
        return flag;
    }


    //ReturnSale
    public DefaultTableModel searchReturnSale(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<ReturnSale> list = this.<ReturnSale>search("ReturnSale", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<ReturnSale> listIterator = list.listIterator();
        String[] columnNames = {"退货票号", "商品批号", "商品数量", "金额", "退货时间", "操作员"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            ReturnSale temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getRsaleID(), temp.getCommodityID(), temp.getNumber(), temp.getMoney(), temp.getReturnTime(), temp.getOperator()});
        }
        return tableModel;
    }

    public DefaultTableModel searchAllReturnSale() throws SQLException, ClassNotFoundException {
        List<ReturnSale> list = this.<ReturnSale>searchAll("ReturnSale");
        ListIterator<ReturnSale> listIterator = list.listIterator();
        String[] columnNames = {"退货票号", "商品编号", "商品数量", "金额", "退货时间", "操作员"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            ReturnSale temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getRsaleID(), temp.getCommodityID(), temp.getNumber(), temp.getMoney(), temp.getReturnTime(), temp.getOperator()});
        }
        return tableModel;
    }

    public boolean addReturnSale(String[] memberName, String[] matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        ReturnSale o = new ReturnSale();
        o.setRsaleID(matchInformation[0]);
        o.setCommodityID(matchInformation[1]);
        o.setNumber(Integer.parseInt(matchInformation[2]));
        o.setMoney(Float.parseFloat(matchInformation[3]));
        o.setReturnTime(matchInformation[4]);
        o.setOperator(matchInformation[5]);
        flag = this.<ReturnSale>add("ReturnSale", o);
        return flag;
    }

    public boolean deleteReturnSale(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.delete("ReturnSale", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        return flag;
    }

    public boolean changeReturnSale(String[] memberName, String[] updateInformation, String key) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.change("ReturnSale", memberName, updateInformation, key);
        return flag;
    }

    //Sale
    public DefaultTableModel searchSale(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<Sale> list = this.<Sale>search("Sale", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<Sale> listIterator = list.listIterator();
        String[] columnNames = {"销售票号", "商品批号", "销售日期", "销售数量", "收入", "销售员"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Sale temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getSaleId(), temp.getCommodityId(), temp.getSellTime(), temp.getNumber(), temp.getMoney(), temp.getOperator()});
        }
        return tableModel;

    }

    public DefaultTableModel searchAllSale() throws SQLException, ClassNotFoundException {
        List<Sale> list = this.<Sale>searchAll("Sale");
        ListIterator<Sale> listIterator = list.listIterator();
        String[] columnNames = {"销售票号", "商品批号", "销售日期", "销售数量", "收入", "销售员"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Sale temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getSaleId(), temp.getCommodityId(), temp.getSellTime(), temp.getNumber(), temp.getMoney(), temp.getOperator()});
        }
        return tableModel;
    }

    public boolean addSale(String[] memberName, String[] matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        Sale o = new Sale();
        o.setSaleId(matchInformation[0]);
        o.setCommodityId(matchInformation[1]);
        o.setSellTime(matchInformation[2]);
        o.setNumber(Integer.parseInt(matchInformation[3]));
        o.setMoney(Float.parseFloat(matchInformation[4]));
        o.setOperator(matchInformation[5]);
        flag = this.<Sale>add("Sale", o);
        return flag;
    }

    public boolean deleteSale(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.delete("Sale", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        return flag;
    }

    public boolean changeSale(String[] memberName, String[] updateInformation, String key) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.change("Sale", memberName, updateInformation, key);
        return flag;
    }

    //Storage
    public DefaultTableModel searchStorage(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<Storage> list = this.<Storage>search("Storage", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<Storage> listIterator = list.listIterator();
        String[] columnNames = {"入库编号", "商品批号", "数量", "进货价格", "入库时间", "操作员"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Storage temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getStorageID(), temp.getCommodityID(), temp.getNumber(), temp.getInPrice(), temp.getInDate(), temp.getOperator()});
        }
        return tableModel;
    }

    public DefaultTableModel searchAllStorage() throws SQLException, ClassNotFoundException {
        List<Storage> list = this.<Storage>searchAll("Storage");
        ListIterator<Storage> listIterator = list.listIterator();
        String[] columnNames = {"入库编号", "商品批号", "数量", "进货价格", "入库时间", "操作员"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Storage temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getStorageID(), temp.getCommodityID(), temp.getNumber(), temp.getInPrice(), temp.getInDate(), temp.getOperator()});
        }
        return tableModel;
    }

    public boolean addStorage(String[] memberName, String[] matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        Storage o = new Storage();
        o.setStorageID(matchInformation[0]);
        o.setCommodityID(matchInformation[1]);
        o.setNumber(Integer.parseInt(matchInformation[2]));
        o.setInPrice(Float.parseFloat(matchInformation[3]));
        o.setInDate(matchInformation[4]);
        o.setOperator(matchInformation[5]);
        flag = this.<Storage>add("Storage", o);
        return flag;
    }

    public boolean deleteStorage(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.delete("Storage", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        return flag;
    }

    public boolean changeStorage(String[] memberName, String[] updateInformation, String key) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.change("Storage", memberName, updateInformation, key);
        return flag;
    }

    //Stock
    public DefaultTableModel searchStock(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<Stock> list = this.<Stock>search("Stock", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<Stock> listIterator = list.listIterator();
        String[] columnNames = {"库存编号", "商品名称", "销售价格", "数量", "数量下限"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Stock temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getStockID(), temp.getCommodityName(), temp.getSaleprice(), temp.getNumber(), temp.getDownLimit()});
        }
        return tableModel;
    }

    public DefaultTableModel searchAllStock() throws SQLException, ClassNotFoundException {
        List<Stock> list = this.<Stock>searchAll("Stock");
        ListIterator<Stock> listIterator = list.listIterator();
        String[] columnNames = {"库存编号", "商品名称", "销售价格", "数量", "数量下限"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Stock temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getStockID(), temp.getCommodityName(), temp.getSaleprice(), temp.getNumber(), temp.getDownLimit()});
        }
        return tableModel;
    }

    public boolean addStock(String[] memberName, String[] matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        Stock o = new Stock();
        o.setStockID(matchInformation[0]);
        o.setCommodityName(matchInformation[1]);
        o.setSaleprice(Float.parseFloat(matchInformation[2]));
        o.setNumber(Integer.parseInt(matchInformation[3]));
        o.setDownLimit(Integer.parseInt(matchInformation[4]));
        flag = this.<Stock>add("Stock", o);
        return flag;
    }

    public boolean changeStock(String[] memberName, String[] updateInformation, String key) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.change("Stock", memberName, updateInformation, key);
        return flag;
    }

    public boolean deleteStock(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        flag = this.delete("Stock", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        return flag;
    }

    //Achievements
    public DefaultTableModel searchAchievements(ArrayList<String> memberName, ArrayList<String> matchInformation) throws SQLException, ClassNotFoundException {
        List<Account> list = this.<Account>search("Account", (String[]) memberName.toArray(new String[memberName.size()]), (String[]) matchInformation.toArray(new String[matchInformation.size()]));
        ListIterator<Account> listIterator = list.listIterator();
        String[] columnNames = {"员工编号", "员工姓名", "总收入", "总利润", "手机号"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Account temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getManagerID(), temp.getManagerName(), temp.getEarning(), temp.getProfit(), temp.getMobilePhone()});
        }
        return tableModel;
    }

    public DefaultTableModel searchAllAchievements() throws SQLException, ClassNotFoundException {
        List<Account> list = this.<Account>searchAll("Account");
        ListIterator<Account> listIterator = list.listIterator();
        String[] columnNames = {"员工编号", "员工姓名", "总收入", "总利润", "销售数量", "手机号"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (listIterator.hasNext()) {
            Account temp = listIterator.next();
            tableModel.addRow(new Object[]{temp.getManagerID(), temp.getManagerName(), temp.getEarning(), temp.getProfit(), temp.getMobilePhone()});
        }
        return tableModel;
    }

    public String[] getAllStockID() throws SQLException, ClassNotFoundException {
        List<Stock> list = this.<Stock>searchAll("Stock");
        ListIterator<Stock> listIterator = list.listIterator();
        String[] id = new String[list.size()];
        for(int i=0;i<list.size();i++){
            if (listIterator.hasNext()) {
                id[i] = new String(listIterator.next().getStockID());
            }
        }
        return id;
    }

    public String[] getAllCommodityID() throws SQLException, ClassNotFoundException {
        List<Commodity> list = this.<Commodity>searchAll("Commodity");
        ListIterator<Commodity> listIterator = list.listIterator();
        String[] id = new String[list.size()];
        for (int i=0;i<list.size();i++) {
            if (listIterator.hasNext()) {
                id[i] = new String(listIterator.next().getCommodityID());
            }
        }
        return id;
    }


    public String[] getAllProviderID() throws SQLException, ClassNotFoundException {
        List<Provider> list = this.<Provider>searchAll("Provider");
        ListIterator<Provider> listIterator = list.listIterator();
        String[] id = new String[list.size()];
        for(int i=0;i<list.size();i++){
            if (listIterator.hasNext()) {
                id[i] = new String(listIterator.next().getProviderID());
            }
        }
        return id;
    }
}

