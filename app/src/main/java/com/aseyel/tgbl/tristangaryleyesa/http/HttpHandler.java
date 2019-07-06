package com.aseyel.tgbl.tristangaryleyesa.http;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tgbleyesa on 07/10/2017.
 */

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();
    Liquid.ApiData mApiData;
    private ProgressDialog pDialog;
    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {

            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream(),8192);
            response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public String makeServicePostCall(String reqUrl,JSONObject postData) {
        String response = null;
        try {

            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            // Send the post body
            if (postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }
            // read the response

            InputStream in = new BufferedInputStream(conn.getInputStream(),8192);
            response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"Error : ",e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,"Error : ",e);
            }
        }


        return sb.toString();
    }

    public void doPostToServer(final View view,
                                 String ApiLink,
                                 final String[] columns,
                                 final String[] values){
            try{
                StringRequest mStringRequest = new StringRequest(Request.Method.POST,ApiLink, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray final_response = Liquid.StringToJson(response);
                        Log.i(TAG,response);


                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Liquid.ShowMessage(view.getContext(), String.valueOf(error));
                        Liquid.UploadResult = false;
                    }
                }){
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        for(int a = 0; a < columns.length;a++){
                           params.put(columns[a],values[a]);
                        }
                        Log.i(TAG,params.toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                requestQueue.add(mStringRequest);
            }catch(Exception e){
                Log.e(TAG,"Error ",e);
            }
    }

    public void doBulkPostToServerUnecrypted(final Context context,
                                   String ApiLink,
                                   final JSONObject final_data
    ){

        try{
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


            StringRequest   mStringRequest   = new StringRequest (Request.Method.POST,ApiLink ,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,response);
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                   JSONObject final_response =  Liquid.NotEncryptedStringToJsonObject(response);
                    try {
                        Log.i(TAG,response);
                        if(final_response.getString("result") == "true"){
                            Liquid.showDialogInfo(context,"Valid",final_response.getString("message"));
                        }else{
                            Liquid.showDialogInfo(context,"Invalid",final_response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Liquid.showDialogError(context,"Invalid", String.valueOf(error));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return final_data == null ? null : final_data.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", final_data, "utf-8");
                        return null;
                    }
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(mStringRequest);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }


    public void doBulkPostToServerNoReturn(final View view,
                                   String ApiLink,
                                   final JSONObject final_data

    ){
        try{
            StringRequest   mStringRequest   = new StringRequest (Request.Method.POST,ApiLink ,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                        Log.i(TAG,response);
                        JSONObject final_response = Liquid.StringToJsonObject(response);
                    try {
                        if(final_response.getString("result") == "true"){

                        }else{
                            Liquid.ErrorUpload.put(final_data);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Liquid.showDialogError(view.getContext(),"Invalid", String.valueOf(error));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return final_data == null ? null : final_data.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", final_data, "utf-8");
                        return null;
                    }
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(mStringRequest);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }

    public void doBulkPostToServer(final View view,
                                   String ApiLink,
                                   final JSONObject final_data
                                  ){

        try{

            StringRequest   mStringRequest   = new StringRequest (Request.Method.POST,ApiLink ,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.i(TAG,response);
                        JSONObject final_response = Liquid.StringToJsonObject(response);
                            if(final_response.getString("result") == "true"){
                                Liquid.showDialogInfo(view.getContext(),"Valid",final_response.getString("message"));
                            }else{
                                Liquid.showDialogInfo(view.getContext(),"Invalid",final_response.getString("message"));
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Liquid.showDialogError(view.getContext(),"Invalid", String.valueOf(error));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return final_data == null ? null : final_data.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", final_data, "utf-8");
                        return null;
                    }
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(mStringRequest);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }
}
