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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import safe17.weitudevelop.R;
import safe17.weitudevelop.tool.BitmapUtils;

/**
 * Created by ouxuewen on 2015/9/5.
 */
public class PhotoSingleViewActivity extends Activity implements ViewPager.OnPageChangeListener {

    private android.support.v4.view.ViewPager mViewPager;

    private List<ImageView> imageIdList;
    private int first_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.photo_single_view);
        mViewPager = (ViewPager)findViewById(R.id.vf_photo);
        imageIdList = new ArrayList<ImageView>();

        Intent it = super.getIntent();
        first_position = it.getIntExtra("position", 1);
        ArrayList<String> position_array = it.getStringArrayListExtra("position_array");

        for (int i=0;i<position_array.size();i++){
            imageIdList.add(getBitmap(position_array.get(i)));
        }

        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setOnPageChangeListener(this);
    }

    private ImageView getBitmap(String fileName) {

        ImageView imageView  = new ImageView(this);
        imageView.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFd(fileName, 500));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PhotoSingleViewActivity.this,"点击了" + mViewPager.getCurrentItem(),Toast.LENGTH_SHORT).show();
            }
        });
        return imageView;
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
            ((ViewPager)container).removeView(imageIdList.get(position));

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
