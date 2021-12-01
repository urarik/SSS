package org.sehproject.sss.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentRecommendBinding
import org.sehproject.sss.databinding.ItemPlaceBinding
import org.sehproject.sss.viewmodel.RecommendViewModel

class RecommendFragment : Fragment() {
    private val recommendViewModel: RecommendViewModel by lazy {
        ViewModelProvider(this).get(RecommendViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recommendBinding: FragmentRecommendBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_recommend, container, false)

        initObserver(recommendBinding)

        recommendViewModel.setPlaces()

        return recommendBinding.root
    }

    private fun initObserver(recommendBinding: FragmentRecommendBinding) {
        recommendViewModel.popularPlaceLiveData.observe(viewLifecycleOwner, {
            recommendBinding.recyclerRecommend.adapter = PlaceAdapter(it)
        })
    }



    private inner class PlaceHolder(private val itemPlaceBinding: ItemPlaceBinding) : RecyclerView.ViewHolder(itemPlaceBinding.root) {
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(place: String) {
            //itemPlaceBinding.imageRecommendPlace.setImageBitmap(place.image)
            itemPlaceBinding.textLocationName.text = place
        }
    }
    private inner class PlaceAdapter(val places: List<String>) :
        RecyclerView.Adapter<PlaceHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
            val itemPlaceBinding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PlaceHolder(itemPlaceBinding)
        }

        override fun getItemCount(): Int = places.size

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
            val place = places[position]
            holder.bind(place)
        }

    }
}