package com.aseyel.tgbl.tristangaryleyesa.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.util.Log;
import android.widget.Toast;


import com.aseyel.tgbl.tristangaryleyesa.BaseActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.io.IOException;

import java.io.OutputStream;

import java.util.Set;
import java.util.UUID;

import java.util.stream.Stream;

import com.aseyel.tgbl.tristangaryleyesa.model.Bill;
import com.aseyel.tgbl.tristangaryleyesa.model.BillItem;
import com.aseyel.tgbl.tristangaryleyesa.model.ItemStyle;

import static com.aseyel.tgbl.tristangaryleyesa.model.Bill.CountDetails;

public class LiquidPrintBill {
    private static final String TAG = LiquidPrintBill.class.getSimpleName();
    private static BluetoothDevice bluetoothDevice;
    private static OutputStream outputStream;
    static BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    static String CompanyName = "BATELEC II";
    static byte FONT_TYPE;

    public static int RelativeOffset = 0;
    static int mm = 8;
    static String BarCode;
    static int[] Columns;
    static int Ypos, FontSize, Margin, Padding, LinePad, LineHeight, LineGap;
    static int DetailHeight, FooterHeight, TotalHeight, HeaderTop;
    static int HeaderHeight, SectionHeight, TopSectionHeight, TitleHeight, SubtitleHeight, ThreeMosHeight;
    static int Notice1Height, Notice2Height, Notice3Height, DetailSubtotalHeight, SeparatorHeight, SummaryPos, TotalPos;
    static int HeaderSize, m1_size, m2_size, m3_size, m4_size, m5_size, m6_size, m7_size, m8_size,
            m9_size, m10_size, m11_size, m12_size, m13_size, m14_size, m15_size, m16_size, m17_size,
            m18_size, m19_size, m20_size, m21_size, m22_size, m23_size, m24_size, m25_size, m26_size,
            m27_size, m28_size, m29_size, m30_size;
    static boolean PrintToFile;
    static Stream PrintStream;
    static int PaperMargin = 0;
    static int PaperWidth = 70;
    static int PrinterWidth = 71;
    static int PaperOffset = -1;
    static int t_rent_size = 0;
    static int p_rent_size = 0;
    static int s_cap_size = 0;
    static int o_bill_size = 0;

    static String Batelec2BillTitle = "STATEMENT OF ACCOUNT";
    static String Iselco2BillTitle = "BILLING NOTICE";
    static String Ileco2BillTitle = "Statement of Account";
    private Bill mBill;


    public boolean pairPrinter()  {
        try{
            BarCode = Liquid.AccountNumber;
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
                        mBill = new Bill();
                        socket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(SerialPortServiceClass_UUID);

                        if (!socket.isConnected()) {
                            socket.connect();
                            Thread.sleep(1000); // <-- WAIT FOR SOCKET
                        }

                        outputStream = socket.getOutputStream();

                        switch(Liquid.Client){
                            case "batelec2":
                                SetPageParams();
                                WriteTitleBatelec2(0);
                                WriteHeaderBatelec2(1);
                                WriteDetailsBatelec2(mBill.BillItemsBatelec2);
                                WriteNoticeBatelec2();
                                break;
                            case "iselco2":
                                SetPageParams();
                                WriteTitleIselco2(0);
                                WriteHeaderIselco2(1);
                                WriteDetailsIselco2(mBill.BillItemsIselco2);
                                WriteNoticeIselco2();
                                break;
                            case "ileco2":
                                SetPageParams(); //ok
                                WriteTitleIleco2(0); //ok
                                WriteHeaderIleco2(1); //ok
                                WriteDetailsIleco2(mBill.BillItemsIleco2);
                                if(LiquidBilling.arrears > 0)
                                    WriteOptionalNoticeIleco2();
                                WriteNoticeIleco2();
                                break;
                            case "more_power":
                                SetPageParams(); //ok
                                WriteTitleMorePower(0); //ok
                                WriteHeaderMorePower(1); //ok
                                WriteDetailsMorePower(mBill.BillItemsMorePower);

                                WriteDashedLine(2);

                                WriteTitleMorePower(0); //ok
                                WriteHeaderMorePower(1); //ok
                                WriteDetailsMorePower(mBill.BillSummaryMorePower);
                                //WriteNoticeIleco2();
                                WriteFooter2();

                                break;
                            case "pelco2":
                                SetPageParams(); //ok
                                WriteTitlePelco2(0); // created
                                WriteHeaderPelco2(1); // created
                                WriteDetailsPelco2(mBill.BillItemsPelco2); //created
                                WriteNoticePelco2(); //created
                                WriteFootnotePelco2(1); // created
                                break;
                            case "baliwag_wd":
                                SetPageParams(); //ok
                                WriteTitleBaliwagWD(0); //ok
                                WriteHeaderBaliwagWD(1); //ok
                                WriteDetailsBaliwagWD(mBill.BillItemsBaliwagWD);
                                WriteNoticeBaliwagWD();
                                break;
                            default:
                                SetPageParams(); //ok
                                WriteTitleMorePower(0); //ok
                                WriteHeaderIleco2(1); //ok
                                WriteDetailsIleco2(mBill.BillItemsIleco2);
                                WriteNoticeIleco2();


                        }

                        Liquid.PrintResponse = true;
                        Thread.sleep(5000);
                        outputStream.close();
                        BA.cancelDiscovery();
                    } catch (IOException e) {
                        Log.e(TAG,"IOException");
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
            Log.e(TAG,"Error ",e);
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
    public static void WriteTitleMorePower(int section) {
        try {
            int LogoHeight = 142;
            int LogoMargin = 16;
            int BillTitleHeight = 10;
            int BillTitleMargin = 0;
            int BillNoHeight = LineHeight-20;
            int TotalSize = LogoHeight + LogoMargin + BillTitleHeight + BillTitleMargin + BillNoHeight * 2 + 2 * mm + 6;

            String data = "! "+Margin+" 200 200 "+ TotalSize +" 1\r\n"
                    +PrintCenter(Columns[2] + Margin)
                    +"PCX 0 0 !<MORE_POW.PCX\r\n"
                    +PrintText("4", 0, LogoHeight + LogoMargin + LineHeight - 25, Ileco2BillTitle)
                    +"PRINT\r\n";

            outputStream.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteTitleBaliwagWD(int section) {
        try {
            int LogoHeight = 142;
            int LogoMargin = 16;
            int BillTitleHeight = 10;
            int BillTitleMargin = 0;
            int BillNoHeight = LineHeight-20;
            int TotalSize = LogoHeight + LogoMargin + BillTitleHeight + BillTitleMargin + BillNoHeight * 8 + 2 * mm + 6;

            String data = "! "+Margin+" 200 200 "+ TotalSize +" 1\r\n"
                    +PrintCenter(Columns[2] + Margin)
                    +"PCX 0 0 !<BALIWAG.PCX\r\n"
                    +PrintText("4", 0, LogoHeight + LogoMargin + LineHeight - 25, "BILLING NOTICE")
                    +PrintText("5", 0, LogoHeight + LogoMargin + LineHeight *2, "SIN: "+Liquid.C_ID)
                    +PrintText("5", 0, LogoHeight + LogoMargin + LineHeight *3, "Bill Period: "+Liquid.BillingCycle)
                    +"PRINT\r\n";

            outputStream.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteTitlePelco2(int section) {
        try {
            int LogoHeight = 142;
            int LogoMargin = 16;
            int BillTitleHeight = 10;
            int BillTitleMargin = 0;
            int BillNoHeight = LineHeight-20;
            int TotalSize = LogoHeight + LogoMargin + BillTitleHeight + BillTitleMargin + BillNoHeight * 2 + 2 * mm + 6;

            String data = "! "+Margin+" 200 200 "+ TotalSize +" 1\r\n"
                    +PrintCenter(Columns[2] + Margin)
                    +"PCX 0 0 !<PELCO2.PCX\r\n"
                    +PrintText("4", 0, LogoHeight + LogoMargin + LineHeight - 25, Ileco2BillTitle)
                    +"PRINT\r\n";

            outputStream.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteTitleIleco2(int section) {
        try {
            int LogoHeight = 142;
            int LogoMargin = 16;
            int BillTitleHeight = 10;
            int BillTitleMargin = 0;
            int BillNoHeight = LineHeight-20;
            int TotalSize = LogoHeight + LogoMargin + BillTitleHeight + BillTitleMargin + BillNoHeight * 2 + 2 * mm + 6;

            String data = "! "+Margin+" 200 200 "+ TotalSize +" 1\r\n"
                    +PrintCenter(Columns[2] + Margin)
                    +"PCX 0 0 !<ILECO2.PCX\r\n"
                    +PrintText("4", 0, LogoHeight + LogoMargin + LineHeight - 25, Ileco2BillTitle)
                    +"PRINT\r\n";

            outputStream.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void WriteTitleIselco2(int section) {
        try {
            int LogoHeight = 142;
            int LogoMargin = 16;
            int BillTitleHeight = 10;
            int BillTitleMargin = 0;
            int BillNoHeight = LineHeight-20;
            int TotalSize = LogoHeight + LogoMargin + BillTitleHeight + BillTitleMargin + BillNoHeight * 2 + 2 * mm + 6;

            String data = "! "+Margin+" 200 200 "+ TotalSize +" 1\r\n"
                    +PrintCenter(Columns[2] + Margin)
                    +"PCX 0 0 !<ISELCO2.PCX\r\n"
                    +PrintText("5", 0, LogoHeight + LogoMargin + LineHeight - 25, Iselco2BillTitle)
                    +"PRINT\r\n";

            outputStream.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void WriteTitleBatelec2(int section) {
        try {
            int LogoHeight = 142;
            int LogoMargin = 16;
            int BillTitleHeight = 10;
            int BillTitleMargin = 0;
            int BillNoHeight = LineHeight-20;
            int TotalSize = LogoHeight + LogoMargin + BillTitleHeight + BillTitleMargin + BillNoHeight * 2 + 2 * mm + 6;

            String data = "! "+Margin+" 200 200 "+ TotalSize +" 1\r\n"
                    +PrintCenter(Columns[2] + Margin)
                    +"PCX 0 0 !<BATELEC2.PCX\r\n"
                    +PrintText("5", 0, LogoHeight + LogoMargin + LineHeight - 25, Batelec2BillTitle)
                    +PrintText("5", 0, LogoHeight + LogoMargin + BillNoHeight + 6 + BillTitleHeight + 5, "Power Bill No: " + Liquid.bill_number)
                    +"PRINT\r\n";

            outputStream.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int ComputeDetailHeightIselco2(BillItem[] items)
    {
        return
                CountDetails(items, ItemStyle.Header) * TitleHeight
                        + CountDetails(items, ItemStyle.Subtitle) * LineHeight
                        + CountDetails(items, ItemStyle.Detail) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalDetail) * LineHeight
                        + CountDetails(items, ItemStyle.Separator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.OptionalSeparator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.Subtotal) * DetailSubtotalHeight
                        + CountDetails(items, ItemStyle.Footer) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooter) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooter2) * LineHeight
                        + CountDetails(items, ItemStyle.Notice1) * Notice1Height
                        + CountDetails(items, ItemStyle.Notice2) * Notice2Height
                        + CountDetails(items, ItemStyle.Notice3) * Notice3Height
                        + CountDetails(items, ItemStyle.Total) * TotalHeight
                        + CountDetails(items, ItemStyle.Footnote) * LineHeight
                        + CountDetails(items, ItemStyle.SummaryHeader) * LineHeight
                        + CountDetails(items, ItemStyle.Aftertotal) * (LineHeight - 35 + Padding);
    }
    private static int ComputeDetailHeightMorePower(BillItem[] items)
    {
        return
                CountDetails(items, ItemStyle.Header) * TitleHeight
                        + CountDetails(items, ItemStyle.Subtitle) * LineHeight
                        + CountDetails(items, ItemStyle.Detail) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalDetail) * LineHeight
                        + CountDetails(items, ItemStyle.Separator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.OptionalSeparator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.Subtotal) * DetailSubtotalHeight
                        + CountDetails(items, ItemStyle.Footer) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooter) * LineHeight
                        //+ CountDetails(items, ItemStyle.OptionalFooter2) * LineHeight
                        + CountDetails(items, ItemStyle.Notice1) * Notice1Height
                        + CountDetails(items, ItemStyle.Notice2) * Notice2Height
                        + CountDetails(items, ItemStyle.Notice3) * Notice3Height
                        + CountDetails(items, ItemStyle.Total) * TotalHeight
                        + CountDetails(items, ItemStyle.Footnote) * LineHeight
                        + CountDetails(items, ItemStyle.SummaryHeader) * LineHeight
                        + CountDetails(items, ItemStyle.Aftertotal) * (LineHeight - 10 + Padding) + 35;
    }
    private static int ComputeDetailHeightIleco2(BillItem[] items)
    {
        return
                CountDetails(items, ItemStyle.Header) * TitleHeight
                        + CountDetails(items, ItemStyle.Subtitle) * LineHeight
                        + CountDetails(items, ItemStyle.Detail) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalDetail) * LineHeight
                        + CountDetails(items, ItemStyle.Separator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.OptionalSeparator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.Subtotal) * DetailSubtotalHeight
                        + CountDetails(items, ItemStyle.Footer) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooter) * LineHeight
                        //+ CountDetails(items, ItemStyle.OptionalFooter2) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooterAdditional) * LineHeight
                        + CountDetails(items, ItemStyle.Notice1) * Notice1Height
                        + CountDetails(items, ItemStyle.Notice2) * Notice2Height
                        + CountDetails(items, ItemStyle.Notice3) * Notice3Height
                        + CountDetails(items, ItemStyle.Total) * TotalHeight
                        + CountDetails(items, ItemStyle.Footnote) * LineHeight
                        + CountDetails(items, ItemStyle.SummaryHeader) * LineHeight
                        + CountDetails(items, ItemStyle.Aftertotal) * (LineHeight - 10 + Padding);
    }
    private static int ComputeDetailHeightPelco2(BillItem[] items)
    {
        return
                CountDetails(items, ItemStyle.Header) * TitleHeight
                        + CountDetails(items, ItemStyle.Subtitle) * LineHeight
                        + CountDetails(items, ItemStyle.Detail) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalDetail) * LineHeight
                        + CountDetails(items, ItemStyle.Separator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.OptionalSeparator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.Subtotal) * DetailSubtotalHeight
                        + CountDetails(items, ItemStyle.Footer) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooter) * LineHeight
                        //+ CountDetails(items, ItemStyle.OptionalFooter2) * LineHeight
                        + CountDetails(items, ItemStyle.Notice1) * Notice1Height
                        + CountDetails(items, ItemStyle.Notice2) * Notice2Height
                        + CountDetails(items, ItemStyle.Notice3) * Notice3Height
                        + CountDetails(items, ItemStyle.Total) * TotalHeight
                        + CountDetails(items, ItemStyle.Footnote) * LineHeight
                        + CountDetails(items, ItemStyle.FooterTitle) * LineHeight
                        + CountDetails(items, ItemStyle.SummaryHeader) * LineHeight
                        + CountDetails(items, ItemStyle.Aftertotal) * (LineHeight - 10 + Padding);
    }
    private static int ComputeDetailHeightBaliwagWD(BillItem[] items)
    {
        return
                CountDetails(items, ItemStyle.Header) * TitleHeight
                        + CountDetails(items, ItemStyle.Subtitle) * LineHeight
                        + CountDetails(items, ItemStyle.Detail) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalDetail) * LineHeight
                        + CountDetails(items, ItemStyle.Separator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.OptionalSeparator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.Subtotal) * DetailSubtotalHeight
                        + CountDetails(items, ItemStyle.Footer) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooter) * LineHeight
                        //+ CountDetails(items, ItemStyle.OptionalFooter2) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooterAdditional) * LineHeight
                        + CountDetails(items, ItemStyle.Notice1) * Notice1Height
                        + CountDetails(items, ItemStyle.Notice2) * Notice2Height
                        + CountDetails(items, ItemStyle.Notice3) * Notice3Height
                        + CountDetails(items, ItemStyle.Total) * TotalHeight
                        + CountDetails(items, ItemStyle.Footnote) * LineHeight
                        + CountDetails(items, ItemStyle.SummaryHeader) * LineHeight
                        + CountDetails(items, ItemStyle.Aftertotal) * (LineHeight - 5 + Padding);
    }
    private static int ComputeDetailHeight(BillItem[] items)
    {
        return
                CountDetails(items, ItemStyle.Header) * TitleHeight
                        + CountDetails(items, ItemStyle.Subtitle) * LineHeight
                        + CountDetails(items, ItemStyle.Detail) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalDetail) * LineHeight
                        + CountDetails(items, ItemStyle.Separator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.OptionalSeparator) * SeparatorHeight
                        + CountDetails(items, ItemStyle.Subtotal) * DetailSubtotalHeight
                        + CountDetails(items, ItemStyle.Footer) * LineHeight
                        + CountDetails(items, ItemStyle.OptionalFooter) * LineHeight
                        //+ CountDetails(items, ItemStyle.OptionalFooter2) * LineHeight
                        + CountDetails(items, ItemStyle.Notice1) * Notice1Height
                        + CountDetails(items, ItemStyle.Notice2) * Notice2Height
                        + CountDetails(items, ItemStyle.Notice3) * Notice3Height
                        + CountDetails(items, ItemStyle.Total) * TotalHeight
                        + CountDetails(items, ItemStyle.Footnote) * LineHeight
                        + CountDetails(items, ItemStyle.SummaryHeader) * LineHeight
                        + CountDetails(items, ItemStyle.Aftertotal) * (LineHeight - 5 + Padding);
    }

    private static String WriteDetail(String label, Object a, Object b)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_DETAIL_LINE = "{0,-40}{1,10:n4}{2,15:n2}";
            // data+=(FORMAT_DETAIL_LINE, label, a, b);
        }

        else
        {

            data+=PrintText("7", Padding + 6, Ypos + LinePad, label);
            data+=PrintRight(Columns[1] + Margin - Padding - 1);
            data+=PrintText("7", 0, Ypos + LinePad, Liquid.StringRoundDown4D(Double.parseDouble(String.valueOf(a))));
            data+=PrintRight(Columns[2] + Margin - Padding);
            data+=PrintText("7", 0, Ypos + LinePad,  Liquid.NumberFormat(String.valueOf(b)));
            Ypos += LineHeight;
            data+=PrintLeft();
        }
        return data;
    }


    private static String WriteTableHeader(String label, String a, String b)
    {
        String data = "";
        if (PrintToFile)
        {
            final int width = 65;
            //String HorizontalLine = new String('-', width);
            final String FORMAT_DETAIL_TITLE = "{0,-40}{1,10}{2,15:n2}";

            // data+=HorizontalLine;
            // data+=(FORMAT_DETAIL_TITLE, label, a, b);
            //data+=HorizontalLine;
        }

        else
        {
            Ypos += LineGap;
            int starty = Ypos;
            data+=PrintLine(0, Ypos, Columns[2], Ypos);
            Ypos += Padding;
            if (LiquidBilling.total_amount_due < 100000)
            {
                HeaderTop = TitleHeight;
                data+=PrintLine(Columns[0], starty + 2, Columns[0], HeaderTop);
                data+=PrintLine(Columns[1], starty + 2, Columns[1], HeaderTop);
            }
            data+=PrintText("5", Padding + 4, Ypos + LinePad + 1, label);
            data+=PrintRight(Columns[1] + Margin - Padding - 2);
            data+=PrintText("5", Columns[0] + Padding, Ypos + LinePad + 1, a);
            data+=PrintRight(Columns[2] + Margin - Padding);
            data+=PrintText("5", Columns[1] + Padding, Ypos + LinePad + 1, b);
            data+=PrintLeft();
            Ypos += LineHeight + Padding;
            data+=PrintLine(0, Ypos - 1, Columns[2], Ypos - 1);
            Ypos += LineGap;
        }
        return data;
    }
    private static String WriteSubtitle(String label)
    {
        String data = "";
        if (PrintToFile)
            data+=label;

        else
        {
            data+=PrintText("5", Padding + 4, Ypos, label.toUpperCase());
            Ypos += LineHeight;
        }
        return data;
    }

    private static String WriteFootnote(String label, String value)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,44}{1,-21}";
            //data+=(FORMAT_FOOTER_LINE, label, value);
        }
        else
        {
            data+=PrintText("7", 34, Ypos + LinePad, label + " " + value);
            Ypos += LineHeight;
        }
        return data;
    }

    private static void WriteFootnotePelco2(int section)
    {
        try
        {
            int totalHeaderHeight = HeaderHeight - 180 + mm;
            String data = "! "+Margin+" 200 200 "+totalHeaderHeight+" 1\r\n";
            data+=PrintLine(0, LineGap, 0, HeaderHeight - 4 - 230);// left
            data+=PrintLine(Columns[2], LineGap, Columns[2], HeaderHeight - 4 - 230);                       // right
            data+=PrintLine(0, HeaderHeight - 4 - 230, Columns[2], HeaderHeight - 4 - 230);                       // bottom
            data+=PrintLine(0, LineGap, Columns[2], LineGap);                       // top

            String billing_period = Liquid.dateChangeFormat(Liquid.BillMonth,"MM","MMM") +" "+Liquid.BillYear ;
            String due_date = Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","MMM dd, yyyy");

            int LineHeight = 15;
            Ypos = 10;
            data+=PrintCenter(Columns[2] + Margin);
            data+=PrintText("5", Padding + 18, Ypos  + LinePad + Ypos,"PELCO II PAYMENT SLIP");
            Ypos += LineHeight + 2;
            data+=PrintLeft();
            data+=PrintText("7", Padding + 18, Ypos  + LinePad + Ypos,"Account Number : " + Liquid.AccountNumber +" "+  Liquid.AccountName);
            Ypos += LineHeight;
            data+=PrintText("7", Padding + 18, Ypos  + LinePad + Ypos,"Billing Period : " + billing_period);
            Ypos += LineHeight;
            data+=PrintText("7", Padding + 18, Ypos  + LinePad + Ypos, "Due Date : " + due_date);
            Ypos += LineHeight;
            data+=PrintText("7", Padding + 18, Ypos  + LinePad + Ypos, "KWH Consumption : " + Liquid.FormatKWH(Liquid.Present_Consumption));
            Ypos += LineHeight;
            data+=PrintText("7", Padding + 18, Ypos  + LinePad + Ypos, "Total Amount Due : " + LiquidBilling.total_amount_due);
            Ypos += LineHeight + 10;
            data+=PrintText("7", Padding + 18, Ypos  + LinePad + Ypos, "");
            data+=PrintLeft();

            data+="PRINT\n";
            outputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String WriteAfterTotalIselco2(String label, String value)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,50}{1,15}";
            //data+=(FORMAT_FOOTER_LINE, label, value);
        }
        else
        {


            data+=PrintCenter(Columns[1] + Margin - Padding - 4);
            data+=PrintText("5", 34, Ypos + LinePad, label);
            data+=PrintRight(75 + Margin - Padding - 4);
            data+=PrintText("5", 370, Ypos + LinePad, value);
            data+=PrintLeft();
            Ypos += LineHeight;

        }
        return data;
    }

    private static String WriteAfterTotal(String label, String value)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,50}{1,15}";
            //data+=(FORMAT_FOOTER_LINE, label, value);
        }
        else
        {

            data+=PrintText("5", 34, Ypos + LinePad, label);
            data+=PrintRight(Columns[2] + Margin - Padding - 4);
            data+=PrintText("5", Columns[1], Ypos + LinePad, value);
            data+=PrintLeft();
            Ypos += LineHeight;
        }
        return data;
    }
    private static String WriteSeparator()
    {
        String data = "";
        if (PrintToFile)
        {
            final int width = 65;
            //String HorizontalLine = new String('-', width);
            //data+=HorizontalLine;
        }

        else
        {
            Ypos += LineGap;
            data+=PrintLeft();
            data+=PrintLine(0, Ypos, Columns[2], Ypos);
            Ypos += LineGap;
        }
        return data;
    }


    private static String WriteNotice(String label)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0}";
            String[] lines = label.split("|");
            //data+=(FORMAT_FOOTER_LINE, lines[0]);
            //data+=(FORMAT_FOOTER_LINE, lines[1]);
        }
        else
        {
            if (TotalPos == 0)
                TotalPos = Ypos - LineGap;
            data+=PrintCenter(Columns[2] + Margin);
            Ypos += mm + 1;
//            String[] lines = label.split(",");
            String[] lines = label.split(";");
            for (String line : lines)
            {
                data+=PrintText("5", 0, Ypos, line);
                Ypos += LineHeight + 1;
            }
            Ypos += mm - 5;
        }
        return data;
    }

    private static String WriteSubtotal(String label, double value)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,-40}{1,10:n4}{2,15:n2}";
            //data+=(FORMAT_FOOTER_LINE, "Subtotal", value);
        }
        else
        {
            Ypos += 2 * Padding;
            data+=PrintText("5", Padding + 4, Ypos + LinePad + 1, label);
            data+=PrintRight(Columns[1] + Margin - Padding - 1);
            data+=PrintText("5", 0, Ypos + LinePad + 1, "Subtotal");
            data+=PrintRight(Columns[2] + Margin - Padding);
            data+=PrintText("5", Columns[1], Ypos + LinePad + 1,Liquid.NumberFormat(String.valueOf(value)));
            data+=PrintLeft();
            Ypos += LineHeight;
        }
        return data;
    }

    private static String WriteTotal(String label, double total)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,50}{1,15:n2}";
            //data+=(FORMAT_FOOTER_LINE, label, total);
        }
        else
        {
            if (SummaryPos == 0)
                SummaryPos = Ypos - LineGap;
            if (TotalPos == 0)
                TotalPos = Ypos - LineGap;
            Ypos += Padding;
            data+=PrintText("5", 34, Ypos + 14, label);
            data+=PrintRight(Columns[2] + Margin - Padding - 4);
            data+=PrintText("4", Columns[1], Ypos, Liquid.NumberFormat(String.valueOf(total)));
            data+=PrintLeft();
            Ypos += 43 + Padding;
        }
        return data;
    }

    private static String WriteTotalIselco2(String label, double total)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,50}{1,15:n2}";
            //data+=(FORMAT_FOOTER_LINE, label, total);
        }
        else
        {
            if (SummaryPos == 0)
                SummaryPos = Ypos - LineGap;
            if (TotalPos == 0)
                TotalPos = Ypos - LineGap;
            Ypos += Padding;
            data+=PrintText("5", 34, Ypos + 14, label);
            data+=PrintRight(Columns[2] + Margin - Padding - 4);
            data+=PrintText("4", Columns[1], Ypos, total);
            data+=PrintLeft();
            Ypos += 43 + Padding;
        }
        return data;
    }

    private static String WriteSummaryHeader(String label, String value)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,50}{1,15}";
            // data+=(FORMAT_FOOTER_LINE, label, value);
        }
        else
        {
            data+=PrintText("5", 14, Ypos + LinePad, label);
            data+=PrintRight(Columns[2] + Margin - Padding - 4);
            data+=PrintText("5", Columns[1], Ypos + LinePad, value);
            data+=PrintLeft();
            Ypos += LineHeight;
        }
        return data;
    }
    private static void WriteDetailsBatelec2(BillItem[] items) {
        Ypos = 0;
        SummaryPos = 0;
        TotalPos = 0;
        HeaderTop = LineGap + 2;
        Ypos = 0;
        SummaryPos = 0;
        TotalPos = 0;
        HeaderTop = LineGap + 2;

        m1_size = 0;
        m2_size = 0;
        m3_size = 0;
        m4_size = 0;
        m5_size = 0;
        m6_size = 0;
        m7_size = 0;
        m8_size = 0;
        m9_size = 0;
        m10_size = 0;
        m11_size = 0;
        m12_size = 0;
        m13_size = 0;
        m14_size = 0;
        m15_size = 0;
        m16_size = 0;
        m17_size = 0;
        m18_size = 0;
        m19_size = 0;
        m20_size = 0;
        m21_size = 0;
        m22_size = 0;
        m23_size = 0;
        m24_size = 0;
        m25_size = 0;
        m26_size = 0;
        m27_size = 0;
        m28_size = 0;
        m29_size = 0;
        m30_size = 0;

        if (m30_size == 0)
        {
            m1_size = 30;
        }
        if (m30_size == 0)
        {
            m2_size = 30;
        }
        if (m30_size == 0)
        {
            m3_size = 30;
        }
        if (m30_size == 0)
        {
            m4_size = 30;
        }
        if (m30_size == 0)
        {
            m5_size = 30;
        }
        if (m30_size == 0)
        {
            m6_size = 30;
        }
        if (m30_size == 0)
        {
            m7_size = 30;
        }
        if (m30_size == 0)
        {
            m8_size = 30;
        }
        if (m30_size == 0)
        {
            m9_size = 30;
        }
        if (m30_size == 0)
        {
            m10_size = 30;
        }
        if (m30_size == 0)
        {
            m11_size = 30;
        }
        if (m30_size == 0)
        {
            m12_size = 30;
        }
        if (m30_size == 0)
        {
            m13_size = 30;
        }
        if (m30_size == 0)
        {
            m14_size = 30;
        }
        if (m30_size == 0)
        {
            m15_size = 30;
        }
        if (m30_size == 0)
        {
            m16_size = 30;
        }
        if (m30_size == 0)
        {
            m17_size = 30;
        }
        if (m30_size == 0)
        {
            m18_size = 30;
        }
        if (m30_size == 0)
        {
            m19_size = 30;
        }
        if (m30_size == 0)
        {
            m20_size = 30;
        }
        if (m30_size == 0)
        {
            m21_size = 30;
        }
        if (m30_size == 0)
        {
            m22_size = 30;
        }
        if (m30_size == 0)
        {
            m23_size = 30;
        }
        if (m30_size == 0)
        {
            m24_size = 30;
        }
        if (m30_size == 0)
        {
            m25_size = 30;
        }
        if (m30_size == 0)
        {
            m26_size = 30;
        }
        if (m30_size == 0)
        {
            m27_size = 30;
        }
        if (m30_size == 0)
        {
            m28_size = 30;
        }
        if (m30_size == 0)
        {
            m29_size = 30;
        }
        if (m30_size == 0)
        {
            m30_size = 30;
        }
        DetailHeight = ComputeDetailHeight(items);
        int total_height =  DetailHeight - HeaderSize;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        for (BillItem item : items)
        {
            switch (item.Style)
            {
                case Header:
                    data+=WriteTableHeader(item.Label, item.RateVar, item.AmountVar);
                    break;

                case Detail:
                    if (item.AmountVar == "")
                    {
                        data+=WriteDetail(item.Label, "", "");
                        break;
                    }
                    if (item.Label == "Life Rate Subsidy" && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Life line Discount" + "(" + Double.parseDouble(String.valueOf(Math.abs(LiquidBilling.lifeline * 100))) + "%)" , item.Rate(), item.Amount());
                        break;
                    }
                    if (item.AmountVar == "Senior Citizen Subsidy" && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Senior Citizen Discount", item.Rate(), item.Amount());
                        break;
                    }


                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case OptionalDetail:

                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case Subtitle:
                    data+=WriteSubtitle(item.Label);
                    break;

                case Separator:
                    data+=WriteSeparator();
                    break;

                case OptionalSeparator:
                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteSeparator();
                    break;

                case OptionalFooter:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteSummary(item.Label, Double.parseDouble(item.Amount()));
                    break;



                case Footer:
                    if (item.AmountVar == "M1" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;

                        break;
                    }
                    if (item.AmountVar == "M2" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M3" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;

                        break;
                    }

                    if (item.AmountVar == "M4" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M5" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M6" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M7" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M8" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M9" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M10" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M11" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M12" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M13" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M14" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M15" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M16" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M17" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M18" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M19" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M20" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M21" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M22" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M23" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M24" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M25" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M26" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M27" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M28" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M29" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "M30" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.AmountVar == "E1" && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }

                    data+=WriteSummary(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Notice1:
                case Notice2:
                case Notice3:
                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteNotice(item.Label);
                    break;

                case Subtotal:
                    data+=WriteSubtotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Total:
                    data+=WriteTotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footnote:
                    data+=WriteFootnote(item.Label, String.valueOf(item.Amount()));
                    break;

                case Aftertotal:
                    data+=WriteAfterTotal(item.Label, String.valueOf(item.Amount()));
                    break;

                case SummaryHeader:
                    data+=WriteSummaryHeader(item.Label, String.valueOf(item.Amount()));
                    break;
            }
        }

        if (!PrintToFile)
        {
            data+=PrintLine(0, LineGap + 2, 0, DetailHeight - LineGap - LinePad);
            if (LiquidBilling.total_amount_due < 100000)
            {

                if (TotalPos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], TotalPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], Ypos - LinePad);

                if (SummaryPos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], SummaryPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], Ypos - LinePad);
            }
            data+=PrintLine(Columns[2], LineGap + 2, Columns[2], DetailHeight - LineGap - LinePad);
            data+="PRINT\r\n";
        }

        try {

            outputStream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private static void WriteDetailsIselco2(BillItem[] items) {
        Ypos = 0;
        SummaryPos = 0;
        TotalPos = 0;
        HeaderTop = LineGap + 2;


        t_rent_size = 0;
        p_rent_size = 0;
        s_cap_size = 0;
        o_bill_size = 0;

        if (Double.parseDouble(Liquid.rentalfee) == 0)
        {
            t_rent_size = 30;
        }
        if (Double.parseDouble(Liquid.PoleRent) == 0)
        {
            p_rent_size = 30;
        }
        if (Double.parseDouble(Liquid.ShareCap) == 0)
        {
            s_cap_size = 30;
        }
        if (Double.parseDouble(Liquid.OtherBill) == 0)
        {
            o_bill_size = 30;
        }

        HeaderSize = t_rent_size + p_rent_size + s_cap_size + o_bill_size;

        if (1 <= 0)
        {
            DetailHeight = ComputeDetailHeightIselco2(items) - 60;
        }
        else
        {
            DetailHeight = ComputeDetailHeightIselco2(items);
        }
        DetailHeight = DetailHeight + 75;
        int total_height =  DetailHeight - HeaderSize;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        for (BillItem item : items)
        {
            switch (item.Style)
            {
                case Header:

                    data+=WriteTableHeader(item.Label, item.RateVar, item.AmountVar);
                    break;

                case Detail:

                    if (item.AmountVar.equals(""))
                    {
                        data+=WriteDetail(item.Label, "", "");
                        break;
                    }
                    if (item.Label == "Life Rate Subsidy" && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Life line Discount" + "(" + Double.parseDouble(String.valueOf(Math.abs(LiquidBilling.lifeline * 100))) + "%)" , item.Rate(), item.Amount());
                        break;
                    }
                    if (item.AmountVar == "Senior Citizen Subsidy" && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Senior Citizen Discount", item.Rate(), item.Amount());
                        break;
                    }

                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case OptionalDetail:

                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case Subtitle:
                    data+=WriteSubtitle(item.Label);
                    break;

                case Separator:
                    data+=WriteSeparator();
                    break;

                case OptionalSeparator:
                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteSeparator();
                    break;

                case OptionalFooter:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;



                case Footer:

                    if (item.Label.equals("Share Capital") && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.Label.equals("Pole Rental") && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.Label.equals("Transformer Rental") && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }
                    if (item.Label.equals("Others") && Double.parseDouble(item.Amount()) == 0)
                    {
                        DetailHeight -= 30;
                        break;
                    }

                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Notice1:
                case Notice2:
                case Notice3:
                    try{
                        if (Double.parseDouble(item.Amount()) == 0) break;
                    }catch (Exception e){

                    }
                    data+=WriteNotice(item.Label);
                    break;

                case Subtotal:
                    data+=WriteSubtotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Total:
                    data+=WriteTotalIselco2(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footnote:
                    data+=WriteFootnote(item.Label, String.valueOf(item.Amount()));
                    break;

                case Aftertotal:
                    data+=WriteAfterTotalIselco2(item.Label, String.valueOf(item.Amount()));
                    break;

                case SummaryHeader:
                    data+=WriteSummaryHeader(item.Label, String.valueOf(item.Amount()));
                    break;
            }
        }

        if (!PrintToFile)
        {
            data+=PrintLine(0, LineGap + 2, 0, DetailHeight - LineGap - LinePad);
            if (LiquidBilling.total_amount_due < 100000)
            {

                if (TotalPos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], TotalPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], Ypos - LinePad);

                if (SummaryPos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], SummaryPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], Ypos - LinePad);
            }
            data+=PrintLine(Columns[2], LineGap + 2, Columns[2], DetailHeight - LineGap - LinePad);
            data+="PRINT\r\n";
        }

        try {

            outputStream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void WriteDashedLine(int section)
    {
//        int total_height =115 + LineHeight * 5 + mm * 3;
//        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";
//        data+=PrintCenter(Columns[2] + Margin);
//        Ypos = mm;
//        data+=PrintLeft();
//        data+=PrintText("5", 0, Ypos, "Meter Readers are not authorized");
//        Ypos += LineHeight;
//        data+=PrintText("5", 0, Ypos, "to accept payments");
//        Ypos += LineHeight;
//        data+=PrintText("7", 0, Ypos - 5 + mm / 2, Liquid.repeatChar('-',30));
//        data+=PrintCenter(Columns[2] + Margin);
//        data+="BARCODE 128 1 1 "+50+" "+0+" "+Ypos+" "+Liquid.AccountNumber+"\r\n";
//        Ypos += LineHeight + mm + 20;
//        data+=PrintText("5", 0, Ypos,Liquid.AccountNumber);
//        Ypos += LineHeight + mm;
//        data+=PrintText("7", 0, Ypos,Liquid.User + "-" + Liquid.UserFullname+" "+ Liquid.currentDateTime() +" "+ Liquid.Status);
//        Ypos += LineHeight + mm;
//        data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
//        Ypos += LineHeight;
//        data+="PRINT\r\n";
//
//        String data = "! "+Margin+" 200 200 "+LineHeight+" 1\r\n";
//        data+=PrintCenter(Columns[2] + Margin);
//        Ypos = mm + 2;
//        data+=PrintText("5", 0, Ypos, "Meter Readers are not authorized");
//        Ypos += LineHeight;
//        data+=PrintText("5", 0, Ypos, "to accept payments");
//        Ypos += LineHeight;
//        data+=PrintText("7", 0, Ypos - 5 + mm / 2, Liquid.repeatChar('-',30));
//
//        int total_height = LineHeight * 3;
//        data+="! "+Margin+" 200 200 "+total_height+"\r\n";
//        data+=PrintCenter(Columns[2] + Margin);
//        Ypos = mm + 2;
//        data+=PrintText("5", 0, Ypos, "This serves as Official Receipt");
//        Ypos += LineHeight;
//        data+=PrintText("5", 0, Ypos, "if machine validated");
//        Ypos += LineHeight;
//        data+=PrintText("7", 0, Ypos + mm / 2, Liquid.repeatChar('-',45));
        String data = "";
        if (section == 2)
        {
            int total_height =LineHeight * 6;
            data ="! "+Margin+" 200 200 "+total_height+" 1\r\n";
            data+=PrintCenter(Columns[2] + Margin);
            Ypos = mm + 2;
            data+=PrintText("5", 0, Ypos, "Meter Readers are not authorized");
            Ypos += LineHeight;
            data+=PrintText("5", 0, Ypos, "to accept payments.");
            Ypos += LineHeight;
            data+=PrintText("5", 0, Ypos, "This document is not valid for claim of input tax.");
            Ypos += LineHeight;
            data+=PrintText("5", 0, Ypos, "This is a system-generated statement of account.");
            Ypos += LineHeight;
            data+=PrintText("5", 0, Ypos, "No signature is required.");
            Ypos += LineHeight;
            data+=PrintText("7", 0, Ypos + mm / 2, Liquid.repeatChar('-',45));
            data+="PRINT\r\n";
        }
        else
        {
//            data+="! "+Margin+" 200 200 "+0 * 3 + 5 * mm+" 1";
//            data+=PrintLeft();
//            data+=PrintText("7", 0, 0, Liquid.repeatChar('-',47));
//            data+="PRINT\r\n";
        }

        try {

            outputStream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void WriteDetailsBaliwagWD(BillItem[] items) {
        Ypos = 0;
        SummaryPos = 0;
        TotalPos = 0;
        HeaderTop = LineGap + 2;


        DetailHeight = ComputeDetailHeightBaliwagWD(items);

        DetailHeight = DetailHeight;
        int total_height =  DetailHeight;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        for (BillItem item : items)
        {
            switch (item.Style)
            {
                case Header:

                    data+=WriteTableHeader(item.Label, item.RateVar, item.AmountVar);
                    break;

                case Detail:

                    if (item.AmountVar.equals(""))
                    {
                        data+=WriteDetail("Lifeline Rate Discount" , item.Rate(), item.Amount());
                        break;
                    }
//                    if (item.Label.equals("Lifeline Rate Subsidy") && Double.parseDouble(item.Amount()) < 0)
//                    {
//                        data+=WriteDetail("Lifeline Rate Discount" , item.Rate(), item.Amount());
//                        break;
//                    }
//                    if (item.Label.equals("Senior Citizen Subsidy") && Double.parseDouble(item.Amount()) < 0)
//                    {
//                        data+=WriteDetail("Senior Citizen Discount", item.Rate(), item.Amount());
//                        break;
//                    }

                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case OptionalDetail:

                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case Subtitle:
                    data+=WriteSubtitle(item.Label);
                    break;

                case Separator:
                    data+=WriteSeparator();
                    break;

                case OptionalSeparator:
                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteSeparator();
                    break;

                case OptionalFooter:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footer:

                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;
                case OptionalFooterAdditional:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteOptionalFooterAdditional(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Notice1:
                case Notice2:
                case Notice3:
                    try{
                        if (Double.parseDouble(item.Amount()) == 0) break;
                    }catch (Exception e){

                    }
                    data+=WriteNotice(item.Label);
                    break;

                case Subtotal:
                    data+=WriteSubtotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Total:
                    data+=WriteTotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footnote:
                    data+=WriteFootnote(item.Label, String.valueOf(item.Amount()));
                    break;

                case Aftertotal:
                    String value = String.valueOf(item.Amount());
                    if(Liquid.isDouble(value)){
                        data += WriteAfterTotal(item.Label, Liquid.NumberFormat(value));
                        break;
                    }

                    data += WriteAfterTotal(item.Label, value);
                    break;
                case SummaryHeader:
                    data+=WriteSummaryHeader(item.Label, String.valueOf(item.Amount()));
                    break;
            }
        }

        if (!PrintToFile)
        {
            data+=PrintLine(0, LineGap + 2, 0, DetailHeight - LineGap - LinePad);
            if (LiquidBilling.total_amount_due < 100000)
            {

                if (TotalPos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], TotalPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], Ypos - LinePad);

//                if (SummaryPos > HeaderTop)
//                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], SummaryPos);
//                else if (Ypos > HeaderTop)
//                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], Ypos - LinePad);
            }
            data+=PrintLine(Columns[2], LineGap + 2, Columns[2], DetailHeight - LineGap - LinePad);
            data+="PRINT\r\n";
        }

        try {

            outputStream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void WriteDetailsPelco2(BillItem[] items) {
        Ypos = 0;
        SummaryPos = 0;
        TotalPos = 0;
        HeaderTop = LineGap + 2;


        DetailHeight = ComputeDetailHeightPelco2(items);

        DetailHeight = DetailHeight;
        int total_height =  DetailHeight;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        for (BillItem item : items)
        {
            switch (item.Style)
            {
                case Header:

                    data+=WriteTableHeader(item.Label, item.RateVar, item.AmountVar);
                    break;

                case Detail:

                    if (item.AmountVar.equals(""))
                    {
                        data+=WriteDetail(item.Label, "", "");
                        break;
                    }
                    if (item.Label.equals("Lifeline Rate Subsidy") && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Lifeline Rate Discount" , item.Rate(), item.Amount());
                        break;
                    }
                    if (item.Label.equals("Senior Citizen Subsidy") && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Senior Citizen Discount", item.Rate(), item.Amount());
                        break;
                    }

                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case OptionalDetail:

                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case Subtitle:
                    data+=WriteSubtitle(item.Label);
                    break;

                case Separator:
                    data+=WriteSeparator();
                    break;

                case OptionalSeparator:
                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteSeparator();
                    break;

                case OptionalFooter:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footer:

                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;



                case FooterTitle:

                    data+=WriteSummaryFooterTitle(item.Label);
                    break;
                case Notice1:
                case Notice2:
                case Notice3:
                    try{
                        if (Double.parseDouble(item.Amount()) == 0) break;
                    }catch (Exception e){

                    }
                    data+=WriteNotice(item.Label);
                    break;

                case Subtotal:
                    data+=WriteSubtotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Total:
                    data+=WriteTotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footnote:
                    data+=WriteFootnote(item.Label, String.valueOf(item.Amount()));
                    break;

                case Aftertotal:
                    data+=WriteAfterTotal(item.Label, String.valueOf(item.Amount()));
                    break;

                case SummaryHeader:
                    data+=WriteSummaryHeader(item.Label, String.valueOf(item.Amount()));
                    break;
            }
        }

        if (!PrintToFile)
        {
            data+=PrintLine(0, LineGap + 2, 0, DetailHeight - LineGap - LinePad);
            if (LiquidBilling.total_amount_due < 100000)
            {

                if (TotalPos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], TotalPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], Ypos - LinePad);

                if (SummaryPos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], SummaryPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], Ypos - LinePad);
            }
            data+=PrintLine(Columns[2], LineGap + 2, Columns[2], DetailHeight - LineGap - LinePad);
            data+="PRINT\r\n";
        }

        try {

            outputStream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void WriteDetailsIleco2(BillItem[] items) {
        Ypos = 0;
        SummaryPos = 0;
        TotalPos = 0;
        HeaderTop = LineGap + 2;


        DetailHeight = ComputeDetailHeightIleco2(items);

        DetailHeight = DetailHeight;
        int total_height =  DetailHeight;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        for (BillItem item : items)
        {
            switch (item.Style)
            {
                case Header:

                    data+=WriteTableHeader(item.Label, item.RateVar, item.AmountVar);
                    break;

                case Detail:

                    if (item.AmountVar.equals(""))
                    {
                        data+=WriteDetail(item.Label, "", "");
                        break;
                    }
                    if (item.Label.equals("Lifeline Rate Subsidy") && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Lifeline Rate Discount" , item.Rate(), item.Amount());
                        break;
                    }
                    if (item.Label.equals("Senior Citizen Subsidy") && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Senior Citizen Discount", item.Rate(), item.Amount());
                        break;
                    }

                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case OptionalDetail:

                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case Subtitle:
                    data+=WriteSubtitle(item.Label);
                    break;

                case Separator:
                    data+=WriteSeparator();
                    break;

                case OptionalSeparator:
                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteSeparator();
                    break;

                case OptionalFooter:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footer:

                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;
                case OptionalFooterAdditional:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteOptionalFooterAdditional(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Notice1:
                case Notice2:
                case Notice3:
                    try{
                        if (Double.parseDouble(item.Amount()) == 0) break;
                    }catch (Exception e){

                    }
                    data+=WriteNotice(item.Label);
                    break;

                case Subtotal:
                    data+=WriteSubtotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Total:
                    data+=WriteTotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footnote:
                    data+=WriteFootnote(item.Label, String.valueOf(item.Amount()));
                    break;

                case Aftertotal:
                    data+=WriteAfterTotal(item.Label, String.valueOf(item.Amount()));
                    break;

                case SummaryHeader:
                    data+=WriteSummaryHeader(item.Label, String.valueOf(item.Amount()));
                    break;
            }
        }

        if (!PrintToFile)
        {
            data+=PrintLine(0, LineGap + 2, 0, DetailHeight - LineGap - LinePad);
            if (LiquidBilling.total_amount_due < 100000)
            {

                if (TotalPos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], TotalPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], Ypos - LinePad);

                if (SummaryPos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], SummaryPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], Ypos - LinePad);
            }
            data+=PrintLine(Columns[2], LineGap + 2, Columns[2], DetailHeight - LineGap - LinePad);
            data+="PRINT\r\n";
        }

        try {

            outputStream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void WriteDetailsMorePower(BillItem[] items) {
        Ypos = 0;
        SummaryPos = 0;
        TotalPos = 0;
        HeaderTop = LineGap + 2;


        DetailHeight = ComputeDetailHeightMorePower(items);


        int total_height =  DetailHeight;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        for (BillItem item : items)
        {
            switch (item.Style)
            {
                case Header:

                    data+=WriteTableHeader(item.Label, item.RateVar, item.AmountVar);
                    break;

                case Detail:

                    if (item.AmountVar.equals(""))
                    {
                        data+=WriteDetail(item.Label, "", "");
                        break;
                    }
                    if (item.Label.equals("Lifeline Rate Subsidy") && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Lifeline Rate Discount" , item.Rate(), item.Amount());
                        break;
                    }
                    if (item.Label.equals("Senior Citizen Subsidy") && Double.parseDouble(item.Amount()) < 0)
                    {
                        data+=WriteDetail("Senior Citizen Discount", item.Rate(), item.Amount());
                        break;
                    }

                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case OptionalDetail:

                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteDetail(item.Label, item.Rate(), item.Amount());
                    break;

                case Subtitle:
                    data+=WriteSubtitle(item.Label);
                    break;

                case Separator:
                    data+=WriteSeparator();
                    break;

                case OptionalSeparator:
                    if (Double.parseDouble(item.Amount()) == 0) break;
                    data+=WriteSeparator();
                    break;

                case OptionalFooter:
                    if (Double.parseDouble(item.Amount()) == 0 || Double.parseDouble(item.Amount()) < 0) break;
                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footer:

                    data+=WriteSummaryFooter(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Notice1:
                case Notice2:
                case Notice3:
                    try{
                        if (Double.parseDouble(item.Amount()) == 0) break;
                    }catch (Exception e){

                    }
                    data+=WriteNotice(item.Label);
                    break;

                case Subtotal:
                    data+=WriteSubtotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Total:
                    data+=WriteTotal(item.Label, Double.parseDouble(item.Amount()));
                    break;

                case Footnote:
                    data+=WriteFootnote(item.Label, String.valueOf(item.Amount()));
                    break;

                case Aftertotal:
                    data+=WriteAfterTotal(item.Label, String.valueOf(item.Amount()));
                    break;

                case SummaryHeader:
                    data+=WriteSummaryHeader(item.Label, String.valueOf(item.Amount()));
                    break;
            }
        }

        if (!PrintToFile)
        {
            data+=PrintLine(0, LineGap + 2, 0, DetailHeight - LineGap - LinePad);
            if (LiquidBilling.total_amount_due < 100000)
            {

                if (TotalPos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], TotalPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[0], HeaderTop, Columns[0], Ypos - LinePad);

                if (SummaryPos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], SummaryPos);
                else if (Ypos > HeaderTop)
                    data+=PrintLine(Columns[1], HeaderTop, Columns[1], Ypos - LinePad);
            }
            data+=PrintLine(Columns[2], LineGap + 2, Columns[2], DetailHeight - LineGap - LinePad);
            data+="PRINT\r\n";
        }

        try {

            outputStream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String WriteSummaryFooterTitle(String label)
    {
        String data = "";
        if (PrintToFile)
        {
            //const string FORMAT_FOOTER_LINE = "{0,50}{1,15:n2}";
            //PrintWriter.WriteLine(FORMAT_FOOTER_LINE, label, a);
        }
        else
        {
            if (SummaryPos == 0)
                SummaryPos = Ypos - LineGap;
            //PrintRight(Columns[0] + Margin - Padding - 4);
            data+=PrintLeft();
            data+=PrintText("5", Padding + 6, Ypos + LinePad, label);
            data+=PrintRight(Columns[2] + Margin - Padding);
            data+=PrintText("7", 0, Ypos + LinePad, "");
            data+=PrintLeft();
            Ypos += LineHeight;

        }
        return  data;
    }

    private static String WriteSummaryFooter(String label, double a)
    {
        String data = "";
        if (PrintToFile)
        {
            //const string FORMAT_FOOTER_LINE = "{0,50}{1,15:n2}";
            //PrintWriter.WriteLine(FORMAT_FOOTER_LINE, label, a);
        }
        else
        {
            if (SummaryPos == 0)
                SummaryPos = Ypos - LineGap;
            //PrintRight(Columns[0] + Margin - Padding - 4);
            data+=PrintLeft();
            data+=PrintText("7", Padding + 6, Ypos + LinePad, label);
            data+=PrintRight(Columns[2] + Margin - Padding);
            data+=PrintText("7", 0, Ypos + LinePad, a);
            data+=PrintLeft();
            Ypos += LineHeight;

        }
        return  data;
    }

    private static String WriteOptionalFooterAdditional(String label, double a)
    {
        String data = "";
        if (PrintToFile)
        {
            //const string FORMAT_FOOTER_LINE = "{0,50}{1,15:n2}";
            //PrintWriter.WriteLine(FORMAT_FOOTER_LINE, label, a);
        }
        else
        {
            if (SummaryPos == 0)
                SummaryPos = Ypos - LineGap;
            //PrintRight(Columns[0] + Margin - Padding - 4);
            data+=PrintLeft();
            data+=PrintText("7", Padding + 30, Ypos + LinePad, label);
            data+=PrintRight(Columns[2] + Margin - Padding - 5);
            data+=PrintText("7", 0, Ypos + LinePad, a);
            data+=PrintLeft();
            Ypos += LineHeight;

        }
        return  data;
    }

    private static String transaction_type(String average)
    {
        if (average.toUpperCase() == "TRUE")
        {
            return "AVERAGE READING";
        }
        else
        {
            return "NORMAL READING";
        }
    }

    private static void WriteFooter2()
    {

        int total_height =115 + LineHeight * 5 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";
        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;
        data+=PrintLeft();
        data+=PrintText("5", 0, Ypos, "Please present this when paying your electric bill.");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "This document is not valid for claim of input tax.");
        Ypos += LineHeight;
        data+=PrintCenter(Columns[2] + Margin);
        data+="BARCODE 128 1 1 "+50+" "+0+" "+Ypos+" "+Liquid.AccountNumber+"\r\n";
        Ypos += LineHeight + mm + 20;
        data+=PrintText("5", 0, Ypos,Liquid.AccountNumber);
        Ypos += LineHeight + mm;
        data+=PrintText("7", 0, Ypos,Liquid.User + "-" + Liquid.UserFullname+" "+ Liquid.currentDateTime() +" "+ Liquid.Status);
        Ypos += LineHeight + mm;
        data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
        Ypos += LineHeight;
        data+="PRINT\r\n";

//        String data = "";
//        if (PrintToFile)
//        {
//             data = ""+Liquid.User+"-"+Liquid.route+" "+Liquid.currentDateTime()+"";
//        }
//
//        else
//        {
//            data+="! "+Margin+" 200 200 " + 115 + LineHeight * 5 + mm * 3 + " 1";
//            data+=PrintCenter(Columns[2] + Margin);
//            Ypos = mm;
//
//                /*
//                PrintLeft();
//                PrintText("7", 4, Ypos, "Account: " + Data.customer_accountno);
//                PrintRight(Columns[2] + Margin);
//                PrintText("7", 0, Ypos, "Amount: " + "PHP" + T1);
//                Ypos += LineHeight;
//
//
//                PrintCenter();
//                PrintText("7", 4, Ypos, "Name: " + Data.customer_fullname.ToProperCase());
//                PrintRight(Columns[2] + Margin);
//                PrintText("7", 0, Ypos, "Due: " + Bill.DueDate);
//                Ypos += LineHeight + mm;
//                 */
//            data+=PrintCenter(Columns[2] + Margin);
//            data+=PrintText("5", 0, Ypos, "Please present this when paying your electric bill.");
//            Ypos += LineHeight;
//            data+=PrintText("5", 0, Ypos, "This document is not valid for claim of input tax.");
//            Ypos += LineHeight + mm;
//
//            data+="BARCODE 128 1 1 "+50+" "+0+" "+Ypos+" "+BarCode+"";
//            Ypos += LineHeight + mm * 4;
//
//            data+=PrintCenter(Columns[2] + Margin);
//            data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
//            Ypos += LineHeight + mm;
//                /*
//                PrintCenter(Columns[2] + Margin);
//                PrintText("7", 0, Ypos, "Reader No." + Data.roverid);
//                */
//            data+=PrintLeft();
//            data+=PrintText("7", 0, Ypos, "Reader No." + Liquid.User);
//            data+=PrintRight(Columns[2] + Margin);
//            data+=PrintText("7", 0, Ypos, Liquid.currentDateTime());
//            Ypos += LineHeight;
//
//            data+=PrintLeft();
//            data+=PrintText("7", 0, Ypos, "Print:" + Liquid.Print_Attempt);
//            Ypos += LineHeight;
//
//            data+="PRINT\r\n";



            try {

                outputStream.write(data.getBytes());


            } catch (IOException e) {
                e.printStackTrace();
            }

        //}
    }

    private static void WriteNoticePelco2()
    {
        String due_date = Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","MMMM dd, yyyy");

        int total_height =115 + LineHeight * 14 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";
        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;
        data+=PrintCenter(Columns[2] + Margin);
        data+=PrintText("7", 0, Ypos, "** PLEASE PRESENT THIS STATEMENT UPON PAYMENT **");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "** THANK YOU FOR PAYING YOUR BILLS ON TIME **");
        Ypos += LineHeight + 10;
        data+=PrintText("5", 0, Ypos,"DUE DATE : "+due_date);
        Ypos += LineHeight + 10;
        data+=PrintText("5", 0, Ypos,"N O T I C E");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Please be informed that payment of this current");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Statement of account is within forty eight (48)");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "hours after due date, otherwise it will mean");
        Ypos += LineHeight - 5;
        data+=PrintText("7", 0, Ypos, "automatic disconnection of your electric service.");
        Ypos += LineHeight + 10;
        data+=PrintText("7", 0, Ypos, "Please disregard NOTICE if payments");
        Ypos += LineHeight - 5;
        data+=PrintText("7", 0, Ypos, "has already been made.");
        Ypos += LineHeight + 10;
        data+=PrintText("7", 0, Ypos, "Please pay your Electric Bills thru");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Authorized Coop Teller only.");
        Ypos += LineHeight;
        data+="BARCODE 128 1 1 "+50+" "+0+" "+Ypos+" "+Liquid.AccountNumber+"\r\n";
        Ypos += LineHeight + mm + 20;
        data+=PrintText("5", 0, Ypos,Liquid.AccountNumber);
        Ypos += LineHeight + mm;
        data+=PrintText("7", 0, Ypos,Liquid.User + "-" + Liquid.UserFullname+" "+ Liquid.currentDateTime() +" "+ Liquid.Status);
        Ypos += LineHeight + mm;
        data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
        Ypos += LineHeight;
        data+="PRINT\r\n";
        try {

            outputStream.write(data.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void WriteOptionalNoticeIleco2()
    {
        int total_height =115 + LineHeight * 2 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";
        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;
        data+=PrintText("5", 0, Ypos, "NOTICE OF DISCONNECTION");
        Ypos += LineHeight +10;
        data+=PrintText("5", 0, Ypos, "Please check and settle your accounts withing 48 hrs");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "(2 days) upon receipt to avoid disconnection.");
        Ypos += LineHeight;
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Disregard this notice if payments has been made");
        data+="PRINT\r\n";
        try {

            outputStream.write(data.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void WriteNoticeBaliwagWD()
    {
        int total_height =115 + LineHeight * 12 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";
        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;
        data+=PrintText("5", 0, Ypos, "Reconnection Fee of P100.00 will be charged if");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Service has been disconnected.");
        Ypos += LineHeight;
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "ALL BILLING COMPLAINTS WILL BE");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "ACKNOWLEDGED WITHIN 7 WORKING DAYS");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "ONLY AFTER READING DATE.");
        Ypos += LineHeight+3;
        data+=PrintText("5", 0, Ypos, "NO FIELD COLLECTOR");
        Ypos += LineHeight;
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "Notice: This is NOT valid as Official Receipt.");
        Ypos += LineHeight;
        data+=PrintCenter(Columns[2] + Margin);
        data+="BARCODE 128 1 1 "+50+" "+0+" "+Ypos+" "+Liquid.AccountNumber+"\r\n";
        Ypos += LineHeight + mm + 20;
        data+=PrintText("5", 0, Ypos,Liquid.AccountNumber);
        Ypos += LineHeight + mm;
        data+=PrintText("7", 0, Ypos,Liquid.User + "-" + Liquid.UserFullname+" "+ Liquid.currentDateTime() +" "+ Liquid.Status);
        Ypos += LineHeight + mm;
        data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
        Ypos += LineHeight;
        data+="PRINT\r\n";
        try {

            outputStream.write(data.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void WriteNoticeIleco2()
    {
        int total_height =115 + LineHeight * 5 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";
        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;
        data+=PrintText("5", 0, Ypos, "Please present this when paying your electric bill");
        Ypos += LineHeight;
        data+=PrintText("5", 0, Ypos, "*** Upon receipt you can pay after 3 days ***");
        Ypos += LineHeight;
        data+=PrintCenter(Columns[2] + Margin);
        data+="BARCODE 128 1 1 "+50+" "+0+" "+Ypos+" "+Liquid.AccountNumber+"\r\n";
        Ypos += LineHeight + mm + 20;
        data+=PrintText("5", 0, Ypos,Liquid.AccountNumber);
        Ypos += LineHeight + mm;
        data+=PrintText("7", 0, Ypos,Liquid.User + "-" + Liquid.UserFullname+" "+ Liquid.currentDateTime() +" "+ Liquid.Status);
        Ypos += LineHeight + mm;
        data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
        Ypos += LineHeight;
        data+="PRINT\r\n";
        try {

            outputStream.write(data.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void WriteNoticeIselco2()
    {
        int total_height =115 + LineHeight * 12 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;

        data+=PrintLeft();
        data+=PrintText("7", 0, Ypos, "TRANSACTION TYPE: " + transaction_type(Liquid.Average_Reading));
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "RUN DATE: " +Liquid.setUpCurrentDate("MM/dd/yyyy hh:mm tt"));
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "METER READER: " + Liquid.User + "-" + Liquid.UserFullname);
        Ypos += LineHeight + mm;

        data+=PrintCenter(Columns[2] + Margin);
        data+="BARCODE 128 1 1 "+100+" "+0+" "+Ypos+" "+Liquid.AccountNumber+"\r\n";
        Ypos += LineHeight + mm * 10;

        data+=PrintText("5", 0, Ypos,  Liquid.AccountNumber);
        Ypos += LineHeight + 10;
        data+=PrintLeft();
        data+=PrintText("7", 0, Ypos, "This is VALID as Official Receipt when/if");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "machine - validated. Or pay to authorized");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "collector and demand an Official Receipt.");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Please disregard arrears if payment has");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "been made. Thank you!");
        Ypos += LineHeight + 15;
        data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
        Ypos += LineHeight;
        data+="PRINT\r\n";
        try {

            outputStream.write(data.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void WriteNoticeBatelec2()
    {
        int total_height = 110 + LineHeight * 21 + mm * 3;
        String data = "! "+Margin+" 200 200 "+total_height+" 1\r\n";

        data+=PrintCenter(Columns[2] + Margin);
        Ypos = mm;

        data+=PrintLeft();
        data+=PrintText("7", 0, Ypos, "Online/Mobile Ref. No.: " + Liquid.setUpCurrentDate("yyyyMMdd") +  Liquid.AccountNumber);
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Reader: " + Liquid.User + "-" + Liquid.UserFullname);
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Reading Time: " + Liquid.setUpCurrentDate("MM/dd/yyyy hh:mm tt"));
        Ypos += LineHeight + mm;

        data+=PrintCenter(Columns[2] + Margin);
        data+="BARCODE 128 1 1 "+100+" "+0+" "+Ypos+" "+Liquid.AccountNumber+"\r\n";
        Ypos += LineHeight + mm * 10;

        data+=PrintText("5", 0, Ypos,  Liquid.AccountNumber);
        Ypos += LineHeight + 10;
        data+=PrintLeft();
        data+=PrintText("5", 0, Ypos, "Payment Center Due Date: " + Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","dd-MMM-yyyy"));
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "KINDLY SETTLE YOUR ACCOUNTS AT BATELEC II OR");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "AT AUTHORIZED PAYMENT CENTERS, ON OR BEFORE");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","dd-MMM-yyyy") + ". FOR QUESTIONS AND SUGGESTIONS");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "EMAIL US AT admin@batelec2.com.ph or CALL");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "043 756-6337, 312-2080, 981-1865 local 165-167");
        Ypos += LineHeight + 15;
        data+=PrintText("5", 0, Ypos, "Powered by: FIELDTECH SPECIALIST, INC. (FSI)");
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos - 5 + mm / 2,Liquid.repeatChar('-',50));
        Ypos += LineHeight - 5;
        data+=PrintCenter(Columns[2] + Margin);
        data+=PrintText("5", 0, Ypos, "FOR PAYMENT CENTER USE ONLY");
        Ypos += LineHeight - 5;
        data+=PrintText("7", 0, Ypos - 5 + mm / 2, Liquid.repeatChar('-',50));
        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("7", 0, Ypos, "Account Name" + "  :" + Liquid.AccountName);
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Account Number" + ":" + Liquid.AccountNumber);
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Amount" + "        :" + LiquidBilling.total_amount_due);
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "Meter Number" + "  :" +  Liquid.MeterNumber);
        Ypos += LineHeight;
        data+=PrintText("7", 0, Ypos, "SEQ/BookID" + "    :" + Liquid.Sequence + "/" + Liquid.route + "/" + Liquid.Complete_Address);
        Ypos += LineHeight;
        data+="PRINT\r\n";
        try {

            outputStream.write(data.getBytes());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String WriteSummary(String label, double a)
    {
        String data = "";
        if (PrintToFile)
        {
            final String FORMAT_FOOTER_LINE = "{0,50}{1,15:n2}";
            //data+=(FORMAT_FOOTER_LINE, label, a);
        }
        else
        {
            if (SummaryPos == 0)
                SummaryPos = Ypos - LineGap;
            data+=PrintRight(Columns[0] + Margin - Padding - 4);
            data+=PrintText("7", 0, Ypos + LinePad, label);
            data+=PrintRight(Columns[2] + Margin - Padding);
            data+=PrintText("7", 0, Ypos + LinePad, a);
            data+=PrintLeft();
            Ypos += LineHeight;
        }
        return data;
    }

    private static String FormatAmt(double value, String format)
    {
        if (value < 0)
            return "(" + String.format(format,-value) + ")";
        else
            return String.format(format,value);
    }

    private static void WriteHeaderBaliwagWD(int section)
    {
        try
        {
            int totalHeaderHeight = HeaderHeight - 100 + mm;
            String data = "! "+Margin+" 200 200 "+totalHeaderHeight+" 1\r\n";
            String address = Liquid.Complete_Address;
            if(address.length() >= 35)
                address = address.subSequence(0,34)+"...";
            data+=PrintLine(0, LineGap, 0, HeaderHeight - 4 - 90);// left
//            data+=PrintLine(Columns[0], LineGap, Columns[0], TitleHeight - LineGap - 4); // top center
            data+=PrintLine(Columns[2], LineGap, Columns[2], HeaderHeight - 4 - 90);                       // right
            data+=PrintLine(0, HeaderHeight - 4 - 90, Columns[2], HeaderHeight - 4 - 90);                       // bottom
            data+=PrintLine(Columns[0], TopSectionHeight + LineGap, Columns[0], HeaderHeight - 4);    // bottom center
            Ypos = 0;
            String AccountLabel = Liquid.AccountNumber;
            data+=WriteHeaderHeader("Acct No. " + AccountLabel, "","");
            int offset = (TopSectionHeight - TitleHeight - 2 * 24 - 12) / 12;
            data+=PrintLeft();
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10, Liquid.AccountName);
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10 + 24 + 12, address);
            data+=PrintLeft();
            Ypos = TopSectionHeight;

            data+=WriteHeaderHeaderBaliwagWD();

            Ypos = TopSectionHeight + LineGap + Padding * 2 + LineHeight* 2;

            data+=WriteBaliwagMeterReading(
                    Liquid.dateChangeFormat(Liquid.previous_reading_date,"yyyy-MM-dd","MM/dd/yyyy"), Liquid.previous_reading,
                    Liquid.setUpCurrentDate("MM/dd/yyyy"), Liquid.Reading,
                    Liquid.FormatKWH(Liquid.Present_Consumption));
            //String.valueOf(Liquid.RoundUp(Double.parseDouble(Liquid.Present_Consumption))));


            Ypos = HeaderHeight - ThreeMosHeight - 4;
            //WriteThreeMos();
            data+="PRINT\n";

            outputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void WriteHeaderPelco2(int section)
    {
        try
        {
            int totalHeaderHeight = HeaderHeight - 100 + mm;
            String data = "! "+Margin+" 200 200 "+totalHeaderHeight+" 1\r\n";
            data+=PrintLine(0, LineGap, 0, HeaderHeight - 4 - 90);// left
            data+=PrintLine(Columns[0], LineGap, Columns[0], TitleHeight - LineGap - 4); // top center
            data+=PrintLine(Columns[2], LineGap, Columns[2], HeaderHeight - 4 - 90);                       // right
            data+=PrintLine(0, HeaderHeight - 4 - 90, Columns[2], HeaderHeight - 4 - 90);                       // bottom
            data+=PrintLine(Columns[0], TopSectionHeight + LineGap, Columns[0], HeaderHeight - 4);    // bottom center
            Ypos = 0;
            String AccountLabel = Liquid.AccountNumber;
            data+=WriteHeaderHeader("Account No. " + AccountLabel, "","Book No: "+ Liquid.itinerary);
            int offset = (TopSectionHeight - TitleHeight - 2 * 24 - 12) / 12;
            data+=PrintLeft();
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10, Liquid.AccountName);
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10 + 24 + 12, Liquid.Complete_Address);
            data+=PrintLeft();
            Ypos = TopSectionHeight;

            data+=WriteHeaderHeaderPelco2();

            Ypos = TopSectionHeight + LineGap + Padding * 2 + LineHeight * 2;

            data+=WritePelco2MeterReading(
                    Liquid.dateChangeFormat(Liquid.previous_reading_date,"yyyy-MM-dd","MM/dd/yyyy"), Liquid.previous_reading,
                    Liquid.setUpCurrentDate("MM/dd/yyyy"), Liquid.Reading,
                    Liquid.FormatKWH(Liquid.Present_Consumption));
            //String.valueOf(Liquid.RoundUp(Double.parseDouble(Liquid.Present_Consumption))));


            Ypos = HeaderHeight - ThreeMosHeight - 4;
            //WriteThreeMos();
            data+="PRINT\n";

            outputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void WriteHeaderIleco2(int section)
    {
        try
        {
            int totalHeaderHeight = HeaderHeight - 100 + mm;
            String data = "! "+Margin+" 200 200 "+totalHeaderHeight+" 1\r\n";
            data+=PrintLine(0, LineGap, 0, HeaderHeight - 4 - 90);// left
            data+=PrintLine(Columns[0], LineGap, Columns[0], TitleHeight - LineGap - 4); // top center
            data+=PrintLine(Columns[2], LineGap, Columns[2], HeaderHeight - 4 - 90);                       // right
            data+=PrintLine(0, HeaderHeight - 4 - 90, Columns[2], HeaderHeight - 4 - 90);                       // bottom
            data+=PrintLine(Columns[0], TopSectionHeight + LineGap, Columns[0], HeaderHeight - 4);    // bottom center
            Ypos = 0;
            String AccountLabel = Liquid.AccountNumber;
            data+=WriteHeaderHeader("Account No. " + AccountLabel, "","Book No: "+ Liquid.route);
            int offset = (TopSectionHeight - TitleHeight - 2 * 24 - 12) / 12;
            data+=PrintLeft();
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10, Liquid.AccountName);
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10 + 24 + 12, Liquid.Complete_Address);
            data+=PrintLeft();
            Ypos = TopSectionHeight;

            data+=WriteHeaderHeaderIleco2();

            Ypos = TopSectionHeight + LineGap + Padding * 2 + LineHeight * 2;

            data+=WriteIleco2MeterReading(
                    Liquid.dateChangeFormat(Liquid.previous_reading_date,"yyyy-MM-dd","MM/dd/yyyy"), Liquid.previous_reading,
                    Liquid.setUpCurrentDate("MM/dd/yyyy"), Liquid.Reading,
                    Liquid.FormatKWH(Liquid.Present_Consumption));
                    //String.valueOf(Liquid.RoundUp(Double.parseDouble(Liquid.Present_Consumption))));


            Ypos = HeaderHeight - ThreeMosHeight - 4;
            //WriteThreeMos();
            data+="PRINT\n";

            outputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void WriteHeaderMorePower(int section)
    {
        try
        {
            int totalHeaderHeight = HeaderHeight - 40 + mm;
            String data = "! "+Margin+" 200 200 "+totalHeaderHeight+" 1\r\n";
            data+=PrintLine(0, LineGap, 0, HeaderHeight - 4 - 90 + 60);// left
            data+=PrintLine(Columns[0], LineGap, Columns[0], TitleHeight - LineGap - 4); // top center
            data+=PrintLine(Columns[2], LineGap, Columns[2], HeaderHeight - 4 - 90 + 60);                       // right
            data+=PrintLine(0, HeaderHeight - 4 - 90 + 60, Columns[2], HeaderHeight - 4 - 90 + 60);    // bottom
            data+=PrintLine(Columns[0], TopSectionHeight + LineGap, Columns[0], HeaderHeight - 4);    // bottom center
            Ypos = 0;
            String AccountLabel = Liquid.AccountNumber;
            data+=WriteHeaderHeader("Accnt No. " + AccountLabel, "","Route: "+ Liquid.route);
            int offset = (TopSectionHeight - TitleHeight - 2 * 24 - 12) / 12;
            data+=PrintLeft();
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10, Liquid.AccountName);
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10 + 24 + 12, Liquid.Complete_Address);
            data+=PrintLeft();
            Ypos = TopSectionHeight;

            data+=WriteHeaderHeaderMorePower();

            Ypos = TopSectionHeight + LineGap + Padding * 2 + LineHeight * 2 + 60;

            data+=WriteIleco2MeterReading(
                    Liquid.dateChangeFormat(Liquid.previous_reading_date,"yyyy-MM-dd","MM/dd/yyyy"), Liquid.previous_reading,
                    Liquid.setUpCurrentDate("MM/dd/yyyy"), Liquid.Reading,
                    Liquid.FormatKWH(Liquid.Present_Consumption));
            //String.valueOf(Liquid.RoundUp(Double.parseDouble(Liquid.Present_Consumption))));


            Ypos = HeaderHeight - ThreeMosHeight - 4;
            //WriteThreeMos();
            data+="PRINT\n";

            outputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void WriteHeaderIselco2(int section)
    {
        try
        {
            int totalHeaderHeight = HeaderHeight - 100 + mm;
            String data = "! "+Margin+" 200 200 "+totalHeaderHeight+" 1\r\n";
            data+=PrintLine(0, LineGap, 0, HeaderHeight - 4 - 90);// left
            data+=PrintLine(Columns[0], LineGap, Columns[0], TitleHeight - LineGap - 4); // top center
            data+=PrintLine(Columns[2], LineGap, Columns[2], HeaderHeight - 4 - 90);                       // right
            data+=PrintLine(0, HeaderHeight - 4 - 90, Columns[2], HeaderHeight - 4 - 90);                       // bottom
            data+=PrintLine(Columns[0], TopSectionHeight + LineGap, Columns[0], HeaderHeight - 4);    // bottom center
            Ypos = 0;
            String AccountLabel = Liquid.AccountNumber;
            data+=WriteHeaderHeader("Account No. " + AccountLabel, "",Liquid.dateChangeFormat(Liquid.BillingCycle,"yyyy-MM","MMM yyyy").toUpperCase());
            int offset = (TopSectionHeight - TitleHeight - 2 * 24 - 12) / 12;
            data+=PrintLeft();
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10, Liquid.AccountName);
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10 + 24 + 12, Liquid.Complete_Address);
            data+=PrintLeft();
            Ypos = TopSectionHeight;

            data+=WriteHeaderHeaderIleco2();

            Ypos = TopSectionHeight + LineGap + Padding * 2 + LineHeight * 2;

            data+=WriteMeterReading(
                    Liquid.dateChangeFormat(Liquid.previous_reading_date,"yyyy-MM-dd","MM/dd/yyyy"), Liquid.previous_reading,
                    Liquid.setUpCurrentDate("MM/dd/yyyy"), Liquid.Reading,
                    Liquid.FormatKWH(Liquid.Present_Consumption)+" kwh");

            Ypos = HeaderHeight - ThreeMosHeight - 4;
            //WriteThreeMos();
            data+="PRINT\n";

            outputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void WriteHeaderBatelec2(int section)
    {
        try
        {
            int totalHeaderHeight = HeaderHeight - 100 + mm;
            String data = "! "+Margin+" 200 200 "+totalHeaderHeight+" 1\r\n";
            data+=PrintLine(0, LineGap, 0, HeaderHeight - 4 - 90);// left
            data+=PrintLine(Columns[0], LineGap, Columns[0], TitleHeight - LineGap - 4); // top center
            data+=PrintLine(Columns[2], LineGap, Columns[2], HeaderHeight - 4 - 90);                       // right
            data+=PrintLine(0, HeaderHeight - 4 - 90, Columns[2], HeaderHeight - 4 - 90);                       // bottom
            data+=PrintLine(Columns[0], TopSectionHeight + LineGap, Columns[0], HeaderHeight - 4);    // bottom center
            Ypos = 0;
            String AccountLabel = Liquid.AccountNumber;
            data+=WriteHeaderHeader("Account No. " + AccountLabel, "","");
            int offset = (TopSectionHeight - TitleHeight - 2 * 24 - 12) / 12;
            data+=PrintLeft();
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10, Liquid.AccountName);
            data+=PrintText("5", Padding + 18, Ypos + offset + LinePad + 10 + 24 + 12, Liquid.Sequence + " -" + Liquid.route + " -" + Liquid.Complete_Address);
            data+=PrintLeft();
            Ypos = TopSectionHeight;

            data+=WriteHeaderHeaderIselco2();

            Ypos = TopSectionHeight + LineGap + Padding * 2 + LineHeight * 2;

            data+=WriteMeterReading(
                    Liquid.dateChangeFormat(Liquid.previous_reading_date,"yyyy-MM-dd","MM/dd/yyyy"), Liquid.previous_reading,
                    Liquid.setUpCurrentDate("MM/dd/yyyy"), Liquid.Reading,
                    Liquid.FormatKWH(Liquid.Present_Consumption)+" kwh");

            Ypos = HeaderHeight - ThreeMosHeight - 4;
            //WriteThreeMos();
            data+="PRINT\n";

            outputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String WriteBaliwagMeterReading(String a, String b, String c, String d, String e)
    {
        int gap = LineGap;
        int offset = (SectionHeight - 66 - gap - LineHeight) / 2;
        int xpad = Columns[0] / 10 + 8;

        String data = PrintText("5", Columns[0] + Padding * 10, Ypos + offset-10, "Cubic Meter");
        data += PrintText("5", Columns[0] + Padding * 11, Ypos + offset+15, "Consumed");

        data+=PrintText("4", Columns[0] + Padding * 15, Ypos + offset + 24 + gap, String.valueOf(Math.round(Float.parseFloat(e))));

        Ypos += LineGap * 2;
        data+=PrintText("5", xpad + 38, Ypos + LinePad + 1, "Date");
        data+=PrintRight(Columns[0] + Margin - xpad);
        data+=PrintText("5", 0, Ypos + LinePad + 1, "Reading");

        Ypos += LineHeight + LineGap + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, c);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, d);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, a);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, b);
        data+=PrintLeft();

        return data;
    }

    private static String WritePelco2MeterReading(String a, String b, String c, String d, String e)
    {
        int gap = LineGap;
        int offset = (SectionHeight - 66 - gap - LineHeight) / 2;
        int xpad = Columns[0] / 10 + 8;

        String data = PrintText("5", Columns[0] + Padding * 12, Ypos + offset, "KWH Used");

        data+=PrintText("4", Columns[0] + Padding * 15, Ypos + offset + 24 + gap, e);

        Ypos += LineGap * 2;
        data+=PrintText("5", xpad + 38, Ypos + LinePad + 1, "Date");
        data+=PrintRight(Columns[0] + Margin - xpad);
        data+=PrintText("5", 0, Ypos + LinePad + 1, "Reading");

        Ypos += LineHeight + LineGap + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, a);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, b);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, c);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, d);
        data+=PrintLeft();

        return data;
    }

    private static String WriteIleco2MeterReading(String a, String b, String c, String d, String e)
    {
        int gap = LineGap;
        int offset = (SectionHeight - 66 - gap - LineHeight) / 2;
        int xpad = Columns[0] / 10 + 8;

        String data = PrintText("5", Columns[0] + Padding * 12, Ypos + offset, "KWH Used");

        data+=PrintText("4", Columns[0] + Padding * 15, Ypos + offset + 24 + gap, String.valueOf(Math.round(Float.parseFloat(e))));

        Ypos += LineGap * 2;
        data+=PrintText("5", xpad + 38, Ypos + LinePad + 1, "Date");
        data+=PrintRight(Columns[0] + Margin - xpad);
        data+=PrintText("5", 0, Ypos + LinePad + 1, "Reading");

        Ypos += LineHeight + LineGap + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, a);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, b);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, c);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, d);
        data+=PrintLeft();

        return data;
    }

    private static String WriteMeterReading(String a, String b, String c, String d, String e)
    {
        int gap = LineGap;
        int offset = (SectionHeight - 66 - gap - LineHeight) / 2;
        int xpad = Columns[0] / 10 + 8;

        String data = PrintText("5", Columns[0] + Padding * 2, Ypos + offset, Liquid.dateChangeFormat(Liquid.BillingCycle,"yyyy-MM","MMM yyyy").toUpperCase());

        data+=PrintText("4", Columns[0] + Padding * 2, Ypos + offset + 24 + gap, e);

        Ypos += LineGap * 2;
        data+=PrintText("5", xpad + 38, Ypos + LinePad + 1, "Date");
        data+=PrintRight(Columns[0] + Margin - xpad);
        data+=PrintText("5", 0, Ypos + LinePad + 1, "Reading");

        Ypos += LineHeight + LineGap + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, a);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, b);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLeft();
        data+=PrintText("7", xpad, Ypos, c);
        data+=PrintRight(Columns[0] - xpad - Padding - 10);
        data+=PrintText("7", 0, Ypos, d);
        data+=PrintLeft();

        return data;
    }



    private static String WriteHeaderHeader(String label, String code, String acctype)
    {
        Ypos += LineGap;
        int save = Ypos;
        String data = PrintLine(0, Ypos, Columns[2], Ypos);
        Ypos += Padding;
        data+= PrintText("5", Padding + 10, Ypos + LinePad + 1, label);
        data+= PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, code);
        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, acctype);
        Ypos += LineHeight + Padding;
        data+=PrintLine(0, Ypos, Columns[2], Ypos);
        if(!acctype.matches(""))
            data+=PrintLine(Columns[0], save, Columns[0], Ypos);
        Ypos += LineGap;
        return data;
    }

    private static String WriteHeaderHeaderBaliwagWD()
    {
        Ypos += LineGap; //SORECO

        String data = PrintLine(0, Ypos, Columns[2], Ypos);

        Ypos += Padding;
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Type");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.rate_code);
        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1,"Zone");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.route);

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Meter No.");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.MeterNumber);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, "Sequence");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.Sequence);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLine(0, Ypos, Columns[2], Ypos);
        Ypos += LineGap;

        return data;
    }

    private static String WriteHeaderHeaderPelco2()
    {
        Ypos += LineGap; //SORECO

        String data = PrintLine(0, Ypos, Columns[2], Ypos);

        Ypos += Padding;
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Meter No.");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.MeterNumber);
        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1,"Multiplier");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.multiplier);

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Type");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.AccountType);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, "Sequence");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.Sequence);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLine(0, Ypos, Columns[2], Ypos);
        Ypos += LineGap;

        return data;
    }

    private static String WriteHeaderHeaderIleco2()
    {
        Ypos += LineGap; //SORECO

        String data = PrintLine(0, Ypos, Columns[2], Ypos);

        Ypos += Padding;
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Meter No.");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.MeterNumber);
        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1,"Multiplier");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.multiplier);

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Serial");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.serial);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, "Type");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.rate_code);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLine(0, Ypos, Columns[2], Ypos);
        Ypos += LineGap;

        return data;
    }


    private static String WriteHeaderHeaderMorePower()
    {
        Ypos += LineGap; //SORECO

        String data = PrintLine(0, Ypos, Columns[2], Ypos);

        Ypos += Padding;
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Meter No.");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.MeterNumber);
        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1,"Multiplier");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.multiplier);

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Serial");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.serial);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, "Type");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.rate_description);
        data+=PrintLeft();

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Connected Load");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.connectionload);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, "Load Factor");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.LoadFactor);
        data+=PrintLeft();

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Demand");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.Demand);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, "");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, "");
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLine(0, Ypos, Columns[2], Ypos);
        Ypos += LineGap;

        return data;
    }


    private static String WriteHeaderHeaderIselco2()
    {
        Ypos += LineGap; //SORECO

        String data = PrintLine(0, Ypos, Columns[2], Ypos);

        Ypos += Padding;
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Meter No.");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.MeterNumber);
        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1,"Type");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1,  RateDescription(Liquid.rate_code));

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Multiplier");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.multiplier);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, "Book");
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.route);
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLine(0, Ypos, Columns[2], Ypos);
        Ypos += LineGap;

        return data;
    }

    private static String WriteHeaderHeaderBatelec2()
    {
        Ypos += LineGap; //SORECO

        String data = PrintLine(0, Ypos, Columns[2], Ypos);

        Ypos += Padding;
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Multiplier");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.multiplier);
        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, RateDescription(Liquid.rate_code));
        data+=PrintRight(Columns[2] + Margin - Padding - 6);

        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, "");

        Ypos += LineHeight;
        data+=PrintLeft();
        data+=PrintText("5", Padding + 10, Ypos + LinePad + 1, "Meter No.");
        data+=PrintRight(Columns[0] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, Liquid.MeterNumber);

        data+=PrintLeft();
        data+=PrintText("5", Columns[0] + Padding + 10, Ypos + LinePad + 1, Liquid.classification);
        data+=PrintRight(Columns[2] + Margin - Padding - 6);
        data+=PrintText("7", Padding + 10, Ypos + LinePad + 1, "");
        data+=PrintLeft();

        Ypos += LineHeight + Padding;
        data+=PrintLine(0, Ypos, Columns[2], Ypos);
        Ypos += LineGap;
        return data;
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
    private static void WriteLogo()
    {
        try {
            String setup = "! "+Margin+" 200 200 "+160+" 1";
            outputStream.write(setup.getBytes());
            //PrintPCX(0, 0, Images.beneco);
            outputStream.write("PRINT".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private static void PrintPCX(int x, int y, byte[] pcx)
    {
        try {

            outputStream.write("PCX {0} {1}", 0, 0);
            char[] a = Xml.Encoding.ASCII.GetChars(pcx);
            PrintWriter.Write(a, 0, a.Length);
            PrintWriter.WriteLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

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

    private static String PrintLine(int x1, int y1, int x2, int y2)
    {
        return "LINE "+x1+" "+y1+" "+x2+" "+y2+" 1\r\n";
    }

    private static String PrintLeft()
    {
        return "LEFT\r\n";
    }



    private static String PrintRight(int offset)
    {
        return  "RIGHT " + offset +"\r\n";
    }


    public static String RateDescription(String RateCode){
        switch (RateCode)
        {
            case "R":
                return "Residential";
            case "C":
                return "Commercial";

            case "G":
                return "Government";

            case "I":
                return "Industrial";


            case "P":
                if (Liquid.Client == "peco")
                {
                    return "Power";
                }
                else
                {
                    return "Public Building";
                }


            case "S":
                return "Street Light";

            case "M":
                return "Power";

            case "N":
                return "Power";

            case "O":
                return "Power";

            case "L":
                return "Power";

            case "K":
                return "Power";

            case "X":
                return "Power";

            case "Y":
                return "Power";

            case "Z":
                return "Power";

            case "BAPA":
                return "BAPA (MM)";

            case "CWW":
                return "CWW";

            case "LI":
                return "Large Industrial";

            case "SI":
                return "Small Industrial";

            default:
                return "Residential";

        }
    }

}
