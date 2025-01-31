package cat.dam.dishdiscovery.layouts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.dam.dishdiscovery.objects.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignInScreenViewModel : ViewModel() {
    private val _loading = MutableLiveData(false)
    private val auth = Firebase.auth
    private val TAG = "SignInScreenViewModel"
    fun signIn(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "createUser: User created")
                        home()
                    } else {
                        Log.d(TAG, "createUser: User not created")
                    }
                    _loading.value = false
                }
        }
    }

    fun createUser() {
        val user = User(
            uuid = auth.currentUser?.uid.toString(),
            userName = auth.currentUser?.email.toString(),
            administrator = false,
            likedDishes = listOf(),
            mealPlanner = listOf(),
            premium = false,
            publishedDishes = listOf()
        ).userToMap()

        FirebaseFirestore.getInstance().collection("User")
            .document(auth.currentUser?.uid.toString())
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "createUser: User created")
            }
            .addOnFailureListener {
                Log.d(TAG, "createUser: User not created")
            }
    }
}