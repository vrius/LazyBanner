package com.vrius.lazybanner;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Context   mContext;
    private List<Integer> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mList.add(R.mipmap.a);
        mList.add(R.mipmap.b);
        mList.add(R.mipmap.c);
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        mViewPager.setOffscreenPageLimit(mList.size());
        mViewPager.setPageTransformer(true,new CoverModeTransformer(mViewPager));
        mViewPager.setAdapter(new LazyPagerAdapter(mList));

    }

    public class LazyPagerAdapter extends PagerAdapter{
        private List<Integer> mData;

        public LazyPagerAdapter(List<Integer> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE/2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mContext);

            int lazyPosition;
            if (position>=0 && position<mData.size()){
                lazyPosition = position;
            }else{
                lazyPosition = position%mData.size();
            }

            view.setImageBitmap(BitmapFactory.decodeResource(getResources(),mData.get(lazyPosition)));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
