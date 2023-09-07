package com.example.greenatomvessels

class ListDataModel(vessel: String, port: String, arrivalDate: String, arrivalLeft: String, departureDate: String, departureLeft: String, favorite: Boolean = false) {
    var mVessel = vessel
    var mPort = port
    var mArrivalDate = arrivalDate
    var mArrivalLeft = arrivalLeft
    var mDepartureDate = departureDate
    var mDepartureLeft = departureLeft
    var mFavorite = favorite
}