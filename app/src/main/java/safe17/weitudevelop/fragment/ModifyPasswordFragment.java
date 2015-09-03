package safe17.weitudevelop.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import safe17.weitudevelop.R;

/**
 * Created by wangxubing on 2015/8/29.
 * Modified by Lidecai on 2015/8/31.
 */
public class ModifyPasswordFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        ImageView button =  (ImageView) getActivity().findViewById(R.id.btnDrawer);
//        button.setImageResource(R.mipmap.navigation_back);


        return inflater.inflate(R.layout.fragment_modify_password, null);
    }

}
