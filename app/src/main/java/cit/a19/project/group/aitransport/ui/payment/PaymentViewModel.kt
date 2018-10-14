package cit.a19.project.group.aitransport.ui.payment

import android.content.Context
import cit.a19.project.group.aitransport.PermissionManager
import cit.a19.project.group.aitransport.PermissionManagerImpl

interface PaymentFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                PaymentFragmentModelImpl = PaymentFragmentModelImpl(
                // add param here
                activityContext,
                PermissionManagerImpl(activityContext)
        )
    }
    // add functions here
}
// add data class here

class PaymentFragmentModelImpl(
        // add Managers here
        private val activityContext: Context,
        private val permissionManager: PermissionManager
) : PaymentFragmentModel {

}
