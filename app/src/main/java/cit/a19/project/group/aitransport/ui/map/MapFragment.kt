package cit.a19.project.group.aitransport.ui.map

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cit.a19.project.group.aitransport.PermissionManagerImpl
import cit.a19.project.group.aitransport.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback, com.google.android.gms.location.LocationListener {

    private lateinit var viewModel: MapViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var activityContext: Context
    private lateinit var permissionManager: PermissionManagerImpl
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingClient: SettingsClient

    companion object {
        fun newInstance() = MapFragment()
        private const val LOCATION_REQUEST_CODE = 101
        private var CurrentLat: Double= 0.0
        private var CurrentLong: Double = 0.0
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
        if (!this::permissionManager.isInitialized) {
            permissionManager = PermissionManagerImpl(activityContext)
        }
        if (!permissionManager.isLocationPermissionGranted()) {
            val permissionsList = arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionsList, LOCATION_REQUEST_CODE)
        }
        if (!this::fusedLocationClient.isInitialized) {
            fusedLocationClient = FusedLocationProviderClient(activityContext)
        }
        if (!this::settingClient.isInitialized) {
            settingClient = SettingsClient(activityContext)
        }
        getDeviceLocation()
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
    }
    override fun onLocationChanged(location: Location?) {
        location?.let {
            it.latitude = CurrentLat
            it.longitude = CurrentLong
        }
        val latLng = LatLng(CurrentLat, CurrentLong)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20f))

    }


    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location ->
                        CurrentLat = location.latitude
                        CurrentLong = location.longitude
                        // Add a marker in Sydney and move the camera
                        val latLng = LatLng(CurrentLat, CurrentLong)
                        mMap.addMarker(MarkerOptions().position(latLng).title("Marker in Cork"))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16f))
                    }

        }
    }

    /*private fun getDeviceLocation() {
        /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient.getLastLocation()
                locationResult.addOnCompleteListener(this, object : OnCompleteListener() {
                    fun onComplete(task: Task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult()
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM))
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.")
                            Log.e(TAG, "Exception: %s", task.getException())
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM))
                            mMap.uiSettings.isMyLocationButtonEnabled = false
                        }
                    }
                })
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }

    }*/


}
