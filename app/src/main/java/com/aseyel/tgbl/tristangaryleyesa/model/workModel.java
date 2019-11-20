package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Romeo on 2018-01-05.
 */

public class workModel {
    private static final String TAG = "GetLocalJobOrder";
    ArrayList<HashMap<String, String>> EmptyData;
    public static Cursor result;
    public static Cursor GetLocalJobOrder(String job_id) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "id," +
                "title, " +
                "details, " +
                "sysentrydate " +
                "FROM joborder " +
                "WHERE id like '%" + job_id + "%' OR " +
                "details like '%" + job_id + "%'"+
                "ORDER BY sysentrydate DESC ");


    }

    public static Cursor GetAuditDownload(String job_id,String AccountNumber) {
        String where = "";
        if(!AccountNumber.equals("")){
            where = "AND AccountNumber like '%" + AccountNumber + "%' ";
        }
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "JobOrderId," + //0
                "AccountNumber,"+ //1
                "JobOrderTitle, " + //2
                "JobOrderDetails, " + //3
                "sysentrydate, " + //4
                "Latitude, " + //5
                "Longitude, " + //6
                "JobOrderDate " + //7
                "FROM audit_download " +
                "WHERE JobOrderId like '%" + job_id + "%' " +
                where +
                "ORDER BY sysentrydate DESC ");


    }


    public static Cursor GetDisconnectionSearchDownload(String job_id,String Search) {
        String where = "";
        if(!Search.equals("")){
            where = "AND c.accountnumber like '%" + Search + "%' OR  c.meternumber like '%"+Search+"%' OR c.account_name like '%"+Search+"%'";
        }
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "c.Client,"+ //0
                "c.Code,"+ //1
                "c.job_id,"+ //2
                "c.id,"+ //3
                "c.lastname,"+ //4
                "c.firstname,"+ //5
                "c.middlename,"+ //6
                "c.type,"+ //7
                "c.establishment,"+ //8
                "c.accountnumber,"+ //9
                "c.account_name,"+ //10
                "c.tin,"+ //11
                "c.BA,"+ //12
                "c.route,"+ //13
                "c.route_itinerary,"+ //14
                "c.itinerary,"+ //15
                "c.status,"+ //16
                "c.status_description,"+ //17
                "c.country,"+ //18
                "c.region,"+ //19
                "c.province,"+ //20
                "c.city,"+ //21
                "c.brgy,"+ //22
                "c.country_label,"+ //23
                "c.region_label,"+ //24
                "c.province_label,"+ //25
                "c.municipality_city_label,"+ //26
                "c.loc_barangay_label,"+ //27
                "c.street,"+ //28
                "c.complete_address,"+ //29
                "c.longitude,"+ //30
                "c.latitude,"+ //31
                "c.sequence,"+ //32
                "c.multiplier,"+ //33
                "c.meter_type,"+ //34
                "c.meterbrand,"+ //35
                "c.meternumber,"+ //36
                "c.serial,"+ //37
                "c.meter_count,"+ //38
                "c.previousreading,"+ //39
                "c.previousconsumption,"+ //40
                "c.previous_reading_date,"+ //41
                "c.disconnection_date,"+ //42
                "c.amountdue,"+ //43
                "c.duedate,"+ //44
                "c.cycleyear,"+ //45
                "c.cyclemonth,"+ //46
                "c.or_number,"+ //47
                "c.client_remarks,"+ //48
                "c.arrears,"+ //49
                "c.total_amount_due,"+ //50
                "c.last_payment_date,"+ //51
                "c.sysentrydate,"+ //52
                "c.modifieddate,"+ //53
                "c.modifiedby "+ //54
                "FROM customer_disconnection_downloads c " +
                "WHERE c.job_id like '%" + job_id + "%' " +
                where +
                "ORDER BY sysentrydate DESC ");


    }
    public static Cursor GetDisconnectionDownload(String job_id,String AccountNumber) {
        String where = "";
        if(!AccountNumber.equals("")){
            where = "AND c.accountnumber like '%" + AccountNumber + "%' ";
        }
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "c.Client,"+ //0
                "c.Code,"+ //1
                "c.job_id,"+ //2
                "c.id,"+ //3
                "c.lastname,"+ //4
                "c.firstname,"+ //5
                "c.middlename,"+ //6
                "c.type,"+ //7
                "c.establishment,"+ //8
                "c.accountnumber,"+ //9
                "c.account_name,"+ //10
                "c.tin,"+ //11
                "c.BA,"+ //12
                "c.route,"+ //13
                "c.route_itinerary,"+ //14
                "c.itinerary,"+ //15
                "c.status,"+ //16
                "c.status_description,"+ //17
                "c.country,"+ //18
                "c.region,"+ //19
                "c.province,"+ //20
                "c.city,"+ //21
                "c.brgy,"+ //22
                "c.country_label,"+ //23
                "c.region_label,"+ //24
                "c.province_label,"+ //25
                "c.municipality_city_label,"+ //26
                "c.loc_barangay_label,"+ //27
                "c.street,"+ //28
                "c.complete_address,"+ //29
                "c.longitude,"+ //30
                "c.latitude,"+ //31
                "c.sequence,"+ //32
                "c.multiplier,"+ //33
                "c.meter_type,"+ //34
                "c.meterbrand,"+ //35
                "c.meternumber,"+ //36
                "c.serial,"+ //37
                "c.meter_count,"+ //38
                "c.previousreading,"+ //39
                "c.previousconsumption,"+ //40
                "c.previous_reading_date,"+ //41
                "c.disconnection_date,"+ //42
                "c.amountdue,"+ //43
                "c.duedate,"+ //44
                "c.cycleyear,"+ //45
                "c.cyclemonth,"+ //46
                "c.or_number,"+ //47
                "c.client_remarks,"+ //48
                "c.arrears,"+ //49
                "c.total_amount_due,"+ //50
                "c.last_payment_date,"+ //51
                "c.sysentrydate,"+ //52
                "c.modifieddate,"+ //53
                "c.modifiedby "+ //54
                "FROM customer_disconnection_downloads c " +
                "WHERE c.job_id like '%" + job_id + "%' " +
                where +
                "ORDER BY sysentrydate DESC ");


    }



    public static Cursor GetLastJobID() {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "COUNT(id) " +
                "FROM joborder " +
                "ORDER BY date DESC ");
    }
    public static Cursor GetLastAccountNumber() {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "COUNT(AccountNumber) " +
                "FROM audit_download "
        );
    }


    public static Cursor GetReadingRemarks(String Remarks){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "remarks_id,"+ //0
                        "remarks_description, "+ //1
                        "remarks_abbreviation "+ //2
                        "FROM ref_remarks " +
                        "WHERE remarks_id like '%"+Remarks+"%' OR " +
                        "remarks_description like '%"+Remarks+"%' " +
                        "ORDER BY CAST(remarks_id as int)"
        );
    }

    public static Cursor GetDisconnectionRemarks(){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "remarks_id,"+ //0
                        "remarks_description "+ //1
                        "FROM ref_disconnection_remarks ORDER BY CAST(remarks_id as int)"
        );
    }




    public static Cursor GetDisconnectioAccomplishement(String SearchBy,String job_id,String AccountNumber){
        String where = "";
        switch(SearchBy){
            case "Disco":
                where = "FROM  disconnection r "+
                        "LEFT JOIN  customer_disconnection_downloads c "+
                        "ON r.accountnumber = c.accountnumber AND c.disconnection_date = r.disconnection_date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.accountnumber like '%"+AccountNumber+"%' AND " +
                        "r.isdisconnected = '1' AND "+
                        "r.disconnection_date IS NOT NULL " +
                        "ORDER BY c.sequence ASC";
                break;
            case "Undone":
                where = "FROM  customer_disconnection_downloads  c "+
                        "LEFT JOIN  disconnection r "+
                        "ON r.accountnumber = c.accountnumber AND c.disconnection_date = r.disconnection_date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.accountnumber like '%"+AccountNumber+"%' AND " +
                        "r.disconnection_date IS NULL " +
                        "ORDER BY c.sequence ASC";
                break;
            case "Coll":
                where = "FROM  disconnection r "+
                        "LEFT JOIN  customer_disconnection_downloads c "+
                        "ON r.accountnumber = c.accountnumber AND c.disconnection_date = r.disconnection_date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.accountnumber like '%"+AccountNumber+"%' AND " +
                        "r.ispayed = '1' AND "+
                        "r.disconnection_date IS NOT NULL " +
                        "ORDER BY c.sequence ASC";
                break;

        }
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "c.Client,"+ //0
                        "c.Code,"+ //1
                        "c.job_id,"+ //2
                        "c.id,"+ //3
                        "c.lastname,"+ //4
                        "c.firstname,"+ //5
                        "c.middlename,"+ //6
                        "c.type,"+ //7
                        "c.establishment,"+ //8
                        "c.accountnumber,"+ //9
                        "c.account_name,"+ //10
                        "c.tin,"+ //11
                        "c.BA,"+ //12
                        "c.route,"+ //13
                        "c.route_itinerary,"+ //14
                        "c.itinerary,"+ //15
                        "c.status,"+ //16
                        "c.status_description,"+ //17
                        "c.country,"+ //18
                        "c.region,"+ //19
                        "c.province,"+ //20
                        "c.city,"+ //21
                        "c.brgy,"+ //22
                        "c.country_label,"+ //23
                        "c.region_label,"+ //24
                        "c.province_label,"+ //25
                        "c.municipality_city_label,"+ //26
                        "c.loc_barangay_label,"+ //27
                        "c.street,"+ //28
                        "c.complete_address,"+ //29
                        "c.longitude,"+ //30
                        "c.latitude,"+ //31
                        "c.sequence,"+ //32
                        "c.multiplier,"+ //33
                        "c.meter_type,"+ //34
                        "c.meterbrand,"+ //35
                        "c.meternumber,"+ //36
                        "c.serial,"+ //37
                        "c.meter_count,"+ //38
                        "c.previousreading,"+ //39
                        "c.previousconsumption,"+ //40
                        "c.previous_reading_date,"+ //41
                        "c.disconnection_date,"+ //42
                        "c.amountdue,"+ //43
                        "c.duedate,"+ //44
                        "c.cycleyear,"+ //45
                        "c.cyclemonth,"+ //46
                        "c.or_number,"+ //47
                        "c.client_remarks,"+ //48
                        "c.arrears,"+ //49
                        "c.total_amount_due,"+ //50
                        "c.last_payment_date,"+ //51
                        "c.sysentrydate,"+ //52
                        "c.modifieddate,"+ //53
                        "c.modifiedby "+ //54
                        where
        );

    }
    public static Cursor GetReadAndBillDataReaded(String ToGet,String SearchBy,String job_id,String AccountNumber){
        String where = "";
        String orderby = "";
        //i add this paramater/switch just because of the needs of more powerr project
        switch(Liquid.Client){
//            case "more_power":
//                orderby = " ORDER BY c.Complete_Address,CAST(c.CED_Sequence as INTEGER) ASC";
//                break;
            default:
                orderby = " ORDER BY CAST(c.CED_Sequence as INTEGER) ASC";
        }
        //where depending on the needed parameter
        switch(SearchBy){
            case "All":
                where = "FROM  customer_reading_downloads  c "+
                        "LEFT JOIN  reading r "+
                        "ON r.AccountNumber = c.C_AccountNumber AND c.reading_date = r.Reading_Date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.C_AccountNumber like '%"+AccountNumber+"%' ";
                break;
            case "Read":
                where = "FROM  reading r "+
                        "LEFT JOIN  customer_reading_downloads c "+
                        "ON r.AccountNumber = c.C_AccountNumber AND c.reading_date = r.Reading_Date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.C_AccountNumber like '%"+AccountNumber+"%' AND " +
                        "r.present_Reading IS NOT NULL ";
                break;
            case "Unread":
                where = "FROM  customer_reading_downloads  c "+
                        "LEFT JOIN  reading r "+
                        "ON r.AccountNumber = c.C_AccountNumber AND c.reading_date = r.Reading_Date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.C_AccountNumber like '%"+AccountNumber+"%' AND " +
                        "r.present_Reading IS NULL ";
                break;
            case "Print":
                where = "FROM  reading r "+
                        "LEFT JOIN  customer_reading_downloads c "+
                        "ON r.AccountNumber = c.C_AccountNumber AND c.reading_date = r.Reading_Date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.C_AccountNumber like '%"+AccountNumber+"%' AND " +
                        "CAST(r.Print_Attempt AS INT) > '0' AND " +
                        "r.present_Reading IS NOT NULL  ";
                break;
            case "Unprint":
                where = "FROM customer_reading_downloads c  "+
                        "LEFT JOIN reading  r "+
                        "ON r.AccountNumber = c.C_AccountNumber AND c.reading_date = r.Reading_Date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "c.C_AccountNumber like '%"+AccountNumber+"%' AND " +
                        "r.Print_Attempt = '0' AND " +
                        "r.present_Reading IS NOT NULL ";
                break;
        }
        //this is the query if the app only need the count of rows.
        if(ToGet.equals("Count")){
            return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                    "SELECT "+
                            "COUNT(c.C_Client) as total "+ //0
                            where
            );
        }

        // this will return the data needed
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "c.C_Client,"+ //0
                        "c.Code,"+ //1
                        "c.job_id,"+//2
                        "c.C_ID,"+//3
                        "c.C_Lastname,"+//4
                        "c.C_Firstname,"+//5
                        "c.C_Middlename,"+//6
                        "c.C_Type,"+//7
                        "c.classification,"+//8
                        "c.C_Status,"+//9
                        "c.C_Establishment,"+//10
                        "c.route,"+//11
                        "c.route_itinerary,"+//12
                        "c.itinerary,"+//13
                        "c.status_description,"+//14
                        "c.C_Approval_Status,"+//15
                        "c.R_Description,"+//16
                        "c.account_name,"+//17
                        "c.C_Country,"+//18
                        "c.C_Region,"+//19
                        "c.C_Province,"+//20
                        "c.C_City,"+//21
                        "c.C_Brgy,"+//22
                        "c.Country_Label,"+//23
                        "c.Region_Label,"+//24
                        "c.Province_Label,"+//25
                        "c.Municipality_City_Label,"+//26
                        "c.Loc_Barangay_Label,"+//27
                        "c.C_Street,"+//28
                        "c.Complete_Address,"+//29
                        "c.C_AccountNumber,"+//30
                        "c.C_Meter_Type,"+//31
                        "c.bill_route,"+//32
                        "c.bill_itinerary,"+//33
                        "c.bill_sequence,"+//34
                        "c.CED_Sequence,"+//35
                        "c.CED_MeterBrand,"+//36
                        "c.CED_MeterNumber,"+//37
                        "c.pipe_size,"+//38
                        "c.special_meter_tag,"+//39
                        "c.multiplier,"+//40
                        "c.cg_vat_zero_tag,"+//41
                        "c.meter_count,"+//42
                        "c.arrears,"+//43
                        "c.coreloss,"+//44
                        "c.Average_Reading,"+//45
                        "c.Averange_Consumption,"+//46
                        "c.PreviousReading,"+//47
                        "c.PreviousReactive,"+//48
                        "c.PreviousDemand,"+//49
                        "c.PreviousPowerFactor,"+//50
                        "c.PreviousKWCummulative,"+//51
                        "c.PreviousConsumption,"+//52
                        "c.Previous_Reading_Date,"+//53
                        "c.reading_date,"+//54
                        "c.rentalfee,"+//55
                        "c.senior_citizen_tag,"+//56
                        "c.months_unpaid_bill,"+//57
                        "c.change_meter_reading,"+//58
                        "c.c_group,"+//59
                        "c.book,"+//60
                        "c.ispn,"+//61
                        "c.interest,"+//62
                        "c.serial,"+//63
                        "c.duedate,"+//64
                        "c.bill_number,"+//65
                        "c.BillMonth,"+//66
                        "c.BillDate,"+//67
                        "c.year,"+//68
                        "c.Average_amount,"+//69
                        "c.eda,"+//70
                        "c.C_SysEntryDate,"+//71
                        "c.C_ModifiedDate,"+//72
                        "c.C_ModifiedBy,"+//73
                        "c.over90days," + //74
                        "c.over60days," + //75
                        "c.advance_payment," +  //76
                        "c.scap_bill," + //77
                        "c.scap_paid," + //78
                        "c.adjust_amount," + //79
                        "c.adjust_amount1," + //80
                        "c.add_deduct," + //81
                        "c.discount," + //82
                        "c.total_initial, "+ //83
                        "ifnull(r.Present_Consumption,'NO READING') as Present_Consumption, "+ //84
                        "ifnull(r.amountdue,'0.00') as amountdue, "+ //85
                        "ifnull(r.Print_TimeStamp,'') as Print_TimeStamp "+ //86
                        where + orderby +" LIMIT "+(Liquid.RecyclerItemLimit+Liquid.UpdateRecyclerItemLimit)
        );

    }

    public static Cursor GetReadAndBillJobOrderDetails(String job_id,String AccountNumber) {

        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "C_Client,"+ //0
                        "Code,"+ //1
                        "job_id,"+//2
                        "C_ID,"+//3
                        "C_Lastname,"+//4
                        "C_Firstname,"+//5
                        "C_Middlename,"+//6
                        "C_Type,"+//7
                        "classification,"+//8
                        "C_Status,"+//9
                        "C_Establishment,"+//10
                        "route,"+//11
                        "route_itinerary,"+//12
                        "itinerary,"+//13
                        "status_description,"+//14
                        "C_Approval_Status,"+//15
                        "R_Description,"+//16
                        "account_name,"+//17
                        "C_Country,"+//18
                        "C_Region,"+//19
                        "C_Province,"+//20
                        "C_City,"+//21
                        "C_Brgy,"+//22
                        "Country_Label,"+//23
                        "Region_Label,"+//24
                        "Province_Label,"+//25
                        "Municipality_City_Label,"+//26
                        "Loc_Barangay_Label,"+//27
                        "C_Street,"+//28
                        "Complete_Address,"+//29
                        "C_AccountNumber,"+//30
                        "C_Meter_Type,"+//31
                        "bill_route,"+//32
                        "bill_itinerary,"+//33
                        "bill_sequence,"+//34
                        "CED_Sequence,"+//35
                        "CED_MeterBrand,"+//36
                        "CED_MeterNumber,"+//37
                        "pipe_size,"+//38
                        "special_meter_tag,"+//39
                        "multiplier,"+//40
                        "cg_vat_zero_tag,"+//41
                        "meter_count,"+//42
                        "arrears,"+//43
                        "coreloss,"+//44
                        "Average_Reading,"+//45
                        "Averange_Consumption,"+//46
                        "PreviousReading,"+//47
                        "PreviousReactive,"+//48
                        "PreviousDemand,"+//49
                        "PreviousPowerFactor,"+//50
                        "PreviousKWCummulative,"+//51
                        "PreviousConsumption,"+//52
                        "Previous_Reading_Date,"+//53
                        "reading_date,"+//54
                        "rentalfee,"+//55
                        "senior_citizen_tag,"+//56
                        "months_unpaid_bill,"+//57
                        "change_meter_reading,"+//58
                        "c_group,"+//59
                        "book,"+//60
                        "ispn,"+//61
                        "interest,"+//62
                        "serial,"+//63
                        "duedate,"+//64
                        "bill_number,"+//65
                        "BillMonth,"+//66
                        "BillDate,"+//67
                        "year,"+//68
                        "Average_amount,"+//69
                        "eda,"+//70
                        "C_SysEntryDate,"+//71
                        "C_ModifiedDate,"+//72
                        "C_ModifiedBy,"+//73
                        "over90days," + //74
                        "over60days," + //75
                        "advance_payment," +  //76
                        "scap_bill," + //77
                        "scap_paid," + //78
                        "adjust_amount," + //79
                        "adjust_amount1," + //80
                        "add_deduct," + //81
                        "discount," + //82
                        "total_initial, "+ //83
                        "rowid, "+//84
                        "Transformer, "+//85
                        "inclusion, "+//86
                        "load, "+//87
                        "OCDate1 "+//88
                        "FROM customer_reading_downloads "+
                        "WHERE job_id like '%"+job_id+"%' AND "+
                        "C_AccountNumber like '%"+AccountNumber+"%' " +
                        "ORDER BY cast(CED_Sequence as int) "
        );
    }

    public static Cursor GetReadAndBillSelectedJobOrderDetails(String job_id,String AccountNumber) {

        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "C_Client,"+ //0
                        "Code,"+ //1
                        "job_id,"+//2
                        "C_ID,"+//3
                        "C_Lastname,"+//4
                        "C_Firstname,"+//5
                        "C_Middlename,"+//6
                        "C_Type,"+//7
                        "classification,"+//8
                        "C_Status,"+//9
                        "C_Establishment,"+//10
                        "route,"+//11
                        "route_itinerary,"+//12
                        "itinerary,"+//13
                        "status_description,"+//14
                        "C_Approval_Status,"+//15
                        "R_Description,"+//16
                        "account_name,"+//17
                        "C_Country,"+//18
                        "C_Region,"+//19
                        "C_Province,"+//20
                        "C_City,"+//21
                        "C_Brgy,"+//22
                        "Country_Label,"+//23
                        "Region_Label,"+//24
                        "Province_Label,"+//25
                        "Municipality_City_Label,"+//26
                        "Loc_Barangay_Label,"+//27
                        "C_Street,"+//28
                        "Complete_Address,"+//29
                        "C_AccountNumber,"+//30
                        "C_Meter_Type,"+//31
                        "bill_route,"+//32
                        "bill_itinerary,"+//33
                        "bill_sequence,"+//34
                        "CED_Sequence,"+//35
                        "CED_MeterBrand,"+//36
                        "CED_MeterNumber,"+//37
                        "pipe_size,"+//38
                        "special_meter_tag,"+//39
                        "multiplier,"+//40
                        "cg_vat_zero_tag,"+//41
                        "meter_count,"+//42
                        "arrears,"+//43
                        "coreloss,"+//44
                        "Average_Reading,"+//45
                        "Averange_Consumption,"+//46
                        "PreviousReading,"+//47
                        "PreviousReactive,"+//48
                        "PreviousDemand,"+//49
                        "PreviousPowerFactor,"+//50
                        "PreviousKWCummulative,"+//51
                        "PreviousConsumption,"+//52
                        "Previous_Reading_Date,"+//53
                        "reading_date,"+//54
                        "rentalfee,"+//55
                        "senior_citizen_tag,"+//56
                        "months_unpaid_bill,"+//57
                        "change_meter_reading,"+//58
                        "c_group,"+//59
                        "book,"+//60
                        "ispn,"+//61
                        "interest,"+//62
                        "serial,"+//63
                        "duedate,"+//64
                        "bill_number,"+//65
                        "BillMonth,"+//66
                        "BillDate,"+//67
                        "year,"+//68
                        "Average_amount,"+//69
                        "eda,"+//70
                        "C_SysEntryDate,"+//71
                        "C_ModifiedDate,"+//72
                        "C_ModifiedBy,"+//73
                        "over90days," + //74
                        "over60days," + //75
                        "advance_payment," +  //76
                        "scap_bill," + //77
                        "scap_paid," + //78
                        "adjust_amount," + //79
                        "adjust_amount1," + //80
                        "add_deduct," + //81
                        "discount," + //82
                        "total_initial, "+ //83
                        "rowid, "+//84
                        "Transformer, "+//85
                        "inclusion, "+//86
                        "load, "+//87
                        "OCDate1, "+//88
                        "CMPresentReadingKWH, "+//89
                        "over30days, "+//90
                        "CMPreviousReadingKWH, "+//91
                        "CareOf " + //92
                        "FROM customer_reading_downloads "+
                        "WHERE job_id = '"+job_id+"' AND "+
                        "C_AccountNumber = '"+AccountNumber+"' " +
                        "ORDER BY cast(CED_Sequence as int) "
        );
    }

    public static Cursor GetByAMNReadAndBillData(String job_id,String Search) {

        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT "+
                        "c.C_Client,"+ //0
                        "c.Code,"+ //1
                        "c.job_id,"+//2
                        "c.C_ID,"+//3
                        "c.C_Lastname,"+//4
                        "c.C_Firstname,"+//5
                        "c.C_Middlename,"+//6
                        "c.C_Type,"+//7
                        "c.classification,"+//8
                        "c.C_Status,"+//9
                        "c.C_Establishment,"+//10
                        "c.route,"+//11
                        "c.route_itinerary,"+//12
                        "c.itinerary,"+//13
                        "c.status_description,"+//14
                        "c.C_Approval_Status,"+//15
                        "c.R_Description,"+//16
                        "c.account_name,"+//17
                        "c.C_Country,"+//18
                        "c.C_Region,"+//19
                        "c.C_Province,"+//20
                        "c.C_City,"+//21
                        "c.C_Brgy,"+//22
                        "c.Country_Label,"+//23
                        "c.Region_Label,"+//24
                        "c.Province_Label,"+//25
                        "c.Municipality_City_Label,"+//26
                        "c.Loc_Barangay_Label,"+//27
                        "c.C_Street,"+//28
                        "c.Complete_Address,"+//29
                        "c.C_AccountNumber,"+//30
                        "c.C_Meter_Type,"+//31
                        "c.bill_route,"+//32
                        "c.bill_itinerary,"+//33
                        "c.bill_sequence,"+//34
                        "c.CED_Sequence,"+//35
                        "c.CED_MeterBrand,"+//36
                        "c.CED_MeterNumber,"+//37
                        "c.pipe_size,"+//38
                        "c.special_meter_tag,"+//39
                        "c.multiplier,"+//40
                        "c.cg_vat_zero_tag,"+//41
                        "c.meter_count,"+//42
                        "c.arrears,"+//43
                        "c.coreloss,"+//44
                        "c.Average_Reading,"+//45
                        "c.Averange_Consumption,"+//46
                        "c.PreviousReading,"+//47
                        "c.PreviousReactive,"+//48
                        "c.PreviousDemand,"+//49
                        "c.PreviousPowerFactor,"+//50
                        "c.PreviousKWCummulative,"+//51
                        "c.PreviousConsumption,"+//52
                        "c.Previous_Reading_Date,"+//53
                        "c.reading_date,"+//54
                        "c.rentalfee,"+//55
                        "c.senior_citizen_tag,"+//56
                        "c.months_unpaid_bill,"+//57
                        "c.change_meter_reading,"+//58
                        "c.c_group,"+//59
                        "c.book,"+//60
                        "c.ispn,"+//61
                        "c.interest,"+//62
                        "c.serial,"+//63
                        "c.duedate,"+//64
                        "c.bill_number,"+//65
                        "c.BillMonth,"+//66
                        "c.BillDate,"+//67
                        "c.year,"+//68
                        "c.Average_amount,"+//69
                        "c.eda,"+//70
                        "c.C_SysEntryDate,"+//71
                        "c.C_ModifiedDate,"+//72
                        "c.C_ModifiedBy,"+//73
                        "c.over90days," + //74
                        "c.over60days," + //75
                        "c.advance_payment," +  //76
                        "c.scap_bill," + //77
                        "c.scap_paid," + //78
                        "c.adjust_amount," + //79
                        "c.adjust_amount1," + //80
                        "c.add_deduct," + //81
                        "c.discount," + //82
                        "c.total_initial, "+ //83
                        "ifnull(r.Present_Consumption,'NO READING') as Present_Consumption, "+ //84
                        "ifnull(r.amountdue,'0.00') as amountdue "+
                        "FROM  customer_reading_downloads  c "+
                        "LEFT JOIN  reading r "+
                        "ON r.AccountNumber = c.C_AccountNumber AND c.reading_date = r.Reading_Date AND c.job_id = r.job_id " +
                        "WHERE c.job_id like '%"+job_id+"%' AND "+
                        "(c.C_AccountNumber like '%"+Search+"%' OR c.CED_MeterNumber like '%"+Search+"%' OR c.account_name like '%"+Search+"%' OR c.serial like '%"+Search+"%' OR c.Complete_Address like '%"+Search+"%') "+
                        "ORDER BY c.CED_Sequence ASC"


        );

    }

    public static Cursor GetCokeLocalJobOrderDetails(String job_id,String AccountNumber) {
        /*
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "period || '-' || Sales_Route as JobId, "+ //0
                "Name," + //1
                "Name_2, " + //2
                "Customer_No, " + //3
                "MKTSGM, " + //4
                "SEGM, " + //5
                "City, " + //6
                "Street, " + //7
                "sysentrydate, " + //8
                "Latitude, " + //9
                "Longitude " + //10
                "FROM customer " +
                "WHERE period || '-' || Sales_Route like '%" + job_id + "%' AND  Customer_No like '%" + AccountNumber + "%' " +
                "OR period || '-' || Sales_Route like '%" + job_id + "%' AND Name like '%" + AccountNumber + "%'" +
                "OR period || '-' || Sales_Route like '%" + job_id + "%' AND Name_2 like '%" + AccountNumber + "%' " +

                "ORDER BY Name DESC ");
            */

        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                        "SELECT "+
                        "c.period || '-' || c.Sales_Route as JobId,  "+ //1
                        "c.Name as Name, "+ //2
                        "c.Name_2 as Name_2, "+ //3
                        "c.Customer_No as Customer_No, "+ //4
                        "c.MKTSGM as MKTSGM, "+ //5
                        "c.SEGM as SEGM, "+ //6
                        "c.City as City, "+ //7
                        "c.Street as Street, "+ //8
                        "c.sysentrydate as sysentrydate, "+ //9
                        "c.Latitude as Latitude, "+ //10
                        "c.Longitude as Longitude, "+ //11
                        "s.status as status "+ //12
                        "FROM customer as c "+
                        "LEFT JOIN "+
                        "stores_status s "+
                        "ON "+
                        "c.period = s.period   AND "+
                        "c.Customer_No = s.customer_id "+
                        "WHERE c.period || '-' || c.Sales_Route like '%"+job_id +"%' AND  c.Customer_No like '%"+AccountNumber +"%' "+
                        "OR c.period || '-' || c.Sales_Route like '%"+job_id +"%' AND c.Name like '%"+AccountNumber +"%' "+
                        "OR c.period || '-' || c.Sales_Route like '%"+ job_id+"%' AND c.Name_2 like '%"+AccountNumber +"%' "+
                        "ORDER BY Name DESC ");

    }


    public static boolean doSubmitNewJobOrder(String JobOrderId,
                                              String client,
                                              String title,
                                              String details,
                                              String date
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.joborders;
            Liquid.LiquidValues = new String[] {
                    JobOrderId,
                    client,
                    details,
                    title,
                    date,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("joborder",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitJobOrderDetails(String JobOrderId,
                                                  String client,
                                                  String AccountNumber,
                                                  String title,
                                                  String details,
                                                  String Latitude,
                                                  String Longitude,
                                                  String date
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.audit_job_order;
            Liquid.LiquidValues = new String[] {
                    JobOrderId,
                    client,
                    AccountNumber,
                    title,
                    details,
                    Latitude,
                    Longitude,
                    date,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("audit_download",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
}
