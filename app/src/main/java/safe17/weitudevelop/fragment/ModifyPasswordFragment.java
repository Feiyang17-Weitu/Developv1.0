package safe17.weitudevelop.fragment;

import android.content.res.Resources;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import safe17.weitudevelop.tool.SharePrefrencesTools;
import safe17.weitudevelop.R;

/**
 * Created by wangxubing on 2015/8/29.
 * Modified by Lidecai on 2015/8/31.
 */
public class ModifyPasswordFragment extends Fragment
{
    EditText SavedPasswd;
    EditText NewPasswd;
    Button BtnModifyPasswd;
    String SavedPasswdStr;
    String NewPasswdStr;
    SharePrefrencesTools mTools;
    int TryPasswd=5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //修改title_bar的背景图片
        ImageView button =  (ImageView) getActivity().findViewById(R.id.btnDrawer);
        button.setBackgroundResource(R.mipmap.navigation_back);
        ImageView right_button =  (ImageView) getActivity().findViewById(R.id.add_album);
        right_button.setVisibility(View.INVISIBLE);
        TextView tittle_text = (TextView) getActivity().findViewById(R.id.bar_title);
        tittle_text.setText("修改密码");


        View view= inflater.inflate(R.layout.fragment_modify_password,container,false);
        //绑定对话框和确定按钮
        SavedPasswd=(EditText)view.findViewById(R.id.SavedPasswd);
        NewPasswd=(EditText)view.findViewById(R.id.NewPasswd);
        BtnModifyPasswd=(Button)view.findViewById(R.id.BtnModifyPasswd);
        //----绑定完毕

        mTools = new SharePrefrencesTools(getActivity(), "true_password_info");

        BtnModifyPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedPasswdStr=SavedPasswd.getText().toString();
                NewPasswdStr=NewPasswd.getText().toString();
                //5次尝试机会
                if(TryPasswd > 0) {
                    //与原密码不符的情况
                    if (!mTools.getTurePassword().equals(SavedPasswdStr)) {
                        TryPasswd--;
                        Toast.makeText(getActivity(), "你输入的密码和原密码不符合，请重新输入!您还有" + TryPasswd + "次机会.", Toast.LENGTH_LONG).show();
                    }
                    //没有输入密码的情况
                    else if(SavedPasswdStr.equals(""))
                    {
                        Toast.makeText(getActivity(), "您输入的密码为空，请重新输入..", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        mTools.saveTurePassword(NewPasswdStr);
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
