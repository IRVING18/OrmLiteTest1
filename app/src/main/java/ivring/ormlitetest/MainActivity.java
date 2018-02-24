package ivring.ormlitetest;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ivring.ormlitetest.adapter.ListViewAdapter;
import ivring.ormlitetest.bean.Student;
import ivring.ormlitetest.helper.DaoUtils;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView listview;
    private Context context = this ;
    private List<Student> list_student;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initListView();
    }

    private void initListView() {
        adapter = new ListViewAdapter(context, list_student);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setDialog("修改",id);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                setDeleteAlert(position);
                try {
                    List<Student> list = DaoUtils.getInstance(context).queryAll();

                    adapter.refreshListView(list,true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    private void setDeleteAlert(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            boolean delete = DaoUtils.getInstance(context).delete(list_student.get(position));
                            if (delete){
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    private void initData() {
        try {
            list_student = new ArrayList<>();
            List<Student> students = DaoUtils.getInstance(context).queryAll();
            if (students!=null) {
                list_student.addAll(students);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tools,menu);
//        获取searchView
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insert:
                setDialog("添加", 0L);
                break;
        }
        return true;
    }

    private void setDialog(final String str, final Long id) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_view,null);
        final EditText et_name= (EditText) view.findViewById(R.id.et_name);
        final EditText et_sex= (EditText) view.findViewById(R.id.et_sex);
        final EditText et_age= (EditText) view.findViewById(R.id.et_age);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str+"数据")
                .setView(view)
                .setPositiveButton(str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //动态的获取姓名 性别 年龄
                        String name=et_name.getText().toString().trim();
                        String sex=et_sex.getText().toString().trim();
                        String age=et_age.getText().toString().trim();
                        //判断字符串是否为空字符串
                        if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(sex)&&!TextUtils.isEmpty(age)){
                            //创建一个Student对象，赋值。
                            Student student = new Student(Integer.parseInt(age), name, sex);
                            //将数据以Student对象的形式存入进数据库中
                            try {
                                boolean flag =false;
                                switch (str){
                                    case "添加":
                                        //返回值是判断插入成功还是失败
                                        flag = DaoUtils.getInstance(context).add(student);
                                        break;
                                    case "修改":
                                        //返回值是判断插入成功还是失败
                                        student.set_id(id);
                                        flag = DaoUtils.getInstance(context).update(student);
                                        break;
                                }

                                if (flag){
                                    Toast.makeText(MainActivity.this, str+"成功", Toast.LENGTH_SHORT).show();
                                    //并刷新listView显示
                                    list_student = DaoUtils.getInstance(context).queryAll();
                                    //刷新ListView
                                    adapter.refreshListView(list_student,true);
                                }else {
                                    Toast.makeText(MainActivity.this, str+"失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }
}
