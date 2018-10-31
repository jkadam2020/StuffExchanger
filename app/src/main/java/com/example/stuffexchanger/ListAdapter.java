package com.example.stuffexchanger;

/**
 * Created by jitu on 4/21/2017.
 */

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Item> {

    private List<Item> items;
    int c;

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        c=0;
    }
    public ListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);

        this.items = items;
        c=0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        Item p = getItem(position);

        if (p != null) {

            ImageView iv = (ImageView) v.findViewById(R.id.imageView);
            TextView itemname = (TextView) v.findViewById(R.id.itemname);
            TextView price = (TextView) v.findViewById(R.id.price);
            TextView pdate = (TextView) v.findViewById(R.id.pDateTextView);
            TextView postedby = (TextView) v.findViewById(R.id.postedbyTextView);
            RatingBar rb = (RatingBar) v.findViewById(R.id.ratingBar);

            if (iv != null) {
                iv.setImageBitmap(DbBitmapUtility.getImage(p.getImage()));
            }

            if (itemname != null) {
                String t1=itemname.getText().toString();
                itemname.setText(t1+p.getItemname());
            }
            if (price != null) {
                String t2=price.getText().toString();
                price.setText(t2+" $"+Double.toString(p.getPrice()));
            }
            if(pdate!=null) {
                pdate.setText("Date:"+p.getPdate());
            }
            if(postedby!=null) {
                postedby.setText("Posted By:"+p.getUsername());
            }
            if(rb!=null) {
                rb.setRating((float)p.getRating());
            }

        }

        return v;
    }
}

