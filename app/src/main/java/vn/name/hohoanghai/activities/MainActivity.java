package vn.name.hohoanghai.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import vn.name.hohoanghai.bases.BaseActivity;
import vn.name.hohoanghai.models.RequestResult;
import vn.name.hohoanghai.pdtntt.R;
import vn.name.hohoanghai.utils.DownloadUtils;
import vn.name.hohoanghai.utils.RequestUtils;
import vn.name.hohoanghai.utils.Settings;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String studentId;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initViews() {
        studentId = Settings.getStudentId();

        setupDrawer();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function(){document.getElementById('ctl00_ucRight1_txtMaSV').value='" + studentId + "';})()");
                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView.setVisibility(View.INVISIBLE);
                //makeNotice(mainFragment, getString(R.string.no_internet));
            }

            @Override
            public void onPageStarted(WebView view, String thisUrl, Bitmap favicon) {
                super.onPageStarted(view, thisUrl, favicon);
                if (thisUrl.endsWith(".pdf") || thisUrl.endsWith(".rar") || thisUrl.endsWith(".doc") || thisUrl.endsWith(".docx") || thisUrl.endsWith(".xlsx") || thisUrl.endsWith(".xls")) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (MainActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            DownloadUtils.downloadFile(thisUrl);
                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    } else {
                        DownloadUtils.downloadFile(thisUrl);
                    }
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        webView.setInitialScale(100);
        webView.loadUrl(Settings.URL_HOME);
    }

    protected void initEvents() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
    }

    private void setupDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                new RequestUtils().sendRequest(Settings.URL_HOME);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveResult(RequestResult result) {
        if (result.isSuccess()) {
            String body = result.getBody();
//            body = body.concat("<script>onload=function(){document.querySelectorAll('div.col-left').style.display='none';}</script>");
            webView.loadDataWithBaseURL("", body, "text/html", "UTF-8", null);
        } else {
            Toast.makeText(this, "Có lỗi gì đó", Toast.LENGTH_SHORT).show();
        }
    }
}