package com.software.upc.fluency.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.StudyItem;

public class StudyActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        text=(TextView)findViewById(R.id.study_text);
        Bundle b=getIntent().getExtras();
        StudyItem studyItem =(StudyItem) b.getSerializable("study");
        text.setText(studyItem.getStudyText());
        // TODO mp3播放
    }
}
