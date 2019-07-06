package com.aseyel.tgbl.tristangaryleyesa.model;

import android.accounts.Account;
import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

public class ReadingModel {
    private static final String TAG = ReadingModel.class.getSimpleName();


    public static Cursor get_next_customer_sequence(String job_id, String rowid)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select job_id,C_AccountNumber,rowid from customer_reading_downloads " +
                        "where job_id = '"+job_id+"' and rowid = ('"+rowid+"' + 1) " +
                        "order by cast(CED_Sequence as int) LIMIT 1"

        );
    }

    public static Cursor get_prev_customer_sequence(String job_id, String rowid)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select job_id,C_AccountNumber,rowid from customer_reading_downloads " +
                        "where job_id = '"+job_id+"' and rowid = ('"+rowid+"' - 1) " +
                        "order by cast(CED_Sequence as int) LIMIT 1"

        );
    }

    public static Cursor get_customer_moa(String client, String accountnumber, String readingdate){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT *" +
                        "FROM moa "+
                        "WHERE client = '"+client+"' AND accountnumber ='"+accountnumber+"' AND reading_date ='"+readingdate+"'");
    }

    public static Cursor GetDataDownload(String job_id){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "COUNT(rowid) as total_download " + //0
                        "FROM customer_reading_downloads "+
                        "WHERE job_id = '"+job_id+"'");
    }
    public static Cursor GetDataUploaded(String job_id, String status){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                       "SELECT " +
                        "COUNT(rowid) as total_upload " + //0
                        "FROM reading "+
                        "WHERE job_id = '"+job_id+"' AND upload_status ='"+status+"'");


    }

    public static Cursor GetPicture(String AccountNumber) {
        String where = "";
        if(!AccountNumber.equals("")){
            where = "WHERE AccountNumber like '" + AccountNumber + "' ";
        }
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "client," +//0
                        "AccountNumber, " +//1
                        "type, " +//2
                        "picture, " +//3
                        "timestamp, " +//4
                        "modifieddate, " +//5
                        "Reader_ID, " +//6
                        "modifiedby, " +//7
                        "reading_date " +//8
                        "FROM meter_reading_pictures  "+
                        where +
                        "ORDER BY timestamp ASC ");
    }

    public static Cursor GetMapData(String job_id) {

        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "c.job_id as JobId,  "+ //0
                        "c.account_name as Name, "+ //1
                        "c.account_name as Name_2, "+ //2
                        "c.C_ID as Customer_No, "+ //3
                        "c.C_Type as MKTSGM, "+ //4
                        "c.route as SEGM, "+ //5
                        "c.Complete_Address as City, "+ //6
                        "c.CED_Sequence as sequence, "+ //7
                        "c.C_SysEntryDate as sysentrydate, "+ //8
                        "c.latitude as Latitude, "+ //9
                        "c.longitude as Longitude, "+ //10
                        "c.C_Status as status, "+ //11
                        "c.CED_MeterNumber as meternumber, "+ //12
                        "c.C_AccountNumber as accountnumber, "+ //13
                        "r.present_Reading as reading, "+ //14
                        "r.r_latitude as R_Latitude, "+ //15
                        "r.r_longitude as R_Longitude "+ //16
                        "FROM customer_reading_downloads c "+
                        "LEFT JOIN reading r on "+
                        "r.job_id = c.job_id and "+
                        "r.C_ID = c.C_ID "+
                        "WHERE c.job_id = '"+job_id +"'");

    }

    public static Cursor GetUntransferdData()
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select " +
                        "job_id," + //0
                        "AccountNumber," + //1
                        "r_latitude," + //2
                        "r_longitude," + //3
                        "Remarks," +//4
                        "Present_Consumption" +//5
                        " from reading  WHERE transfer_data_status = 'Pending' LIMIT 1"
        );
    }
    public static boolean UpdateTransferStatus(String JobId,String AccountNumber){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "transfer_data_status",
            };
            Liquid.LiquidValues = new String[] {
                    "Uploaded",
            };
            whereClause = "AccountNumber=? AND job_id =?";
            whereArgs = new String[] {AccountNumber,JobId};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("reading",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Tristan Gary Leyesa", e);
            return false;
        }
    }
    public static boolean UpdateUploadStatus(String JobId,String AccountNumber){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                  "upload_status",
            };
            Liquid.LiquidValues = new String[] {
                    "Uploaded",
            };
            whereClause = "AccountNumber=? AND job_id =?";
            whereArgs = new String[] {AccountNumber,JobId};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("reading",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Tristan Gary Leyesa", e);
            return false;
        }
    }



    public static boolean UpdateUploadStatusPicture(String row_id){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "transfer_status",
            };
            Liquid.LiquidValues = new String[] {
                    "Uploaded",
            };
            whereClause = "rowid=?";
            whereArgs = new String[] {row_id};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("meter_reading_pictures",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Tristan Gary Leyesa", e);
            return false;
        }
    }

    public static boolean UpdateUploadStatusMeterNotInList(String row_id){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "transfer_status",
            };
            Liquid.LiquidValues = new String[] {
                    "Uploaded",
            };
            whereClause = "rowid=?";
            whereArgs = new String[] {row_id};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("meter_not_in_list",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Tristan Gary Leyesa", e);
            return false;
        }
    }
    public static boolean UpdateUploadStatusLogs(String row_id){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "upload_status",
            };
            Liquid.LiquidValues = new String[] {
                    "Uploaded",
            };
            whereClause = "rowid=?";
            whereArgs = new String[] {row_id};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("reading_logs",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Tristan Gary Leyesa", e);
            return false;
        }
    }

    public static boolean UpdatePrintAttempt(String JobId,String AccountNumber,String printattempt) {
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "Print_Attempt",
            };
            Liquid.LiquidValues = new String[] {
                    printattempt,
            };
            whereClause = "AccountNumber=? AND job_id =?";
            whereArgs = new String[] { AccountNumber,JobId };
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("reading",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static Cursor GetReadingDetails(String job_id,String AccountNumber){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "[Client] ,"+ //0
                        "[C_ID] ,"+ //1
                        "[AccountNumber] ,"+ //2
                        "[job_id],"+ //3
                        "[name],"+//4
                        "[route],"+//5
                        "[itinerary],"+//6
                        "[meter_number] ,"+//7
                        "[serial],"+//8
                        "[previous_reading],"+//9
                        "[present_Reading],"+//10
                        "[previous_consumption],"+ //11
                        "[Present_Consumption],"+ //12
                        "[previous_reading_date],"+ //13
                        "[present_reading_date],"+ //14
                        "[duedate],"+ //15
                        "[discondate],"+ //16
                        "[Reading_Date] ,"+ //17
                        "[BillYear],"+ //18
                        "[BillMonth],"+ //19
                        "[BillDate],"+ //20
                        "[month],"+ //21
                        "[day],"+ //22
                        "[year],"+ //23
                        "[Demand],"+ //24
                        "[reactive],"+ //25
                        "[powerfactor],"+ //26
                        "[kw_cummulative],"+ //27
                        "[reading1],"+ //28
                        "[reading2],"+ //29
                        "[reading3],"+ //30
                        "[reading4],"+ //31
                        "[reading5],"+ //32
                        "[reading6],"+ //33
                        "[reading7],"+ //34
                        "[reading8],"+ //35
                        "[reading9],"+ //36
                        "[reading10],"+ //37
                        "[iwpowerfactor],"+ //38
                        "[demand_consumption],"+ //39
                        "[reactive_consumption],"+ //40
                        "[Averange_Consumption],"+ //41
                        "[Average_Reading],"+ //42
                        "[multiplier],"+ //43
                        "[Meter_Box],"+ //44
                        "[Demand_Reset],"+ //45
                        "[Test_Block],"+ //46
                        "[Remarks_Code],"+ //47
                        "[remarks_abbreviation],"+ //48
                        "[Remarks],"+ //49
                        "[Comment],"+ //50
                        "[Reader_ID],"+ //51
                        "[meter_reader],"+ //52
                        "[Reading_Attempt],"+ //53
                        "[Print_Attempt],"+ //54
                        "[force_reading],"+ //55
                        "[r_latitude],"+ //56
                        "[r_longitude],"+ //57
                        "[printlatitude],"+ //58
                        "[printlongitude],"+ //59
                        "[improbable_reading],"+ //60
                        "[negative_reading],"+ //61
                        "[change_reading],"+ //62
                        "[cg_vat_zero_tag],"+ //63
                        "[reading_remarks],"+ //64
                        "[old_key],"+ //65
                        "[new_key],"+ //66
                        "[transfer_data_status],"+ //67
                        "[upload_status],"+ //68
                        "[code],"+ //69
                        "[area],"+ //70
                        "[rate_code],"+ //71
                        "[cummulative_multiplier],"+ //72
                        "[oebr_number],"+ //73
                        "[sequence],"+ //74
                        "[Reading_TimeStamp],"+ //75
                        "[Print_TimeStamp],"+ //76
                        "[timestamp],"+ //77
                        "[bill_number],"+ //78
                        "[GenerationSystem],"+ //79
                        "[BenHost],"+ //80
                        "[GRAM],"+//81
                        "[ICERA],"+//82
                        "[PowerArtReduction],"+//83
                        "[TransmissionDelivery],"+//84
                        "[TransmissionDelivery2],"+//85
                        "[System_Loss],"+//86
                        "[Gen_Trans_Rev],"+//87
                        "[DistributionNetwork],"+//88
                        "[DistributionNetwork2],"+//89
                        "[DistributionNetwork3],"+//90
                        "[RetailElectricService],"+//91
                        "[RetailElectricService2],"+//92
                        "[Metering(cust)],"+//93
                        "[Metering(cust)2],"+//94
                        "[Metering(kwh)],"+//95
                        "[loan],"+//96
                        "[RFSC],"+//97
                        "[Distribution_Rev],"+//98
                        "[MissionaryElectrification],"+//99
                        "[EnvironmentCharge],"+//100
                        "[NPC_StrandedDebts],"+//101
                        "[NPC_StrandedCost],"+//102
                        "[DUsCost],"+//103
                        "[DCDistributionCharge],"+//104
                        "[DCDemandCharge],"+//105
                        "[TCTransSystemCharge],"+//106
                        "[SCSupplySysCharge],"+//107
                        "[equal_tax],"+//108
                        "[CrossSubsidyRemoval],"+//109
                        "[Universal_Charges],"+//110
                        "[Lifeline(Charge)],"+//111
                        "[InterclassCrossSubsidy],"+//112
                        "[SeniorCitizenSubsidy],"+//113
                        "[ICCS_Adjustment],"+//114
                        "[ICCrossSubsidyCharge],"+//115
                        "[FitAllCharge],"+//116
                        "[PPD_Adjustment],"+//117
                        "[GenerationCostAdjustment],"+//118
                        "[PowerCostAdjustment],"+//119
                        "[Other_Rev],"+//120
                        "[GenerationVat],"+//121
                        "[TransmissionVat],"+//122
                        "[SystemLossVat],"+//123
                        "[DistributionVat],"+//124
                        "[OtherVat],"+//125
                        "[Government_Rev],"+//126
                        "[CurrentBill],"+//127
                        "[amountdue],"+//128
                        "[overdue],"+//129
                        "[franchise_tax],"+//130
                        "[coreloss],"+//131
                        "[surcharge],"+//132
                        "[rentalfee],"+//133
                        "[delivered],"+//134
                        "[check_previous],"+//135
                        "[ispn],"+
                        "[SCD],"+
                        "[pnrecvdte],"+
                        "[pnrecvby],"+
                        "[recvby],"+
                        "[hash],"+
                        "[isreset],"+
                        "[isprntd],"+
                        "[meter_count],"+
                        "[delivery_id],"+
                        "[delivery_remarks],"+
                        "[delivery_comment],"+
                        "[reading_signature],"+
                        "[real_property_tax],"+
                        "[cc_rstc_refund],"+
                        "[cc_rstc_refund2],"+
                        "[moa],"+
                        "[eda],"+
                        "[ModifiedDate],"+
                        "[ModifiedBy] "+
                        "FROM reading "+
                        "WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%'");
    }


    public static Cursor GetReadingLogsDetails(String job_id,String AccountNumber){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "[Client] ,"+ //0
                        "[C_ID] ,"+ //1
                        "[AccountNumber] ,"+ //2
                        "[job_id],"+ //3
                        "[name],"+//4
                        "[route],"+//5
                        "[itinerary],"+//6
                        "[meter_number] ,"+//7
                        "[serial],"+//8
                        "[previous_reading],"+//9
                        "[present_Reading],"+//10
                        "[previous_consumption],"+ //11
                        "[Present_Consumption],"+ //12
                        "[previous_reading_date],"+ //13
                        "[present_reading_date],"+ //14
                        "[duedate],"+ //15
                        "[discondate],"+ //16
                        "[Reading_Date] ,"+ //17
                        "[BillYear],"+ //18
                        "[BillMonth],"+ //19
                        "[BillDate],"+ //20
                        "[month],"+ //21
                        "[day],"+ //22
                        "[year],"+ //23
                        "[Demand],"+ //24
                        "[reactive],"+ //25
                        "[powerfactor],"+ //26
                        "[kw_cummulative],"+ //27
                        "[reading1],"+ //28
                        "[reading2],"+ //29
                        "[reading3],"+ //30
                        "[reading4],"+ //31
                        "[reading5],"+ //32
                        "[reading6],"+ //33
                        "[reading7],"+ //34
                        "[reading8],"+ //35
                        "[reading9],"+ //36
                        "[reading10],"+ //37
                        "[iwpowerfactor],"+ //38
                        "[demand_consumption],"+ //39
                        "[reactive_consumption],"+ //40
                        "[Averange_Consumption],"+ //41
                        "[Average_Reading],"+ //42
                        "[multiplier],"+ //43
                        "[Meter_Box],"+ //44
                        "[Demand_Reset],"+ //45
                        "[Test_Block],"+ //46
                        "[Remarks_Code],"+ //47
                        "[remarks_abbreviation],"+ //48
                        "[Remarks],"+ //49
                        "[Comment],"+ //50
                        "[Reader_ID],"+ //51
                        "[meter_reader],"+ //52
                        "[Reading_Attempt],"+ //53
                        "[Print_Attempt],"+ //54
                        "[force_reading],"+ //55
                        "[r_latitude],"+ //56
                        "[r_longitude],"+ //57
                        "[printlatitude],"+ //58
                        "[printlongitude],"+ //59
                        "[improbable_reading],"+ //60
                        "[negative_reading],"+ //61
                        "[change_reading],"+ //62
                        "[cg_vat_zero_tag],"+ //63
                        "[reading_remarks],"+ //64
                        "[old_key],"+ //65
                        "[new_key],"+ //66
                        "[transfer_data_status],"+ //67
                        "[upload_status],"+ //68
                        "[code],"+ //69
                        "[area],"+ //70
                        "[rate_code],"+ //71
                        "[cummulative_multiplier],"+ //72
                        "[oebr_number],"+ //73
                        "[sequence],"+ //74
                        "[Reading_TimeStamp],"+ //75
                        "[Print_TimeStamp],"+ //76
                        "[timestamp],"+ //77
                        "[bill_number],"+ //78
                        "[GenerationSystem],"+ //79
                        "[BenHost],"+ //80
                        "[GRAM],"+//81
                        "[ICERA],"+//82
                        "[PowerArtReduction],"+//83
                        "[TransmissionDelivery],"+//84
                        "[TransmissionDelivery2],"+//85
                        "[System_Loss],"+//86
                        "[Gen_Trans_Rev],"+//87
                        "[DistributionNetwork],"+//88
                        "[DistributionNetwork2],"+//89
                        "[DistributionNetwork3],"+//90
                        "[RetailElectricService],"+//91
                        "[RetailElectricService2],"+//92
                        "[Metering(cust)],"+//93
                        "[Metering(cust)2],"+//94
                        "[Metering(kwh)],"+//95
                        "[loan],"+//96
                        "[RFSC],"+//97
                        "[Distribution_Rev],"+//98
                        "[MissionaryElectrification],"+//99
                        "[EnvironmentCharge],"+//100
                        "[NPC_StrandedDebts],"+//101
                        "[NPC_StrandedCost],"+//102
                        "[DUsCost],"+//103
                        "[DCDistributionCharge],"+//104
                        "[DCDemandCharge],"+//105
                        "[TCTransSystemCharge],"+//106
                        "[SCSupplySysCharge],"+//107
                        "[equal_tax],"+//108
                        "[CrossSubsidyRemoval],"+//109
                        "[Universal_Charges],"+//110
                        "[Lifeline(Charge)],"+//111
                        "[InterclassCrossSubsidy],"+//112
                        "[SeniorCitizenSubsidy],"+//113
                        "[ICCS_Adjustment],"+//114
                        "[ICCrossSubsidyCharge],"+//115
                        "[FitAllCharge],"+//116
                        "[PPD_Adjustment],"+//117
                        "[GenerationCostAdjustment],"+//118
                        "[PowerCostAdjustment],"+//119
                        "[Other_Rev],"+//120
                        "[GenerationVat],"+//121
                        "[TransmissionVat],"+//122
                        "[SystemLossVat],"+//123
                        "[DistributionVat],"+//124
                        "[OtherVat],"+//125
                        "[Government_Rev],"+//126
                        "[CurrentBill],"+//127
                        "[amountdue],"+//128
                        "[overdue],"+//129
                        "[franchise_tax],"+//130
                        "[coreloss],"+//131
                        "[surcharge],"+//132
                        "[rentalfee],"+//133
                        "[delivered],"+//134
                        "[check_previous],"+//135
                        "[ispn],"+
                        "[SCD],"+
                        "[pnrecvdte],"+
                        "[pnrecvby],"+
                        "[recvby],"+
                        "[hash],"+
                        "[isreset],"+
                        "[isprntd],"+
                        "[meter_count],"+
                        "[delivery_id],"+
                        "[delivery_remarks],"+
                        "[delivery_comment],"+
                        "[reading_signature],"+
                        "[real_property_tax],"+
                        "[cc_rstc_refund],"+
                        "[cc_rstc_refund2],"+
                        "[moa],"+
                        "[eda],"+
                        "[ModifiedDate],"+
                        "[ModifiedBy] "+
                        "FROM reading_logs "+
                        "WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%'");
    }

    public static Cursor GetReadingDetails(String job_id,String AccountNumber,String Status){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "[Client] ,"+ //0
                        "[C_ID] ,"+ //1
                        "[AccountNumber] ,"+ //2
                        "[job_id],"+ //3
                        "[name],"+//4
                        "[route],"+//5
                        "[itinerary],"+//6
                        "[meter_number] ,"+//7
                        "[serial],"+//8
                        "[previous_reading],"+//9
                        "[present_Reading],"+//10
                        "[previous_consumption],"+ //11
                        "[Present_Consumption],"+ //12
                        "[previous_reading_date],"+ //13
                        "[present_reading_date],"+ //14
                        "[duedate],"+ //15
                        "[discondate],"+ //16
                        "[Reading_Date] ,"+ //17
                        "[BillYear],"+ //18
                        "[BillMonth],"+ //19
                        "[BillDate],"+ //20
                        "[month],"+ //21
                        "[day],"+ //22
                        "[year],"+ //23
                        "[Demand],"+ //24
                        "[reactive],"+ //25
                        "[powerfactor],"+ //26
                        "[kw_cummulative],"+ //27
                        "[reading1],"+ //28
                        "[reading2],"+ //29
                        "[reading3],"+ //30
                        "[reading4],"+ //31
                        "[reading5],"+ //32
                        "[reading6],"+ //33
                        "[reading7],"+ //34
                        "[reading8],"+ //35
                        "[reading9],"+ //36
                        "[reading10],"+ //37
                        "[iwpowerfactor],"+ //38
                        "[demand_consumption],"+ //39
                        "[reactive_consumption],"+ //40
                        "[Averange_Consumption],"+ //41
                        "[Average_Reading],"+ //42
                        "[multiplier],"+ //43
                        "[Meter_Box],"+ //44
                        "[Demand_Reset],"+ //45
                        "[Test_Block],"+ //46
                        "[Remarks_Code],"+ //47
                        "[remarks_abbreviation],"+ //48
                        "[Remarks],"+ //49
                        "[Comment],"+ //50
                        "[Reader_ID],"+ //51
                        "[meter_reader],"+ //52
                        "[Reading_Attempt],"+ //53
                        "[Print_Attempt],"+ //54
                        "[force_reading],"+ //55
                        "[r_latitude],"+ //56
                        "[r_longitude],"+ //57
                        "[printlatitude],"+ //58
                        "[printlongitude],"+ //59
                        "[improbable_reading],"+ //60
                        "[negative_reading],"+ //61
                        "[change_reading],"+ //62
                        "[cg_vat_zero_tag],"+ //63
                        "[reading_remarks],"+ //64
                        "[old_key],"+ //65
                        "[new_key],"+ //66
                        "[transfer_data_status],"+ //67
                        "[upload_status],"+ //68
                        "[code],"+ //69
                        "[area],"+ //70
                        "[rate_code],"+ //71
                        "[cummulative_multiplier],"+ //72
                        "[oebr_number],"+ //73
                        "[sequence],"+ //74
                        "[Reading_TimeStamp],"+ //75
                        "[Print_TimeStamp],"+ //76
                        "[timestamp],"+ //77
                        "[bill_number],"+ //78
                        "[GenerationSystem],"+ //79
                        "[BenHost],"+ //80
                        "[GRAM],"+//81
                        "[ICERA],"+//82
                        "[PowerArtReduction],"+//83
                        "[TransmissionDelivery],"+//84
                        "[TransmissionDelivery2],"+//85
                        "[System_Loss],"+//86
                        "[Gen_Trans_Rev],"+//87
                        "[DistributionNetwork],"+//88
                        "[DistributionNetwork2],"+//89
                        "[DistributionNetwork3],"+//90
                        "[RetailElectricService],"+//91
                        "[RetailElectricService2],"+//92
                        "[Metering(cust)],"+//93
                        "[Metering(cust)2],"+//94
                        "[Metering(kwh)],"+//95
                        "[loan],"+//96
                        "[RFSC],"+//97
                        "[Distribution_Rev],"+//98
                        "[MissionaryElectrification],"+//99
                        "[EnvironmentCharge],"+//100
                        "[NPC_StrandedDebts],"+//101
                        "[NPC_StrandedCost],"+//102
                        "[DUsCost],"+//103
                        "[DCDistributionCharge],"+//104
                        "[DCDemandCharge],"+//105
                        "[TCTransSystemCharge],"+//106
                        "[SCSupplySysCharge],"+//107
                        "[equal_tax],"+//108
                        "[CrossSubsidyRemoval],"+//109
                        "[Universal_Charges],"+//110
                        "[Lifeline(Charge)],"+//111
                        "[InterclassCrossSubsidy],"+//112
                        "[SeniorCitizenSubsidy],"+//113
                        "[ICCS_Adjustment],"+//114
                        "[ICCrossSubsidyCharge],"+//115
                        "[FitAllCharge],"+//116
                        "[PPD_Adjustment],"+//117
                        "[GenerationCostAdjustment],"+//118
                        "[PowerCostAdjustment],"+//119
                        "[Other_Rev],"+//120
                        "[GenerationVat],"+//121
                        "[TransmissionVat],"+//122
                        "[SystemLossVat],"+//123
                        "[DistributionVat],"+//124
                        "[OtherVat],"+//125
                        "[Government_Rev],"+//126
                        "[CurrentBill],"+//127
                        "[amountdue],"+//128
                        "[overdue],"+//129
                        "[franchise_tax],"+//130
                        "[coreloss],"+//131
                        "[surcharge],"+//132
                        "[rentalfee],"+//133
                        "[delivered],"+//134
                        "[check_previous],"+//135
                        "[ispn],"+
                        "[SCD],"+
                        "[pnrecvdte],"+
                        "[pnrecvby],"+
                        "[recvby],"+
                        "[hash],"+
                        "[isreset],"+
                        "[isprntd],"+
                        "[meter_count],"+
                        "[delivery_id],"+
                        "[delivery_remarks],"+
                        "[delivery_comment],"+
                        "[reading_signature],"+
                        "[real_property_tax],"+
                        "[cc_rstc_refund],"+
                        "[cc_rstc_refund2],"+
                        "[moa],"+
                        "[eda],"+
                        "[ModifiedDate],"+
                        "[ModifiedBy] "+
                        "FROM reading "+
                        "WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%' AND upload_status = '"+Status+"'");
                        //"WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%' ");
                        //"WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%'");
    }


    public static Cursor GetReadingPictureDetails(String job_id,String AccountNumber){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "[Client] ,"+ //0
                        "[C_ID] ,"+ //1
                        "[AccountNumber] ,"+ //2
                        "[job_id],"+ //3
                        "[name],"+//4
                        "[route],"+//5
                        "[itinerary],"+//6
                        "[meter_number] ,"+//7
                        "[serial],"+//8
                        "[previous_reading],"+//9
                        "[present_Reading],"+//10
                        "[previous_consumption],"+ //11
                        "[Present_Consumption],"+ //12
                        "[previous_reading_date],"+ //13
                        "[present_reading_date],"+ //14
                        "[duedate],"+ //15
                        "[discondate],"+ //16
                        "[Reading_Date] ,"+ //17
                        "[BillYear],"+ //18
                        "[BillMonth],"+ //19
                        "[BillDate],"+ //20
                        "[month],"+ //21
                        "[day],"+ //22
                        "[year],"+ //23
                        "[Demand],"+ //24
                        "[reactive],"+ //25
                        "[powerfactor],"+ //26
                        "[kw_cummulative],"+ //27
                        "[reading1],"+ //28
                        "[reading2],"+ //29
                        "[reading3],"+ //30
                        "[reading4],"+ //31
                        "[reading5],"+ //32
                        "[reading6],"+ //33
                        "[reading7],"+ //34
                        "[reading8],"+ //35
                        "[reading9],"+ //36
                        "[reading10],"+ //37
                        "[iwpowerfactor],"+ //38
                        "[demand_consumption],"+ //39
                        "[reactive_consumption],"+ //40
                        "[Averange_Consumption],"+ //41
                        "[Average_Reading],"+ //42
                        "[multiplier],"+ //43
                        "[Meter_Box],"+ //44
                        "[Demand_Reset],"+ //45
                        "[Test_Block],"+ //46
                        "[Remarks_Code],"+ //47
                        "[remarks_abbreviation],"+ //48
                        "[Remarks],"+ //49
                        "[Comment],"+ //50
                        "[Reader_ID],"+ //51
                        "[meter_reader],"+ //52
                        "[Reading_Attempt],"+ //53
                        "[Print_Attempt],"+ //54
                        "[force_reading],"+ //55
                        "[r_latitude],"+ //56
                        "[r_longitude],"+ //57
                        "[printlatitude],"+ //58
                        "[printlongitude],"+ //59
                        "[improbable_reading],"+ //60
                        "[negative_reading],"+ //61
                        "[change_reading],"+ //62
                        "[cg_vat_zero_tag],"+ //63
                        "[reading_remarks],"+ //64
                        "[old_key],"+ //65
                        "[new_key],"+ //66
                        "[transfer_data_status],"+ //67
                        "[upload_status],"+ //68
                        "[code],"+ //69
                        "[area],"+ //70
                        "[rate_code],"+ //71
                        "[cummulative_multiplier],"+ //72
                        "[oebr_number],"+ //73
                        "[sequence],"+ //74
                        "[Reading_TimeStamp],"+ //75
                        "[Print_TimeStamp],"+ //76
                        "[timestamp],"+ //77
                        "[bill_number],"+ //78
                        "[GenerationSystem],"+ //79
                        "[BenHost],"+ //80
                        "[GRAM],"+//81
                        "[ICERA],"+//82
                        "[PowerArtReduction],"+//83
                        "[TransmissionDelivery],"+//84
                        "[TransmissionDelivery2],"+//85
                        "[System_Loss],"+//86
                        "[Gen_Trans_Rev],"+//87
                        "[DistributionNetwork],"+//88
                        "[DistributionNetwork2],"+//89
                        "[DistributionNetwork3],"+//90
                        "[RetailElectricService],"+//91
                        "[RetailElectricService2],"+//92
                        "[Metering(cust)],"+//93
                        "[Metering(cust)2],"+//94
                        "[Metering(kwh)],"+//95
                        "[loan],"+//96
                        "[RFSC],"+//97
                        "[Distribution_Rev],"+//98
                        "[MissionaryElectrification],"+//99
                        "[EnvironmentCharge],"+//100
                        "[NPC_StrandedDebts],"+//101
                        "[NPC_StrandedCost],"+//102
                        "[DUsCost],"+//103
                        "[DCDistributionCharge],"+//104
                        "[DCDemandCharge],"+//105
                        "[TCTransSystemCharge],"+//106
                        "[SCSupplySysCharge],"+//107
                        "[equal_tax],"+//108
                        "[CrossSubsidyRemoval],"+//109
                        "[Universal_Charges],"+//110
                        "[Lifeline(Charge)],"+//111
                        "[InterclassCrossSubsidy],"+//112
                        "[SeniorCitizenSubsidy],"+//113
                        "[ICCS_Adjustment],"+//114
                        "[ICCrossSubsidyCharge],"+//115
                        "[FitAllCharge],"+//116
                        "[PPD_Adjustment],"+//117
                        "[GenerationCostAdjustment],"+//118
                        "[PowerCostAdjustment],"+//119
                        "[Other_Rev],"+//120
                        "[GenerationVat],"+//121
                        "[TransmissionVat],"+//122
                        "[SystemLossVat],"+//123
                        "[DistributionVat],"+//124
                        "[OtherVat],"+//125
                        "[Government_Rev],"+//126
                        "[CurrentBill],"+//127
                        "[amountdue],"+//128
                        "[overdue],"+//129
                        "[franchise_tax],"+//130
                        "[coreloss],"+//131
                        "[surcharge],"+//132
                        "[rentalfee],"+//133
                        "[delivered],"+//134
                        "[check_previous],"+//135
                        "[ispn],"+
                        "[SCD],"+
                        "[pnrecvdte],"+
                        "[pnrecvby],"+
                        "[recvby],"+
                        "[hash],"+
                        "[isreset],"+
                        "[isprntd],"+
                        "[meter_count],"+
                        "[delivery_id],"+
                        "[delivery_remarks],"+
                        "[delivery_comment],"+
                        "[reading_signature],"+
                        "[real_property_tax],"+
                        "[cc_rstc_refund],"+
                        "[cc_rstc_refund2],"+
                        "[moa],"+
                        "[eda],"+
                        "[ModifiedDate],"+
                        "[ModifiedBy] "+
                        "FROM reading "+
                        //"WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%' AND upload_status = '"+Status+"'");
                        "WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%'");
    }


    public static Cursor GetReadingLogsDetails(String job_id,String AccountNumber,String Status){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "[Client] ,"+ //0
                        "[C_ID] ,"+ //1
                        "[AccountNumber] ,"+ //2
                        "[job_id],"+ //3
                        "[name],"+//4
                        "[route],"+//5
                        "[itinerary],"+//6
                        "[meter_number] ,"+//7
                        "[serial],"+//8
                        "[previous_reading],"+//9
                        "[present_Reading],"+//10
                        "[previous_consumption],"+ //11
                        "[Present_Consumption],"+ //12
                        "[previous_reading_date],"+ //13
                        "[present_reading_date],"+ //14
                        "[duedate],"+ //15
                        "[discondate],"+ //16
                        "[Reading_Date] ,"+ //17
                        "[BillYear],"+ //18
                        "[BillMonth],"+ //19
                        "[BillDate],"+ //20
                        "[month],"+ //21
                        "[day],"+ //22
                        "[year],"+ //23
                        "[Demand],"+ //24
                        "[reactive],"+ //25
                        "[powerfactor],"+ //26
                        "[kw_cummulative],"+ //27
                        "[reading1],"+ //28
                        "[reading2],"+ //29
                        "[reading3],"+ //30
                        "[reading4],"+ //31
                        "[reading5],"+ //32
                        "[reading6],"+ //33
                        "[reading7],"+ //34
                        "[reading8],"+ //35
                        "[reading9],"+ //36
                        "[reading10],"+ //37
                        "[iwpowerfactor],"+ //38
                        "[demand_consumption],"+ //39
                        "[reactive_consumption],"+ //40
                        "[Averange_Consumption],"+ //41
                        "[Average_Reading],"+ //42
                        "[multiplier],"+ //43
                        "[Meter_Box],"+ //44
                        "[Demand_Reset],"+ //45
                        "[Test_Block],"+ //46
                        "[Remarks_Code],"+ //47
                        "[remarks_abbreviation],"+ //48
                        "[Remarks],"+ //49
                        "[Comment],"+ //50
                        "[Reader_ID],"+ //51
                        "[meter_reader],"+ //52
                        "[Reading_Attempt],"+ //53
                        "[Print_Attempt],"+ //54
                        "[force_reading],"+ //55
                        "[r_latitude],"+ //56
                        "[r_longitude],"+ //57
                        "[printlatitude],"+ //58
                        "[printlongitude],"+ //59
                        "[improbable_reading],"+ //60
                        "[negative_reading],"+ //61
                        "[change_reading],"+ //62
                        "[cg_vat_zero_tag],"+ //63
                        "[reading_remarks],"+ //64
                        "[old_key],"+ //65
                        "[new_key],"+ //66
                        "[transfer_data_status],"+ //67
                        "[upload_status],"+ //68
                        "[code],"+ //69
                        "[area],"+ //70
                        "[rate_code],"+ //71
                        "[cummulative_multiplier],"+ //72
                        "[oebr_number],"+ //73
                        "[sequence],"+ //74
                        "[Reading_TimeStamp],"+ //75
                        "[Print_TimeStamp],"+ //76
                        "[timestamp],"+ //77
                        "[bill_number],"+ //78
                        "[GenerationSystem],"+ //79
                        "[BenHost],"+ //80
                        "[GRAM],"+//81
                        "[ICERA],"+//82
                        "[PowerArtReduction],"+//83
                        "[TransmissionDelivery],"+//84
                        "[TransmissionDelivery2],"+//85
                        "[System_Loss],"+//86
                        "[Gen_Trans_Rev],"+//87
                        "[DistributionNetwork],"+//88
                        "[DistributionNetwork2],"+//89
                        "[DistributionNetwork3],"+//90
                        "[RetailElectricService],"+//91
                        "[RetailElectricService2],"+//92
                        "[Metering(cust)],"+//93
                        "[Metering(cust)2],"+//94
                        "[Metering(kwh)],"+//95
                        "[loan],"+//96
                        "[RFSC],"+//97
                        "[Distribution_Rev],"+//98
                        "[MissionaryElectrification],"+//99
                        "[EnvironmentCharge],"+//100
                        "[NPC_StrandedDebts],"+//101
                        "[NPC_StrandedCost],"+//102
                        "[DUsCost],"+//103
                        "[DCDistributionCharge],"+//104
                        "[DCDemandCharge],"+//105
                        "[TCTransSystemCharge],"+//106
                        "[SCSupplySysCharge],"+//107
                        "[equal_tax],"+//108
                        "[CrossSubsidyRemoval],"+//109
                        "[Universal_Charges],"+//110
                        "[Lifeline(Charge)],"+//111
                        "[InterclassCrossSubsidy],"+//112
                        "[SeniorCitizenSubsidy],"+//113
                        "[ICCS_Adjustment],"+//114
                        "[ICCrossSubsidyCharge],"+//115
                        "[FitAllCharge],"+//116
                        "[PPD_Adjustment],"+//117
                        "[GenerationCostAdjustment],"+//118
                        "[PowerCostAdjustment],"+//119
                        "[Other_Rev],"+//120
                        "[GenerationVat],"+//121
                        "[TransmissionVat],"+//122
                        "[SystemLossVat],"+//123
                        "[DistributionVat],"+//124
                        "[OtherVat],"+//125
                        "[Government_Rev],"+//126
                        "[CurrentBill],"+//127
                        "[amountdue],"+//128
                        "[overdue],"+//129
                        "[franchise_tax],"+//130
                        "[coreloss],"+//131
                        "[surcharge],"+//132
                        "[rentalfee],"+//133
                        "[delivered],"+//134
                        "[check_previous],"+//135
                        "[ispn],"+//136
                        "[SCD],"+//137
                        "[pnrecvdte],"+//138
                        "[pnrecvby],"+//139
                        "[recvby],"+//140
                        "[hash],"+//141
                        "[isreset],"+//142
                        "[isprntd],"+//143
                        "[meter_count],"+//144
                        "[delivery_id],"+//145
                        "[delivery_remarks],"+//146
                        "[delivery_comment],"+//147
                        "[reading_signature],"+//148
                        "[real_property_tax],"+//149
                        "[cc_rstc_refund],"+//150
                        "[cc_rstc_refund2],"+//151
                        "[moa],"+//152
                        "[eda],"+//153
                        "[rowid],"+//154
                        "[ModifiedDate],"+//136
                        "[ModifiedBy] "+//136
                        "FROM reading_logs "+
                        "WHERE job_id = '"+job_id+"' AND AccountNumber like '%"+ AccountNumber+"%' AND upload_status = '"+Status+"'");
    }





    public static boolean doReadingLogs(   String Client,
                                       String C_ID,
                                       String AccountNumber,
                                       String job_id,
                                       String name,
                                       String route,
                                       String itinerary,
                                       String meter_number,
                                       String serial,
                                       String previous_reading,
                                       String present_Reading,
                                       String previous_consumption,
                                       String Present_Consumption,
                                       String previous_reading_date,
                                       String present_reading_date,
                                       String duedate,
                                       String discondate,
                                       String Reading_Date,
                                       String BillYear,
                                       String BillMonth,
                                       String BillDate,
                                       String month,
                                       String day,
                                       String year,
                                       String Demand,
                                       String reactive,
                                       String powerfactor,
                                       String kw_cummulative,
                                       String reading1,
                                       String reading2,
                                       String reading3,
                                       String reading4,
                                       String reading5,
                                       String reading6,
                                       String reading7,
                                       String reading8,
                                       String reading9,
                                       String reading10,
                                       String iwpowerfactor,
                                       String demand_consumption,
                                       String reactive_consumption,
                                       String Averange_Consumption,
                                       String Average_Reading,
                                       String multiplier,
                                       String Meter_Box,
                                       String Demand_Reset,
                                       String Test_Block,
                                       String Remarks_Code,
                                       String remarks_abbreviation,
                                       String Remarks,
                                       String Comment,
                                       String Reader_ID,
                                       String meter_reader,
                                       String Reading_Attempt,
                                       String Print_Attempt,
                                       String force_reading,
                                       String r_latitude,
                                       String r_longitude,
                                       String printlatitude,
                                       String printlongitude,
                                       String improbable_reading,
                                       String negative_reading,
                                       String change_reading,
                                       String cg_vat_zero_tag,
                                       String reading_remarks,
                                       String old_key,
                                       String new_key,
                                       String transfer_data_status,
                                       String upload_status,
                                       String code,
                                       String area,
                                       String rate_code,
                                       String cummulative_multiplier,
                                       String oebr_number,
                                       String sequence,
                                       String Reading_TimeStamp,
                                       String Print_TimeStamp,
                                       String timestamp,
                                       String bill_number,
                                       String GenerationSystem,
                                       String BenHost,
                                       String GRAM,
                                       String ICERA,
                                       String PowerArtReduction,
                                       String TransmissionDelivery,
                                       String TransmissionDelivery2,
                                       String System_Loss,
                                       String Gen_Trans_Rev,
                                       String DistributionNetwork,
                                       String DistributionNetwork2,
                                       String DistributionNetwork3,
                                       String RetailElectricService,
                                       String RetailElectricService2,
                                       String Metering_cust,
                                       String Metering_cust_2,
                                       String Metering_kwh,
                                       String loan,
                                       String RFSC,
                                       String Distribution_Rev,
                                       String MissionaryElectrification,
                                       String EnvironmentCharge,
                                       String NPC_StrandedDebts,
                                       String NPC_StrandedCost,
                                       String DUsCost,
                                       String DCDistributionCharge,
                                       String DCDemandCharge,
                                       String TCTransSystemCharge,
                                       String SCSupplySysCharge,
                                       String equal_tax,
                                       String CrossSubsidyRemoval,
                                       String Universal_Charges,
                                       String Lifeline_Charge,
                                       String InterclassCrossSubsidy,
                                       String SeniorCitizenSubsidy,
                                       String ICCS_Adjustment,
                                       String ICCrossSubsidyCharge,
                                       String FitAllCharge,
                                       String PPD_Adjustment,
                                       String GenerationCostAdjustment,
                                       String PowerCostAdjustment,
                                       String Other_Rev,
                                       String GenerationVat,
                                       String TransmissionVat,
                                       String SystemLossVat,
                                       String DistributionVat,
                                       String OtherVat,
                                       String Government_Rev,
                                       String CurrentBill,
                                       String amountdue,
                                       String overdue,
                                       String franchise_tax,
                                       String coreloss,
                                       String surcharge,
                                       String rentalfee,
                                       String delivered,
                                       String check_previous,
                                       String ispn,
                                       String SCD,
                                       String pnrecvdte,
                                       String pnrecvby,
                                       String recvby,
                                       String hash,
                                       String isreset,
                                       String isprntd,
                                       String meter_count,
                                       String delivery_id,
                                       String delivery_remarks,
                                       String delivery_comment,
                                       String reading_signature,
                                       String real_property_tax,
                                       String cc_rstc_refund,
                                       String cc_rstc_refund2,
                                       String moa,
                                       String eda,
                                       String ModifiedDate,
                                       String ModifiedBy
    ){
        try{
            boolean result = false;
            boolean result_logs = false;
            Liquid.LiquidColumns = Liquid.ReadingColumns;
            Liquid.LiquidValues = new String[]{
                    Client,
                    C_ID,
                    AccountNumber,
                    job_id,
                    name,
                    route,
                    itinerary,
                    meter_number,
                    serial,
                    previous_reading,
                    present_Reading,
                    previous_consumption,
                    Present_Consumption,
                    previous_reading_date,
                    present_reading_date,
                    duedate,
                    discondate,
                    Reading_Date,
                    BillYear,
                    BillMonth,
                    BillDate,
                    month,
                    day,
                    year,
                    Demand,
                    reactive,
                    powerfactor,
                    kw_cummulative,
                    reading1,
                    reading2,
                    reading3,
                    reading4,
                    reading5,
                    reading6,
                    reading7,
                    reading8,
                    reading9,
                    reading10,
                    iwpowerfactor,
                    demand_consumption,
                    reactive_consumption,
                    Averange_Consumption,
                    Average_Reading,
                    multiplier,
                    Meter_Box,
                    Demand_Reset,
                    Test_Block,
                    Remarks_Code,
                    remarks_abbreviation,
                    Remarks,
                    Comment,
                    Reader_ID,
                    meter_reader,
                    Reading_Attempt,
                    Print_Attempt,
                    force_reading,
                    r_latitude,
                    r_longitude,
                    printlatitude,
                    printlongitude,
                    improbable_reading,
                    negative_reading,
                    change_reading,
                    cg_vat_zero_tag,
                    reading_remarks,
                    old_key,
                    new_key,
                    transfer_data_status,
                    upload_status,
                    code,
                    area,
                    rate_code,
                    cummulative_multiplier,
                    oebr_number,
                    sequence,
                    Reading_TimeStamp,
                    Print_TimeStamp,
                    timestamp,
                    bill_number,
                    GenerationSystem,
                    BenHost,
                    GRAM,
                    ICERA,
                    PowerArtReduction,
                    TransmissionDelivery,
                    TransmissionDelivery2,
                    System_Loss,
                    Gen_Trans_Rev,
                    DistributionNetwork,
                    DistributionNetwork2,
                    DistributionNetwork3,
                    RetailElectricService,
                    RetailElectricService2,
                    Metering_cust,
                    Metering_cust_2,
                    Metering_kwh,
                    loan,
                    RFSC,
                    Distribution_Rev,
                    MissionaryElectrification,
                    EnvironmentCharge,
                    NPC_StrandedDebts,
                    NPC_StrandedCost,
                    DUsCost,
                    DCDistributionCharge,
                    DCDemandCharge,
                    TCTransSystemCharge,
                    SCSupplySysCharge,
                    equal_tax,
                    CrossSubsidyRemoval,
                    Universal_Charges,
                    Lifeline_Charge,
                    InterclassCrossSubsidy,
                    SeniorCitizenSubsidy,
                    ICCS_Adjustment,
                    ICCrossSubsidyCharge,
                    FitAllCharge,
                    PPD_Adjustment,
                    GenerationCostAdjustment,
                    PowerCostAdjustment,
                    Other_Rev,
                    GenerationVat,
                    TransmissionVat,
                    SystemLossVat,
                    DistributionVat,
                    OtherVat,
                    Government_Rev,
                    CurrentBill,
                    amountdue,
                    overdue,
                    franchise_tax,
                    coreloss,
                    surcharge,
                    rentalfee,
                    delivered,
                    check_previous,
                    ispn,
                    SCD,
                    pnrecvdte,
                    pnrecvby,
                    recvby,
                    hash,
                    isreset,
                    isprntd,
                    meter_count,
                    delivery_id,
                    delivery_remarks,
                    delivery_comment,
                    reading_signature,
                    real_property_tax,
                    cc_rstc_refund,
                    cc_rstc_refund2,
                    moa,
                    eda,
                    ModifiedDate,
                    ModifiedBy
            };

            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("reading_logs",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
    public static boolean doReading(   String Client,
                                       String C_ID,
                                       String AccountNumber,
                                       String job_id,
                                       String name,
                                       String route,
                                       String itinerary,
                                       String meter_number,
                                       String serial,
                                       String previous_reading,
                                       String present_Reading,
                                       String previous_consumption,
                                       String Present_Consumption,
                                       String previous_reading_date,
                                       String present_reading_date,
                                       String duedate,
                                       String discondate,
                                       String Reading_Date,
                                       String BillYear,
                                       String BillMonth,
                                       String BillDate,
                                       String month,
                                       String day,
                                       String year,
                                       String Demand,
                                       String reactive,
                                       String powerfactor,
                                       String kw_cummulative,
                                       String reading1,
                                       String reading2,
                                       String reading3,
                                       String reading4,
                                       String reading5,
                                       String reading6,
                                       String reading7,
                                       String reading8,
                                       String reading9,
                                       String reading10,
                                       String iwpowerfactor,
                                       String demand_consumption,
                                       String reactive_consumption,
                                       String Averange_Consumption,
                                       String Average_Reading,
                                       String multiplier,
                                       String Meter_Box,
                                       String Demand_Reset,
                                       String Test_Block,
                                       String Remarks_Code,
                                       String remarks_abbreviation,
                                       String Remarks,
                                       String Comment,
                                       String Reader_ID,
                                       String meter_reader,
                                       String Reading_Attempt,
                                       String Print_Attempt,
                                       String force_reading,
                                       String r_latitude,
                                       String r_longitude,
                                       String printlatitude,
                                       String printlongitude,
                                       String improbable_reading,
                                       String negative_reading,
                                       String change_reading,
                                       String cg_vat_zero_tag,
                                       String reading_remarks,
                                       String old_key,
                                       String new_key,
                                       String transfer_data_status,
                                       String upload_status,
                                       String code,
                                       String area,
                                       String rate_code,
                                       String cummulative_multiplier,
                                       String oebr_number,
                                       String sequence,
                                       String Reading_TimeStamp,
                                       String Print_TimeStamp,
                                       String timestamp,
                                       String bill_number,
                                       String GenerationSystem,
                                       String BenHost,
                                       String GRAM,
                                       String ICERA,
                                       String PowerArtReduction,
                                       String TransmissionDelivery,
                                       String TransmissionDelivery2,
                                       String System_Loss,
                                       String Gen_Trans_Rev,
                                       String DistributionNetwork,
                                       String DistributionNetwork2,
                                       String DistributionNetwork3,
                                       String RetailElectricService,
                                       String RetailElectricService2,
                                       String Metering_cust,
                                        String Metering_cust_2,
                                        String Metering_kwh,
                                        String loan,
                                        String RFSC,
                                        String Distribution_Rev,
                                        String MissionaryElectrification,
                                        String EnvironmentCharge,
                                        String NPC_StrandedDebts,
                                        String NPC_StrandedCost,
                                        String DUsCost,
                                        String DCDistributionCharge,
                                        String DCDemandCharge,
                                        String TCTransSystemCharge,
                                        String SCSupplySysCharge,
                                        String equal_tax,
                                        String CrossSubsidyRemoval,
                                        String Universal_Charges,
                                        String Lifeline_Charge,
                                        String InterclassCrossSubsidy,
                                        String SeniorCitizenSubsidy,
                                        String ICCS_Adjustment,
                                        String ICCrossSubsidyCharge,
                                        String FitAllCharge,
                                        String PPD_Adjustment,
                                        String GenerationCostAdjustment,
                                        String PowerCostAdjustment,
                                        String Other_Rev,
                                        String GenerationVat,
                                        String TransmissionVat,
                                        String SystemLossVat,
                                        String DistributionVat,
                                        String OtherVat,
                                        String Government_Rev,
                                        String CurrentBill,
                                        String amountdue,
                                        String overdue,
                                        String franchise_tax,
                                        String coreloss,
                                        String surcharge,
                                        String rentalfee,
                                        String delivered,
                                        String check_previous,
                                        String ispn,
                                        String SCD,
                                        String pnrecvdte,
                                        String pnrecvby,
                                        String recvby,
                                        String hash,
                                        String isreset,
                                        String isprntd,
                                        String meter_count,
                                        String delivery_id,
                                        String delivery_remarks,
                                        String delivery_comment,
                                        String reading_signature,
                                        String real_property_tax,
                                        String cc_rstc_refund,
                                        String cc_rstc_refund2,
                                        String moa,
                                        String eda,
                                        String ModifiedDate,
                                        String ModifiedBy
    ){
        try{
            boolean result = false;
            boolean result_logs = false;
            Liquid.LiquidColumns = Liquid.ReadingColumns;
            Liquid.LiquidValues = new String[]{
                    Client,
                    C_ID,
                    AccountNumber,
                    job_id,
                    name,
                    route,
                    itinerary,
                    meter_number,
                    serial,
                    previous_reading,
                    present_Reading,
                    previous_consumption,
                    Present_Consumption,
                    previous_reading_date,
                    present_reading_date,
                    duedate,
                    discondate,
                    Reading_Date,
                    BillYear,
                    BillMonth,
                    BillDate,
                    month,
                    day,
                    year,
                    Demand,
                    reactive,
                    powerfactor,
                    kw_cummulative,
                    reading1,
                    reading2,
                    reading3,
                    reading4,
                    reading5,
                    reading6,
                    reading7,
                    reading8,
                    reading9,
                    reading10,
                    iwpowerfactor,
                    demand_consumption,
                    reactive_consumption,
                    Averange_Consumption,
                    Average_Reading,
                    multiplier,
                    Meter_Box,
                    Demand_Reset,
                    Test_Block,
                    Remarks_Code,
                    remarks_abbreviation,
                    Remarks,
                    Comment,
                    Reader_ID,
                    meter_reader,
                    Reading_Attempt,
                    Print_Attempt,
                    force_reading,
                    r_latitude,
                    r_longitude,
                    printlatitude,
                    printlongitude,
                    improbable_reading,
                    negative_reading,
                    change_reading,
                    cg_vat_zero_tag,
                    reading_remarks,
                    old_key,
                    new_key,
                    transfer_data_status,
                    upload_status,
                    code,
                    area,
                    rate_code,
                    cummulative_multiplier,
                    oebr_number,
                    sequence,
                    Reading_TimeStamp,
                    Print_TimeStamp,
                    timestamp,
                    bill_number,
                    GenerationSystem,
                    BenHost,
                    GRAM,
                    ICERA,
                    PowerArtReduction,
                    TransmissionDelivery,
                    TransmissionDelivery2,
                    System_Loss,
                    Gen_Trans_Rev,
                    DistributionNetwork,
                    DistributionNetwork2,
                    DistributionNetwork3,
                    RetailElectricService,
                    RetailElectricService2,
                    Metering_cust,
                    Metering_cust_2,
                    Metering_kwh,
                    loan,
                    RFSC,
                    Distribution_Rev,
                    MissionaryElectrification,
                    EnvironmentCharge,
                    NPC_StrandedDebts,
                    NPC_StrandedCost,
                    DUsCost,
                    DCDistributionCharge,
                    DCDemandCharge,
                    TCTransSystemCharge,
                    SCSupplySysCharge,
                    equal_tax,
                    CrossSubsidyRemoval,
                    Universal_Charges,
                    Lifeline_Charge,
                    InterclassCrossSubsidy,
                    SeniorCitizenSubsidy,
                    ICCS_Adjustment,
                    ICCrossSubsidyCharge,
                    FitAllCharge,
                    PPD_Adjustment,
                    GenerationCostAdjustment,
                    PowerCostAdjustment,
                    Other_Rev,
                    GenerationVat,
                    TransmissionVat,
                    SystemLossVat,
                    DistributionVat,
                    OtherVat,
                    Government_Rev,
                    CurrentBill,
                    amountdue,
                    overdue,
                    franchise_tax,
                    coreloss,
                    surcharge,
                    rentalfee,
                    delivered,
                    check_previous,
                    ispn,
                    SCD,
                    pnrecvdte,
                    pnrecvby,
                    recvby,
                    hash,
                    isreset,
                    isprntd,
                    meter_count,
                    delivery_id,
                    delivery_remarks,
                    delivery_comment,
                    reading_signature,
                    real_property_tax,
                    cc_rstc_refund,
                    cc_rstc_refund2,
                    moa,
                    eda,
                    ModifiedDate,
                    ModifiedBy
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("reading",Liquid.LiquidColumns,Liquid.LiquidValues);

            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitPicture( String client,
                                           String AccountNumber,
                                           String type,
                                           String picture,
                                           String timestamp,
                                           String modifieddate,
                                           String Reader_ID,
                                           String modifiedby,
                                           String reading_date
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.ColumnPicture;
            Liquid.LiquidValues = new String[]{
                    client,
                    AccountNumber,
                    type,
                    picture,
                    timestamp,
                    modifieddate,
                    Reader_ID,
                    modifiedby,
                    reading_date,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("meter_reading_pictures",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
}
