package com.aseyel.tgbl.tristangaryleyesa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TranslatorModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = ChatAdapter.class.getSimpleName();
    private final Context mContext;
    private final List<TranslatorModel> ListItems = new ArrayList<>();

    public ChatAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        CellViewHolder mCellViewHolder = new CellViewHolder(view);
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
    }



    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    public void updateItems(boolean animated,ArrayList<HashMap<String, String>> Data) {
        //ListItems.clear();
        for(int i = 0; i < Data.size(); i++) {
            ListItems.addAll(Arrays.asList(
                    new TranslatorModel(
                            Data.get(i).get("Title"),
                            Data.get(i).get("Message"),
                            Data.get(i).get("TimeStamp"),
                            Integer.parseInt(Data.get(i).get("MessageType"))
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
        TextView leftTextViewChat;
        TextView rightTextViewChat;

        LinearLayout mReceiverMessage;
        LinearLayout mSenderMessage;
        TranslatorModel Items;
        TextView tvCurrentDate;
        TextView tvOtherChatTime;
        TextView tvUserChatTime;
        public CellViewHolder(View itemView) {
            super(itemView);
             leftTextViewChat = (TextView) itemView.findViewById(R.id.leftTextViewChat);
             rightTextViewChat = (TextView) itemView.findViewById(R.id.rightTextViewChat);

             mReceiverMessage = (LinearLayout) itemView.findViewById(R.id.mReceiverMessage);
             mSenderMessage = (LinearLayout) itemView.findViewById(R.id.mSenderMessage);
             tvCurrentDate = (TextView) itemView.findViewById(R.id.tvCurrentDate);
            tvOtherChatTime = (TextView) itemView.findViewById(R.id.tvOtherChatTime);
            tvUserChatTime = (TextView) itemView.findViewById(R.id.tvUserChatTime);
        }

        public void bindView(TranslatorModel Items) {
            this.Items = Items;
            int adapterPosition = getAdapterPosition();

            if(tvCurrentDate.getText().toString().equals(Liquid.setUpCurrentDate("MMMM dd"))){
                tvCurrentDate.setVisibility(View.GONE);
            }else{
                tvCurrentDate.setText(Liquid.setUpCurrentDate("MMMM dd"));
            }

            mSenderMessage.setVisibility(View.GONE);
            mReceiverMessage.setVisibility(View.GONE);
           switch (Items.MessageType){
               case 1:
                   mSenderMessage.setVisibility(View.VISIBLE);
                   leftTextViewChat.setText(Items.Mesasge);
                   tvOtherChatTime.setText(Liquid.setUpCurrentDate("HH:mm:ss"));
                   break;
               case 2:
                   mReceiverMessage.setVisibility(View.VISIBLE);
                   rightTextViewChat.setText(Items.Mesasge);
                   tvUserChatTime.setText(Liquid.setUpCurrentDate("HH:mm:ss"));
                   break;

           }
        }

        public TranslatorModel getItems() {
            return Items;
        }
    }
}
