package com.xu.xbannerview;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.xu.xbannerview.adapter.XAdapter;
import com.xu.xbannerview.view.XBannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ImageView> mList;
    ViewPager viewPager;
    XAdapter xAdapter;
    private XBannerView xBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);
        xBannerView = findViewById(R.id.xbannerview);

        mList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
            mList.add(imageView);
        }

        viewPager = findViewById(R.id.viewpager);
        xAdapter = new XAdapter(mList);
        viewPager.setAdapter(xAdapter);
        xBannerView.bindWithViewPager(viewPager);

    }
}
