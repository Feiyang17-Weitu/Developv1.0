package safe17.weitudevelop.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safe17.weitudevelop.R;
import safe17.weitudevelop.adapter.FolderDataHelper;
import safe17.weitudevelop.ui.PhotoGridViewActivity;
import safe17.weitudevelop.ui.PowerOnActivity;



public class DefaultFragment extends Fragment {

    private ListView photoList;
    private SimpleAdapter simpleAdapter;


   // public String data[][] = new String[][]{{"默认相册","1张照片"}};
   List<Map<String, String>> list = new ArrayList<Map<String,String>>();

    public void getListData()
    {
        FolderDataHelper Folderdb= new FolderDataHelper(getActivity().getApplicationContext());
        SQLiteDatabase db=Folderdb.getReadableDatabase();
        Cursor cursor=db.rawQuery("select name,picturenum from Folder", null);
        while (cursor.moveToNext())
        {
            Map<String, String> map = new HashMap<String, String>();
            String tmp=cursor.getInt(1) +"张照片";
            Log.i(tmp,tmp);
            map.put("album_name", cursor.getString(0));
            map.put("photo_num",tmp);
            this.list.add(map);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //修改title_bar的背景图片
        ImageView button =  (ImageView) getActivity().findViewById(R.id.btnDrawer);
        button.setImageResource(R.mipmap.list);
        ImageView right_button =  (ImageView) getActivity().findViewById(R.id.add_album);
        right_button.setVisibility(View.VISIBLE);
        TextView tittle_text = (TextView) getActivity().findViewById(R.id.bar_title);
        tittle_text.setText("维图相册");

        View view = inflater.inflate(R.layout.fragment_default, null);
        photoList = (ListView) view.findViewById(R.id.photo_list);
        photoList.setOnItemClickListener(new OnItemClickedListener());
        /*
        for(int x=0;x < data.length;x++){
            Map<String, String> map = new HashMap<String, String>();
            map.put("album_name", String.valueOf(data[x][0]));
            map.put("photo_num", String.valueOf(data[x][1]));
            this.list.add(map);
        }*/
       getListData();
       simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.photo_list,
                new String[]{"album_name", "photo_num"}, new int[]{R.id.album_name,R.id.photo_num});
        photoList.setAdapter(simpleAdapter);

        return view;
    }

/*
    @Override
    public void onResume()
    {
            super.onResume();
            photoList.setAdapter(null);

            getListData();
            simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.photo_list,
                    new String[]{"album_name", "photo_num"}, new int[]{R.id.album_name,R.id.photo_num});
         //Log.i(getActivity().toString(), getActivity().toString());
            photoList.setAdapter(simpleAdapter);

    }*/



    private class OnItemClickedListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Log.i("ListView", "Item " + position);
            Map<String, String> map = (Map<String, String>) simpleAdapter.getItem(position);
            String album_name = map.get("album_name");

            Intent main_photo = new Intent(getActivity(), PhotoGridViewActivity.class);
            main_photo.putExtra("album_name", album_name);
            getActivity().startActivity(main_photo);
        }

    }

}