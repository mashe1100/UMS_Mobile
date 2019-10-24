package com.aseyel.tgbl.tristangaryleyesa.data;

/**
 * Created by Tristan on 2018-01-17.
 */

public class LiquidReference {
    //Update 11/16/2018 status of picture and meter not in list
    public static String AlterStatusPicture = "ALTER TABLE  meter_reading_pictures ADD COLUMN transfer_status TEXT default 'Pending'";
    public static String AlterStatusMeterNotInList = "ALTER TABLE  meter_not_in_list ADD COLUMN transfer_status TEXT default 'Pending'";
    public static String AlterDemandMeterNotInList = "ALTER TABLE  meter_not_in_list ADD COLUMN demand TEXT NULL";
    public static String AlterAmpirCapacityMeterNotInList = "ALTER TABLE  meter_not_in_list ADD COLUMN ampirCapacity TEXT NULL";
    public static String AlterTypeMeterNotInList = "ALTER TABLE  meter_not_in_list ADD COLUMN type TEXT NULL";
    public static String AlterHouseLatitudeMeterNotInList = "ALTER TABLE  meter_not_in_list ADD COLUMN house_latitude TEXT NULL";
    public static String AlterHouseLongitudeMeterNotInList = "ALTER TABLE  meter_not_in_list ADD COLUMN house_longitude TEXT NULL";
    public static String AlterLatitudeCustomerReadingDownload = "ALTER TABLE  customer_reading_download ADD COLUMN latitude TEXT NULL";
    public static String AlterLongitudeCustomerReadingDownload = "ALTER TABLE  customer_reading_download ADD COLUMN longitude TEXT NULL";

    //SQLITE TABLE
    public static String DropCokeCustomerTable = "DROP TABLE IF EXISTS customer";
    public static String CokeCustomerTable = "CREATE TABLE IF NOT EXISTS  customer (INCLUDED text  NULL,EVO text  NULL,SERVICE_MODEL text  NULL,MKTSGM text  NULL,SEGM text  NULL,CU text  NULL,REGION text  NULL,SALES_LOC_CODE text  NULL,SUB_DEMAND_CODE text  NULL,WITH_SCL text  NULL,SCL text  NULL,WITH_VOLUME text  NULL,VOLUME text  NULL,LEGO text  NULL,Customer_No text  PRIMARY KEY NOT NULL,Customer_group text  NULL,Oprl_Mark_Type text  NULL,Transportzone text  NULL,DeliverPlant text  NULL,Region_Lookup text  NULL,Sales_Org text  NULL,Sales_Group text  NULL,Sales_Office text  NULL,Sales_Route text  NULL,Sales_Location text  NULL,Demand_area text  NULL,Sub_Demand_Area text  NULL,Name text  NULL,Name_2 text  NULL,City text  NULL,Postal_Codes text  NULL,Street text  NULL,House_No text  NULL,Street_4 text  NULL,Train_station text  NULL,Created_on text  NULL,Account_group text  NULL,Customer_class text  NULL,Business_Type text  NULL,Bus_Type_Ext text  NULL,Cus_Subtrd_Chn text  NULL,Suppres_Reason text  NULL,Cust_Plan_Lev text  NULL,Sales_district text  NULL,Master_Route text  NULL,Driver text  NULL,BP__Customer_Partner text  NULL,PY__Customer_Partner text  NULL,ZR__Personel_Partner text  NULL,ZW__Customer_Partner text  NULL,ZG__Personel_Partner text  NULL,Longitude text  NULL,Latitude text  NULL,Bus_Comp_Type text  NULL,DPWI text  NULL,Account text  NULL,address text  NULL,new_address text  NULL,province text  NULL,mt_region text  NULL,Address_1 text  NULL,address_2 text  NULL,Remarks text  NULL,mt_city text  NULL,period TEXT  NULL,sysentrydate TEXT NULL,modifieddate TEXT NULL,modifiedby TEXT NULL)";
    public static String DropJobOrderTable = "DROP TABLE IF EXISTS joborder";
    public static String JobOrderTable = "CREATE TABLE IF NOT EXISTS  joborder (id text  PRIMARY KEY,client text  NULL,details text  NULL,title text  NULL,date text  NULL,sysentrydate TEXT NULL,modifieddate TEXT NULL,modifiedby TEXT NULL)";
    public static String DropProductList = "DROP TABLE IF EXISTS products";
    public static String ProductList = "CREATE TABLE IF NOT EXISTS  [products] ([category] text  NULL,[productCode] text  NOT NULL,[productName] text NOT NULL,[articleno] TEXT  NULL,[cokeonematerialno] TEXT  NULL,[articlename] TEXT  NULL,[itembarcode] TEXT  NULL,[casebarcode] TEXT  NULL,[productType] text  NULL,[size] text  NULL,[uom] text  NULL,[productCategory] TEXT  NULL,[typeDescription] TEXT  NULL)";
    public static String DropSoviProductList = "DROP TABLE IF EXISTS sovi_product";
    public static String SoviProductList = "CREATE TABLE IF NOT EXISTS  [sovi_product] ([id] INTEGER NOT NULL PRIMARY KEY,[description] TEXT  NULL,[product] TEXT  NULL,[type] TEXT  NULL)";
    public static String DropTrackingAvalability = "DROP TABLE IF EXISTS availability";
    public static String TrackingAvalability = "CREATE TABLE IF NOT EXISTS  availability (customer_id text, prodcode text, twobottles text, onebottle TEXT, visible TEXT, cold TEXT, comment text, retprice text,  picture TEXT, period TEXT,sysentrydate text,modifieddate text, modifiedby text, PRIMARY KEY (customer_id, prodcode, period, modifiedby))";
    public static String DropTrackingSovi = "DROP TABLE IF EXISTS mt_sovi";
    public static String TrackingSovi = "CREATE TABLE IF NOT EXISTS  mt_sovi (customer_id TEXT, productname TEXT, description TEXT, sovi_type TEXT, location TEXT, numkof TEXT, numnonkof TEXT, cans TEXT, sspet TEXT, mspet TEXT, ssdoy TEXT, ssbrick TEXT, msbrick TEXT, sswedge TEXT, box TEXT, litro TEXT, pounch TEXT, picture TEXT, comment TEXT,  period TEXT,sysentrydate text,modifieddate text, modifiedby text, PRIMARY KEY (customer_id, productname, sovi_type, location, modifiedby, period))";
    public static String DropTrackingActivation  = "DROP TABLE IF EXISTS mt_activation";
    public static String TrackingActivation = "CREATE TABLE IF NOT EXISTS  mt_activation (customer_id text, name text, available text, nummaterial TEXT, categoriesUsedFor TEXT,  picture TEXT, period TEXT, sysentrydate text,modifieddate text, modifiedby text, PRIMARY KEY (customer_id, name, categoriesUsedFor, period, modifiedby))";
    public static String DropTrackingCDE = "DROP TABLE IF EXISTS mt_cde";
    public static String TrackingCDE = "CREATE TABLE IF NOT EXISTS  mt_cde (customer_id text,name text,category TEXT,question text,questionvalue TEXT,count text,picture TEXT,comment TEXT,period TEXT,sysentrydate text,modifieddate text,modifiedby text,PRIMARY KEY (customer_id, name, category, question, period, modifiedby))";
    public static String DropTrackingShelfPlanogram = "DROP TABLE IF EXISTS mt_shelf_planogram";
    public static String TrackingShelfPlanogram = "CREATE TABLE IF NOT EXISTS  mt_shelf_planogram (customer_id TEXT, name TEXT, value TEXT, picture TEXT, period TEXT, sysentrydate text,modifieddate text,modifiedby TEXT, PRIMARY KEY (customer_id, name, period, modifiedby))";
    public static String DropTrackingCoolerPlanogram = "DROP TABLE IF EXISTS mt_cde_planogram";
    public static String TrackingCoolerPlanogram = "CREATE TABLE IF NOT EXISTS  mt_cde_planogram (customer_id TEXT, name TEXT, value TEXT, picture TEXT, period TEXT, sysentrydate text,modifieddate text,modifiedby TEXT, PRIMARY KEY (customer_id, name, period, modifiedby))";
    public static String DropCoolerPlanogramList = "DROP TABLE IF EXISTS cde_planogram_compliance";
    public static String CoolerPlanogramList = "CREATE TABLE IF NOT EXISTS  cde_planogram_compliance (id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,description TEXT  NULL)";
    public static String DropProductCategoryList = "DROP TABLE IF EXISTS product_category";
    public static String ProductCategoryList = "CREATE TABLE IF NOT EXISTS  product_category (id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,description TEXT  NULL)";
    public static String DropLocationCategoryList = "DROP TABLE IF EXISTS location_category";
    public static String LocationCategoryList = "CREATE TABLE IF NOT EXISTS  location_category (id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,description TEXT  NULL)";
    public static String DropTrackingStoreStatus = "DROP TABLE IF EXISTS stores_status";
    public static String TrackingStoreStatus = "CREATE TABLE IF NOT EXISTS  stores_status (customer_id TEXT, latitude text, longitude text, status text,  transferdatastatus TEXT, picture TEXT, period TEXT, sysentrydate text,modifieddate text,modifiedby TEXT, PRIMARY KEY (customer_id, period))";
    public static String DropTrackingComment = "DROP TABLE IF EXISTS mt_comment";
    public static String TrackingComment = "CREATE TABLE IF NOT EXISTS  [mt_comment] ([customer_id] TEXT  NULL,[comment] TEXT  NULL,[period] TEXT  NULL,sysentrydate text null,modifieddate text null,modifiedby  TEXT  NULL,PRIMARY KEY ([customer_id],period,[modifiedby]))";
    public static String DropTrackingSignature = "DROP TABLE IF EXISTS mt_signature";
    public static String TrackingSignature = "CREATE TABLE IF NOT EXISTS  mt_signature (customer_id TEXT, name TEXT,period TEXT, sysentrydate text null,modifieddate text null,modifiedby TEXT, PRIMARY KEY (customer_id, period, modifiedby))";
    public static String DropTrackingSoviLocation = "DROP TABLE IF EXISTS mt_sovi_location";
    public static String TrackingSoviLocation = "CREATE TABLE IF NOT EXISTS  [mt_sovi_location] ([customer_id] TEXT  NULL,[location] TEXT  NULL,[picture] TEXT  NULL,[period] TEXT  NULL, sysentrydate text null,modifieddate text null,modifiedby TEXT, PRIMARY KEY ([customer_id],[location],[period],[modifiedby]))";
    public static String DropTrackingPicture = "DROP TABLE IF EXISTS mt_picture";
    public static String TrackingPicture = "CREATE TABLE IF NOT EXISTS  [mt_picture] ([a_id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[customer_id] TEXT  NULL,[category] TEXT  NULL,[subcategory] TEXT  NULL,[subsubcategory] TEXT  NULL,[subtype] TEXT  NULL,[count] TEXT  NULL,[picture] TEXT  NULL,[period] TEXT  NULL,sysentrydate text null,modifieddate text null,modifiedby TEXT)";
    public static String DropTrackingCategoryComment = "DROP TABLE IF EXISTS mt_category_comment";
    public static String TrackingCategoryComment = "CREATE TABLE IF NOT EXISTS  [mt_category_comment] ([customer_id] TEXT  NULL,[category] TEXT  NULL,[comment] TEXT  NULL,[period] TEXT  NULL, sysentrydate text null,modifieddate text null,modifiedby TEXT, PRIMARY KEY ([customer_id],[category],[period],[modifiedby]))";
    //public static String DropDeliveryRemarksList = "DROP TABLE IF EXISTS delivery_remarks";
    //public static String DeliveryRemarksList = "CREATE TABLE IF NOT EXISTS  delivery_remarks (id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,description TEXT  NULL)";
    public static String DropDeliveryPictures = "DROP TABLE IF EXISTS messengerial_pictures";
    public static String DeliveryPictures = "CREATE TABLE IF NOT EXISTS [messengerial_pictures] (client TEXT NULL,accountnumber TEXT NULL,picture TEXT NULL,timestamp TEXT NULL,date TEXT NULL,user_id TEXT NULL,sysentrydate TEXT NULL,modifieddate TEXT NULL,modifiedby TEXT NULL)";


    //AUDIT
    public static String DropAuditDownload = "DROP TABLE IF EXISTS audit_download";
    public static String AuditDownload = "CREATE TABLE IF NOT EXISTS  [audit_download] ([JobOrderId] TEXT  NULL,[Client] TEXT NULL,[JobOrderTitle] TEXT  NULL,[JobOrderDetails] TEXT  NULL,[Route] TEXT  NULL,[Itinerary] TEXT  NULL,[AccountNumber] TEXT  NULL,[AccountName] TEXT  NULL,[Address] TEXT  NULL,[Latitude] TEXT  NULL,[Longitude] TEXT  NULL,[JobOrderDate] TEXT NULL, sysentrydate text null,modifieddate text null,modifiedby TEXT, PRIMARY KEY ([JobOrderId],[AccountNumber],[JobOrderDate]))";
    public static String DropAuditUpload = "DROP TABLE IF EXISTS audit_upload";
    public static String AuditUpload = "CREATE TABLE IF NOT EXISTS  [audit_upload] ([AuditId] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[JobOrderId] TEXT  NULL,[Client] TEXT NULL,[AccountNumber] TEXT  NULL,[Vehicle] TEXT  NULL,[Fare] TEXT  NULL,[Comment] TEXT  NULL,[Latitude] TEXT  NULL,[Longitude] TEXT  NULL,[JobOrderDate] TEXT NULL,[Status] TEXT NULL, sysentrydate text null,modifieddate text null,modifiedby TEXT null)";


    //LOGIN
    public static String DropUMSSettings = "DROP TABLE IF EXISTS ums_settings";
    public static String UMSSettings = "CREATE TABLE IF NOT EXISTS  [ums_settings] (" +
            "[Id] TEXT PRIMARY KEY," +
            "[ReverseInput] TEXT NULL," +
            "[HideKeyboard] TEXT NULL," +
            "[sysentrydate] TEXT  NULL," +
            "[modifieddate] TEXT  NULL," +
            "[modifiedby] TEXT  NULL )";


    //LOGIN
    public static String DropUMSAccount = "DROP TABLE IF EXISTS ums_account";
    public static String UMSAccount = "CREATE TABLE IF NOT EXISTS  [ums_account] (" +
            "[UserId] TEXT PRIMARY KEY," +
            "[Client] TEXT NULL," +
            "[Username] TEXT  NULL," +
            "[Password] TEXT  NULL," +
            "[Status] TEXT  NULL," +
            "[branch] TEXT  NULL," +
            "[position] TEXT  NULL," +
            "[name] TEXT  NULL," +
            "[lastname] TEXT  NULL," +
            "[firstname] TEXT  NULL," +
            "[middlename] TEXT  NULL," +
            "[sysentrydate] TEXT  NULL," +
            "[modifieddate] TEXT  NULL," +
            "[modifiedby] TEXT  NULL )";

    //ADMIN
    public static String Admin = "INSERT INTO ums_account ([UserId],[Username],[Password],[Status]) VALUES('001','000','c6f057b86584942e415435ffb1fa93d4','Logout')";
    public static String[] CoolerPlanogramListData = {
            "INSERT INTO cde_planogram_compliance(id,description) VALUES ('1','60:40');",
            "INSERT INTO cde_planogram_compliance(id,description) VALUES ('2','Coke RED Cooler');",
            "INSERT INTO cde_planogram_compliance(id,description) VALUES ('3','MDC Cooler Rental');"
    };

    //DELIVERY TABLE
    public static String DropDeliveryTableList = "DROP TABLE IF EXISTS messengerial";
    public static String DeliveryTableList = "CREATE TABLE IF NOT EXISTS  [messengerial] (" +
            "[m_client] TEXT  NULL," +
            "[job_id] TEXT  NULL," +
            "[job_title] TEXT  NULL," +
            "[m_accountnumber] TEXT  NULL," +
            "[m_type] TEXT  NULL," +
            "[m_type_description] TEXT  NULL," +
            "[user_id] TEXT  NULL," +
            "[m_status] TEXT  NULL," +
            "[m_remark_code] TEXT  NULL," +
            "[m_remark] TEXT  NULL," +
            "[m_comment] TEXT  NULL," +
            "[m_latitude] TEXT  NULL," +
            "[m_longitude] TEXT  NULL," +
            "[m_delivered_timestamp] TEXT  NULL," +
            "[m_delivered_date] TEXT  NULL," +
            "[transfer_data_status] TEXT  NULL," +
            "[m_signature] TEXT  NULL," +
            "[upload_status] TEXT  NULL," +
            "[battery_life] TEXT  NULL," +
            "[sysentrydate] TEXT  NULL," +
            "[modifieddate] TEXT  NULL," +
            "[modifiedby] TEXT  NULL," +
            "PRIMARY KEY ([m_accountnumber],[m_client],[job_id])" +
            ")";

    public static String DropStockInItem = "DROP TABLE IF EXISTS StockInItem";
    public static String StockInItem = "CREATE TABLE IF NOT EXISTS  [StockInItem] (\n" +
            "[client] TEXT  NULL,\n" +
            "[stockInId] TEXT  NULL,\n" +
            "[stockInTitle] TEXT  NULL,\n" +
            "[itemTypeId] TEXT  NULL,\n" +
            "[itemDescription] TEXT  NULL,\n" +
            "[itemQuantity] TEXT  NULL,\n" +
            "[stockInDate] TEXT  NULL,\n" +
            "[userId] TEXT  NULL,\n" +
            "[sysEntryDate] TEXT  NULL,\n" +
            "[modifiedBy] TEXT  NULL,\n" +
            "PRIMARY KEY ([client],[stockInId],[itemTypeId],[stockInDate],[userId])" +
            ")";
    public static String DropItemType = "DROP TABLE IF EXISTS ItemType";
    public static String ItemType = "CREATE TABLE IF NOT EXISTS  [ItemType] (" +
            "[id] TEXT PRIMARY KEY," +
            "[description] TEXT  NULL," +
            "[abbreviation] TEXT  NULL," +
            "[sysentrydate] TEXT  NULL"+
            ")";

    //Data of the ItemType
    public static String[] ItemTypeData = {
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('1','Statement of Accounts','SOA');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('2','Disconnection Notice','DN');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('3','NCT','NCT');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('4','NAC','NAC');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('5','OSB','OSB');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('6','OSN','OSN');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('7','MECO LTR','MECO LTR');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('8','AUBD','AUBD');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('9','RE OUT SOA','RE OUT SOA');",
            "INSERT INTO ItemType(id,description,abbreviation) VALUES ('10','RE OUT DN','RE OUT DN');",
    };


    public static String DropCustomerDeliveryList = "DROP TABLE IF EXISTS customer_delivery_downloads";
    public static String CustomerDeliveryList = "CREATE TABLE IF NOT EXISTS  [customer_delivery_downloads] (" +
            "[tracking_number]," +
            "[barcode] TEXT  NULL," +
            "[client] TEXT  NULL," +
            "[job_id] TEXT  NULL," +
            "[user_id] TEXT  NULL," +
            "[fullname] TEXT  NULL," +
            "[position] TEXT  NULL," +
            "[code] TEXT  NULL," +
            "[type] TEXT  NULL," +
            "[quantity] TEXT  NULL," +
            "[timestamp] TEXT  NULL," +
            "[delivery_date] TEXT  NULL," +
            "[received] TEXT  NULL," +
            "[status] TEXT  NULL," +
            "[latitude] TEXT  NULL," +
            "[longitude] TEXT  NULL," +
            "[sysentrydate] TEXT  NULL," +
            "[modifieddate] TEXT  NULL," +
            "[modifiedby] TEXT  NULL," +
            "PRIMARY KEY ([tracking_number],[client],[job_id])" +
            ")";

    public static String[] LocationCategoryListData = {
            "INSERT INTO location_category(id,description) VALUES ('1','Shelves');",
            "INSERT INTO location_category(id,description) VALUES ('2','Exhibits');",
            "INSERT INTO location_category(id,description) VALUES ('3','Common Cold Vault');",
            "INSERT INTO location_category(id,description) VALUES ('4','Company-Owned Coolers');",
    };


    //Meter Reading
    public static String DropMeterReadingCustomerReadingDownloads = "DROP TABLE IF EXISTS customer_reading_downloads";
    public static String MeterReadingCustomerReadingDownloads =
            "CREATE TABLE IF NOT EXISTS  [customer_reading_downloads] (" +
                    "[C_Client] TEXT  NULL," +
                    "[Code] TEXT  NULL," +
                    "[job_id] TEXT  NULL," +
                    "[C_ID] TEXT  NULL," +
                    "[C_Lastname] TEXT  NULL," +
                    "[C_Firstname] TEXT  NULL," +
                    "[C_Middlename] TEXT  NULL," +
                    "[C_Type] TEXT  NULL," +
                    "[classification] TEXT  NULL," +
                    "[C_Status] TEXT  NULL," +
                    "[C_Establishment] TEXT  NULL," +
                    "[route] TEXT  NULL," +
                    "[route_itinerary] TEXT  NULL," +
                    "[itinerary] TEXT  NULL," +
                    "[status_description] TEXT  NULL," +
                    "[C_Approval_Status] TEXT  NULL," +
                    "[R_Description] TEXT  NULL," +
                    "[account_name] TEXT  NULL," +
                    "[C_Country] TEXT  NULL," +
                    "[C_Region] TEXT  NULL," +
                    "[C_Province] TEXT  NULL," +
                    "[C_City] TEXT  NULL," +
                    "[C_Brgy] TEXT  NULL," +
                    "[Country_Label] TEXT  NULL," +
                    "[Region_Label] TEXT  NULL," +
                    "[Province_Label] TEXT  NULL," +
                    "[Municipality_City_Label] TEXT  NULL," +
                    "[Loc_Barangay_Label] TEXT  NULL," +
                    "[C_Street] TEXT  NULL," +
                    "[Complete_Address] TEXT  NULL," +
                    "[C_AccountNumber] TEXT  NULL," +
                    "[C_Meter_Type] TEXT  NULL," +
                    "[bill_route] TEXT  NULL," +
                    "[bill_itinerary] TEXT  NULL," +
                    "[bill_sequence] TEXT  NULL," +
                    "[CED_Sequence] TEXT  NULL," +
                    "[CED_MeterBrand] TEXT  NULL," +
                    "[CED_MeterNumber] TEXT  NULL," +
                    "[pipe_size] TEXT  NULL," +
                    "[special_meter_tag] TEXT  NULL," +
                    "[multiplier] TEXT  NULL," +
                    "[cg_vat_zero_tag] TEXT  NULL," +
                    "[meter_count] TEXT  NULL," +
                    "[arrears] TEXT  NULL," +
                    "[coreloss] TEXT  NULL," +
                    "[Average_Reading] TEXT  NULL," +
                    "[Averange_Consumption] TEXT  NULL," +
                    "[PreviousReading] TEXT  NULL," +
                    "[PreviousReactive] TEXT  NULL," +
                    "[PreviousDemand] TEXT  NULL," +
                    "[PreviousPowerFactor] TEXT  NULL," +
                    "[PreviousKWCummulative] TEXT  NULL," +
                    "[PreviousConsumption] TEXT  NULL," +
                    "[Previous_Reading_Date] TEXT  NULL," +
                    "[reading_date] TEXT  NULL," +
                    "[rentalfee] TEXT  NULL," +
                    "[Transformer] TEXT  NULL," +
                    "[inclusion] TEXT  NULL," +
                    "[senior_citizen_tag] TEXT  NULL," +
                    "[months_unpaid_bill] TEXT  NULL," +
                    "[change_meter_reading] TEXT  NULL," +
                    "[c_group] TEXT  NULL," +
                    "[book] TEXT  NULL," +
                    "[ispn] TEXT  NULL," +
                    "[interest] TEXT  NULL," +
                    "[serial] TEXT  NULL," +
                    "[duedate] TEXT  NULL," +
                    "[bill_number] TEXT  NULL," +
                    "[BillMonth] TEXT  NULL," +
                    "[BillDate] TEXT  NULL," +
                    "[year] TEXT  NULL," +
                    "[C_SysEntryDate] TEXT  NULL," +
                    "[C_ModifiedDate] TEXT  NULL," +
                    "[C_ModifiedBy] TEXT  NULL," +
                    "[Average_amount] TEXT  NULL," +
                    "[eda] TEXT  NULL," +
                    "[over30days] TEXT  NULL, " +
                    "over90days TEXT NULL, " +
                    "over60days TEXT NULL, " +
                    "advance_payment TEXT NULL, " +
                    "scap_bill TEXT NULL, " +
                    "scap_paid TEXT NULL, " +
                    "adjust_amount TEXT NULL, " +
                    "adjust_amount1 TEXT NULL, " +
                    "add_deduct TEXT NULL, " +
                    "discount TEXT NULL, " +
                    "[load] TEXT  NULL," +
                    "[OCDate1] TEXT NULL," +
                    "total_initial TEXT NULL,"+
                    "latitude TEXT NULL,"+
                    "longitude TEXT NULL,"+
                    "CMPresentReadingKWH TEXT NULL,"+
                    "PRIMARY KEY ([C_Client],[C_ID],[C_AccountNumber],[CED_MeterNumber],[serial],[reading_date])" +
                    ")";


    public static String DropMeterReadingMeterNotInList = "DROP TABLE IF EXISTS meter_not_in_list";
    public static String MeterReadingMeterNotInList =
            "CREATE TABLE IF NOT EXISTS  [meter_not_in_list] (" +
                    "[id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "[job_id] TEXT  NULL," +
                    "[client] TEXT  NULL," +
                    "[customer_meterno] TEXT  NULL," +
                    "[customer_name] TEXT  NULL," +
                    "[customer_address] TEXT  NULL," +
                    "[remarks] TEXT  NULL," +
                    "[picture] TEXT  NULL," +
                    "[latitude] TEXT  NULL," +
                    "[longitude] TEXT  NULL," +
                    "[timestamp] TEXT  NULL," +
                    "[Reader_ID] TEXT  NULL," +
                    "[modifiedby] TEXT  NULL," +
                    "[route] TEXT  NULL," +
                    "[itinerary] TEXT  NULL," +
                    "[reading_date] TEXT  NULL," +
                    "[nearest_meter] TEXT  NULL,"+
                    "[nearest_seq] TEXT  NULL,"+
                    "[reading] TEXT  NULL,"+
                    "[accountnumber] TEXT  NULL,"+
                    "[serial] TEXT  NULL,"+
                    "[contactnumber] TEXT  NULL,"+
                    "[emailaddress] TEXT  NULL,"+
                    "[meterbrand] TEXT  NULL,"+
                    "[metertype] TEXT  NULL,"+
                    "[structure] TEXT  NULL"+
                    ")";


    public static String DropMeterReadingPictures = "DROP TABLE IF EXISTS meter_reading_pictures";
    public static String MeterReadingPictures =
            "CREATE TABLE IF NOT EXISTS  [meter_reading_pictures] (\n" +
                    "[client] TEXT  NULL,\n" +
                    "[AccountNumber] TEXT  NULL,\n" +
                    "[type] TEXT  NULL,\n" +
                    "[picture] TEXT  NULL,\n" +
                    "[timestamp] TEXT  NULL,\n" +
                    "[modifieddate] TEXT  NULL,\n" +
                    "[Reader_ID] TEXT  NULL,\n" +
                    "[modifiedby] TEXT  NULL,\n" +
                    "[reading_date] TEXT  NULL,\n" +
                    "PRIMARY KEY ([client],[AccountNumber],[type],[picture],[reading_date])\n" +
                    ")";

    public static String DropMeterReadingMOA = "DROP TABLE IF EXISTS moa";
    public static String MeterReadingMOA = "CREATE TABLE IF NOT EXISTS  [moa] (\n" +
            "[client] TEXT  NULL,\n" +
            "[job_id] TEXT  NULL,\n" +
            "[route] TEXT  NULL,\n" +
            "[reading_date] text  NULL,\n" +
            "[accountnumber] text  NULL,\n" +
            "[description] text  NULL,\n" +
            "[amount] text  NULL,\n" +
            "[sysentrydate] text  NULL,\n" +
            "[modifieddate] text  NULL,\n" +
            "[modifiedby] text  NULL,\n" +
            "PRIMARY KEY ([client],[job_id],[route],[reading_date],[accountnumber],[description])\n" +
            ")";

    public static String DropMeterReadingRates = "DROP TABLE IF EXISTS rates";
    public static String MeterReadingRates = "CREATE TABLE IF NOT EXISTS  rates (R_Client TEXT, R_ID TEXT, R_Type TEXT, RD_ID TEXT, rate_description TEXT, classification TEXT, Rates_Price TEXT,cycle TEXT, rate_date_from TEXT, rate_date_to TEXT, Rates_Status TEXT, Rates_SysEntryDate timestamp, Rates_ModifiedDate timestamp, Rates_ModifiedBy TEXT, PRIMARY KEY (R_ID, RD_ID, rate_date_from))";

    public static String DropMeterReadingRates2 = "DROP TABLE IF EXISTS rates2";
    public static String MeterReadingRates2 = "CREATE TABLE IF NOT EXISTS  [rates2] (\n" +
            "[r_id] TEXT  NOT NULL,\n" +
            "[client] TEXT  NULL,\n" +
            "[billmonth] TEXT  NOT NULL,\n" +
            "[consumption] TEXT  NOT NULL,\n" +
            "[rates] TEXT  NULL,\n" +
            "[Rates_SysEntryDate] TEXT  NULL,\n" +
            "[Rates_ModifiedDate] TEXT  NULL,\n" +
            "[Rates_ModifiedBy] TEXT  NULL,\n" +
            "PRIMARY KEY ([r_id],[client],[billmonth],[consumption])\n" +
            ")";

    public static String DropMeterReadingRatesDescription = "DROP TABLE IF EXISTS rates_description";
    public static String MeterReadingRatesDescription = "CREATE TABLE IF NOT EXISTS  `rates_description` (\n" +
            "  `RD_ID` int(11) ,\n" +
            "  `RD_Client` text,\n" +
            "  `R_Formula` text,\n" +
            "  `R_Group` text,\n" +
            "  `RD_Description` text,\n" +
            "  `classification_id` text,\n" +
            "  `classification` text,\n" +
            "  `RD_SysEntryDate` text,\n" +
            "  `RD_ModifiedDate` text,\n" +
            "  `RD_ModifiedBy` text,\n" +
            "  PRIMARY KEY (`RD_ID`,`RD_Client`,`RD_Description`)\n" +
            ")";


    public static String DropMeterReadingLifeline = "DROP TABLE IF EXISTS rates_lifeline";
    public static String MeterReadingLifeline = "CREATE TABLE IF NOT EXISTS  [rates_lifeline] (\n" +
            "[client] TEXT  NOT NULL,\n" +
            "[billmonth] TEXT  NOT NULL,\n" +
            "[start_kwh] TEXT  NOT NULL,\n" +
            "[end_kwh] TEXT  NOT NULL,\n" +
            "[discount] TEXT  NOT NULL,\n" +
            "[SysEntryDate] timestamp  NULL,\n" +
            "[ModifiedDate] timestamp  NULL,\n" +
            "[ModifiedBy] TEXT  NULL,\n" +
            "PRIMARY KEY ([client],[billmonth],[start_kwh],[end_kwh])\n" +
            ")";

    public static String DropMeterReadingReading = "DROP TABLE IF EXISTS reading";
    public static String MeterReadingReading = "CREATE TABLE IF NOT EXISTS  [reading] (\n" +
            "[Client] TEXT  NOT NULL,\n" +
            "[C_ID] TEXT  NOT NULL,\n" +
            "[AccountNumber] TEXT  NOT NULL,\n" +
            "[job_id] TEXT  NULL,\n" +
            "[name] TEXT  NULL,\n" +
            "[route] TEXT  NULL,\n" +
            "[itinerary] TEXT  NULL,\n" +
            "[meter_number] TEXT  NOT NULL,\n" +
            "[serial] TEXT  NULL,\n" +
            "[previous_reading] TEXT  NULL,\n" +
            "[present_Reading] TEXT  NULL,\n" +
            "[previous_consumption] TEXT  NULL,\n" +
            "[Present_Consumption] TEXT  NULL,\n" +
            "[previous_reading_date] TEXT  NULL,\n" +
            "[present_reading_date] TEXT  NULL,\n" +
            "[duedate] TEXT  NULL,\n" +
            "[discondate] TEXT  NULL,\n" +
            "[Reading_Date] TEXT  NOT NULL,\n" +
            "[BillYear] TEXT  NULL,\n" +
            "[BillMonth] TEXT  NULL,\n" +
            "[BillDate] TEXT  NULL,\n" +
            "[month] TEXT  NULL,\n" +
            "[day] TEXT  NULL,\n" +
            "[year] TEXT  NULL,\n" +
            "[Demand] TEXT  NULL,\n" +
            "[reactive] TEXT  NULL,\n" +
            "[powerfactor] TEXT  NULL,\n" +
            "[kw_cummulative] TEXT  NULL,\n" +
            "[reading1] TEXT  NULL,\n" +
            "[reading2] TEXT  NULL,\n" +
            "[reading3] TEXT  NULL,\n" +
            "[reading4] TEXT  NULL,\n" +
            "[reading5] TEXT  NULL,\n" +
            "[reading6] TEXT  NULL,\n" +
            "[reading7] TEXT  NULL,\n" +
            "[reading8] TEXT  NULL,\n" +
            "[reading9] TEXT  NULL,\n" +
            "[reading10] TEXT  NULL,\n" +
            "[iwpowerfactor] TEXT  NULL,\n" +
            "[demand_consumption] TEXT  NULL,\n" +
            "[reactive_consumption] TEXT  NULL,\n" +
            "[Averange_Consumption] TEXT  NULL,\n" +
            "[Average_Reading] TEXT  NULL,\n" +
            "[multiplier] TEXT  NULL,\n" +
            "[Meter_Box] TEXT  NULL,\n" +
            "[Demand_Reset] TEXT  NULL,\n" +
            "[Test_Block] TEXT  NULL,\n" +
            "[Remarks_Code] TEXT  NULL,\n" +
            "[remarks_abbreviation] TEXT  NULL,\n" +
            "[Remarks] TEXT  NULL,\n" +
            "[Comment] TEXT  NULL,\n" +
            "[Reader_ID] TEXT  NULL,\n" +
            "[meter_reader] TEXT  NULL,\n" +
            "[Reading_Attempt] TEXT  NULL,\n" +
            "[Print_Attempt] TEXT  NULL,\n" +
            "[force_reading] TEXT  NULL,\n" +
            "[r_latitude] TEXT  NULL,\n" +
            "[r_longitude] TEXT  NULL,\n" +
            "[printlatitude] TEXT  NULL,\n" +
            "[printlongitude] TEXT  NULL,\n" +
            "[improbable_reading] TEXT  NULL,\n" +
            "[negative_reading] TEXT  NULL,\n" +
            "[change_reading] TEXT  NULL,\n" +
            "[cg_vat_zero_tag] TEXT  NULL,\n" +
            "[reading_remarks] TEXT  NULL,\n" +
            "[old_key] TEXT  NULL,\n" +
            "[new_key] TEXT  NULL,\n" +
            "[transfer_data_status] TEXT  NULL,\n" +
            "[upload_status] TEXT  NULL,\n" +
            "[code] TEXT  NULL,\n" +
            "[area] TEXT  NULL,\n" +
            "[rate_code] TEXT  NULL,\n" +
            "[cummulative_multiplier] TEXT  NULL,\n" +
            "[oebr_number] TEXT  NULL,\n" +
            "[sequence] TEXT  NULL,\n" +
            "[Reading_TimeStamp] TEXT  NULL,\n" +
            "[Print_TimeStamp] TEXT  NULL,\n" +
            "[timestamp] TEXT  NULL,\n" +
            "[bill_number] TEXT  NULL,\n" +
            "[GenerationSystem] TEXT  NULL,\n" +
            "[BenHost] TEXT  NULL,\n" +
            "[GRAM] TEXT  NULL,\n" +
            "[ICERA] TEXT  NULL,\n" +
            "[PowerArtReduction] TEXT  NULL,\n" +
            "[TransmissionDelivery] TEXT  NULL,\n" +
            "[TransmissionDelivery2] TEXT  NULL,\n" +
            "[System_Loss] TEXT  NULL,\n" +
            "[Gen_Trans_Rev] TEXT  NULL,\n" +
            "[DistributionNetwork] TEXT  NULL,\n" +
            "[DistributionNetwork2] TEXT  NULL,\n" +
            "[DistributionNetwork3] TEXT  NULL,\n" +
            "[RetailElectricService] TEXT  NULL,\n" +
            "[RetailElectricService2] TEXT  NULL,\n" +
            "[Metering(cust)] TEXT  NULL,\n" +
            "[Metering(cust)2] TEXT  NULL,\n" +
            "[Metering(kwh)] TEXT  NULL,\n" +
            "[loan] TEXT  NULL,\n" + //inclusion
            "[RFSC] TEXT  NULL,\n" +
            "[Distribution_Rev] TEXT  NULL,\n" +
            "[MissionaryElectrification] TEXT  NULL,\n" +
            "[EnvironmentCharge] TEXT  NULL,\n" +
            "[NPC_StrandedDebts] TEXT  NULL,\n" +
            "[NPC_StrandedCost] TEXT  NULL,\n" +
            "[DUsCost] TEXT  NULL,\n" +
            "[DCDistributionCharge] TEXT  NULL,\n" +
            "[DCDemandCharge] TEXT  NULL,\n" +
            "[TCTransSystemCharge] TEXT  NULL,\n" +
            "[SCSupplySysCharge] TEXT  NULL,\n" +
            "[equal_tax] TEXT  NULL,\n" +
            "[CrossSubsidyRemoval] TEXT  NULL,\n" +
            "[Universal_Charges] TEXT  NULL,\n" +
            "[Lifeline(Charge)] TEXT  NULL,\n" +
            "[InterclassCrossSubsidy] TEXT  NULL,\n" +
            "[SeniorCitizenSubsidy] TEXT  NULL,\n" +
            "[ICCS_Adjustment] TEXT  NULL,\n" +
            "[ICCrossSubsidyCharge] TEXT  NULL,\n" +
            "[FitAllCharge] TEXT  NULL,\n" +
            "[PPD_Adjustment] TEXT  NULL,\n" +
            "[GenerationCostAdjustment] TEXT  NULL,\n" +
            "[PowerCostAdjustment] TEXT  NULL,\n" +
            "[Other_Rev] TEXT  NULL,\n" +
            "[GenerationVat] TEXT  NULL,\n" +
            "[TransmissionVat] TEXT  NULL,\n" +
            "[SystemLossVat] TEXT  NULL,\n" +
            "[DistributionVat] TEXT  NULL,\n" +
            "[OtherVat] TEXT  NULL,\n" +
            "[Government_Rev] TEXT  NULL,\n" +
            "[CurrentBill] TEXT  NULL,\n" +
            "[amountdue] TEXT  NULL,\n" +
            "[overdue] TEXT  NULL,\n" +
            "[franchise_tax] TEXT  NULL,\n" +
            "[coreloss] TEXT  NULL,\n" +
            "[surcharge] TEXT  NULL,\n" + //ILP
            "[rentalfee] TEXT  NULL,\n" + // government subsidies
            "[delivered] TEXT  NULL,\n" +
            "[check_previous] TEXT  NULL,\n" +
            "[ispn] TEXT  NULL,\n" +
            "[SCD] TEXT  NULL,\n" +
            "[pnrecvdte] TEXT  NULL,\n" +
            "[pnrecvby] TEXT  NULL,\n" +
            "[recvby] TEXT  NULL,\n" +
            "[hash] TEXT  NULL,\n" +
            "[isreset] TEXT  NULL,\n" +
            "[isprntd] TEXT  NULL,\n" +
            "[meter_count] TEXT  NULL,\n" +
            "[delivery_id] TEXT  NULL,\n" +
            "[delivery_remarks] TEXT  NULL,\n" +
            "[delivery_comment] TEXT  NULL,\n" +
            "[reading_signature] TEXT  NULL,\n" +
            "[real_property_tax] TEXT  NULL,\n" +
            "[cc_rstc_refund] TEXT  NULL,\n" +
            "[cc_rstc_refund2] TEXT  NULL,\n" +
            "[moa] TEXT  NULL,\n" +
            "[eda] TEXT  NULL, \n" +

            "[ModifiedDate] TEXT  NULL,\n" +
            "[ModifiedBy] TEXT  NULL,\n" +
            "PRIMARY KEY ([Client],[C_ID],[AccountNumber],[job_id],[meter_number],[Reading_Date])\n" +
            ")";
    public static String DropMeterReadingReadingLogs = "DROP TABLE IF EXISTS reading_logs";
    public static String MeterReadingReadingLogs = "CREATE TABLE IF NOT EXISTS  [reading_logs] (\n" +
            "[id] INTEGER  PRIMARY KEY AUTOINCREMENT NULL,\n" +
            "[Client] TEXT  NOT NULL,\n" +
            "[C_ID] TEXT  NOT NULL,\n" +
            "[AccountNumber] TEXT  NOT NULL,\n" +
            "[job_id] TEXT  NULL,\n" +
            "[name] TEXT  NULL,\n" +
            "[route] TEXT  NULL,\n" +
            "[itinerary] TEXT  NULL,\n" +
            "[meter_number] TEXT  NOT NULL,\n" +
            "[serial] TEXT  NULL,\n" +
            "[previous_reading] TEXT  NULL,\n" +
            "[present_Reading] TEXT  NULL,\n" +
            "[previous_consumption] TEXT  NULL,\n" +
            "[Present_Consumption] TEXT  NULL,\n" +
            "[previous_reading_date] TEXT  NULL,\n" +
            "[present_reading_date] TEXT  NULL,\n" +
            "[duedate] TEXT  NULL,\n" +
            "[discondate] TEXT  NULL,\n" +
            "[Reading_Date] TEXT  NOT NULL,\n" +
            "[BillYear] TEXT  NULL,\n" +
            "[BillMonth] TEXT  NULL,\n" +
            "[BillDate] TEXT  NULL,\n" +
            "[month] TEXT  NULL,\n" +
            "[day] TEXT  NULL,\n" +
            "[year] TEXT  NULL,\n" +
            "[Demand] TEXT  NULL,\n" +
            "[reactive] TEXT  NULL,\n" +
            "[powerfactor] TEXT  NULL,\n" +
            "[kw_cummulative] TEXT  NULL,\n" +
            "[reading1] TEXT  NULL,\n" +
            "[reading2] TEXT  NULL,\n" +
            "[reading3] TEXT  NULL,\n" +
            "[reading4] TEXT  NULL,\n" +
            "[reading5] TEXT  NULL,\n" +
            "[reading6] TEXT  NULL,\n" +
            "[reading7] TEXT  NULL,\n" +
            "[reading8] TEXT  NULL,\n" +
            "[reading9] TEXT  NULL,\n" +
            "[reading10] TEXT  NULL,\n" +
            "[iwpowerfactor] TEXT  NULL,\n" +
            "[demand_consumption] TEXT  NULL,\n" +
            "[reactive_consumption] TEXT  NULL,\n" +
            "[Averange_Consumption] TEXT  NULL,\n" +
            "[Average_Reading] TEXT  NULL,\n" +
            "[multiplier] TEXT  NULL,\n" +
            "[Meter_Box] TEXT  NULL,\n" +
            "[Demand_Reset] TEXT  NULL,\n" +
            "[Test_Block] TEXT  NULL,\n" +
            "[Remarks_Code] TEXT  NULL,\n" +
            "[remarks_abbreviation] TEXT  NULL,\n" +
            "[Remarks] TEXT  NULL,\n" +
            "[Comment] TEXT  NULL,\n" +
            "[Reader_ID] TEXT  NULL,\n" +
            "[meter_reader] TEXT  NULL,\n" +
            "[Reading_Attempt] TEXT  NULL,\n" +
            "[Print_Attempt] TEXT  NULL,\n" +
            "[force_reading] TEXT  NULL,\n" +
            "[r_latitude] TEXT  NULL,\n" +
            "[r_longitude] TEXT  NULL,\n" +
            "[printlatitude] TEXT  NULL,\n" +
            "[printlongitude] TEXT  NULL,\n" +
            "[improbable_reading] TEXT  NULL,\n" +
            "[negative_reading] TEXT  NULL,\n" +
            "[change_reading] TEXT  NULL,\n" +
            "[cg_vat_zero_tag] TEXT  NULL,\n" +
            "[reading_remarks] TEXT  NULL,\n" +
            "[old_key] TEXT  NULL,\n" +
            "[new_key] TEXT  NULL,\n" +
            "[transfer_data_status] TEXT  NULL,\n" +
            "[upload_status] TEXT  NULL,\n" +
            "[code] TEXT  NULL,\n" +
            "[area] TEXT  NULL,\n" +
            "[rate_code] TEXT  NULL,\n" +
            "[cummulative_multiplier] TEXT  NULL,\n" +
            "[oebr_number] TEXT  NULL,\n" +
            "[sequence] TEXT  NULL,\n" +
            "[Reading_TimeStamp] TEXT  NULL,\n" +
            "[Print_TimeStamp] TEXT  NULL,\n" +
            "[timestamp] TEXT  NULL,\n" +
            "[bill_number] TEXT  NULL,\n" +
            "[GenerationSystem] TEXT  NULL,\n" +
            "[BenHost] TEXT  NULL,\n" +
            "[GRAM] TEXT  NULL,\n" +
            "[ICERA] TEXT  NULL,\n" +
            "[PowerArtReduction] TEXT  NULL,\n" +
            "[TransmissionDelivery] TEXT  NULL,\n" +
            "[TransmissionDelivery2] TEXT  NULL,\n" +
            "[System_Loss] TEXT  NULL,\n" +
            "[Gen_Trans_Rev] TEXT  NULL,\n" +
            "[DistributionNetwork] TEXT  NULL,\n" +
            "[DistributionNetwork2] TEXT  NULL,\n" +
            "[DistributionNetwork3] TEXT  NULL,\n" +
            "[RetailElectricService] TEXT  NULL,\n" +
            "[RetailElectricService2] TEXT  NULL,\n" +
            "[Metering(cust)] TEXT  NULL,\n" +
            "[Metering(cust)2] TEXT  NULL,\n" +
            "[Metering(kwh)] TEXT  NULL,\n" +
            "[loan] TEXT  NULL,\n" +
            "[RFSC] TEXT  NULL,\n" +
            "[Distribution_Rev] TEXT  NULL,\n" +
            "[MissionaryElectrification] TEXT  NULL,\n" +
            "[EnvironmentCharge] TEXT  NULL,\n" +
            "[NPC_StrandedDebts] TEXT  NULL,\n" +
            "[NPC_StrandedCost] TEXT  NULL,\n" +
            "[DUsCost] TEXT  NULL,\n" +
            "[DCDistributionCharge] TEXT  NULL,\n" +
            "[DCDemandCharge] TEXT  NULL,\n" +
            "[TCTransSystemCharge] TEXT  NULL,\n" +
            "[SCSupplySysCharge] TEXT  NULL,\n" +
            "[equal_tax] TEXT  NULL,\n" +
            "[CrossSubsidyRemoval] TEXT  NULL,\n" +
            "[Universal_Charges] TEXT  NULL,\n" +
            "[Lifeline(Charge)] TEXT  NULL,\n" +
            "[InterclassCrossSubsidy] TEXT  NULL,\n" +
            "[SeniorCitizenSubsidy] TEXT  NULL,\n" +
            "[ICCS_Adjustment] TEXT  NULL,\n" +
            "[ICCrossSubsidyCharge] TEXT  NULL,\n" +
            "[FitAllCharge] TEXT  NULL,\n" +
            "[PPD_Adjustment] TEXT  NULL,\n" +
            "[GenerationCostAdjustment] TEXT  NULL,\n" +
            "[PowerCostAdjustment] TEXT  NULL,\n" +
            "[Other_Rev] TEXT  NULL,\n" +
            "[GenerationVat] TEXT  NULL,\n" +
            "[TransmissionVat] TEXT  NULL,\n" +
            "[SystemLossVat] TEXT  NULL,\n" +
            "[DistributionVat] TEXT  NULL,\n" +
            "[OtherVat] TEXT  NULL,\n" +
            "[Government_Rev] TEXT  NULL,\n" +
            "[CurrentBill] TEXT  NULL,\n" +
            "[amountdue] TEXT  NULL,\n" +
            "[overdue] TEXT  NULL,\n" +
            "[franchise_tax] TEXT  NULL,\n" +
            "[coreloss] TEXT  NULL,\n" +
            "[surcharge] TEXT  NULL,\n" +
            "[rentalfee] TEXT  NULL,\n" +
            "[delivered] TEXT  NULL,\n" +
            "[check_previous] TEXT  NULL,\n" +
            "[ispn] TEXT  NULL,\n" +
            "[SCD] TEXT  NULL,\n" +
            "[pnrecvdte] TEXT  NULL,\n" +
            "[pnrecvby] TEXT  NULL,\n" +
            "[recvby] TEXT  NULL,\n" +
            "[hash] TEXT  NULL,\n" +
            "[isreset] TEXT  NULL,\n" +
            "[isprntd] TEXT  NULL,\n" +
            "[meter_count] TEXT  NULL,\n" +
            "[delivery_id] TEXT  NULL,\n" +
            "[delivery_remarks] TEXT  NULL,\n" +
            "[delivery_comment] TEXT  NULL,\n" +
            "[reading_signature] TEXT  NULL,\n" +
            "[real_property_tax] TEXT  NULL,\n" +
            "[cc_rstc_refund] TEXT  NULL,\n" +
            "[cc_rstc_refund2] TEXT  NULL,\n" +
            "[moa] TEXT  NULL,\n" +
            "[eda] TEXT  NULL,\n" +
            "[ModifiedDate] TEXT  NULL,\n" +
            "[ModifiedBy] TEXT  NULL\n" +
            ")";



    //Disconnection Structure
    public static String DropCustomerDiconnectionDownloads = "DROP TABLE IF EXISTS [customer_disconnection_downloads] ";
    public static String CustomerDiconnectionDownloads =
            "CREATE TABLE IF NOT EXISTS  [customer_disconnection_downloads] (" +
                    "[Client] TEXT NULL," +
                    "[Code] TEXT NULL," +
                    "[job_id] TEXT NULL," +
                    "[id] TEXT NULL," +
                    "[lastname] TEXT NULL," +
                    "[firstname] TEXT NULL," +
                    "[middlename] TEXT NULL," +
                    "[type] TEXT NULL," +
                    "[establishment] TEXT NULL," +
                    "[accountnumber] TEXT NULL," +
                    "[account_name] TEXT NULL," +
                    "[tin] TEXT NULL," +
                    "[BA] TEXT NULL," +
                    "[route] TEXT NULL," +
                    "[route_itinerary] TEXT NULL," +
                    "[itinerary] TEXT NULL," +
                    "[status] TEXT NULL," +
                    "[status_description] TEXT NULL," +
                    "[country] TEXT NULL," +
                    "[region] TEXT NULL," +
                    "[province] TEXT NULL," +
                    "[city] TEXT NULL," +
                    "[brgy] TEXT NULL," +
                    "[country_label] TEXT NULL," +
                    "[region_label] TEXT NULL," +
                    "[province_label] TEXT NULL," +
                    "[municipality_city_label] TEXT NULL," +
                    "[loc_barangay_label] TEXT NULL," +
                    "[street] TEXT NULL," +
                    "[complete_address] TEXT NULL," +
                    "[longitude] TEXT  NULL,"+
                    "[latitude] TEXT  NULL,"+
                    "[sequence] TEXT NULL," +
                    "[multiplier] TEXT NULL," +
                    "[meter_type] TEXT NULL," +
                    "[meterbrand] TEXT NULL," +
                    "[meternumber] TEXT NULL," +
                    "[serial] TEXT NULL," +
                    "[meter_count] TEXT NULL," +
                    "[previousreading] TEXT NULL," +
                    "[previousconsumption] TEXT NULL," +
                    "[previous_reading_date] TEXT NULL," +
                    "[disconnection_date] TEXT NULL,"+
                    "[amountdue] TEXT NULL," +
                    "[duedate] TEXT NULL," +
                    "[cycleyear] TEXT NULL," +
                    "[cyclemonth] TEXT NULL," +
                    "[or_number] TEXT NULL," +
                    "[client_remarks] TEXT NULL," +
                    "[arrears] TEXT NULL," +
                    "[total_amount_due] TEXT NULL," +
                    "[last_payment_date] TEXT NULL," +

                    "[sysentrydate] TEXT NULL," +
                    "[modifieddate] TEXT NULL," +
                    "[modifiedby] TEXT NULL," +
                    " PRIMARY KEY ([Client],[job_id],[id],[accountnumber],[meternumber],[disconnection_date]))";

    public static String DropDiconnectionTable = "DROP TABLE IF EXISTS [disconnection] ";
    public static String  DiconnectionTable =
            "CREATE TABLE [disconnection] ("+
                    "[job_id] TEXT NULL,"+
                    "[client] TEXT NULL,"+
                    "[id] char(25) NOT NULL,"+
                    "[accountnumber] TEXT NULL,"+
                    "[name] TEXT NULL,"+
                    "[BA] TEXT NULL,"+
                    "[route] TEXT NULL,"+
                    "[itinerary] TEXT NULL,"+
                    "[tin] TEXT NULL,"+
                    "[meter_number] TEXT NULL,"+
                    "[meter_count] TEXT NULL,"+
                    "[serial] TEXT NULL,"+
                    "[previous_reading] TEXT NULL,"+
                    "[last_Reading] TEXT NULL,"+
                    "[previous_consumption] TEXT NULL,"+
                    "[last_consumption] TEXT NULL,"+
                    "[previous_reading_date] TEXT NULL,"+
                    "[disconnection_date] TEXT NULL,"+
                    "[month] TEXT NULL,"+
                    "[day] TEXT NULL,"+
                    "[year] TEXT NULL,"+
                    "[demand] TEXT NULL,"+
                    "[reactive] TEXT NULL,"+
                    "[powerfactor] TEXT NULL,"+
                    "[reading1] TEXT NULL,"+
                    "[reading2] TEXT NULL,"+
                    "[reading3] TEXT NULL,"+
                    "[reading4] TEXT NULL,"+
                    "[reading5] TEXT NULL,"+
                    "[reading6] TEXT NULL,"+
                    "[reading7] TEXT NULL,"+
                    "[reading8] TEXT NULL,"+
                    "[reading9] TEXT NULL,"+
                    "[reading10] TEXT NULL,"+
                    "[iwpowerfactor] TEXT NULL,"+
                    "[multiplier] TEXT NULL,"+
                    "[meter_box] TEXT NULL,"+
                    "[demand_reset] TEXT NULL,"+
                    "[remarks_code] TEXT NULL,"+
                    "[remarks_description] TEXT NULL,"+
                    "[remarks_reason] TEXT NULL,"+
                    "[comment] TEXT NULL,"+
                    "[meter_type] TEXT NULL,"+
                    "[meter_brand] TEXT NULL,"+
                    "[reader_id] TEXT NULL,"+
                    "[meter_reader] TEXT NULL,"+
                    "[disconnection_attempt] TEXT NULL,"+
                    "[r_latitude] double DEFAULT NULL,"+
                    "[r_longitude] double DEFAULT NULL,"+
                    "[transfer_data_status] TEXT NULL,"+
                    "[upload_status] TEXT NULL,"+
                    "[status] TEXT NULL,"+
                    "[code] int(11) DEFAULT NULL,"+
                    "[area] int(11) DEFAULT NULL,"+
                    "[region] TEXT NULL,"+
                    "[country] TEXT NULL,"+
                    "[province] TEXT NULL,"+
                    "[city] TEXT NULL,"+
                    "[brgy] TEXT NULL,"+
                    "[country_label] TEXT NULL,"+
                    "[province_label] TEXT NULL,"+
                    "[region_label] TEXT NULL,"+
                    "[municipality_city_label] TEXT NULL,"+
                    "[loc_barangay_label] TEXT NULL,"+
                    "[street] TEXT NULL,"+
                    "[complete_address] TEXT NULL,"+
                    "[rate_code] TEXT NULL,"+
                    "[sequence] TEXT NULL,"+
                    "[disconnection_timestamp] TEXT NULL,"+
                    "[timestamp] TEXT NULL,"+
                    "[isdisconnected] TEXT NULL,"+
                    "[ispayed] TEXT NULL,"+
                    "[disconnection_signature] TEXT NULL,"+
                    "[recvby] TEXT NULL,"+
                    "[amountdue] TEXT NULL,"+
                    "[duedate] TEXT NULL,"+
                    "[cyclemonth] TEXT NULL,"+
                    "[cycleyear] TEXT NULL,"+
                    "[or_number] TEXT NULL,"+
                    "[arrears] TEXT NULL,"+
                    "[total_amount_due] TEXT NULL,"+
                    "[last_payment_date] TEXT NULL,"+
                    "[sysentrydate] TEXT NULL,"+
                    "[modifieddate] TEXT NULL,"+
                    "[modifiedby] TEXT NULL,"+
                    "PRIMARY KEY ([job_id],[client],[id],[accountnumber],[meter_number],[disconnection_date])) ";

    public static String DropDisconnectionRemarks = "DROP TABLE IF  EXISTS ref_disconnection_remarks";
    public static String DisconnectionRemarks = "CREATE TABLE IF NOT EXISTS  [ref_disconnection_remarks] (\n" +
            "[remarks_id] TEXT  PRIMARY KEY NULL,\n" +
            "[remarks_description] TEXT  NULL\n" +
            ")";






    public static String DropMeterReadingDeliveryRemarks = "DROP TABLE IF EXISTS ref_delivery_remarks";
    public static String MeterReadingDeliveryRemarks = "CREATE TABLE IF NOT EXISTS  [ref_delivery_remarks] (\n" +
            "[remarks_id] TEXT  NULL PRIMARY KEY,\n" +
            "[remarks_description] TEXT  NULL\n" +
            ")";


    public static String DropMeterReadingImprobable = "DROP TABLE IF EXISTS ref_improbable";
    public static String MeterReadingImprobable = "CREATE TABLE IF NOT EXISTS  [ref_improbable] (\n" +
            "[improbable_id] TEXT  NULL PRIMARY KEY,\n" +
            "[improbable_description] TEXT  NULL\n" +
            ")";

    public static String DropMeterReadingMeterBrand = "DROP TABLE IF  EXISTS ref_meter_brand";
    public static String MeterReadingMeterBrand = "CREATE TABLE IF NOT EXISTS  [ref_meter_brand] (\n" +
            "[meter_brand_id] int(11)  NOT NULL,\n" +
            "[meter_client] varchar(50) DEFAULT 'NULL' NULL,\n" +
            "[meter_brand] varchar(100)  NOT NULL,\n" +
            "[meter_required_fields] int(11)  NOT NULL,\n" +
            "[sysentrydate] timestamp  NOT NULL,\n" +
            "[modifieddate] timestamp  NOT NULL,\n" +
            "[modifiedby] char(17)  NOT NULL\n" +
            ")";

    public static String DropMeterReadingNegativeReading = "DROP TABLE IF  EXISTS ref_negative";
    public static String MeterReadingNegativeReading = "CREATE TABLE IF NOT EXISTS  [ref_negative] (\n" +
            "[negative_reading_id] TEXT  PRIMARY KEY NULL,\n" +
            "[negative_reading_description] TEXT  NULL\n" +
            ")";


    public static String DropMeterReadingRemarks = "DROP TABLE IF  EXISTS ref_remarks";
    public static String MeterReadingRemarks = "CREATE TABLE IF NOT EXISTS  [ref_remarks] (\n" +
            "[remarks_id] TEXT  PRIMARY KEY NULL,\n" +
            "[remarks_description] TEXT  NULL,\n" +
            "[remarks_abbreviation] TEXT  NULL\n" +
            ")";

    public static String DropMeterReadingRoute = "DROP TABLE IF  EXISTS route";
    public static String MeterReadingRoute = "CREATE TABLE IF NOT EXISTS  [route] (\n" +
            "[id] TEXT  NOT NULL,\n" +
            "[client] TEXT  NULL,\n" +
            "[route] TEXT  NULL,\n" +
            "[area] TEXT  NULL,\n" +
            "[location] TEXT  NULL,\n" +
            "[landmark] TEXT  NULL,\n" +
            "[sysentrydate] TEXT  NULL,\n" +
            "[modifieddate] TEXT  NULL,\n" +
            "[modifiedby] TEXT  NULL,\n" +
            "PRIMARY KEY ([id],[client])\n" +
            ")";


    public static String IndexCustomerMeterReading = "CREATE UNIQUE INDEX [index_customer] ON [customer_reading_downloads](\n" +
            "[job_id]  ASC,\n" +
            "[C_Client]  ASC,\n" +
            "[C_ID]  ASC,\n" +
            "[C_AccountNumber]  ASC,\n" +
            "[CED_MeterNumber]  ASC,\n" +
            "[reading_date]  ASC\n" +
            ")";


    public static String IndexMeterReadingPicture = "CREATE UNIQUE INDEX [index_picture] ON [meter_reading_pictures](\n" +
            "[client]  ASC,\n" +
            "[AccountNumber]  ASC,\n" +
            "[picture]  ASC,\n" +
            "[reading_date]  ASC\n" +
            ")";


    public static String IndexMeterReadingRates = "CREATE UNIQUE INDEX index_rates ON rates (R_ID, RD_ID, rate_date_from)";

    public static String IndexMeterReadingRates2 = "CREATE UNIQUE INDEX [index_rates2] ON [rates2] ([r_id],[client],[billmonth],[consumption])";

    public static String IndexMeterReadingReading = "CREATE UNIQUE INDEX [index_reading] ON [reading](\n" +
            "[job_id]  ASC,\n" +
            "[Client]  ASC,\n" +
            "[C_ID]  ASC,\n" +
            "[AccountNumber]  ASC,\n" +
            "[meter_number]  ASC,\n" +
            "[Reading_Date]  ASC\n" +
            ")";

    public static String IndexMeterReadingMOA = "CREATE INDEX [m_account_number] ON [moa](\n" +
            "[accountnumber]  ASC\n" +
            ")";
    public static String DropCreateViewJobOrderDetails = "DROP VIEW IF  EXISTS vw_job_details";

    public  static String CreateViewJobOrderDetails =
            "CREATE VIEW vw_job_details as \n" +
            "SELECT c.*, ass.AccountNumber AS AccountNumber,\n" +
            "ass.Reader_ID AS Reader_ID,\n" +
            "ass.present_Reading AS m_reading,\n" +
            "ass.Present_Consumption AS m_consumption,\n" +
            "ass.Remarks AS m_remarks,\n" +
            "ass.Print_TimeStamp as Print_TimeStamp \n" +
            "FROM customer_reading_downloads c \n" +
            "LEFT JOIN reading ass \n" +
            "ON ass.AccountNumber = c.C_AccountNumber";

    public static String[] RatesDescriptionData = {
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('304','batelec2','2','2','CC/RSTC Refund','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('305','batelec2','3','2','CC/RSTC Refund','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('306','batelec2','2','2','CC/RSTC Refund','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('307','batelec2','3','2','CC/RSTC Refund','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('308','batelec2','2','2','CC/RSTC Refund','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('309','batelec2','3','2','CC/RSTC Refund','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('310','batelec2','2','5','Distribution','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('311','batelec2','2','5','Distribution','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('312','batelec2','2','5','Distribution','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('313','batelec2','2','3','Distribution Network Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('314','batelec2','3','3','Distribution Network Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('315','batelec2','5','3','Distribution Network Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('316','batelec2','2','3','Distribution Network Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('317','batelec2','3','3','Distribution Network Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('318','batelec2','5','3','Distribution Network Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('319','batelec2','2','3','Distribution Network Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('320','batelec2','3','3','Distribution Network Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('321','batelec2','5','3','Distribution Network Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('322','batelec2','2','5','Environmental Charges','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('323','batelec2','2','5','Environmental Charges','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('324','batelec2','2','5','Environmental Charges','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('325','batelec2','2','5','FIT-All (Renewable)','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('326','batelec2','2','5','FIT-All (Renewable)','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('327','batelec2','2','5','FIT-All (Renewable)','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('328','batelec2','2','5','Generation','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('329','batelec2','2','5','Generation','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('330','batelec2','2','5','Generation','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('331','batelec2','2','2','Generation Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('332','batelec2','2','2','Generation Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('333','batelec2','2','2','Generation Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('334','batelec2','2','5','GRAM/ICERA DAA VAT','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('335','batelec2','2','5','GRAM/ICERA DAA VAT','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('336','batelec2','2','5','GRAM/ICERA DAA VAT','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('337','batelec2','2','5','GRAM/ICERA DAA-ERC Order 6/20/17','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('338','batelec2','2','5','GRAM/ICERA DAA-ERC Order 6/20/17','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('339','batelec2','2','5','GRAM/ICERA DAA-ERC Order 6/20/17','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('340','batelec2','2','4','Life Rate Subsidy (discount)','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('341','batelec2','2','4','Life Rate Subsidy (discount)','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('342','batelec2','2','4','Life Rate Subsidy (discount)','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('343','batelec2','2','3','Metering Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('344','batelec2','4','3','Metering Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('345','batelec2','2','3','Metering Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('346','batelec2','4','3','Metering Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('347','batelec2','2','3','Metering Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('348','batelec2','4','3','Metering Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('349','batelec2','2','5','Missionary Electrification','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('350','batelec2','2','5','Missionary Electrification','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('351','batelec2','2','5','Missionary Electrification','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('352','batelec2','2','5','NPC Stranded Debts','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('353','batelec2','2','5','NPC Stranded Debts','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('354','batelec2','2','5','NPC Stranded Debts','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('355','batelec2','2','5','Others','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('356','batelec2','2','5','Others','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('357','batelec2','2','5','Others','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('358','batelec2','2','2','Power Act Reduction','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('359','batelec2','2','2','Power Act Reduction','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('360','batelec2','2','2','Power Act Reduction','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('361','batelec2','2','2','Prev. Years Adj. on Pwr Cost','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('362','batelec2','2','2','Prev. Years Adj. on Pwr Cost','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('363','batelec2','2','2','Prev. Years Adj. on Pwr Cost','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('364','batelec2','2','5','Real Property Tax','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('365','batelec2','2','5','Real Property Tax','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('366','batelec2','2','5','Real Property Tax','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('367','batelec2','2','3','Rein. Fund For Sustainable CAPEX','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('368','batelec2','2','3','Rein. Fund For Sustainable CAPEX','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('369','batelec2','2','3','Rein. Fund For Sustainable CAPEX','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('370','batelec2','2','3','Retail Electric Service Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('371','batelec2','4','3','Retail Electric Service Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('372','batelec2','2','3','Retail Electric Service Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('373','batelec2','4','3','Retail Electric Service Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('374','batelec2','2','3','Retail Electric Service Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('375','batelec2','4','3','Retail Electric Service Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('376','batelec2','2','4','Senior Citizen Subsidy (discount)','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('377','batelec2','2','4','Senior Citizen Subsidy (discount)','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('378','batelec2','2','4','Senior Citizen Subsidy (discount)','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('379','batelec2','2','5','Stranded Contract Cost','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('380','batelec2','2','5','Stranded Contract Cost','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('381','batelec2','2','5','Stranded Contract Cost','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('382','batelec2','2','2','System Loss','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('383','batelec2','2','5','System Loss','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('384','batelec2','2','2','System Loss','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('385','batelec2','2','5','System Loss','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('386','batelec2','2','2','System Loss','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('387','batelec2','2','5','System Loss','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('388','batelec2','2','5','Transmission','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('389','batelec2','2','5','Transmission','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('390','batelec2','2','5','Transmission','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('391','batelec2','2','2','Transmission Delivery Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('392','batelec2','3','2','Transmission Delivery Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('393','batelec2','2','2','Transmission Delivery Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('394','batelec2','3','2','Transmission Delivery Charge','3','Low Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('395','batelec2','2','2','Transmission Delivery Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('396','batelec2','3','2','Transmission Delivery Charge','2','High Voltage');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('398','batelec2','0','0','TRATE Transmission Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('399','batelec2','0','0','TRATE System Loss Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('401','batelec2','0','0','TRATE Supply Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('402','batelec2','0','0','TRATE Metering Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('397','batelec2','0','0','TRATE Generation Charge','1','Residential');",
            "INSERT INTO rates_description(RD_ID,RD_Client,R_Formula,R_Group,RD_Description,classification_id,classification) VALUES ('400','batelec2','0','0','TRATE Distribution Charge','1','Residential');",

    };
    public static String[] MeterReadingDeliveryRemarksData = {
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (0,'No Field Findings');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (39,'Slip Under the Door');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (40,'Clip On Gate');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (48,'Drop To Mailbox');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (49,'Drop To Gate');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (30,'SIGNED BY NEIGHBORS');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (31,'SIGNED BY RELATIVE');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (34,'REFUSED TO ACCEPT');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (35,'HOUSE VACANT');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (36,'HOUSE DEMOLISHED');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (37,'NOT ATTENDED(WHY)');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (38,'Flooded');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (41,'MR involved in accident');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (42,'House Burned');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (32,'Customer Refused to Sign');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (44,'Hostile');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (45,'No care of billing address');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (46,'Always Out');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (47,'Wrong Address');",
            "INSERT INTO ref_delivery_remarks(remarks_id,remarks_description) VALUES (50,'OTHERS');",
    };


    public static String[] DisconnectionRemarksData = {
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('31','WITH PAYMENT');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5C','ARROGANT CUSTOMER');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5D','REFUSED TO CLOSE');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('59','CANNOT LOCATE METER');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5B','WITH SICK/BABY/DEAD');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('58','WITH COMPLAINT');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5I','METER WITH PADLOCK');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('51','WITH PN');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('52','GOVERNMENT ACCT');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5H','BROKEN GATE VALVE');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('54','BURIED METER');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('53','METER IN PREMISES');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5N','NO METER');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5A','METER WITH CAGE');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5J','METER INSIDE PREMISE');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('23','DRUNK');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('56','NO GATE VALVE');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5F','ACCT UNDER PROGRAM');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5M','DISASTER AREA');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5K','NO RESIDENT');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5E','WITH DOG NEAR METER');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5L','DEMOLISHED AREA');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('22','CUST WANTS FISTFIGHT');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('55','MTR COVERED CEMENT');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('5G','FOUND DISCONNECTED');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('57','METER NOT UPDATED');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('21','WITH DEADLY WEAPON');",
            "INSERT INTO ref_disconnection_remarks(remarks_id,remarks_description) VALUES('0','NO FIELD FINDINGS');",

    };
    public static String DeleteRemarks = "DELETE FROM ref_remarks";
    public static String[] MeterReadingBaliwagWDRemarksData = {
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('11','NORMAL READING','AA');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('12','REALIGNMENT OF METER','AB');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('13','BROKEN GLASS METER','BGR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('14','LEAK BEFORE METER','LBM');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('15','BLURRED METER GLASS','BMR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('16','BALL VALVE LEAK','BVL');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('17','CLOSED','CTD');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('18','DIRTY WATER','DW');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('19','HIGH CONSUMPTION','HC');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('20','BELOW AVERAGE','IBA');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('21','ILLEGAL CONNECTION','ICI');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('22','INVERTED METER','IM');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('23','INSTALLATION OF METER STAND','IMS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('24','MAINLINE LEAK','ML');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('25','METER STAND LEAK','MSK');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('26','NEW METER','NM');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('27','NO TENANT','NT');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('28','NOT IN USE','NU');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('29','NO WATER','NW');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('30','OPEN','OTD');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('31','LOW PRESSURE','PI');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('32','FOR RECLASSIFICATION','RI');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('33','*FOR RELOCATION','RMN');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('34','REPLACEMENT OF METER STAND','RMS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('35','*REVERSE READING','RR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('36','SERVICE LINE LEAK','SL');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('37','STOP METER','SM');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('38','TAIL PIECE LEAK','TP');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('39','*BROKEN GLASS','BG');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('40','*BLURRED METER GLASS','BMG');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('41','*CONCRETE CAGE','CC');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('42','*FLOODED AREA','FA');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('43','*METER INSIDE PROPERTY','MIP');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('44','*METER NOT LOCATED','MNL');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('45','*STOLEN METER','ST');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('46','*TALL GRASS','TG');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('47','*TRANSFER OF SERVICE CONNECTION','TS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description,remarks_abbreviation) VALUES ('48','*WITH DUMP','WD');"

    };
    public static String[] MeterReadingMorePowerRemarksData = {
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('0','NO FIELD FINDINGS');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('11','*W/ WATER INSIDE');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('12','*HANGING METER');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('13','*BROKEN GLASS');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('15','*MTR INSIDE HOUSE');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('16','*VERY HIGH MTR');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('21','*BURNED METER');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('22','*STOP METER');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('23','*DEFECTIVE REGISTRY');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('26','*NO OCCUPANT');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('28','*WRAP AROUND MTR');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('115','SELDOMLY USE');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('116','NEW METER');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('117','NO MORE METER');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('118','BILLING IMPROBABLE');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('119','SEAL METER LINE OK');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('120','*HARD TO READ');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('121','METER BOARD ROTTEN');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('122','METER NOT USED');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('123','REFER TO INSPECTION SECTION');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('124','METER NUMBER NOT UPDATED');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('125','METER NOT IN READING LIST BUT IN FIELD');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('126','METER NOT LOCATED/METER IN READING LIST NOT ON FIELD');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('127','NOT VISITED');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('128','OTHER FINDINGS');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('129','ZERO READING');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('130','NEGATIVE READING');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('131','BILLING IMPROBABLE - INCREASE');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('132','BILLING IMPROBABLE - DECREASE');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES ('133','BILLING IMPROBABLE - ZERO');",

            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('0','NF','NO FIELD FINDINGS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('2','HM','HANGING METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('3','BG','BROKEN GLASS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('4','MP','METER INSIDE PREMISES');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('5','HI','HIGH METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('6','BM','BURNED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('7','DM','DEFECTIVE METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('8','NO','NO OCCUPANT');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('9','MW','METER WRAPPED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('10','CM','CHANGED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('11','UM','UNLOCATED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('12','HR','HARD TO READ');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('13','DB','DAMAGED METER BOARD');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('14','DS','DISCONNECTED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('15','FI','FOR INSPECTION');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('16','VH','VERY HIGH METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('17','OR','OTHER REMARKS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('18','UN','UNCLEAR METER NUMBER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('19','US','UNCLEAR SERIAL NUMBER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('20','MM','MISSING MORE SEAL');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('21','ME','MISSING ERC SEAL');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('22','TM','TRANSFERRED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('23','DR','DEFECTIVE DEMAND REGISTRY');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('24','WJ','WITH JUMPER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('25','HZ','HOLD BILL-ZERO');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('26','HN','HOLD BILL-NEGATIVE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('27','HD','HOLD BILL-DECREASE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('28','HC','HOLD BILL-INCREASE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_abbreviation,remarks_description) VALUES ('29','EM','ELEVATED METER CLUSTER');"

    };
    public static String[] MeterReadingIleco2RemarksData = {
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (0,'NO FIELD FINDINGS');",
//            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1,'STOP METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (2,'BROKEN GLASS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (5,'DEFECTIVE REGISTRY');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (7,'UNSYNCHRONIZED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (9,'LAST READING DISCO');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (14,'HANGING METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (15,'BLURRED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (16,'WATER INSIDE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (17,'NOT USED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1002,'DISCONNECTED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1003,'PULL OUT');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1004,'CLOSED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1005,'TRANSFER METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1007,'HIGH METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1008,'HARD TO READ');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1009,'BURNED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1010,'STOP METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (97,'CHANGED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (28,'WRAP AROUND');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (110,'BROWNOUT');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (115,'NO DISPLAY');",
    };
    public static String[] MeterReadingRemarksData = {
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (0,'NO FIELD FINDINGS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (1,'*NOT USED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (2,'LAST READING DISCO');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (3,'TRANSFER METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (4,'UNSYNCHRONIZED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (27,'DISCONNECTED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (23,'*DEFECTIVE REGISTRY');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (11,'*W/ WATER INSIDE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (22,'*STOP METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (9,'*CLOSED GATE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (102,'PULL OUT METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (97,'CHANGE METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (16,'*VERY HIGH MTR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (13,'*BROKEN GLASS');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (21,'*BURNED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (30,'SJUMPER/OUTSIDE CON');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (31,'SHUNTED MTR BASE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (32,'DISC ROTATING BACK');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (33,'BORED H/FOIM');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (34,'MSNG/DF SEAL/SRING');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (35,'*DPOA');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (37,'ROTATED GLASS CVR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (40,'ZERO READING');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (42,'*OBS MTR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (44,'NUM ERASD/UNREADBLE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (45,'READ BY CUSTOMER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (46,'MSSNG/BRKN DMAND S');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (57,'*N POWER N DISP');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (58,'UR-FLOODED');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (62,'DEF DEMAND RESETTR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (67,'*LOOSE PARTS IN MTR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (68,'DEMAND NOT RESET');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (10,'*WITH DOG');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (12,'*HANGING METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (14,'*WITH MOIST');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (15,'*MTR INSIDE HOUSE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (17,'*TILTED METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (18,'*SWINGING MTR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (20,'*RUSTY OLD MTR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (24,'*CREEPING METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (25,'*FAST METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (26,'*NO OCCUPANT');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (28,'*WRAP AROUND MTR');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (100,'METER NOT FOUND');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (101,'CANNOT LOCATE');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (98,'WRONG METER');",
            "INSERT INTO ref_remarks(remarks_id,remarks_description) VALUES (99,'*NO METER');",
    };

    //End Meter Reading
    public static String[] ProductCategoryListData = {
            "INSERT INTO product_category(id,description) VALUES ('1','Sparkling');",
            "INSERT INTO product_category(id,description) VALUES ('2','Water');",
            "INSERT INTO product_category(id,description) VALUES ('3','RTD Juice');",
            "INSERT INTO product_category(id,description) VALUES ('4','RTD Tea');",
            "INSERT INTO product_category(id,description) VALUES ('5','Sports');",
            "INSERT INTO product_category(id,description) VALUES ('6','Powdered Juice');",
            "INSERT INTO product_category(id,description) VALUES ('7','Powdered Tea');"
    };
    public static String[] ProductSoviListData = {
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('1','Coke','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('2','Coke Light','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('3','Coke Zero','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('4','Sprite','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('5','Sprite Zero','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('6','Royal Orange','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('7','Royal Grape','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('8','Sarsi','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('9','Schweppes Soda','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('10','Schweppes Tonic','Sparkling','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('11','Wilkins','Packaged Water','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('12','Wilkins Pure','Packaged Water','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('13','Viva Mineral','Packaged Water','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('14','Minute Maid Fresh Orange','RTD Juice','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('15','Minute Maid Orange','RTD Juice','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('16','Minute Maid Mango Orange','RTD Juice','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('17','Minute Maid Four Seasons','RTD Juice','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('18','Powerade Mountain Blast','Sports Drink','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('19','Powerade Orange','Sports Drink','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('20','Powerade Berry Ice','Sports Drink','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('21','Powerade Silver Charge','Sports Drink','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('22','Real Leaf Frutcy Lemon Ice','RTD Tea','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('23','Real Leaf Frutcy Lemon','RTD Tea','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('24','Real Leaf Honey Apple','RTD Tea','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('25','Real Leaf Honey Lemon','RTD Tea','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('26','Real Leaf Frutcy Apple','RTD Tea','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('27','Real Leaf Honey Lychee','RTD Tea','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('28','Real Leaf Frutcy Calamansi','RTD Tea','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('29','EOC Orange','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('30','EOC Pineapple','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('31','EOC Orange Mango','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('32','EOC Mango','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('33','EOC Lemon Tea','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('34','EOC Apple Tea','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('35','EOC Strawberry','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('36','EOC Grape','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('37','EOC Ponkana','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('58','Mountain Dew','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('59','Pepsi','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('60','7 UP','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('61','MUG','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('62','Zesto fruit Soda','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('63','Mirinda','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('64','RC Cola','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('65','RC COLA FREE','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('66','Pepsi Max','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('67','Diet Pepsi and Pepsi Light','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('68','Fruit Soda','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('69','Nature Spring Purified Water','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('70','Absolute','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('71','Summit','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('72','Refresh','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('73','Wet Water','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('74','Blue','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('75','Premier','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('76','Private Label','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('77','Aqua Sweet','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('78','Vital','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('79','EVIAN','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('80','HOPE IN A BOTTLE','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('81','Gatorade','Sports Drink','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('82','Pocari Sweat','Sports Drink','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('83','Sun Coast','Sports Drink','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('84','100 PLUS','Sports Drink','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('85','BIG 250','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('86','Del Monte','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('87','Zesto','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('88','PLUS','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('89','Tropicana Twister','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('90','Refresh','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('91','OK','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('92','Fit N Right','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('93','Smart C+','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('94','Mogu Mogu','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('95','DOLE','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('96','SUNKIST','RTD Juice','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('97','Lipton','RTD Tea','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('98','C2','RTD Tea','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('99','Tang','Powders','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('100','Nesfruta','Powders','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('101','Oishi Sundays','Powders','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('102','My Juiz','Powders','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('103','Nestea','Powders','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('104','Wilkins Delight','Packaged Water','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('105','Minute Maid Fresh Apple','RTD Juice','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('106','ARCYS MUG','Sparkling','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('107','EOC Calamansi','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('108','EOC Dalandan','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('109','EOC Guyabano','Powders','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('110','Viva Pure','Packaged Water','KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('111','Aquafina','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('112','G-Active','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('113','Nestea','RTD Tea','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('114','Del Monte F&R Active','Sports Drink','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('115','Nature Spring Distilled Water','Packaged Water','NON-KOF');",
            "INSERT INTO sovi_product(id,description,product,type) VALUES ('116','Le Minerale','Packaged Water','NON-KOF');",

    };


    public static String[] ProductListData = {
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101604','COKE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102032','COKE 1250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1250 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101650','COKE 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103652','COKE 1750 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101683','COKE 200 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','200 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101640','Coke 2000 ml NR x 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101588','COKE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105331','COKE 237 ML X 24 TIMEOUT',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104473','COKE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101620','COKE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101592','COKE 355 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104896','COKE 400 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','400 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101667','COKE 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101596','COKE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102277','COKE 750 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101822','COKE BIB 5.28 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','5.28 gal',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101659','COKE LIGHT 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101639','COKE LIGHT 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101711','COKE LIGHT 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101682','COKE LIGHT 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101655','COKE ZERO 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101637','COKE ZERO 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101703','COKE ZERO 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101677','COKE ZERO 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101821','COKE ZERO BIB 2.64 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','2.64 gal',NULL,'Sparkling','Multi-Serve');",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101914','EOC APPLE TEA 25 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','25 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100079','EOC Apple Tea 400 g x 24',NULL,NULL,NULL,NULL,NULL,'POUCH','400 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101765','EOC APPLE TEA 800 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','800 g',NULL,'Powdered Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100064','EOC Grape 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104507','EOC GRAPE 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101915','EOC LEMON TEA 25 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','25 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100078','EOC Lemon Tea 400 g x 24',NULL,NULL,NULL,NULL,NULL,'POUCH','400 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101912','EOC LEMON TEA 800 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','800 g',NULL,'Powdered Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104816','EOC MANGO 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100060','EOC Mango 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102352','EOC MANGO 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104813','EOC MANGO 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104807','EOC ORANGE 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105366','EOC ORANGE 30 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102547','EOC ORANGE 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104808','EOC ORANGE 350 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','350 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104806','EOC ORANGE 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104814','EOC ORANGE MANGO 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100059','EOC Orange Mango 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102353','EOC ORANGE MANGO 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104818','EOC ORANGE MANGO 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104811','EOC PINEAPPLE 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100058','EOC Pineapple 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102350','EOC PINEAPPLE 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104810','EOC PINEAPPLE 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104815','EOC PONKANA 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100065','EOC Ponkana 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104817','EOC PONKANA 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100150','EOC PONKANA 350 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','350 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104812','EOC PONKANA 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102351','EOC STRAWBERRY 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103914','MINUTE MAID FOUR SEASONS 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103913','MINUTE MAID FOUR SEASONS 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100037','Minute Maid Fresh Orange 200 ml x 10',NULL,NULL,NULL,NULL,NULL,'Tetra','200 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104552','MINUTE MAID FRESH ORANGE 250 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105343','MINUTE MAID FRESH ORANGE 800 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','800 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104228','MINUTE MAID MANGO ORANGE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104234','MINUTE MAID MANGO ORANGE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102525','MINUTE MAID ORANGE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102527','MINUTE MAID ORANGE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'RTD Juice','Single Serve');",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101627','POP COLA 240 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','240 ml',NULL,'Sparkling','Single Serve');",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101660','POP COLA 800 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','800 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100048','POWERADE BERRY ICE 500 ml x 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100502','POWERADE MTN BERRY 250 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101693','POWERADE MTN BLAST 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101695','POWERADE MTN BLAST SPORTS 500 ML 2X24 MP',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101692','POWERADE ORANGE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105151','POWERADE SILVER CHARGE FLAT 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103887','POWERADE SILVER CHARGE SPORTS 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103967','REAL LEAF FRUTCY APPLE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103963','REAL LEAF FRUTCY CALAMANSI 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103964','REAL LEAF FRUTCY LEMON 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103962','REAL LEAF FRUTCY LEMON ICE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100501','REAL LEAF FRUTCY SWEET TEA 250 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103957','REAL LEAF HONEY APPLE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103952','REAL LEAF HONEY LEMON 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','103958','REAL LEAF HONEY LYCHEE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101625','ROYAL GRAPE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104791','ROYAL ORANGE 1000 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101605','ROYAL ORANGE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101651','ROYAL ORANGE 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100092','ROYAL ORANGE 1750 ML',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101635','ROYAL ORANGE 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101589','ROYAL ORANGE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104955','ROYAL ORANGE 250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104954','ROYAL ORANGE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101621','ROYAL ORANGE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101593','ROYAL ORANGE 355 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101669','ROYAL ORANGE 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101597','ROYAL ORANGE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104238','ROYAL ORANGE 750 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101823','ROYAL ORANGE BIB 5.28 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','5.28 gal',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102407','ROYAL ORANGE TALL 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104488','ROYAL RAINBOW CASE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','106189','ROYAL WATTAMELON 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','106185','ROYAL WATTAMELON 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101628','SARSI 240 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','240 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105311','SARSI 250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101700','SARSI 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101710','SCHWEPPES SODA 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101709','SCHWEPPES TONIC 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101633','SPARKLE 240 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','240 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105506','SPARKLE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105315','SPARKLE 355 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101661','SPARKLE 800 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','800 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104785','SPRITE 1000 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101608','SPRITE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101653','SPRITE 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100091','Sprite 1750 ML',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101636','SPRITE 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101590','SPRITE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104956','SPRITE 250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104952','SPRITE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101622','SPRITE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101594','SPRITE 355 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104892','SPRITE 400 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','400 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101668','SPRITE 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101598','SPRITE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104237','SPRITE 750 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101824','SPRITE BIB 5.28 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','5.28 gal',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102408','SPRITE TALL 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101624','SPRITE ZERO 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100035','VIVA MINERAL 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101874','VIVA MINERAL 330 ML X 30',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101862','VIVA MINERAL 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','106125','WILKINS 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100030','Wilkins 1500 ml x 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100027','Wilkins 330 ml x 30',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102008','WILKINS 4000 ML X 4',NULL,NULL,NULL,NULL,NULL,'PET','4000 ml',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','101319','WILKINS 5 GAL',NULL,NULL,NULL,NULL,NULL,'PET','5 gal',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','105058','WILKINS 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','102007','WILKINS 6000 ML X 3',NULL,NULL,NULL,NULL,NULL,'PET','6000 ml',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100088','WILKINS DELIGHT APPLE 250 ML',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100094','WILKINS DELIGHT APPLE 425 ML',NULL,NULL,NULL,NULL,NULL,'PET','425 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100090','WILKINS DELIGHT ORANGE 250 ML',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100095','WILKINS DELIGHT ORANGE 425 ML',NULL,NULL,NULL,NULL,NULL,'PET','425 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100089','WILKINS DELIGHT PINK POMELO 250 ML',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100096','WILKINS DELIGHT PINK POMELO 425 ML',NULL,NULL,NULL,NULL,NULL,'PET','425 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104201','WILKINS PURE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104784','WILKINS PURE 330 ML X 30',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','104200','WILKINS PURE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101604','COKE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102032','COKE 1250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1250 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101650','COKE 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103652','COKE 1750 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101683','COKE 200 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','200 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101640','Coke 2000 ml NR x 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101588','COKE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105331','COKE 237 ML X 24 TIMEOUT',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104473','COKE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101620','COKE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101592','COKE 355 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104896','COKE 400 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','400 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101667','COKE 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101596','COKE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102277','COKE 750 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101822','COKE BIB 5.28 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','5.28 gal',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101659','COKE LIGHT 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101639','COKE LIGHT 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101711','COKE LIGHT 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101682','COKE LIGHT 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101655','COKE ZERO 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101637','COKE ZERO 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101703','COKE ZERO 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101677','COKE ZERO 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101821','COKE ZERO BIB 2.64 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','2.64 gal',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101914','EOC APPLE TEA 25 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','25 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100079','EOC Apple Tea 400 g x 24',NULL,NULL,NULL,NULL,NULL,'POUCH','400 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101765','EOC APPLE TEA 800 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','800 g',NULL,'Powdered Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100064','EOC Grape 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104507','EOC GRAPE 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101915','EOC LEMON TEA 25 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','25 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100078','EOC Lemon Tea 400 g x 24',NULL,NULL,NULL,NULL,NULL,'POUCH','400 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101912','EOC LEMON TEA 800 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','800 g',NULL,'Powdered Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104816','EOC MANGO 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100060','EOC Mango 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102352','EOC MANGO 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104813','EOC MANGO 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104807','EOC ORANGE 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105366','EOC ORANGE 30 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102547','EOC ORANGE 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104808','EOC ORANGE 350 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','350 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104806','EOC ORANGE 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104814','EOC ORANGE MANGO 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100059','EOC Orange Mango 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102353','EOC ORANGE MANGO 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104818','EOC ORANGE MANGO 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104811','EOC PINEAPPLE 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100058','EOC Pineapple 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102350','EOC PINEAPPLE 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104810','EOC PINEAPPLE 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104815','EOC PONKANA 175 G X 24',NULL,NULL,NULL,NULL,NULL,'POUCH','175 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100065','EOC Ponkana 30 g x 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104817','EOC PONKANA 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100150','EOC PONKANA 350 G X 12',NULL,NULL,NULL,NULL,NULL,'POUCH','350 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104812','EOC PONKANA 87.5 G X 48',NULL,NULL,NULL,NULL,NULL,'POUCH','87.5 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102351','EOC STRAWBERRY 35 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','35 g',NULL,'Powdered Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103914','MINUTE MAID FOUR SEASONS 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103913','MINUTE MAID FOUR SEASONS 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104552','MINUTE MAID FRESH ORANGE 250 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105343','MINUTE MAID FRESH ORANGE 800 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','800 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104228','MINUTE MAID MANGO ORANGE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104234','MINUTE MAID MANGO ORANGE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102525','MINUTE MAID ORANGE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'RTD Juice','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102527','MINUTE MAID ORANGE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'RTD Juice','Single Serve');",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101627','POP COLA 240 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','240 ml',NULL,'Sparkling','Single Serve');",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101660','POP COLA 800 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','800 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100048','POWERADE BERRY ICE 500 ml x 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100502','POWERADE MTN BERRY 250 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101693','POWERADE MTN BLAST 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101695','POWERADE MTN BLAST SPORTS 500 ML 2X24 MP',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101692','POWERADE ORANGE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105151','POWERADE SILVER CHARGE FLAT 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103887','POWERADE SILVER CHARGE SPORTS 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103967','REAL LEAF FRUTCY APPLE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103963','REAL LEAF FRUTCY CALAMANSI 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103964','REAL LEAF FRUTCY LEMON 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103962','REAL LEAF FRUTCY LEMON ICE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100501','REAL LEAF FRUTCY SWEET TEA 250 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103957','REAL LEAF HONEY APPLE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103952','REAL LEAF HONEY LEMON 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','103958','REAL LEAF HONEY LYCHEE 480 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','480 ml',NULL,'RTD Tea','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101625','ROYAL GRAPE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104791','ROYAL ORANGE 1000 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101605','ROYAL ORANGE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101651','ROYAL ORANGE 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100092','ROYAL ORANGE 1750 ML',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101635','ROYAL ORANGE 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101589','ROYAL ORANGE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104955','ROYAL ORANGE 250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104954','ROYAL ORANGE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101621','ROYAL ORANGE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101593','ROYAL ORANGE 355 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101669','ROYAL ORANGE 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101597','ROYAL ORANGE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104238','ROYAL ORANGE 750 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101823','ROYAL ORANGE BIB 5.28 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','5.28 gal',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102407','ROYAL ORANGE TALL 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104488','ROYAL RAINBOW CASE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106189','ROYAL WATTAMELON 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106185','ROYAL WATTAMELON 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101628','SARSI 240 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','240 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105311','SARSI 250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101700','SARSI 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101710','SCHWEPPES SODA 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101709','SCHWEPPES TONIC 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101633','SPARKLE 240 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','240 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105506','SPARKLE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105315','SPARKLE 355 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101661','SPARKLE 800 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','800 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104785','SPRITE 1000 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101608','SPRITE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','1000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101653','SPRITE 1500 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100091','Sprite 1750 ML',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101636','SPRITE 2000 ML NR X 8',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101590','SPRITE 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104956','SPRITE 250 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104952','SPRITE 300 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','300 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101622','SPRITE 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101594','SPRITE 355 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','355 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104892','SPRITE 400 ML NR X 12',NULL,NULL,NULL,NULL,NULL,'PET','400 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101668','SPRITE 500 ML NR X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101598','SPRITE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','500 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104237','SPRITE 750 ML X 12',NULL,NULL,NULL,NULL,NULL,'RGB','750 ml',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101824','SPRITE BIB 5.28 GAL',NULL,NULL,NULL,NULL,NULL,'FTN','5.28 gal',NULL,'Sparkling','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102408','SPRITE TALL 237 ML X 24',NULL,NULL,NULL,NULL,NULL,'RGB','237 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101624','SPRITE ZERO 330 ML X 24',NULL,NULL,NULL,NULL,NULL,'CANS','330 ml',NULL,'Sparkling','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100035','VIVA MINERAL 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101874','VIVA MINERAL 330 ML X 30',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101862','VIVA MINERAL 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106125','WILKINS 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100030','Wilkins 1500 ml x 12',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100027','Wilkins 330 ml x 30',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102008','WILKINS 4000 ML X 4',NULL,NULL,NULL,NULL,NULL,'PET','4000 ml',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','101319','WILKINS 5 GAL',NULL,NULL,NULL,NULL,NULL,'PET','5 gal',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105058','WILKINS 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','102007','WILKINS 6000 ML X 3',NULL,NULL,NULL,NULL,NULL,'PET','6000 ml',NULL,'Packaged Water','Multi-Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100088','WILKINS DELIGHT APPLE 250 ML',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100094','WILKINS DELIGHT APPLE 425 ML',NULL,NULL,NULL,NULL,NULL,'PET','425 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100090','WILKINS DELIGHT ORANGE 250 ML',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100095','WILKINS DELIGHT ORANGE 425 ML',NULL,NULL,NULL,NULL,NULL,'PET','425 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100089','WILKINS DELIGHT PINK POMELO 250 ML',NULL,NULL,NULL,NULL,NULL,'PET','250 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100096','WILKINS DELIGHT PINK POMELO 425 ML',NULL,NULL,NULL,NULL,NULL,'PET','425 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104201','WILKINS PURE 1000 ML X 12',NULL,NULL,NULL,NULL,NULL,'PET','1000 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104784','WILKINS PURE 330 ML X 30',NULL,NULL,NULL,NULL,NULL,'PET','330 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','104200','WILKINS PURE 500 ML X 24',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('GT','100151','MINUTE MAID FRESH APPLE 200 ML X 10',NULL,NULL,NULL,NULL,NULL,'Tetra','200 ml',NULL,'RTD Juice','Single Served');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100151','MINUTE MAID FRESH APPLE 200 ML X 10',NULL,NULL,NULL,NULL,NULL,'Tetra','200 ml',NULL,'RTD Juice','Single Served');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','100037','Minute Maid Fresh Orange 200 ml x 10',NULL,NULL,NULL,NULL,NULL,'Tetra','200 ml',NULL,'RTD Juice','Single Serve');",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001000','Gatorade Orange Chill 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001001','Gatorade Blue Bolt 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001002','Gatorade white lightning 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001003','Gatorade Tropical Fruit 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001004','Gatorade Grape 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001005','Gatorade Lemon Lime 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sports Drink',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001006','Blue Calamansi 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001007','Blue Lychee 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001008','Blue Orange 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001009','Blue Pear 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001010','Blue Peach 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001011','Nesfruta Dalandan 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001012','Nesfruta Orange 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001013','Nesfruta Melon 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001014','Nesfruta Buko 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001015','Nesfruta Guyabano 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001016','Nesfruta Mangoosten 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001017','Tang Grape 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001018','Tang Calamansi 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001019','Tang Dalandan 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001020','Tang Guyabano 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001021','Tang Lemon 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001022','Tang Mango 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001023','Tang Orange 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001024','Tang Orange Mango 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001025','Tang Pineapple 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001026','Tang Ponkan 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001027','Tang Strawberry 25/30g',NULL,NULL,NULL,NULL,NULL,'Sachet','25/30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001028','Pepsi 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001029','Mt Dew 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001030','Mirinda 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001031','Zesto Soda Dalandan 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001032','RC Cola 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001033','Pepsi 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001034','Mt Dew 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001035','Mirinda 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001036','Zesto Soda Dalandan 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001037','RC Cola 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001038','Pepsi 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001039','Mt Dew 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001040','Mirinda 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001041','Zesto Soda Dalandan 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001042','RC Cola 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001043','Pepsi 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001044','Mt Dew 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001045','Mirinda 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001046','Zesto Soda Dalandan 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001047','RC Cola 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001048','Pepsi 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001049','Mt Dew 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001050','Mirinda 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001051','Zesto Soda Dalandan 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001052','RC Cola 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001053','Coke 2000 ml NR x 2 x 4',NULL,NULL,NULL,NULL,NULL,'Multipack PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','107157','COKE + COKE ZERO 2000 ML NR X 2 X 4 MP',NULL,NULL,NULL,NULL,NULL,'Multipack PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','107158','COKE + RTO 2000 ML NR X 2 X 4 MP',NULL,NULL,NULL,NULL,NULL,'Multipack PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','107159','COKE + SPRITE 2000 ML NR X 2 X 4 MP',NULL,NULL,NULL,NULL,NULL,'Multipack PET','2000 ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106351','EOC CALAMANSI 25 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','25 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106352','EOC DALANDAN 25 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','25 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106350','EOC GUYABANO 25 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','25 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105783','EOC STRAWBERRY 30 G X 120',NULL,NULL,NULL,NULL,NULL,'SACHET','30 g',NULL,'Powdered Juice',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106844','VIVA PURE 4000 ML X 4',NULL,NULL,NULL,NULL,NULL,'PET','4000 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106852','VIVA PURE 6000 ML X 3',NULL,NULL,NULL,NULL,NULL,'PET','6000 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105773','WILKINS 5000 ML X 4',NULL,NULL,NULL,NULL,NULL,'PET','5000 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','105774','WILKINS 7000 ML X 3',NULL,NULL,NULL,NULL,NULL,'PET','7000 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106451','WILKINS PURE 330 ML X 6 X 5 MP',NULL,NULL,NULL,NULL,NULL,'Multipack PET','330 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','106535','WILKINS PURE 500 ML X 6 X 4 MP',NULL,NULL,NULL,NULL,NULL,'Multipack PET','500 ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001054','Zesto Soda Calamansi 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001059','Zesto Soda Lemon 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001064','Zesto Soda Orange 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001069','Zesto Soda Pomelo 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001074','Zesto Soda RootBeer 330ml',NULL,NULL,NULL,NULL,NULL,'Cans','330ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001055','Zesto Soda Calamansi 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001060','Zesto Soda Lemon 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001065','Zesto Soda Orange 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001070','Zesto Soda Pomelo 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001075','Zesto Soda RootBeer 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001056','Zesto Soda Calamansi 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001061','Zesto Soda Lemon 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001066','Zesto Soda Orange 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001071','Zesto Soda Pomelo 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001076','Zesto Soda RootBeer 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001057','Zesto Soda Calamansi 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001062','Zesto Soda Lemon 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001067','Zesto Soda Orange 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001072','Zesto Soda Pomelo 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001077','Zesto Soda RootBeer 1750ml',NULL,NULL,NULL,NULL,NULL,'PET','1750ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001058','Zesto Soda Calamansi 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001063','Zesto Soda Lemon 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001068','Zesto Soda Orange 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001073','Zesto Soda Pomelo 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001078','Zesto Soda RootBeer 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000ml',NULL,'Sparkling',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001079','C2 Green Tea Classic 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'RTD TEA',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001080','C2 Green Tea Apple 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'RTD TEA',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001081','C2 Green Tea Lemon 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'RTD TEA',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001082','C2 Red Tea Raspberry 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'RTD TEA',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001083','C2 Iced Tea Lemon 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'RTD TEA',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','20001084','C2 Green Tea Peach 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'RTD TEA',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010856','Nature Spring Purified Water 330ml',NULL,NULL,NULL,NULL,NULL,'PET','330ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010857','Nature Spring Purified Water 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010858','Nature Spring Purified Water 1L',NULL,NULL,NULL,NULL,NULL,'PET','1L',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010859','Nature Spring Purified Water 4 Gal',NULL,NULL,NULL,NULL,NULL,'PET','4G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010860','Nature Spring Purified Water 5 Gal',NULL,NULL,NULL,NULL,NULL,'PET','5G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010861','Nature Spring Purified Water 6 Gal',NULL,NULL,NULL,NULL,NULL,'PET','6G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010862','Nature Spring Distilled Water 330ml',NULL,NULL,NULL,NULL,NULL,'PET','330ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010863','Nature Spring Distilled Water 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010864','Nature Spring Distilled Water 1L',NULL,NULL,NULL,NULL,NULL,'PET','1L',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010865','Nature Spring Distilled Water 4 Gal',NULL,NULL,NULL,NULL,NULL,'PET','4G',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010866','Nature Spring Distilled Water 5 Gal',NULL,NULL,NULL,NULL,NULL,'PET','5G',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010867','Nature Spring Distilled Water 6 Gal',NULL,NULL,NULL,NULL,NULL,'PET','6G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010868','Absolute 330ml',NULL,NULL,NULL,NULL,NULL,'PET','330ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010869','Absolute 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010870','Absolute 1L',NULL,NULL,NULL,NULL,NULL,'PET','1L',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010871','Absolute 4 Gal',NULL,NULL,NULL,NULL,NULL,'PET','4G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010872','Absolute 5 Gal',NULL,NULL,NULL,NULL,NULL,'PET','5G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010873','Absolute 6 Gal',NULL,NULL,NULL,NULL,NULL,'PET','6G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010874','Summit 330ml',NULL,NULL,NULL,NULL,NULL,'PET','330ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010875','Summit 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010876','Summit 1L',NULL,NULL,NULL,NULL,NULL,'PET','1L',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010877','Summit 4 Gal',NULL,NULL,NULL,NULL,NULL,'PET','4G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010878','Summit 5 Gal',NULL,NULL,NULL,NULL,NULL,'PET','5G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010879','Summit 6 Gal',NULL,NULL,NULL,NULL,NULL,'PET','6G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010880','Evian 330ml',NULL,NULL,NULL,NULL,NULL,'PET','330ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010881','Evian 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010882','Evian 1L',NULL,NULL,NULL,NULL,NULL,'PET','1L',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010883','Evian 4 Gal',NULL,NULL,NULL,NULL,NULL,'PET','4G',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010884','Evian 5 Gal',NULL,NULL,NULL,NULL,NULL,'PET','5G',NULL,'Packaged Water',NULL);",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010885','Evian 6 Gal',NULL,NULL,NULL,NULL,NULL,'PET','6G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010886','Le Minerale 330ml',NULL,NULL,NULL,NULL,NULL,'PET','330ml',NULL,'Packaged Water',NULL);",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010887','Le Minerale 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010888','Le Minerale 1L',NULL,NULL,NULL,NULL,NULL,'PET','1L',NULL,'Packaged Water',NULL);",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010889','Le Minerale 4 Gal',NULL,NULL,NULL,NULL,NULL,'PET','4G',NULL,'Packaged Water',NULL);",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010890','Le Minerale 5 Gal',NULL,NULL,NULL,NULL,NULL,'PET','5G',NULL,'Packaged Water',NULL);",
            //"INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010891','Le Minerale 6 Gal',NULL,NULL,NULL,NULL,NULL,'PET','6G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010892','Aquafina 330ml',NULL,NULL,NULL,NULL,NULL,'PET','330ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010893','Aquafina 500ml',NULL,NULL,NULL,NULL,NULL,'PET','500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010894','Aquafina 1L',NULL,NULL,NULL,NULL,NULL,'PET','1L',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010895','Aquafina 4 Gal',NULL,NULL,NULL,NULL,NULL,'PET','4G',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010896','Aquafina 5 Gal',NULL,NULL,NULL,NULL,NULL,'PET','5G',NULL,'Packaged Water',NULL);",
            // "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010897','Aquafina 6 Gal',NULL,NULL,NULL,NULL,NULL,'PET','6G',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010858','Nature Spring Purified Water 1000ml',NULL,NULL,NULL,NULL,NULL,'PET','1000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010864','Nature Spring Distilled Water 1000ml',NULL,NULL,NULL,NULL,NULL,'PET','1000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010870','Absolute 1000ml',NULL,NULL,NULL,NULL,NULL,'PET','1000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010876','Summit 1000ml',NULL,NULL,NULL,NULL,NULL,'PET','1000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010882','Evian 1000ml',NULL,NULL,NULL,NULL,NULL,'PET','1000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010888','Le Minerale 1000ml',NULL,NULL,NULL,NULL,NULL,'PET','1000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010894','Aquafina 1000ml',NULL,NULL,NULL,NULL,NULL,'PET','1000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010898','Absolute 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010899','Absolute 6000ml',NULL,NULL,NULL,NULL,NULL,'PET','6000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010900','Absolute 4000ml',NULL,NULL,NULL,NULL,NULL,'PET','4000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010901','Aquafina 350ml',NULL,NULL,NULL,NULL,NULL,'PET','350ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010902','Evian 750ml',NULL,NULL,NULL,NULL,NULL,'PET','750ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010903','Evian 1250ml',NULL,NULL,NULL,NULL,NULL,'PET','1250ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010904','Le Minerale 600ml',NULL,NULL,NULL,NULL,NULL,'PET','600ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010905','Nature Spring Distilled Water 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010906','Nature Spring Distilled Water 6000ml',NULL,NULL,NULL,NULL,NULL,'PET','6000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010907','Nature Spring Distilled Water 8000ml',NULL,NULL,NULL,NULL,NULL,'PET','8000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010908','Nature Spring Purified Water 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010909','Nature Spring Purified Water 6000ml',NULL,NULL,NULL,NULL,NULL,'PET','6000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010910','Nature Spring Purified Water 8000ml',NULL,NULL,NULL,NULL,NULL,'PET','8000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010911','Summit 350ml',NULL,NULL,NULL,NULL,NULL,'PET','350ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010912','Summit 1500ml',NULL,NULL,NULL,NULL,NULL,'PET','1500ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010913','Summit 4000ml',NULL,NULL,NULL,NULL,NULL,'PET','4000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010914','Summit 6000ml',NULL,NULL,NULL,NULL,NULL,'PET','6000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010915','Summit 2000ml',NULL,NULL,NULL,NULL,NULL,'PET','2000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010916','Absolute 5000ml',NULL,NULL,NULL,NULL,NULL,'PET','5000ml',NULL,'Packaged Water',NULL);",
            "INSERT INTO products(category,productCode,productName,articleno,cokeonematerialno,articlename,itembarcode,casebarcode,productType,size,uom,productCategory,typeDescription) VALUES ('MT','200010917','Absolute 350ml',NULL,NULL,NULL,NULL,NULL,'PET','350ml',NULL,'Packaged Water',NULL);",





    };

    public static String[] SettingsData = {
            "INSERT INTO ums_settings(Id,ReverseInput,HideKeyboard,sysentrydate,modifieddate,modifiedby) VALUES ('0','0','0','0','0','0');",
    };

    public static String[] DeliveryRemarksListData = {
            "INSERT INTO delivery_remarks(id,description) VALUES (39,'Slip Under the Door');",
            "INSERT INTO delivery_remarks(id,description) VALUES (40,'Clip On Gate');",
            "INSERT INTO delivery_remarks(id,description) VALUES (48,'Drop To Mailbox');",
            "INSERT INTO delivery_remarks(id,description) VALUES (49,'Drop To Gate');",
            "INSERT INTO delivery_remarks(id,description) VALUES (30,'SIGNED BY NEIGHBORS');",
            "INSERT INTO delivery_remarks(id,description) VALUES (31,'SIGNED BY RELATIVE');",
            "INSERT INTO delivery_remarks(id,description) VALUES (32,'CANNOT LOCATE');",
            "INSERT INTO delivery_remarks(id,description) VALUES (33,'OUT OF ROUTE');",
            "INSERT INTO delivery_remarks(id,description) VALUES (34,'REFUSED TO ACCEPT');",
            "INSERT INTO delivery_remarks(id,description) VALUES (35,'HOUSE VACANT');",
            "INSERT INTO delivery_remarks(id,description) VALUES (36,'HOUSE DEMOLISHED');",
            "INSERT INTO delivery_remarks(id,description) VALUES (37,'NOT ATTENDED(WHY)');",
            "INSERT INTO delivery_remarks(id,description) VALUES (38,'Flooded');",
            "INSERT INTO delivery_remarks(id,description) VALUES (41,'Messenger involved in accident');",
            "INSERT INTO delivery_remarks(id,description) VALUES (42,'House Burned');",
            "INSERT INTO delivery_remarks(id,description) VALUES (43,'Customer Refused to Sign');",
            "INSERT INTO delivery_remarks(id,description) VALUES (44,'Hostile');",
            "INSERT INTO delivery_remarks(id,description) VALUES (45,'No care of billing address');",
            "INSERT INTO delivery_remarks(id,description) VALUES (46,'Always Out');",
            "INSERT INTO delivery_remarks(id,description) VALUES (47,'Wrong Address');",
            "INSERT INTO delivery_remarks(id,description) VALUES (50,'OTHERS');",
            "INSERT INTO delivery_remarks(id,description) VALUES (0,'No Field Findings');",
            "INSERT INTO delivery_remarks(id,description) VALUES (51,'Lack of Time');",
    };

}
