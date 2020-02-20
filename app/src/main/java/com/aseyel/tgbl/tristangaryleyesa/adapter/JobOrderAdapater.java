package com.aseyel.tgbl.tristangaryleyesa.adapter;


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
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabCloudFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.JobOrdersModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class JobOrderAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = "JobOrderAdapater";
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_download_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Fragment fragment;
    View view;
    private final List<JobOrdersModel> JobOrdersListItems = new ArrayList<>();

    public JobOrderAdapater(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joborder,parent,false);
            CellJobOrderViewHolder mCellJobOrderViewHolder = new CellJobOrderViewHolder(view);
            setupClickableViews(view,mCellJobOrderViewHolder);
            return mCellJobOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellJobOrderViewHolder) viewHolder).bindView(JobOrdersListItems.get(position));
    }

    private void setupClickableViews(final View view, final CellJobOrderViewHolder mCellJobOrderViewHolder) {
        mCellJobOrderViewHolder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                int adapterPosition = mCellJobOrderViewHolder.getAdapterPosition();
                    String JobOrderId =  JobOrdersListItems.get(adapterPosition).getJobOrderId();
                    String date =  JobOrdersListItems.get(adapterPosition).getDate();
                    String title =  JobOrdersListItems.get(adapterPosition).getTitle();
                    String details =  JobOrdersListItems.get(adapterPosition).getDetails();
                    String cycle =  JobOrdersListItems.get(adapterPosition).getCycle();
                    notifyItemChanged(adapterPosition, ACTION_DOWNLOAD_BUTTON_CLICKED);

                    if (fragment instanceof TabCloudFragment) {
                       /* if(((TabCloudFragment) fragment).doDownloadJobOrder(JobOrderId,date,title,details) == true) {
                            ((TabCloudFragment) fragment).new GetJobOrderListDetails().execute(JobOrderId);
                        }*/
                        ((TabCloudFragment) fragment).DoDownload(JobOrderId,date,title,details,cycle);
                    }
                }
                catch(Exception e){
                    Log.e(TAG,"Error : ",e);
                }
            }
        });

        mCellJobOrderViewHolder.btnOtherDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellJobOrderViewHolder.getAdapterPosition();
                    String JobOrderId =  JobOrdersListItems.get(adapterPosition).getJobOrderId();
                    String date =  JobOrdersListItems.get(adapterPosition).getDate();
                    String title =  JobOrdersListItems.get(adapterPosition).getTitle();
                    String details =  JobOrdersListItems.get(adapterPosition).getDetails();
                    String cycle =  JobOrdersListItems.get(adapterPosition).getCycle();
                    notifyItemChanged(adapterPosition, ACTION_DOWNLOAD_BUTTON_CLICKED);
                    if (fragment instanceof TabCloudFragment) {
                       /* if(((TabCloudFragment) fragment).doDownloadJobOrder(JobOrderId,date,title,details) == true) {
                            ((TabCloudFragment) fragment).new GetJobOrderListDetails().execute(JobOrderId);
                        }*/
                        ((TabCloudFragment) fragment).DoOtherDownload(JobOrderId,date,title,details,cycle);
                    }
                }
                catch(Exception e){
                    Log.e(TAG,"Error : ",e);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return JobOrdersListItems.size();
    }

    public void updateItems(boolean animated,ArrayList<HashMap<String, String>> JobOrders) {
        JobOrdersListItems.clear();
        for(int i = 0; i < JobOrders.size(); i++) {
            JobOrdersListItems.addAll(Arrays.asList(
                    new JobOrdersModel(
                            JobOrders.get(i).get("id"),
                            JobOrders.get(i).get("title"),
                            JobOrders.get(i).get("details"),
                            JobOrders.get(i).get("date"),
                            JobOrders.get(i).get("cycle")
                    )
            ));
        }

        if(animated){
            notifyItemRangeInserted(0, JobOrdersListItems.size());
        }else{
            notifyDataSetChanged();
        }

    }

    public static class CellJobOrderViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.ivJobOrderCenter)
        //ImageView ivJobOrderCenter;
        @BindView(R.id.tsJobOrderDetails)
        TextSwitcher tsJobOrderDetails;
        @BindView(R.id.btnDownload)
        ImageButton btnDownload;
       // @BindView(R.id.vBgJobOrderDownload)
        //View vBgJobOrderDownload;
        //@BindView(R.id.ivJobOrderDownload)
        //ImageView ivJobOrderDownload;
        @BindView(R.id.ivJobOrderProfile)
        ImageView ivJobOrderProfile;
        //@BindView(R.id.vJobOrderImageRoot)
        //FrameLayout vJobOrderImageRoot;
        JobOrdersModel JobOrdersItems;
        @BindView(R.id.tvJobOrderTitle)
        TextView tvJobOrderTitle;
        @BindView(R.id.tvJobOrderDetails)
        TextView tvJobOrderDetails;
        @BindView(R.id.tvJobOrderAging)
        TextView tvJobOrderAging;
        @BindView(R.id.btnOtherDownloads)
        ImageButton btnOtherDownloads;

        public CellJobOrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(JobOrdersModel JobOrdersItems) {
            this.JobOrdersItems = JobOrdersItems;
            int adapterPosition = getAdapterPosition();
            btnOtherDownloads.setVisibility(View.GONE);
            if(Liquid.ServiceType == "READ AND BILL"){
                btnOtherDownloads.setVisibility(View.VISIBLE);
            }
            tvJobOrderTitle.setText(JobOrdersItems.Title);
            tvJobOrderAging.setText(JobOrdersItems.Date);
            tsJobOrderDetails.setCurrentText(adapterPosition % 2 == 0 ? JobOrdersItems.Details : JobOrdersItems.Details);
        }

        public JobOrdersModel getJobOrdersItems() {
            return JobOrdersItems;
        }
    }
}
