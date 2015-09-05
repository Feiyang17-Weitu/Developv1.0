package safe17.weitudevelop.fragment;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import safe17.weitudevelop.R;

/**
 * Created by lidecai on 2015/8/31.
 */
public class AboutUsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //修改title_bar的背景图片
        ImageView button =  (ImageView) getActivity().findViewById(R.id.btnDrawer);
        button.setImageResource(R.mipmap.navigation_back);
        ImageView right_button =  (ImageView) getActivity().findViewById(R.id.add_album);
        right_button.setVisibility(View.INVISIBLE);
        TextView tittle_text = (TextView) getActivity().findViewById(R.id.bar_title);
        tittle_text.setText("关于");
        return inflater.inflate(R.layout.fragment_aboutus, null);
    }
}
