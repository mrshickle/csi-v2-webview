package com.sadasystems.csi_webview3;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.view.View;
        import android.view.WindowManager;
        import android.webkit.WebChromeClient;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ProgressBar;


public class Dashboard extends Activity {


    @SuppressLint("SetJavaScriptEnabled") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WebView engine = (WebView) findViewById(R.id.webView);

        // Progress bar.
        // With full screen app, window progress bar (FEATURE_PROGRESS) doesn't seem to show,
        // so we add an explicit one.
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);

        engine.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                progressBar.setProgress(progress);
            }
        });

        engine.setWebViewClient(new FixedWebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url)
            {
                progressBar.setVisibility(View.GONE);
            }
        });
        engine.getSettings().setJavaScriptEnabled(true);
        engine.clearCache(true);
        engine.loadUrl("http://qa-dot-csi-v2.appspot.com/");
    }

    public void onBackPressed() {
        WebView engine = (WebView) findViewById(R.id.webView);
        String url = engine.getUrl();
        if (url.equals("http://learnscripture.net/") ||
                url.equals("http://learnscripture.net/dashboard/")) {
            // exit
            super.onBackPressed();
        } else {
            // go back a page, like normal browser
            engine.goBack();
        }
    }

    private class FixedWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}