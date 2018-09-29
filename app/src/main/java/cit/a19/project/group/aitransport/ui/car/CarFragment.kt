package cit.a19.project.group.aitransport.ui.car

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cit.a19.project.group.aitransport.R

class CarFragment : Fragment() {

    companion object {
        fun newInstance() = CarFragment()
    }

    private lateinit var viewModel: CarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CarViewModel::class.java)
    }

}
