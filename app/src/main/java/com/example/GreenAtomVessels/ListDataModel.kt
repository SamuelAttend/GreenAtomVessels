package com.example.GreenAtomVessels

import java.util.Date

class ListDataModel(vessel: String, port: String, arrivalDate: Date, arrivalLeft: Int, departureDate: Date, departureLeft: Int) {
    var mVessel = vessel;
    var mPort = port;
    var mArrivalDate = arrivalDate;
    var mArrivalLeft = arrivalLeft;
    var mDepartureDate = departureDate;
    var mDepartureLeft = departureLeft;
}