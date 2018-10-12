package cit.a19.project.group.aitransport.ui.edit

import android.content.Context
import cit.a19.project.group.aitransport.PermissionManager
import cit.a19.project.group.aitransport.PermissionManagerImpl

interface EditFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                EditFragmentModelImpl = EditFragmentModelImpl(
                // add param here
                activityContext,
                PermissionManagerImpl(activityContext)
        )
    }
    // add functions here
}
// add data class here

class EditFragmentModelImpl(
        // add Managers here
        private val activityContext: Context,
        private val permissionManager: PermissionManager
) : EditFragmentModel {

}
