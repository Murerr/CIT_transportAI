package cit.a19.project.group.aitransport.ui.booking

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cit.a19.project.group.aitransport.R


class BookingFragment : Fragment() {

    companion object {
        fun newInstance() = BookingFragment()
    }

    private lateinit var model: BookingFragmentModel
    private lateinit var activityContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!this::activityContext.isInitialized && isAdded) {
            activityContext = activity?.applicationContext!!
        }
        if (!this::model.isInitialized) {
            model = BookingFragmentModel.newInstance(activityContext)
        }
    }

}
