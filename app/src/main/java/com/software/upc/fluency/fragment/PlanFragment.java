package com.software.upc.fluency.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.activity.AddPlanActivity;
import com.software.upc.fluency.activity.PlanMediaActivity;
import com.software.upc.fluency.adapter.PlanAdapter;
import com.software.upc.fluency.model.Plan;
import com.software.upc.fluency.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class PlanFragment extends Fragment {

    private List<Plan> mPlan,handleList;
    private ListView planList;
    private String userName;
    private Button addPlan;
    private PlanAdapter adapter;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        planList = (ListView) view.findViewById(R.id.plan_list);
        addPlan = (Button) view.findViewById(R.id.btn_add_plan);
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddPlanActivity.class);
                startActivity(intent);
            }
        });
        //handler
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        handleList = (List<Plan>) msg.obj;
                        adapter = new PlanAdapter(getActivity(),handleList);
                        planList.setAdapter(adapter);
                }
            }
        };

        /*
       * 查询计划
       * */
        User user = BmobUser.getCurrentUser(User.class);
        userName = user.getUsername();
        BmobQuery<Plan> query = new BmobQuery<Plan>();
        query.order("-createdAt");
        query.addWhereEqualTo("userName", userName);
        //执行查询方法
        query.findObjects(new FindListener<Plan>() {
            @Override
            public void done(List<Plan> list, BmobException e) {
                if(e==null){
                    for (Plan plan : list) {
                        plan.getStudyTheme();
                    }
                    mPlan = list;
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = mPlan;
                    handler.sendMessage(msg);
                    //创建自定义Adapter的对象
                    //adapter = new PlanAdapter(PlanFragment.this.getActivity(),list);
                    //将布局添加到ListView中
                    //planList.setAdapter(adapter);
                    Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        //listview的item点击事件
        planList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO mp3播放
                Plan plan = mPlan.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("plan", plan);
                Intent intent = new Intent(getActivity(),PlanMediaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        planList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int p = position;
                final Plan plan = mPlan.get(position);
                AlertDialog dlgShowBack = new AlertDialog.Builder(getActivity()).create();
                dlgShowBack.setTitle("提示");
                dlgShowBack.setMessage("确定删除此条计划？");

                dlgShowBack.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlgShowBack.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        plan.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","成功");
                                    handleList.remove(p);
                                    Log.i("bmob","handleLlist is +++++"+handleList.toString());
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        //TODO 列表刷
                    }
                });
                dlgShowBack.show();
                Button btnPositive =
                        dlgShowBack.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
                Button btnNegative =
                        dlgShowBack.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
                btnNegative.setTextColor(getResources().getColor(R.color.black));
                btnNegative.setTextSize(18);
                btnPositive.setTextColor(getResources().getColor(R.color.black));
                btnPositive.setTextSize(18);
                return true;
            }
        });

        return view;
    }

}
