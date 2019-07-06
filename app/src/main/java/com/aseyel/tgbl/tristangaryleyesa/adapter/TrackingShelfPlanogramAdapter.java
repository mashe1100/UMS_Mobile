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
import com.aseyel.tgbl.tristangaryleyesa.TrackingShelfPlanogramActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingShelfPlanogramFragment;
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

public class TrackingShelfPlanogramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TrackingShelfPlanogramAdapter.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_sovi_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Fragment fragment;
    private final List<FeedModel> ListItems = new ArrayList<>();

    public TrackingShelfPlanogramAdapter(Fragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracking_shelfplanogram,parent,false);
            CellViewHolder mCellViewHolder = new CellViewHolder(view);
            setupClickableViews(view,mCellViewHolder);
            return mCellViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellViewHolder) viewHolder).bindView(ListItems.get(position));
    }

    private void setupClickableViews(final View view, final CellViewHolder mCellViewHolder) {
        mCellViewHolder.vTrackingShelfPlanogramImageRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();
                    Liquid.SelectedDescription = ListItems.get(adapterPosition).getTitle();
                    Intent i = new Intent(view.getContext(), TrackingShelfPlanogramActivity.class);
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
                    Intent i = new Intent(view.getContext(), TrackingShelfPlanogramActivity.class);
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
                    ((TrackingShelfPlanogramFragment) fragment).DeleteData(Liquid.SelectedAccountNumber,Liquid.SelectedDescription,Liquid.SelectedPeriod);
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

        @BindView(R.id.ivTrackingShelfPlanogramCenter)
        ImageView ivTrackingShelfPlanogramCenter;
        @BindView(R.id.tsTrackingShelfPlanogramDetails)
        TextSwitcher tsTrackingShelfPlanogramDetails;

        @BindView(R.id.vTrackingShelfPlanogramSelected)
        View vTrackingShelfPlanogramSelected;
        @BindView(R.id.ivTrackingShelfPlanogramProfile)
        ImageView ivTrackingShelfPlanogramProfile;
        @BindView(R.id.vTrackingShelfPlanogramImageRoot)
        FrameLayout vTrackingShelfPlanogramImageRoot;
        @BindView(R.id.tvTrackingShelfPlanogramTitle)
        TextView tvTrackingShelfPlanogramTitle;
        @BindView(R.id.tvTrackingShelfPlanogramDetails)
        TextView tvTrackingShelfPlanogramDetails;
        @BindView(R.id.tvTrackingShelfPlanogramAging)
        TextView tvTrackingShelfPlanogramAging;
        @BindView(R.id.ivTrackingShelfPlanogramImage)
        ImageView ivTrackingShelfPlanogramImage;
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
            tvTrackingShelfPlanogramTitle.setText(Items.Title);
            tvTrackingShelfPlanogramAging.setText(Items.Date);
            tsTrackingShelfPlanogramDetails.setCurrentText("Shelf Planogram of "+Items.Title);
            ivTrackingShelfPlanogramCenter.setImageResource(Integer.parseInt(Items.Filepath));
        }

        public FeedModel getItems() {
            return Items;
        }
    }







}
