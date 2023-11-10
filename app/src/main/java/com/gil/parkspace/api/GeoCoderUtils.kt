package com.gil.parkspace.api

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

object GeoCoderUtils {
    fun getAddress(context: Context, lat: Double, lng: Double): String {
        val geoCoder = Geocoder(context, Locale.KOREA)
        val address: ArrayList<Address>
        var addressResult = "주소를 가져 올 수 없습니다."
        try {
            address = geoCoder.getFromLocation(lat, lng, 1) as ArrayList<Address>
            if (address.size > 0) {
                // 주소 받아오기
                val currentLocationAddress = address[0].getAddressLine(0)
                    .toString()
                addressResult = currentLocationAddress
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressResult
    }

}