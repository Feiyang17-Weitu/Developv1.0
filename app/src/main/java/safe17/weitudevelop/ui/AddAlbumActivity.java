package safe17.weitudevelop.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import safe17.weitudevelop.R;

/**
 * Created by ouxuewen on 2015/9/2.
 */

public class AddAlbumActivity extends Activity {

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
        Button FinishAlbum=(Button)findViewById(R.id.FinishAlbum);
        FinishAlbum.setOnClickListener(new FinishAlbumListener());
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

            EditText InputAlbum=(EditText)findViewById(R.id.flodername);
            EditText InputPasswd=(EditText)findViewById(R.id.Album_Passwd);

            CreatedAlbumName=InputAlbum.getText().toString();
            CreatedAlbumPasswd=InputPasswd.getText().toString();
            CheckBox isPrivateChecked=(CheckBox)findViewById(R.id.choose_private);
            if(isPrivateChecked.isChecked()==true)
            {
                isPrivateAlbum=true;
            }
            else
            {
                isPrivateAlbum=false;
            }

            //----获取相册名和是否是隐私相册结束
            Toast.makeText(getApplicationContext(), "文件夹名称为"+CreatedAlbumName+",是否为隐私文件夹："+isPrivateAlbum+",密码是："+CreatedAlbumPasswd, Toast.LENGTH_LONG).show();



        }
    }

}
