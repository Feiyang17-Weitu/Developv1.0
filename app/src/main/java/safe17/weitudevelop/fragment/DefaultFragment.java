package safe17.weitudevelop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safe17.weitudevelop.R;
import safe17.weitudevelop.ui.PhotoViewActivity;

/**
 * Created by ouxuewen on 2015/9/1.
 */
public class DefaultFragment extends Fragment {

    private ListView photoList;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, String>> list = new ArrayList<Map<String,String>>();
    private String data[][] = new String[][]{{"相册1","4张照片"},
            {"相册2","5张照片"},{"默认相册","1张照片"}};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default, null);
        photoList = (ListView) view.findViewById(R.id.photo_list);

        photoList.setOnItemClickListener(new OnItemClickedListener());
        for(int x=0;x < data.length;x++){
            Map<String, String> map = new HashMap<String, String>();
            map.put("album_name", String.valueOf(data[x][0]));
            map.put("photo_num", String.valueOf(data[x][1]));
            this.list.add(map);
        }

        simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.photo_list,
                new String[]{"album_name", "photo_num"}, new int[]{R.id.album_name,R.id.photo_num});
        photoList.setAdapter(simpleAdapter);

        return view;
    }

    private class OnItemClickedListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Log.i("ListView", "Item " + position);
            Map<String, String> map = (Map<String, String>) simpleAdapter.getItem(position);
            String album_name = map.get("album_name");

            Intent main_photo = new Intent(getActivity(), PhotoViewActivity.class);
            main_photo.putExtra("album_name", album_name);
            getActivity().startActivity(main_photo);
        }

    }
}