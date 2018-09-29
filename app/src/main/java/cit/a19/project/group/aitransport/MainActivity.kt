
package cit.a19.project.group.aitransport

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import cit.a19.project.group.aitransport.ui.car.CarFragment
import cit.a19.project.group.aitransport.ui.map.MapFragment
import cit.a19.project.group.aitransport.ui.home.HomeFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mMap: GoogleMap

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener (loadFragment(HomeFragment.newInstance(), "home"))
            }
            R.id.navigation_car -> {
                return@OnNavigationItemSelectedListener (loadFragment(CarFragment.newInstance(), "car"))
            }
            R.id.navigation_map -> {
                return@OnNavigationItemSelectedListener (loadFragment(MapFragment.newInstance(), "car"))
            }
        }
        false
    }
    private val mOnNavigationItemReSelectedListener = BottomNavigationView.OnNavigationItemReselectedListener {
        // when reselected do nothing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            loadFragment(HomeFragment(), "home") // OnCreate get last Fragment selected
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReSelectedListener)
    }

    private fun loadFragment(fragment: Fragment?, tag: String): Boolean {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, tag)
                    .commit()
            return true
        }
        return false
    }

}
