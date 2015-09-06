package safe17.weitudevelop.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lidecai on 2015/9/6.
 */
public class FolderDataHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "Folder.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public FolderDataHelper(Context context)
    {
        super(context,DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create a database");
        String sql = "create table Folder(name varchar(50) primary key ,isprivate integer not null,picturenum integer not null)";
        db.execSQL(sql);

        String sql2="insert into Folder(name,isprivate,picturenum) values('默认相册','0',0)";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {

    }

    public void AddFolder(SQLiteDatabase db,String name,boolean isprivate)
    {
        Integer val;
        if(isprivate==true)
            val=1;
        else
            val=0;
        String sql="insert into Folder(name,isprivate,picturenum) values('"+name+"','"+ val +"',0)";
        db.execSQL(sql);
    }

    public int getFolderPicNum(SQLiteDatabase db,String name)
    {
        Cursor c=db.rawQuery("select picturenum from Folder where name='"+name+"'", null);
        return c.getInt(0);
    }

    //增加照片数量,第一个为数据库名称,第二个为文件夹名字，第三个为添加后的数量
    public void FolderAddNum(SQLiteDatabase db,String name,int addnum)
    {
        String sql="update Folder set picturenum= " + (addnum+getFolderPicNum(db,name))+ " where name='"+name+"'";
        db.execSQL(sql);
    }

    public void deleteFolder(SQLiteDatabase db,String name)
    {
        String sql = "delete from Folder where name='"+(name)+"'";//删除操作的SQL语句
        db.execSQL(sql);//执行删除操作
    }
    public boolean isPrivateFolder(SQLiteDatabase db,String name)
    {
        Cursor c=db.rawQuery("select isprivate from Folder where name='"+name+"'", null);
        if(c.getString(0).equals("1"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<Map<String,String>> converDBtoList(SQLiteDatabase db)
    {
        Cursor cursor=db.rawQuery("select name , picturenum from Folder",null);
        ArrayList<Map<String,String>> result=new ArrayList<Map<String, String>>();
        while (cursor.moveToNext())
        {
            Map<String,String> map=new HashMap<>();
            map.put("album_name", cursor.getString(0));
            String tmp=cursor.getInt(1) +"张照片";
            //Log.i(tmp,tmp);
            map.put("photo_num",tmp);
        }
        return result;
    }
}
