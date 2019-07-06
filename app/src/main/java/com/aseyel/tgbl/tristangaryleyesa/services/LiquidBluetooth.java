package com.aseyel.tgbl.tristangaryleyesa.services;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.aseyel.tgbl.tristangaryleyesa.BaseActivity;
import com.aseyel.tgbl.tristangaryleyesa.BaseFormActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class LiquidBluetooth extends BaseActivity {

    private static final String TAG = LiquidBluetooth.class.getSimpleName();
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private BluetoothAdapter bluetoothAdapter;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Thread thread;
    Fragment mFragment;

    private byte[] readBuffer;
    private int readBufferPosition;
    private volatile boolean stopWorker;



    //open Bluetooth Printer
    public void openBluetoothPrinter()  throws IOException {
        try{
            //Standard uuid from string
            UUID uuidString = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuidString);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();

            beginListenData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void beginListenData(){
        try{
            final Handler handler = new Handler();
            final byte delimeter = 10;
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!Thread.currentThread().isInterrupted() && !stopWorker){
                        try{
                            int byteAvailable = inputStream.available();
                            if(byteAvailable > 0){
                                byte[] packageByte = new byte[byteAvailable];
                                inputStream.read(packageByte);

                                for(int i = 0; i < byteAvailable; i++){
                                    byte b = packageByte[i];
                                    if(b == delimeter){
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer,0,
                                                encodedByte,0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte,"US-ASCII");
                                        readBufferPosition = 0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                //lblPrinterName.setText(data);
                                            }
                                        });
                                    }else{
                                        readBuffer[readBufferPosition++]=b;
                                    }
                                }

                            }
                        }catch (Exception e){
                            stopWorker = true;
                        }
                    }
                }
            });
            thread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void FindBluetoothDevice(){
        try{

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(bluetoothAdapter == null){
                //lblPrinterName.setText("No Bluetooth Adapter found!");
                Liquid.showDialogError(getApplication(),"Invalid","No Bluetooth Adapter found!");
            }

            if(bluetoothAdapter.isEnabled()){
               // Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(enableBT,0);
            }else{
                bluetoothAdapter.enable();
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if(pairedDevice.size() > 0){
                for(BluetoothDevice pairedDev:pairedDevice){
                    //Name of Printer
                    if(pairedDev.getName().equals("UMS")){
                        bluetoothDevice = pairedDev;
                        //lblPrinterName.setText("Bluetooth Printer Attached: "+pairedDev.getName());
                        break;
                    }
                }
            }

            //lblPrinterName.setText("Bluetooth Printer Attached!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // Printing Text to Bluetooth Printer //
    public void printData() throws IOException{
        try{
            String msg = "Test";
            msg+="\n";
            outputStream.write(msg.getBytes());
            //lblPrinterName.setText("Printing Text...");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void disconnectBT() throws IOException{
        try{
            stopWorker = true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            //lblPrinterName.setText("Printer Disconnected.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String AlignCenter(String msg){

        return msg;
    }




}
