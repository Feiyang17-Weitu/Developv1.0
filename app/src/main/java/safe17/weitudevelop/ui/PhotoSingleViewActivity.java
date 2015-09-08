package safe17.weitudevelop.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import safe17.weitudevelop.R;
import safe17.weitudevelop.tool.BitmapUtils;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * Created by ouxuewen on 2015/9/5.
 */
public class PhotoSingleViewActivity extends Activity implements ViewPager.OnPageChangeListener {

    private android.support.v4.view.ViewPager mViewPager;

    private List<ImageView> imageIdList;
    private LinearLayout title_layout;
    private RelativeLayout bottom_layout;

    private int first_position;
    private Button btnDelPicture = null;
    private Button btnTransferPicture = null;
    private TextView picturePosition = null;
    private ImageView btnBack = null;
    private ArrayList<String> position_array = new ArrayList<String>();
    private boolean boFullScreen = true;
    private String strPicturePosition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_single_view);
        mViewPager = (ViewPager)findViewById(R.id.vf_photo);

        imageIdList = new ArrayList<ImageView>();
        btnDelPicture = (Button)findViewById(R.id.del_picture);
        btnTransferPicture = (Button)findViewById(R.id.tansfer_picture);

        Intent it = super.getIntent();
        first_position = it.getIntExtra("position", 1);
        position_array = it.getStringArrayListExtra("position_array");

        for (int i=0;i<position_array.size();i++){
            imageIdList.add(getBitmap(position_array.get(i)));
        }
        title_layout = (LinearLayout)findViewById(R.id.title_layout);
        bottom_layout = (RelativeLayout)findViewById(R.id.bottom_layout);
        picturePosition = (TextView)findViewById(R.id.picture_name);
        strPicturePosition = String.valueOf(first_position + 1) + "/" + String.valueOf(position_array.size());
        picturePosition.setText(strPicturePosition);

        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setCurrentItem(first_position);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                //activity从1到2滑动，2被加载后掉用此方法
                strPicturePosition = String.valueOf(arg0 + 1) + "/" + String.valueOf(position_array.size());
                picturePosition.setText(strPicturePosition);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                //从1到2滑动，在1滑动前调用
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                //状态有三个0空闲，1是增在滑行中，2目标加载完毕
                /**
                 * Indicates that the pager is in an idle, settled state. The current page
                 * is fully in view and no animation is in progress.
                 */
                //public static final int SCROLL_STATE_IDLE = 0;
                /**
                 * Indicates that the pager is currently being dragged by the user.
                 */
                //public static final int SCROLL_STATE_DRAGGING = 1;
                /**
                 * Indicates that the pager is in the process of settling to a final position.
                 */
                //public static final int SCROLL_STATE_SETTLING = 2;

            }
        });

        btnBack = (ImageView)findViewById(R.id.back);
        btnBack.setOnClickListener(new GoBackClickListener());

        btnDelPicture = (Button)findViewById(R.id.del_picture);
        btnDelPicture.setOnClickListener(new DelPictureClickListener());
    }

    private ImageView getBitmap(String fileName) {

        ImageView imageView  = new ImageView(this);
        imageView.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFd(fileName, 800));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boFullScreen) {
                    title_layout.setVisibility(View.GONE);
                    bottom_layout.setVisibility(View.GONE);
                    boFullScreen = false;
                }
                else
                {
                    title_layout.setVisibility(View.VISIBLE);
                    bottom_layout.setVisibility(View.VISIBLE);
                    boFullScreen = true;
                }

            }
        });

        return imageView;
    }

    class GoBackClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent BackIntent = new Intent(PhotoSingleViewActivity.this, PowerOnActivity.class);
            BackIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(BackIntent);
            PhotoSingleViewActivity.this.finish();
        }
    }

    class DelPictureClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PhotoSingleViewActivity.this);
            //    设置Title的图标
            builder.setIcon(R.mipmap.ic_launcher);
            //    设置Title的内容
            builder.setTitle("弹出警告框");
            //    设置Content来显示一个信息
            builder.setMessage("确定删除吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Toast.makeText(PhotoSingleViewActivity.this, "positive: " + which, Toast.LENGTH_SHORT).show();
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Toast.makeText(PhotoSingleViewActivity.this, "negative: " + which, Toast.LENGTH_SHORT).show();
                }
            });
            //    显示出该对话框
            builder.show();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageIdList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(imageIdList.get(position));

        }
        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {

            ((ViewPager) container).addView(imageIdList.get(position));
            return imageIdList.get(position);
        }



    }
}
