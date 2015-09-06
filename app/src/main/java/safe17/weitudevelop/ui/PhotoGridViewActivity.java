package safe17.weitudevelop.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.GridView;
import java.util.ArrayList;

import android.widget.LinearLayout;
import android.widget.TextView;

import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import safe17.weitudevelop.R;

import safe17.weitudevelop.tool.SharePrefrencesTools;



public class PhotoGridViewActivity extends Activity {

    private static final int REQUEST_PICK = 0;
    private GridView gridview;
    private GridAdapter adapter;
    private ArrayList<String> selectedPicture = new ArrayList<String>();
    private ImageView AddPicture;
    private TextView title;
    private SharePrefrencesTools mTools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_photo);

        //修改title_bar
        ImageView left_button =  (ImageView) findViewById(R.id.btnDrawer);
        left_button.setImageResource(R.mipmap.navigation_back);
        left_button.setOnClickListener(new SelectBackClickListener());
        AddPicture =  (ImageView) findViewById(R.id.add_album);
        AddPicture.setImageResource(R.mipmap.camera);
        AddPicture.setOnClickListener(new SelectPictureClickListener());

        Intent it = super.getIntent();
        String album_name = it.getStringExtra("album_name");
        title = (TextView) findViewById(R.id.bar_title);
        title.setText(album_name);

        mTools = new SharePrefrencesTools(this, album_name);
        this.readImagFiles();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(300).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new OnPhotoItemClickListener());

        adapter = new GridAdapter();
        gridview.setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            ArrayList<String> resultData =  (ArrayList<String>) data
                    .getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
            selectedPicture.addAll(resultData);
            //去除重复图片
            for (int i = 0; i < selectedPicture.size() - 1; i++)
            {
                for (int j = i + 1; j < selectedPicture.size(); j++)
                {
                    if (selectedPicture.get(i).equals(selectedPicture.get(j)))
                    {
                        selectedPicture.remove(j);
                        j--;
                    }
                }
            }
            // 存储
            this.saveImgFiles();

            adapter.notifyDataSetChanged();
        }
    }


    private void readImagFiles() {
        selectedPicture = mTools.getPicPaths();
    }

    private void saveImgFiles() {
        mTools.savePicPaths(selectedPicture);
    }

    class GridAdapter extends BaseAdapter {
        LayoutParams params = new AbsListView.LayoutParams(200, 200);

        @Override
        public int getCount() {
            return selectedPicture.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ImageView(PhotoGridViewActivity.this);
                ((ImageView) convertView).setScaleType(ScaleType.CENTER_CROP);
                convertView.setLayoutParams(params);
            }
            ImageLoader.getInstance().displayImage("file://" + selectedPicture.get(position),
                    (ImageView) convertView);
            return convertView;
        }

    }

    private class SelectPictureClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(PhotoGridViewActivity.this, SelectPictureActivity.class), REQUEST_PICK);
        }
    }
    private class SelectBackClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent BackIntent = new Intent(PhotoGridViewActivity.this, PowerOnActivity.class);
            startActivity(BackIntent);
        }
    }
    private class OnPhotoItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub

            Intent SinglePhotoIntent = new Intent(PhotoGridViewActivity.this, PhotoSingleViewActivity.class);
//            Toast.makeText(PhotoGridViewActivity.this,selectedPicture.get(position),Toast.LENGTH_LONG).show();
            SinglePhotoIntent.putExtra("position_array", selectedPicture);
            SinglePhotoIntent.putExtra("position",position);
            startActivity(SinglePhotoIntent);
        }

    }
}
