package vn.name.hohoanghai.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public final class ImageUtils {

    public static void loadAvatar(Context context, ImageView imgView, String url) {
        Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(imgView);
    }
}