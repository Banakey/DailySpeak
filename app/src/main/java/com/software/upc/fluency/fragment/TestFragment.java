package com.software.upc.fluency.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.software.upc.fluency.R;
import com.software.upc.fluency.activity.TestActivity;


public class TestFragment extends Fragment {
    private Button tTestButton;
    private Button vTestButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        tTestButton = (Button)view.findViewById(R.id.btn_texttest);
        tTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TestActivity.class);
                startActivity(intent);
            }
        });
        vTestButton = (Button)view.findViewById(R.id.btn_voicetest);
        return view;
    }
}
