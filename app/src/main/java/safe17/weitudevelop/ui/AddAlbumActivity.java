package safe17.weitudevelop.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import safe17.weitudevelop.PasswdInfo;
import safe17.weitudevelop.R;

/**
 * Created by ouxuewen on 2015/9/2.
 */

public class AddAlbumActivity extends Activity {

    EditText InputPasswd;
    EditText InputAlbum;
    CheckBox isPrivateChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_album);
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
        InputAlbum=(EditText)findViewById(R.id.flodername);
        InputPasswd=(EditText)findViewById(R.id.Album_Passwd);
        InputPasswd.setVisibility(View.INVISIBLE);
        isPrivateChecked=(CheckBox)findViewById(R.id.choose_private);

        Button FinishAlbum=(Button)findViewById(R.id.FinishAlbum);

        FinishAlbum.setOnClickListener(new FinishAlbumListener());
        isPrivateChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                InputPasswd.setVisibility(View.VISIBLE);
            }
        });
        //----创建结束

    }
    class FinishAlbumListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            //获取相册名和是否是隐私相册
            boolean isPrivateAlbum;
            String CreatedAlbumName;
            String CreatedAlbumPasswd;

            CreatedAlbumName=InputAlbum.getText().toString();
            CreatedAlbumPasswd=InputPasswd.getText().toString();
            if(isPrivateChecked.isChecked()==true)
            {
                isPrivateAlbum=true;
            }
            else
            {
                isPrivateAlbum=false;
            }

            //----获取相册名和是否是隐私相册结束
          //  Toast.makeText(getApplicationContext(), "文件夹名称为"+CreatedAlbumName+",是否为隐私文件夹："+isPrivateAlbum+",密码是："+CreatedAlbumPasswd, Toast.LENGTH_LONG).show();
            //存储密码
            //为空
            if (isPrivateChecked.isChecked()==true && CreatedAlbumPasswd.equals("")==true)
            {
                Toast.makeText(getApplicationContext(), "您的伪密码输入为空值，请重新输入", Toast.LENGTH_LONG).show();
            }
            //和真实密码一样
            else if(PasswdInfo.preferences.getString("name", "").equals(CreatedAlbumPasswd))
            {
                Toast.makeText(getApplicationContext(), "伪密码和真密码重复了，请重新设置", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "哈哈，这就对了！放activity吧。文件夹名称为"+CreatedAlbumName+",是否为隐私文件夹："+isPrivateAlbum+",密码是："+CreatedAlbumPasswd, Toast.LENGTH_LONG).show();
            }


        }
    }

}
