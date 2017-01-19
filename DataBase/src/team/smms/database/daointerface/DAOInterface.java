package team.smms.database.daointerface;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 2016/7/22.
 */

public interface DAOInterface<E> {

    public abstract List<E> findAll() throws SQLException;

    public abstract List<E> find(String[] memberName, String[] matchInformation) throws SQLException;

    public abstract boolean delete(String[] memberName, String[] matchInformation) throws SQLException;

    public abstract boolean insert(E e) throws SQLException;

    public abstract boolean update(String[] memberName,String[] updateInformation,String key) throws SQLException;


}
