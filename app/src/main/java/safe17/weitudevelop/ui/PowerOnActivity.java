package safe17.weitudevelop.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
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
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safe17.weitudevelop.R;
import safe17.weitudevelop.fragment.AboutUsFragment;
import safe17.weitudevelop.fragment.DefaultFragment;
import safe17.weitudevelop.fragment.FeedBackFragment;
import safe17.weitudevelop.fragment.ModifyFakePasswordFragment;
import safe17.weitudevelop.fragment.ModifyPasswordFragment;


public class PowerOnActivity extends FragmentActivity implements OnItemClickListener {

    public static final String[] TITLES = {"唯图相册","修改密码","修改伪密码" ,"帮助与反馈", "关于","退出" };
   private DrawerLayout mDrawer_layout;//DrawerLayout容器
    private RelativeLayout mMenu_layout_left;//左边抽屉

    private String data[][] = new String[][]{{"相册1","4张照片"},
            {"相册2","5张照片"},{"默认相册","1张照片"}};

    private List<Map<String, String>> list = new ArrayList<Map<String,String>>();

    private SimpleAdapter simpleAdapter;

    private ImageView add_album;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_on);
       mDrawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
       mMenu_layout_left = (RelativeLayout) findViewById(R.id.menu_layout_left);
       ListView menu_listview_l = (ListView) mMenu_layout_left.findViewById(R.id.menu_listView_l);
        menu_listview_l.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item1, TITLES));

        //监听菜单
        menu_listview_l.setOnItemClickListener(new DrawerItemClickListenerLeft());

        //photoList = (ListView) findViewById(R.id.photo_list);
        add_album = (ImageView) findViewById(R.id.add_album);
        add_album.setOnClickListener(new onClickLisentertmp());
        for(int x=0;x < this.data.length;x++){
            Map<String, String> map = new HashMap<String, String>();
            map.put("album_name", String.valueOf(this.data[x][0]));
            map.put("photo_num", String.valueOf(this.data[x][1]));
            this.list.add(map);
        }

        this.simpleAdapter = new SimpleAdapter(this, this.list, R.layout.photo_list,
                new String[]{"album_name", "photo_num"}, new int[]{R.id.album_name,R.id.photo_num});

        ImageView btndrawer=(ImageView)findViewById(R.id.btnDrawer);

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
                    mDrawer_layout.bringToFront();
//                    photoList.setVisibility(View.GONE);
                }
             //   Toast.makeText(getApplicationContext(), "呵呵！", Toast.LENGTH_LONG).show();
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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment =  new DefaultFragment();
        ft.replace(R.id.fragment_layout, fragment);
        ft.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "item click", Toast.LENGTH_LONG).show();
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
                    fragment = new DefaultFragment();
                    break;
                case 1:
                    fragment = new ModifyPasswordFragment();
                    break;
                case 2:
                    fragment = new ModifyFakePasswordFragment();
                    break;
                case 3:
                    fragment = new FeedBackFragment();
                    break;
                case 4:
                    fragment = new AboutUsFragment();
                    break;
                case 5:
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
            mDrawer_layout.closeDrawer(mMenu_layout_left);//关闭mMenu_layout
        }

    }

    private class onClickLisentertmp implements View.OnClickListener {


//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//            switch (v.getId()) {
//                case R.id.add_album://添加相册
//
//                    new AlertDialog.Builder(PowerOnActivity.this).setTitle("请输入相册名")
//                            .setView(new EditText(PowerOnActivity.this)).setPositiveButton("添加", null).setNegativeButton("取消", null).show();
//                    break;
//
//                default:
//                    break;
//            }
//        }
    @Override
        public void onClick(View view) {

                    Intent LoginIntent = new Intent(PowerOnActivity.this, AddAlbumActivity.class);
                    startActivity(LoginIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_power_on, menu);
        return true;
    }

}
