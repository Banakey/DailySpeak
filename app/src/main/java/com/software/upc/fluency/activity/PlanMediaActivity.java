package com.software.upc.fluency.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.Plan;

public class PlanMediaActivity extends AppCompatActivity {
    private TextView text,total_Time,playing_Time;
    private MediaPlayer mpStudy = new MediaPlayer();
    private ImageButton play,pause;
    private SeekBar sbStudy;
    private int totalTime = 0;
    private Handler handler = new Handler();
    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_media);

        text=(TextView)findViewById(R.id.study_text);

        total_Time = (TextView)findViewById(R.id.totalTime);
        playing_Time = (TextView)findViewById(R.id.playingTime);

        play = (ImageButton)findViewById(R.id.md_play);
        play.setOnClickListener(new myOnClickListener());

        pause = (ImageButton)findViewById(R.id.md_pause);
        pause.setOnClickListener(new myOnClickListener());

        sbStudy = (SeekBar)findViewById(R.id.study_progress);
        sbStudy.setOnSeekBarChangeListener(new mySeekBarListener());

        Bundle b=getIntent().getExtras();
        Plan plan =(Plan) b.getSerializable("plan");
        //System.out.print(url);
        text.setText(plan.getStudyText());
        initMediaPlayer();
    }

    public void initMediaPlayer() {
        try {
            mpStudy.reset();
            mpStudy = MediaPlayer.create(PlanMediaActivity.this, R.raw.getup);//设置要播放的音频
            //mpStudy.start();//开始播放
            //mpStudy.setDataSource();
            //mpStudy.prepare();
            setTotalTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class mySeekBarListener implements SeekBar.OnSeekBarChangeListener {
        /*
        * SeekBar滚动时的回调函数
        */
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }
        /*
        * SeekBar开始滚动的回调函数
        */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        /*
        * SeekBar停止滚动的回调函数
        */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mpStudy.seekTo(sbStudy.getProgress()*1000);
            updateTimepos();
        }
    }
    private class myOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.md_play:
                    setPlayMusic();
                    break;
                case R.id.md_pause:
                    setPauseMusic();
                    break;
            }
        }
    }

    public void setPlayMusic() {
        if (!mpStudy.isPlaying()) {
            mpStudy.start();
            flag = true;
            refreshTimepos();
        }
    }

    public void setPauseMusic() {
        if(mpStudy.isPlaying()) {
            mpStudy.pause();
            flag = false;
        }
    }
    /*
* 获取音频总时长
* */
    public void setTotalTime() {
        //这个方法返回歌曲以毫秒为单位的总持续时间
        totalTime = mpStudy.getDuration() / 1000;
        Log.d("MediaPlayerTest", String.valueOf(totalTime));
        String pos = String.valueOf(totalTime/60/10)+String.valueOf(totalTime/60%10)
                +':'+String.valueOf(totalTime%60/10)+String.valueOf(totalTime%60%10);
        total_Time.setText(pos);
        sbStudy.setProgress(0);
        sbStudy.setMax(totalTime);
    }

    public void updateTimepos() {
        int timepos = sbStudy.getProgress()+1;
        if(timepos >= totalTime-1) {
            timepos = 0;
            flag = false;
        }
        sbStudy.setProgress(timepos);
        String pos = String.valueOf(timepos/60/10)+String.valueOf(timepos/60%10)
                +':'+String.valueOf(timepos%60/10)+String.valueOf(timepos%60%10);
        playing_Time.setText(pos);
    }



    private void refreshTimepos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(flag && sbStudy.getProgress()<totalTime-1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateTimepos();
                        }
                    });
                }
            }
        }).start();
    }
    //退出当前Activity时被调用,调用之后Activity就结束了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setPauseMusic();
    }
}
