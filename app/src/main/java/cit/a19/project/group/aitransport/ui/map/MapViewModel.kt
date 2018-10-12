package cit.a19.project.group.aitransport.ui.map

import android.content.Context
import cit.a19.project.group.aitransport.PermissionManager
import cit.a19.project.group.aitransport.PermissionManagerImpl


interface MapFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                MapFragmentModelImpl = MapFragmentModelImpl(
                // add param here
                activityContext,
                PermissionManagerImpl(activityContext)
        )
    }
    // add functions here

    fun isLocationPermissionGranted(): Boolean

}
// add data class here

class MapFragmentModelImpl(
        // add Managers here
        private val activityContext: Context,
        private val permissionManager: PermissionManager
) : MapFragmentModel {
    override fun isLocationPermissionGranted(): Boolean {
        return permissionManager.isLocationPermissionGranted()
    }


}
