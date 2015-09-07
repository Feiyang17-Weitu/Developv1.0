package safe17.weitudevelop.adapter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by lidecai on 2015/9/6.
 */
public class FolderDataHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "Folder.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public FolderDataHelper(Context context)
    {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create a database");
        String sql = "create table Folder(name varchar(50) primary key ,path varchar(50),isprivate integer not null,picturenum integer not null)";
        db.execSQL(sql);

        String sql1 = "create table Photo(id integer PRIMARY KEY autoincrement,name varchar(50),path varchar(50))";
        db.execSQL(sql1);

        //String sql2="insert into Folder(name,path,isprivate,picturenum) values('默认相册','" + GetPath() + "',0,0)";


        String sdcard_path = Environment.getExternalStorageDirectory().getPath();
        File destDir = new File(sdcard_path + "/secphoto/" + GetPath());
        if (!destDir.exists()) {destDir.mkdirs();}

        //db.execSQL(sql2);

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
        String sql="insert into Folder(name,path,isprivate,picturenum) values('"+name+"','"+GetPath()+ "',"+ val +",0)";

        String sdcard_path = Environment.getExternalStorageDirectory().getPath();
        File destDir = new File(sdcard_path + "/secphoto/" + GetPath());
        if (!destDir.exists()) {destDir.mkdirs();}

        db.execSQL(sql);
    }

    private static String GetPath(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public int getFolderPicNum(SQLiteDatabase db,String name)
    {
        Cursor c=db.rawQuery("select picturenum from Folder where name='" + name + "'", null);
        return c.getInt(0);
    }


    //增加照片数量,第一个为数据库名称,第二个为文件夹名字，第三个为添加后的数量
    public void FolderAddNum(SQLiteDatabase db,String name,int addnum)
    {
        String sql="update Folder set picturenum = " + addnum+ " where name= '"+name+"'";
        System.out.println("SQL is:"+sql);
        db.execSQL(sql);
    }


    public void AddPhoto(SQLiteDatabase db,String album_name,ArrayList<String> photo_list){

      //  if(this.isPrivateFolder(db,album_name)) {
            //String sql="";
            //Cursor c=db.rawQuery("select path from Folder where name='"+ album_name +"'", null);
            //String album_path = c.getString(0);
            //String folder_path = c.getString(c.getColumnIndex("path"));
            //String sdcard_rootpath = Environment.getExternalStorageDirectory().getPath();
       // }

        String sql;
        for(int i=0;i<photo_list.size();i++) {
            sql = "insert into Photo(name,path) values('" + album_name + "','" + photo_list.get(i) + "')";

            db.execSQL(sql);
        }
    }

    public ArrayList<String> GetPhoto(SQLiteDatabase db,String album_name){
        String sql = "select path from Photo where name='" + album_name + "'";
        Cursor c = db.rawQuery(sql,null);
        ArrayList<String> photo_list = new ArrayList<String>();
        while(c.moveToNext()) {
            photo_list.add(c.getString(c.getColumnIndex("path")));

        }
        return photo_list;
    }




    public void deleteFolder(SQLiteDatabase db,String name)
    {
        String sql = "delete from Folder where name='"+(name)+"'";//删除操作的SQL语句
        db.execSQL(sql);//执行删除操作
    }
    public boolean isPrivateFolder(SQLiteDatabase db,String name)
    {
        Cursor c=db.rawQuery("select isprivate from Folder where name='"+name+"'", null);
        if(c.getInt(0)==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    private boolean moveFile(String srcFileName, String destDirName) {
        File srcFile = new File(srcFileName);
        if(!srcFile.exists() || !srcFile.isFile())
            return false;

        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();

        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
        }




}
