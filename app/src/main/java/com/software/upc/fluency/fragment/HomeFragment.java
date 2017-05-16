package com.software.upc.fluency.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.activity.StudyActivity;
import com.software.upc.fluency.adapter.StudyAdapter;
import com.software.upc.fluency.model.StudyItem;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.software.upc.fluency.R.layout.fragment_home;


public class HomeFragment extends Fragment {
    //定义数据
    private List<StudyItem> mData;
    //定义ListView对象
    private ListView stu_list;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_home, container, false);
        //为ListView对象赋值
        stu_list = (ListView) view.findViewById(R.id.study_list);
        /*
        * 查询数据
        * */
        BmobQuery<StudyItem> query = new BmobQuery<StudyItem>();
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(new FindListener<StudyItem>() {
            @Override
            public void done(List<StudyItem> list, BmobException e) {
                if(e==null){
                    for (StudyItem studyItem : list) {
                        studyItem.getStudyText();
                        studyItem.getStudyTheme();
                    }
                    //创建自定义Adapter的对象
                    StudyAdapter adapter = new StudyAdapter(HomeFragment.this.getActivity(),list);
                    mData = list;
                    //将布局添加到ListView中
                    stu_list.setAdapter(adapter);
                    Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        //listview的item点击事件
        stu_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO mp3播放
                StudyItem studyItem = mData.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("study", studyItem);
                Intent intent = new Intent(getActivity(),StudyActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }
}
