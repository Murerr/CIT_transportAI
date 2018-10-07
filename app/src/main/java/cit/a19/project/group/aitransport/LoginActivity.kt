package cit.a19.project.group.aitransport

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cit.a19.project.group.aitransport.LoginActivity.Companion.mAuth
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    companion object {
        const val RC_SIGN_IN = 123
        const val RC_SIGN_UP = 124
        private lateinit var mAuth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        email_sign_in_button.setOnClickListener {
            val email = "rudy.murer@mycit.ie"
            val password = "7417RudyM"
            signInWithEmailAndPassword(email,password)
        }
        email_sign_up_button.setOnClickListener {
            val providers = listOf(AuthUI.IdpConfig.EmailBuilder().build())
            createSignInIntent(providers, RC_SIGN_UP)
        }
        google_sign_in_button.setOnClickListener {
            val providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build())
            createSignInIntent(providers, RC_SIGN_UP)
        }
        facebook_login_button.setOnClickListener {
            val providers = listOf(AuthUI.IdpConfig.FacebookBuilder().build())
            createSignInIntent(providers, RC_SIGN_UP)
        }

    }

    private fun createSignInIntent(providers: List<AuthUI.IdpConfig>, resultCode: Int) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                resultCode)

    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login Success", "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        toast("user registered")
                        val providers = listOf(AuthUI.IdpConfig.EmailBuilder().build())
                        createSignInIntent(providers, RC_SIGN_IN)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login Error", "createUserWithEmail:failure", it.exception)
                        toast("SIgn up failed")
                    }
                }

    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login Success", "createUserWithEmail:success")
                        redirectToMainActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login Error", "createUserWithEmail:failure", it.exception)
                        toast("Authentication failed")
                    }
                }
    }


    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    // java -> to kotlin lambda
                    // ...
                    //toast(R.string.logoff)
                }
        // [END auth_fui_signout]
    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener {
                    // java -> to kotlin lambda
                    // ...
                }
        // [END auth_fui_delete]
    }

    private fun redirectToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*private fun onLogin(response: LoginActivityModelResponse) {
        when (response.status) {
            OK -> {savePreferences()
                redirecToMainctivity()}
            AUTH_FAILED-> {showProgress(false)
                showErrorMesage(response.errorMessage)}
            NO_NETWORK ->{showProgress(false)
                showErrorMesage(response.errorMessage)}
            FAILED -> {showProgress(false)
                showErrorMesage(response.errorMessage)}
        }
    }*/
    enum class LoginActivityModelStatus {
        OK,
        NO_NETWORK,
        FAILED,
        AUTH_FAILED,
    }

    data class LoginActivityModelResponse(val status: LoginActivityModelStatus, val errorMessage: String)


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = mAuth.currentUser
                signInWithEmailAndPassword(user!!.email!!,"7417RudyM")
                toast("signed in !!!")
            } else {
                toast("Error while sign in ")
                response?.error
                Log.d("Error-signin", response?.error.toString())
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
        if (requestCode == RC_SIGN_UP) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {

                val user = FirebaseAuth.getInstance().currentUser
                createUserWithEmailAndPassword(user!!.email!!, "7417RudyM")
                toast("sign-up in progress ")
                Log.d("Error-signin", response?.error.toString())
            } else {
                toast("Error while sign in ")
                Log.d("Error-signin", response?.error.toString())
                // Sign in failed. If response is null the user canceled the
                // sign-in flow usFirebaseAuth.ing the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

    }


}
