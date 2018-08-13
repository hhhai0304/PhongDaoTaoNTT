package vn.name.hohoanghai.utils;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.name.hohoanghai.bases.BaseRetrofit;
import vn.name.hohoanghai.interfaces.BaseApi;
import vn.name.hohoanghai.models.RequestResult;

public class RequestUtils extends BaseRetrofit {
    private BaseApi api;

    public RequestUtils() {
        api = getRetrofit().create(BaseApi.class);
    }

    public void sendRequest(String url) {
        Call<ResponseBody> call = api.sendGetRequest(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        EventBus.getDefault().post(new RequestResult(true, result));
                    } catch (Exception e) {
                        DLog.e(e.getMessage());
                        EventBus.getDefault().post(new RequestResult(e.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                EventBus.getDefault().post(new RequestResult(t.getMessage()));
            }
        });
    }
}