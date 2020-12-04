package com.test.recoder;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.recoder.permission.Permissions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;


public class MainActivity extends Activity {
    private String  TAG ="MainActivity";
    static {
        System.loadLibrary("recoder-lib");
    }

    TextView textView;
    TextView textpath;
    TextView texttime;
    Button recoderBtn;
    Button palyBtn;
    Button btsettime;
    EditText edtime;

    private volatile boolean mIsRecording;
    private volatile boolean mIsPalying;

    String filePath;
    boolean isAvailable = true;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        textView = findViewById(R.id.textViewStream);
        textpath = findViewById(R.id.textpath);
        texttime = findViewById(R.id.texttime);
        recoderBtn = findViewById(R.id.buttonStream);
        palyBtn = findViewById(R.id.button4);
        btsettime = findViewById(R.id.btsettime);
        edtime = findViewById(R.id.edtime);

        Permissions.requestPermissionAll(this);
        Log.i(TAG, "getlongtime = "+ getlongtime("2020-12-02 15:10:18"));
        Log.i(TAG, "time = "+ gettime(getSetTime()));
        texttime.setText(gettime(getSetTime()));
    }
    /**
     * 设置有效时间
     */
    public void settime(View view){
       String avatime = edtime.getText().toString();
        int result = setAvailableTime(getlongtime(avatime));
        isAvailable = true;
        texttime.setText(gettime(getSetTime()));
        Log.i(TAG, "btsettime = "+ result);
    }

    /**
     * 开始按钮 点击开始录音
     */
    public void start(View view) {
        if(!isAvailable){
            return;
        }
        if (mIsRecording) { //
            mIsRecording = false;
            recoderBtn.setText("开始录音");
            textView.setText(textView.getText() + "\n录音成功");
            stopRecod();
        } else {
            textView.setText(textView.getText() + "\n开始录音。。。");
            recoderBtn.setText("结束录音");
            mIsRecording = true;
           filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecorderTest/" +
                   System.currentTimeMillis() + ".pcm";

            Log.i(TAG,"filepath = "+filePath);
            textpath.setText("路径："+filePath);
            File mAudioFile = new File(filePath);
            if (!mAudioFile.getParentFile().exists()) {
               boolean ismkdir = mAudioFile.getParentFile().mkdirs();
               if(ismkdir){
                   Log.e("TAG", "文件夹创建 成功");
               }else {
                   Log.e("TAG", "文件夹创建 失败");
               }
                Log.i(TAG,"mkdirs");
            }
            try {
                mAudioFile.createNewFile();
               int state = record(filePath);
               if(state == -1){
                   isAvailable = false;
                   mIsRecording = false;
                   textView.setText(textView.getText() + "\n录音失败");
                   recoderBtn.setText("so库已失效");
                   textpath.setText("路径:");
                   if (mAudioFile.exists()) {
                       mAudioFile.delete();
                       Log.i(TAG,"delete mkdirs");
                   }
               }else{
                   isAvailable = true;
                   mIsRecording = true;
               }
                Log.i(TAG,"current time  = "+System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void streamPlay(View view) {
        if(!isAvailable){
            palyBtn.setText("so库已失效");
            return;
        }
        if (!mIsPalying) {
            textView.setText(textView.getText() + "\n开始播放。。。");
            palyBtn.setText("结束播放");
            mIsPalying = true;
            play(filePath);
        } else {
            mIsPalying = false;
            palyBtn.setText("播放");
            textView.setText(textView.getText() + "\n播放结束");
            playStop();
        }
    }

    /**
     * 获取当前日期
     * @return
     */
    public String  gettime(long longtime){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(longtime);//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
    /**
     * 转换指定日期为毫秒级
     * @return
     */
    public long getlongtime (String source ){ //"2016-09-02 23:02:17"
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if(source == null){
                return System.currentTimeMillis();
            }
            long time = dateformat.parse(source).getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playStop();
        stopRecod();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Permissions.changePermissionState(this,permissions[0],true);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //native interface
    public native int play(String filePath);

    public native int playStop();

    public native int record(String filePath);

    public native int stopRecod();

    public native int setAvailableTime(long avatime);

    public native long getSetTime();
}
