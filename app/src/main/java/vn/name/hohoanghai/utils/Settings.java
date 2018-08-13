package vn.name.hohoanghai.utils;

import android.content.Context;

import vn.name.hohoanghai.pdtntt.App;

public class Settings {
    private static final String KEY_STUDENT_ID = "key_student_id";
    private static final String KEY_HOME_PAGE = "key_home_page";

    public static final String URL_BASE = "http://phongdaotao2.ntt.edu.vn/";
    public static final String URL_HOME = URL_BASE + "Default.aspx";
    public static final String URL_WEEK_SCHEDULE = URL_BASE + "LichHocLichThiTuan.aspx?MSSV=";
    public static final String URL_SCHEDULE = URL_BASE + "XemLichHoc.aspx?MSSV=";
    public static final String URL_EXAMINATION = URL_BASE + "XemLichThi.aspx?MSSV=";
    public static final String URL_RESULT = URL_BASE + "XemDiem.aspx?MSSV=";
    public static final String URL_ATTENDACE = URL_BASE + "ThongTinDiemDanh.aspx?MSSV=";
    public static final String URL_DEBT = URL_BASE + "CongNoSinhVien.aspx?MSSV=";
    public static final String URL_OUTCOME_STANDARD = URL_BASE + "News.aspx?MenuID=351";
    public static final String URL_AVATAR = URL_BASE + "GetImage.aspx?MSSV=";

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
}