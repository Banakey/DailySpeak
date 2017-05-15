package com.software.upc.fluency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.Plan;

import java.util.List;

/**
 * Created by jmy on 2017/5/15.
 */

public class PlanAdapter extends BaseAdapter {

    //定义数据
    private List<Plan> mPlan;
    //定义Inflater,加载自定义的布局
    private LayoutInflater mInflater;
    private Context mContext;
    public PlanAdapter(Context context){
        mContext = context;
    }
    public PlanAdapter(Context context,List<Plan> list)
    {
        mContext = context;
        this.mPlan = list;
    }

    @Override
    public int getCount() {
        return mPlan.size();
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
            convertView=View.inflate(mContext, R.layout.list_plan,null);
            holder.planTitle=(TextView)convertView.findViewById(R.id.plan_title);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.planTitle.setText(mPlan.get(position).getStudyTheme());
        return convertView;
    }
    static class Holder {
        TextView planTitle;
        //TextView studyText;
    }
}
