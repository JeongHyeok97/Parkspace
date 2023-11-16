package com.gil.parkspace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gil.parkspace.api.MapApplicationUtils
import com.gil.parkspace.databinding.FragmentInfoBinding


class InfoFragment(private val parkingLot: ParkingLot) : Fragment(){
    companion object{
        private const val TAG = "InfoFragment"
    }

    private lateinit var infoBinding: FragmentInfoBinding
    private lateinit var mRootActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootActivity = requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        infoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        infoBinding.infoNavigationButton.setOnClickListener {
            startMap(parkingLot)
        }
        return infoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoBinding.parkinglot = parkingLot
    }

    fun startMap(parkingLot: ParkingLot){
        val uriString = MapApplicationUtils.getNaverScheme(
            mRootActivity.getCurrentLocation().first,
            mRootActivity.getCurrentLocation().second,
            parkingLot)
        val uri = Uri.parse(uriString)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val pm = requireContext().packageManager
        if (pm.resolveActivity(intent, 0) != null){
            startActivity(intent)
        }
        else{
            val kakaoUri = MapApplicationUtils.getKakaoScheme(mRootActivity.getCurrentLocation().first,
                mRootActivity.getCurrentLocation().second,
                parkingLot)
            intent.data= Uri.parse(kakaoUri)
        }
    }
}