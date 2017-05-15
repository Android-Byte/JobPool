package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dell.job.R;

/**
 * Created by Suraj shakya on 11/8/16.
 * shakyasuraj08@mail.com
 */
public class SearchAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;

    public SearchAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        layoutInflater = LayoutInflater.from(context);
        final ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
          convertView = layoutInflater.inflate(R.layout.searchlist_infilater,null);

          convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder{

    }
}
