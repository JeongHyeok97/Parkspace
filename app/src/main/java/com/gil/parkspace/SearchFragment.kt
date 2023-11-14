package com.gil.parkspace

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gil.parkspace.api.SeoulAPI
import com.gil.parkspace.databinding.FragmentSearchBinding
import com.gil.parkspace.view.OnItemClickListener
import com.gil.parkspace.view.SearchAdapter
import com.gil.parkspace.viewmodel.DataViewModel
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.*

class SearchFragment : Fragment(){

    private val mJob = SupervisorJob()
    private val mBackgroundScope = CoroutineScope(Dispatchers.IO + mJob)
    private lateinit var mBinding: FragmentSearchBinding
    private val mSearchAdapter = SearchAdapter(mutableListOf())
    private lateinit var mCameraPosition: LatLng
    companion object{
        const val TAG = "Search"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootActivity = activity as MainActivity
        mCameraPosition = rootActivity.getCameraLocation()
        mSearchAdapter.setCurrentLocation(mCameraPosition)
        mSearchAdapter.setOnClickListener(object : OnItemClickListener {
            override fun onClickItem(item: Any) {
                if (item is ParkingLot){
                    rootActivity.moveCamera(item)
                }
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_search, container, false)
        mBinding.searchItemListview.layoutManager = LinearLayoutManager(requireContext())
        mBinding.searchItemListview.adapter = mSearchAdapter
        return mBinding.root
    }


    val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[DataViewModel::class.java]
        viewModel.dataList.observe(viewLifecycleOwner){
            mSearchAdapter.clearAndAddAll(it)
            mBinding.searchItemListview.visibility = VISIBLE
        }
        val searchBar = mBinding.locationSearchBar
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                mBinding.searchItemListview.visibility = INVISIBLE
                handler.postDelayed({
                    mBackgroundScope.launch {
                        var inputText = newText
                        if (inputText?.isBlank() == true){
                            inputText+="*"
                        }
                        val data = SeoulAPI.getSeoulData(
                            SeoulAPI.SEOUL_URL,
                            SeoulAPI.SEOUL_KEY,
                            1, 1000, inputText)
                        withContext(Dispatchers.Main){
                            viewModel.setData(data)
                        }

                    }
                }, 800)

                return false
            }
        })
    }


}