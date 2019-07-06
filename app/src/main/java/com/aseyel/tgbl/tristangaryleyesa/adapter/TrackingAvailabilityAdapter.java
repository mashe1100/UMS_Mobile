package com.aseyel.tgbl.tristangaryleyesa.adapter;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingAvailabilityActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingAvailabilityFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingAvailabilityModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class TrackingAvailabilityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TrackingAvailabilityAdapter.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_availability_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Fragment fragment;
    private final List<TrackingAvailabilityModel> ListItems = new ArrayList<>();

    public TrackingAvailabilityAdapter(Fragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracking_availability,parent,false);
            CellViewHolder mCellViewHolder = new CellViewHolder(view);
            setupClickableViews(view,mCellViewHolder);
            return mCellViewHolder;
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return  null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellViewHolder) viewHolder).bindView(ListItems.get(position));
    }

    private void setupClickableViews(final View view, final CellViewHolder mCellViewHolder) {

        mCellViewHolder.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getProductCode();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getProductName();
                    Intent i = new Intent(view.getContext(), TrackingAvailabilityActivity.class);
                    view.getContext().startActivity(i);
                }
                catch(Exception e){
                    Log.e(TAG,"Error ",e);
                }

            }
        });

        mCellViewHolder.btnToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getProductCode();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getProductName();
                    Intent i = new Intent(view.getContext(), TrackingAvailabilityActivity.class);
                    view.getContext().startActivity(i);
                }
                catch(Exception e){
                    Log.e(TAG,"Error ",e);
                }

            }
        });


        mCellViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getProductCode();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getProductName();
                    ((TrackingAvailabilityFragment) fragment).DeleteData( Liquid.SelectedAccountNumber, Liquid.SelectedCode,Liquid.SelectedPeriod);
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
                    new TrackingAvailabilityModel(
                            Data.get(i).get("productCode"),
                            Data.get(i).get("productName"),
                            Data.get(i).get("productPrice"),
                            Data.get(i).get("productType"),
                            Data.get(i).get("productComment"),
                            Data.get(i).get("productTimeStamp"),
                            Data.get(i).get("productPicturePath"),
                            Data.get(i).get("productModifiedby")
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
        @BindView(R.id.llDetails)
        LinearLayout llDetails;
        @BindView(R.id.ivProfile)
        ImageView ivProfile;
        @BindView(R.id.tvDetails)
        TextView tvDetails;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tsDetails)
        TextSwitcher tsDetails;
        @BindView(R.id.btnToDo)
        ImageButton btnToDo;
        @BindView(R.id.btnDelete)
                ImageButton btnDelete;
        TrackingAvailabilityModel Items;

        public CellViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(TrackingAvailabilityModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();
            int imageURL;
            String Details = "";
            try{

                //imageURL = Liquid.LiquidImages.get("img_coke_product_"+Items.productCode) == null ? R.drawable.img_toolbar_logo : Liquid.LiquidImages.get("img_coke_product_"+Items.productCode);
                imageURL = Liquid.LiquidImages.get("img_coke_product_"+Items.productCode) == null ? R.drawable.img_coke : Liquid.LiquidImages.get("img_coke_product_"+Items.productCode);
                Details =   "Code : "+ Items.productCode + "\n"+
                            "Type : "+ Items.productType + "\n";

                tvTitle.setText(Items.productName);
                tsDetails.setCurrentText(Details);
                ivProfile.setImageResource(imageURL);

            }catch(Exception e){
                Log.e(TAG,"Error ",e);
            }

        }

        public TrackingAvailabilityModel getItems() {
            return Items;
        }
    }







}
