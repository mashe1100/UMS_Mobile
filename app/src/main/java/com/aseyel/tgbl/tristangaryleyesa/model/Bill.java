package com.aseyel.tgbl.tristangaryleyesa.model;

import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidBilling;

public class Bill {

    public  BillItem[] BillItemsBatelec2 =
            {
                    new BillItem("RATE COMPONENT",                  "RATE" , "AMOUNT",    ItemStyle.Header),
                    new BillItem("Generation Charge",                String.valueOf(LiquidBilling.generation_charge), String.valueOf(LiquidBilling.total_generation_charge),ItemStyle.Detail),
                    new BillItem("Power Act Reduction",              String.valueOf(LiquidBilling.power_act_reduc), String.valueOf(LiquidBilling.total_power_act_reduc),           ItemStyle.Detail),
                    new BillItem("Trans Delivery Charge",            String.valueOf(LiquidBilling.trans_del_charge_1), String.valueOf(LiquidBilling.total_trans_del_charge_1),        ItemStyle.Detail),
                    new BillItem("CC/RSTC Refund",                   String.valueOf(LiquidBilling.rstc_refund_1), String.valueOf(LiquidBilling.total_rstc_refund_1),             ItemStyle.Detail),
                    new BillItem("Trans Delivery Charge",            String.valueOf(LiquidBilling.trans_del_charge_2), String.valueOf(LiquidBilling.total_trans_del_charge_2),        ItemStyle.Detail),
                    new BillItem("CC/RSTC Refund",                   String.valueOf(LiquidBilling.rstc_refund_2), String.valueOf(LiquidBilling.total_rstc_refund_2),         ItemStyle.Detail),
                    new BillItem("System Loss",                      String.valueOf(LiquidBilling.sys_loss_1),  String.valueOf(LiquidBilling.total_sys_loss_1),         ItemStyle.Detail),
                    new BillItem("Power Cost Adjustment",            String.valueOf(LiquidBilling.power_cost_adj),  String.valueOf(LiquidBilling.total_power_cost_adj),         ItemStyle.Detail),
                    new BillItem("GEN & TRANS",                      String.valueOf(LiquidBilling.total_gen_trans),                                ItemStyle.Subtotal),
                    new BillItem(                                                                                                                        ItemStyle.Separator),
                    new BillItem("Distribution Network",             String.valueOf(LiquidBilling.distrib_net_charge_1), String.valueOf(LiquidBilling.total_distrib_net_charge_1),           ItemStyle.Detail),
                    new BillItem("Distribution Network",             String.valueOf(LiquidBilling.distrib_net_charge_2), String.valueOf(LiquidBilling.total_distrib_net_charge_2),          ItemStyle.Detail),
                    new BillItem("Distribution Network",             String.valueOf(LiquidBilling.distrib_net_charge_3),  String.valueOf(LiquidBilling.total_distrib_net_charge_3),          ItemStyle.Detail),
                    new BillItem("Retail Electric Service",          String.valueOf(LiquidBilling.ret_elec_serv_charge_1), String.valueOf(LiquidBilling.total_ret_elec_serv_charge_1),        ItemStyle.Detail),
                    new BillItem("Retail Electric Service",          String.valueOf(LiquidBilling.ret_elec_serv_charge_2), String.valueOf(LiquidBilling.total_ret_elec_serv_charge_2),        ItemStyle.Detail),
                    new BillItem("Metering Charge",                  String.valueOf(LiquidBilling.met_charge_1), String.valueOf(LiquidBilling.total_met_charge_1),                    ItemStyle.Detail),
                    new BillItem("Metering Charge",                  String.valueOf(LiquidBilling.met_charge_2), String.valueOf(LiquidBilling.total_met_charge_2),       ItemStyle.Detail),
                    new BillItem("RFSC",                             String.valueOf(LiquidBilling.rfsc), String.valueOf(LiquidBilling.total_rfsc),              ItemStyle.Detail),
                    new BillItem("DISTRIBUTION REVENUE",             String.valueOf(LiquidBilling.total_distribution_revenue),          ItemStyle.Subtotal),
                    new BillItem(                                                                                                                   ItemStyle.Separator),
                    new BillItem("Life Rate Subsidy",                   String.valueOf(LiquidBilling.lifeline),  String.valueOf(LiquidBilling.total_lifeline),         ItemStyle.Detail),
                    new BillItem("Senior Citizen Subsidy",              String.valueOf(LiquidBilling.senior), String.valueOf(LiquidBilling.total_senior),           ItemStyle.Detail),
                    new BillItem("OTHER CHARGES",                       String.valueOf(LiquidBilling.total_other_charges),              ItemStyle.Subtotal),
                    new BillItem(                                                                                                             ItemStyle.Separator),
                    new BillItem("Generation",                         String.valueOf(LiquidBilling.generation_charge),  String.valueOf(LiquidBilling.total_generation_charge),             ItemStyle.Detail),
                    new BillItem("Transmission",                       String.valueOf(LiquidBilling.transmission), String.valueOf(LiquidBilling.total_transmission),            ItemStyle.Detail),
                    new BillItem("System Loss",                        String.valueOf(LiquidBilling.sys_loss_2), String.valueOf(LiquidBilling.total_sys_loss_2),            ItemStyle.Detail),
                    new BillItem("Distribution",                       String.valueOf(LiquidBilling.distribution), String.valueOf(LiquidBilling.total_distribution),            ItemStyle.Detail),
                    new BillItem("Others",                             String.valueOf(LiquidBilling.others),  String.valueOf(LiquidBilling.total_others),           ItemStyle.Detail),
                    new BillItem("GRAM/ICERA DAA VAT",                 String.valueOf(LiquidBilling.gram_icera_daa), String.valueOf(LiquidBilling.total_gram_icera_daa),      ItemStyle.Detail),
                    new BillItem("Missionary Electrification",         String.valueOf(LiquidBilling.missionary_elec), String.valueOf(LiquidBilling.total_missionary_elec),           ItemStyle.Detail),
                    new BillItem("Environmental Charges",              String.valueOf(LiquidBilling.environmental_charge),  String.valueOf(LiquidBilling.total_environmental_charge),          ItemStyle.Detail),
                    new BillItem("Stranded Contract Cost",             String.valueOf(LiquidBilling.stran_contract_cost), String.valueOf(LiquidBilling.total_stran_contract_cost),              ItemStyle.Detail),
                    new BillItem("NPC Stranded Debts",                 String.valueOf(LiquidBilling.npc_stranded_debts), String.valueOf(LiquidBilling.total_npc_stranded_debts),      ItemStyle.Detail),
                    new BillItem("GRAM/ICERA DAA ERC-Order",           String.valueOf(LiquidBilling.gram_icera_daa_erc), String.valueOf(LiquidBilling.total_gram_icera_daa_erc),       ItemStyle.Detail),
                    new BillItem("FIT-All (Renewable)",                String.valueOf(LiquidBilling.fit_all), String.valueOf(LiquidBilling.total_fit_all),       ItemStyle.Detail),
                    new BillItem("GOVERNMENT REVENUE",                 String.valueOf(LiquidBilling.total_government_revenue),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    /*new BillItem("BACK BILLING",                              "M6", ItemStyle.Footer),
                    new BillItem("TAX RECOVERY ADJUSTMENT",            "M1", ItemStyle.Footer),
                    new BillItem("REFUND JAN. 2014 BILLING",                "M2", ItemStyle.Footer),
                    new BillItem("POWERBILL RESTRUCTURING",                "M3", ItemStyle.Footer),
                    new BillItem("ADJUSTMENT",                              "M4", ItemStyle.Footer),
                    new BillItem("ADVANCE PAYMENT",                              "M5", ItemStyle.Footer),
                    new BillItem("BACK BILLING NOV 2011",                              "M7", ItemStyle.Footer),
                    new BillItem("BILL DEPOSIT - INTEREST",                              "M8", ItemStyle.Footer),
                    new BillItem("BILL DEPOSIT - MOA",                              "M9", ItemStyle.Footer),
                    new BillItem("EDA BACKBILLING",                              "M10", ItemStyle.Footer),
                    new BillItem("ENERGY DEPOSIT AMORT",                              "M11", ItemStyle.Footer),
                    new BillItem("GENERATION ADJ DEC 2013",                              "M12", ItemStyle.Footer),
                    new BillItem("GENERATION ADJ JAN 2014",                              "M13", ItemStyle.Footer),
                    new BillItem("GOVERNMENT SUBSIDY",                              "M14", ItemStyle.Footer),
                    new BillItem("LIFELINE DISCOUNT",                              "M15", ItemStyle.Footer),
                    new BillItem("MATERIAL DEPOSIT",                              "M16", ItemStyle.Footer),
                    new BillItem("MATERIAL LOAN PROGRAM",                              "M17", ItemStyle.Footer),
                    new BillItem("METER REPLACEMENT LOAN",                     "M18", ItemStyle.Footer),
                    new BillItem("MRL ADJUSTMENT",                             "M19", ItemStyle.Footer),
                    new BillItem("PBR ADJUSTMENT",                             "M20", ItemStyle.Footer),
                    new BillItem("PENALTY",                                    "M21", ItemStyle.Footer),
                    new BillItem("POWERBILL ADJUSTMENT",                       "M22", ItemStyle.Footer),
                    new BillItem("POWERBILL OFFSETTING",                       "M23", ItemStyle.Footer),
                    new BillItem("PROMPT PAYMENT DISCOUNT",                    "M24", ItemStyle.Footer),
                    new BillItem("REFUND DEC 2013 BILLING",                    "M25", ItemStyle.Footer),
                    new BillItem("SENIOR ADJUSTMENT",                          "M26", ItemStyle.Footer),
                    new BillItem("SENIOR DISCOUNT",                            "M27", ItemStyle.Footer),
                    new BillItem("TRANSFORMER COST",                           "M28", ItemStyle.Footer),
                    new BillItem("TRANSFORMER RENTAL",                         "M29", ItemStyle.Footer),
                    new BillItem("VAT EXEMPTION",                              "M30", ItemStyle.Footer),
                    new BillItem(                                                       ItemStyle.Separator),
                    new BillItem("ENERGY DEPOSIT",                              "E1", ItemStyle.Footer),*/
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("TOTAL AMOUNT DUE",                  String.valueOf(LiquidBilling.total_current_bill), ItemStyle.Total),
                    new BillItem("Previous Unpaid Balance",           String.valueOf(LiquidBilling.arrears), ItemStyle.Aftertotal),
                    new BillItem(                                           ItemStyle.Separator),


            };


    public  BillItem[] BillItemsIselco2 =
            {
                    new BillItem("RATE COMPONENT",                      "RATE" , "AMOUNT", ItemStyle.Header),
                    new BillItem("Generation System Charge",             String.valueOf(LiquidBilling.gen_sys_charge), String.valueOf(LiquidBilling.total_gen_sys_charge),       ItemStyle.Detail),
                    new BillItem("Transmission Sys Chg",                 String.valueOf(LiquidBilling.trans_sys_chg), String.valueOf(LiquidBilling.total_trans_sys_chg),       ItemStyle.Detail),
                    new BillItem("System Loss Chg",                      String.valueOf(LiquidBilling.sys_los_chg),  String.valueOf(LiquidBilling.total_sys_los_chg),       ItemStyle.Detail),
                    new BillItem("Generation & Transmission",            String.valueOf(LiquidBilling.total_gen_trans),       ItemStyle.Subtotal),
                    new BillItem(                                                               ItemStyle.Separator),
                    new BillItem("Distribution Sys Chg",             String.valueOf(LiquidBilling.dis_sys_chg), String.valueOf(LiquidBilling.total_dis_sys_chg),       ItemStyle.Detail),
                    new BillItem("Supply Sys Chg",                   String.valueOf(LiquidBilling.sup_sys_chg), String.valueOf(LiquidBilling.total_sup_sys_chg),       ItemStyle.Detail),
                    new BillItem("Retail Customer Chg",              String.valueOf(LiquidBilling.ret_cust_chg_1), String.valueOf(LiquidBilling.total_ret_cust_chg_1),       ItemStyle.Detail),
                    new BillItem("Retail Cust Met Chg",              String.valueOf(LiquidBilling.ret_cust_chg_2), String.valueOf(LiquidBilling.total_ret_cust_chg_2),       ItemStyle.Detail),
                    new BillItem("Metering Sys Chg",                 String.valueOf(LiquidBilling.met_sys_chg), String.valueOf(LiquidBilling.total_met_sys_chg),       ItemStyle.Detail),
                    new BillItem("Senior Citizen Discnt",                         String.valueOf(LiquidBilling.sc_disc), String.valueOf(LiquidBilling.total_sc_disc),       ItemStyle.Detail),
                    new BillItem("Senior Citizen Subsidy",                         String.valueOf(LiquidBilling.sc_sub), String.valueOf(LiquidBilling.total_sc_sub),       ItemStyle.Detail),
                    new BillItem("Distribution Revenues",                     String.valueOf(LiquidBilling.total_distribution_revenue),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("Missionary Elect. Chg",                 String.valueOf(LiquidBilling.missionary), String.valueOf(LiquidBilling.total_missionary),       ItemStyle.Detail),
                    new BillItem("Environmental Chg",                   String.valueOf(LiquidBilling.environmental_charge),  String.valueOf(LiquidBilling.total_environmental_charge),       ItemStyle.Detail),
                    new BillItem("NPC Stranded Debts",                 String.valueOf(LiquidBilling.npc_stran_deb), String.valueOf(LiquidBilling.total_npc_stran_deb),       ItemStyle.Detail),
                    new BillItem("NPC Stranded Cost",                   String.valueOf(LiquidBilling.npc_stran_cos), String.valueOf(LiquidBilling.total_npc_stran_cos),       ItemStyle.Detail),
                    new BillItem("UCME REDC",                           String.valueOf(LiquidBilling.ucme_redc), String.valueOf(LiquidBilling.total_ucme_redc),       ItemStyle.Detail),
                    new BillItem("Universal Charges",                       String.valueOf(LiquidBilling.total_universal),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("Contribution for Capex",                 String.valueOf(LiquidBilling.capex), String.valueOf(LiquidBilling.total_capex),       ItemStyle.Detail),
                    new BillItem("Lifeline Rate Discount",          String.valueOf(LiquidBilling.lifeline),  String.valueOf(LiquidBilling.total_lifeline),       ItemStyle.Detail),
                    new BillItem("Fran Tax Recovery",                 String.valueOf(LiquidBilling.fran_tax_rec),  String.valueOf(LiquidBilling.total_fran_tax_rec),       ItemStyle.Detail),
                    new BillItem("FIT-ALL",                          String.valueOf(LiquidBilling.fit_all), String.valueOf(LiquidBilling.total_fit_all),       ItemStyle.Detail),
                    new BillItem("PSALM-DAA",                       String.valueOf(LiquidBilling.psalm_daa), String.valueOf(LiquidBilling.total_psalm_daa),       ItemStyle.Detail),
                    new BillItem("Other Charges",                       String.valueOf(LiquidBilling.total_others),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("Generation Vat",                        String.valueOf(LiquidBilling.gen_vat), String.valueOf(LiquidBilling.total_gen_vat),       ItemStyle.Detail),
                    new BillItem("Transmission Vat",                       String.valueOf(LiquidBilling.trans_vat),  String.valueOf(LiquidBilling.total_trans_vat),       ItemStyle.Detail),
                    new BillItem("System Loss Vat",                        String.valueOf(LiquidBilling.sys_loss_vat),  String.valueOf(LiquidBilling.total_sys_loss_vat),       ItemStyle.Detail),
                    new BillItem("Government Revenues",                   String.valueOf(LiquidBilling.total_government_revenue),       ItemStyle.Subtotal),
                    new BillItem(                                                                                                           ItemStyle.Separator),
                    new BillItem("Total Energy Charges",                     String.valueOf(LiquidBilling.total_energy_charges),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("Distribution Vat",                       String.valueOf(LiquidBilling.dis_vat), String.valueOf(LiquidBilling.dis_vat),       ItemStyle.Detail),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("Share Capital",                String.valueOf(Liquid.ShareCap),       ItemStyle.Footer),
                    new BillItem("Pole Rental",                   String.valueOf(Liquid.PoleRent),       ItemStyle.Footer),
                    new BillItem("Transformer Rental",              String.valueOf(Liquid.rentalfee),       ItemStyle.Footer),
                    new BillItem("Others",                       String.valueOf(Liquid.OtherBill),       ItemStyle.Footer),
                    new BillItem("Rentals and Others",                    String.valueOf(LiquidBilling.total_rental_others),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("CURRENT BILL",                        String.valueOf(LiquidBilling.total_current_bill), ItemStyle.Total),
                    new BillItem("SURCHARGE VAT",               String.valueOf(LiquidBilling.arrears), ItemStyle.Total),
                    new BillItem("AFTER DUE",                         String.valueOf(LiquidBilling.total_amount_due), ItemStyle.Total),
                    new BillItem(                                                                                       ItemStyle.Separator),
                    new BillItem("NOTICE: WITH ARREARS," +
                            "Please settle your unpaid bills to our," +
                            "nearest ISELCO II Office. Thank you.",   "1", ItemStyle.Notice3),
                    new BillItem("DISCONNECTION AFTER DUE ",     Liquid.discondate, ItemStyle.Notice2),
                    new BillItem("Due Date on Current Bill: " ,      Liquid.duedate, ItemStyle.Aftertotal),
                    new BillItem(                                           ItemStyle.Separator),

            };

    public  BillItem[] BillItemsIleco2 =
            {
                    new BillItem("RATE COMPONENT",                      "RATE" , "AMOUNT", ItemStyle.Header),
                    new BillItem("Generation Charge",             String.valueOf(LiquidBilling.gen_sys_charge), String.valueOf(LiquidBilling.total_gen_sys_charge),       ItemStyle.Detail),
                    new BillItem("Power Act Reduction",                 String.valueOf(LiquidBilling.power_act_reduc), String.valueOf(LiquidBilling.total_power_act_reduc),       ItemStyle.Detail),
                    new BillItem("Transmission System Charge",                 String.valueOf(LiquidBilling.trans_del_charge_1), String.valueOf(LiquidBilling.total_trans_del_charge_1),       ItemStyle.Detail),
                    new BillItem("Transmission Demand Charge",                 String.valueOf(LiquidBilling.trans_del_charge_2), String.valueOf(LiquidBilling.total_trans_del_charge_2),       ItemStyle.Detail),
                    new BillItem("System Loss Charge",                      String.valueOf(LiquidBilling.sys_loss_1),  String.valueOf(LiquidBilling.total_sys_loss_1),       ItemStyle.Detail),
                    new BillItem("Generation & Transmission",            String.valueOf(LiquidBilling.total_gen_trans),       ItemStyle.Subtotal),
                    new BillItem(                                                               ItemStyle.Separator),
                    new BillItem("Distribution System Charge",             String.valueOf(LiquidBilling.distrib_net_charge_1), String.valueOf(LiquidBilling.total_distrib_net_charge_1),       ItemStyle.Detail),
                    new BillItem("Distribution Demand Charge",             String.valueOf(LiquidBilling.distrib_net_charge_2), String.valueOf(LiquidBilling.total_distrib_net_charge_2),       ItemStyle.Detail),
                    new BillItem("Retail Service Charge",              String.valueOf(LiquidBilling.ret_elec_serv_charge_1), String.valueOf(LiquidBilling.total_ret_elec_serv_charge_1),       ItemStyle.Detail),
                    new BillItem("Retail Demand Charge",              String.valueOf(LiquidBilling.ret_elec_serv_charge_2), String.valueOf(LiquidBilling.total_ret_elec_serv_charge_2),       ItemStyle.Detail),
                    new BillItem("Metering System Charge",                 String.valueOf(LiquidBilling.met_charge_1), String.valueOf(LiquidBilling.total_met_charge_1),       ItemStyle.Detail),
                    new BillItem("Metering Retail Customer",                         String.valueOf(LiquidBilling.met_charge_2), String.valueOf(LiquidBilling.total_met_charge_2),       ItemStyle.Detail),
                    new BillItem("Distribution Connection",                         String.valueOf(LiquidBilling.distrib_net_charge_3), String.valueOf(LiquidBilling.total_distrib_net_charge_3),       ItemStyle.Detail),
                    new BillItem("Final Loan Condonation",                         String.valueOf(LiquidBilling.final_loan_con), String.valueOf(LiquidBilling.total_final_loan_con),       ItemStyle.Detail),
                    new BillItem("Distribution Revenues",                     String.valueOf(LiquidBilling.total_distribution_revenue),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("Lifeline Rate Subsidy",                 String.valueOf(LiquidBilling.lifeline),  String.valueOf(LiquidBilling.total_lifeline),       ItemStyle.Detail),
                    new BillItem("Interclass Cross Subsidy",                          String.valueOf(LiquidBilling.interclass), String.valueOf(LiquidBilling.total_interclass),       ItemStyle.Detail),
                    new BillItem("Other Charges",                       String.valueOf(LiquidBilling.total_others),       ItemStyle.Subtotal),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("RFSC",                             String.valueOf(LiquidBilling.rfsc), String.valueOf(LiquidBilling.total_rfsc),       ItemStyle.Detail),
                    new BillItem("Prompt Payment Disc. Adj.",          String.valueOf(LiquidBilling.prompt_payment_disc_adj),  String.valueOf(LiquidBilling.total_prompt_payment_disc_adj),       ItemStyle.Detail),
                    new BillItem(                                           ItemStyle.Separator),
                    new BillItem("Generation VAT",                        String.valueOf(LiquidBilling.gen_vat), String.valueOf(LiquidBilling.total_generation),       ItemStyle.Detail),
                    new BillItem("Transmission VAT",                       String.valueOf(LiquidBilling.trans_vat),  String.valueOf(LiquidBilling.total_transmission),       ItemStyle.Detail),
                    new BillItem("System Loss VAT",                        String.valueOf(LiquidBilling.sys_loss_vat),  String.valueOf(LiquidBilling.total_sys_loss_2),       ItemStyle.Detail),
                    new BillItem("Distribution VAT",                        String.valueOf(LiquidBilling.dis_vat),  String.valueOf(LiquidBilling.total_distribution),       ItemStyle.Detail),
                    new BillItem("Other VAT",                        String.valueOf(LiquidBilling.others_vat),  String.valueOf(LiquidBilling.total_others_vat),       ItemStyle.Detail),
                    new BillItem("Value Added Taxes",                   String.valueOf(LiquidBilling.total_government_revenue),       ItemStyle.Subtotal),
                    new BillItem(                                               ItemStyle.Separator),
                    new BillItem("ME - NPC SPUG",                       String.valueOf(LiquidBilling.missionary_elec), String.valueOf(LiquidBilling.total_missionary_elec),       ItemStyle.Detail),
                    new BillItem("Environmental Charges",               String.valueOf(LiquidBilling.environmental_charge),  String.valueOf(LiquidBilling.total_environmental_charge),       ItemStyle.Detail),
                    new BillItem("NPC Stranded Debts",                  String.valueOf(LiquidBilling.npc_stran_deb), String.valueOf(LiquidBilling.total_npc_stran_deb),       ItemStyle.Detail),
                    new BillItem("NPC Stranded Cost",                   String.valueOf(LiquidBilling.npc_stran_cos), String.valueOf(LiquidBilling.total_npc_stran_cos),       ItemStyle.Detail),
                    new BillItem("DUs Stranded Contract Cost",                   String.valueOf(LiquidBilling.stran_contract_cost), String.valueOf(LiquidBilling.total_stran_contract_cost),       ItemStyle.Detail),
                    new BillItem("Equalization Taxes",                           String.valueOf(LiquidBilling.equalization_taxes), String.valueOf(LiquidBilling.total_equalization_taxes),       ItemStyle.Detail),
                    new BillItem("Cross-Subsidy Removal",                           String.valueOf(LiquidBilling.cross_subsidy_removal), String.valueOf(LiquidBilling.total_cross_subsidy_removal),       ItemStyle.Detail),
                    new BillItem("ME - Renewable Energy Dev",                           String.valueOf(LiquidBilling.me_renewable_energy_dev), String.valueOf(LiquidBilling.total_me_renewable_energy_dev),       ItemStyle.Detail),
                    new BillItem("FIT-ALL (Renewable)",                           String.valueOf(LiquidBilling.fit_all), String.valueOf(LiquidBilling.total_fit_all),       ItemStyle.Detail),
                    new BillItem("PSALM Recovery",                           String.valueOf(LiquidBilling.psalm_daa), String.valueOf(LiquidBilling.total_psalm_daa),       ItemStyle.Detail),
                    new BillItem("Universal Charges (Non- Vat)",                       String.valueOf(LiquidBilling.total_universal),       ItemStyle.Subtotal),
                    new BillItem(                                                                                                           ItemStyle.Separator),
                    new BillItem("Senior Citizen Subsidy",                           String.valueOf(LiquidBilling.senior), String.valueOf(LiquidBilling.total_senior),       ItemStyle.Detail),
                    new BillItem(                                                                                                           ItemStyle.Separator),
                    new BillItem("Amount Due before Due",                        String.valueOf(LiquidBilling.total_current_bill), ItemStyle.Total),
                    new BillItem("Previous unpaid balance",                     String.valueOf(LiquidBilling.arrears), ItemStyle.OptionalFooterAdditional),
                    new BillItem("Penalty Fee",                     String.valueOf(LiquidBilling.arrears_additional), ItemStyle.OptionalFooterAdditional),
                    new BillItem("Service Fee",                     String.valueOf(LiquidBilling.arrears_additional), ItemStyle.OptionalFooterAdditional),
                    new BillItem("ARREARS (Due immediately)",                         String.valueOf(LiquidBilling.arrears+(LiquidBilling.arrears_additional*2)), ItemStyle.OptionalFooterAdditional),
                    new BillItem("TOTAL AMOUNT DUE",                        String.valueOf(LiquidBilling.total_amount_due), ItemStyle.Total),
                    new BillItem("Due Date for "+Liquid.dateChangeFormat(Liquid.BillMonth,"MM","MMM") +" "+Liquid.BillYear ,      Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","MM/dd/yyyy"), ItemStyle.Aftertotal),
                    new BillItem(                                           ItemStyle.Separator),

            };

    public  BillItem[] BillSummaryMorePower =
            {
                    new BillItem(											ItemStyle.Separator),
                    new BillItem("BILL SUMMARY",                                 "AMOUNT", ItemStyle.SummaryHeader),
                    new BillItem(											ItemStyle.Separator),
                    new BillItem("Current Amount Due",                    String.valueOf(LiquidBilling.total_current_bill),  ItemStyle.Footer),
                    new BillItem("Overdue Amount",             	        String.valueOf(LiquidBilling.arrears), ItemStyle.Footer),
                    new BillItem("Surcharge",             	            String.valueOf(LiquidBilling.surcharge), ItemStyle.Footer),
//                    new BillItem(											ItemStyle.Separator),
//                    new BillItem("" +
//                                  "MORE POWER collecting bank nearest you," +
//                                  "on or before due date to avoid discon",           "0", ItemStyle.Notice3),
                    new BillItem(											ItemStyle.Separator),
                    new BillItem("TOTAL AMOUNT DUE",                        String.valueOf(LiquidBilling.total_amount_due), ItemStyle.Total),
                    new BillItem("Current Bill Due Date",               Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","dd-MMM-yyyy"), ItemStyle.Aftertotal),
                    new BillItem(											ItemStyle.Separator),

            };
    public  BillItem[] BillItemsMorePower =
            {
                    new BillItem("RATE COMPONENT", 		                 "PRICE" , "AMOUNT", ItemStyle.Header),
                    new BillItem("Distribution Charge",                    String.valueOf(LiquidBilling.distrib_net_charge_1), String.valueOf(LiquidBilling.total_distrib_net_charge_1), ItemStyle.Detail),
                    new BillItem("Demand Charge",                          String.valueOf(LiquidBilling.dis_demand_charge), String.valueOf(LiquidBilling.total_dis_demand_charge),       ItemStyle.Detail),
                    new BillItem("Supply Charge",                          String.valueOf(LiquidBilling.sup_sys_chg), String.valueOf(LiquidBilling.total_sup_sys_chg),       ItemStyle.Detail),
                    new BillItem("Metering Charge",                        String.valueOf(LiquidBilling.met_charge_1), String.valueOf(LiquidBilling.total_met_charge_1),       ItemStyle.Detail),
                    new BillItem("Retail Customer Charge",                 String.valueOf(LiquidBilling.ret_cust_chg_1), String.valueOf(LiquidBilling.total_ret_cust_chg_1),       ItemStyle.Detail),
                    new BillItem("MORE Related Charges",                    String.valueOf(LiquidBilling.total_distribution_revenue),              ItemStyle.Subtotal),
                    new BillItem(									                                    		    ItemStyle.Separator),
                    new BillItem("Generation Charge",                      String.valueOf(LiquidBilling.gen_sys_charge), String.valueOf(LiquidBilling.total_gen_sys_charge),        ItemStyle.Detail),
                     new BillItem("Transmission Charge",                   String.valueOf(LiquidBilling.trans_sys_chg), String.valueOf(LiquidBilling.total_trans_sys_chg),        ItemStyle.Detail),
                    new BillItem("System Loss Charge",                     String.valueOf(LiquidBilling.sys_los_chg), String.valueOf(LiquidBilling.total_sys_los_chg),        ItemStyle.Detail),
                    new BillItem("Supplier Related Charges",               String.valueOf(LiquidBilling.total_gen_trans),       ItemStyle.Subtotal),
                    new BillItem(											                                                ItemStyle.Separator),
                    new BillItem("Lifeline Rate Subsidy",                  String.valueOf(LiquidBilling.lifeline), String.valueOf(LiquidBilling.total_lifeline),       ItemStyle.Detail),
                    new BillItem("Senior Citizen Discount",                String.valueOf(LiquidBilling.senior), String.valueOf(LiquidBilling.total_senior),      ItemStyle.Detail),
                    new BillItem("Subsidies",                              String.valueOf(LiquidBilling.total_others),        ItemStyle.Subtotal),
                    new BillItem(										                        	ItemStyle.Separator),
                    new BillItem("VAT on Generation",                     String.valueOf(LiquidBilling.gen_vat), String.valueOf(LiquidBilling.total_gen_vat),         ItemStyle.Detail),
                    new BillItem("VAT on Transmission",                   String.valueOf(LiquidBilling.trans_vat), String.valueOf(LiquidBilling.total_trans_vat),      ItemStyle.Detail),
                    new BillItem("VAT on Other Charges",                  String.valueOf(LiquidBilling.others_vat), String.valueOf(LiquidBilling.total_others_vat),         ItemStyle.Detail),
                    new BillItem("Franchise Tax",                       String.valueOf(LiquidBilling.fran_tax_rec), String.valueOf(LiquidBilling.total_fran_tax_rec),       ItemStyle.Detail),
                    new BillItem("Missionary Electrification",          String.valueOf(LiquidBilling.missionary_elec), String.valueOf(LiquidBilling.total_missionary_elec),        ItemStyle.Detail),
                    new BillItem("Environmental Charges",                String.valueOf(LiquidBilling.environmental_charge), String.valueOf(LiquidBilling.total_environmental_chg),        ItemStyle.Detail),
                    new BillItem("NPC Stranded Contract Cost",           String.valueOf(LiquidBilling.npc_stran_cos), String.valueOf(LiquidBilling.total_npc_stran_cos),      ItemStyle.Detail),
                    new BillItem("FIT - Allowance",                       String.valueOf(LiquidBilling.fit_all), String.valueOf(LiquidBilling.total_fit_all),       ItemStyle.Detail),
                    new BillItem("Taxes & Universal Charges",            String.valueOf(LiquidBilling.total_government_revenue),       ItemStyle.Subtotal),
                    new BillItem(											    ItemStyle.Separator),
                    new BillItem("Current Amount Due",                     String.valueOf(LiquidBilling.total_current_bill),    ItemStyle.Footer),
                    new BillItem("Overdue Amount",             	        String.valueOf(LiquidBilling.arrears), ItemStyle.Footer),
                    new BillItem("Surcharge",             	            String.valueOf(LiquidBilling.surcharge), ItemStyle.Footer),
                    new BillItem(											    ItemStyle.Separator),
                    new BillItem( "Pay at MORE office,BDO,PNB,Robinsons Bank,;" +
                                        "Landbank,LBC,Palawan Express & RD pawnshop;" +
                                        "to avoid disconnection. 2% surcharge will be billed;" +
                                        "for unpaid bill for every month or a fraction thereof.;" ,         "1", ItemStyle.Notice3),
                    new BillItem(											ItemStyle.Separator),
                    new BillItem("TOTAL AMOUNT DUE",                         String.valueOf(LiquidBilling.total_amount_due), ItemStyle.Total),
                    new BillItem("Current Bill Due Date",                              Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","dd-MMM-yyyy")  , ItemStyle.Aftertotal),
                    new BillItem(											ItemStyle.Separator),



            };

    public  BillItem[] BillItemsPelco2 =
            {
                    new BillItem("CHARGES",                      "RATE" , "AMOUNT", ItemStyle.Header),
                    new BillItem("Generation System Charge",             String.valueOf(LiquidBilling.gen_sys_charge), String.valueOf(LiquidBilling.total_gen_sys_charge),       ItemStyle.Detail),
                    new BillItem("Franchise and BHC Charge",             String.valueOf(LiquidBilling.fran_bhc_charge), String.valueOf(LiquidBilling.total_fran_bhc_charge),       ItemStyle.Detail),
                    new BillItem("Automatic Cost Adj.",                  String.valueOf(LiquidBilling.auto_cost_adj), String.valueOf(LiquidBilling.total_auto_cost_adj),       ItemStyle.Detail),
                    new BillItem("Other Generation Adj.",                String.valueOf(LiquidBilling.other_gen_adj), String.valueOf(LiquidBilling.total_other_gen_adj),       ItemStyle.Detail),
                    new BillItem("Generation Charges",                   String.valueOf(LiquidBilling.total_generation_charge),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("Transmission System Charge",           String.valueOf(LiquidBilling.trans_del_charge_1), String.valueOf(LiquidBilling.total_trans_del_charge_1),       ItemStyle.Detail),
                    new BillItem("Other Trans. Cost Adj.    ",           String.valueOf(LiquidBilling.other_trans_cost_adj), String.valueOf(LiquidBilling.total_other_trans_cost_adj),       ItemStyle.Detail),
                    new BillItem("Transmission Charges",                 String.valueOf(LiquidBilling.total_transmission_charge),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("Systems Loss Charge",                  String.valueOf(LiquidBilling.sys_los_chg),  String.valueOf(LiquidBilling.total_sys_los_chg),       ItemStyle.Detail),
                    new BillItem("Other System Loss Adj.",               String.valueOf(LiquidBilling.other_sys_loss_adj),  String.valueOf(LiquidBilling.total_other_sys_loss_adj),      ItemStyle.Detail),
                    new BillItem("System Loss Charges",                  String.valueOf(LiquidBilling.total_systems_loss),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("Distribution System Charge",           String.valueOf(LiquidBilling.distrib_net_charge_1), String.valueOf(LiquidBilling.total_distrib_net_charge_1),       ItemStyle.Detail),
                    new BillItem("Distribution Charges",                 String.valueOf(LiquidBilling.total_distrib_net_charge_1),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("Supply System Charge",                 String.valueOf(LiquidBilling.sup_sys_chg), String.valueOf(LiquidBilling.total_sup_sys_chg),       ItemStyle.Detail),
                    new BillItem("Supply Charges",                       String.valueOf(LiquidBilling.total_sup_sys_chg),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("Metering System Charge",               String.valueOf(LiquidBilling.met_sys_chg), String.valueOf(LiquidBilling.total_met_sys_chg),       ItemStyle.Detail),
                    new BillItem("Retail Customer Charge",               String.valueOf(LiquidBilling.ret_elec_serv_charge_1), String.valueOf(LiquidBilling.total_ret_elec_serv_charge_1),       ItemStyle.Detail),
                    new BillItem("Metering Charges",                     String.valueOf(LiquidBilling.total_met_charge_1),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("PPA Refund",                           String.valueOf(LiquidBilling.ppa_refund), String.valueOf(LiquidBilling.total_ppa_refund),       ItemStyle.Detail),
                    new BillItem("RFSC",                                 String.valueOf(LiquidBilling.rfsc), String.valueOf(LiquidBilling.total_rfsc),       ItemStyle.Detail),
                    new BillItem("Other Charges",                        String.valueOf(LiquidBilling.total_other_charges),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("Lifeline Rate Subsidy",                String.valueOf(LiquidBilling.lifeline),  String.valueOf(LiquidBilling.total_lifeline),       ItemStyle.Detail),
                    new BillItem("Senior Citizen Subsidy",               String.valueOf(LiquidBilling.senior), String.valueOf(LiquidBilling.total_senior),       ItemStyle.Detail),
                    new BillItem("Other Lifeline Rate Adj.",             String.valueOf(LiquidBilling.other_life_rate_adj),  String.valueOf(LiquidBilling.total_other_life_rate_adj),      ItemStyle.Detail),
                    new BillItem("Subsidies/Discount Charges",           String.valueOf(LiquidBilling.total_sub_dis_charge),                          ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("VAT Generation",                       String.valueOf(LiquidBilling.gen_vat), String.valueOf(LiquidBilling.total_generation),       ItemStyle.Detail),
                    new BillItem("VAT Transmission",                     String.valueOf(LiquidBilling.trans_vat),  String.valueOf(LiquidBilling.total_transmission),       ItemStyle.Detail),
                    new BillItem("VAT System Loss (Gen)",                String.valueOf(LiquidBilling.sys_loss_1),  String.valueOf(LiquidBilling.total_sys_loss_1),      ItemStyle.Detail),
                    new BillItem("VAT System Loss (Trans)",              String.valueOf(LiquidBilling.sys_loss_2),  String.valueOf(LiquidBilling.total_sys_loss_2),      ItemStyle.Detail),
                    new BillItem("VAT Distribution",                     String.valueOf(LiquidBilling.dis_vat),  String.valueOf(LiquidBilling.total_distribution),       ItemStyle.Detail),
                    new BillItem("VAT Others",                           String.valueOf(LiquidBilling.others_vat),  String.valueOf(LiquidBilling.total_others_vat),       ItemStyle.Detail),
                    new BillItem("Value Added Tax",                      String.valueOf(LiquidBilling.total_government_revenue),       ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("Missionary Elec (NPC-SPUG)",           String.valueOf(LiquidBilling.me_npc_spug),  String.valueOf(LiquidBilling.total_me_npc_spug),      ItemStyle.Detail),
                    new BillItem("Missionary Elec (RED)",                String.valueOf(LiquidBilling.me_renewable_energy_dev),  String.valueOf(LiquidBilling.total_me_renewable_energy_dev),      ItemStyle.Detail),
                    new BillItem("Environmental Charge",                 String.valueOf(LiquidBilling.environmental_charge),  String.valueOf(LiquidBilling.total_environmental_charge),       ItemStyle.Detail),
                    new BillItem("NPC Stranded Debt",                    String.valueOf(LiquidBilling.npc_stran_deb), String.valueOf(LiquidBilling.total_npc_stran_deb),       ItemStyle.Detail),
                    new BillItem("Universal Charges",                    String.valueOf(LiquidBilling.total_universal),       ItemStyle.Subtotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("FIT-All (Renewable)",                  String.valueOf(LiquidBilling.fit_all), String.valueOf(LiquidBilling.total_fit_all),       ItemStyle.Detail),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("CURRENT BILL",                         String.valueOf(LiquidBilling.total_amount_due), ItemStyle.Total),
//                    new BillItem("Due Date: "+Liquid.dateChangeFormat(Liquid.BillMonth,"MM","MMM") +" "+Liquid.BillYear ,      Liquid.dateChangeFormat(Liquid.duedate,"yyyy-MM-dd","MM/dd/yyyy"), ItemStyle.Aftertotal),
                    new BillItem("Interest After Due Date",              String.valueOf(LiquidBilling.surcharge), ItemStyle.Aftertotal),
                    new BillItem(                                                                                          ItemStyle.Separator),
                    new BillItem("AMOUNT BEFORE DUE",                    String.valueOf(LiquidBilling.total_amount_due), ItemStyle.Total),
                    new BillItem("AMOUNT AFTER DUE",                     String.valueOf(LiquidBilling.total_amount_due2), ItemStyle.Total),
                    new BillItem(                                                                                          ItemStyle.Separator),

                    new BillItem("Bill Deposit",                         String.valueOf(0), ItemStyle.FooterTitle),
                    new BillItem("Principal Amount",                     String.valueOf(LiquidBilling.total_moa), ItemStyle.Footer),
                    new BillItem("Interest",                             String.valueOf(0), ItemStyle.Footer),
                    new BillItem("Total Bill Deposit",                   String.valueOf(LiquidBilling.total_moa), ItemStyle.Footer),
                    new BillItem(                                                                                          ItemStyle.Separator),
            };


    public static int CountDetails(ItemStyle style)
    {
        Bill mBill = new Bill();
        switch(Liquid.Client){
            case "batelec2":
                return CountDetails(mBill.BillItemsBatelec2, style);
            case "iselco2":
                return CountDetails(mBill.BillItemsIselco2, style);
            case "ileco2":
                return CountDetails(mBill.BillItemsIleco2, style);
            case "more_power":
                return CountDetails(mBill.BillItemsMorePower, style);
            default:
                return CountDetails(mBill.BillItemsBatelec2, style);
        }

    }

    public static int CountDetails(BillItem[] items, ItemStyle style)
    {
        int count = 0;
        for (BillItem item : items)
        {
            if (item.Style == style)
            {
                double amount = 0;
                try{
                    amount = Double.parseDouble(item.Amount().equals("") ? "0" :  item.Amount());
                }catch(Exception e){
                    amount = 0;
                }

                // An optional line is not printed if the amount is 0.00
                if ((style == ItemStyle.OptionalDetail || style == ItemStyle.OptionalFooter ||
                        style == ItemStyle.OptionalSeparator || style == ItemStyle.Notice1 ||
                        style == ItemStyle.Notice2 || style == ItemStyle.Notice3 ||
                        style == ItemStyle.OptionalFooter2 || style == ItemStyle.OptionalFooterAdditional) &&
                        amount == 0)
                    continue;

                count++;
            }
        }
        return count;
    }
}
