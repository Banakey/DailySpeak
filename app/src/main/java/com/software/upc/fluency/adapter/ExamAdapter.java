package com.software.upc.fluency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.Exam;

import java.util.List;

/**
 * Created by jmy on 2017/5/18.
 */

public class ExamAdapter extends BaseAdapter{
    //定义数据
    private List<Exam> mExam;
    //定义Inflater,加载自定义的布局
    private LayoutInflater mInflater;
    private Context mContext;
    public ExamAdapter(Context context){
        mContext = context;
    }
    public ExamAdapter(Context context,List<Exam> list)
    {
        mContext = context;
        this.mExam = list;
    }

    @Override
    public int getCount() {
        return mExam.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder= new Holder();
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.list_exam,null);
            holder.examTitle=(TextView)convertView.findViewById(R.id.exam_title);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.examTitle.setText(mExam.get(position).getQuestionName());
        return convertView;
    }
    static class Holder {
        TextView examTitle;
        //TextView studyText;
    }
}
