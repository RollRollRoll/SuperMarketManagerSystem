package team.smms.proxy.operation;

import team.smms.database.factory.FactoryDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kgfan on 2016/12/13.
 */
public class DAOOperation {
    Connection con;

    public DAOOperation(Connection con){
        this.con=con;
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
}
