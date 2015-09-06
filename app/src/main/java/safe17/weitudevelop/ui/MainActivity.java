package safe17.weitudevelop.ui;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import safe17.weitudevelop.R;
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

            if (mTools != null && !mTools.getTurePassword().equals("")) {
                password2.setVisibility(View.INVISIBLE);
                String welcome_mes = "输入您的密码";
                myTextView.setText(welcome_mes);
                PublicData.FirstLogin=false;
            }

            btnlogin = (Button) findViewById(R.id.BtnLogin);
        btnlogin.setOnClickListener(new LoginClickListener());
    }

    //登录监听类，用来判断密码是否正确
    class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!mTools.getTurePassword().equals(""))  //密码不为空
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
            else {
                if (password1.getText().toString().equals("") || password2.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (password1.getText().toString().equals(password2.getText().toString())) {
                    mTools.saveTurePassword(password1.getText().toString());
                    PublicData.LoginInTruePasswd = true;
                    Intent LoginIntent = new Intent(MainActivity.this, PowerOnActivity.class);
                    startActivity(LoginIntent);
                    password1.setText("");
                }
                else
                    Toast.makeText(getApplicationContext(), "两次输入密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
            }
        }
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
}


