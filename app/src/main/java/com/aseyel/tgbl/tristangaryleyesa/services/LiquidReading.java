package com.aseyel.tgbl.tristangaryleyesa.services;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.DisconnectionModel;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LiquidReading {
    public static final String TAG = LiquidReading.class.getSimpleName();
    public static JSONObject UploadReading(String JobID){
        try{
            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray final_response_log = new JSONArray();
            JSONArray final_response_picture = new JSONArray();
            JSONArray final_response_meter_not_in_list = new JSONArray();
            JSONArray final_reponse_for_picture = new JSONArray();

            final_response = UploadReading(JobID,"");
            final_response_log = UploadReadingLogs(JobID,"");
            final_response_picture = UploadPicture("Pending");
            final_response_meter_not_in_list = UploadMeterNotInList(JobID,"Pending");
            final_reponse_for_picture = UploadPictureReading(JobID,"");


            final_data_response.put("data",final_response);
            final_data_response.put("picture",final_response_picture);
            final_data_response.put("logs",final_response_log);
            final_data_response.put("meter_not_in_list",final_response_meter_not_in_list);
            final_data_response.put("get_picture_data",final_reponse_for_picture);
            return final_data_response;
        }catch(Exception e){
            Log.e(TAG,"Error: ",e);
            return null;
        }
    }

    public static JSONObject UploadReadingLog(){
        try{
            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray final_response_picture = new JSONArray();

            final_response = UploadReadingLogs(Liquid.SelectedId,"");
            final_response_picture = UploadPicture("");
            Log.i(TAG, String.valueOf(final_response));
            final_data_response.put("data",final_response);
            final_data_response.put("picture",final_response_picture);

            return final_data_response;
        }catch(Exception e){
            Log.e(TAG,"Error: ",e);
            return null;
        }
    }
    public static JSONObject UploadAccountReading(String AccountNumber){
        try{

            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray final_response_picture = new JSONArray();

            final_response = UploadReading(Liquid.SelectedId,AccountNumber);
            final_response_picture = UploadPicture(AccountNumber);

            final_data_response.put("data",final_response);
            final_data_response.put("picture",final_response_picture);

            return final_data_response;
        }catch(Exception e){
            Log.e(TAG,"Error: ",e);
            return null;
        }
    }
    public static JSONArray UploadMeterNotInList(String job_id,String status){
        JSONArray final_response = new JSONArray();
        Cursor result = MeterNotInListModel.GetMeterNotInListUpload(job_id,status);

        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("id",result.getString(25));
                data.put("job_id",result.getString(0));
                data.put("client",result.getString(1));
                data.put("request_type","MobileDoPostMeterNotInListDetails");
                data.put("lastname","");
                data.put("firstname","");
                data.put("timestamp",result.getString(9));
                data.put("customer_address",result.getString(4));
                data.put("customer_meterno",result.getString(2));
                data.put("customer_name",result.getString(3));
                data.put("remarks_id","");
                data.put("remarks_description",result.getString(5));
                data.put("reading", result.getString(17));
                data.put("remarks", result.getString(5));
                data.put("latitude",result.getString(7));
                data.put("longitude",result.getString(8));
                data.put("reader_id",result.getString(10));
                data.put("route",result.getString(12));
                data.put("itinerary",result.getString(13));
                data.put("reading_date",result.getString(14));
                data.put("contactnumber",result.getString(18));
                data.put("emailaddress",result.getString(19));
                data.put("meterbrand",result.getString(20));
                data.put("metertype",result.getString(21));
                data.put("structure",result.getString(22));
                data.put("nearest_meter",result.getString(15));
                data.put("nearest_seq",result.getString(16));
                data.put("row_id",result.getString(26));
                data.put("accountnumber",result.getString(23));
                data.put("serial",result.getString(24));
                data.put("demand",result.getString(26));
                data.put("ampirCapacity",result.getString(27));
                data.put("type",result.getString(28));
                data.put("house_latitude",result.getString(29));
                data.put("house_longitude",result.getString(30));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",result.getString(10));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return final_response;
    }
    public static JSONArray UploadPicture(String Status){
        JSONArray final_response = new JSONArray();
        Cursor result = AuditModel.GetUploadPicture(Status);

        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("client",result.getString(0));
                data.put("AccountNumber",result.getString(1));
                data.put("type",result.getString(2));
                data.put("picture",result.getString(3));
                data.put("timestamp",result.getString(4));
                data.put("modifieddate",result.getString(5));
                data.put("Reader_ID",result.getString(6));
                data.put("modifiedby", result.getString(7));
                data.put("reading_date",result.getString(8));
                data.put("row_id",result.getString(9));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",result.getString(7));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return final_response;
    }
    public static JSONArray UploadPictureReading(String JobOrderId,String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = ReadingModel.GetReadingPictureDetails(JobOrderId, AccountNumber);
        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {

                data.put("Client",result.getString(0));
                data.put("C_ID",result.getString(1));
                data.put("AccountNumber",result.getString(2));
                data.put("job_id",result.getString(3));
                data.put("name",result.getString(4));
                data.put("route",result.getString(5));
                data.put("itinerary",result.getString(6));
                data.put("meter_number",result.getString(7));
                data.put("serial",result.getString(8));
                data.put("previous_reading",result.getString(9));
                data.put("present_Reading",result.getString(10));
                data.put("previous_consumption",result.getString(11));
                data.put("Present_Consumption",result.getString(12));
                data.put("previous_reading_date",result.getString(13));
                data.put("present_reading_date",result.getString(14));
                data.put("duedate",result.getString(15));
                data.put("discondate",result.getString(16));
                data.put("Reading_Date",result.getString(17));
                data.put("BillYear",result.getString(18));
                data.put("BillMonth",result.getString(19));
                data.put("BillDate",result.getString(20));
                data.put("month",result.getString(21));
                data.put("day",result.getString(22));
                data.put("year",result.getString(23));
                data.put("Demand",result.getString(24));
                data.put("reactive",result.getString(25));
                data.put("powerfactor",result.getString(26));
                data.put("kw_cummulative",result.getString(27));
                data.put("reading1",result.getString(28));
                data.put("reading2",result.getString(29));
                data.put("reading3",result.getString(30));
                data.put("reading4",result.getString(31));
                data.put("reading5",result.getString(32));
                data.put("reading6",result.getString(33));
                data.put("reading7",result.getString(34));
                data.put("reading8",result.getString(35));
                data.put("reading9",result.getString(36));
                data.put("reading10",result.getString(37));
                data.put("iwpowerfactor",result.getString(38));
                data.put("demand_consumption",result.getString(39));
                data.put("reactive_consumption",result.getString(40));
                data.put("Averange_Consumption",result.getString(41));
                data.put("Average_Reading",result.getString(42));
                data.put("multiplier",result.getString(43));
                data.put("Meter_Box",result.getString(44));
                data.put("Demand_Reset",result.getString(45));
                data.put("Test_Block",result.getString(46));
                data.put("Remarks_Code",result.getString(47));
                data.put("remarks_abbreviation",result.getString(48));
                data.put("Remarks",result.getString(49));
                data.put("Comment",result.getString(50));
                data.put("Reader_ID",result.getString(51));
                data.put("meter_reader",result.getString(52));
                data.put("Reading_Attempt",result.getString(53));
                data.put("Print_Attempt",result.getString(54));
                data.put("force_reading",result.getString(55));
                data.put("r_latitude",result.getString(56));
                data.put("r_longitude",result.getString(57));
                data.put("printlatitude",result.getString(58));
                data.put("printlongitude",result.getString(59));
                data.put("improbable_reading",result.getString(60));
                data.put("negative_reading",result.getString(61));
                data.put("change_reading",result.getString(62));
                data.put("cg_vat_zero_tag",result.getString(63));
                data.put("reading_remarks",result.getString(64));
                data.put("old_key",result.getString(65));
                data.put("new_key",result.getString(66));
                data.put("transfer_data_status",result.getString(67));
                data.put("upload_status",result.getString(68));
                data.put("code",result.getString(69));
                data.put("area",result.getString(70));
                data.put("rate_code",result.getString(71));
                data.put("cummulative_multiplier",result.getString(72));
                data.put("oebr_number",result.getString(73));
                data.put("sequence",result.getString(74));
                data.put("Reading_TimeStamp",result.getString(75));
                data.put("Print_TimeStamp",result.getString(76));
                data.put("timestamp",result.getString(77));
                data.put("bill_number",result.getString(78));
                data.put("GenerationSystem",result.getString(79));
                data.put("BenHost",result.getString(80));
                data.put("GRAM",result.getString(81));
                data.put("ICERA",result.getString(82));
                data.put("PowerArtReduction",result.getString(83));
                data.put("TransmissionDelivery",result.getString(84));
                data.put("TransmissionDelivery2",result.getString(85));
                data.put("System_Loss",result.getString(86));
                data.put("Gen_Trans_Rev",result.getString(87));
                data.put("DistributionNetwork",result.getString(88));
                data.put("DistributionNetwork2",result.getString(89));
                data.put("DistributionNetwork3",result.getString(90));
                data.put("RetailElectricService",result.getString(91));
                data.put("RetailElectricService2",result.getString(92));
                data.put("Metering(cust)",result.getString(93));
                data.put("Metering(cust)2",result.getString(94));
                data.put("Metering(kwh)",result.getString(95));
                data.put("loan",result.getString(96));
                data.put("RFSC",result.getString(97));
                data.put("Distribution_Rev",result.getString(98));
                data.put("MissionaryElectrification",result.getString(99));
                data.put("EnvironmentCharge",result.getString(100));
                data.put("NPC_StrandedDebts",result.getString(101));
                data.put("NPC_StrandedCost",result.getString(102));
                data.put("DUsCost",result.getString(103));
                data.put("DCDistributionCharge",result.getString(104));
                data.put("DCDemandCharge",result.getString(105));
                data.put("TCTransSystemCharge",result.getString(106));
                data.put("SCSupplySysCharge",result.getString(107));
                data.put("equal_tax",result.getString(108));
                data.put("CrossSubsidyRemoval",result.getString(109));
                data.put("Universal_Charges",result.getString(110));
                data.put("Lifeline(Charge)",result.getString(111));
                data.put("InterclassCrossSubsidy",result.getString(112));
                data.put("SeniorCitizenSubsidy",result.getString(113));
                data.put("ICCS_Adjustment",result.getString(114));
                data.put("ICCrossSubsidyCharge",result.getString(115));
                data.put("FitAllCharge",result.getString(116));
                data.put("PPD_Adjustment",result.getString(117));
                data.put("GenerationCostAdjustment",result.getString(118));
                data.put("PowerCostAdjustment",result.getString(119));
                data.put("Other_Rev",result.getString(120));
                data.put("GenerationVat",result.getString(121));
                data.put("TransmissionVat",result.getString(122));
                data.put("SystemLossVat",result.getString(123));
                data.put("DistributionVat",result.getString(124));
                data.put("OtherVat",result.getString(125));
                data.put("Government_Rev",result.getString(126));
                data.put("CurrentBill",result.getString(127));
                data.put("amountdue",result.getString(128));
                data.put("overdue",result.getString(129));
                data.put("franchise_tax",result.getString(130));
                data.put("coreloss",result.getString(131));
                data.put("surcharge",result.getString(132));
                data.put("rentalfee",result.getString(133));
                data.put("delivered",result.getString(134));
                data.put("check_previous",result.getString(135));
                data.put("ispn",result.getString(136));
                data.put("SCD",result.getString(137));
                data.put("pnrecvdte",result.getString(138));
                data.put("pnrecvby",result.getString(139));
                data.put("recvby",result.getString(140));
                data.put("hash",result.getString(141));
                data.put("isreset",result.getString(142));
                data.put("isprntd",result.getString(143));
                data.put("meter_count",result.getString(144));
                data.put("delivery_id",result.getString(145));
                data.put("delivery_remarks",result.getString(146));
                data.put("delivery_comment",result.getString(147));
                data.put("reading_signature",result.getString(148));
                data.put("real_property_tax",result.getString(149));
                data.put("cc_rstc_refund",result.getString(150));
                data.put("cc_rstc_refund2",result.getString(151));
                data.put("moa",result.getString(152));
                data.put("eda",result.getString(153));
                data.put("ModifiedDate",result.getString(154));
                data.put("ModifiedBy",result.getString(155));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(data));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return final_response;
    }
    public static JSONArray UploadReading(String JobOrderId,String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = ReadingModel.GetReadingDetails(JobOrderId, AccountNumber,"Pending");
        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {

                data.put("Client",result.getString(0));
                data.put("C_ID",result.getString(1));
                data.put("AccountNumber",result.getString(2));
                data.put("job_id",result.getString(3));
                data.put("name",result.getString(4));
                data.put("route",result.getString(5));
                data.put("itinerary",result.getString(6));
                data.put("meter_number",result.getString(7));
                data.put("serial",result.getString(8));
                data.put("previous_reading",result.getString(9));
                data.put("present_Reading",result.getString(10));
                data.put("previous_consumption",result.getString(11));
                data.put("Present_Consumption",result.getString(12));
                data.put("previous_reading_date",result.getString(13));
                data.put("present_reading_date",result.getString(14));
                data.put("duedate",result.getString(15));
                data.put("discondate",result.getString(16));
                data.put("Reading_Date",result.getString(17));
                data.put("BillYear",result.getString(18));
                data.put("BillMonth",result.getString(19));
                data.put("BillDate",result.getString(20));
                data.put("month",result.getString(21));
                data.put("day",result.getString(22));
                data.put("year",result.getString(23));
                data.put("Demand",result.getString(24));
                data.put("reactive",result.getString(25));
                data.put("powerfactor",result.getString(26));
                data.put("kw_cummulative",result.getString(27));
                data.put("reading1",result.getString(28));
                data.put("reading2",result.getString(29));
                data.put("reading3",result.getString(30));
                data.put("reading4",result.getString(31));
                data.put("reading5",result.getString(32));
                data.put("reading6",result.getString(33));
                data.put("reading7",result.getString(34));
                data.put("reading8",result.getString(35));
                data.put("reading9",result.getString(36));
                data.put("reading10",result.getString(37));
                data.put("iwpowerfactor",result.getString(38));
                data.put("demand_consumption",result.getString(39));
                data.put("reactive_consumption",result.getString(40));
                data.put("Averange_Consumption",result.getString(41));
                data.put("Average_Reading",result.getString(42));
                data.put("multiplier",result.getString(43));
                data.put("Meter_Box",result.getString(44));
                data.put("Demand_Reset",result.getString(45));
                data.put("Test_Block",result.getString(46));
                data.put("Remarks_Code",result.getString(47));
                data.put("remarks_abbreviation",result.getString(48));
                data.put("Remarks",result.getString(49));
                data.put("Comment",result.getString(50));
                data.put("Reader_ID",result.getString(51));
                data.put("meter_reader",result.getString(52));
                data.put("Reading_Attempt",result.getString(53));
                data.put("Print_Attempt",result.getString(54));
                data.put("force_reading",result.getString(55));
                data.put("r_latitude",result.getString(56));
                data.put("r_longitude",result.getString(57));
                data.put("printlatitude",result.getString(58));
                data.put("printlongitude",result.getString(59));
                data.put("improbable_reading",result.getString(60));
                data.put("negative_reading",result.getString(61));
                data.put("change_reading",result.getString(62));
                data.put("cg_vat_zero_tag",result.getString(63));
                data.put("reading_remarks",result.getString(64));
                data.put("old_key",result.getString(65));
                data.put("new_key",result.getString(66));
                data.put("transfer_data_status",result.getString(67));
                data.put("upload_status",result.getString(68));
                data.put("code",result.getString(69));
                data.put("area",result.getString(70));
                data.put("rate_code",result.getString(71));
                data.put("cummulative_multiplier",result.getString(72));
                data.put("oebr_number",result.getString(73));
                data.put("sequence",result.getString(74));
                data.put("Reading_TimeStamp",result.getString(75));
                data.put("Print_TimeStamp",result.getString(76));
                data.put("timestamp",result.getString(77));
                data.put("bill_number",result.getString(78));
                data.put("GenerationSystem",result.getString(79));
                data.put("BenHost",result.getString(80));
                data.put("GRAM",result.getString(81));
                data.put("ICERA",result.getString(82));
                data.put("PowerArtReduction",result.getString(83));
                data.put("TransmissionDelivery",result.getString(84));
                data.put("TransmissionDelivery2",result.getString(85));
                data.put("System_Loss",result.getString(86));
                data.put("Gen_Trans_Rev",result.getString(87));
                data.put("DistributionNetwork",result.getString(88));
                data.put("DistributionNetwork2",result.getString(89));
                data.put("DistributionNetwork3",result.getString(90));
                data.put("RetailElectricService",result.getString(91));
                data.put("RetailElectricService2",result.getString(92));
                data.put("Metering(cust)",result.getString(93));
                data.put("Metering(cust)2",result.getString(94));
                data.put("Metering(kwh)",result.getString(95));
                data.put("loan",result.getString(96));
                data.put("RFSC",result.getString(97));
                data.put("Distribution_Rev",result.getString(98));
                data.put("MissionaryElectrification",result.getString(99));
                data.put("EnvironmentCharge",result.getString(100));
                data.put("NPC_StrandedDebts",result.getString(101));
                data.put("NPC_StrandedCost",result.getString(102));
                data.put("DUsCost",result.getString(103));
                data.put("DCDistributionCharge",result.getString(104));
                data.put("DCDemandCharge",result.getString(105));
                data.put("TCTransSystemCharge",result.getString(106));
                data.put("SCSupplySysCharge",result.getString(107));
                data.put("equal_tax",result.getString(108));
                data.put("CrossSubsidyRemoval",result.getString(109));
                data.put("Universal_Charges",result.getString(110));
                data.put("Lifeline(Charge)",result.getString(111));
                data.put("InterclassCrossSubsidy",result.getString(112));
                data.put("SeniorCitizenSubsidy",result.getString(113));
                data.put("ICCS_Adjustment",result.getString(114));
                data.put("ICCrossSubsidyCharge",result.getString(115));
                data.put("FitAllCharge",result.getString(116));
                data.put("PPD_Adjustment",result.getString(117));
                data.put("GenerationCostAdjustment",result.getString(118));
                data.put("PowerCostAdjustment",result.getString(119));
                data.put("Other_Rev",result.getString(120));
                data.put("GenerationVat",result.getString(121));
                data.put("TransmissionVat",result.getString(122));
                data.put("SystemLossVat",result.getString(123));
                data.put("DistributionVat",result.getString(124));
                data.put("OtherVat",result.getString(125));
                data.put("Government_Rev",result.getString(126));
                data.put("CurrentBill",result.getString(127));
                data.put("amountdue",result.getString(128));
                data.put("overdue",result.getString(129));
                data.put("franchise_tax",result.getString(130));
                data.put("coreloss",result.getString(131));
                data.put("surcharge",result.getString(132));
                data.put("rentalfee",result.getString(133));
                data.put("delivered",result.getString(134));
                data.put("check_previous",result.getString(135));
                data.put("ispn",result.getString(136));
                data.put("SCD",result.getString(137));
                data.put("pnrecvdte",result.getString(138));
                data.put("pnrecvby",result.getString(139));
                data.put("recvby",result.getString(140));
                data.put("hash",result.getString(141));
                data.put("isreset",result.getString(142));
                data.put("isprntd",result.getString(143));
                data.put("meter_count",result.getString(144));
                data.put("delivery_id",result.getString(145));
                data.put("delivery_remarks",result.getString(146));
                data.put("delivery_comment",result.getString(147));
                data.put("reading_signature",result.getString(148));
                data.put("real_property_tax",result.getString(149));
                data.put("cc_rstc_refund",result.getString(150));
                data.put("cc_rstc_refund2",result.getString(151));
                data.put("moa",result.getString(152));
                data.put("eda",result.getString(153));
                data.put("ModifiedDate",result.getString(154));
                data.put("ModifiedBy",result.getString(155));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(data));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return final_response;
    }


    public static JSONArray UploadReadingLogs(String JobOrderId,String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = ReadingModel.GetReadingLogsDetails(JobOrderId, AccountNumber,"Pending");
        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("Client",result.getString(0));
                data.put("C_ID",result.getString(1));
                data.put("AccountNumber",result.getString(2));
                data.put("job_id",result.getString(3));
                data.put("name",result.getString(4));
                data.put("route",result.getString(5));
                data.put("itinerary",result.getString(6));
                data.put("meter_number",result.getString(7));
                data.put("serial",result.getString(8));
                data.put("previous_reading",result.getString(9));
                data.put("present_Reading",result.getString(10));
                data.put("previous_consumption",result.getString(11));
                data.put("Present_Consumption",result.getString(12));
                data.put("previous_reading_date",result.getString(13));
                data.put("present_reading_date",result.getString(14));
                data.put("duedate",result.getString(15));
                data.put("discondate",result.getString(16));
                data.put("Reading_Date",result.getString(17));
                data.put("BillYear",result.getString(18));
                data.put("BillMonth",result.getString(19));
                data.put("BillDate",result.getString(20));
                data.put("month",result.getString(21));
                data.put("day",result.getString(22));
                data.put("year",result.getString(23));
                data.put("Demand",result.getString(24));
                data.put("reactive",result.getString(25));
                data.put("powerfactor",result.getString(26));
                data.put("kw_cummulative",result.getString(27));
                data.put("reading1",result.getString(28));
                data.put("reading2",result.getString(29));
                data.put("reading3",result.getString(30));
                data.put("reading4",result.getString(31));
                data.put("reading5",result.getString(32));
                data.put("reading6",result.getString(33));
                data.put("reading7",result.getString(34));
                data.put("reading8",result.getString(35));
                data.put("reading9",result.getString(36));
                data.put("reading10",result.getString(37));
                data.put("iwpowerfactor",result.getString(38));
                data.put("demand_consumption",result.getString(39));
                data.put("reactive_consumption",result.getString(40));
                data.put("Averange_Consumption",result.getString(41));
                data.put("Average_Reading",result.getString(42));
                data.put("multiplier",result.getString(43));
                data.put("Meter_Box",result.getString(44));
                data.put("Demand_Reset",result.getString(45));
                data.put("Test_Block",result.getString(46));
                data.put("Remarks_Code",result.getString(47));
                data.put("remarks_abbreviation",result.getString(48));
                data.put("Remarks",result.getString(49));
                data.put("Comment",result.getString(50));
                data.put("Reader_ID",result.getString(51));
                data.put("meter_reader",result.getString(52));
                data.put("Reading_Attempt",result.getString(53));
                data.put("Print_Attempt",result.getString(54));
                data.put("force_reading",result.getString(55));
                data.put("r_latitude",result.getString(56));
                data.put("r_longitude",result.getString(57));
                data.put("printlatitude",result.getString(58));
                data.put("printlongitude",result.getString(59));
                data.put("improbable_reading",result.getString(60));
                data.put("negative_reading",result.getString(61));
                data.put("change_reading",result.getString(62));
                data.put("cg_vat_zero_tag",result.getString(63));
                data.put("reading_remarks",result.getString(64));
                data.put("old_key",result.getString(65));
                data.put("new_key",result.getString(66));
                data.put("transfer_data_status",result.getString(67));
                data.put("upload_status",result.getString(68));
                data.put("code",result.getString(69));
                data.put("area",result.getString(70));
                data.put("rate_code",result.getString(71));
                data.put("cummulative_multiplier",result.getString(72));
                data.put("oebr_number",result.getString(73));
                data.put("sequence",result.getString(74));
                data.put("Reading_TimeStamp",result.getString(75));
                data.put("Print_TimeStamp",result.getString(76));
                data.put("timestamp",result.getString(77));
                data.put("bill_number",result.getString(78));
                data.put("GenerationSystem",result.getString(79));
                data.put("BenHost",result.getString(80));
                data.put("GRAM",result.getString(81));
                data.put("ICERA",result.getString(82));
                data.put("PowerArtReduction",result.getString(83));
                data.put("TransmissionDelivery",result.getString(84));
                data.put("TransmissionDelivery2",result.getString(85));
                data.put("System_Loss",result.getString(86));
                data.put("Gen_Trans_Rev",result.getString(87));
                data.put("DistributionNetwork",result.getString(88));
                data.put("DistributionNetwork2",result.getString(89));
                data.put("DistributionNetwork3",result.getString(90));
                data.put("RetailElectricService",result.getString(91));
                data.put("RetailElectricService2",result.getString(92));
                data.put("Metering(cust)",result.getString(93));
                data.put("Metering(cust)2",result.getString(94));
                data.put("Metering(kwh)",result.getString(95));
                data.put("loan",result.getString(96));
                data.put("RFSC",result.getString(97));
                data.put("Distribution_Rev",result.getString(98));
                data.put("MissionaryElectrification",result.getString(99));
                data.put("EnvironmentCharge",result.getString(100));
                data.put("NPC_StrandedDebts",result.getString(101));
                data.put("NPC_StrandedCost",result.getString(102));
                data.put("DUsCost",result.getString(103));
                data.put("DCDistributionCharge",result.getString(104));
                data.put("DCDemandCharge",result.getString(105));
                data.put("TCTransSystemCharge",result.getString(106));
                data.put("SCSupplySysCharge",result.getString(107));
                data.put("equal_tax",result.getString(108));
                data.put("CrossSubsidyRemoval",result.getString(109));
                data.put("Universal_Charges",result.getString(110));
                data.put("Lifeline(Charge)",result.getString(111));
                data.put("InterclassCrossSubsidy",result.getString(112));
                data.put("SeniorCitizenSubsidy",result.getString(113));
                data.put("ICCS_Adjustment",result.getString(114));
                data.put("ICCrossSubsidyCharge",result.getString(115));
                data.put("FitAllCharge",result.getString(116));
                data.put("PPD_Adjustment",result.getString(117));
                data.put("GenerationCostAdjustment",result.getString(118));
                data.put("PowerCostAdjustment",result.getString(119));
                data.put("Other_Rev",result.getString(120));
                data.put("GenerationVat",result.getString(121));
                data.put("TransmissionVat",result.getString(122));
                data.put("SystemLossVat",result.getString(123));
                data.put("DistributionVat",result.getString(124));
                data.put("OtherVat",result.getString(125));
                data.put("Government_Rev",result.getString(126));
                data.put("CurrentBill",result.getString(127));
                data.put("amountdue",result.getString(128));
                data.put("overdue",result.getString(129));
                data.put("franchise_tax",result.getString(130));
                data.put("coreloss",result.getString(131));
                data.put("surcharge",result.getString(132));
                data.put("rentalfee",result.getString(133));
                data.put("delivered",result.getString(134));
                data.put("check_previous",result.getString(135));
                data.put("ispn",result.getString(136));
                data.put("SCD",result.getString(137));
                data.put("pnrecvdte",result.getString(138));
                data.put("pnrecvby",result.getString(139));
                data.put("recvby",result.getString(140));
                data.put("hash",result.getString(141));
                data.put("isreset",result.getString(142));
                data.put("isprntd",result.getString(143));
                data.put("meter_count",result.getString(144));
                data.put("delivery_id",result.getString(145));
                data.put("delivery_remarks",result.getString(146));
                data.put("delivery_comment",result.getString(147));
                data.put("reading_signature",result.getString(148));
                data.put("real_property_tax",result.getString(149));
                data.put("cc_rstc_refund",result.getString(150));
                data.put("cc_rstc_refund2",result.getString(151));
                data.put("moa",result.getString(152));
                data.put("eda",result.getString(153));
                data.put("row_id",result.getString(154));
                data.put("ModifiedDate",result.getString(154));
                data.put("ModifiedBy",result.getString(155));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",Liquid.User);
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return final_response;
    }
}
