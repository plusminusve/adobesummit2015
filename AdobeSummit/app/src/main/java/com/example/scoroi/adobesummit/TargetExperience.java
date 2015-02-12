package com.example.scoroi.adobesummit;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;

import com.adobe.mobile.Config;
import com.adobe.mobile.Target;
import com.adobe.mobile.TargetLocationRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;


public class TargetExperience extends Activity {

    private static final String MIME_TYPE = "text/html";

    private WebView targetExperienceWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_experience);

        Properties appProperties;
        try {
            appProperties = getProperties(this.getAssets());
        } catch (IOException e) {
            Log.e("app", "Unable to read properties", e);
            return;
        }

        Config.setContext(this.getApplicationContext());
        Config.setDebugLogging(true);

        createTargetExperienceView();

        fetchAndSetExperience(appProperties);
    }

    private void fetchAndSetExperience(Properties appProperties) {
        Target.clearCookies();

        final String url = appProperties.getProperty("url");
        String mbox = appProperties.getProperty("mbox");
        String nativeAppMboxParameter = appProperties.getProperty("nativeAppMboxParameter");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(nativeAppMboxParameter, "true");
        parameters.put("mbox3rdPartyId", getMbox3rdPartyId());

        TargetLocationRequest locationRequest = Target.createRequest(mbox, url, parameters);

        final TargetExperience targetExperience = this;
        Target.loadRequest(locationRequest, new Target.TargetCallback<String>() {
            @Override
            public void call(final String content) {
                targetExperience.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        targetExperienceWebView
                            .loadDataWithBaseURL(url, content, MIME_TYPE, UTF_8.name(), null);
                    }
                });
            }
        });
    }

    private Integer getMbox3rdPartyId() {
        return getIntent().getExtras().getInt(Constants.MBOX_THIRD_PARTY_ID);
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

    private void createTargetExperienceView() {
        targetExperienceWebView = (WebView) findViewById(R.id.targetExperienceWebView);
        targetExperienceWebView.setWebViewClient(new WebViewClient());

        targetExperienceWebView.setPadding(0, 0, 0, 0);
        targetExperienceWebView.setInitialScale(getScale());
    }

    private Properties getProperties(AssetManager assetManager) throws IOException {
        InputStream propertiesInputStream = assetManager.open("app.properties");
        Properties appProperties = new Properties();
        appProperties.load(propertiesInputStream);
        return appProperties;
    }

    private int getScale() {
        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        Double val = width / 400D;
        val = val * 100d;
        return val.intValue();
    }
}
