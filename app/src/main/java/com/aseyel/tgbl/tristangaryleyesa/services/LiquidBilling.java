package com.aseyel.tgbl.tristangaryleyesa.services;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.ReadingActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingV2Activity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.BillingModel;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LiquidBilling {
    public static final String TAG = LiquidBilling.class.getSimpleName();
    String rate_description,rd_id,rd_group;;
    //Iselco
    public static double gen_sys_charge = 0,
            interclass = 0,
            dis_demand_charge = 0,
            trans_sys_chg = 0,
            sys_los_chg = 0,
            dis_sys_chg = 0,
            sup_sys_chg = 0,
            ret_cust_chg_1 = 0,
            ret_cust_chg_2 = 0,
            met_sys_chg = 0,
            sc_disc = 0,
            sc_sub = 0,
            missionary = 0,
            environmental_chg = 0,
            npc_stran_deb = 0,
            npc_stran_cos = 0,
            ucme_redc = 0,
            capex = 0,
            fran_tax_rec = 0,
            psalm_daa = 0,
            gen_vat = 0,
            trans_vat = 0,
            sys_loss_vat = 0,
            met_ret_chg = 0,
            dis_con = 0,
            final_loan_con = 0,
            me_npc_spug = 0,
            prompt_payment_disc_adj = 0,
            others_vat = 0,
            equalization_taxes = 0,
            cross_subsidy_removal = 0,
            me_renewable_energy_dev = 0,
            senior_citizen_subsidy = 0,
            bilp = 0,
            dis_vat = 0,
            fran_bhc_charge = 0,
            auto_cost_adj = 0,
            other_gen_adj = 0,
            other_trans_cost_adj = 0,
            other_sys_loss_adj = 0,
            ppa_refund = 0,
            other_life_rate_adj = 0;


    public static double total_gen_sys_charge = 0,
            total_amount_due2 = 0,
            total_interclass = 0,
            total_dis_demand_charge = 0,
            total_trans_sys_chg = 0,
            total_sys_los_chg = 0,
            total_dis_sys_chg = 0,
            total_sup_sys_chg = 0,
            total_ret_cust_chg_1 = 0,
            total_ret_cust_chg_2 = 0,
            total_met_sys_chg = 0,
            total_sc_disc = 0,
            total_sc_sub = 0,
            total_sub_dis_charge = 0,
            total_missionary = 0,
            total_environmental_chg = 0,
            total_npc_stran_deb = 0,
            total_npc_stran_cos = 0,
            total_ucme_redc = 0,
            total_capex = 0,
            total_fran_tax_rec = 0,
            total_psalm_daa = 0,
            total_gen_vat = 0,
            total_trans_vat = 0,
            total_sys_loss_vat = 0,
            total_bilp = 0,
            total_cross_subsidy_removal = 0,
            total_me_npc_spug = 0,
            total_equalization_taxes = 0,
            total_others_vat = 0,
            total_prompt_payment_disc_adj = 0,
            total_dis_con = 0,
            total_met_ret_chg= 0,
            total_final_loan_con = 0,
            total_me_renewable_energy_dev = 0,
            total_senior_citizen_subsidy = 0,
            total_lifeline_dis = 0,
            total_dis_vat = 0,
            total_fran_bhc_charge = 0,
            total_auto_cost_adj = 0,
            total_other_gen_adj = 0,
            total_other_trans_cost_adj = 0,
            total_other_sys_loss_adj = 0,
            total_ppa_refund = 0,
            total_other_life_rate_adj = 0;

    //Bill BreakDown
    public static double  generation_charge = 0,power_act_reduc= 0,
            trans_del_charge_1= 0,trans_del_charge_2= 0,
            rstc_refund_1= 0,rstc_refund_2= 0,
            sys_loss_1= 0,sys_loss_2= 0,
            power_cost_adj= 0,
            distrib_net_charge_1= 0,distrib_net_charge_2 =0,distrib_net_charge_3=0,
            ret_elec_serv_charge_1= 0,ret_elec_serv_charge_2=0,
            met_charge_1= 0,met_charge_2=0,
            rfsc= 0,
            lifeline= 0,
            senior= 0,
            generation= 0,
            transmission= 0,
            distribution= 0,
            others= 0,
            gram_icera_daa= 0,
            real_prop_tax= 0,
            missionary_elec= 0,
            environmental_charge= 0,
            stran_contract_cost= 0,
            npc_stranded_debts= 0,
            gram_icera_daa_erc= 0,
            fit_all= 0,
            trate_gen_charge= 0,
            trate_trans_charge= 0,
            trate_sys_loss= 0,
            trate_dis_charge= 0,
            trate_sup_charge= 0,
            trate_met_charge= 0,
            arrears = 0,
            arrears_additional = 0,
            eda = 0,
            surcharge = 0,
            overdue = 0,
            rentalfee = 0
    ;


    public static double  total_generation_charge,total_transmission_charge,total_power_act_reduc,
            total_trans_del_charge_1,total_trans_del_charge_2,
            total_rstc_refund_1,total_rstc_refund_2,
            total_systems_loss,
            total_sys_loss_1,total_sys_loss_2,
            total_power_cost_adj,
            total_distrib_net_charge_1,total_distrib_net_charge_2,total_distrib_net_charge_3,
            total_ret_elec_serv_charge_1,total_ret_elec_serv_charge_2,
            total_met_charge_1,total_met_charge_2,
            total_rfsc,
            total_lifeline,
            total_senior = 0,
            total_generation,
            total_transmission,
            total_distribution,
            total_others,
            total_gram_icera_daa,
            total_real_prop_tax,
            total_missionary_elec,
            total_environmental_charge,
            total_stran_contract_cost,
            total_npc_stranded_debts,
            total_gram_icera_daa_erc,
            total_fit_all,
            total_trate_gen_charge,
            total_trate_trans_charge,
            total_trate_sys_loss,
            total_trate_dis_charge,
            total_trate_sup_charge,
            total_trate_met_charge,
            total_universal,
    //total_trate,
    total_trate,
    //total_revenue
    total_gen_trans,
            total_distribution_revenue,
            total_other_charges,
            total_government_revenue,
            total_moa,
            total_current_bill,
            total_amount_due,
            total_rental_others,
            total_energy_charges;
    ;
    public static void clearData(){


        gen_sys_charge = 0;
        interclass = 0;
        dis_demand_charge = 0;
        trans_sys_chg = 0;
        sys_los_chg = 0;
        dis_sys_chg = 0;
        sup_sys_chg = 0;
        ret_cust_chg_1 = 0;
        ret_cust_chg_2 = 0;
        met_sys_chg = 0;
        sc_disc = 0;
        sc_sub = 0;
        missionary = 0;
        environmental_chg = 0;
        npc_stran_deb = 0;
        npc_stran_cos = 0;
        ucme_redc = 0;
        capex = 0;
        fran_tax_rec = 0;
        psalm_daa = 0;
        gen_vat = 0;
        trans_vat = 0;
        sys_loss_vat = 0;
        met_ret_chg = 0;
        dis_con = 0;
        final_loan_con = 0;
        me_npc_spug = 0;
        prompt_payment_disc_adj = 0;
        others_vat = 0;
        equalization_taxes = 0;
        cross_subsidy_removal = 0;
        me_renewable_energy_dev = 0;
        senior_citizen_subsidy = 0;
        bilp = 0;
        dis_vat = 0;


        total_gen_sys_charge = 0;
        total_senior = 0;
        total_amount_due2 =0;
        total_interclass = 0;
        total_dis_demand_charge = 0;
        total_trans_sys_chg = 0;
        total_sys_los_chg = 0;
        total_dis_sys_chg = 0;
        total_sup_sys_chg = 0;
        total_ret_cust_chg_1 = 0;
        total_ret_cust_chg_2 = 0;
        total_met_sys_chg = 0;
        total_sc_disc = 0;
        total_sc_sub = 0;
        total_missionary = 0;
        total_environmental_chg = 0;
        total_npc_stran_deb = 0;
        total_npc_stran_cos = 0;
        total_ucme_redc = 0;
        total_capex = 0;
        total_fran_tax_rec = 0;
        total_psalm_daa = 0;
        total_gen_vat = 0;
        total_trans_vat = 0;
        total_sys_loss_vat = 0;
        total_bilp = 0;
        total_cross_subsidy_removal = 0;
        total_me_npc_spug = 0;
        total_equalization_taxes = 0;
        total_others_vat = 0;
        total_prompt_payment_disc_adj = 0;
        total_dis_con = 0;
        total_met_ret_chg= 0;
        total_final_loan_con = 0;
        total_me_renewable_energy_dev = 0;
        total_senior_citizen_subsidy = 0;
        total_lifeline_dis = 0;
        total_dis_vat = 0;


        generation_charge = 0;power_act_reduc= 0;
        trans_del_charge_1= 0;trans_del_charge_2= 0;
        rstc_refund_1= 0;rstc_refund_2= 0;
        sys_loss_1= 0;sys_loss_2= 0;
        power_cost_adj= 0;
        distrib_net_charge_1= 0;distrib_net_charge_2 =0;distrib_net_charge_3=0;
        ret_elec_serv_charge_1= 0;ret_elec_serv_charge_2=0;
        met_charge_1= 0;met_charge_2=0;
        rfsc= 0;
        lifeline= 0;
        senior= 0;
        generation= 0;
        transmission= 0;
        distribution= 0;
        others= 0;
        gram_icera_daa= 0;
        real_prop_tax= 0;
        missionary_elec= 0;
        environmental_charge= 0;
        stran_contract_cost= 0;
        npc_stranded_debts= 0;
        gram_icera_daa_erc= 0;
        fit_all= 0;
        trate_gen_charge= 0;
        trate_trans_charge= 0;
        trate_sys_loss= 0;
        trate_dis_charge= 0;
        trate_sup_charge= 0;
        trate_met_charge= 0;
        arrears = 0;
        eda = 0;

        total_generation_charge= 0;total_power_act_reduc= 0;
        total_trans_del_charge_1= 0;total_trans_del_charge_2= 0;
        total_rstc_refund_1= 0;total_rstc_refund_2= 0;
        total_sys_loss_1= 0;total_sys_loss_2= 0;
        total_power_cost_adj= 0;
        total_distrib_net_charge_1= 0;total_distrib_net_charge_2= 0;total_distrib_net_charge_3= 0;
        total_ret_elec_serv_charge_1= 0;total_ret_elec_serv_charge_2= 0;
        total_met_charge_1= 0;total_met_charge_2= 0;
        total_rfsc= 0;
        total_lifeline= 0;
        total_senior = 0;
        total_generation= 0;
        total_transmission= 0;
        total_distribution= 0;
        total_others= 0;
        total_gram_icera_daa= 0;
        total_real_prop_tax= 0;
        total_missionary_elec= 0;
        total_environmental_charge= 0;
        total_stran_contract_cost= 0;
        total_npc_stranded_debts= 0;
        total_gram_icera_daa_erc= 0;
        total_fit_all= 0;
        total_trate_gen_charge= 0;
        total_trate_trans_charge= 0;
        total_trate_sys_loss= 0;
        total_trate_dis_charge= 0;
        total_trate_sup_charge= 0;
        total_trate_met_charge= 0;
        total_universal= 0;
        //total_trate= 0;
        total_trate= 0;
        //total_revenue
        total_gen_trans= 0;
        total_distribution_revenue= 0;
        total_other_charges= 0;
        total_government_revenue= 0;
        total_moa= 0;
        total_current_bill= 0;
        total_amount_due= 0;
        total_rental_others= 0;
        total_energy_charges =0;
    }

    public double RatesKWHComputation(String KWH, String Rates){

        //KWH = !KWH.equals("") || !KWH.equals(null) || !KWH.isEmpty() ? KWH : "0";
        //Rates = !Rates.equals("") || !Rates.equals(null) || !Rates.isEmpty() ? Rates : "0";

        if(KWH.equals("") || KWH.equals(null) || KWH.isEmpty()){
            KWH = "0";
        }
        if(Rates.equals("") || Rates.equals(null) || Rates.isEmpty()){
            KWH = "0";
        }

        double Total = Double.valueOf(KWH) * Double.valueOf(Rates);

        return Liquid.RoundUp(Total);
    }

    public void MorePowerElectricBillingComputaion(String KWH,
                                                String Demand,
                                                String RateCode,
                                                String Classification,
                                                String BillingCycle,
                                                String Arrears,
                                                String EDATag,
                                                String SeniorTag) {

        KWH = KWH != "" ? KWH : "0";
        Demand = Demand != "" ? Demand : "0";

        Cursor result = BillingModel.GetRates(RateCode, Classification, BillingCycle);

        if(!Liquid.meter_count.equals("1")){
            Liquid.LoadFactor = String.valueOf(GetLoadFactor(Double.parseDouble(KWH),
                    Double.parseDouble(Demand),
                    Liquid.ConvertStringToDate(Liquid.present_reading_date),
                    Liquid.ConvertStringToDate(Liquid.previous_reading_date)));
        }


        if (result.getCount() == 0) {
            return;
        }
        while (result.moveToNext()) {

            rate_description = result.getString(result.getColumnIndex("rate_description"));

            switch (rate_description) {
                //Related Charges
                case "Distribution Charge":
                    distrib_net_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Demand Charge":
                    dis_demand_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Supply Charge":
                    sup_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Metering Charge":
                    met_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Retail Customer Charge":
                    ret_cust_chg_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Supplier Related Charges
                case "Generation Charge":
                    gen_sys_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
//                case "Generation Cost Adjustment":
//                    Data.generation_cost_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
//                    break;
//                case "Power Cost Adjustment":
//                    Data.power_cost_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
//                    break;
                case "Transmission Charge":
                    trans_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "System Loss Charge":
                    sys_los_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Subsidies
//                case "Interclass Cross Subsidy":
//                    Data.interclass_cross_sub = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
//                    break;
                case "Lifeline Rate Charge":
                    lifeline = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Taxes and Universal Charges
                case "VAT on Generation":
                    gen_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT on Transmission":
                    trans_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT on Other Charges":
                    others_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Franchise Tax":
                    fran_tax_rec = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Missionary Electrification":
                   missionary_elec = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Environmental Charges":
                    environmental_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "NPC Stranded Contract Cost":
                    npc_stran_cos = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "FIT - Allowance":
                    fit_all = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
//                case "ICCS Adjustment":
//                    Data.iccs_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
//                    break;

            }


        }

        ///Related Charges
        total_distrib_net_charge_1 = RatesKWHComputation(KWH, String.valueOf(distrib_net_charge_1));

        total_dis_demand_charge = RatesKWHComputation(Demand, String.valueOf(dis_demand_charge));
        total_met_charge_1 = RatesKWHComputation(KWH, String.valueOf(met_charge_1));
        total_ret_cust_chg_1 = Liquid.RoundUp(ret_cust_chg_1);
        //supplyCharge
        total_sup_sys_chg = getMorePowerSupplyCharge(sup_sys_chg,RateCode,Double.parseDouble(KWH));
        //subTotal Related Charges
        total_distribution_revenue =
                Liquid.RoundUp(total_distrib_net_charge_1 +
                total_dis_demand_charge +
                total_met_charge_1 +
                total_ret_cust_chg_1 +
                total_sup_sys_chg)
        ;

        //Supplier Related Charges
        total_gen_sys_charge= RatesKWHComputation(KWH, String.valueOf(gen_sys_charge));
        total_trans_sys_chg = RatesKWHComputation(KWH, String.valueOf(trans_sys_chg));
        total_sys_los_chg = RatesKWHComputation(KWH, String.valueOf(sys_los_chg));

        //Subtotal Supplier Related Charges
        total_gen_trans = Liquid.RoundUp(total_gen_sys_charge +
                total_trans_sys_chg+
                total_sys_los_chg);

        //Subsidies
        total_trate =  Liquid.RoundUp(total_distribution_revenue +
                total_gen_trans);
        total_lifeline = getLifelineMorePowerDiscount(RateCode,  Double.parseDouble(KWH),total_trate);
        if(total_lifeline < 0){
            total_senior = 0;
        }else{
            total_senior = getSeniorMorePowerCitizen(RateCode, SeniorTag, Double.parseDouble(KWH), total_trate);
        }


        //SubTotal Subsidies
        total_others = Liquid.RoundUp(total_lifeline + total_senior);

        //Governement
        total_gen_vat = Liquid.RoundUp(total_gen_sys_charge * gen_vat);
        total_trans_vat = Liquid.RoundUp(total_trans_sys_chg * trans_vat);
        total_fran_tax_rec = Liquid.RoundUp((total_distribution_revenue + total_gen_trans +  total_others) * fran_tax_rec);
        total_others_vat = Liquid.RoundUp((total_distribution_revenue + total_sys_los_chg +  total_others + total_fran_tax_rec) * others_vat);
        total_missionary_elec = RatesKWHComputation(KWH, String.valueOf(missionary_elec));
        total_environmental_chg = RatesKWHComputation(KWH, String.valueOf(environmental_charge));
        total_npc_stran_cos = RatesKWHComputation(KWH, String.valueOf(npc_stran_cos));
        total_fit_all = RatesKWHComputation(KWH, String.valueOf(fit_all));

        //Subtotal Governement
        total_government_revenue = Liquid.RoundUp(total_gen_vat +
                total_trans_vat +
                total_fran_tax_rec +
                total_others_vat +
                total_missionary_elec +
                total_environmental_chg +
                total_npc_stran_cos +
                total_fit_all);


        //CurrentBill
        total_current_bill = Liquid.RoundUp(total_distribution_revenue +
                             total_gen_trans +
                             total_others +
                             total_government_revenue);

        //Arrears
        surcharge = Double.parseDouble(Liquid.surcharge); //ILP
        arrears = Double.parseDouble(Liquid.arrears); // inclusion
        total_amount_due = Liquid.RoundUp(total_current_bill + surcharge + arrears);

    }


    public void Ileco2ElectricBillingComputaion(String KWH,
                                                String Demand,
                                                String RateCode,
                                                String Classification,
                                                String BillingCycle,
                                                String Arrears,
                                                String EDATag,
                                                String SeniorTag) {

        KWH = KWH != "" ? KWH : "0";
        Demand = Demand != "" ? Demand : "0";

        Cursor result = BillingModel.GetRates(RateCode, Classification, BillingCycle);

        if (result.getCount() == 0) {
            return;
        }
        while (result.moveToNext()) {

            rate_description = result.getString(result.getColumnIndex("rate_description"));

            switch (rate_description) {
                case "Generation Charge":
                    gen_sys_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Power Act Reduction":
                    power_act_reduc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmission System Charge":
                    trans_del_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmission Demand Charge":
                    trans_del_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "System Loss Charge":
                    sys_loss_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Distribution Revenues
                case "Distribution System Charge":
                    distrib_net_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Distribution Demand Charge":
                    distrib_net_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Retail Service Charge":
                    ret_elec_serv_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Retail Demand Charge":
                    ret_elec_serv_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Metering System Charge":
                    met_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Metering Retail Customer":
                    met_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Distribution Connection":
                    distrib_net_charge_3 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Final Loan Condonation":
                    final_loan_con = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Other Charges
                case "Interclass Cross Subsidy":
                    interclass = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "RFSC/MCC":
                    rfsc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Prompt Payment Disc Adj":
                    prompt_payment_disc_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Government Revenues
                case "Generation Vat":
                    gen_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmission Vat":
                    trans_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "System Loss Vat":
                    sys_loss_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Vat":
                    //dis_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    //others_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    dis_vat = (float) 0.1200;
                    others_vat = (float) 0.1200;
                    break;

                //Universal Charges
                case "ME - NPC SPUG":
                    missionary_elec = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Environmental Charges":
                    environmental_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "NPC Stranded Debts":
                    npc_stran_deb = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "NPC Stranded Costs":
                    npc_stran_cos = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "DUs Stranded Contract Cost":
                    stran_contract_cost = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Equalization Taxes":
                    equalization_taxes = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Cross-Subsidy Removal":
                    cross_subsidy_removal = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "ME - Renewable Energy Dev":
                    me_renewable_energy_dev = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "FIT-All Renewable":
                    fit_all = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "PSALM Recovery":
                    psalm_daa = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //ETC
                case "Senior Citizen Subsidy":
                    senior = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Inclusion / BILP":
                    bilp = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Subsidies
                case "Lifeline Rate Subsidy":
                    lifeline = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
            }


        }

        //Generation and Transmission

        total_gen_sys_charge= RatesKWHComputation(KWH, String.valueOf(gen_sys_charge));

        total_power_act_reduc = RatesKWHComputation(KWH, String.valueOf(power_act_reduc));
        total_trans_del_charge_1 = RatesKWHComputation(KWH, String.valueOf(trans_del_charge_1));
        total_trans_del_charge_2 = RatesKWHComputation(KWH, String.valueOf(trans_del_charge_2));
        total_sys_loss_1 = RatesKWHComputation(KWH, String.valueOf(sys_loss_1));

        total_gen_trans =
                total_gen_sys_charge +
                        total_power_act_reduc+
                        total_trans_del_charge_1 +
                        total_trans_del_charge_2+
                        total_sys_loss_1;

        //Distribution Revenue

        total_distrib_net_charge_1 = RatesKWHComputation(KWH, String.valueOf(distrib_net_charge_1));
        total_distrib_net_charge_2 = RatesKWHComputation(Demand, String.valueOf(distrib_net_charge_2));
        total_ret_elec_serv_charge_1 = RatesKWHComputation(KWH, String.valueOf(ret_elec_serv_charge_1));
        total_ret_elec_serv_charge_2 = Liquid.RoundUp(ret_elec_serv_charge_2);
        total_met_charge_1 = RatesKWHComputation(KWH, String.valueOf(met_charge_1));
        total_met_charge_2 = Liquid.RoundUp(met_charge_2);
        total_distrib_net_charge_3 = RatesKWHComputation(KWH, String.valueOf(distrib_net_charge_3));
        total_final_loan_con = RatesKWHComputation(KWH, String.valueOf(final_loan_con));

        total_distribution_revenue = total_distrib_net_charge_1 +
                total_distrib_net_charge_2 +
                total_ret_elec_serv_charge_1 +
                total_ret_elec_serv_charge_2 +
                total_met_charge_1 +
                total_met_charge_2 +
                total_distrib_net_charge_3 +
                total_final_loan_con
        ;

        //Other Charges
        total_interclass = RatesKWHComputation(KWH, String.valueOf(interclass));
        total_rfsc = RatesKWHComputation(KWH, String.valueOf(rfsc));
        if(RateCode.equals("R")){
            total_prompt_payment_disc_adj = RatesKWHComputation(KWH, String.valueOf(prompt_payment_disc_adj));
        }else{
            total_prompt_payment_disc_adj = Liquid.RoundUp(prompt_payment_disc_adj);
        }

        total_trate =  Liquid.RoundUp(total_gen_sys_charge + total_trans_del_charge_1 + total_sys_loss_1 + total_distrib_net_charge_1 + total_ret_elec_serv_charge_1 + total_met_charge_1);

        total_lifeline = getLifelineIleco2Discount(RateCode,  Double.parseDouble(KWH),total_trate);

        //modified: 01/02/19: add total lifeline to trate - ALEX
        total_senior = getSeniorIleco2Citizen(RateCode, SeniorTag, Double.parseDouble(KWH), total_gen_trans + total_distribution_revenue,total_lifeline);

        total_others =  total_lifeline +
                total_interclass;

        if(total_lifeline < 0){
            total_distribution = Liquid.RoundUp(GetDistributionVat(total_distribution_revenue,total_lifeline_dis));
            total_others_vat =  Liquid.RoundUp(GetOtherVat());
        }else{
            total_distribution = Liquid.RoundUp(total_distribution_revenue * dis_vat);
            total_others_vat =  Liquid.RoundUp(total_others * others_vat);
        }

        total_distribution = Liquid.RoundUp(total_distribution);
        //Government Revenue
        total_generation = RatesKWHComputation(KWH, String.valueOf(gen_vat));
        total_transmission = RatesKWHComputation(KWH, String.valueOf(trans_vat));
        total_sys_loss_2 = RatesKWHComputation(KWH, String.valueOf(sys_loss_vat));

        total_government_revenue = total_generation +
                total_transmission +
                total_sys_loss_2 +
                total_distribution+
                total_others_vat
        ;

        //Universal Charges
        total_missionary_elec = RatesKWHComputation(KWH, String.valueOf(missionary_elec));
        total_environmental_charge = RatesKWHComputation(KWH, String.valueOf(environmental_charge));
        total_npc_stran_deb = RatesKWHComputation(KWH, String.valueOf(npc_stran_deb));

        total_npc_stran_cos = RatesKWHComputation(KWH, String.valueOf(npc_stran_cos));
        total_stran_contract_cost = RatesKWHComputation(KWH, String.valueOf(stran_contract_cost));
        total_equalization_taxes = RatesKWHComputation(KWH, String.valueOf(equalization_taxes));
        total_cross_subsidy_removal = RatesKWHComputation(KWH, String.valueOf(cross_subsidy_removal));
        total_me_renewable_energy_dev = RatesKWHComputation(KWH, String.valueOf(me_renewable_energy_dev));
        total_fit_all = RatesKWHComputation(KWH, String.valueOf(fit_all));
        total_psalm_daa = RatesKWHComputation(KWH, String.valueOf(psalm_daa));

        total_universal = total_missionary_elec +
                total_environmental_charge +
                total_npc_stran_deb+
                total_npc_stran_cos +
                total_stran_contract_cost +
                total_equalization_taxes +
                total_cross_subsidy_removal +
                total_me_renewable_energy_dev +
                total_fit_all +
                total_psalm_daa;


        //Arrears
        arrears = Double.parseDouble(Arrears);
        if(arrears > 0)
            arrears_additional = 50;
        else
            arrears_additional = 0;

        //SubTotal
        total_gen_trans = Double.parseDouble(Liquid.StringRoundUp2D(total_gen_trans));
        total_distribution_revenue = Double.parseDouble(Liquid.StringRoundUp2D(total_distribution_revenue));
        total_universal = Double.parseDouble(Liquid.StringRoundUp2D(total_universal));
        total_others = Double.parseDouble(Liquid.StringRoundUp2D(total_others));
        total_government_revenue = Double.parseDouble(Liquid.StringRoundUp2D(total_government_revenue));
        total_rfsc =Double.parseDouble(Liquid.StringRoundUp2D(total_rfsc));
        total_prompt_payment_disc_adj = Double.parseDouble(Liquid.StringRoundUp2D(total_prompt_payment_disc_adj));
        total_senior = Double.parseDouble(Liquid.StringRoundUp2D(total_senior));

        total_energy_charges = Liquid.RoundUp(total_gen_trans +
                total_distribution_revenue +
                total_universal +
                total_others +
                total_government_revenue +
                total_rfsc +
                total_prompt_payment_disc_adj);

        surcharge = Double.parseDouble(Liquid.surcharge); //ILP
        overdue = Double.parseDouble(Liquid.overdue); // inclusion

//        total_current_bill = Liquid.RoundUp(total_gen_trans + total_distribution_revenue + total_universal + total_others + total_rfsc + total_prompt_payment_disc_adj);
        total_current_bill = Liquid.RoundUp(total_energy_charges + total_senior + surcharge + overdue );
        total_amount_due = Liquid.RoundUp(total_energy_charges + total_senior +arrears + arrears_additional+arrears_additional+ surcharge + overdue );

        rentalfee =  Double.parseDouble(Liquid.rentalfee) > total_amount_due ? Double.parseDouble(Liquid.rentalfee) : total_amount_due;
        Liquid.rentalfee = String.valueOf(rentalfee); //gov subsidy
    }

    public void Pelco2ElectricBillingComputaion(String KWH,
                                                String Demand,
                                                String RateCode,
                                                String Classification,
                                                String BillingCycle,
                                                String Arrears,
                                                String EDATag,
                                                String SeniorTag) {

        KWH = KWH != "" ? KWH : "0";
        Demand = Demand != "" ? Demand : "0";

        Cursor result = BillingModel.GetRates(RateCode, Classification, BillingCycle);

        if (result.getCount() == 0) {
            return;
        }
        while (result.moveToNext()) {

            rate_description = result.getString(result.getColumnIndex("rate_description"));

            switch (rate_description) {
                case "Generation System Charge":
                    gen_sys_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Franchise and BHC Charge":
                    fran_bhc_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmission System Charge":
                    trans_del_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Systems Loss Charge":
                    sys_los_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Distribution Revenues
                case "Distribution System Charge":
                    distrib_net_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Retail Customer Charge":
                    ret_elec_serv_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Metering System Charge":
                    met_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Other Charges
                case "RFSC/MCC":
                    rfsc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "PPA Refund":
                    ppa_refund = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Government Revenues
                case "VAT Generation":
                    gen_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT Transmission":
                    trans_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT System Loss":
                    sys_loss_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Environmental Charge":
                    environmental_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "NPC Stranded Debt":
                    npc_stran_deb = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "FIT-All (Renewable)":
                    fit_all = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //ETC
                case "Senior Citizen Subsidy":
                    senior = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                //Subsidies
                case "Lifeline Rate Subsidy":
                    lifeline = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Automatic Cost Adjustment":
                    auto_cost_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Other Generation Adjustment":
                    other_gen_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Other Trans. Cost Adjustment":
                    other_trans_cost_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Other System Loss Adjustment":
                    other_sys_loss_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Other Lifeline Rate Adjustment":
                    other_life_rate_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT System Loss (Gen)":
                    sys_loss_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT System Loss (Trans)":
                    sys_loss_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Missionary Elec (NPC-SPUG)":
                    me_npc_spug = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Missionary Elec (RED)":
                    me_npc_spug = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT Distribution":
                    dis_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "VAT Others":
                    others_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "RFSC":
                    rfsc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Supply System Charge":
                    sup_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Capex":
                    rfsc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Missionary Charge":
                    me_npc_spug = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "MC Red":
                    me_renewable_energy_dev = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
            }


        }
        // STATIC FIELDS
        dis_vat = (float) 0.1200;
        others_vat = (float) 0.1200;
        //TEMPORARY DATA
        sys_loss_1 = (float) 0.1430;
        sys_loss_2 = (float) 0.1430;


        // Generation  Charges
        total_gen_sys_charge= RatesKWHComputation(KWH, String.valueOf(gen_sys_charge));
        total_fran_bhc_charge= RatesKWHComputation(KWH, String.valueOf(fran_bhc_charge));
        total_auto_cost_adj= RatesKWHComputation(KWH, String.valueOf(auto_cost_adj));
        total_other_gen_adj= RatesKWHComputation(KWH, String.valueOf(other_gen_adj));
        total_generation_charge =
                total_gen_sys_charge +
                        total_fran_bhc_charge +
                        total_auto_cost_adj +
                        total_other_gen_adj;

        // Transmission Charges
        total_trans_del_charge_1 = RatesKWHComputation(KWH, String.valueOf(trans_del_charge_1));
        total_other_trans_cost_adj = RatesKWHComputation(KWH, String.valueOf(other_trans_cost_adj));
        total_transmission_charge =
                total_trans_del_charge_1 +
                        total_other_trans_cost_adj ;

        // System Loss Charges
        total_sys_los_chg = RatesKWHComputation(KWH, String.valueOf(sys_los_chg));
        total_other_sys_loss_adj = RatesKWHComputation(KWH, String.valueOf(other_sys_loss_adj));
        total_systems_loss =
                total_sys_los_chg +
                        total_other_sys_loss_adj ;

        // Distribution Charges
        total_distrib_net_charge_1 = RatesKWHComputation(KWH, String.valueOf(distrib_net_charge_1));


        // Supply Charges
        total_sup_sys_chg = RatesKWHComputation(KWH, String.valueOf(sup_sys_chg));


        // Metering Charges
        total_met_sys_chg = RatesKWHComputation(KWH, String.valueOf(met_sys_chg));
        total_ret_elec_serv_charge_1 = ret_elec_serv_charge_1;
        total_met_charge_1 =
                total_met_sys_chg +
                        total_ret_elec_serv_charge_1 ;


        // Other Charges
        total_ppa_refund = RatesKWHComputation(KWH, String.valueOf(ppa_refund));
        total_rfsc = RatesKWHComputation(KWH, String.valueOf(rfsc));
        total_other_charges =
                total_ppa_refund +
                        total_rfsc ;


        // Subsidies/Discount Charges
        total_lifeline = RatesKWHComputation(KWH, String.valueOf(lifeline));
        total_senior = RatesKWHComputation(KWH, String.valueOf(senior));
        total_other_life_rate_adj = RatesKWHComputation(KWH, String.valueOf(other_life_rate_adj));
        total_sub_dis_charge =
                total_lifeline +
                        total_senior +
                        total_other_life_rate_adj ;


        // VAT
        total_generation_charge = Double.parseDouble(Liquid.StringRoundUp2D(total_generation_charge));
        total_transmission_charge = Double.parseDouble(Liquid.StringRoundUp2D(total_transmission_charge));

        total_generation = Liquid.RoundUp((total_generation_charge * gen_vat) * (float)0.1200);
        total_transmission = Liquid.RoundUp((total_transmission_charge * trans_vat) * (float)0.1200);

        total_systems_loss = Double.parseDouble(Liquid.StringRoundUp2D(total_systems_loss));
        total_distrib_net_charge_1 = Double.parseDouble(Liquid.StringRoundUp2D(total_distrib_net_charge_1));
        total_sup_sys_chg = Double.parseDouble(Liquid.StringRoundUp2D(total_sup_sys_chg));
        total_met_charge_1 = Double.parseDouble(Liquid.StringRoundUp2D(total_met_charge_1));
        total_other_charges = Double.parseDouble(Liquid.StringRoundUp2D(total_other_charges));
        total_sub_dis_charge = Double.parseDouble(Liquid.StringRoundUp2D(total_sub_dis_charge));
        total_generation = Double.parseDouble(Liquid.StringRoundUp2D(total_generation));
        total_transmission = Double.parseDouble(Liquid.StringRoundUp2D(total_transmission));

        total_sys_loss_1 = Liquid.RoundUp((total_generation) * sys_loss_1);
        total_sys_loss_2 = Liquid.RoundUp((total_transmission) * sys_loss_2);
        total_distribution = Liquid.RoundUp((total_distrib_net_charge_1 + total_sup_sys_chg + total_met_charge_1) * dis_vat);
        total_others_vat = Liquid.RoundUp((total_other_charges + total_sub_dis_charge) * others_vat);

//        total_trate =  Liquid.RoundUp(total_gen_sys_charge + total_trans_del_charge_1 + total_sys_loss_1 + total_distrib_net_charge_1 + total_ret_elec_serv_charge_1 + total_met_charge_1);
//        total_lifeline = getLifelineIleco2Discount(RateCode,  Double.parseDouble(KWH),total_trate);
//
//        //modified: 01/02/19: add total lifeline to trate - ALEX
//        total_senior = getSeniorIleco2Citizen(RateCode, SeniorTag, Double.parseDouble(KWH), total_gen_trans + total_distribution_revenue,total_lifeline);
//
//        total_others =  total_lifeline +
//                total_interclass;
//
//        if(total_lifeline < 0){
//            total_distribution = Liquid.RoundUp(GetDistributionVat(total_distribution_revenue,total_lifeline_dis));
//            total_others_vat =  Liquid.RoundUp(GetOtherVat());
//        }else{
//            total_distribution = Liquid.RoundUp(total_distribution_revenue * dis_vat);
//            total_others_vat =  Liquid.RoundUp(total_others * others_vat);
//        }
        total_government_revenue =
                total_generation +
                        total_transmission +
                        total_sys_loss_1 +
                        total_sys_loss_2 +
                        total_distribution +
                        total_others_vat;

        // Universal Charges
        total_me_npc_spug = RatesKWHComputation(KWH, String.valueOf(me_npc_spug));
        total_me_renewable_energy_dev = RatesKWHComputation(KWH, String.valueOf(me_renewable_energy_dev));
        total_environmental_charge = RatesKWHComputation(KWH, String.valueOf(environmental_charge));
        total_npc_stran_deb = RatesKWHComputation(KWH, String.valueOf(npc_stran_deb));
        total_fit_all = RatesKWHComputation(KWH, String.valueOf(fit_all));
        total_universal =
                total_me_npc_spug +
                        total_me_renewable_energy_dev +
                        total_environmental_charge +
                        total_npc_stran_deb;


        //Arrears
        //arrears = Double.parseDouble(Arrears);
        //SubTotal
        total_other_charges = Double.parseDouble(Liquid.StringRoundUp2D(total_other_charges));
        total_government_revenue = Double.parseDouble(Liquid.StringRoundUp2D(total_government_revenue));
        total_universal = Double.parseDouble(Liquid.StringRoundUp2D(total_universal));

//        total_others = Double.parseDouble(Liquid.StringRoundUp2D(total_others));
//        total_rfsc =Double.parseDouble(Liquid.StringRoundUp2D(total_rfsc));
//        total_senior = Double.parseDouble(Liquid.StringRoundUp2D(total_senior));

        total_energy_charges = Liquid.RoundUp(
                total_generation_charge +
                        total_transmission_charge +
                        total_systems_loss +
                        total_distrib_net_charge_1 +
                        total_sup_sys_chg +
                        total_met_charge_1 +
                        total_other_charges +
                        total_sub_dis_charge +
                        total_government_revenue +
                        total_universal +
                        total_fit_all);

        surcharge = Double.parseDouble(Liquid.surcharge); //ILP
        overdue = Double.parseDouble(Liquid.overdue); // inclusion

        total_moa = Double.parseDouble(Liquid.moa);

        total_current_bill = Liquid.RoundUp(
                total_generation_charge +
                        total_transmission_charge +
                        total_systems_loss +
                        total_distrib_net_charge_1 +
                        total_sup_sys_chg +
                        total_met_charge_1 +
                        total_other_charges +
                        total_sub_dis_charge +
                        total_government_revenue +
                        total_universal +
                        total_fit_all);
        total_amount_due = Liquid.RoundUp(total_energy_charges + total_senior +arrears + surcharge + overdue );
        total_amount_due2 = total_amount_due + surcharge;

        rentalfee =  Double.parseDouble(Liquid.rentalfee) > total_amount_due ? Double.parseDouble(Liquid.rentalfee) : total_amount_due;
        Liquid.rentalfee = String.valueOf(rentalfee); //gov subsidy
    }

    public float GetDistributionVat(double DisRev,double DivLifeline){
        float final_response = 0;
        final_response = (float) (((DisRev - 5) * DivLifeline) * 0.12);
        return final_response;
    }

    public float GetOtherVat(){
        float final_response = 0;
        final_response = (float) (5*0.12);
        return final_response;
    }
    public void Iselco2ElectricBillingComputaion(String KWH,
                                                 String Demand,
                                                 String RateCode,
                                                 String Classification,
                                                 String BillingCycle,
                                                 String Arrears,
                                                 String EDATag,
                                                 String SeniorTag) {

        KWH = KWH != "" ? KWH : "0";
        Demand = Demand != "" ? Demand : "0";

        Cursor result = BillingModel.GetRates(RateCode, Classification, BillingCycle);

        if (result.getCount() == 0) {
            return;
        }
        while (result.moveToNext()) {

            rate_description = result.getString(result.getColumnIndex("rate_description"));


            switch (rate_description) {
                case "Generation System Charge":
                    gen_sys_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmision System Charge":
                    trans_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                case "System Loss Charge":
                    sys_los_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Distribution Revenues

                case "Distribution System Charge":
                    dis_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Supply System Charge":
                    sup_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Retail Customer Charge (Supply)":
                    ret_cust_chg_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Retail Customer Charge (Metering)":
                    ret_cust_chg_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Metering System Charge":
                    met_sys_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Senior Citizen Discount":
                    sc_disc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Senior Citizen Subsidy":
                    sc_sub = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Universal Charges
                case "Missionary Elec. Charge":
                    missionary = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Environmental Charge":
                    environmental_chg = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "NPC Stranded Debts":
                    npc_stran_deb = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "NPC Stranded Costs":
                    npc_stran_cos = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "PPD Adj":
                    ucme_redc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Other Charges
                case "Contribution for CAPEX":
                    capex = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Lifeline Rate (Disc) / Subsidy":
                    lifeline = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Franchise Tax Recovery":
                    fran_tax_rec = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Fit - All Charge":
                    fit_all = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "PSALM - DAA":
                    psalm_daa = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Government Revenues
                case "Generation Vat":
                    gen_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmission Vat":
                    trans_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "System Loss Vat":
                    sys_loss_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Distribution Vat":
                    dis_vat = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

            }


        }

        //Generation and Transmission

        total_gen_sys_charge= RatesKWHComputation(KWH, String.valueOf(gen_sys_charge));
        total_trans_sys_chg = RatesKWHComputation(KWH, String.valueOf(trans_sys_chg));
        total_sys_los_chg = RatesKWHComputation(KWH, String.valueOf(sys_los_chg));

        //Distribution Revenue
        total_dis_sys_chg = RatesKWHComputation(KWH, String.valueOf(dis_sys_chg));
        total_sup_sys_chg = RatesKWHComputation(Demand, String.valueOf(sup_sys_chg));
        total_ret_cust_chg_1 = RatesKWHComputation(KWH, String.valueOf(ret_cust_chg_1));
        total_ret_cust_chg_2 = RatesKWHComputation(KWH, String.valueOf(ret_cust_chg_2));
        total_met_sys_chg = Liquid.RoundUp(met_sys_chg);
        total_sc_disc = RatesKWHComputation(KWH, String.valueOf(sc_disc));
        total_sc_sub = Liquid.RoundUp(sc_sub);

        //Universal Charges
        total_missionary = RatesKWHComputation(KWH, String.valueOf(missionary));
        total_environmental_chg = RatesKWHComputation(KWH, String.valueOf(environmental_chg));
        total_npc_stran_deb = RatesKWHComputation(KWH, String.valueOf(npc_stran_deb));
        total_npc_stran_cos = RatesKWHComputation(KWH, String.valueOf(npc_stran_cos));
        total_ucme_redc = RatesKWHComputation(KWH, String.valueOf(ucme_redc));

        //Other Charges
        total_capex = RatesKWHComputation(KWH, String.valueOf(capex));
        total_lifeline = RatesKWHComputation(KWH, String.valueOf(lifeline));
        total_fran_tax_rec = RatesKWHComputation(KWH, String.valueOf(fran_tax_rec));
        total_fit_all = RatesKWHComputation(KWH, String.valueOf(fit_all));
        total_psalm_daa = RatesKWHComputation(KWH, String.valueOf(psalm_daa));

        //Government Revenue
        total_generation = RatesKWHComputation(KWH, String.valueOf(gen_vat));
        total_transmission = RatesKWHComputation(KWH, String.valueOf(trans_vat));
        total_sys_loss_2 = RatesKWHComputation(KWH, String.valueOf(sys_loss_vat));
        total_distribution = RatesKWHComputation(KWH, String.valueOf(dis_vat));


        //Arrears
        arrears = Double.parseDouble(Arrears);

        //Senior Citizen
        total_lifeline = getLifelineDiscount(Classification, Double.parseDouble(KWH),total_trate);
        total_senior = getSeniorCitizen(Classification, SeniorTag, Double.parseDouble(KWH), total_trate,total_lifeline);


        //SubTotal
        total_gen_trans = Liquid.RoundUp(total_gen_sys_charge + total_trans_sys_chg + total_sys_los_chg);
        total_distribution_revenue = Liquid.RoundUp(total_dis_sys_chg +
                total_sup_sys_chg +
                total_ret_cust_chg_1 +
                total_ret_cust_chg_2 +
                total_met_sys_chg +
                total_sc_disc +
                total_sc_sub
        );
        total_universal = Liquid.RoundUp(total_missionary +
                total_environmental_chg +
                npc_stran_deb +
                npc_stran_cos +
                ucme_redc
        );
        total_others = Liquid.RoundUp(capex +
                lifeline +
                fran_tax_rec +
                fit_all +
                psalm_daa
        );
        total_government_revenue = Liquid.RoundUp(gen_vat +
                trans_vat +
                sys_loss_vat +
                dis_vat
        );
        total_energy_charges = Liquid.RoundUp(total_gen_trans +
                total_distribution_revenue +
                total_universal +
                total_others +
                total_government_revenue
        );
        total_rental_others = Liquid.RoundUp(
                Double.parseDouble(ReadingActivity.rentalfee) +
                        Double.parseDouble(ReadingActivity.ShareCap) +
                        Double.parseDouble(ReadingActivity.PoleRent ) +
                        Double.parseDouble(ReadingActivity.OtherBill)
        );

        total_current_bill = Liquid.RoundUp(total_energy_charges +
                total_rental_others +
                total_distribution
        );


        total_amount_due = Liquid.RoundUp(total_current_bill + arrears);

    }

    public void Batellec2ElectricBillingComputaion(String KWH,
                                                   String Demand,
                                                   String RateCode,
                                                   String Classification,
                                                   String BillingCycle,
                                                   String Arrears,
                                                   String EDATag,
                                                   String SeniorTag){


        Demand = !Demand.equals("") ? Demand : "0";

        Cursor result = BillingModel.GetRates(RateCode,Classification,BillingCycle);

        if(result.getCount() == 0){
            return;
        }
        while(result.moveToNext()) {

            rate_description =  result.getString(result.getColumnIndex("rate_description"));
            rd_id =  result.getString(result.getColumnIndex("R_Formula"));
            rd_group =  result.getString(result.getColumnIndex("R_Group"));


            switch (rate_description)
            {
                //Generation and Transmission
                case "Generation Charge":
                    generation_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Power Act Reduction":
                    power_act_reduc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmission Delivery Charge":

                    if (rd_id.equals("2"))
                    {
                        trans_del_charge_1 =Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    if (rd_id.equals("3"))
                    {
                        trans_del_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    break;

                case "CC/RSTC Refund":
                    if (rd_id.equals("2"))
                    {
                        rstc_refund_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));;
                    }
                    if (rd_id.equals("3"))
                    {
                        rstc_refund_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }

                    break;

                case "System Loss":
                    if (rd_group.equals("2"))
                    {
                        sys_loss_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    if (rd_group.equals("5"))
                    {
                        sys_loss_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }

                    break;
                case "Prev. Years Adj. on Pwr Cost":
                    power_cost_adj = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Distribution Revenue
                case "Distribution Network Charge":
                    if (rd_id.equals("2"))
                    {
                        distrib_net_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    if (rd_id.equals("3"))
                    {
                        distrib_net_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    if (rd_id.equals("5"))
                    {
                        distrib_net_charge_3 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }

                    break;
                case "Retail Electric Service Charge":
                    if (rd_id.equals("2"))
                    {
                        ret_elec_serv_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    if (rd_id.equals("4"))
                    {
                        ret_elec_serv_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    break;
                case "Metering Charge":
                    if (rd_id.equals("2"))
                    {
                        met_charge_1 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    if (rd_id.equals("4"))
                    {
                        met_charge_2 = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    }
                    break;
                case "Rein. Fund For Sustainable CAPEX":
                    rfsc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Other Charges
                case "Life Rate Subsidy (discount)":
                    lifeline = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Senior Citizen Subsidy (discount)":
                    senior = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //Government Revenue
                case "Generation":
                    generation = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Transmission":
                    transmission = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Distribution":
                    distribution = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Others":
                    others = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "GRAM/ICERA DAA VAT":
                    gram_icera_daa = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Real Property Tax":
                    real_prop_tax = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Missionary Electrification":
                    missionary_elec = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Environmental Charges":
                    environmental_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "Stranded Contract Cost":
                    stran_contract_cost = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "NPC Stranded Debts":
                    npc_stranded_debts = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "GRAM/ICERA DAA-ERC Order 6/20/17":
                    gram_icera_daa_erc = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "FIT-All (Renewable)":
                    fit_all = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

                //TRATE
                case "TRATE Generation Charge":
                    trate_gen_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "TRATE Transmission Charge":
                    trate_trans_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "TRATE System Loss Charge":
                    trate_sys_loss = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "TRATE Distribution Charge":
                    trate_dis_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "TRATE Supply Charge":
                    trate_sup_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;
                case "TRATE Metering Charge":
                    trate_met_charge = Double.parseDouble(result.getString(result.getColumnIndex("Rates_Price")));
                    break;

            }


        }

        //Generation and Transmission

        total_generation_charge = RatesKWHComputation(KWH, String.valueOf(generation_charge));
        total_power_act_reduc = RatesKWHComputation(KWH, String.valueOf(power_act_reduc));
        total_trans_del_charge_1 = RatesKWHComputation(KWH, String.valueOf(trans_del_charge_1));

        total_trans_del_charge_2 = RatesKWHComputation(Demand, String.valueOf(trans_del_charge_2));
        total_rstc_refund_1 = RatesKWHComputation(KWH, String.valueOf(rstc_refund_1));
        total_rstc_refund_2 = RatesKWHComputation(Demand, String.valueOf(rstc_refund_2));
        total_sys_loss_1 = RatesKWHComputation(KWH, String.valueOf(sys_loss_1));
        total_power_cost_adj =  RatesKWHComputation(KWH, String.valueOf(power_cost_adj));

        //Distribution Revenue
        total_distrib_net_charge_1 = RatesKWHComputation(KWH, String.valueOf(distrib_net_charge_1));
        total_distrib_net_charge_2 = RatesKWHComputation(Demand, String.valueOf(distrib_net_charge_2));
        total_distrib_net_charge_3 = RatesKWHComputation(KWH, String.valueOf(distrib_net_charge_3));
        total_ret_elec_serv_charge_1  = RatesKWHComputation(KWH, String.valueOf(ret_elec_serv_charge_1));
        total_ret_elec_serv_charge_2  = Liquid.RoundUp(ret_elec_serv_charge_2);
        total_met_charge_1 = RatesKWHComputation(KWH, String.valueOf(met_charge_1));
        total_met_charge_2 = Liquid.RoundUp(met_charge_2);
        total_rfsc = RatesKWHComputation(KWH, String.valueOf(rfsc));

        //Subsidies
        total_lifeline = RatesKWHComputation(KWH, String.valueOf(lifeline));
        total_senior = RatesKWHComputation(KWH, String.valueOf(senior));

        //Government Revenue
        total_generation = RatesKWHComputation(KWH, String.valueOf(generation));
        total_transmission = RatesKWHComputation(KWH, String.valueOf(transmission));
        total_sys_loss_2 = RatesKWHComputation(KWH, String.valueOf(sys_loss_2));
        total_distribution = RatesKWHComputation(KWH, String.valueOf(distribution));
        total_others = RatesKWHComputation(KWH, String.valueOf(others));
        total_gram_icera_daa = RatesKWHComputation(KWH, String.valueOf(gram_icera_daa));
        total_real_prop_tax = RatesKWHComputation(KWH, String.valueOf(real_prop_tax));
        total_missionary_elec = RatesKWHComputation(KWH, String.valueOf(missionary_elec));
        total_environmental_charge = RatesKWHComputation(KWH, String.valueOf(environmental_charge));
        total_stran_contract_cost = RatesKWHComputation(KWH, String.valueOf(stran_contract_cost));
        total_npc_stranded_debts = RatesKWHComputation(KWH, String.valueOf(npc_stranded_debts));


        total_gram_icera_daa_erc = RatesKWHComputation(KWH, String.valueOf(gram_icera_daa_erc));
        total_fit_all = RatesKWHComputation(KWH, String.valueOf(fit_all));

        //Trate
        total_trate_gen_charge = RatesKWHComputation(KWH, String.valueOf(trate_gen_charge));
        total_trate_trans_charge = RatesKWHComputation(KWH, String.valueOf(trate_trans_charge));
        total_trate_sys_loss = RatesKWHComputation(KWH, String.valueOf(trate_sys_loss ));
        total_trate_dis_charge = RatesKWHComputation(KWH, String.valueOf(trate_dis_charge));
        total_trate_sup_charge = RatesKWHComputation(KWH, String.valueOf(trate_sup_charge));
        total_trate_met_charge = RatesKWHComputation(KWH, String.valueOf(trate_met_charge));

        //Arrears
        arrears = Double.parseDouble(Arrears);

        //Trate Total
        total_trate = Liquid.RoundUp(total_trate_gen_charge + total_trate_trans_charge + total_trate_sys_loss+total_trate_dis_charge + total_trate_sup_charge + total_trate_met_charge);
        //Senior Citizen
        total_lifeline = getLifelineDiscount(Classification,Double.parseDouble(KWH),total_trate);
        total_senior = getSeniorCitizen(Classification,SeniorTag, Double.parseDouble(KWH),total_trate,total_lifeline);


        //SubTotal
        total_gen_trans = Liquid.RoundUp(total_generation_charge + total_power_act_reduc + total_trans_del_charge_1 + total_trans_del_charge_2 + total_rstc_refund_1+total_sys_loss_1 + total_power_cost_adj);

        total_distribution_revenue = Liquid.RoundUp(  total_distrib_net_charge_1 +
                total_distrib_net_charge_2 +
                total_distrib_net_charge_3 +
                total_ret_elec_serv_charge_1 +
                total_ret_elec_serv_charge_2+
                total_met_charge_1 +
                total_met_charge_2+
                total_rfsc
        );

        total_other_charges = Liquid.RoundUp(total_lifeline + total_senior);

        total_government_revenue = Liquid.RoundUp(total_generation+
                total_transmission+
                total_sys_loss_2+
                total_distribution+
                total_others+
                total_gram_icera_daa+
                total_real_prop_tax+
                total_missionary_elec+
                total_environmental_charge+
                total_stran_contract_cost+
                total_npc_stranded_debts +
                total_gram_icera_daa_erc +
                total_fit_all);

        total_moa = 0;

        total_current_bill = Liquid.RoundUp(total_gen_trans +
                total_distribution_revenue +
                total_other_charges +
                total_government_revenue +
                total_moa);

        eda = getEDA(EDATag,total_gen_trans,
                total_distribution_revenue,
                total_other_charges,
                total_moa);

        total_amount_due = Liquid.RoundUp(total_current_bill + eda);
    }


    public  double getLifelineIleco2Discount(String Classification, double KWH, double trate)
    {

        if (Classification.equals("R"))
        {
            if (KWH <= 15.4)
            {
                lifeline = (float) -0.50;
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.50;

            }
            else if (KWH <= 16.4 && KWH >= 15.5)
            {
                lifeline = (float) -0.40;
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.60;
            }
            else if (KWH <= 17.4 && KWH >= 16.5)
            {
                lifeline = (float) -0.30;
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.70;
            }
            else if (KWH <= 18.4 && KWH >= 17.5)
            {
                lifeline = (float) -(0.20);
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.80;
            }
            else if (KWH <= 19.4 && KWH >= 18.5)
            {
                lifeline = (float) -(0.10);
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.90;
            }

            else if ((KWH >= 19.5) && (KWH <= 25.4))
            {
                lifeline = (float) -(0.05);
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.95;
            }
            else
            {
                lifeline = lifeline;
                total_lifeline = Liquid.RoundUp((lifeline * KWH));
            }
        }
        else
        {
            lifeline = lifeline;
            total_lifeline = Liquid.RoundUp((lifeline * KWH));
        }


        return Liquid.RoundUp(total_lifeline);

    }

    public  double getLifelineMorePowerDiscount(String Classification, double KWH, double trate)
    {

        if (Classification.equals("R"))
        {
            if (KWH >= 0 && KWH <= 20)
            {
                lifeline = (float) -1;
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0;

            }
            else if (KWH >= 21 && KWH <= 50)
            {
                lifeline = (float) -0.50;
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.50;
            }
            else if (KWH >= 51 && KWH <= 60)
            {
                lifeline = (float) -0.45;
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.55;
            }
            else if (KWH >= 61 && KWH <= 70)
            {
                lifeline = (float) -(0.35);
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.65;
            }
            else if (KWH >= 71 && KWH <= 80)
            {
                lifeline = (float) -(0.20);
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.80;
            }

            else if ((KWH >= 81) && (KWH <= 95))
            {
                lifeline = (float) -(0.10);
                total_lifeline = Liquid.RoundUp(trate * lifeline);
                total_lifeline_dis = (float) 0.90;
            }

            else if ((KWH >= 96))
            {
                lifeline = lifeline;
                total_lifeline = Liquid.RoundUp((lifeline * KWH));
            }

        }
        else
        {
            lifeline = lifeline;
            total_lifeline = Liquid.RoundUp((lifeline * KWH));
        }


        return Liquid.RoundUp(total_lifeline);

    }


    public  double getLifelineDiscount(String Classification, double KWH, double trate)
    {
        if (Classification == "Residential")
        {
            if (KWH <= 15)
            {
                lifeline = (float) -0.50;
                total_lifeline = Liquid.RoundUp((trate * KWH + 5) * lifeline);
            }
            else if ((KWH >= 16) && (KWH <= 20))
            {
                lifeline = (float) -0.45;
                total_lifeline = Liquid.RoundUp((trate * KWH + 5) * lifeline);
            }
            else if ((KWH >= 21) && (KWH <= 25))
            {
                lifeline = (float) -0.40;
                total_lifeline = Liquid.RoundUp((trate * KWH + 5) * lifeline);
            }
            else if ((KWH >= 26)
                    && (KWH <= 30))
            {
                lifeline = (float) -(0.35);
                total_lifeline = Liquid.RoundUp((trate * KWH + 5) * lifeline);
            }
            else if ((KWH >= 31)
                    && (KWH <= 35))
            {
                lifeline = (float) -(0.20);
                total_lifeline = Liquid.RoundUp((trate * KWH + 5) * lifeline);
            }
            else if ((KWH >= 36)
                    && (KWH <= 40))
            {
                lifeline = (float) -(0.10);
                total_lifeline = Liquid.RoundUp((trate * KWH + 5) * lifeline);
            }

            else if ((KWH >= 41)
                    && (KWH <= 45))
            {
                lifeline = (float) -(0.05);
                total_lifeline = Liquid.RoundUp((trate * KWH + 5) * lifeline);
            }
            else
            {
                lifeline = lifeline;
                total_lifeline = Liquid.RoundUp((lifeline * KWH));
            }
        }
        else
        {
            lifeline = lifeline;
            total_lifeline = Liquid.RoundUp((lifeline * KWH));
        }

        return Liquid.RoundUp(total_lifeline);

    }


    public double getMorePowerSupplyCharge(double rateSupplyCharge,String rateClass,double consumption){
        //validation for supply charge
        double supplyCharge = 0;
        switch (rateClass)
        {
            case "R":
                supplyCharge = rateSupplyCharge * consumption;
                break;
            default:
                supplyCharge = rateSupplyCharge;
                break;
        }
        return Liquid.RoundUp(supplyCharge);
    }
    public  double getSeniorIleco2Citizen(String Classification,
                                          String scd,
                                          double consumption,
                                          double trate,
                                          double total_lifeline)
    {
        double msenior = senior;
        if (Classification.equals("R"))
        {
            if (consumption <= 100.0)
            {
                switch (scd)
                {
                    case "1":
                        msenior = (float) -0.05;
                      if (total_lifeline <= 0)
                      {
                          total_senior = Liquid.RoundUp((trate + total_lifeline) * msenior);
                      }
                      else
                      {
                          total_senior = Liquid.RoundUp(trate * msenior);
                      }
                        break;

                    default:
                        if(total_lifeline <= 0){
                            total_senior = 0;
                        }else{
                            total_senior = Liquid.RoundUp((msenior * consumption));
                        }

                        break;
                }
            }
            else
            {
                total_senior = Liquid.RoundUp((msenior * consumption));
            }

        }

        else
        {
            total_senior = Liquid.RoundUp((msenior * consumption));
        }


        return Liquid.RoundUp(total_senior);

    }


    public  double getSeniorMorePowerCitizen(String Classification,
                                          String scd,
                                          double consumption,
                                          double trate
                                       )
    {
        double msenior = senior;
        if (Classification.equals("R"))
        {
            if (consumption <= 100.0)
            {
                switch (scd)
                {
                    case "1":
                        msenior = (float) -0.05;
                              total_senior = Liquid.RoundUp((trate  * msenior));
                        break;

                    default:

                            total_senior = Liquid.RoundUp((0));

                        break;
                }
            }
        }




        return Liquid.RoundUp(total_senior);

    }
    public  double getSeniorCitizen(String Classification,
                                    String scd,
                                    double consumption,
                                    double trate,

                                    double total_lifeline)
    {

        if (Classification == "Residential")
        {
            if (consumption <= 100.0)
            {
                switch (scd)
                {
                    case "1":
                        senior = (float) -0.05;
                        if (total_lifeline >= 0)
                        {
                            total_senior = Liquid.RoundUp((((trate * consumption + 5) * 1) + 0) * senior);
                        }
                        else
                        {
                            total_senior = Liquid.RoundUp((((trate * consumption + 5) * 1) + total_lifeline) * senior);
                        }

                        break;
                    default:
                        senior = senior;
                        total_senior = Liquid.RoundUp((senior * consumption));
                        break;
                }
            }
            else
            {
                senior = senior;
                total_senior = Liquid.RoundUp((senior * consumption));
            }

        }

        else
        {
            senior = senior;
            total_senior = Liquid.RoundUp((senior * consumption));
        }


        return Liquid.RoundUp(total_senior);

    }


    public  double getEDA(String eda,
                          double total_gen_trans,
                          double total_distribution_revenue,
                          double total_other_charges,
                          double total_moa)
    {
        float E1 = 0;
        switch (eda)
        {
            case "1":
                E1 = (float) -((total_gen_trans + total_distribution_revenue + total_other_charges + total_moa) * 0.10);
                break;
            default:
                E1 = 0;
                break;
        }

        return Liquid.RoundUp(E1);

    }

    public double GetLoadFactor(double KWH,double KW, Date dateFrom, Date dateTo){
        double loadFactor = 0;


        long days = Liquid.diffDate(dateFrom,dateTo);

        if(days == 0){
            days = 1;
        }

        loadFactor = KWH / ((KW) * 24 * (days));

        return Liquid.RoundUp(loadFactor);
    }


}


