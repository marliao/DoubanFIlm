package com.marliao.doubanfilm.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.marliao.doubanfilm.R;
import com.marliao.doubanfilm.Utils.GeneraJson;
import com.marliao.doubanfilm.Utils.HttpUtil;
import com.marliao.doubanfilm.Utils.ResolveJson;
import com.marliao.doubanfilm.engine.MyApplication;
import com.marliao.doubanfilm.vo.Douban;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final int NODATA = 100;
    private static final int HAVEDATA = 101;
    private EditText etScreeningCity;
    private ImageView ivSearch;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HAVEDATA:
                    startActivity(new Intent(MainActivity.this, MoveListActivity.class));
                    break;
                case NODATA:
                    showDIalogToTellUser();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void showDIalogToTellUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("小贴士");
        builder.setMessage("很抱歉，你查询的城市暂无结果，请稍后再试！");
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        etScreeningCity = (EditText) findViewById(R.id.et_screening_city);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = etScreeningCity.getText().toString().trim();
                if (cityName != null && !TextUtils.isEmpty(cityName)) {
                    String regular = "[\u4e00-\u9fa5]{2,10}";
                    Pattern pattern = Pattern.compile(regular);
                    if (pattern.matcher(cityName).matches()) {
                        getHttpData(cityName);
                    } else {
                        MyApplication.showToast("请输入正确的城市名！");
                    }
                } else {
                    MyApplication.showToast("城市不能为空！");
                }
            }
        });
    }

    private void getHttpData(final String cityName) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String generahttp = GeneraJson.generahttp(cityName);
                    String httpResult = HttpUtil.doGet(generahttp);
                    Douban douban = ResolveJson.resolveJson(httpResult);
                    if (douban == null) {
                        Message msg = Message.obtain();
                        msg.what = NODATA;
                        mHandler.sendMessage(msg);
                    }
                    MyApplication.setDouban(douban);
                    Message msg = Message.obtain();
                    msg.what = HAVEDATA;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }
}
