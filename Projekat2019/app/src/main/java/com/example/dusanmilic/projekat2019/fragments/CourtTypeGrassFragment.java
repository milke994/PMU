package com.example.dusanmilic.projekat2019.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dusanmilic.projekat2019.R;


public class CourtTypeGrassFragment extends Fragment {

    private ImageView courtImage;
    private TextView courtImageDescription;


    public CourtTypeGrassFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_court_grass_type,container, false);

        courtImage = view.findViewById(R.id.fragmentCourtTypeImageViewId);
        courtImageDescription = view.findViewById(R.id.fragmentCourtTypeTextViewId);

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.grass_court);
        courtImage.setImageBitmap(bitmap);

        courtImageDescription.setText(R.string.grass);
        return view;
    }


}
