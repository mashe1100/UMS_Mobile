package com.aseyel.tgbl.tristangaryleyesa.adapter;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingCoolerPlanogramActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingCoolerPlanogramFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.FeedModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class TrackingCoolerPlanogramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TrackingCoolerPlanogramAdapter.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_sovi_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Fragment fragment;
    private final List<FeedModel> ListItems = new ArrayList<>();

    public TrackingCoolerPlanogramAdapter(Fragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracking_coolerplanogram,parent,false);
            CellViewHolder mCellViewHolder = new CellViewHolder(view);
            setupClickableViews(view,mCellViewHolder);
            return mCellViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellViewHolder) viewHolder).bindView(ListItems.get(position));
    }

    private void setupClickableViews(final View view, final CellViewHolder mCellViewHolder) {
        mCellViewHolder.vTrackingCoolerPlanogramImageRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getTitle();
                    Intent i = new Intent(view.getContext(), TrackingCoolerPlanogramActivity.class);
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
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getTitle();
                    Intent i = new Intent(view.getContext(), TrackingCoolerPlanogramActivity.class);
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
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getTitle();
                    ((TrackingCoolerPlanogramFragment) fragment).DeleteData(Liquid.SelectedAccountNumber,Liquid.SelectedDescription,Liquid.SelectedPeriod);

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
                    new FeedModel(
                            Data.get(i).get("Id"),
                            Data.get(i).get("Title"),
                            Data.get(i).get("Details"),
                            Data.get(i).get("Date"),
                            Data.get(i).get("Filepath")
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

        @BindView(R.id.ivTrackingCoolerPlanogramCenter)
        ImageView ivTrackingCoolerPlanogramCenter;
        @BindView(R.id.tsTrackingCoolerPlanogramDetails)
        TextSwitcher tsTrackingCoolerPlanogramDetails;

        @BindView(R.id.vTrackingCoolerPlanogramSelected)
        View vTrackingCoolerPlanogramSelected;
        @BindView(R.id.ivTrackingCoolerPlanogramProfile)
        ImageView ivTrackingCoolerPlanogramProfile;
        @BindView(R.id.vTrackingCoolerPlanogramImageRoot)
        FrameLayout vTrackingCoolerPlanogramImageRoot;
        @BindView(R.id.tvTrackingCoolerPlanogramTitle)
        TextView tvTrackingCoolerPlanogramTitle;
        @BindView(R.id.tvTrackingCoolerPlanogramDetails)
        TextView tvTrackingCoolerPlanogramDetails;
        @BindView(R.id.tvTrackingCoolerPlanogramAging)
        TextView tvTrackingCoolerPlanogramAging;
        @BindView(R.id.ivTrackingCoolerPlanogramImage)
        ImageView ivTrackingCoolerPlanogramImage;
        @BindView(R.id.btnToDo)
        ImageButton btnToDo;
        @BindView(R.id.btnDelete)
                ImageButton btnDelete;

        FeedModel Items;

        public CellViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(FeedModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();
            int imageURL;
            tvTrackingCoolerPlanogramTitle.setText(Items.Title);
            tvTrackingCoolerPlanogramAging.setText(Items.Date);
            tsTrackingCoolerPlanogramDetails.setCurrentText("Cooler Planogram of "+Items.Title);
            ivTrackingCoolerPlanogramCenter.setImageResource(Integer.parseInt(Items.Filepath));
        }

        public FeedModel getItems() {
            return Items;
        }
    }







}
