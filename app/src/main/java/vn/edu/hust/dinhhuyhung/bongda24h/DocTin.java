package vn.edu.hust.dinhhuyhung.bongda24h;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.os.AsyncTask;
import android.webkit.WebViewClient;

/**
 * Created by Dinh Huy Hung on 5/17/2015.
 */
public class DocTin extends ActionBarActivity {
    private WebView webView;
    private ProgressDialog dialog;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctin);

        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("link");
        int giaidau = bundle.getInt("magiai");
        setTitle(Variables.GIAIDAU[giaidau]);

        webView = (WebView) findViewById(R.id.wvNews);

        webView.getSettings().setSupportZoom(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setWebViewClient(new DocTinWebClient());

        dialog = ProgressDialog.show(DocTin.this, "", "Loading... ");
        dialog.setCancelable(true);
        new DocTinTask().execute();

    }

    class DocTinTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            webView.loadUrl(link);
            return null;
        }
    }

    class DocTinWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if (dialog!=null) {
                dialog.dismiss();
            }
            super.onPageFinished(view, url);
        }
    }
}
