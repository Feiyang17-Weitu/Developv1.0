package safe17.weitudevelop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;




public class MainActivity extends Activity {

    //登录监听类，用来判断密码是否正确
    class LoginClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
             /*    密码正确的情况下* if()
        {*/
            Intent LoginIntent = new Intent(MainActivity.this, PowerOnActivity.class);
            startActivity(LoginIntent);
        /*}
         else if () 假密码的情况下
         {
         }
         else  错误密码的情况下
         */
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Button btnlogin = (Button) findViewById(R.id.BtnLogin);
        btnlogin.setOnClickListener(new LoginClickListener());
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



