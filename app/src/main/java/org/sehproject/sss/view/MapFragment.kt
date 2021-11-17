package org.sehproject.sss.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nhn.android.naverlogin.OAuthLoginDefine.LOG_TAG
import net.daum.mf.map.api.MapLayout
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentMapBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment(),MapView.OpenAPIKeyAuthenticationResultListener,
    MapView.MapViewEventListener,
    MapView.CurrentLocationEventListener {
    // TODO: Rename and change types of parameters

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

        mapView.setDaumMapApiKey("3549708e5615a6373ce58622aae7434d")
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


        return mapBinding.root
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

    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
        Log.d("TAG", "!@#!")
        Log.d("TAG", p0.toString())
        Log.d("TAG", p1.toString())
        Log.d("TAG", p2.toString())
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

}