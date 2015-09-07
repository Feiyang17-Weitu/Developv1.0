package safe17.weitudevelop.ui;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import safe17.weitudevelop.R;
import safe17.weitudevelop.tool.RsaTool;
import safe17.weitudevelop.tool.SharePrefrencesTools;
import safe17.weitudevelop.tool.PublicData;

public class MainActivity extends Activity {
    private TextView myTextView;
    EditText password1;
    EditText password2;
    Button   btnlogin;
    public SharePrefrencesTools mTools;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            password1 = (EditText) findViewById(R.id.password1);
            password2 = (EditText) findViewById(R.id.password2);
            myTextView = (TextView) findViewById(R.id.title);
            mTools = new SharePrefrencesTools(MainActivity.this, "password_info");

            if (mTools != null && !mTools.getTurePassword().equals("") && PublicData.FirstLogin == true)
            {
                password2.setVisibility(View.GONE);
                String welcome_mes = "输入您的密码";
                myTextView.setText(welcome_mes);
                PublicData.FirstLogin=false;
                String rootpath = getFilesDir().getPath();
                String publictkey = rootpath + "public.key";
                String privatekey = rootpath + "private.key";
                RsaTool rsatool = new RsaTool();
                try {
                    rsatool.makekeyfile(publictkey, privatekey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            btnlogin = (Button) findViewById(R.id.BtnLogin);
            btnlogin.setOnClickListener(new LoginClickListener());
    }

    //登录监听类，用来判断密码是否正确
    class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!mTools.getTurePassword().equals("") && PublicData.FirstLogin == false)  //密码不为空,有密码
            {
                //真密码
                if(password1.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
                }
                else if (password1.getText().toString().equals(mTools.getTurePassword())) {
                    //密码正确，页面跳转
                    PublicData.LoginInTruePasswd = true;
                    Intent LoginIntent = new Intent(MainActivity.this, PowerOnActivity.class);
                    startActivity(LoginIntent);
                    password1.setText("");
                }
                else if (password1.getText().toString().equals(mTools.getFakePassword()))
                {
                    PublicData.LoginInTruePasswd = false;
                    Intent LoginIntent = new Intent(MainActivity.this, PowerOnActivity.class);
                    startActivity(LoginIntent);
                    password1.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "密码输入错误，请重新输入！", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                if (password1.getText().toString().equals("") || password2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
                }
                else if (password1.getText().toString().equals(password2.getText().toString()))
                {
                    mTools.saveTurePassword(password1.getText().toString());
                    PublicData.LoginInTruePasswd = true;
                    password1.setText("");

                    Intent LoginIntent = new Intent(MainActivity.this, PowerOnActivity.class);
                    startActivity(LoginIntent);
                }
                else
                    Toast.makeText(getApplicationContext(), "两次输入密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        }
        else
        {
            System.exit(0);
        }
    }

}


