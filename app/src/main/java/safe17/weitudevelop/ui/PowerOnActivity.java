package safe17.weitudevelop.ui;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ImageView;
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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safe17.weitudevelop.R;
import safe17.weitudevelop.adapter.FolderDataHelper;
import safe17.weitudevelop.fragment.AboutUsFragment;
import safe17.weitudevelop.fragment.DefaultFragment;
import safe17.weitudevelop.fragment.ModifyFakePasswordFragment;
import safe17.weitudevelop.fragment.ModifyPasswordFragment;
import safe17.weitudevelop.tool.PublicData;


public class PowerOnActivity extends FragmentActivity implements OnItemClickListener
{

    public static final String[] TITLES = {"返回相册", "修改密码", "修改伪密码", "关于", "注销"};
    public static final String[] FAKETITLES = {"返回相册", "修改密码", "关于", "注销"};
    private int[] imageIds = new int[]{R.mipmap.home,R.mipmap.passowrd_true,R.mipmap.passwor_false,R.mipmap.about,R.mipmap.exit};
    private int[] imageIdsFake = new int[]{R.mipmap.home,R.mipmap.passowrd_true,R.mipmap.about,R.mipmap.exit};
    private DrawerLayout mDrawer_layout;//DrawerLayout容器
    private RelativeLayout mMenu_layout_left;//左边抽屉
    private ImageView add_album;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_on);
        mDrawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenu_layout_left = (RelativeLayout) findViewById(R.id.menu_layout_left);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new Fragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView menu_listview_l = (ListView) mMenu_layout_left.findViewById(R.id.menu_listView_l);
        if(PublicData.LoginInTruePasswd)
            menu_listview_l.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item1, TITLES));
        else
            menu_listview_l.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item1, FAKETITLES));

        List<Map<String, Object>> listItems = new ArrayList<Map<String,Object>>();/*在数组中存放数据*/
        for(int i=0;i<TITLES.length;i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("header", imageIds[i]);//加入图片
            listItem.put("name", TITLES[i]);
            listItems.add(listItem);
        }
        List<Map<String, Object>> listItems_fake = new ArrayList<Map<String,Object>>();/*在数组中存放数据*/
        for(int i=0;i<FAKETITLES.length;i++)
        {
            Map<String, Object> listItem_Fake = new HashMap<String, Object>();
            listItem_Fake.put("header", imageIdsFake[i]);//加入图片
            listItem_Fake.put("name", FAKETITLES[i]);
            listItems_fake.add(listItem_Fake);
        }

        if(PublicData.LoginInTruePasswd){
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.leftdrawer_item,
                    new String[]{"header", "name"}, new int[]{R.id.drawer_icon,R.id.drawer_text});
            menu_listview_l.setAdapter(simpleAdapter);
        }
        else{
            SimpleAdapter simpleAdapter_fake = new SimpleAdapter(this, listItems_fake, R.layout.leftdrawer_item,
                    new String[]{"header", "name"}, new int[]{R.id.drawer_icon,R.id.drawer_text});
            menu_listview_l.setAdapter(simpleAdapter_fake);
        }


        //监听菜单
        menu_listview_l.setOnItemClickListener(new DrawerItemClickListenerLeft());
        add_album = (ImageView) findViewById(R.id.add_album);
        add_album.setOnClickListener(new onClickLisentertmp());

        ImageView btndrawer = (ImageView) findViewById(R.id.btnDrawer);
        btndrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawer_layout.isDrawerOpen(mMenu_layout_left) == true) {
                    mDrawer_layout.closeDrawer(mMenu_layout_left);
                } else {
                    mDrawer_layout.openDrawer(mMenu_layout_left);
                    mDrawer_layout.bringToFront();
                }
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawer_layout,         /* DrawerLayout object */
                R.mipmap.ic_launcher,  /* nav drawer image to replace 'Up' caret */
                R.string.abc_action_bar_home_description,  /* "open drawer" description for accessibility */
                R.string.abc_action_bar_home_description  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                mDrawer_layout.bringToFront();
            }
        };
        mDrawer_layout.setDrawerListener(mDrawerToggle);

        FolderDataHelper Folderdb = new FolderDataHelper(this);
        SQLiteDatabase Folder_Data=Folderdb.getReadableDatabase();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new DefaultFragment();
        ft.replace(R.id.fragment_layout, fragment);
        ft.commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
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
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "item click", Toast.LENGTH_LONG).show();
    }

    /*左侧列表点击事件*/
    public class DrawerItemClickListenerLeft implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = null;
            if(PublicData.LoginInTruePasswd)
            {
                //根据item点击行号判断启用哪个Fragment
                switch (position) {
                    case 0:
                        fragment = new DefaultFragment();
                        PublicData.isMainPage=true;
                        break;
                    case 1:
                        fragment = new ModifyPasswordFragment();
                        break;
                    case 2:
                        fragment = new ModifyFakePasswordFragment();
                        break;
                    case 3:
                        fragment = new AboutUsFragment();
                        break;
                    case 4: {
                        finish();
                        break;
                    }
                    default:
                        break;
                }
                ft.replace(R.id.fragment_layout, fragment);
                ft.commit();
                mDrawer_layout.closeDrawer(mMenu_layout_left);//关闭mMenu_layout
            }
            else
            {
                switch (position) {
                    case 0:
                        fragment = new DefaultFragment();
                        PublicData.isMainPage=true;
                        break;
                    case 1:
                        fragment = new ModifyFakePasswordFragment();
                        break;
                    case 2:
                        fragment = new AboutUsFragment();
                        break;
                    case 3:
                    {
                        finish();
                        break;
                    }
                    default:
                        break;
                }
                ft.replace(R.id.fragment_layout, fragment);
                ft.commit();
                mDrawer_layout.closeDrawer(mMenu_layout_left);//关闭mMenu_layout
            }
        }
    }




    private class onClickLisentertmp implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent LoginIntent = new Intent(PowerOnActivity.this, AddAlbumActivity.class);
            startActivity(LoginIntent);
            //finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_power_on, menu);
        return true;
    }

}
