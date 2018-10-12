package cit.a19.project.group.aitransport.ui.booking

import android.arch.lifecycle.ViewModelProviders
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

    private lateinit var viewModel: BookingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
