package vn.name.hohoanghai.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import vn.name.hohoanghai.bases.BaseActivity;
import vn.name.hohoanghai.pdtntt.R;
import vn.name.hohoanghai.utils.Settings;
import vn.name.hohoanghai.utils.WebViewUtils;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edt_student_id)
    EditText edtStudentId;
    @BindView(R.id.web_view)
    WebView webView;

    @BindString(R.string.login_incorrect_student_id)
    String incorrectStudentId;
    @BindString(R.string.login_successful)
    String loginSuccessful;

    String studentId;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        hideActionBar();
        if (!TextUtils.isEmpty(Settings.getStudentHash())) {
            appear(MainActivity.class);
        }
    }

    @Override
    protected void initEvents() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.equals(Settings.URL_LOGIN)) {
                    webView.loadUrl("javascript:(function(){document.getElementById('txtMaSoSV').value='" + studentId + "';})()");
                    webView.loadUrl("javascript:(function(){document.getElementById('txtSecurityCodeValue1').value='74b87337454200d4d33f80c4663dc5e5';})()");
                    webView.loadUrl("javascript:(function(){document.getElementById('txtSercurityCode1').value='aaaa';})()");
                    webView.loadUrl("javascript:(function(){document.getElementById('Button1').click();})()");
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.equals(Settings.URL_LOGIN)) {
                    super.onPageStarted(view, url, favicon);
                } else {
                    final String split = "\\?k=";
                    String[] urlSplit = url.split(split);
                    if (urlSplit.length == 2) {
                        Toast.makeText(LoginActivity.this, loginSuccessful, Toast.LENGTH_SHORT).show();
                        Settings.setStudentId(studentId);
                        Settings.setStudentHash(urlSplit[1]);
                        appear(MainActivity.class);
                    }
                }
            }
        };
        WebViewUtils.setWebViewSettings(webView, webViewClient);

        edtStudentId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (length == 10) {
                    studentId = editable.toString();
                    webView.loadUrl(Settings.URL_LOGIN);

                    doAnim();
                } else if (length > 10) {
                    Toast.makeText(LoginActivity.this, incorrectStudentId, Toast.LENGTH_LONG).show();
                    edtStudentId.setText("");
                    edtStudentId.requestFocus();
                }
            }
        });
    }

    private void doAnim() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        edtStudentId.setLayoutParams(layoutParams);
        edtStudentId.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        edtStudentId.setTextColor(Color.WHITE);
        edtStudentId.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_white, 0, 0, 0);
        edtStudentId.setTypeface(null, Typeface.BOLD);
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputManager).hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        webView.setVisibility(View.VISIBLE);

        TranslateAnimation animate = new TranslateAnimation(0, 0, webView.getWidth(), 0);
        animate.setDuration(1000);
        animate.setFillAfter(true);

        edtStudentId.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showDialog();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        webView.startAnimation(animate);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.login_dialog_title)
                .setMessage(R.string.login_dialog_message)
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(false)
                .show();
    }
}