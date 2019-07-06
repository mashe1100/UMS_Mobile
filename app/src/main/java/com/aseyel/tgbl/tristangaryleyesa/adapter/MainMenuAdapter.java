package com.aseyel.tgbl.tristangaryleyesa.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabMenuFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.FeedModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 2018-03-06.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = MainMenuAdapter.class.getSimpleName();
    private Fragment fragment;
    private final List<FeedModel> ListItems = new ArrayList<>();

    public MainMenuAdapter(Fragment fragment){
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainmenu,parent,false);
        CellViewHolder mCellViewHolder = new CellViewHolder(view);
        setupClickableViews(view,mCellViewHolder);
        return mCellViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellViewHolder) viewHolder).bindView(ListItems.get(position));
    }


    @Override
    public int getItemCount() {
        return ListItems.size();
    }


    private void setupClickableViews(final View view, final CellViewHolder mCellViewHolder) {
        mCellViewHolder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    String Description =  ListItems.get(adapterPosition).getId();
                    if (fragment instanceof TabMenuFragment) {
                        MenuExecution(Description);
                    }

                }
                catch(Exception e){
                    Log.e(TAG, e.toString(),e);
                }
            }
        });
        mCellViewHolder.tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    int adapterPosition = mCellViewHolder.getAdapterPosition();
                    String Description =  ListItems.get(adapterPosition).getId();
                    if (fragment instanceof TabMenuFragment) {
                        MenuExecution(Description);
                    }

                }
                catch(Exception e){
                    Log.e(TAG, e.toString(),e);
                }
            }
        });

    }

    private void MenuExecution(String ListName){
        switch(ListName){
            case "001":
                ((TabMenuFragment) fragment).CreateBackUp();
                break;
            case "002":
                ((TabMenuFragment) fragment).ImportData();

            break;
            case "003":
                ((TabMenuFragment) fragment).DoLogout();

                break;
            case "004":
                ((TabMenuFragment) fragment).GoToAbout();
                break;
            case "005":
                ((TabMenuFragment) fragment).ClearData();
                break;
            case "006":
                ((TabMenuFragment) fragment).Settings();
                break;
            case "007":
                ((TabMenuFragment) fragment).ChangePassword();
                break;
        }
    }
    public void updateItems(boolean animated,ArrayList<HashMap<String, String>> Data) {
        ListItems.clear();
        for(int i = 0; i < Data.size(); i++) {
            ListItems.addAll(Arrays.asList(
                    new FeedModel(
                            Data.get(i).get("Id"),
                            Data.get(i).get("Description"),
                            "",
                            "",
                            ""
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
        @BindView(R.id.ivMenu)
        ImageView ivMenu;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        FeedModel Items;

        public CellViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(FeedModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();
            tvDescription.setText(Items.Title);
            ivMenu.setImageResource(R.drawable.ic_action_logout);

        }

        public FeedModel getItems() {
            return Items;
        }
    }

}
