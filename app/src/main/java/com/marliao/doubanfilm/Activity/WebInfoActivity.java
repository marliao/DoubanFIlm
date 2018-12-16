package com.marliao.doubanfilm.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.marliao.doubanfilm.R;
import com.marliao.doubanfilm.engine.MyApplication;

import java.util.List;

public class WebInfoActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvBack;
    private WebView webMoveInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_info);
        initUI();
        initView();
        String url = MyApplication.getUrl();
        webMoveInfo.loadUrl(url);
    }

    private void initView() {
        webMoveInfo.setWebChromeClient(new WebChromeClient());
        webMoveInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return openApp(url);
            }

            private boolean openApp(String url) {
                if (TextUtils.isEmpty(url)) return false;
                try {
                    if (!url.startsWith("http") || !url.startsWith("https") || !url.startsWith("ftp")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        List<ResolveInfo> resolveInfos = getApplication().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
                        if (resolveInfos.size() > 0) {
                            startActivity(intent);
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return false;
            }
        });
        WebSettings settings = webMoveInfo.getSettings();
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webMoveInfo.setWebContentsDebuggingEnabled(true);
        }
    }

    private void initUI() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(MyApplication.getTitle());
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WebInfoActivity.this, MoveListActivity.class));
                finish();
            }
        });
        webMoveInfo = (WebView) findViewById(R.id.web_move_info);
    }
}
