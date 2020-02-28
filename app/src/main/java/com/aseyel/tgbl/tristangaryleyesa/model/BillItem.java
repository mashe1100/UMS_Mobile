package com.aseyel.tgbl.tristangaryleyesa.model;


public class BillItem {
    public String Label;
    public String RateVar;
    public String AmountVar;
    public ItemStyle Style;

    public BillItem(String label, String ratevar, String amountvar, ItemStyle style)
    {
        this.Label = label;
        this.RateVar = ratevar;
        this.AmountVar = amountvar;
        this.Style = style;
    }

    public BillItem(String label, String amountvar, ItemStyle style)
    {
        this.Label = label;
        this.AmountVar = amountvar;
        this.Style = style;
    }

    public BillItem(String label, ItemStyle style)
    {
        this.Label = label;
        this.Style = style;
    }

    public BillItem(ItemStyle style)
    {
        this.Style = style;
    }

    public Object Rate()
    {

            try
            {
                return RateVar;
            }
            catch(Exception e)
            {
                return RateVar;
            }
    }

    public String Amount()
    {
            return String.valueOf(this.AmountVar.equals("") || this.AmountVar.equals(null) ? 0 : this.AmountVar);
    }
}