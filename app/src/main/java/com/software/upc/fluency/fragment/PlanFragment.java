package com.software.upc.fluency.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.activity.AddPlanActivity;
import com.software.upc.fluency.activity.PlanActivity;
import com.software.upc.fluency.activity.PlanMediaActivity;
import com.software.upc.fluency.adapter.PlanAdapter;
import com.software.upc.fluency.model.Plan;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;


public class PlanFragment extends Fragment {

    private List<Plan> mPlan;
    private ListView planList;
    private String userName;
    private Button addPlan;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userName = ((PlanActivity) activity).getuserName();
    }

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

        /*
       * 查询数据
       * */
        //Log.e("myapplication", app.getName());
        BmobQuery<Plan> query = new BmobQuery<Plan>();
        query.order("-createdAt");
        query.addWhereEqualTo("userName", userName);
        //query.addWhereEqualTo("userName",app.getName());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(PlanFragment.this.getContext(),new FindListener<Plan>() {
            @Override
            public void onSuccess(List<Plan> list) {
                mPlan = list;
                Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
                System.out.println("添加数据成功");
                //创建自定义Adapter的对象
                final PlanAdapter adapter = new PlanAdapter(PlanFragment.this.getActivity(),mPlan);
                //将布局添加到ListView中
                planList.setAdapter(adapter);
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                System.out.println("添加数据失败");
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
                        plan.delete(PlanFragment.this.getContext(), new DeleteListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //TODO 列表刷新
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
//                PlanAdapter adapter = new PlanAdapter(PlanFragment.this.getActivity(),mPlan);
//                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return view;
    }

}
