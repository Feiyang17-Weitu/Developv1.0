package safe17.weitudevelop.fragment;

/**
 * Created by wangxubing on 2015/8/29.
 * Modified by Lidecai on 2015/8/31.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import safe17.weitudevelop.tool.SharePrefrencesTools;
import safe17.weitudevelop.R;

public class ModifyFakePasswordFragment extends Fragment {

    EditText oldFakePasswd;
    EditText newFakePasswd;
    Button BtnModifyPasswd;
    String oldFakePasswdStr;
    String newFakePasswdStr;
    SharePrefrencesTools mTools;
    int TryPasswd=5;

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

        View view= inflater.inflate(R.layout.fragment_modify_fakepassword, container, false);
        //绑定对话框和确定按钮
        oldFakePasswd = (EditText)view.findViewById(R.id.old_fake_password);
        newFakePasswd = (EditText)view.findViewById(R.id.new_fake_password);
        BtnModifyPasswd = (Button)view.findViewById(R.id.BtnOk);

        mTools = new SharePrefrencesTools(getActivity(), "password_info");

        BtnModifyPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldFakePasswdStr = oldFakePasswd.getText().toString();
                newFakePasswdStr = newFakePasswd.getText().toString();
                //5次尝试机会
                if(TryPasswd > 0) {
                    //与原密码不符的情况
                    if (!mTools.getFakePassword().equals(oldFakePasswdStr)) {
                        TryPasswd--;
                        Toast.makeText(getActivity(), "你输入的密码和原密码不符合，请重新输入!您还有" + TryPasswd + "次机会.", Toast.LENGTH_LONG).show();
                    }
                    //没有输入密码的情况
                    else if(newFakePasswdStr.equals("")==true)
                    {
                        Toast.makeText(getActivity(), "您输入的密码为空，请重新输入..", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        mTools.saveFakePassword(newFakePasswdStr);
                        Toast.makeText(getActivity(), "修改密码成功！", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "您尝试的次数过多.请仔细回忆后再次输入", Toast.LENGTH_LONG).show();

                }

            }
        });

        return view;
    }
}