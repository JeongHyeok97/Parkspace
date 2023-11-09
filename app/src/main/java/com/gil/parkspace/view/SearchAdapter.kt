package com.gil.parkspace.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gil.parkspace.ParkingLot
import com.gil.parkspace.databinding.ItemSearchBindingBinding
import com.naver.maps.geometry.LatLng

class SearchAdapter(
    private var itemList: List<ParkingLot>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null
    private var currentLatLng: LatLng? = null


    inner class SearchViewHolder
        (private val binding: ItemSearchBindingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ParkingLot, space: String, distance:String) {
            binding.parkingLot = item
            itemView.setOnClickListener {
                if (itemClickListener != null){
                    itemClickListener?.onClickItem(item)
                }
            }
            binding.possibleLot = space
            binding.distance = distance
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding= ItemSearchBindingBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = itemList[position]
        val parkingSpaces = item.capacity - item.currentParking
        val string = "잔여 : ${parkingSpaces.toInt()} 대"
        val distance = currentLatLng?.distanceTo(LatLng(item.latitude, item.longitude))!!
        holder.bindItem(item, string, String.format("%.1f",distance/1000) + "km")
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun clearAndAddAll(list: List<ParkingLot>){
        itemList = list
        notifyDataSetChanged()
    }

    fun setOnClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setCurrentLocation(latLng: LatLng) {
        this.currentLatLng = latLng
    }
}