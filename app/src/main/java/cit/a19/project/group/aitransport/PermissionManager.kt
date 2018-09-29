package cit.a19.project.group.aitransport


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build


interface PermissionManager {
    fun isLocationPermissionGranted(): Boolean
}

class PermissionManagerImpl(private val context: Context) : PermissionManager {
    override fun isLocationPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

}