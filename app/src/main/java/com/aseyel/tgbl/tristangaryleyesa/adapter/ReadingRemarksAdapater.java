package com.aseyel.tgbl.tristangaryleyesa.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
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
import com.aseyel.tgbl.tristangaryleyesa.AuditTravelRideActivity;
import com.aseyel.tgbl.tristangaryleyesa.DeliveryActivityV2;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.ReadingGalleryActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingRemarksActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingSummaryActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingV2Activity;
import com.aseyel.tgbl.tristangaryleyesa.SignatureActivity;
import com.aseyel.tgbl.tristangaryleyesa.TypeActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabDeliveryFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.ListModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidBilling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class ReadingRemarksAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = ReadingRemarksAdapater.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_download_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Context context;
    private Fragment fragment;
    private final List<ListModel> ListItems = new ArrayList<>();

    public ReadingRemarksAdapater(Context context,Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reading_remarks,parent,false);
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
                Intent i;
                int adapterPosition = mCellViewHolder.getAdapterPosition();

                try
                {
                    switch(Liquid.SelectedJobType){
                        case "METER READER":
                            Liquid.RemarksCode = ListItems.get(adapterPosition).getId();
                            Liquid.Remarks = ListItems.get(adapterPosition).getTitle();
                            Liquid.ReaderComment = ((ReadingRemarksActivity) context).txtComment.getText().toString();
                            Liquid.RemarksAbbreviation = ListItems.get(adapterPosition).getFilepath();
                            switch (Liquid.Client){
                                case "baliwag_wd":
                                    boolean hit = false;
                                    for (int x=0; x<Liquid.BaliwagAverageRemarksAbbreviation.length; x++){
                                        if(Liquid.RemarksAbbreviation.matches(Liquid.BaliwagAverageRemarksAbbreviation[x])){
                                            Liquid.Reading = Integer.toString((int)(Integer.parseInt(Liquid.previous_reading)+Integer.parseInt(Liquid.Averange_Consumption)));
                                            Liquid.Present_Consumption = Liquid.Averange_Consumption;
                                            Liquid.save_only = false;
                                            ReadingV2Activity.Computation();
                                            hit = true;
                                        }
                                    }

                                    if(!hit && !Liquid.Reading.matches("") && (!Liquid.reading_remarks.matches("NEGATIVE CONSUMPTION") && !Liquid.reading_remarks.matches("ZERO CONSUMPTION"))){
                                        Liquid.Reading = Liquid.ReadingInputTemporaryHolder;
                                        Liquid.Present_Consumption = Liquid.PresentConsumptionTemporaryHolder;
                                        ReadingV2Activity.Computation();
                                    }

                                    if(Liquid.Reading.matches("") && !hit){
                                        LiquidBilling.total_current_bill = 0;
                                        LiquidBilling.senior = 0;
                                        LiquidBilling.arrears = 0;
                                        LiquidBilling.surcharge = 0;
                                        LiquidBilling.surcharge = 0;
                                        LiquidBilling.total_environmental_charge = 0;
                                        LiquidBilling.total_other_charges = 0;
                                        LiquidBilling.total_amount_due = 0;
                                        LiquidBilling.total_amount_due2 = 0;

                                        Liquid.save_only = true;
                                    }
                                    break;
                            }
                             i = new Intent(view.getContext(), ReadingSummaryActivity.class);
                            view.getContext().startActivity(i);
                            break;
                        case "Messengerial":
                        case "MESSENGERIAL":
                            switch(Liquid.DeliveryStep){
                                case "Type":
                                    Liquid.DeliveryItemTypeCode = ListItems.get(adapterPosition).getId();
                                    Liquid.DeliveryItemTypeDescription = ListItems.get(adapterPosition).getTitle();
//                                    i = new Intent(view.getContext(), ReadingRemarksActivity.class);
//                                    view.getContext().startActivity(i);

                                    //REVISION FOR FASTER USER INTERACTION
                                    ((TabDeliveryFragment) fragment).GoToList();
                                    ((TypeActivity)view.getContext()).finish();
                                    break;
                                case "Remarks":
                                    Liquid.RemarksCode = ListItems.get(adapterPosition).getId();
                                    Liquid.Remarks = ListItems.get(adapterPosition).getTitle();
                                    Liquid.ReaderComment = ((ReadingRemarksActivity) context).txtComment.getText().toString();
                                    i = new Intent(view.getContext(), SignatureActivity.class);
                                    view.getContext().startActivity(i);
                                    break;
                            }
                            break;
                    }
                    //Remove due to camera will be in first step
                    //Intent i = new Intent(view.getContext(), ReadingGalleryActivity.class);
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

    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    public void updateItems(boolean animated,ArrayList<HashMap<String, String>> Data) {
        ListItems.clear();
        for(int i = 0; i < Data.size(); i++) {
            ListItems.addAll(Arrays.asList(
                    new ListModel(
                            Data.get(i).get("Id"),
                            Data.get(i).get("Title"),
                            Data.get(i).get("Details"),
                            Data.get(i).get("Date"),
                            Data.get(i).get("Filepath")
                    )
            ));
        }
        if(animated){
            notifyItemRangeInserted(0, ListItems.size());
        }else{
            notifyDataSetChanged();
        }
    }

    public static class CellViewHolder extends RecyclerView.ViewHolder {
        @BindView (R.id.card_view)
        CardView card_view;
       // @BindView(R.id.btnToDo)
       // ImageButton btnToDo;
        @BindView(R.id.ivProfile)
        ImageView ivProfile;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDetails)
        TextView tvDetails;
        @BindView(R.id.tsDetails)
        TextSwitcher tsDetails;
        @BindView(R.id.tvAging)
        TextView tvAging;
        @BindView(R.id.llDetails)
        LinearLayout llDetails;
        //@BindView(R.id.btnDelete)
        // ImageButton btnDelete;
        ListModel Items;
        View view;

        public CellViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        public void bindView(ListModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();
            card_view.setCardBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
            tvTitle.setTextColor(view.getResources().getColor(R.color.colorAccent));
            tvDetails.setTextColor(view.getResources().getColor(R.color.colorAccent));
            if(Liquid.RemarksCode.equals(Items.Id) || Liquid.DeliveryItemTypeCode.equals(Items.Id)){
                card_view.setCardBackgroundColor(view.getResources().getColor(R.color.colorAccent));
                tvTitle.setTextColor(view.getResources().getColor(R.color.colorPrimary));
                tvDetails.setTextColor(view.getResources().getColor(R.color.colorPrimary));
            }
            tvTitle.setText(Items.Title);
            tvAging.setText(Items.Date);
            tsDetails.setCurrentText(adapterPosition % 2 == 0 ? Items.Details : Items.Details);
        }
        public ListModel getJobOrdersDetailsItems() {
            return Items;
        }
    }
}
