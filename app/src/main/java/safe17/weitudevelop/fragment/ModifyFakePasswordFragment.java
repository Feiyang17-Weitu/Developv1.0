package safe17.weitudevelop.fragment;

/**
 * Created by wangxubing on 2015/8/29.
 * Modified by Lidecai on 2015/8/31.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import safe17.weitudevelop.tool.PublicData;
import safe17.weitudevelop.tool.SharePrefrencesTools;
import safe17.weitudevelop.R;
import safe17.weitudevelop.ui.MainActivity;
import safe17.weitudevelop.ui.PowerOnActivity;

public class ModifyFakePasswordFragment extends Fragment
{
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

        final View view= inflater.inflate(R.layout.fragment_modify_fakepassword, container, false);
        //绑定对话框和确定按钮
        oldFakePasswd = (EditText)view.findViewById(R.id.old_fake_password);
        newFakePasswd = (EditText)view.findViewById(R.id.new_fake_password);
        BtnModifyPasswd = (Button)view.findViewById(R.id.BtnOk);

        mTools = new SharePrefrencesTools(getActivity(), "password_info");
        if(PublicData.LoginInTruePasswd)
        {
            tittle_text.setText("修改伪密码");
            TextView origin=(TextView) view.findViewById(R.id.originPasswd);
            origin.setVisibility(view.GONE);
            oldFakePasswd.setVisibility(view.GONE);
        }
        else
            tittle_text.setText("修改密码");


        BtnModifyPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldFakePasswdStr = oldFakePasswd.getText().toString();
                newFakePasswdStr = newFakePasswd.getText().toString();

                if(PublicData.LoginInTruePasswd)
                {
                    if(mTools.getTurePassword().equals(newFakePasswdStr))
                    {
                        Toast.makeText(getActivity(), "密码和伪密码不能重复！", Toast.LENGTH_LONG).show();
                    }
                    else {
                        mTools.saveFakePassword(newFakePasswdStr);
                        Toast.makeText(getActivity(), "修改伪密码成功！", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    //5次尝试机会
                    if(TryPasswd > 0) {
                        //与原密码不符的情况
                        if (!mTools.getFakePassword().equals(oldFakePasswdStr)) {
                            TryPasswd--;
                            Toast.makeText(getActivity(), "你输入的密码和原密码不符合，请重新输入!您还有" + TryPasswd + "次机会.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if(newFakePasswdStr.equals("")==true || newFakePasswdStr.equals("")==true)
                            {
                                Toast.makeText(getActivity(), "您输入的密码为空，请重新输入..", Toast.LENGTH_LONG).show();
                            }
                            if(newFakePasswdStr==oldFakePasswdStr)
                            {
                                mTools.saveFakePassword(newFakePasswdStr);
                                Toast.makeText(getActivity(), "修改密码成功！", Toast.LENGTH_LONG).show();

                                getActivity();
                            }
                        }

                     }
                    else
                    {
                        Toast.makeText(getActivity(), "您尝试的次数过多.请仔细回忆后再次输入", Toast.LENGTH_LONG).show();
                        Intent act=new Intent(getActivity(),MainActivity.class);
                        startActivity(act);
                    }

                }


            }
        });

        return view;
    }
}