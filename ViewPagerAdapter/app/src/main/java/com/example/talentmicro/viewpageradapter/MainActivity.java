package com.example.talentmicro.viewpageradapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    int currentPage = 0;
    Timer timer = new Timer();
    final long DELAY_MS = 10000;
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        internetPeemission();
        populateAdapter();

    }

    private void internetPeemission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.INTERNET}, 1);
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateAdapter() {
        try {
            ArrayList<String> urlList = new ArrayList<>();
            urlList.add("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
            urlList.add("https://homepages.cae.wisc.edu/~ece533/images/baboon.png");
            urlList.add("https://homepages.cae.wisc.edu/~ece533/images/cat.png");
            urlList.add("https://homepages.cae.wisc.edu/~ece533/images/monarch.png");
            urlList.add("https://homepages.cae.wisc.edu/~ece533/images/girl.png");

            ArrayList<MainListDto> dataList = new ArrayList<>();
            MainListDto mainListDto = new MainListDto();
            mainListDto.setName("Masood");
            mainListDto.setUrlList(urlList);
            dataList.add(mainListDto);

            MainListDto mainListDto1 = new MainListDto();
            mainListDto1.setName("Sujit");
            mainListDto1.setUrlList(urlList);
            dataList.add(mainListDto1);

            MainListDto mainListDto2 = new MainListDto();
            mainListDto2.setName("Akash");
            mainListDto2.setUrlList(urlList);
            dataList.add(mainListDto2);

            MainListDto mainListDto3 = new MainListDto();
            mainListDto3.setName("Bhatt");
            mainListDto3.setUrlList(urlList);
            dataList.add(mainListDto3);


            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, dataList);

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(viewPagerAdapter);
            recyclerView.setAdapter(viewPagerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
        ArrayList<MainListDto> MainList;
        Context context;

        public ViewPagerAdapter(Context context, ArrayList<MainListDto> MainList) {
            this.MainList = MainList;
            this.context = context;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.view_pager_tmpl, viewGroup, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final MainListDto rowitem = MainList.get(position);
            final ArrayList<String> urlList = rowitem.getUrlList();
            holder.textView.setText(rowitem.getName());
            Log.e("size", "" + rowitem.getUrlList().size());
//            Picasso.with(context).load(Uri.parse(s)).into(holder.imageView);

            ViewPagerAdapterForImageSwipe viewPagerAdapter = new ViewPagerAdapterForImageSwipe(context,
                    urlList);
            holder.viewPager.setAdapter(viewPagerAdapter);
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == urlList.size()) {
                        currentPage = 0;
                    }
                    holder.viewPager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);


        }

        @Override
        public int getItemCount() {
            return MainList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView textView;
            private ViewPager viewPager;

            public ViewHolder(View view) {
                super(view);
//                imageView = view.findViewById(R.id.iv_image);
                viewPager = view.findViewById(R.id.view_pager);
                textView = view.findViewById(R.id.text);
            }
        }
    }
}
