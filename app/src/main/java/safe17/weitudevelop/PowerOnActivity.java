package safe17.weitudevelop;

import android.media.Image;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class PowerOnActivity extends FragmentActivity {

    public static final String[] TITLES = {"修改密码","修改伪密码" ,"帮助与反馈", "关于","退出" };
   private DrawerLayout mDrawer_layout;//DrawerLayout容器
    private RelativeLayout mMenu_layout_left;//左边抽屉

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_on);
       mDrawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
       mMenu_layout_left = (RelativeLayout) findViewById(R.id.menu_layout_left);
       ListView menu_listview_l = (ListView) mMenu_layout_left.findViewById(R.id.menu_listView_l);
        menu_listview_l.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, TITLES));

        //监听菜单
        menu_listview_l.setOnItemClickListener(new DrawerItemClickListenerLeft());

        ImageButton btndrawer=(ImageButton)findViewById(R.id.btnDrawer);

        btndrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawer_layout.isDrawerOpen(mMenu_layout_left)==true)
                {
                    mDrawer_layout.closeDrawer(mMenu_layout_left);
                }
                else
                {
                    mDrawer_layout.openDrawer(mMenu_layout_left);
                }
             //   Toast.makeText(getApplicationContext(), "呵呵！", Toast.LENGTH_LONG).show();
            }
        });

    }

    /*左侧列表点击事件*/
    public class DrawerItemClickListenerLeft implements OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = null;

            //根据item点击行号判断启用哪个Fragment
            switch (position)
            {
                case 0:
                    fragment = new ModifyPasswordFragment();
                    break;
                case 1:
                    fragment = new ModifyFakePasswordFragment();
                    break;
                case 2:
                    fragment = new FeedBackFragment();
                    break;
                case 3:
                    fragment = new AboutUsFragment();
                    break;
                case 4:
                {
                    android.os.Process.killProcess(android.os.Process.myPid());
                  //  System.exit(0);
                    break;
                }
                default:
                    break;
            }
            ft.replace(R.id.fragment_layout, fragment);
            ft.commit();
            mDrawer_layout.closeDrawer(mMenu_layout_left);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_power_on, menu);
        return true;
    }

}
