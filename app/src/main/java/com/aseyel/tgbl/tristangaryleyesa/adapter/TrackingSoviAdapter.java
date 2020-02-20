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
import com.aseyel.tgbl.tristangaryleyesa.TrackingSoviActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingSoviFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingSoviModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class TrackingSoviAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TrackingSoviAdapter.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_sovi_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Fragment fragment;
    private final List<TrackingSoviModel> ListItems = new ArrayList<>();

    public TrackingSoviAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracking_sovi,parent,false);
            CellViewHolder mCellViewHolder = new CellViewHolder(view);
            setupClickableViews(view,mCellViewHolder);
            return mCellViewHolder;
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
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getProductName();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getProductType();
                    Liquid.SelectedType = ListItems.get(adapterPosition).getProductCategory();
                    Intent i = new Intent(view.getContext(), TrackingSoviActivity.class);
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
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getProductName();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getProductType();
                    Liquid.SelectedType = ListItems.get(adapterPosition).getProductCategory();
                    Intent i = new Intent(view.getContext(), TrackingSoviActivity.class);
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
                    Liquid.SelectedType = ListItems.get(adapterPosition).getProductCategory();
                    ((TrackingSoviFragment) fragment).DeleteData( Liquid.SelectedAccountNumber, Liquid.SelectedDescription,Liquid.SelectedType,Liquid.SelectedPeriod);
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
                    new TrackingSoviModel(
                            Data.get(i).get("productCode"),
                            Data.get(i).get("productName"),
                            Data.get(i).get("productType"),
                            Data.get(i).get("productCategory"),
                            Integer.parseInt(Data.get(i).get("productNumKof")),
                            Integer.parseInt(Data.get(i).get("productNumNonKof")),
                            Integer.parseInt(Data.get(i).get("productNumCans")),
                            Integer.parseInt(Data.get(i).get("productNumSspet")),
                            Integer.parseInt(Data.get(i).get("productNumMspet")),
                            Integer.parseInt(Data.get(i).get("productNumSsdoy")),
                            Integer.parseInt(Data.get(i).get("productNumSsbrick")),
                            Integer.parseInt(Data.get(i).get("productNumMsbrick")),
                            Integer.parseInt(Data.get(i).get("productNumSswedge")),
                            Integer.parseInt(Data.get(i).get("productNumBox")),
                            Integer.parseInt(Data.get(i).get("productNumLitro")),
                            Integer.parseInt(Data.get(i).get("productNumPounch")),
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
        @BindView(R.id.tsDetails)
        TextSwitcher tsDetails;
        @BindView(R.id.btnToDo)
        ImageButton btnToDo;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;
        TrackingSoviModel Items;

        public CellViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(TrackingSoviModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();
            int imageURL;
            String Details = "";
            Details = "Type : " +Items.productType+"\n" +
                      "Category : "+Items.productCategory+"\n";
            imageURL = Liquid.LiquidImages.get("img_coke_sovi_"+Items.productCode) == null ? R.drawable.img_coke : Liquid.LiquidImages.get("img_coke_sovi_"+Items.productCode);
            tvTitle.setText(Items.productName);
            tsDetails.setCurrentText(Details);
            ivProfile.setImageResource(imageURL);
        }

        public TrackingSoviModel getItems() {
            return Items;
        }
    }
}
