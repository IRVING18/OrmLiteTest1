package ivring.ormlitetest.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ivring.ormlitetest.bean.Student;

/**
 * $desc$
 * Created by IVRING on 2017/6/22.
 */

public class OrmHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "ormtest.db";
    private static final int DB_VERSION = 1;
    private static OrmHelper ormHelper;
    private static Dao<Student,Long> dao ;
    public static OrmHelper getInstance(Context context){
        if (ormHelper == null){
            ormHelper = new OrmHelper(context);
        }
        return ormHelper;
    }
    public Dao<Student,Long> getStudentDao() throws SQLException {
        if (dao == null){
            //调用getDao方法，传入实体类的字节码文件对象
            dao = getDao(Student.class);
        }
        return dao;
    }
    private OrmHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource,Student.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            //具体的更新代码
            try {
                //删除原来的表，第三个参数：是否忽略异常
                TableUtils.dropTable(connectionSource,Student.class,true);
                //创建新的表
                TableUtils.createTable(connectionSource, Student.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
