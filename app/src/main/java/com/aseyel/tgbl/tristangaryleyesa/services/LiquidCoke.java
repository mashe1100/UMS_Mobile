package com.aseyel.tgbl.tristangaryleyesa.services;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.ListJobOrderFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabLocalFragment;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LiquidCoke {
    private static final String TAG = LiquidCoke.class.getSimpleName();
    public static  ArrayList<HashMap<String, String>> JobOrderDetails = new ArrayList<>();

    public static JSONObject UploadCokeTrackingAccountData(String AccountNumber){
        try{
                JSONObject final_data_response = new JSONObject();
                JSONArray final_data_result = new JSONArray();
                Liquid.SelectedPeriod = Liquid.getCokePeriod(Liquid.SelectedId);
                JSONObject data_response = new JSONObject();
                for(int b = 0; b < Liquid.TrackingCategory.length; b++){
                    switch(Liquid.TrackingCategory[b]){
                        case "StoreStatus":
                            data_response.put("store_status",UploadStoreStatus(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Store Status");
                            break;
                        case "Availability":
                            data_response.put("availability", UploadAvailability(AccountNumber));
                            break;
                        case "SOVI":
                            data_response.put("sovi", UploadSOVI(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"SOVI");
                            break;
                        case "Activation":
                            data_response.put("activation", UploadActivation(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Activation");
                            break;
                        case "CDE":
                            data_response.put("cde", UploadCDE(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"CDE");
                            break;
                        case "ShelfPlanogram":
                            data_response.put("shelfplanogram", UploadShelfPlanogram(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Shelf Planogram");
                            break;
                        case "SoviLocation":
                            data_response.put("sovilocation", UploadSoviLocation(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"SoviLocation");
                            break;
                        case "CoolerPlanogram":
                            data_response.put("coolerplanogram", UploadCoolerPlanogram(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Cooler Planogram");
                            break;
                        case "Signature":
                            data_response.put("signature", UploadSignatureData(AccountNumber));
                            //CokeUploadImage(view,JobOrderDetails.get(a).get("AccountNumber"),"Cooler Planogram");
                            break;
                        case "Picture":
                            data_response.put("picture", UploadPicture(AccountNumber));
                            break;
                        case "Comment":
                            data_response.put("comment", UploadComment(AccountNumber));
                            break;
                        case "CategoryComment":
                            data_response.put("category_comment", UploadCategoryComment(AccountNumber));
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

            final_data_response.put("data",final_data_result);


            //final_data_response.put("data",final_data_result);
            //final_image_response.put("image",final_image_result);
            //final_signature_response.put("signature",final_signature_result);


            //((TabLocalFragment) fragment).new FilePostingToServer(final_image_response).execute();
            //((TabLocalFragment) fragment).new FileSignaturePostingToServer(final_signature_response).execute();
            //((TabLocalFragment) fragment).new DataPostingToServer(final_data_response).execute();
            //((TabLocalFragment) fragment).new DataPostingToServer(final_data_response).execute();
            return final_data_response;

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
            return null;
        }

    }

    public static JSONObject UploadCokeTrackingData(){
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


            return final_data_response;

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
            return  null;
        }

    }

    public static ArrayList<HashMap<String, String>> GetCokeJobOrderDetails(String JobOrderId){
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

    public static JSONArray UploadStoreStatus(String AccountNumber){
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

    public static JSONArray UploadAvailability(String AccountNumber){
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

    public static JSONArray UploadComment(String AccountNumber){
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

    public static JSONArray UploadCategoryComment(String AccountNumber){
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

    public static JSONArray UploadShelfPlanogram(String AccountNumber){
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

    public static JSONArray UploadPicture(String AccountNumber){
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

    public static JSONArray UploadCoolerPlanogram(String AccountNumber){
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

    public static JSONArray UploadSignatureData(String AccountNumber){
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

    public static JSONArray UploadSoviLocation(String AccountNumber){
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

    public static JSONArray UploadCDE(String AccountNumber){
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

    public static JSONArray UploadActivation(String AccountNumber){
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

    public  static JSONArray UploadSOVI(String AccountNumber){

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
}
