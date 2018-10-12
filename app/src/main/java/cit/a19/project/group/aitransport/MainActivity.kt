package cit.a19.project.group.aitransport

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import cit.a19.project.group.aitransport.ui.map.MapFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.toast


class
MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val RC_SIGN_IN = 123
        const val CHANNEL_ID = 456
        private lateinit var mAuth: FirebaseAuth
        val providers = listOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build(),
                AuthUI.IdpConfig.EmailBuilder().build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mAuth = FirebaseAuth.getInstance()
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        if (mAuth.currentUser != null) {
            updateUI(mAuth.currentUser)
        } else {
            silentSignIn()
        }
        if (savedInstanceState == null) {
            loadFragment(MapFragment.newInstance(), "map") // OnCreate get last Fragment selected
        }

    }



    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val name = currentUser.displayName
            val email = currentUser.email
            val photo = currentUser.photoUrl

            userIcon?.let {
                Glide.with(applicationContext)
                        .load(photo)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userIcon)
            }
            userEmail?.let { it.text = email }
            userName?.let { it.text = name }
        } else {

            userIcon?.let { Glide.with(userIcon).clear(userIcon) }
            userEmail?.setText(R.string.display_profile_email)
            userName?.setText(R.string.display_profile_name)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_login -> {
                createSignInIntent()
                R.id.nav_login
            }
            R.id.nav_logout -> {
                signOut()
            }
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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

    private fun createSignInIntent() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.common_google_signin_btn_icon_light)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
        // [END auth_fui_theme_logo]
    }

    private fun silentSignIn() {
        AuthUI.getInstance().silentSignIn(this, providers)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Signed in! Start loading data
                        val user = FirebaseAuth.getInstance().currentUser
                        updateUI(user)
                    } else {
                        createSignInIntent()
                        toast("Silent sign-in failed")
                    }

                }
    }

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    if (it.isComplete) {
                        toast("Signed out")
                        updateUI(currentUser = null)
                    }
                }
        // [END auth_fui_signout]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                updateUI(user)

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                if (response == null) {
                    // User pressed back button
                    toast(R.string.sign_in_cancelled)
                    return
                }

                if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    toast(R.string.error_no_network)

                }
                toast(R.string.unknown_error)
                Log.e("Error Sign in", "Sign-in error: ", response.error)
            }
        }
    }
}

