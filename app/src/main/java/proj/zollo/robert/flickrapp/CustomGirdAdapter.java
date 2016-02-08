package proj.zollo.robert.flickrapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomGirdAdapter extends BaseAdapter
{
    private Context mContext;
    private ArrayList<String> gridData = new ArrayList<String>();

    public CustomGirdAdapter(Context mContext, ArrayList<String> mGridData) {
        this.mContext = mContext;
        this.gridData = mGridData;
    }

    public int getCount() {
        return gridData.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView  = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else {
            imageView = (ImageView) convertView;
        }
        String[] array= new String[gridData.size()];
        gridData.toArray(array);
        System.out.println(position+" "+array[position]);
        Picasso.with(mContext).load(array[position]).fit().centerCrop().into(imageView);

        return imageView;
    }
}
