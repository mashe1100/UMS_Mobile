package com.aseyel.tgbl.tristangaryleyesa;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.adapter.ChatAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.TranslatorModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.aseyel.tgbl.tristangaryleyesa.services.Speech;
import com.aseyel.tgbl.tristangaryleyesa.utils.DateUtils;
import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AthenaActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    private final String TAG = AthenaActivity.class.getSimpleName();
    private RecyclerView mChatRecyclerView;
    private List<TranslatorModel> mTranslatorModel;
    private ChatAdapter mTranslatorAdapater;
    private ImageButton btnSend;
    private EditText mMessage;
    private String Language = "English";
    private ImageButton btnVoiceRecognition;
    private final int LONG_DURATION = 5000;
    private final int SHORT_DURATION = 1200;
    private MenuItem mMenu;
    private int Items = 0;
    private TextToSpeech tts;
    private boolean ready = false;
    private boolean allowed = false;
    public final int CHECK_CODE = 0x1;
    private LinearLayout mAnalyzingLoading;
    private LiquidGPS mGPS;
    private SpeechRecognizer speechRecognizer;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athena);
        init();

    }

    public void getSpeechInput(Context context) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0).equals("")){
                        ArrayList<HashMap<String, String>> finalResult = new ArrayList<>();
                        HashMap<String, String> data_message = new HashMap<>();
                        data_message.put("Title","");
                        data_message.put("Message","You didn't speak! Please talk to me.");
                        data_message.put("TimeStamp",Liquid.currentDateTime());
                        data_message.put("MessageType","1");
                        finalResult.add(data_message);
                        mTranslatorAdapater.updateItems(false,finalResult);
                    }else{
                        //mMessage.setText(result.get(0));
                        SendMessage(result.get(0));
                    }

                }
                break;
            case CHECK_CODE:
                if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                    tts = new TextToSpeech(this, this);

                }else {
                    Intent install = new Intent();
                    install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(install);
                }
                allow(true);
                break;
        }

    }

    private void AthenaSpeak(String Message){
       pause(SHORT_DURATION);
       speak(Message);

       //destroy();
    }

    private void AnalyzingLoading(){
        if(mAnalyzingLoading.getVisibility() == View.VISIBLE){
            mAnalyzingLoading.setVisibility(View.GONE);
        }else{
            mAnalyzingLoading.setVisibility(View.VISIBLE);
        }
    }

    private void initSpeechRecognizer(){
        int[] colors = {
                ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this, R.color.color2),
                ContextCompat.getColor(this, R.color.color3),
                ContextCompat.getColor(this, R.color.color4),
                ContextCompat.getColor(this, R.color.color5)
        };

        int[] heights = { 20, 24, 18, 23, 16 };
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final RecognitionProgressView recognitionProgressView = (RecognitionProgressView) findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                showResults(results,recognitionProgressView);
            }
        });

        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.setCircleRadiusInDp(2);
        recognitionProgressView.setSpacingInDp(2);
        recognitionProgressView.setIdleStateAmplitudeInDp(2);
        recognitionProgressView.setRotationRadiusInDp(10);
        recognitionProgressView.play();
        recognitionProgressView.setVisibility(View.GONE);

        btnVoiceRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessage.setVisibility(View.GONE);
                recognitionProgressView.setVisibility(View.VISIBLE);
                stopSpecking();
                if (ContextCompat.checkSelfPermission(AthenaActivity.this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    startRecognition();
                    recognitionProgressView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startRecognition();
                        }
                    }, 50);
                }
            }
        });

        recognitionProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopVoiceRecognizer(recognitionProgressView);
            }
        });
    }



    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this, "Requires RECORD_AUDIO permission", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.RECORD_AUDIO },
                    REQUEST_RECORD_AUDIO_PERMISSION_CODE);
        }
    }

    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        speechRecognizer.startListening(intent);
    }

    private void showResults(Bundle results,RecognitionProgressView recognitionProgressView) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        SendMessage(matches.get(0));
        StopVoiceRecognizer(recognitionProgressView);
        //Toast.makeText(this, matches.get(0), Toast.LENGTH_LONG).show();
    }

    private void StopVoiceRecognizer(RecognitionProgressView recognitionProgressView){
        recognitionProgressView.stop();
        recognitionProgressView.play();
        recognitionProgressView.setVisibility(View.GONE);
        mMessage.setVisibility(View.VISIBLE);
    }
    private void init(){
        try {
            mAnalyzingLoading = (LinearLayout) findViewById(R.id.mAnalyzingLoading);
            btnVoiceRecognition = (ImageButton) findViewById(R.id.btnVoiceRecognition);
            btnSend = (ImageButton) findViewById(R.id.btnSend);
            mMessage = (EditText) findViewById(R.id.mMessage);

            mChatRecyclerView = (RecyclerView) findViewById(R.id.mChatRecyclerView);
            mTranslatorModel = new ArrayList<TranslatorModel>();
            mTranslatorAdapater = new ChatAdapter(this);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            mChatRecyclerView.setLayoutManager(llm);
            mChatRecyclerView.setHasFixedSize(true);
            mChatRecyclerView.setLayoutManager(llm);
            mChatRecyclerView.setAdapter(mTranslatorAdapater);
            mGPS = new LiquidGPS(this);
            initSpeechRecognizer();
            //checkTTS();
            tts = new TextToSpeech(this, this);

            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendMessage(mMessage.getText().toString());
                }
            });

            /*btnVoiceRecognition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getSpeechInput(AthenaActivity.this);
                }
            });*/

            AnalyzingLoading();


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(AthenaActivity.this, ProfileActivity.class);
                    AthenaActivity.this.startActivity(i);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.athena_menu, menu);
        mMenu  = menu.findItem(R.id.mMenu);
        mMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(AthenaActivity.this, MenuActivity.class);
                AthenaActivity.this.startActivity(i);
                return false;
            }
        });
        return true;
    }


    private ArrayList WelcomeMessage(){
        ArrayList<HashMap<String, String>> finalResult = new ArrayList<>();
        HashMap<String, String> data = new HashMap<>();
        String Message = "Welcome I'm Athena! How may i help you?";
        data.put("Title","Athena!");
        data.put("Message",Message);
        data.put("TimeStamp", Liquid.currentDateTime());
        data.put("MessageType","1");
        finalResult.add(data);
        Items = Items + 1;
        mChatRecyclerView.scrollToPosition(Items - 1);
        AthenaSpeak(Message);
        return finalResult;
    }

    public static void hideKeyboard(Activity activity){
        InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null){
            view = new View(activity);
        }
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        destroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }

    private void SendMessage(String Message){
        ArrayList<HashMap<String, String>> finalResult = new ArrayList<>();
        HashMap<String, String> data = new HashMap<>();
        data.put("Title","");
        data.put("Message",Message);
        data.put("TimeStamp",Liquid.currentDateTime());
        data.put("MessageType","2");
        finalResult.add(data);
        mTranslatorAdapater.updateItems(false,finalResult);
        mMessage.setText("");
        hideKeyboard(this);
        Items = Items + 1;
        mChatRecyclerView.scrollToPosition(Items - 1);
        GetReply(Message);


    }

    private void GetReply(String Message){

        new DoTalkToAthena().execute(Message);
    }

    private void DoVoiceRecognition(){
        ArrayList<HashMap<String, String>> finalResult = new ArrayList<>();
        HashMap<String, String> data = new HashMap<>();
        String Message = "Please hold the button and speak the word that i will translater :D";
        data.put("Title","");
        data.put("Message",Message);
        data.put("TimeStamp",Liquid.currentDateTime());
        data.put("MessageType","1");
        finalResult.add(data);
        AthenaSpeak(Message);
        mTranslatorAdapater.updateItems(false,finalResult);
        Items = Items + 1;
        mChatRecyclerView.scrollToPosition(Items - 1);


    }


    public class DoTalkToAthena extends AsyncTask<String,Void,Void> {
        ArrayList<HashMap<String, String>> TranslationMessage = new ArrayList<>();
        String Message = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AnalyzingLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Liquid.POSTUMSAPI mPOSTUMSAPI;
                String message = params[0];
                HttpHandler sh = new HttpHandler();
                mGPS.getDeviceLocation();
                JSONObject dataObject = new JSONObject();
                dataObject.put("username",Liquid.Username);
                dataObject.put("password",Liquid.Password);
                dataObject.put("client",Liquid.Client);
                dataObject.put("fullname",Liquid.UserFullname);
                dataObject.put("user_id",Liquid.User);
                dataObject.put("message",message);
                dataObject.put("latitude",String.valueOf(mGPS.getLatitude()));
                dataObject.put("longitude",String.valueOf(mGPS.getLongitude()));
                mPOSTUMSAPI = new Liquid.POSTUMSAPI("athena/php/api/Athena.php");
                Log.i(TAG, "Start processing..");
                String jsonStr = sh.makeServicePostCall(mPOSTUMSAPI.API_Link,dataObject);
                Log.i(TAG, String.valueOf(jsonStr));
                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                Message = response.getString("return");
                HashMap<String, String> data = new HashMap<>();
                data.put("Title","");
                data.put("Message",Message);
                data.put("TimeStamp",Liquid.currentDateTime());
                data.put("MessageType","1");
                TranslationMessage.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e){
                Log.e(TAG,"Error :",e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Items = Items + 1;
            mChatRecyclerView.scrollToPosition(Items - 1);
            if(TranslationMessage.size() == 0){
                Message = "Please connect to internet so i can analyze what are you saying. I don't have local brain.";
                HashMap<String, String> data = new HashMap<>();
                data.put("Title","");
                data.put("Message",Message);
                data.put("TimeStamp",Liquid.currentDateTime());
                data.put("MessageType","1");
                TranslationMessage.add(data);
            }
            AthenaSpeak(Message);
            mTranslatorAdapater.updateItems(false,TranslationMessage);
            AnalyzingLoading();

        }
    }



    @Override
    public void onInit(int i) {
        Log.i(TAG,"Start");
        if(i == TextToSpeech.SUCCESS){
            // Change this to match your
            // locale
            //tts.setLanguage(Locale.KOREAN);
            tts.setLanguage(Locale.US);
            //tts.setPitch((float) 0.8);
            //tts.setSpeechRate((float) 1.0);
            allow(true);
            ready = true;

            mTranslatorAdapater.updateItems(true,WelcomeMessage());

        }else{
            ready = false;
            Log.i(TAG,"NO");
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void allow(boolean allowed){
        this.allowed = allowed;
    }

    public void speak(String text){
        // Speak only if the TTS is ready
        // and the user has allowed speech
        Log.i(TAG,ready + "-"+ allowed);
        if(ready && allowed) {
            HashMap<String, String> hash = new HashMap<String,String>();
            hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_NOTIFICATION));
            //tts.speak(text, TextToSpeech.QUEUE_ADD, hash);
            Log.i(TAG,text);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        }

    }

    public void pause(int duration){
        tts.playSilence(duration, TextToSpeech.QUEUE_ADD, null);
    }

    public void stopSpecking(){
        if(tts !=null){
            tts.stop();
        }
    }
    // Free up resources
    public void destroy(){

        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        // tts.shutdown();
    }

}
