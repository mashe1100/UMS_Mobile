package com.aseyel.tgbl.tristangaryleyesa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aseyel.tgbl.tristangaryleyesa.adapter.ImageViewerAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.services.GalleryViewPager;

import butterknife.BindView;

public class TrackingFullScreenImageActivity extends AppCompatActivity {
    private static final String TAG = TrackingFullScreenImageActivity.class.getSimpleName();
    @BindView(R.id.image_preview)
    GalleryViewPager image_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tracking_full_screen_image);
            setup();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.BLACK);
            }
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }

    private void setup(){
        Bitmap bmp = BitmapFactory.decodeFile(Liquid.SelectedImage);
        ImageViewerAdapter adapter = new ImageViewerAdapter(this);
        adapter.GalImages = Liquid.SelectedGallery;
        image_preview = (GalleryViewPager) findViewById(R.id.image_preview);
        image_preview.setAdapter(adapter);
        image_preview.setCurrentItem(Liquid.SelectedImagePosition);
//        image_preview = (ImageView) findViewById(R.id.image_preview);
//        Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 155, 155, true);
//        image_preview.setImageBitmap(bmp);
    }
}
