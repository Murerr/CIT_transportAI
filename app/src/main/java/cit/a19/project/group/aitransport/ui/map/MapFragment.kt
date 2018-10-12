package cit.a19.project.group.aitransport.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cit.a19.project.group.aitransport.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback, com.google.android.gms.location.LocationListener {

    private lateinit var model: MapFragmentModel
    private lateinit var mMap: GoogleMap
    private lateinit var activityContext: Context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        fun newInstance() = MapFragment()
        private const val LOCATION_REQUEST_CODE = 101
        private var currentLat: Double= 0.0
        private var currentLong: Double = 0.0
        private var currentPosition:LatLng = LatLng(currentLat,currentLong)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!this::activityContext.isInitialized && isAdded) {
            activityContext = activity?.applicationContext!!
        }
        if (!this::model.isInitialized) {
            model = MapFragmentModel.newInstance(activityContext)
        }
        if (!model.isLocationPermissionGranted()){
            requestPermissionLocation()
        }
        if (!this::fusedLocationClient.isInitialized) {
            fusedLocationClient = FusedLocationProviderClient(activityContext)
        }

        getDeviceLocation()
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
    override fun onLocationChanged(location: Location?) {
        getDeviceLocation()
        location?.let {
            it.latitude = currentLat
            it.longitude = currentLong
        }
        currentPosition = LatLng(currentLat, currentLong)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,16f))

    }
    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location ->
                        currentPosition = LatLng( location.latitude, location.longitude)
                        mMap.addMarker(MarkerOptions().position(currentPosition).title("Marker in Cork"))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,16f))

                    }

        }
    }
    private fun requestPermissionLocation(){
            val permissionsList = arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionsList, LOCATION_REQUEST_CODE)
    }




}
