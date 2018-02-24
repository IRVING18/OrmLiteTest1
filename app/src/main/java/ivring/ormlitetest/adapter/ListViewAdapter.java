package ivring.ormlitetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ivring.ormlitetest.R;
import ivring.ormlitetest.bean.Student;

/**
 * $desc$
 * Created by IVRING on 2017/6/22.
 */

public class ListViewAdapter extends BaseAdapter{
    private Context context;
    private List<Student> list;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Student student = list.get(position);
        holder.textView_name.setText(student.getName()+"");
        holder.textView_age.setText(student.getAge()+"");
        holder.textView_sex.setText(student.getSex()+"");
        return convertView;
    }
    public void refreshListView(List<Student> li,boolean flag){
        if (flag){
            list.clear();
            list.addAll(li);
            notifyDataSetChanged();
        }else {
            list.addAll(li);
            notifyDataSetChanged();
        }
    }
    static class ViewHolder{
        private ImageView iv;
        private TextView textView_name;
        private TextView textView_sex;
        private TextView textView_age;
        public ViewHolder(View view){
            iv = (ImageView) view.findViewById(R.id.iv);
            textView_name = (TextView) view.findViewById(R.id.textView_name);
            textView_sex = (TextView) view.findViewById(R.id.textView_sex);
            textView_age = (TextView) view.findViewById(R.id.textView_age);
        }
    }
}
