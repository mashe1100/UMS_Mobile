package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.data.DatabaseHelper;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.HostModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.Speech;


public class SplashActivity extends AppCompatActivity {
    /** Duration of wait **/
    private static final String TAG = SplashActivity.class.getSimpleName();
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    public static DatabaseHelper mDatabaseHelper;


    private Liquid.ApiDataField mApiData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init(){
        Liquid.LiquidPackageName = getApplicationContext().getPackageName();

        mDatabaseHelper = new DatabaseHelper(this);
        setupImage();
        Host();

         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
 //              Intent mainIntent = new Intent(SplashActivity.this,ReceiverActivity.class);
                //Intent mainIntent = new Intent(SplashActivity.this,QRCodeActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    //Mariesher on Feb 15, 2020
    //Create a function to control column to get hostname from ums_host table
    public static String GetHost(String host) {
        //get the current host
        Cursor hostData = HostModel.GetHostID(Liquid.id);
        if(hostData.getCount() == 0){
            return null;
        }

        while(hostData.moveToNext()){

            Liquid.Username = hostData.getString(1);
            Liquid.Password = hostData.getString(2);
            host = hostData.getString(3);
        }

        Log.i(TAG,"mashe test host in splash: "+ host);
        return host;
    }

    public static void Host(){
        try{
            Liquid.umsUrl = GetHost(Liquid.currenthost);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void setupImage(){

        /*Liquid.LiquidImages.put("img_coke_product_101604",R.drawable.img_coke_product_101604);
        Liquid.LiquidImages.put("img_coke_product_102032",R.drawable.img_coke_product_102032);
        Liquid.LiquidImages.put("img_coke_product_101650",R.drawable.img_coke_product_101650);
        Liquid.LiquidImages.put("img_coke_product_103652",R.drawable.img_coke_product_103652);
        Liquid.LiquidImages.put("img_coke_product_101683",R.drawable.img_coke_product_101683);
        Liquid.LiquidImages.put("img_coke_product_101640",R.drawable.img_coke_product_101640);
        Liquid.LiquidImages.put("img_coke_product_101588",R.drawable.img_coke_product_101588);
        Liquid.LiquidImages.put("img_coke_product_105331",R.drawable.img_coke_product_105331);
        Liquid.LiquidImages.put("img_coke_product_104473",R.drawable.img_coke_product_104473);
        Liquid.LiquidImages.put("img_coke_product_101620",R.drawable.img_coke_product_101620);
        Liquid.LiquidImages.put("img_coke_product_104896",R.drawable.img_coke_product_104896);
        Liquid.LiquidImages.put("img_coke_product_101667",R.drawable.img_coke_product_101667);
        Liquid.LiquidImages.put("img_coke_product_101596",R.drawable.img_coke_product_101596);
        Liquid.LiquidImages.put("img_coke_product_102277",R.drawable.img_coke_product_102277);
        Liquid.LiquidImages.put("img_coke_product_101822",R.drawable.img_coke_product_101822);
        Liquid.LiquidImages.put("img_coke_product_101659",R.drawable.img_coke_product_101659);
        Liquid.LiquidImages.put("img_coke_product_101639",R.drawable.img_coke_product_101639);
        Liquid.LiquidImages.put("img_coke_product_101711",R.drawable.img_coke_product_101711);
        Liquid.LiquidImages.put("img_coke_product_101682",R.drawable.img_coke_product_101682);
        Liquid.LiquidImages.put("img_coke_product_101655",R.drawable.img_coke_product_101655);
        Liquid.LiquidImages.put("img_coke_product_101637",R.drawable.img_coke_product_101637);
        Liquid.LiquidImages.put("img_coke_product_101703",R.drawable.img_coke_product_101703);
        Liquid.LiquidImages.put("img_coke_product_101677",R.drawable.img_coke_product_101677);
        Liquid.LiquidImages.put("img_coke_product_101821",R.drawable.img_coke_product_101821);
        Liquid.LiquidImages.put("img_coke_product_101914",R.drawable.img_coke_product_101914);
        Liquid.LiquidImages.put("img_coke_product_100079",R.drawable.img_coke_product_100079);
        Liquid.LiquidImages.put("img_coke_product_101765",R.drawable.img_coke_product_101765);
        Liquid.LiquidImages.put("img_coke_product_100064",R.drawable.img_coke_product_100064);
        Liquid.LiquidImages.put("img_coke_product_104507",R.drawable.img_coke_product_104507);
        Liquid.LiquidImages.put("img_coke_product_101915",R.drawable.img_coke_product_101915);
        Liquid.LiquidImages.put("img_coke_product_100078",R.drawable.img_coke_product_100078);
        Liquid.LiquidImages.put("img_coke_product_101912",R.drawable.img_coke_product_101912);
        Liquid.LiquidImages.put("img_coke_product_104816",R.drawable.img_coke_product_104816);
        Liquid.LiquidImages.put("img_coke_product_100060",R.drawable.img_coke_product_100060);
        Liquid.LiquidImages.put("img_coke_product_102352",R.drawable.img_coke_product_102352);
        Liquid.LiquidImages.put("img_coke_product_104813",R.drawable.img_coke_product_104813);
        Liquid.LiquidImages.put("img_coke_product_104807",R.drawable.img_coke_product_104807);
        Liquid.LiquidImages.put("img_coke_product_105366",R.drawable.img_coke_product_105366);
        Liquid.LiquidImages.put("img_coke_product_102547",R.drawable.img_coke_product_102547);
        Liquid.LiquidImages.put("img_coke_product_104808",R.drawable.img_coke_product_104808);
        Liquid.LiquidImages.put("img_coke_product_104806",R.drawable.img_coke_product_104806);
        Liquid.LiquidImages.put("img_coke_product_104814",R.drawable.img_coke_product_104814);
        Liquid.LiquidImages.put("img_coke_product_100059",R.drawable.img_coke_product_100059);
        Liquid.LiquidImages.put("img_coke_product_102353",R.drawable.img_coke_product_102353);
        Liquid.LiquidImages.put("img_coke_product_104818",R.drawable.img_coke_product_104818);
        Liquid.LiquidImages.put("img_coke_product_104811",R.drawable.img_coke_product_104811);
        Liquid.LiquidImages.put("img_coke_product_100058",R.drawable.img_coke_product_100058);
        Liquid.LiquidImages.put("img_coke_product_102350",R.drawable.img_coke_product_102350);
        Liquid.LiquidImages.put("img_coke_product_104810",R.drawable.img_coke_product_104810);
        Liquid.LiquidImages.put("img_coke_product_100065",R.drawable.img_coke_product_100065);
        Liquid.LiquidImages.put("img_coke_product_104817",R.drawable.img_coke_product_104817);
        Liquid.LiquidImages.put("img_coke_product_100150",R.drawable.img_coke_product_100150);
        Liquid.LiquidImages.put("img_coke_product_104812",R.drawable.img_coke_product_104812);
        Liquid.LiquidImages.put("img_coke_product_102351",R.drawable.img_coke_product_102351);
        Liquid.LiquidImages.put("img_coke_product_103914",R.drawable.img_coke_product_103914);
        Liquid.LiquidImages.put("img_coke_product_103913",R.drawable.img_coke_product_103913);
        Liquid.LiquidImages.put("img_coke_product_104552",R.drawable.img_coke_product_104552);
        Liquid.LiquidImages.put("img_coke_product_105343",R.drawable.img_coke_product_105343);
        Liquid.LiquidImages.put("img_coke_product_104228",R.drawable.img_coke_product_104228);
        Liquid.LiquidImages.put("img_coke_product_104234",R.drawable.img_coke_product_104234);
        Liquid.LiquidImages.put("img_coke_product_102525",R.drawable.img_coke_product_102525);
        Liquid.LiquidImages.put("img_coke_product_102527",R.drawable.img_coke_product_102527);
        Liquid.LiquidImages.put("img_coke_product_101627",R.drawable.img_coke_product_101627);
        Liquid.LiquidImages.put("img_coke_product_101660",R.drawable.img_coke_product_101660);
        Liquid.LiquidImages.put("img_coke_product_100048",R.drawable.img_coke_product_100048);
        Liquid.LiquidImages.put("img_coke_product_100502",R.drawable.img_coke_product_100502);
        Liquid.LiquidImages.put("img_coke_product_101693",R.drawable.img_coke_product_101693);
        Liquid.LiquidImages.put("img_coke_product_101695",R.drawable.img_coke_product_101695);
        Liquid.LiquidImages.put("img_coke_product_101692",R.drawable.img_coke_product_101692);
        Liquid.LiquidImages.put("img_coke_product_105151",R.drawable.img_coke_product_105151);
        Liquid.LiquidImages.put("img_coke_product_103887",R.drawable.img_coke_product_103887);
        Liquid.LiquidImages.put("img_coke_product_103967",R.drawable.img_coke_product_103967);
        Liquid.LiquidImages.put("img_coke_product_103963",R.drawable.img_coke_product_103963);
        Liquid.LiquidImages.put("img_coke_product_103964",R.drawable.img_coke_product_103964);
        Liquid.LiquidImages.put("img_coke_product_103962",R.drawable.img_coke_product_103962);
        Liquid.LiquidImages.put("img_coke_product_100501",R.drawable.img_coke_product_100501);
        Liquid.LiquidImages.put("img_coke_product_103957",R.drawable.img_coke_product_103957);
        Liquid.LiquidImages.put("img_coke_product_103952",R.drawable.img_coke_product_103952);
        Liquid.LiquidImages.put("img_coke_product_103958",R.drawable.img_coke_product_103958);
        Liquid.LiquidImages.put("img_coke_product_101625",R.drawable.img_coke_product_101625);
        Liquid.LiquidImages.put("img_coke_product_104791",R.drawable.img_coke_product_104791);
        Liquid.LiquidImages.put("img_coke_product_101605",R.drawable.img_coke_product_101605);
        Liquid.LiquidImages.put("img_coke_product_101651",R.drawable.img_coke_product_101651);
        Liquid.LiquidImages.put("img_coke_product_100092",R.drawable.img_coke_product_100092);
        Liquid.LiquidImages.put("img_coke_product_101635",R.drawable.img_coke_product_101635);
        Liquid.LiquidImages.put("img_coke_product_101589",R.drawable.img_coke_product_101589);
        Liquid.LiquidImages.put("img_coke_product_104955",R.drawable.img_coke_product_104955);
        Liquid.LiquidImages.put("img_coke_product_104954",R.drawable.img_coke_product_104954);
        Liquid.LiquidImages.put("img_coke_product_101621",R.drawable.img_coke_product_101621);
        Liquid.LiquidImages.put("img_coke_product_101593",R.drawable.img_coke_product_101593);

        Liquid.LiquidImages.put("img_coke_product_101597",R.drawable.img_coke_product_101597);
        Liquid.LiquidImages.put("img_coke_product_104238",R.drawable.img_coke_product_104238);
        Liquid.LiquidImages.put("img_coke_product_101823",R.drawable.img_coke_product_101823);
        Liquid.LiquidImages.put("img_coke_product_102407",R.drawable.img_coke_product_102407);
        Liquid.LiquidImages.put("img_coke_product_104488",R.drawable.img_coke_product_104488);
        Liquid.LiquidImages.put("img_coke_product_106189",R.drawable.img_coke_product_106189);
        Liquid.LiquidImages.put("img_coke_product_106185",R.drawable.img_coke_product_106185);
        Liquid.LiquidImages.put("img_coke_product_101628",R.drawable.img_coke_product_101628);
        Liquid.LiquidImages.put("img_coke_product_105311",R.drawable.img_coke_product_105311);
        Liquid.LiquidImages.put("img_coke_product_101700",R.drawable.img_coke_product_101700);
        Liquid.LiquidImages.put("img_coke_product_101710",R.drawable.img_coke_product_101710);
        Liquid.LiquidImages.put("img_coke_product_101709",R.drawable.img_coke_product_101709);
        Liquid.LiquidImages.put("img_coke_product_101633",R.drawable.img_coke_product_101633);
        Liquid.LiquidImages.put("img_coke_product_105506",R.drawable.img_coke_product_105506);
        Liquid.LiquidImages.put("img_coke_product_105315",R.drawable.img_coke_product_105315);
        Liquid.LiquidImages.put("img_coke_product_101661",R.drawable.img_coke_product_101661);
        Liquid.LiquidImages.put("img_coke_product_104785",R.drawable.img_coke_product_104785);
        Liquid.LiquidImages.put("img_coke_product_101608",R.drawable.img_coke_product_101608);
        Liquid.LiquidImages.put("img_coke_product_101653",R.drawable.img_coke_product_101653);
        Liquid.LiquidImages.put("img_coke_product_100091",R.drawable.img_coke_product_100091);
        Liquid.LiquidImages.put("img_coke_product_101636",R.drawable.img_coke_product_101636);
        Liquid.LiquidImages.put("img_coke_product_101590",R.drawable.img_coke_product_101590);
        Liquid.LiquidImages.put("img_coke_product_104956",R.drawable.img_coke_product_104956);
        Liquid.LiquidImages.put("img_coke_product_104952",R.drawable.img_coke_product_104952);
        Liquid.LiquidImages.put("img_coke_product_101622",R.drawable.img_coke_product_101622);
        Liquid.LiquidImages.put("img_coke_product_101594",R.drawable.img_coke_product_101594);
        Liquid.LiquidImages.put("img_coke_product_104892",R.drawable.img_coke_product_104892);

        Liquid.LiquidImages.put("img_coke_product_101598",R.drawable.img_coke_product_101598);
        Liquid.LiquidImages.put("img_coke_product_104237",R.drawable.img_coke_product_104237);
        Liquid.LiquidImages.put("img_coke_product_101824",R.drawable.img_coke_product_101824);
        Liquid.LiquidImages.put("img_coke_product_102408",R.drawable.img_coke_product_102408);
        Liquid.LiquidImages.put("img_coke_product_101624",R.drawable.img_coke_product_101624);
        Liquid.LiquidImages.put("img_coke_product_100035",R.drawable.img_coke_product_100035);
        Liquid.LiquidImages.put("img_coke_product_101874",R.drawable.img_coke_product_101874);
        Liquid.LiquidImages.put("img_coke_product_101862",R.drawable.img_coke_product_101862);
        Liquid.LiquidImages.put("img_coke_product_106125",R.drawable.img_coke_product_106125);
        Liquid.LiquidImages.put("img_coke_product_100030",R.drawable.img_coke_product_100030);
        Liquid.LiquidImages.put("img_coke_product_100027",R.drawable.img_coke_product_100027);
        Liquid.LiquidImages.put("img_coke_product_102008",R.drawable.img_coke_product_102008);
        Liquid.LiquidImages.put("img_coke_product_101319",R.drawable.img_coke_product_101319);
        Liquid.LiquidImages.put("img_coke_product_105058",R.drawable.img_coke_product_105058);
        Liquid.LiquidImages.put("img_coke_product_102007",R.drawable.img_coke_product_102007);
        Liquid.LiquidImages.put("img_coke_product_100088",R.drawable.img_coke_product_100088);
        Liquid.LiquidImages.put("img_coke_product_100094",R.drawable.img_coke_product_100094);
        Liquid.LiquidImages.put("img_coke_product_100090",R.drawable.img_coke_product_100090);
        Liquid.LiquidImages.put("img_coke_product_100095",R.drawable.img_coke_product_100095);
        Liquid.LiquidImages.put("img_coke_product_100089",R.drawable.img_coke_product_100089);
        Liquid.LiquidImages.put("img_coke_product_100096",R.drawable.img_coke_product_100096);
        Liquid.LiquidImages.put("img_coke_product_104201",R.drawable.img_coke_product_104201);
        Liquid.LiquidImages.put("img_coke_product_104784",R.drawable.img_coke_product_104784);
        Liquid.LiquidImages.put("img_coke_product_104200",R.drawable.img_coke_product_104200);
        Liquid.LiquidImages.put("img_coke_product_100151",R.drawable.img_coke_product_100151);
        Liquid.LiquidImages.put("img_coke_product_100037",R.drawable.img_coke_product_100037);
       Liquid.LiquidImages.put("img_coke_sovi_1",R.drawable.img_coke_sovi_1);
        Liquid.LiquidImages.put("img_coke_sovi_9",R.drawable.img_coke_sovi_9);
        Liquid.LiquidImages.put("img_coke_sovi_10",R.drawable.img_coke_sovi_10);
        Liquid.LiquidImages.put("img_coke_sovi_11",R.drawable.img_coke_sovi_11);
        Liquid.LiquidImages.put("img_coke_sovi_12",R.drawable.img_coke_sovi_12);
        Liquid.LiquidImages.put("img_coke_sovi_15",R.drawable.img_coke_sovi_15);
        Liquid.LiquidImages.put("img_coke_sovi_38",R.drawable.img_coke_sovi_38);
        Liquid.LiquidImages.put("img_coke_sovi_39",R.drawable.img_coke_sovi_39);
        Liquid.LiquidImages.put("img_coke_sovi_40",R.drawable.img_coke_sovi_40);
        Liquid.LiquidImages.put("img_coke_sovi_41",R.drawable.img_coke_sovi_41);
        Liquid.LiquidImages.put("img_coke_sovi_42",R.drawable.img_coke_sovi_42);
        Liquid.LiquidImages.put("img_coke_sovi_43",R.drawable.img_coke_sovi_43);
        Liquid.LiquidImages.put("img_coke_sovi_46",R.drawable.img_coke_sovi_46);
        Liquid.LiquidImages.put("img_coke_sovi_47",R.drawable.img_coke_sovi_47);
        Liquid.LiquidImages.put("img_coke_sovi_50",R.drawable.img_coke_sovi_50);
        Liquid.LiquidImages.put("img_coke_sovi_51",R.drawable.img_coke_sovi_51);
        Liquid.LiquidImages.put("img_coke_sovi_55",R.drawable.img_coke_sovi_55);
        Liquid.LiquidImages.put("img_coke_sovi_59",R.drawable.img_coke_sovi_59);
        Liquid.LiquidImages.put("img_coke_sovi_61",R.drawable.img_coke_sovi_61);
        Liquid.LiquidImages.put("img_coke_sovi_62",R.drawable.img_coke_sovi_62);
        Liquid.LiquidImages.put("img_coke_sovi_64",R.drawable.img_coke_sovi_64);
        Liquid.LiquidImages.put("img_coke_sovi_66",R.drawable.img_coke_sovi_66);
        Liquid.LiquidImages.put("img_coke_sovi_69",R.drawable.img_coke_sovi_69);
        Liquid.LiquidImages.put("img_coke_sovi_72",R.drawable.img_coke_sovi_72);
        Liquid.LiquidImages.put("img_coke_sovi_74",R.drawable.img_coke_sovi_74);
        Liquid.LiquidImages.put("img_coke_sovi_75",R.drawable.img_coke_sovi_75);
        Liquid.LiquidImages.put("img_coke_sovi_76",R.drawable.img_coke_sovi_76);
        Liquid.LiquidImages.put("img_coke_sovi_77",R.drawable.img_coke_sovi_77);
        Liquid.LiquidImages.put("img_coke_sovi_78",R.drawable.img_coke_sovi_78);
        Liquid.LiquidImages.put("img_coke_sovi_79",R.drawable.img_coke_sovi_79);
        Liquid.LiquidImages.put("img_coke_sovi_80",R.drawable.img_coke_sovi_80);
        Liquid.LiquidImages.put("img_coke_sovi_83",R.drawable.img_coke_sovi_83);*/

        Liquid.LiquidImages.put("img_coke_activation_crosscategory",R.drawable.img_coke_activation_crosscategory);
        Liquid.LiquidImages.put("img_coke_activation_endcap",R.drawable.img_coke_activation_endcap);
        Liquid.LiquidImages.put("img_coke_activation_massdisplayunit",R.drawable.img_coke_activation_massdisplayunit);
        Liquid.LiquidImages.put("img_coke_activation_poi1x1",R.drawable.img_coke_activation_poi1x1);
        Liquid.LiquidImages.put("img_coke_activation_specialdisplay",R.drawable.img_coke_activation_specialdisplay);
        Liquid.LiquidImages.put("img_coke_activation_stackpallet",R.drawable.img_coke_activation_stackpallet);
        Liquid.LiquidImages.put("img_coke_activation_wilkins6l",R.drawable.img_coke_activation_wilkins6l);

        Liquid.LiquidImages.put("img_coke_location_shelves",R.drawable.img_coke_location_shelves);
        Liquid.LiquidImages.put("img_coke_location_exhibits",R.drawable.img_coke_location_exhibits);
        Liquid.LiquidImages.put("img_coke_location_commoncoldvault",R.drawable.img_coke_location_companyownedcoolers);
        Liquid.LiquidImages.put("img_coke_location_companyownedcoolers",R.drawable.img_coke_location_commoncoldvault);



    }




}
