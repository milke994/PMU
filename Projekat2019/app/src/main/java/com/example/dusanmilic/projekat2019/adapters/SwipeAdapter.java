package com.example.dusanmilic.projekat2019.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dusanmilic.projekat2019.R;

import java.util.ArrayList;

public class SwipeAdapter extends PagerAdapter {

    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private Context context;
    private int teamSelected;

    public SwipeAdapter(Context context) {
        this.context = context;

        images.add(R.drawable.argentina);
        names.add(context.getString(R.string.Argentina));

        images.add(R.drawable.brazil);
        names.add(context.getString(R.string.Brazil));

        images.add(R.drawable.england);
        names.add(context.getString(R.string.England));

        images.add(R.drawable.france);
        names.add(context.getString(R.string.France));

        images.add(R.drawable.germany);
        names.add(context.getString(R.string.Germany));

        images.add(R.drawable.italy);
        names.add(context.getString(R.string.Italy));

        images.add(R.drawable.portugal);
        names.add(context.getString(R.string.Portugal));

        images.add(R.drawable.serbia);
        names.add(context.getString(R.string.Serbia));

        images.add(R.drawable.spain);
        names.add(context.getString(R.string.Spain));

        images.add(R.drawable.switzerland);
        names.add(context.getString(R.string.Switzerland));

        teamSelected = R.drawable.argentina;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = view.findViewById(R.id.viewPagerImageView);
        TextView textView = view.findViewById(R.id.viewPagerTextView);
        imageView.setImageResource(images.get(position));
        textView.setText(names.get(position));
        container.addView(view);
        teamSelected = images.get(position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    public int getTeamSelected(int position) {
        return images.get(position);
    }


}
