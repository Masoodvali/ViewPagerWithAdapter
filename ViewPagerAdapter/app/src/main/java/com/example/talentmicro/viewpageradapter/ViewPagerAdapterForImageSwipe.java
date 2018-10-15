package com.example.talentmicro.viewpageradapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class ViewPagerAdapterForImageSwipe extends PagerAdapter {
    Context context;
    ArrayList<String> urlList;

    public ViewPagerAdapterForImageSwipe(Context context, ArrayList<String> urlList) {
        this.context = context;
        this.urlList = urlList;

    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView ivImage;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.simple_view_pager_tmpl, container, false);

        ivImage = convertView.findViewById(R.id.iv_image);

        final String url = urlList.get(position);


        Picasso.with(context)
                .load(Uri.parse(url))
                .into(ivImage);



        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this will log the page number that was click
                Log.i("TAG", "This page was clicked: " + position);
            }
        });


        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.e("destroy position", "" + position);
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
