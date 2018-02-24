package ivring.ormlitetest.bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * $desc$
 * Created by IVRING on 2017/6/22.
 */
@DatabaseTable(tableName = "student")
public class Student {
    @DatabaseField(columnName = "_id",generatedId = true)
    private long _id;
    @DatabaseField(columnName = "age",dataType = DataType.INTEGER)
    private int age;
    @DatabaseField(columnName = "name",dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "sex",dataType = DataType.STRING)
    private String sex;

    public Student() {
    }

    public Student(int age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (_id != student._id) return false;
        if (age != student.age) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        return sex != null ? sex.equals(student.sex) : student.sex == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (_id ^ (_id >>> 32));
        result = 31 * result + age;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }
}
