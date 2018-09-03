package vn.name.hohoanghai.activities;

import android.Manifest;
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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import vn.name.hohoanghai.bases.BaseActivity;
import vn.name.hohoanghai.pdtntt.R;
import vn.name.hohoanghai.utils.DownloadUtils;
import vn.name.hohoanghai.utils.Settings;
import vn.name.hohoanghai.utils.WebViewUtils;

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

    private String studentId;
    private String studentHash;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    protected void initData() {
        studentId = Settings.getStudentId();
        studentHash = Settings.getStudentHash();

        setupDrawer();
        setupWebView();

        swipeContainer.setRefreshing(true);
        webView.loadUrl(Settings.URL_HOME);
    }

    private void setupWebView() {
        WebViewClient webViewClient = new WebViewClient() {
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
        };

        WebViewUtils.setWebViewSettings(webView, webViewClient);
    }

    protected void initEvents() {
        swipeContainer.setOnRefreshListener(() -> webView.reload());
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
        String url = null;

        switch (id) {
            case R.id.nav_home:
                url = Settings.URL_HOME;
                break;
            case R.id.nav_week_schedule:
                url = Settings.URL_WEEK_SCHEDULE + studentHash;
                break;
            case R.id.nav_schedule:
                url = Settings.URL_SCHEDULE + studentHash;
                break;
            case R.id.nav_examination:
                url = Settings.URL_EXAMINATION + studentHash;
                break;
            case R.id.nav_result:
                url = Settings.URL_RESULT + studentHash;
                break;
            case R.id.nav_attendance:
                url = Settings.URL_ATTENDANCE + studentHash;
                break;
            case R.id.nav_dept:
                url = Settings.URL_DEBT + studentHash;
                break;
            case R.id.nav_outcome_standard:
                url = Settings.URL_OUTCOME_STANDARD;
                break;
            case R.id.nav_lesson:
                break;
            case R.id.nav_location:
                break;
            case R.id.nav_tuition:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_about:
                break;
        }

        if (!TextUtils.isEmpty(url)) {
            swipeContainer.setRefreshing(true);
            webView.loadUrl(url);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}