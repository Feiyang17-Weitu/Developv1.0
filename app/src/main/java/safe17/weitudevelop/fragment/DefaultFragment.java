package safe17.weitudevelop.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import safe17.weitudevelop.R;
import safe17.weitudevelop.adapter.FolderDataHelper;
import safe17.weitudevelop.tool.PublicData;
import safe17.weitudevelop.ui.PhotoGridViewActivity;
import safe17.weitudevelop.ui.PowerOnActivity;



public class DefaultFragment extends Fragment {

    private ListView photoList;
    private AltColorAdapter simpleAdapter;

    List<Map<String, String>> list = new ArrayList<Map<String,String>>();

    public void getListData()
    {
        FolderDataHelper Folderdb= new FolderDataHelper(getActivity().getApplicationContext());
        SQLiteDatabase db=Folderdb.getReadableDatabase();
        Cursor cursor=db.rawQuery("select name , picturenum , isprivate from Folder", null);
        while (cursor.moveToNext())
        {
            Map<String, String> map = new HashMap<String, String>();

            String tname=cursor.getString(0);
            String tmp=cursor.getInt(1) +"张照片";
            String tprivate=String.valueOf(cursor.getInt(2));
           // String
            map.put("album_name", tname);
            map.put("photo_num", tmp);
            map.put("is_private", tprivate);

            Log.i("相片数量:", map.get("photo_num"));
            Log.i("相册名字:", map.get("album_name"));
           // Log.i("是否隐私:", map.get("is_private"));
            if((PublicData.LoginInTruePasswd) || map.get("is_private").toString().equals("0"))
                this.list.add(map);
        }
        db.close();
    }

    public class AltColorAdapter extends SimpleAdapter {
        public AltColorAdapter(Context context,
                               List<? extends Map<String, ?>> data, int resource, String[] from,
                               int[] to) {
            super(getActivity(), list, R.layout.photo_list,
                    new String[]{"album_name", "photo_num"}, new int[]{R.id.album_name,R.id.photo_num});
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View localView = super.getView(position, convertView, parent);
            Map<String, String> map = new HashMap<String, String>();
            map = list.get(position);
            if (PublicData.LoginInTruePasswd) {
                if (map.get("is_private").equals("1")) {
                    ImageView true_album_pic = (ImageView) localView.findViewById(R.id.photoshow);
                    true_album_pic.setImageResource(R.mipmap.eye);
                } else {
                    ImageView true_album_pic = (ImageView) localView.findViewById(R.id.photoshow);
                    true_album_pic.setImageResource(R.mipmap.picture);
                }
            }
            return localView;
        }
    };

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
        photoList.setOnItemLongClickListener(new OnItemLongClickListener());

       getListData();
       simpleAdapter = new AltColorAdapter(getActivity(), list, R.layout.photo_list,
                new String[]{"album_name", "photo_num"}, new int[]{R.id.album_name,R.id.photo_num});
        photoList.setAdapter(simpleAdapter);
        return view;
    }

   private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
                                long id) {
            // TODO Auto-generated method stub
            //Log.i("ListView", "Item " + position);
            //Toast.makeText(getActivity().getApplicationContext(), position+"长按效果", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity()).setTitle("删除确认");
            builder.setMessage("您是否删除该文件夹？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Map<String, String> map = new HashMap<String, String>();
                    map = list.get(position);
                    if (map.get("album_name").toString().equals("默认相册")) {
                        Toast.makeText(getActivity(), "默认相册不能删除！", Toast.LENGTH_LONG).show();
                    } else {
                        String findername = map.get("album_name").toString();
                        FolderDataHelper Folderdb = new FolderDataHelper(getActivity().getApplicationContext());
                        SQLiteDatabase db = Folderdb.getReadableDatabase();
                        Folderdb.deleteFolder(db, findername);

                        //自动刷新
                        AltColorAdapter sAdapter = (AltColorAdapter) photoList.getAdapter();

                        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_LONG).show();
                        Intent act=new Intent(getActivity(),PowerOnActivity.class);
                        startActivity(act);
                    }
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }
    }

    private class OnItemClickedListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Log.i("ListView", "Item:" + position);
            Map<String, String> map = (Map<String, String>) simpleAdapter.getItem(position);
            String album_name = map.get("album_name");

            Intent main_photo = new Intent(getActivity(), PhotoGridViewActivity.class);
            main_photo.putExtra("album_name", album_name);
            getActivity().startActivity(main_photo);
        }

    }

}