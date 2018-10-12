package cit.a19.project.group.aitransport.ui.payment

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cit.a19.project.group.aitransport.R
import cit.a19.project.group.aitransport.ui.booking.PaymentFragmentModel


class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private lateinit var model: PaymentFragmentModel
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
            model = PaymentFragmentModel.newInstance(activityContext)
        }
    }

}
