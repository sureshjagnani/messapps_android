package com.messapps.utils

import com.messapps.model.NavigationData
import com.messapps.model.estimates.EstimateData
import com.messapps.model.invoices.InvoicesData
import com.messapps.model.progress.ProgressData


const val GLOBAL_TAG = "MessappsTag"
const val APP_SETTINGS_PREF = "SettingsPrefs"
const val PREF_NAME_THEME_MODE = "pref_theme_mode"
var Last_Selection = 0
var CALL_FROM = 0
var Call_Profile = 3
var Call_Contact = 4
const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"




fun getData(): HashMap<String, List<NavigationData>> {
    val expandableListDetail = HashMap<String, List<NavigationData>>()

    val cp: MutableList<NavigationData> = ArrayList()
    cp.add(NavigationData("Home", 1))
    cp.add(NavigationData("Estimates", 2))
    cp.add(NavigationData("Progress", 3))
    cp.add(NavigationData("View Builds", 4))
    cp.add(NavigationData("Invoices", 5))
    val ap: MutableList<NavigationData> = ArrayList()

    val ma: MutableList<NavigationData> = ArrayList()
    ma.add(NavigationData("About us", 6))
    ma.add(NavigationData("Blog", 7))
    ma.add(NavigationData("Press", 8))
    ma.add(NavigationData("Awards", 9))
    ma.add(NavigationData("Social", 10))
    ma.add(NavigationData("Website", 11))
    ma.add(NavigationData("Reviews", 12))

    val profile: MutableList<NavigationData> = ArrayList()
    val contact: MutableList<NavigationData> = ArrayList()

    expandableListDetail["Client Portal"] = cp
    expandableListDetail["Affiliate Portal"] = ap
    expandableListDetail["MessApps"] = ma
    expandableListDetail["Profile"] = profile
    expandableListDetail["Contact"] = contact
    return expandableListDetail
}

fun getTitle(): MutableList<NavigationData> {
    //val expandableListDetail: MutableList<NavigationData> = mutableListOf()

    val cp: MutableList<NavigationData> = mutableListOf()
    cp.add(NavigationData("Client Portal", 1))
    cp.add(NavigationData("Affiliate Portal", 2))
    cp.add(NavigationData("MessApps", 3))
    cp.add(NavigationData("Profile", 4))
    cp.add(NavigationData("Contact", 5))

    return cp
}

fun getEstimate(): MutableList<EstimateData> {
    //val expandableListDetail: MutableList<NavigationData> = mutableListOf()

    val cp: MutableList<EstimateData> = mutableListOf()
    cp.add(EstimateData("Project A1", "90", "$10,000"))
    cp.add(EstimateData("Project A2", "120", "$13,500"))
    cp.add(EstimateData("Project B", "100", "$8,000"))

    return cp
}

fun getProgress(): MutableList<ProgressData> {

    val cp: MutableList<ProgressData> = mutableListOf()
    cp.add(ProgressData("Project A1", "90", "$10,000"))
    cp.add(ProgressData("Project A2", "120", "$13,500"))
    cp.add(ProgressData("Project B", "100", "$8,000"))

    return cp
}

fun getInvoices(): MutableList<InvoicesData> {

    val cp: MutableList<InvoicesData> = mutableListOf()
    cp.add(InvoicesData("June 2024", false, false))
    cp.add(InvoicesData("July 2024", false, false))
    cp.add(InvoicesData("Aug 2024", false, false))

    return cp
}