package avinash.com.newsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Article extends AppCompatActivity {
        WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        wv=findViewById(R.id.webView);

        wv.getSettings().setJavaScriptEnabled(true);

        wv.setWebViewClient(new WebViewClient());
        Intent intent=getIntent();
        String TAG="web";
        Log.i(TAG, "Working webView");
        wv.loadData(intent.getStringExtra("content"),"text/html","UTF-8");
    }
}


