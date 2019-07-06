package com.aseyel.tgbl.tristangaryleyesa.adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.JobOrderActivity;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabDeliveryFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabLocalFragment;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.LocalJobOrdersModel;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidAudit;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidCoke;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidDelivery;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidDisconnection;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidReading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Romeo on 2018-01-05.
 */

public class LocalJobOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = LocalJobOrderAdapter.class.getSimpleName();
    public static final String ACTION_DOWNLOAD_BUTTON_CLICKED = "action_dolocaljoborder_button_button";
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
    private Fragment fragment;
    private final List<LocalJobOrdersModel> LocalJobOrdersListItems = new ArrayList<>();
    private  ArrayList<HashMap<String, String>> JobOrderDetails = new ArrayList<>();

    public LocalJobOrderAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_localjoborder,parent,false);
        CellLocalJobOrderViewHolder mCellLocalJobOrderViewHolder = new CellLocalJobOrderViewHolder(view);
        setupClickableViews(view,mCellLocalJobOrderViewHolder);
        return mCellLocalJobOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellLocalJobOrderViewHolder) viewHolder).bindView(LocalJobOrdersListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return LocalJobOrdersListItems.size();
    }

    public void updateItems(boolean animated,ArrayList<HashMap<String, String>> LocalJobOrders) {
        LocalJobOrdersListItems.clear();
        Log.i(TAG,LocalJobOrders.toString());
        for(int i = 0; i < LocalJobOrders.size(); i++) {
            LocalJobOrdersListItems.addAll(Arrays.asList(
                    new LocalJobOrdersModel(
                            LocalJobOrders.get(i).get("id"),
                            LocalJobOrders.get(i).get("title"),
                            LocalJobOrders.get(i).get("details"),
                            LocalJobOrders.get(i).get("date")
                    )
            ));
        }
        if(animated){
            notifyItemRangeInserted(0, LocalJobOrdersListItems.size());
        }else{
            notifyDataSetChanged();
        }
    }

    public void DeleteItem(int adapterPosition){
        LocalJobOrdersListItems.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }
    private void setupClickableViews(final View view, final LocalJobOrderAdapter.CellLocalJobOrderViewHolder mCellLocalJobOrderViewHolder) {

        /*mCellLocalJobOrderViewHolder.vLocalJobOrderImageRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellLocalJobOrderViewHolder.getAdapterPosition();
                    Liquid.SelectedId = LocalJobOrdersListItems.get(adapterPosition).getJobOrderId();
                    Liquid.SelectedJobType = LocalJobOrdersListItems.get(adapterPosition).getTitle();

                    switch(Liquid.Client){
                        case "coke":
                                 Liquid.SelectedPeriod = Liquid.getCokePeriod(Liquid.SelectedId);
                                 Liquid.SelectedPeriod = Liquid.SelectedId.substring(0,6);
                        break;
                    }
                    Intent i = new Intent(view.getContext(), JobOrderActivity.class);
                    view.getContext().startActivity(i);
                }
                catch(Exception e){
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        mCellLocalJobOrderViewHolder.llJobDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellLocalJobOrderViewHolder.getAdapterPosition();
                    Liquid.SelectedId = LocalJobOrdersListItems.get(adapterPosition).getJobOrderId();
                    Liquid.SelectedJobType = LocalJobOrdersListItems.get(adapterPosition).getTitle();
                    Liquid.ReadingDate = LocalJobOrdersListItems.get(adapterPosition).getDate();

                    switch(Liquid.Client){
                        case "coke":
                            Liquid.SelectedPeriod = Liquid.getCokePeriod(Liquid.SelectedId);
                            Liquid.SelectedPeriod = Liquid.SelectedId.substring(0,6);
                            break;
                    }
                    switch(Liquid.SelectedJobType){
                        case "Messengerial":
                        case "MESSENGERIAL":
                            ((TabDeliveryFragment) fragment).GoToList();
                            break;
                        default:
                            Intent i = new Intent(view.getContext(), JobOrderActivity.class);
                            view.getContext().startActivity(i);

                    }

                }
                catch(Exception e){
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        mCellLocalJobOrderViewHolder.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellLocalJobOrderViewHolder.getAdapterPosition();
                    Liquid.SelectedId = LocalJobOrdersListItems.get(adapterPosition).getJobOrderId();
                    Liquid.SelectedCode = LocalJobOrdersListItems.get(adapterPosition).getJobOrderId();
                    Liquid.SelectedJobType = LocalJobOrdersListItems.get(adapterPosition).getTitle();
                    Liquid.ReadingDate = LocalJobOrdersListItems.get(adapterPosition).getDate();
                    Liquid.ErrorUpload = new JSONArray();

                    if (fragment instanceof TabLocalFragment || fragment instanceof TabDeliveryFragment) {
                        //((TabLocalFragment) fragment).showUploadProgressBar(true);
                        switch(Liquid.SelectedJobType){
                            case "AUDIT":
                                ((TabLocalFragment) fragment).DoUploadAudit(LiquidAudit.UploadAudit());
                                break;

                            case "TRACKING":
                                ((TabLocalFragment) fragment).DoUploadCoke(LiquidCoke.UploadCokeTrackingData());
                                break;
                            case "MESSENGERIAL":
                            case "Messengerial":
                            case "MESSENGER":
                                ((TabDeliveryFragment) fragment).DoUploadMessengerial(LiquidDelivery.UploadDelivery(Liquid.SelectedId));
                                break;
                            case "DISCONNECTOR":
                                ((TabLocalFragment) fragment).DoUploadDisconnection(LiquidDisconnection.UploadDisconnection());
                                break;
                            case "METER READER":
                                ((TabLocalFragment) fragment).DoUploadReading(LiquidReading.UploadReading(Liquid.SelectedId));
                                break;
                        }

                    }

                }
                catch(Exception e){
                   Log.e(TAG,"Error ",e);
                }
            }
        });

        mCellLocalJobOrderViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    int adapterPosition = mCellLocalJobOrderViewHolder.getAdapterPosition();
                    Liquid.SelectedId = LocalJobOrdersListItems.get(adapterPosition).getJobOrderId();
                    Liquid.SelectedJobType = LocalJobOrdersListItems.get(adapterPosition).getTitle();
                    Liquid.ReadingDate = LocalJobOrdersListItems.get(adapterPosition).getDate();
                    ((TabLocalFragment) fragment).DeleteData(Liquid.SelectedId,Liquid.SelectedJobType,adapterPosition);
                }
                catch(Exception e){
                    Log.e(TAG,"Error ",e);
                }
            }
        });


    }




    private void UploadCokeTrackingData(){
        try{
            JSONObject final_data_response = new JSONObject();
            JSONArray final_data_result = new JSONArray();
            Liquid.SelectedPeriod = Liquid.getCokePeriod(Liquid.SelectedId);
            JobOrderDetails = GetCokeJobOrderDetails(Liquid.SelectedId);

            for(int a = 0; a < JobOrderDetails.size(); a++){
                JSONObject data_response = new JSONObject();
                for(int b = 0; b < Liquid.TrackingCategory.length; b++){
                    switch(Liquid.TrackingCategory[b]){
                        case "StoreStatus":
                            data_response.put("store_status",UploadStoreStatus(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Store Status");
                            break;
                        case "Availability":
                            data_response.put("availability", UploadAvailability(JobOrderDetails.get(a).get("AccountNumber")));
                            break;
                        case "SOVI":
                            data_response.put("sovi", UploadSOVI(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"SOVI");
                            break;
                        case "Activation":
                            data_response.put("activation", UploadActivation(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Activation");
                            break;
                        case "CDE":
                           data_response.put("cde", UploadCDE(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"CDE");
                            break;
                        case "ShelfPlanogram":
                            data_response.put("shelfplanogram", UploadShelfPlanogram(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Shelf Planogram");
                            break;
                        case "SoviLocation":
                           data_response.put("sovilocation", UploadSoviLocation(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"SoviLocation");
                            break;
                        case "CoolerPlanogram":
                            data_response.put("coolerplanogram", UploadCoolerPlanogram(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Cooler Planogram");
                            break;
                        case "Signature":
                           data_response.put("signature", UploadSignatureData(JobOrderDetails.get(a).get("AccountNumber")));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Cooler Planogram");
                            break;
                        case "Picture":
                            data_response.put("picture", UploadPicture(JobOrderDetails.get(a).get("AccountNumber")));
                            break;
                        case "Comment":
                            data_response.put("comment", UploadComment(JobOrderDetails.get(a).get("AccountNumber")));
                            break;
                        case "CategoryComment":
                           data_response.put("category_comment", UploadCategoryComment(JobOrderDetails.get(a).get("AccountNumber")));
                            break;
                    }
                }
                //Combine all data
                /*String validate_data = data_response.getString("store_status");
                JSONArray validate_array_data = new JSONArray(validate_data);
                if(validate_array_data.length() != 0){
                    final_data_result.put(data_response);
                }*/

                //final_image_result = UploadImage(view,JobOrderDetails.get(a).get("AccountNumber"));
                //final_signature_result = UploadSignature(view,JobOrderDetails.get(a).get("AccountNumber"));

                String validate_data = data_response.getString("store_status");
                JSONArray validate_array_data = new JSONArray(validate_data);
                if(validate_array_data.length() != 0){
                    final_data_result.put(data_response);

                }
            }
            final_data_response.put("data",final_data_result);


            //final_data_response.put("data",final_data_result);
            //final_image_response.put("image",final_image_result);
            //final_signature_response.put("signature",final_signature_result);


            //((TabLocalFragment) fragment).new FilePostingToServer(final_image_response).execute();
            //((TabLocalFragment) fragment).new FileSignaturePostingToServer(final_signature_response).execute();
            //((TabLocalFragment) fragment).new DataPostingToServer(final_data_response).execute();
            //((TabLocalFragment) fragment).new DataPostingToServer(final_data_response).execute();
            ((TabLocalFragment) fragment).DoUploadCoke(final_data_response);

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }

    }
    private JSONArray UploadSignature(View view,String AccountNumber){
        JSONArray final_image_result = new JSONArray();
        try {

            for (int c = 0; c < Liquid.CokeCategory.length; c++) {
                File mImages;
                File[] listFile;
                String[] Subfolder = new String[1];
                Subfolder[0] = Liquid.CokeCategory[c];

                mImages = Liquid.getDiscSignature(AccountNumber);
                if (!mImages.exists() && !mImages.mkdirs()) {
                    Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
                } else {
                    listFile = mImages.listFiles();
                    for (int e = 0; e < listFile.length; e++) {

                        JSONObject data = new JSONObject();

                        data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));

                        data.put("Filename", listFile[e].getName());

                        //combine all data for image
                        final_image_result.put(data);

                    }

                }
            }
            return final_image_result;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return final_image_result;
        }
    }

    private JSONArray UploadImage(View view,String AccountNumber,String Category){
        JSONArray final_image_result = new JSONArray();
        JSONObject final_image_response = new JSONObject();
        try {

           //for (int c = 0; c < Liquid.CokeCategory.length; c++) {
                File mImages;
                File[] listFile;
                String[] Subfolder = new String[1];
                Subfolder[0] = Category;

                mImages = Liquid.getDiscPicture(AccountNumber, Subfolder);
                if (!mImages.exists() && !mImages.mkdirs()) {
                    Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
                } else {
                    listFile = mImages.listFiles();
                    for (int e = 0; e < listFile.length; e++) {

                        JSONObject data = new JSONObject();

                        data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));

                        data.put("Filename", listFile[e].getName());

                        //combine all data for image
                        final_image_result.put(data);
                        final_image_response.put("image",final_image_result);


                    }

                //}
            }
            return final_image_result;
        } catch (JSONException e1) {
                e1.printStackTrace();
                return final_image_result;
        }
    }

    private Boolean CokeUploadImage(View view,String AccountNumber,String Category){

        try {

            //for (int c = 0; c < Liquid.CokeCategory.length; c++) {
            File mImages;
            File[] listFile;
            String[] Subfolder = new String[1];
            Subfolder[0] = Category;

            mImages = Liquid.getDiscPicture(AccountNumber, Subfolder);
            if (!mImages.exists() && !mImages.mkdirs()) {
                Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
            } else {
                listFile = mImages.listFiles();
                for (int e = 0; e < listFile.length; e++) {
                    JSONArray final_image_result = new JSONArray();
                    JSONObject final_image_response = new JSONObject();
                    JSONObject data = new JSONObject();
                    data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                    data.put("Filename", listFile[e].getName());
                    //combine all data for image
                    final_image_result.put(data);
                    final_image_response.put("image",final_image_result);
                    //((TabLocalFragment) fragment).new FilePostingToServer(final_image_response).execute();
                }
                //}
            }
            return true;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    private JSONArray UploadAudit(String JobOrderId,String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = AuditModel.GetAuditTravelRides(JobOrderId,"","","");
        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("JobOrderId",result.getString(0));
                data.put("Client",result.getString(1));
                data.put("AccountNumber",result.getString(2));
                data.put("Vehicle",result.getString(3));
                data.put("Fare",result.getString(4));
                data.put("Comment",result.getString(5));
                data.put("Latitude", result.getString(6));
                data.put("Longitude",result.getString(7));
                data.put("JobOrderDate",result.getString(8));
                data.put("TimeStamp",result.getString(9));
                data.put("modifiedby",result.getString(10));
                data.put("Status",result.getString(12));
                data.put("Username",Liquid.Username);
                data.put("Password",Liquid.Password);
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return final_response;
    }

    private JSONArray UploadStoreStatus(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetStoreStatus(AccountNumber,
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("latitude",result.getString(1));
                data.put("longitude",result.getString(2));
                data.put("status",result.getString(0));
                data.put("transferdatastatus",result.getString(3));
                data.put("timestamp",result.getString(6));
                data.put("auditor", result.getString(7));
                data.put("period",result.getString(5));
                data.put("picture",result.getString(4));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return final_response;
    }

    private JSONArray UploadAvailability(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetAvailability("",AccountNumber,
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("productCode", result.getString(2));
                data.put("price",result.getString(0));
                data.put("comment",result.getString(1));
                data.put("timestamp",result.getString(4));
                data.put("auditor",result.getString(5));
                data.put("period",result.getString(3));
                data.put("picture","");
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }
    private JSONArray UploadComment(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetComment(AccountNumber,
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("comment",result.getString(0));
                data.put("timestamp", result.getString(2));
                data.put("period",result.getString(3));
                data.put("auditor",result.getString(4));

                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }
    private JSONArray UploadCategoryComment(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetCategoryComment(AccountNumber,"",
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("category",result.getString(1));
                data.put("comment", result.getString(2));
                data.put("period",result.getString(3));
                data.put("timestamp",result.getString(4));
                data.put("auditor",result.getString(5));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }
    private JSONArray UploadShelfPlanogram(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetShelfPlanogram(AccountNumber,
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("description",result.getString(1));
                data.put("value", result.getString(2));
                data.put("auditor",result.getString(6));
                data.put("timestamp",result.getString(5));
                data.put("picture", result.getString(3));
                data.put("period",result.getString(4));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }

    private JSONArray UploadPicture(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetPicture(AccountNumber,
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("a_id",result.getString(0));
                data.put("customer_no",AccountNumber);
                data.put("category",result.getString(2));
                data.put("subcategory",result.getString(3));
                data.put("subsubcategory",result.getString(4));
                data.put("count",result.getString(6));
                data.put("picture",result.getString(7));
                data.put("timestamp", result.getString(10));
                data.put("period",result.getString(8));
                data.put("auditor",result.getString(11));
                data.put("subtype",result.getString(5));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return final_response;
    }

    private JSONArray UploadCoolerPlanogram(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetCoolerPlanogram(AccountNumber,
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("name", result.getString(1));
                data.put("value",result.getString(2));
                data.put("picture",result.getString(3));
                data.put("timestamp",result.getString(5));
                data.put("period",result.getString(4));
                data.put("auditor",result.getString(6));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return final_response;
    }

    private JSONArray UploadSignatureData(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetSignature(AccountNumber,
                Liquid.SelectedPeriod);
        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("store_name",result.getString(1));
                data.put("timestamp",  result.getString(3));
                data.put("auditor", result.getString(4));
                data.put("period", result.getString(2));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }

    private JSONArray UploadSoviLocation(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetSoviLocation(AccountNumber,
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("sovi_location", result.getString(1));
                data.put("auditor",result.getString(5));
                data.put("timestamp",result.getString(4));
                data.put("picture", result.getString(2));
                data.put("period", result.getString(3));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }

    private JSONArray UploadCDE(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetTrackingCDEData(AccountNumber,"","",
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("name",result.getString(1));
                data.put("category", result.getString(2));
                data.put("question",result.getString(3));
                data.put("questionvalue",result.getString(4));
                data.put("total_count",result.getString(5));
                data.put("timestamp", result.getString(9));
                data.put("auditor",result.getString(10));
                data.put("picture",result.getString(6));
                data.put("period",result.getString(8));
                data.put("comment","");
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }
    private JSONArray UploadActivation(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetTrackingActivation(AccountNumber,"","",
                Liquid.SelectedPeriod);

        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("customer_no",AccountNumber);
                data.put("name",result.getString(1));
                data.put("category",result.getString(4));
                data.put("value",result.getString(2));
                data.put("total_count",result.getString(3));
                data.put("picture",result.getString(5));
                data.put("timestamp",result.getString(7));
                data.put("auditor",result.getString(8));
                data.put("period",result.getString(6));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;
    }

    private JSONArray UploadSOVI(String AccountNumber){

        JSONArray final_response = new JSONArray();
        Cursor result = TrackingModel.GetSovi("",AccountNumber,"",
                "",Liquid.SelectedPeriod);
        if (result.getCount() == 0) {
            return final_response;
        }
        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {

                data.put("customer_no",AccountNumber);
                data.put("productName",result.getString(14));
                data.put("description",result.getString(15));
                data.put("sovi_type",result.getString(16));
                data.put("location",result.getString(0));
                data.put("numkof",result.getString(1));
                data.put("numnonkof",result.getString(2));
                data.put("cans",result.getString(3));
                data.put("sspet",result.getString(4));
                data.put("mspet",result.getString(5));
                data.put("ssdoy",result.getString(6));
                data.put("ssbrick",result.getString(7));
                data.put("msbrick",result.getString(8));
                data.put("sswedge",result.getString(9));
                data.put("box",result.getString(10));
                data.put("litro",result.getString(11));
                data.put("pounch",result.getString(12));
                data.put("picture",result.getString(17));
                data.put("timestamp",result.getString(19));
                data.put("auditor",result.getString(20));
                data.put("period",result.getString(18));
                data.put("comment",result.getString(13));

                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return final_response;

    }





    private  ArrayList<HashMap<String, String>> GetCokeJobOrderDetails(String JobOrderId){
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = workModel.GetCokeLocalJobOrderDetails(JobOrderId,"");
        try {
            if (result.getCount() == 0) {
                return final_result;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                data.put("AccountNumber", result.getString(3));
                final_result.add(data);
            }
            return final_result;
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return final_result;
        }
    };

    public static class CellLocalJobOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tsLocalJobOrderDetails)
        TextSwitcher tsLocalJobOrderDetails;
        @BindView(R.id.btnUpload)
        ImageButton btnUpload;
        @BindView(R.id.ivLocalJobOrderProfile)
        ImageView ivLocalJobOrderProfile;
        LocalJobOrdersModel LocalJobOrdersItems;
        @BindView(R.id.tvLocalJobOrderTitle)
        TextView tvLocalJobOrderTitle;
        @BindView(R.id.tvJobOrderDetails)
        TextView tvLocalJobOrderDetails;
        @BindView(R.id.tvLocalJobOrderAging)
        TextView tvLocalJobOrderAging;
        @BindView(R.id.llJobDetails)
        LinearLayout llJobDetails;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;
        public CellLocalJobOrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(LocalJobOrdersModel LocalJobOrdersItems) {
            this.LocalJobOrdersItems = LocalJobOrdersItems;
            int adapterPosition = getAdapterPosition();
            switch(Liquid.Client){
                case "coke":
                    //ivLocalJobOrderCenter.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_coke : R.drawable.img_coke);
                    break;
                case "meralco_batangas":
                    //ivLocalJobOrderCenter.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_delivery : R.drawable.img_delivery);
                    break;
                default:
                    //ivLocalJobOrderCenter.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_ums : R.drawable.img_ums);

            }
            btnDelete.setVisibility(View.GONE);
            tvLocalJobOrderTitle.setText(LocalJobOrdersItems.Title);
            tvLocalJobOrderAging.setText(LocalJobOrdersItems.Date);
            tsLocalJobOrderDetails.setCurrentText(adapterPosition % 2 == 0 ? LocalJobOrdersItems.Details : LocalJobOrdersItems.Details);
        }

        public LocalJobOrdersModel getLocalJobOrdersItems() {
            return LocalJobOrdersItems;
        }
    }


}
