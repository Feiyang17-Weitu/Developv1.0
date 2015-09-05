package safe17.weitudevelop.fragment;

/**
 * Created by wangxubing on 2015/8/29.
 * Modified by Lidecai on 2015/8/31.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import safe17.weitudevelop.R;

public class ModifyFakePasswordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //修改title_bar的背景图片
        ImageView left_button =  (ImageView) getActivity().findViewById(R.id.btnDrawer);
        left_button.setImageResource(R.mipmap.navigation_back);
        ImageView right_button =  (ImageView) getActivity().findViewById(R.id.add_album);
        right_button.setVisibility(View.INVISIBLE);
        TextView tittle_text = (TextView) getActivity().findViewById(R.id.bar_title);
        tittle_text.setText("修改伪密码");



        return inflater.inflate(R.layout.fragment_modify_fakepassword, null);
    }
}