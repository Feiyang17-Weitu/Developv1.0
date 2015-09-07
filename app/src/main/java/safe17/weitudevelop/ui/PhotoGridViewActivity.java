package safe17.weitudevelop.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

import safe17.weitudevelop.R;
import safe17.weitudevelop.adapter.FolderDataHelper;



public class PhotoGridViewActivity extends Activity {

    private static final int REQUEST_PICK = 0;
    private GridView gridview;
    private GridAdapter adapter;
    private ArrayList<String> selectedPicture = new ArrayList<String>();
    private ImageView AddPicture;
    private TextView title;
    private String album_name;
    //private SharePrefrencesTools mTools;
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
        album_name = it.getStringExtra("album_name");
        title = (TextView) findViewById(R.id.bar_title);
        title.setText(album_name);


        FolderDataHelper Folderdb = new FolderDataHelper(getApplicationContext());
        SQLiteDatabase db=Folderdb.getReadableDatabase();
        selectedPicture = Folderdb.GetPhoto(db,album_name);

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


       // mTools = new SharePrefrencesTools(this, album_name);
       // this.readImagFiles();

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
            int counter=selectedPicture.size();
            //去除重复图片
            for (int i = 0; i < selectedPicture.size() - 1; i++)
            {
                for (int j = i + 1; j < selectedPicture.size(); j++)
                {
                    if (selectedPicture.get(i).equals(selectedPicture.get(j))) {
                        selectedPicture.remove(j);
                        j--;
                        counter--;
                    }
                }
            }

            String album_name = data.getStringExtra("album_name");
          //  Log.i("相册值：", album_name);
//            Toast.makeText(getApplicationContext(), "相册位于："+album_name+"counter:"+counter, Toast.LENGTH_LONG).show();
            FolderDataHelper Folderdb = new FolderDataHelper(getApplicationContext());
            SQLiteDatabase db=Folderdb.getReadableDatabase();

            Folderdb.AddPhoto(db, album_name, selectedPicture);
            Folderdb.FolderAddNum(db, album_name,counter);
            // 存储
            //this.saveImgFiles();

            adapter.notifyDataSetChanged();
        }
    }


    //private void readImagFiles() {selectedPicture = mTools.getPicPaths();}

    //private void saveImgFiles() {mTools.savePicPaths(selectedPicture);}

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
                convertView = LayoutInflater.from(PhotoGridViewActivity.this).inflate(R.layout.grid_item, parent, false);
            }
            ImageView imageView = (ImageView)convertView.findViewById(R.id.gridPhoto);
            ImageLoader.getInstance().displayImage("file://" + selectedPicture.get(position),
                    imageView);
            return convertView;
        }

    }

    private class SelectPictureClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent Selectphoto = new Intent(PhotoGridViewActivity.this, SelectPictureActivity.class);

            Selectphoto.putExtra("album_name",PhotoGridViewActivity.this.album_name);
            startActivityForResult(Selectphoto, REQUEST_PICK);
        }
    }
    private class SelectBackClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent BackIntent = new Intent(PhotoGridViewActivity.this, PowerOnActivity.class);
            BackIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(BackIntent);
            PhotoGridViewActivity.this.finish();
        }
    }
    private class OnPhotoItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent SinglePhotoIntent = new Intent(PhotoGridViewActivity.this, PhotoSingleViewActivity.class);
            SinglePhotoIntent.putExtra("position_array", selectedPicture);
            SinglePhotoIntent.putExtra("position",position);
            startActivity(SinglePhotoIntent);
        }

    }
}
