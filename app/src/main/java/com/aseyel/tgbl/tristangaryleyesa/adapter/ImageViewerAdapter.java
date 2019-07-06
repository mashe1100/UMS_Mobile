package com.aseyel.tgbl.tristangaryleyesa.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class ImageViewerAdapter extends PagerAdapter {
    Context context;
    public static ArrayList<Bitmap> GalImages = new ArrayList();

    LayoutInflater mLayoutInflater;

    public ImageViewerAdapter(Context context){
        this.context=context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return GalImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_image_pager, container, false);

        PhotoView imageView = (PhotoView) itemView.findViewById(R.id.picture);
        imageView.setImageBitmap(GalImages.get(position));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}