package safe17.weitudevelop;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
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

public class PowerOnActivity extends FragmentActivity {

    public static final String[] TITLES = {"修改密码","修改伪密码" ,"帮助与反馈", "关于","退出" };
   private DrawerLayout mDrawer_layout;//DrawerLayout容器
    private RelativeLayout mMenu_layout_left;//左边抽屉

    private String data[][] = new String[][]{{"相册1","4张照片"},
            {"相册2","5张照片"},{"默认相册","1张照片"}};

    private List<Map<String, String>> list = new ArrayList<Map<String,String>>();

    private ListView photoList;
    private SimpleAdapter simpleAdapter;

    private ImageView add_album;

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

        photoList = (ListView) findViewById(R.id.photo_list);
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
        this.photoList.setAdapter(this.simpleAdapter);
        this.photoList.setOnItemClickListener(new onItemClickedListenertmp());


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
            mDrawer_layout.closeDrawer(mMenu_layout_left);//关闭mMenu_layout
        }

    }

    private class onClickLisentertmp implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.add_album://添加相册

                    new AlertDialog.Builder(PowerOnActivity.this).setTitle("请输入相册名")
                            .setView(new EditText(PowerOnActivity.this)).setPositiveButton("添加", null).setNegativeButton("取消", null).show();
                    break;

                default:
                    break;
            }
        }

    }

    private class onItemClickedListenertmp implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Map<String, String> map = (Map<String, String>)PowerOnActivity.
                    this.simpleAdapter.getItem(position);
            String album_name = map.get("album_name");

            Intent main_photo = new Intent(PowerOnActivity.this, photo_view.class);
            main_photo.putExtra("album_name", album_name);
            PowerOnActivity.this.startActivity(main_photo);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_power_on, menu);
        return true;
    }

}
