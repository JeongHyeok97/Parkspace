package com.gil.parkspace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gil.parkspace.databinding.FragmentInfoBinding

class InfoFragment(private val parkingLot: ParkingLot) : Fragment(){

    private lateinit var infoBinding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        infoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        infoBinding.infoNavigationButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${parkingLot.latitude}" +
                    ",${parkingLot.longitude}?q=${parkingLot.parkingName}")).apply {
                `package` = "com.google.android.apps.maps"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
        return infoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoBinding.parkinglot = parkingLot
    }
}