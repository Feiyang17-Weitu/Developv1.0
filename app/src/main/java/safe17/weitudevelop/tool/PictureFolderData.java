package safe17.weitudevelop.tool;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lidecai on 2015/9/6.
 */
public class PictureFolderData {

    public Integer PictureID;
    public String PictureName;
    public boolean isTruePasswdAlbum;
    public Integer PictureNum;
    private final String SPLITER = ";";
    public SharedPreferences mPreferences;

    public PictureFolderData(String PictureName, boolean isTruePasswdAlbum)
    {
        PictureNum=0;
        this.PictureName=PictureName;
        this.isTruePasswdAlbum=isTruePasswdAlbum;
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

}