package com.aseyel.tgbl.tristangaryleyesa.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.NewTripActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;



public class LiquidPrintTripTicket {
    private static final String TAG = LiquidPrintTripTicket.class.getSimpleName();
    private static BluetoothDevice bluetoothDevice;
    private static OutputStream outputStream;
    private static BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    public static int RelativeOffset = 0;
    static int mm = 8;
    static String BarCode;
    static int[] Columns;
    static int Ypos, FontSize, Margin, Padding, LinePad, LineHeight, LineGap;
    static int DetailHeight, FooterHeight, TotalHeight, HeaderTop;
    static int HeaderHeight, SectionHeight, TopSectionHeight, TitleHeight, SubtitleHeight, ThreeMosHeight;
    static int Notice1Height, Notice2Height, Notice3Height, DetailSubtotalHeight, SeparatorHeight, SummaryPos, TotalPos;
    static int PaperMargin = 0;
    static int PaperWidth = 70;
    static int PrinterWidth = 71;
    static int PaperOffset = -1;
    static int t_rent_size = 0;
    static int p_rent_size = 0;
    static int s_cap_size = 0;
    static int o_bill_size = 0;
    static String NGCEXPRESSTitle = "Trip Ticket";


    public boolean pairPrinter()  {
        try{
            final UUID SerialPortServiceClass_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    BluetoothSocket socket;
                    BA.cancelDiscovery();
                    Set<BluetoothDevice> pairedDevice = BA.getBondedDevices();

                    if(pairedDevice.size() > 0){
                        for(BluetoothDevice pairedDev:pairedDevice){
                            //Name of Printer
                            if(pairedDev.getName().equals(pairedDev.getName())){
                                bluetoothDevice = pairedDev;

                                break;
                            }
                        }
                    }


                    try {


                        socket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(SerialPortServiceClass_UUID);

                        if (!socket.isConnected()) {
                            socket.connect();
                            Thread.sleep(1000); // <-- WAIT FOR SOCKET
                        }

                        outputStream = socket.getOutputStream();
                        SetPageParams();
                        WriteTitleNGCExpress(0);
                        WriteHeaderNGCExpress(0);
                        Thread.sleep(5000);
                        outputStream.close();
                        BA.cancelDiscovery();

                    } catch (IOException e) {
                        Log.e("","IOException");
                        e.printStackTrace();
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        Log.e(TAG,"Error :",e);

                    }

                }
            });

            t.start();
            return true;
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return false;
        }

    }

    private static void SetPageParams()
    {
        Columns = new int[3];
        Margin = (PaperMargin * 8);
        int PrintWidth = (PaperWidth * 8);

        if (PrintWidth > PrinterWidth * 8)
        {
            int diff = PrintWidth - (PrinterWidth * 8);
            Margin -= diff / 2;
            if (Margin < 0) Margin = 0;
            PrintWidth = (PrinterWidth * 8);
        }

        Columns[2] = PrintWidth - Margin * 2;

        FontSize = 8;
        LineHeight = 30;
        Padding = 5;
        LineGap = 7;
        LinePad = 4;

        if (LiquidBilling.total_amount_due < 100000)
        {
            Columns[0] = Columns[2] * 59 / 100;
            Columns[1] = Columns[2] * 77 / 100;
        }
        else
        {
            Columns[0] = Columns[2] * 56 / 100;
            Columns[1] = Columns[2] * 75 / 100;
        }

        Margin = ((PrinterWidth * 8) - Columns[2]) / 2;
        if (PaperOffset >= 0)
        {
            Margin = (PaperOffset * 8);
        }
        Margin += RelativeOffset * 4;
        if (Margin < 0) Margin = 0;
        if (Columns[2] > (PrinterWidth * 8) - Margin)
            Columns[2] = (PrinterWidth * 8) - Margin;

        TitleHeight = LineHeight + 2 * Padding + 2 * LineGap + LinePad;
        SubtitleHeight = LineHeight;
        DetailSubtotalHeight = LineHeight + 2 * Padding;
        SectionHeight = TitleHeight + 3 * LineHeight + 4 * LineGap;
        ThreeMosHeight = 2 * LineHeight + 2 * LineGap + 2 * LinePad;
        TopSectionHeight = TitleHeight + 2 * 24 + 12 + 2 * 14;
        HeaderHeight = TopSectionHeight + SectionHeight + LineGap + 4 + LineHeight + ThreeMosHeight;

        Notice1Height = LineHeight * 1 + mm * 2 - 1;
        Notice2Height = LineHeight * 4 + mm * 2 - 1;
        Notice3Height = LineHeight * 3 + mm * 2 - 1;

        TotalHeight = 43 + 2 * Padding;
        SeparatorHeight = 2 * LineGap;


    }

    public static void WriteTitleNGCExpress(int section) {
        try {
            int LogoHeight = 142;
            int LogoMargin = 16;
            int BillTitleHeight = 10;
            int BillTitleMargin = 0;
            int BillNoHeight = LineHeight-20;
            int TotalSize = LogoHeight + LogoMargin + BillTitleHeight + BillTitleMargin + BillNoHeight * 2 + 2 * mm + 6;

            String data = "! "+Margin+" 200 200 "+ TotalSize +" 1\r\n";
            data+=PrintCenter(Columns[2] + Margin);
            data+="PCX 0 0 !<NGC.PCX\r\n";
                     Ypos += LineHeight;
            data+=PrintText("5", 0, LogoHeight + LogoMargin + LineHeight - 25, NGCEXPRESSTitle);
            data+=PrintText("5", 0, LogoHeight + LogoMargin + BillNoHeight + 6 + BillTitleHeight + 5, "Ctrl Number: " + NewTripActivity.CtrlNumber);
            data+="PRINT\r\n";

            outputStream.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void WriteHeaderNGCExpress(int section)
    {
        int total_height = 110 + LineHeight * 21 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";
        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;
        data+=PrintLeft();
        data+=PrintText("5", 0, Ypos, Liquid.repeatChar('_',48));
        Ypos += LineHeight;
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "From : " + Liquid.SearchLocation);
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "To : " + Liquid.GoingToLocation);
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, Liquid.repeatChar('_',48));
        Ypos += LineHeight;
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "DEPARTURE");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Time: "+ Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Passenger: " + Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Driver's Name: " + NewTripActivity.DriversName);
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Driver's Signature: "+ Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "JGSPC Representative:" + Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, Liquid.repeatChar('_',48));
        Ypos += LineHeight;
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "ARRIVAL");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Time: "+ Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Passenger: " + Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Driver's Name: " + NewTripActivity.DriversName);
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Driver's Signature: "+ Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "JGSPC Representative:" + Liquid.repeatChar('_',25));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, Liquid.repeatChar('_',48));
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Powered by : UMS");
        data+="PRINT\r\n";
        try {

            outputStream.write(data.getBytes());
            Log.i(TAG,data);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String PrintLeft()
    {
        return "LEFT\r\n";
    }


    private static String PrintLine(int x1, int y1, int x2, int y2)
    {
        return "LINE "+x1+" "+y1+" "+x2+" "+y2+" 1\r\n";
    }

    private static String PrintText(String font, int x, int y, Object text)
    {
        try{
            font = String.format(font, FontSize);
            String setup = "T "+font+" 0 "+x+" "+y+" "+text+"\r\n";
            return  setup;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String PrintText(String font, int size, int x, int y, Object text)
    {
        try{
            font = String.format(font, FontSize);
            String setup = "T "+font+" "+size+" "+x+" "+y+" "+text+"\r\n";
            return  setup;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String PrintCenter(int offset)
    {
        try {
            String setup  = "CENTER " + offset +"\r\n";
            return setup;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
