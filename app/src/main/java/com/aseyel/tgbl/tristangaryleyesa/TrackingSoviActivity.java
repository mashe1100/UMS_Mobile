package com.aseyel.tgbl.tristangaryleyesa;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class TrackingSoviActivity extends BaseFormActivity {
    private static final String TAG = TrackingSoviActivity.class.getSimpleName();
    @BindView(R.id.spinner_ts_location)
    Spinner spinner_ts_location;
    @BindView(R.id.txttaCans)
    EditText txttaCans;
    @BindView(R.id.txttaSSPet)
    EditText txttaSSPet;
    @BindView(R.id.txttaMSPet)
    EditText txttaMSPet;
    @BindView(R.id.txttaSSDoy)
    EditText txttaSSDoy;
    @BindView(R.id.txttaMSbrick)
    EditText txttaMSbrick;
    @BindView(R.id.txttaSSWedge)
    EditText txttaSSWedge;
    @BindView(R.id.txttaBox)
    EditText txttaBox;
    @BindView(R.id.txttaLitro)
    EditText txttaLitro;
    @BindView(R.id.txttaComment)
    EditText txttaComment;
    @BindView(R.id.txttaPounch)
    EditText txttaPounch;
    @BindView(R.id.txttaSSbrick)
    EditText txttaSSbrick;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    @BindView(R.id.txtQuestion)
    TextView txtQuestion;
    @BindView (R.id.txttaKOF)
    TextView txttaKOF;

    @BindView(R.id.iltsCans)
    TextInputLayout iltsCans;
    @BindView(R.id.iltsSSPet)
    TextInputLayout iltsSSPet;
    @BindView(R.id.iltsMSPet)
    TextInputLayout iltsMSPet;
    @BindView(R.id.iltsSSDoy)
    TextInputLayout iltsSSDoy;
    @BindView(R.id.iltsMSbrick)
    TextInputLayout iltsMSbrick;
    @BindView(R.id.iltsSSWedge)
    TextInputLayout iltsSSWedge;
    @BindView(R.id.iltsBox)
    TextInputLayout iltsBox;
    @BindView(R.id.iltsLitro)
    TextInputLayout iltsLitro;
    @BindView(R.id.iltsComment)
    TextInputLayout iltsComment;
    @BindView(R.id.iltsPounch)
    TextInputLayout iltsPounch;
    @BindView(R.id.iltsSSbrick)
    TextInputLayout iltsSSbrick;
    @BindView(R.id.iltsKOF)
    TextInputLayout iltsKOF;
    @BindView(R.id.btnDelete)
    FloatingActionButton btnDelete;
    ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    int mBitmapCount = 0;
    String Filename = "";
    LiquidFile mLiquidFile;
    int GrandTotalKof = 0;
    int GrandTotalNonKof = 0;
    int TotalKof = 0;
    int TotalNonKof = 0;
    int TotalCans = 0;
    int TotalSSPet = 0;
    int TotalMSPet = 0;
    int TotalSSDoy = 0;
    int TotalSSbrick = 0;
    int TotalMSbrick = 0;
    int TotalSSWedge = 0;
    int TotalBox = 0;
    int TotalLitro = 0;
    int TotalPounch = 0;
    int Cans = 0;
    int SSPet = 0;
    int MSPet = 0;
    int SSDoy = 0;
    int SSbrick = 0;
    int MSbrick = 0;
    int SSWedge = 0;
    int Box = 0;
    int Litro = 0;
    int Pounch = 0;
    String Comment;
    String Location;
    ArrayAdapter<CharSequence> adapter_sovi_location;
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    static final int CAM_REQUEST = 1;
    String TrackingCategory = "SOVI";
    File mFile;
    String[] Subfolder;
    File[] listFile;
    File mImages;
    int ImageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_sovi);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
            //GetData(Liquid.SelectedAccountNumber,Liquid.SelectedCode,"",Liquid.SelectedPeriod);
            GetImages();
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }

    private void setup() {
        Subfolder = new String[1];
        Subfolder[0] = TrackingCategory;
        mLiquidFile = new LiquidFile(this);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnDelete = (FloatingActionButton) findViewById(R.id.btnDelete);
        adapter_sovi_location = ArrayAdapter.createFromResource(this,R.array.tracking_sovi_location,android.R.layout.simple_spinner_item);
        adapter_sovi_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ts_location.setAdapter(adapter_sovi_location);
        txtQuestion.setText("How many SOVI for this product "+Liquid.SelectedCode+" and select the correct location? Please count carefully.");

        spinner_ts_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Location = parent.getItemAtPosition(position).toString();
                if(Location.equals("Select Location")){
                    Location = "";
                    txttaKOF.setHint("KOF");
                    txttaCans.setHint("Cans");
                    txttaSSPet.setHint("SS Pet");
                    txttaMSPet.setHint("MS Pet");
                    txttaSSDoy.setHint("SS Doy");
                    txttaSSbrick.setHint("SS Brick");
                    txttaMSbrick.setHint("MS Brick");
                    txttaSSWedge.setHint("SS Wedge");
                    txttaBox.setHint("Box");
                    txttaLitro.setHint("Litro");
                    txttaPounch.setHint("Pounch");
                    txttaComment.setHint("");
                    clearTextBox();
                    return;
                }
                GetData(Liquid.SelectedAccountNumber,Liquid.SelectedCode,Location,Liquid.SelectedDescription,Liquid.SelectedPeriod);
                GetImages();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Location.equals("Select Location") || Location.equals(""))  {
                    Liquid.showDialogError(TrackingSoviActivity.this, "Invalid", "Please select sovi location before deleting");
                    return;
                }
                DeleteData(Liquid.SelectedAccountNumber,Liquid.SelectedCode,Liquid.SelectedDescription,Location,Liquid.SelectedPeriod);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (Location.equals("Select Location") || Location.equals(""))  {
                        Liquid.showDialogError(TrackingSoviActivity.this, "Invalid", "Please answer the questions before taking a image!");
                        return;
                    }
                    Filename = Liquid.SelectedAccountNumber +"_"+"SOVI"+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedCode)+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription)+"_"+Liquid.RemoveSpecialCharacter(Location)+"_"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
                    mFile = mLiquidFile.Directory(Liquid.SelectedAccountNumber,Liquid.RemoveSpecialCharacter(Filename),Subfolder);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mFile));
                    startActivityForResult(intent,CAM_REQUEST);
                }
                catch (Exception e){
                    Liquid.ShowMessage(getApplicationContext(),e.toString());
                }
            }
        });
        GetData(Liquid.SelectedAccountNumber,Liquid.SelectedCode,Location,Liquid.SelectedDescription,Liquid.SelectedPeriod);
        setSoviView(Liquid.SelectedType,Liquid.SelectedDescription);
    }

    public void DeleteData(final String AccountNumber,final String Sovi,final String Description,final String Location,final String Period){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = false;
                        result = TrackingModel.DeleteSOVIByLocation(AccountNumber,Sovi,Description,Location,Period);
                        if (result == true) {
                            Liquid.showDialogInfo(TrackingSoviActivity.this, "Valid", "Successfully Deleted!");
                            GetData(Liquid.SelectedAccountNumber,Liquid.SelectedCode,Location,Liquid.SelectedDescription,Liquid.SelectedPeriod);
                        } else {
                            Liquid.showDialogError(TrackingSoviActivity.this, "Invalid", "Unsuccessfully Deleted!");
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to DELETE?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void setSoviView(String Type,String Category){
        //Liquid.SelectedDescription is getProductType - sparkling;
        //Liquid.SelectedType is getProductCategory - kof or non kof;
        txttaKOF.setVisibility(View.GONE);
        txttaCans.setVisibility(View.GONE);
        txttaSSPet.setVisibility(View.GONE);
        txttaMSPet.setVisibility(View.GONE);
        txttaSSDoy.setVisibility(View.GONE);
        txttaSSbrick.setVisibility(View.GONE);
        txttaMSbrick.setVisibility(View.GONE);
        txttaSSWedge.setVisibility(View.GONE);
        txttaBox.setVisibility(View.GONE);
        txttaLitro.setVisibility(View.GONE);
        txttaPounch.setVisibility(View.GONE);
        iltsKOF.setVisibility(View.GONE);
        iltsCans.setVisibility(View.GONE);
        iltsSSPet.setVisibility(View.GONE);
        iltsMSPet.setVisibility(View.GONE);
        iltsSSDoy.setVisibility(View.GONE);
        iltsSSbrick.setVisibility(View.GONE);
        iltsMSbrick.setVisibility(View.GONE);
        iltsSSWedge.setVisibility(View.GONE);
        iltsBox.setVisibility(View.GONE);
        iltsLitro.setVisibility(View.GONE);
        iltsPounch.setVisibility(View.GONE);

        if(Type.equals("KOF")){
            txttaKOF.setVisibility(View.VISIBLE);
            iltsKOF.setVisibility(View.VISIBLE);
        }
        //Non Kof and Sparkiling - CANS,SS PET,MS PET
        if(Category.equals("Sparkling") && Type.equals("NON-KOF")){
            txttaCans.setVisibility(View.VISIBLE);
            txttaSSPet.setVisibility(View.VISIBLE);
            txttaMSPet.setVisibility(View.VISIBLE);
            iltsKOF.setVisibility(View.VISIBLE);
            iltsCans.setVisibility(View.VISIBLE);
            iltsSSPet.setVisibility(View.VISIBLE);
            iltsMSPet.setVisibility(View.VISIBLE);
        }
        //Non Kof and Sports Drink - CANS,SS PET,MS PET
        if(Category.equals("Sports Drink") && Type.equals("NON-KOF")){
            txttaCans.setVisibility(View.VISIBLE);
            txttaSSPet.setVisibility(View.VISIBLE);
            txttaMSPet.setVisibility(View.VISIBLE);
            iltsKOF.setVisibility(View.VISIBLE);
            iltsCans.setVisibility(View.VISIBLE);
            iltsSSPet.setVisibility(View.VISIBLE);
            iltsMSPet.setVisibility(View.VISIBLE);
        }
        //Non Kof and Sports Drink - CANS,SS PET,MS PET
        if(Category.equals("Packaged Water") && Type.equals("NON-KOF")){
            txttaSSPet.setVisibility(View.VISIBLE);
            txttaMSPet.setVisibility(View.VISIBLE);
            iltsSSPet.setVisibility(View.VISIBLE);
            iltsMSPet.setVisibility(View.VISIBLE);
        }
        //Non Kof and RTD JUICE - CANS,SS PET,MS PET,SS DOY,SS BRICK,MS BRICK,SS WEDGE,BOX
        if(Category.equals("RTD Juice") && Type.equals("NON-KOF")){
          txttaCans.setVisibility(View.VISIBLE);
            txttaSSPet.setVisibility(View.VISIBLE);
            txttaMSPet.setVisibility(View.VISIBLE);
            txttaSSDoy.setVisibility(View.VISIBLE);
            txttaSSbrick.setVisibility(View.VISIBLE);
            txttaMSbrick.setVisibility(View.VISIBLE);
            txttaSSWedge.setVisibility(View.VISIBLE);
            txttaBox.setVisibility(View.VISIBLE);
            iltsCans.setVisibility(View.VISIBLE);
            iltsSSPet.setVisibility(View.VISIBLE);
            iltsMSPet.setVisibility(View.VISIBLE);
            iltsSSDoy.setVisibility(View.VISIBLE);
            iltsSSbrick.setVisibility(View.VISIBLE);
            iltsMSbrick.setVisibility(View.VISIBLE);
            iltsSSWedge.setVisibility(View.VISIBLE);
            iltsBox.setVisibility(View.VISIBLE);
        }
        //Non Kof and RTD JUICE - CANS,SS PET,MS PET,SS DOY,SS BRICK,MS BRICK,SS WEDGE,BOX
        if(Category.equals("RTD Tea") && Type.equals("NON-KOF")){
            txttaCans.setVisibility(View.VISIBLE);
            txttaSSPet.setVisibility(View.VISIBLE);
            txttaMSPet.setVisibility(View.VISIBLE);
            txttaSSDoy.setVisibility(View.VISIBLE);
            txttaSSbrick.setVisibility(View.VISIBLE);
            txttaMSbrick.setVisibility(View.VISIBLE);
            txttaSSWedge.setVisibility(View.VISIBLE);
            iltsCans.setVisibility(View.VISIBLE);
            iltsSSPet.setVisibility(View.VISIBLE);
            iltsMSPet.setVisibility(View.VISIBLE);
            iltsSSDoy.setVisibility(View.VISIBLE);
            iltsSSbrick.setVisibility(View.VISIBLE);
            iltsMSbrick.setVisibility(View.VISIBLE);
            iltsSSWedge.setVisibility(View.VISIBLE);
        }
        //Non Kof and Powders - LITRO,POUNCH
        if(Category.equals("Powders") && Type.equals("NON-KOF")){
            txttaLitro.setVisibility(View.VISIBLE);
            txttaPounch.setVisibility(View.VISIBLE);
            iltsLitro.setVisibility(View.VISIBLE);
            iltsPounch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {

                    boolean result = false;

                    result = TrackingModel.doSubmitPicture(Liquid.SelectedAccountNumber,
                            TrackingCategory,
                            Liquid.SelectedCode,
                            Liquid.SelectedDescription,
                            Location,
                            String.valueOf(mUri.size()),
                            Filename,
                            Liquid.SelectedPeriod
                    );

                    if(result == true){

                        Liquid.resizeImage(mFile.getAbsolutePath(),0.80,0.80);
                        Liquid.ShowMessage(getApplicationContext(),"Save Image Success");
                        mUri.add(Uri.fromFile(mFile));
                        tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                        ImageCount = mUri.size();
                    }
                }
            }
        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case android.R.id.home:
                   this.finish();
                    return true;

                case R.id.action_form_submit:
                    boolean result = false;
                    TotalKof = GrandTotalKof + Integer.parseInt(txttaKOF.getText().toString().equals("") ? "0" : txttaKOF.getText().toString());
                    Cans = TotalCans + Liquid.IntValidation(txttaCans.getText().toString().equals("") ? "0" : txttaCans.getText().toString());
                    SSPet = TotalSSPet + Liquid.IntValidation(txttaSSPet.getText().toString().equals("") ? "0" : txttaSSPet.getText().toString());
                    MSPet = TotalMSPet+Liquid.IntValidation(txttaMSPet.getText().toString().equals("") ? "0" : txttaMSPet.getText().toString());
                    SSDoy = TotalSSDoy + Liquid.IntValidation(txttaSSDoy.getText().toString().equals("") ? "0" : txttaSSDoy.getText().toString());
                    SSbrick = TotalSSbrick + Liquid.IntValidation(txttaSSbrick.getText().toString().equals("") ? "0" : txttaSSbrick.getText().toString());
                    MSbrick = TotalMSbrick + Liquid.IntValidation(txttaMSbrick.getText().toString().equals("") ? "0" : txttaMSbrick.getText().toString());
                    SSWedge = TotalSSWedge + Liquid.IntValidation(txttaSSWedge.getText().toString().equals("") ? "0" : txttaSSWedge.getText().toString());
                    Box = TotalBox + Liquid.IntValidation(txttaBox.getText().toString().equals("") ? "0" : txttaBox.getText().toString());
                    Litro = TotalLitro + Liquid.IntValidation(txttaLitro.getText().toString().equals("") ? "0" : txttaLitro.getText().toString());
                    Pounch = TotalPounch + Liquid.IntValidation(txttaPounch.getText().toString().equals("") ? "0" : txttaPounch.getText().toString());
                    Comment = txttaComment.getText().toString();
                    TotalNonKof =  Cans + SSPet + MSPet + SSDoy + SSbrick + MSbrick + SSWedge + Box + Litro + Pounch;

                    if (Location.equals("Select Location")) {
                        Liquid.showDialogError(this, "Invalid", "Information Incomplete!");
                        return false;
                    }
                    if (mUri.size() == 0) {
                        Liquid.showDialogError(this, "Invalid", "Please take picture!");
                        return false;
                    }
                    if(ImageCount == 0){
                        Liquid.showDialogError(this, "Invalid", "Please take picture!");
                        return false;
                    }
                    result = TrackingModel.doSubmitSovi(
                            Liquid.SelectedAccountNumber,
                            Liquid.SelectedCode,
                            Liquid.SelectedDescription,
                            Liquid.SelectedType,
                            Location,
                            String.valueOf(TotalKof),
                            String.valueOf(TotalNonKof),
                            String.valueOf(Cans),
                            String.valueOf(SSPet),
                            String.valueOf(MSPet),
                            String.valueOf(SSDoy),
                            String.valueOf(SSbrick),
                            String.valueOf(MSbrick),
                            String.valueOf(SSWedge),
                            String.valueOf(Box),
                            String.valueOf(Litro),
                            String.valueOf(Pounch),
                            Filename,
                            Comment,
                            Liquid.SelectedPeriod
                    );

                    if (result == true) {
                        Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
                    }else{
                        Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
                    }
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }
    }

    private void clearTextBox(){
        txttaCans.setText("");
        txttaSSPet.setText("");
        txttaMSPet.setText("");
        txttaSSDoy.setText("");
        txttaSSbrick.setText("");
        txttaMSbrick.setText("");
        txttaSSWedge.setText("");
        txttaBox.setText("");
        txttaLitro.setText("");
        txttaPounch.setText("");
    }

    public void GetData(String AccountNumber,
                        String ItemCode,
                        String Location,
                        String Category,
                        String Period){
        GrandTotalKof = 0;
        GrandTotalNonKof = 0;
        TotalCans = 0;
        TotalSSPet = 0;
        TotalMSPet = 0;
        TotalSSDoy = 0;
        TotalSSbrick = 0;
        TotalMSbrick = 0;
        TotalSSWedge = 0;
        TotalBox = 0;
        TotalLitro = 0;
        TotalPounch = 0;
        Log.i(TAG,Category);
        Cursor result = TrackingModel.GetTrackingSovi(
                ItemCode,
                AccountNumber,
                Location,
                Category,
                Period);
        try
        {
            clearTextBox();
            if(result.getCount() == 0){
                txttaKOF.setHint("KOF");
                txttaCans.setHint("Cans");
                txttaSSPet.setHint("SS Pet");
                txttaMSPet.setHint("MS Pet");
                txttaSSDoy.setHint("SS Doy");
                txttaSSbrick.setHint("SS Brick");
                txttaMSbrick.setHint("MS Brick");
                txttaSSWedge.setHint("SS Wedge");
                txttaBox.setHint("Box");
                txttaLitro.setHint("Litro");
                txttaPounch.setHint("Pounch");
                txttaComment.setHint("");
                return;
            }
            while(result.moveToNext()){
                Location = result.getString(0);
                GrandTotalKof = GrandTotalKof + Integer.parseInt(result.getString(1).equals("") ? "0": result.getString(1));
                GrandTotalNonKof = Integer.parseInt(result.getString(2).equals("") ? "0": result.getString(2));
                TotalCans  = TotalCans + Integer.parseInt(result.getString(3).equals("") ? "0": result.getString(3));
                TotalSSPet  = TotalSSPet + Integer.parseInt(result.getString(4).equals("") ? "0": result.getString(4));
                TotalMSPet  = TotalMSPet + Integer.parseInt(result.getString(5).equals("") ? "0": result.getString(5));
                TotalSSDoy  = TotalSSDoy + Integer.parseInt(result.getString(6).equals("") ? "0": result.getString(6));
                TotalSSbrick  = TotalSSbrick + Integer.parseInt(result.getString(7).equals("") ? "0": result.getString(7));
                TotalMSbrick  = TotalMSbrick + Integer.parseInt(result.getString(8).equals("") ? "0": result.getString(8));
                TotalSSWedge  = TotalSSWedge + Integer.parseInt(result.getString(9).equals("") ? "0": result.getString(9));
                TotalBox  = TotalBox + Integer.parseInt(result.getString(10).equals("") ? "0": result.getString(10));
                TotalLitro  = TotalLitro + Integer.parseInt(result.getString(11).equals("") ? "0": result.getString(11));
                TotalPounch  = TotalPounch + Integer.parseInt(result.getString(12).equals("") ? "0": result.getString(12));
                Comment = result.getString(13);
            }

            txttaCans.setHint("Cans ("+ String.valueOf(TotalCans)+")");
            txttaSSPet.setHint("SS Pet ("+String.valueOf(TotalSSPet)+")");
            txttaMSPet.setHint("MS Pet ("+String.valueOf(TotalMSPet)+")");
            txttaSSDoy.setHint("SS Doy ("+String.valueOf(TotalSSDoy)+")");
            txttaSSbrick.setHint("SS Brick ("+String.valueOf(TotalSSbrick)+")");
            txttaMSbrick.setHint("MS Brick ("+String.valueOf(TotalMSbrick)+")");
            txttaSSWedge.setHint("SSWedge ("+String.valueOf(TotalSSWedge)+")");
            txttaBox.setHint("Box ("+String.valueOf(TotalBox)+")");
            txttaLitro.setHint("Litro ("+String.valueOf(TotalLitro)+")");
            txttaPounch.setHint("Pounch ("+String.valueOf(TotalPounch)+")");
            txttaKOF.setHint("KOF ("+String.valueOf(GrandTotalKof)+")");
            txttaComment.setText(Comment);
           // spinner_ts_location.setSelection(adapter_sovi_location.getPosition(Location));
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }
    }

    private void GetImages(){
        mUri.clear();
        mImages = Liquid.getDiscPicture(Liquid.SelectedAccountNumber,Subfolder);
        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Filename = Liquid.SelectedAccountNumber +"_"+"SOVI"+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedCode)+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription)+"_"+Liquid.RemoveSpecialCharacter(Location)+"_"+String.valueOf(mUri.size()+1);
            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedCode))  && seperated[4].equals(Liquid.RemoveSpecialCharacter(Location))){
                    mUri.add(Uri.fromFile(listFile[a]));
                }
            }
            //tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
        }
    }
}
