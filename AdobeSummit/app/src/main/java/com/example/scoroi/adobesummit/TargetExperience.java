package com.example.scoroi.adobesummit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;


public class TargetExperience extends Activity {

    private WebView targetExperienceWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_experience);

        targetExperienceWebView = (WebView) findViewById(R.id.targetExperienceWebView);
        //targetExperienceWebView.loadUrl("http://google.com");
        targetExperienceWebView.setWebViewClient(new WebViewClient());

        targetExperienceWebView.setPadding(0, 0, 0, 0);
        targetExperienceWebView.setInitialScale(getScale());

        String mbox3rdPartyId = getIntent().getExtras().getString(Constants.MBOX_THIRD_PARTY_ID);

        String html = "<section>\n" +
                "\t\t\t\t\t\t\t\t\t<a href=\"#\" class=\"image featured\"><img src=\"images/fiji.jpg\" alt=\"\"></a>\n" +
                "\t\t\t\t\t\t\t\t\t<header>\n" +
                "\t\t\t\t\t\t\t\t\t\t<h3>Fiji is never a bad idea</h3>\n" +
                "\t\t\t\t\t\t\t\t\t</header>\n" +
                "\t\t\t\t\t\t\t\t\t<p>We travel not to escape life, but for life not to escape us. This <a href=\"https://www.adobe.com/solutions/testing-targeting.html#\">release</a> is all about features and productivity. Target makes it easy for you to create personalized digital experiences that deliver real results and revenue.\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t</p></section>" + mbox3rdPartyId.hashCode();
        String mime = "text/html";
        String encoding = "utf-8";


        targetExperienceWebView.getSettings().setJavaScriptEnabled(true);
        targetExperienceWebView.loadDataWithBaseURL("http://scoroi.github.io/adobesummit2015/", html, mime, encoding, null);


    }

    private int getScale() {
        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        Double val = width / 400D;
        val = val * 100d;
        return val.intValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_target_experience, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
