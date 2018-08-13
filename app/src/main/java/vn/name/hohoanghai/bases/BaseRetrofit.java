package vn.name.hohoanghai.bases;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import vn.name.hohoanghai.utils.Settings;

public abstract class BaseRetrofit {
    protected Retrofit getRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(Settings.URL_BASE)
                .client(client)
                .build();
    }
}