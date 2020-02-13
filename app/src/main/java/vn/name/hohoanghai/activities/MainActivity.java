package vn.name.hohoanghai.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import vn.name.hohoanghai.bases.BaseActivity;
import vn.name.hohoanghai.fragments.ImageFragment;
import vn.name.hohoanghai.pdtntt.BuildConfig;
import vn.name.hohoanghai.pdtntt.R;
import vn.name.hohoanghai.utils.DownloadUtils;
import vn.name.hohoanghai.utils.ImageUtils;
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
    @BindView(R.id.adView)
    AdView adView;

    private String studentId;
    private String studentHash;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    protected void initData() {
        setupAd();

        studentId = Settings.getStudentId();
        studentHash = Settings.getStudentHash();

        if (!BuildConfig.DEBUG) {
            Crashlytics.setUserIdentifier(studentId);
        }

        setupDrawer();
        setupWebView();

        swipeContainer.setRefreshing(true);
        webView.loadUrl(Settings.URL_HOME);
    }

    private void setupAd() {
        FirebaseAnalytics.getInstance(this);
        MobileAds.initialize(this, Settings.ADMOB_ID);
        adView.loadAd(new AdRequest.Builder().build());
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

        View headerView = navView.getHeaderView(0);
        ImageView imgAvatar = headerView.findViewById(R.id.img_avatar);
        TextView tvStudentId = headerView.findViewById(R.id.tv_student_id);
        TextView tvMoreInfo = headerView.findViewById(R.id.tv_more_info);

        tvMoreInfo.setOnClickListener(view -> {
            Settings.clearSession();
            appear(LoginActivity.class);
        });

        ImageUtils.loadAvatar(this, imgAvatar, Settings.URL_AVATAR + studentId);
        tvStudentId.setText(studentId);
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

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

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
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ImageFragment());
                fragmentTransaction.commit();
                break;
            case R.id.nav_location:
                url = Settings.URL_LOCATION;
                break;
            case R.id.nav_tuition:
                url = Settings.URL_TUITION;
                break;
//            case R.id.nav_setting:
//                break;
//            case R.id.nav_about:
//                break;
        }

        if (!TextUtils.isEmpty(url)) {
            swipeContainer.setRefreshing(true);
            webView.loadUrl(url);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}