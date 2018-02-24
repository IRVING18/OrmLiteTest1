package ivring.ormlitetest.helper;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import ivring.ormlitetest.bean.Student;

/**
 * $desc$
 * Created by IVRING on 2017/6/22.
 */

public class DaoUtils {
    private static DaoUtils daoUtils;
    private OrmHelper ormHelper;
    private Dao<Student,Long> dao;
    private  DaoUtils(Context context) throws SQLException {
        ormHelper = OrmHelper.getInstance(context);
        dao = ormHelper.getStudentDao();
    }
    public static DaoUtils getInstance(Context context) throws SQLException {
        if (daoUtils == null){
            daoUtils = new DaoUtils(context);
        }
        return daoUtils;
    }
    public List<Student> queryAll(){
        List<Student> list_student = null;
        try {
            list_student = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list_student;
    }

    /**
     * @author IVRING
     * @param student
     * @return true 插入成功，false插入失败
     * @throws SQLException
     */
    public boolean add(Student student) throws SQLException {
        int flag = dao.create(student);
        if (flag>0){
            return true;
        }
        return false;
    }

    /**
     * @param student
     * @return 删除是否成功
     * @throws SQLException
     */
    public boolean delete(Student student) throws SQLException {
        int flag = dao.delete(student);
        if (flag>0){
            return true;
        }
        return false;
    }

    /**
     * @param list
     * @return
     * @throws SQLException
     */
    public boolean deleteAll(List<Student> list) throws SQLException {
        int flag = dao.delete(list);
        if (flag>0){
            return true;
        }
        return false;
    }

    /**
     * @param student
     * @return
     * @throws SQLException
     */
    public boolean update(Student student) throws SQLException {
        int update = dao.update(student);
        if (update>0){
            return true;
        }
        return false;
    }
}
