package cat.dam.dishdiscovery.layouts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val TAG = "LoginScreenViewModel"
    fun logInWithGoogleCredentials(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            auth.signInWithCredential(credential).addOnCompleteListener {
                try {
                    if (it.isSuccessful) {
                        Log.d(TAG, "signIn: Sign in successful")
                        home()
                    } else {
                        Log.d(TAG, "signIn: Sign in failed")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "signIn: ${e.message}")
                }
            }
        }

    fun logInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                try {
                    if (it.isSuccessful) {
                        Log.d(TAG, "signIn: Sign in successful")
                        home()
                    } else {
                        Log.d(TAG, "signIn: Sign in failed")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "signIn: ${e.message}")
                }
            }
        }
}