package cit.a19.project.group.aitransport.ui.booking

import android.content.Context
import cit.a19.project.group.aitransport.PermissionManager
import cit.a19.project.group.aitransport.PermissionManagerImpl

interface PaymentFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                BookingFragmentModelImpl = BookingFragmentModelImpl(
                // add param here
                activityContext,
                PermissionManagerImpl(activityContext)
        )
    }
    // add functions here
}
// add data class here

class BookingFragmentModelImpl(
        // add Managers here
        private val activityContext: Context,
        private val permissionManager: PermissionManager
) : PaymentFragmentModel, BookingFragmentModel {

}
