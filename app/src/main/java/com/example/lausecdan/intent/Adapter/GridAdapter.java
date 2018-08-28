package com.example.lausecdan.intent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lausecdan.intent.R;

public class GridAdapter extends BaseAdapter{

    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public GridAdapter(Context context, String[] osNameList, int[] osImages) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        this.context=context;
        imageId=osImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        private TextView txtName;
        private ImageView imgSubject;
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder=new ViewHolder();
        View rowView;
        rowView=inflater.inflate(R.layout.custom_grid,null);
        holder.txtName=rowView.findViewById(R.id.txtTypeName);
        holder.imgSubject=rowView.findViewById(R.id.imgSubject);
        holder.txtName.setText(result[position]);
        holder.imgSubject.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You clicked"+result[position],Toast.LENGTH_SHORT).show();

            }
        });
        return rowView;
    }
}
