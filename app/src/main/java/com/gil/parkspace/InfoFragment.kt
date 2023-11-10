package com.gil.parkspace

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gil.parkspace.databinding.FragmentInfoBinding
import com.google.android.gms.common.wrappers.Wrappers.packageManager


class InfoFragment(private val parkingLot: ParkingLot) : Fragment(){

    private lateinit var infoBinding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        infoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        infoBinding.infoNavigationButton.setOnClickListener {
            startGoogleMap(parkingLot)
        }
        return infoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoBinding.parkinglot = parkingLot
    }

    fun startGoogleMap(parkingLot: ParkingLot){

        val title = parkingLot.parkingName
        val latitude = parkingLot.latitude
        val longitude = parkingLot.longitude
        val uri = Uri.parse("geo:$latitude" +
                ",$longitude?q=$title")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val pm = requireContext().packageManager
        val activities: List<ResolveInfo> = pm.queryIntentActivities(intent, 0)
        val isIntentSafe = activities.isNotEmpty()
        Log.d("Info ", "$activities")
        if (isIntentSafe){
            startActivity(intent)
        }
    }
}