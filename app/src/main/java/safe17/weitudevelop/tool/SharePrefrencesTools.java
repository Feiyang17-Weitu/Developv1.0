package safe17.weitudevelop.tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangxubing on 2015/9/4.
 */

public class SharePrefrencesTools {

    private final String SPLITER = ";";
    public SharedPreferences mPreferences;
    private Context mContext;

    public SharePrefrencesTools(Context context, String name) {
        this.mContext = context;
        mPreferences = context.getSharedPreferences(name, 0);
    }


    public void savePicPaths(List<String> paths) {
        SharedPreferences.Editor editor = mPreferences.edit();
        // paths 转成String
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <paths.size() ; i++) {
            sb.append(paths.get(i));
            sb.append(SPLITER);
        }
        editor.putString("paths", sb.toString());
        editor.commit();
    }

    public ArrayList<String> getPicPaths() {
        ArrayList<String> result = new ArrayList<>();
        String s = mPreferences.getString("paths", "");
        if (!s.isEmpty()) {
            String[] arr = s.split(SPLITER);
            result.addAll(Arrays.asList(arr));
        }
        return result;
    }

    public String getTurePassword() {
        String strTruePwd = mPreferences.getString("true_password", "");
        return strTruePwd;
    }

    public void saveTurePassword(String strTruePwd) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("true_password", strTruePwd);
        editor.commit();
    }

    public String getFakePassword() {
        String strFakePwd = mPreferences.getString("fake_password", "");
        return strFakePwd;
    }

    public void saveFakePassword(String strFakePwd) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("fake_password", strFakePwd);
        editor.commit();
    }
}
