package com.gil.parkspace

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.gil.parkspace.api.GeoUtils
import com.gil.parkspace.view.MainInputView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource


class MainActivity : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

        private const val TAG = "MainActivity"
        private val PERMISSIONS = arrayOf(
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mapFragment: MapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getMapAsync(this)
        val mainInputView = findViewById<MainInputView>(R.id.main_input_view)
        mainInputView.setOnClickListener {
            if (supportFragmentManager.findFragmentById(R.id.search_container) == null){
                supportFragmentManager.popBackStack("lot", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom,R.anim.exit_to_bottom)
                    .replace(R.id.search_container, SearchFragment())
                    .addToBackStack(null).commit()
            }


        }


        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {

                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            else {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(map : NaverMap) {
        this.naverMap = map
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.locationSource = locationSource


        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
    }

    fun moveCamera(lot: ParkingLot){
        val latLng = LatLng(lot.latitude, lot.longitude)
        supportFragmentManager.popBackStack()
        naverMap.moveCamera(CameraUpdate.scrollAndZoomTo(latLng, 16.0))
        val marker = Marker(latLng)
        marker.map = naverMap
        val overlayImage = OverlayImage.fromResource(R.drawable.ic_parking_lot)
        marker.icon = overlayImage
//        marker.iconTintColor = ContextCompat.getColor(this, R.color.teal_700)
        marker.captionText = lot.parkingName
        marker.subCaptionText = lot.address
        marker.setOnClickListener {
            openInformation(lot)
            false
        }
        openInformation(lot)
    }
    fun getCameraLocation(): LatLng {
        return naverMap.cameraPosition.target
    }
    fun getCurrentLocation():Pair<String, LatLng>{
        val locationSource= naverMap.locationSource as? FusedLocationSource
        val latitude = locationSource?.lastLocation?.latitude!!
        val longitude = locationSource.lastLocation?.longitude!!
        val address= GeoUtils.getAddress(this, latitude, longitude)
        return Pair(address, LatLng(latitude, longitude))
    }


    private fun openInformation(lot: ParkingLot){
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom,R.anim.exit_to_bottom)
            .replace(R.id.info_container, InfoFragment(lot))
            .addToBackStack("lot")
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


    }
}