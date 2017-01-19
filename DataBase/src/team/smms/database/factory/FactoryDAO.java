package team.smms.database.factory;

import team.smms.database.dao.*;
import team.smms.database.daointerface.DAOInterface;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by admin on 2016/7/22.
 */

public class FactoryDAO {
    public static DAOInterface getInstance(String o, Connection con) throws ClassNotFoundException, SQLException {
        DAOInterface dao = null;
        if (o.equals("Account")) {
            dao = new AccountDAO(con);
        } else if (o.equals("Commodity")) {
            dao = new CommodityDAO(con);
        } else if (o.equals("Provider")) {
            dao = new ProviderDAO(con);
        } else if (o.equals("Sale")) {
            dao = new SaleDAO(con);
        } else if (o.equals("ReturnSale")) {
            dao = new ReturnSaleDAO(con);
        }  else if (o.equals("Storage")) {
            dao = new StorageDAO(con);
        } else if(o.equals("Stock")){
            dao = new StockDAO(con);
        }
        return dao;
    }
}
