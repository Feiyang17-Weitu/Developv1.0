package safe17.weitudevelop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

public class photo_view extends Activity {

    private TextView title = null;

    private GridView myGridView = null;

    private int[] picRes = new int[]{R.mipmap.pic_1,R.mipmap.pic_1,
            R.mipmap.pic_2,R.mipmap.pic_3,R.mipmap.pic_4,R.mipmap.pic_5,
            R.mipmap.pic_6,R.mipmap.pic_7,R.mipmap.pic_8,R.mipmap.pic_9,
            R.mipmap.pic_10,R.mipmap.pic_11,R.mipmap.pic_12,R.mipmap.pic_13};

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.setContentView(R.layout.main_photo);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        this.title = (TextView)super.findViewById(R.id.title);

        Intent it = super.getIntent();
        String album_name = it.getStringExtra("album_name");
        this.title.setText(album_name);




        this.myGridView = (GridView)super.findViewById(R.id.myGridView);
        this.myGridView.setAdapter(new ImageAdapter(this, this.picRes));

        this.myGridView.setOnItemClickListener(new OnItemClickListenertmp());


    }

    private class OnItemClickListenertmp implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            ImageView showimg = new ImageView(photo_view.this);
            showimg.setScaleType(ImageView.ScaleType.CENTER);
            showimg.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            showimg.setImageResource(photo_view.this.picRes[position]);
            Dialog dialog = new AlertDialog.Builder(photo_view.this)
                    .setIcon(R.mipmap.ic_launcher).setTitle("查看图片").setView(showimg)
                    .setNegativeButton("关闭", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    }).create();
            dialog.show();
        }

    }
}
