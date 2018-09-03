package vn.name.hohoanghai.bases;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int layoutId();
    protected abstract void initData();
    protected abstract void initEvents();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        ButterKnife.bind(this);
        initData();
        initEvents();
    }

    protected void hideActionBar() {
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    protected void appear(Class target) {
        startActivity(new Intent(this, target));
        finish();
    }
}