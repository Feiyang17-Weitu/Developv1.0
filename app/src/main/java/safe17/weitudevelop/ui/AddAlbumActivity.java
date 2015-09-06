package safe17.weitudevelop.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import safe17.weitudevelop.R;
import safe17.weitudevelop.adapter.FolderDataHelper;
import safe17.weitudevelop.fragment.DefaultFragment;
import safe17.weitudevelop.tool.PublicData;
import safe17.weitudevelop.tool.SharePrefrencesTools;

/**
 * Created by ouxuewen on 2015/9/2.
 */

public class AddAlbumActivity extends Activity {

    EditText InputPasswd;
    EditText InputAlbum;
    TextView InputHint;
    CheckBox isPrivateChecked;
    SharePrefrencesTools mTools;
    static boolean isPrivateAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTools = new SharePrefrencesTools(AddAlbumActivity.this, "password_info");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
        //返回按钮
        ImageButton NaviBack=(ImageButton)findViewById(R.id.navigation_back);
        NaviBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //----返回结束

        //创建相册文件夹
        InputHint=(TextView)findViewById(R.id.inputHint);
        InputAlbum=(EditText)findViewById(R.id.flodername);
        InputPasswd=(EditText)findViewById(R.id.Album_Passwd);
        InputPasswd.setVisibility(View.INVISIBLE);
        isPrivateChecked=(CheckBox)findViewById(R.id.choose_private);
        Button FinishAlbum=(Button)findViewById(R.id.FinishAlbum);

        if(PublicData.LoginInTruePasswd)
        {
            FinishAlbum.setOnClickListener(new FinishAlbumListener());
            isPrivateChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(mTools.getFakePassword().equals("")) {
                        if (isPrivateChecked.isChecked())
                            InputPasswd.setVisibility(View.VISIBLE);
                        else
                            InputPasswd.setVisibility(View.INVISIBLE);
                    }
                    else
                            InputPasswd.setVisibility(View.INVISIBLE);
                }
            });
        }
        else
        {
            InputHint.setVisibility(View.INVISIBLE);
            isPrivateChecked.setVisibility(View.INVISIBLE);
            InputPasswd.setVisibility(View.INVISIBLE);
            FinishAlbum.setOnClickListener(new FinishAlbumListener());
        }
        //数据库类
        //----创建结束
    }
    class FinishAlbumListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            //获取相册名和是否是隐私相册
            FolderDataHelper Folderdb = new FolderDataHelper(getApplicationContext());
            SQLiteDatabase db=Folderdb.getReadableDatabase();

            String CreatedAlbumName;
            String CreatedAlbumPasswd;
            CreatedAlbumName=InputAlbum.getText().toString();
            CreatedAlbumPasswd=InputPasswd.getText().toString();
            if(CreatedAlbumName.equals(""))
            {
                Toast.makeText(getApplicationContext(), "您没有输入文件夹名称，请重新输入", Toast.LENGTH_LONG).show();
            }
            else
            {
                if(isPrivateChecked.isChecked()==true)
                {
                    if (CreatedAlbumPasswd.equals("") == true && mTools.getFakePassword().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "您的密码输入为空值，请重新输入", Toast.LENGTH_LONG).show();
                    }
                    else if (mTools.getTurePassword().equals(CreatedAlbumPasswd))
                    {
                        Toast.makeText(getApplicationContext(), "伪密码和真密码重复了，请重新设置", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        mTools.saveFakePassword(CreatedAlbumPasswd);
                        Toast.makeText(getApplicationContext(), "成功设定隐私相册", Toast.LENGTH_LONG).show();
                        Folderdb.AddFolder(db, CreatedAlbumName, true);
                        isPrivateAlbum=true;
                        finish();
                        //Folderdb.close();

                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "成功创建密码并设定隐私相册", Toast.LENGTH_LONG).show();
                    Folderdb.AddFolder(db, CreatedAlbumName, false);
                    isPrivateAlbum=false;
                    finish();
                   // Folderdb.close();

                }
            }


        }
    }

}
