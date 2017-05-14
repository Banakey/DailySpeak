package com.software.upc.fluency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.StudyItem;

import java.util.List;

/**
 * Created by jmy on 2017/5/5.
 */

public class StudyAdapter extends BaseAdapter {
    //定义数据
    private List<StudyItem> mData;
    //定义Inflater,加载自定义的布局
    private LayoutInflater mInflater;
    private Context mContext;
    public StudyAdapter(Context context){
        mContext = context;
    }
    public StudyAdapter(Context context,List<StudyItem> list)
    {
        mContext = context;
        this.mData = list;
    }

    //返回数据数量
    @Override
    public int getCount() {
        return mData.size();
    }

    //获得每一条ListView中的Item，position是指每条Item在ListView中的位置（0， 1， 2……）
    @Override
    public Object getItem(int position) {
        return position;
    }

    //获得ListView中每条Item的Id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //将数据一一对应的映射或者添加到定义的View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder= new Holder();
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.study_list,null);
            holder.studyTitle=(TextView)convertView.findViewById(R.id.study_title);
            //holder.studyText=(TextView)convertView.findViewById(R.id.study_text);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.studyTitle.setText(mData.get(position).getStudyTheme());
        //holder.studyText.setText(mData.get(position).getStudyText());
        return convertView;
    }
    static class Holder {
        TextView studyTitle;
        //TextView studyText;
    }
}
