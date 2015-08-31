package safe17.weitudevelop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private Context context = null;
    private int[] picids = null;

    public ImageAdapter(Context context,int[] picids){
        this.context = context;
        this.picids = picids;
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.picids.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.picids[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return this.picids[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ImageView img = new ImageView(this.context);
        img.setImageResource(this.picids[position]);
        img.setScaleType(ImageView.ScaleType.CENTER);


        return img;
    }

}
