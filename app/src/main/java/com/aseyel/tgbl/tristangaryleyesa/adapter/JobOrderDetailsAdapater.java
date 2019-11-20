package com.aseyel.tgbl.tristangaryleyesa.adapter;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.CardView;
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

import com.aseyel.tgbl.tristangaryleyesa.AuditTravelActivity;
import com.aseyel.tgbl.tristangaryleyesa.DeliveryActivity;
import com.aseyel.tgbl.tristangaryleyesa.DeliveryActivityV2;
import com.aseyel.tgbl.tristangaryleyesa.DisconnectionActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;

import com.aseyel.tgbl.tristangaryleyesa.ReadingActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingGalleryActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingV2Activity;
import com.aseyel.tgbl.tristangaryleyesa.SurveyActivity;
import com.aseyel.tgbl.tristangaryleyesa.TrackingActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.ListJobOrderFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabLocalFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.JobOrdersDetailsModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidAudit;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidCoke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class JobOrderDetailsAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = JobOrderDetailsAdapater.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_download_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Fragment fragment;
    private final List<JobOrdersDetailsModel> ListItems = new ArrayList<>();

    public JobOrderDetailsAdapater(Fragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joborderdetails,parent,false);
            CellViewHolder mCellViewHolder = new CellViewHolder(view);
            setupClickableViews(view,mCellViewHolder);
            return mCellViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellViewHolder) viewHolder).bindView(ListItems.get(position));
    }

    private void setupClickableViews(final View view, final CellViewHolder mCellViewHolder) {
        /*mCellViewHolder.tsJobOrderDetailsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedAccountNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.SelectedAccountName = ListItems.get(adapterPosition).getAccountName();
                    Liquid.SelectedJobOrderDate = ListItems.get(adapterPosition).getJobOrderDate();
                    NextActivity(view,Liquid.SelectedJobType);
                }
                catch(Exception e){
                    Log.e(TAG, e.toString(),e);
                }

            }
        });*/

//        mCellViewHolder.btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try
//                {
//                    int adapterPosition = mCellViewHolder.getAdapterPosition();
//                    Liquid.SelectedAccountNumber = ListItems.get(adapterPosition).getAccountNumber();
//                    Liquid.AccountNumber = ListItems.get(adapterPosition).getAccountNumber();
//                    Liquid.SelectedAccountName = ListItems.get(adapterPosition).getAccountName();
//                    Liquid.SelectedJobOrderDate = ListItems.get(adapterPosition).getJobOrderDate();
//                    Liquid.ReadingDate = ListItems.get(adapterPosition).getDate();
//                    if(Liquid.SelectedJobType.equals("AUDIT")){
//                        ((ListJobOrderFragment) fragment).DoUploadAudit(LiquidAudit.UploadAccountAudit(Liquid.SelectedAccountNumber));
//                    }
//
//                    if(Liquid.SelectedJobType.equals("TRACKING")){
//                        //UploadCokeTrackingData();
//                        ((ListJobOrderFragment) fragment).DoUploadCoke(LiquidCoke.UploadCokeTrackingAccountData(Liquid.SelectedAccountNumber));
//                    }
//
//
//                }
//                catch(Exception e){
//                    Log.e(TAG, e.toString(),e);
//                }
//
//            }
//        });
        mCellViewHolder.llJobDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedAccountNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.AccountNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.TrackingNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.SelectedMeterNumber = ListItems.get(adapterPosition).getMeterNumber();
                    Liquid.MeterNumber = ListItems.get(adapterPosition).getMeterNumber();
                    Liquid.SelectedAccountName = ListItems.get(adapterPosition).getAccountName();
                    Liquid.SelectedJobOrderDate = ListItems.get(adapterPosition).getJobOrderDate();
                    Liquid.ReadingDate = ListItems.get(adapterPosition).getDate();


                    NextActivity(view,Liquid.SelectedJobType);
                }
                catch(Exception e){
                    Log.e(TAG, e.toString(),e);
                }

            }
        });

        mCellViewHolder.btnToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedAccountNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.AccountNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.SelectedAccountName = ListItems.get(adapterPosition).getAccountName();
                    Liquid.SelectedJobOrderDate = ListItems.get(adapterPosition).getJobOrderDate();
                    Liquid.ReadingDate = ListItems.get(adapterPosition).getDate();
                    NextActivity(view,Liquid.SelectedJobType);
                }
                catch(Exception e){
                    Log.e(TAG, e.toString(),e);
                }

            }
        });

        mCellViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedAccountNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.AccountNumber = ListItems.get(adapterPosition).getAccountNumber();
                    Liquid.SelectedAccountName = ListItems.get(adapterPosition).getAccountName();
                    Liquid.SelectedJobOrderDate = ListItems.get(adapterPosition).getJobOrderDate();
                    Liquid.ReadingDate = ListItems.get(adapterPosition).getDate();
                    ((ListJobOrderFragment) fragment).DeleteData(Liquid.SelectedJobType,Liquid.SelectedId,Liquid.SelectedAccountNumber,adapterPosition);
                }
                catch(Exception e){
                    Log.e(TAG, e.toString(),e);
                }

            }
        });


    }

    public void DeleteItem(int adapterPosition){
        ListItems.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }


    public void NextActivity(View view,String JobType){
        Intent i;
        switch(JobType){
            case "AUDIT":
                i = new Intent(view.getContext(), AuditTravelActivity.class);
                view.getContext().startActivity(i);
                break;
            case "TRACKING":
                i = new Intent(view.getContext(), TrackingActivity.class);
                view.getContext().startActivity(i);
                break;
            case "Messengerial":
            case "MESSENGERIAL":
            case "MESSENGER":
                i = new Intent(view.getContext(), DeliveryActivityV2.class);
                view.getContext().startActivity(i);
                break;
            case "METER READER":
                //i = new Intent(view.getContext(), ReadingActivity.class);
                //i = new Intent(view.getContext(), ReadingV2Activity.class);
                switch(Liquid.Client){
//                    case "more_power":
//                             i = new Intent(view.getContext(), SurveyActivity.class);
//                        break;
                        default:
                            //ILECO II Request by sir dwight, joelen, vincent
                            i = new Intent(view.getContext(), ReadingGalleryActivity.class);
                }

                view.getContext().startActivity(i);

                break;
            case "DISCONNECTOR":
                i = new Intent(view.getContext(), DisconnectionActivity.class);
                view.getContext().startActivity(i);
                break;
        }
    }
    @Override
    public int getItemCount() {
       // return Math.min(ListItems.size(),Liquid.RecyclerItemLimit);
        return ListItems.size();
    }

    public void updateItems(boolean animated,ArrayList<HashMap<String, String>> Data) {

        //if(Data.size() == 0 && ListItems.size() != 0){
        //    notifyItemRangeRemoved(0,ListItems.size());
        //}

        ListItems.clear();
        for(int i = 0; i < Data.size(); i++) {
            if(Data.get(i).get("Print") == null)
                Data.get(i).put("Print","");

            ListItems.addAll(Arrays.asList(
                    new JobOrdersDetailsModel(
                            Data.get(i).get("JobOrderId"),
                            Data.get(i).get("AccountNumber"),
                            Data.get(i).get("AccountName"),
                            Data.get(i).get("MeterNumber"),
                            Data.get(i).get("Title"),
                            Data.get(i).get("Details"),
                            Data.get(i).get("Date"),
                            Double.parseDouble(Data.get(i).get("Latitude").isEmpty() ? "0" : Data.get(i).get("Latitude")),
                            Double.parseDouble(Data.get(i).get("Longitude").isEmpty()? "0" : Data.get(i).get("Longitude")),
                            Data.get(i).get("JobOrderDate"),
                            Data.get(i).get("Status"),
                            Data.get(i).get("Accomplishment"),
                            Data.get(i).get("Print")
                    )
            ));
        }


        if(animated){
            notifyItemRangeInserted(0, ListItems.size());
        }else{

            notifyItemRangeChanged(0, getItemCount());
            notifyDataSetChanged();
        }
    }



    public static class CellViewHolder extends RecyclerView.ViewHolder {
        @BindView (R.id.card_view)
        CardView card_view;
        @BindView(R.id.btnToDo)
        ImageButton btnToDo;
        @BindView(R.id.ivJobOrderDetailsProfile)
        ImageView ivJobOrderProfile;
        @BindView(R.id.tvJobOrderDetailsTitle)
        TextView tvJobOrderDetailsTitle;
        @BindView(R.id.tvJobOrderDetailsDetails)
        TextView tvJobOrderDetailsDetails;
        @BindView(R.id.tsJobOrderDetailsDetails)
        TextSwitcher tsJobOrderDetailsDetails;
        @BindView(R.id.tvJobOrderDetailsAging)
        TextView tvJobOrderDetailsAging;
        @BindView (R.id.llJobDetails)
        LinearLayout llJobDetails;
        @BindView(R.id.btnUpload)
        ImageButton btnUpload;
        @BindView(R.id.btnDelete)
                ImageButton btnDelete;
        @BindView(R.id.lContent)
                LinearLayout lContent;
        JobOrdersDetailsModel JobOrdersDetailsItems;
        View view;

        public CellViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }

        public void bindView(JobOrdersDetailsModel JobOrdersDetailsItems) {
            this.JobOrdersDetailsItems = JobOrdersDetailsItems;
            int adapterPosition = getAdapterPosition();
            switch(Liquid.SelectedJobType){
                case "AUDIT":
                    btnDelete.setVisibility(View.VISIBLE);
                break;
                case "TRACKING":
                    btnDelete.setVisibility(View.GONE);
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    break;
                case "Messengerial":
                case "MESSENGERIAL":
                case "MESSENGER":
                    btnUpload.setVisibility(View.GONE);
                    //btnDelete.setVisibility(View.GONE);
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    break;
                case "METER READER":
                    btnUpload.setVisibility(View.GONE);
                    llJobDetails.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                    tvJobOrderDetailsTitle.setTextColor(view.getResources().getColor(R.color.colorAccent));
                    tvJobOrderDetailsDetails.setTextColor(view.getResources().getColor(R.color.colorAccent));

                    if(JobOrdersDetailsItems.Status.equals("DISCONNECTED") || JobOrdersDetailsItems.Status.equals("DISCD") || JobOrdersDetailsItems.Status.equals("WOFF") ){
                        llJobDetails.setBackgroundColor(Color.RED);
                        tvJobOrderDetailsTitle.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                        tvJobOrderDetailsDetails.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                    }

                    if(JobOrdersDetailsItems.Accomplishment.equals("NO READING")){

                    }else{
                        llJobDetails.setBackgroundColor(view.getResources().getColor(R.color.colorAccent));
                        tvJobOrderDetailsTitle.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                        tvJobOrderDetailsDetails.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                    }
                    if(!JobOrdersDetailsItems.Print.matches("")){
                        llJobDetails.setBackgroundColor(view.getResources().getColor(R.color.btn_send_pressed));
                        tvJobOrderDetailsTitle.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                        tvJobOrderDetailsDetails.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                    }

                    btnDelete.setVisibility(View.GONE);
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    break;
                case "DISCONNECTOR":
                    btnDelete.setVisibility(View.GONE);
                    break;
            }
            tvJobOrderDetailsTitle.setText(JobOrdersDetailsItems.Title);
            tvJobOrderDetailsAging.setText(JobOrdersDetailsItems.Date);
            tsJobOrderDetailsDetails.setCurrentText(adapterPosition % 2 == 0 ? JobOrdersDetailsItems.Details : JobOrdersDetailsItems.Details);
        }

        public JobOrdersDetailsModel getJobOrdersDetailsItems() {
            return JobOrdersDetailsItems;
        }
    }







}
