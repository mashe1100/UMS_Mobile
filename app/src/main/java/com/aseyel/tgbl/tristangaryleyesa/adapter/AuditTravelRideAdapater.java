package com.aseyel.tgbl.tristangaryleyesa.adapter;


import android.content.Context;
import android.content.Intent;
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
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.ListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 12/12/2017.
 */

public class AuditTravelRideAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = AuditTravelRideAdapater.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_download_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Context context;
    private final List<ListModel> ListItems = new ArrayList<>();

    public AuditTravelRideAdapater(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audit_travels,parent,false);
            CellViewHolder mCellViewHolder = new CellViewHolder(view);
            setupClickableViews(view,mCellViewHolder);
            return mCellViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellViewHolder) viewHolder).bindView(ListItems.get(position));
    }

    private void setupClickableViews(final View view, final CellViewHolder mCellViewHolder) {
        /*mCellViewHolder.tsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();
                    Intent i = new Intent(view.getContext(), AuditTravelRideActivity.class);
                    view.getContext().startActivity(i);
                }
                catch(Exception e){
                    Log.e(TAG, e.toString(),e);
                }

            }
        });*/

        mCellViewHolder.llAuditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();
                    Intent i = new Intent(view.getContext(), AuditTravelRideActivity.class);
                    view.getContext().startActivity(i);
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
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();
                    Intent i = new Intent(view.getContext(), AuditTravelRideActivity.class);
                    view.getContext().startActivity(i);
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
                    Liquid.SelectedCode = ListItems.get(adapterPosition).getId();

                    ((AuditTravelActivity) context).DeleteData(Liquid.SelectedId,Liquid.SelectedAccountNumber,Liquid.SelectedCode,adapterPosition);
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
        @BindView(R.id.btnToDo)
        ImageButton btnToDo;
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
        @BindView(R.id.llAuditDetails)
        LinearLayout llAuditDetails;
        @BindView(R.id.btnDelete)
                ImageButton btnDelete;
        ListModel Items;

        public CellViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(ListModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();

            tvTitle.setText(Items.Title);
            tvAging.setText(Items.Date);
            tsDetails.setCurrentText(adapterPosition % 2 == 0 ? Items.Details : Items.Details);
        }

        public ListModel getJobOrdersDetailsItems() {
            return Items;
        }
    }
}
