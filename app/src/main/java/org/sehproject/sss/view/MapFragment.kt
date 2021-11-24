package org.sehproject.sss.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessaging
import com.nhn.android.naverlogin.OAuthLoginDefine.LOG_TAG
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import net.daum.mf.map.api.MapLayout
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentMapBinding
import org.sehproject.sss.databinding.ItemEtaBinding
import org.sehproject.sss.databinding.ItemGroupBinding
import org.sehproject.sss.datatype.Coordinate
import org.sehproject.sss.datatype.Eta
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.viewmodel.GroupViewModel
import org.sehproject.sss.viewmodel.MapViewModel

class MapFragment : Fragment(),MapView.OpenAPIKeyAuthenticationResultListener,
    MapView.MapViewEventListener,
    MapView.CurrentLocationEventListener {
    //private val safeArgs: MapFragmentArgs by navArgs() //pid
    //private val pid = safeArgs.pid
    private lateinit var job: Job
    private val mapViewModel: MapViewModel by lazy {
        ViewModelProvider(this).get(MapViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //주의!! 지도는 가상에뮬레이터로 실행 불가능.
        //실제 폰만 가능!!
        val mapBinding: FragmentMapBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_map,
            container,
            false
        )
        val mapLayout = MapLayout(requireActivity())
        val mapView = mapLayout.mapView

        mapView.setDaumMapApiKey("test2")
        mapView.setOpenAPIKeyAuthenticationResultListener(this)
        mapView.mapType = MapView.MapType.Standard
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading;
        mapView.setCurrentLocationEventListener(this)

        with(mapView) {
         if(this.parent != null) {
             (this.parent as ViewGroup).removeView(this)
         }
        }
        mapBinding.mapView.addView(mapView)

        initObserver(mapBinding)

        job = repeatRequest()

        return mapBinding.root
    }

    private fun initObserver(mapBinding: FragmentMapBinding) {
        mapViewModel.etaListLiveData.observe(viewLifecycleOwner, {
            mapBinding.recyclerMapEta.adapter = EtaAdapter(it)
        })
    }


    override fun onDaumMapOpenAPIKeyAuthenticationResult(p0: MapView?, p1: Int, p2: String?) {
        Log.d(
            "TAG",
            String.format(
                "Open API Key Authentication Result : code=%d, message=%s",
                p1,
                p2
            )
        )
    }

    override fun onCurrentLocationUpdate(p0: MapView?, currentLocation: MapPoint?, accuracy: Float) {
        mapViewModel.mapLogic.onCurrentLocationUpdate(Coordinate(
            currentLocation!!.mapPointGeoCoord.latitude,
            currentLocation.mapPointGeoCoord.longitude
        ))
    }

    private fun repeatRequest(): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            while(isActive) {
                //do your request
                Log.d("TAG", "repeat!")
                delay(5000)
            }
        }

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
        TODO("Not yet implemented")
    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
        TODO("Not yet implemented")
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewInitialized(p0: MapView?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        TODO("Not yet implemented")
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        TODO("Not yet implemented")
    }

    protected inner class EtaHolder(private val itemEtaBinding: ItemEtaBinding):
        RecyclerView.ViewHolder(itemEtaBinding.root) {

        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(eta: Eta) {
            itemEtaBinding.eta = eta
        }
    }

    protected inner class EtaAdapter(val etas: List<Eta>) :
        RecyclerView.Adapter<EtaHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtaHolder {
            val itemEtaBinding = ItemEtaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return EtaHolder(itemEtaBinding)
        }

        override fun getItemCount(): Int = etas.size

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onBindViewHolder(holder: EtaHolder, position: Int) {
            val eta = etas[position]
            holder.bind(eta)
        }

    }

}