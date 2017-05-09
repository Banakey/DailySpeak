package com.software.upc.fluency.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.StudyAdapter;
import com.software.upc.fluency.model.StudyItem;

import java.util.List;

import cn.bmob.v3.BmobQuery;
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
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(HomeFragment.this.getContext(),new FindListener<StudyItem>() {
            @Override
            public void onSuccess(List<StudyItem> list) {
                    mData = list;
                    Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
                    System.out.println("添加数据成功");
                    //创建自定义Adapter的对象
                    StudyAdapter adapter = new StudyAdapter(HomeFragment.this.getActivity(),mData);
                    //将布局添加到ListView中
                    stu_list.setAdapter(adapter);
//                }
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                System.out.println("添加数据失败");
                //Toast.makeText(HomeFragment.getActivity(),"",Toast.LENGTH_SHORT).show();
            }
        });
        System.out.print("666666666");
        return view;
    }
}
