package vn.name.hohoanghai.utils;

import android.content.Context;

import vn.name.hohoanghai.pdtntt.App;
import vn.name.hohoanghai.pdtntt.BuildConfig;

public class Settings {
    public static final String ADMOB_ID = "ca-app-pub-9204613093493847~6172734415";

    private static final String KEY_STUDENT_ID = "key_student_id";
    private static final String KEY_STUDENT_HASH = "key_student_hash";
    private static final String KEY_HOME_PAGE = "key_home_page";

    private static final String DOMAIN = "phongdaotao2.ntt.edu.vn";

    private static final String URL_BASE = "http://" + DOMAIN;
    public static final String URL_HOME = URL_BASE + "/Default.aspx";
    public static final String URL_LOGIN = URL_BASE + "/TraCuuThongTin.aspx";
    public static final String URL_WEEK_SCHEDULE = URL_BASE + "/LichHocLichThiTuan.aspx?k=";
    public static final String URL_SCHEDULE = URL_BASE + "/XemLichHoc.aspx?k=";
    public static final String URL_EXAMINATION = URL_BASE + "/XemLichThi.aspx?k=";
    public static final String URL_RESULT = URL_BASE + "/XemDiem.aspx?k=";
    public static final String URL_ATTENDANCE = URL_BASE + "/ThongTinDiemDanh.aspx?k=";
    public static final String URL_DEBT = URL_BASE + "/CongNoSinhVien.aspx?k=";
    public static final String URL_OUTCOME_STANDARD = URL_BASE + "/News.aspx?MenuID=351";
    public static final String URL_LOCATION = URL_BASE + "/NewsDetail.aspx?NewsID=580";
    public static final String URL_TUITION = URL_BASE + "/NewsDetail.aspx?NewsID=474";
    public static final String URL_AVATAR = URL_BASE + "/GetImage.aspx?MSSV=";
    public static final String URL_LESSION = "http://h3solution.com/phongdaotaontt/quydinhphantiethoc.png";

    private static Context getContext() {
        return App.getInstance().getApplicationContext();
    }

    public static void setStudentId(String studentId) {
        SettingUtils.getInstance(getContext()).put(KEY_STUDENT_ID, studentId);
    }

    public static String getStudentId() {
        return SettingUtils.getInstance(getContext()).getString(KEY_STUDENT_ID);
    }

    public static void setHomePage(String homePage) {
        SettingUtils.getInstance(getContext()).put(KEY_HOME_PAGE, homePage);
    }

    public static String getHomePage() {
        return SettingUtils.getInstance(getContext()).getString(KEY_HOME_PAGE);
    }

    public static void setStudentHash(String studentHash) {
        SettingUtils.getInstance(getContext()).put(KEY_STUDENT_HASH, studentHash);
    }

    public static String getStudentHash() {
        return SettingUtils.getInstance(getContext()).getString(KEY_STUDENT_HASH);
    }

    public static void clearSession() {
        SettingUtils.getInstance(getContext()).clear(KEY_STUDENT_ID, KEY_STUDENT_HASH);
    }
}