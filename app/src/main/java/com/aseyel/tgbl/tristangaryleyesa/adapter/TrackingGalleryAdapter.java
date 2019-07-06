package com.aseyel.tgbl.tristangaryleyesa.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingFullScreenImageActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.ImageModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class TrackingGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TrackingGalleryAdapter.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_availability_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Context context;
    private final List<ImageModel> ListItems = new ArrayList<>();

    public TrackingGalleryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracking_images,parent,false);
            CellViewHolder mCellViewHolder = new CellViewHolder(view);
            setupClickableViews(view,mCellViewHolder);
            return mCellViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellViewHolder) viewHolder).bindView(ListItems.get(position));
    }

    private void setupClickableViews(final View view, final CellViewHolder mCellViewHolder) {
        mCellViewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Liquid.SelectedImagePosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedImage = ListItems.get(Liquid.SelectedImagePosition).getFilepath();
                    Liquid.SelectedGallery = new ArrayList();
                    for (int i=0; i< ListItems.size(); i++)
                        Liquid.SelectedGallery.add(BitmapFactory.decodeFile(ListItems.get(i).getFilepath()));

                    Intent i = new Intent(view.getContext(), TrackingFullScreenImageActivity.class);
                    view.getContext().startActivity(i);

                }
                catch(Exception e){
                    Log.e(TAG,"Error ",e);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    public void updateItems(boolean animated,ArrayList<HashMap<String, String>> Data) {
        ListItems.clear();
        for(int i = 0; i < Data.size(); i++) {

            ListItems.addAll(Arrays.asList(
                    new ImageModel(
                            Data.get(i).get("FilePath"),
                            Data.get(i).get("Filaname")
                        )
                    )
            );
        }
        if(animated){
            notifyItemRangeInserted(0, ListItems.size());
        }else{
            notifyDataSetChanged();
        }
    }

    public static class CellViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;
        ImageModel Items;

        public CellViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(ImageModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();
            try{

                Bitmap bmp = BitmapFactory.decodeFile(Items.getFilepath());
                double height = Liquid.screenHeight  * 0.20;
                double width = Liquid.screenWidth  * 0.10;
                //Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp,  (int)height, (int)height, true);
                Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp,  (int)height, (int)height, true);

                ivImage.setImageBitmap(bMapScaled);

            }catch(Exception e){
                Log.e(TAG,"Error ",e);
            }
        }

        public ImageModel getItems() {
            return Items;
        }
    }







}
