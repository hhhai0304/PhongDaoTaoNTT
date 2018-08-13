package vn.name.hohoanghai.utils;

import android.os.AsyncTask;
import android.util.Log;

public class WebViewLoadPart extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String[] params) {
//        try {
//
//            return new HttpRequest(params[0]).prepare().sendAndReadString();
//        } catch (Exception e) {
//            DLog.e(e.getMessage());
//            return null;
//        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) return;//Error logged, don't load anything
        result = result.concat("<script>onload=function(){document.querySelector('#sidebar').style.display='none';}</script>");
//        wv.loadData(result, "text/html", "UTF-8");
    }
}