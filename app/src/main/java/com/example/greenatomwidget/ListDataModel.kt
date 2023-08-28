package com.example.greenatomwidget

import java.util.Date

class ListDataModel(ship: String, port: String, arrivalDate: Date, arrivalLeft: Int, departureDate: Date, departureLeft: Int) {
    var mShip = ship;
    var mPort = port;
    var mArrivalDate = arrivalDate;
    var mArrivalLeft = arrivalLeft;
    var mDepartureDate = departureDate;
    var mDepartureLeft = departureLeft;
}