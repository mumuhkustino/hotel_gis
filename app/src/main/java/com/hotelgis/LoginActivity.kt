package com.hotelgis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hotelgis.admin.ui.RegisterUserActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val btnLoginUser = findViewById(R.id.btn_login_user) as Button
        btnLoginUser.setOnClickListener {
            if (!et_email.text.toString().equals("") && !et_email.text.toString().equals(null)) {
                if (!et_password.text.toString().equals("") && !et_password.text.toString().equals(null)) {
                    loginUser(et_email.text.toString(), et_password.text.toString())
                } else {
                    Toast.makeText(baseContext, "Password kosong", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(baseContext, "Email kosong", Toast.LENGTH_SHORT).show()
            }
        }

        val btnLoginAdmin = findViewById(R.id.btn_login_admin) as Button
        btnLoginAdmin.setOnClickListener {
            if (!et_email.text.toString().equals("") && !et_email.text.toString().equals(null)) {
                if (!et_password.text.toString().equals("") && !et_password.text.toString().equals(null)) {
                loginAdmin(et_email.text.toString(), et_password.text.toString())
                } else {
                    Toast.makeText(baseContext, "Password kosong", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(baseContext, "Email kosong", Toast.LENGTH_SHORT).show()
            }
        }

        val btnRegistrasiUser = findViewById(R.id.btn_register) as TextView
        btnRegistrasiUser.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        db = Firebase.firestore
        Log.d("d", "Gawa:onStart : ${currentUser?.uid.toString()}")
        if (currentUser != null) {
            isAdmin(currentUser, false)
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Toast.makeText(baseContext, "Login User Sukses", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("d", "Gawa:loginUserberhasil")
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("d", "signInWithEmail:success")
                    val user = auth.currentUser
                    isNotAdmin(user)
                } else {
                    Log.d("d", "Gawa:loginUsergagal")
                    // If sign in fails, display a message to the user.
                    Log.w("d", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                    // ...
                }

                // ...
            }
    }

    fun loginAdmin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("d", "Gawa:loginAdminberhasil")
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("d", "signInWithEmail:success")
                    val user = auth.currentUser
                    isAdmin(user, true)
                } else {
                    Log.d("d", "Gawa:loginAdmingagal")
                    // If sign in fails, display a message to the user.
                    Log.w("d", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUIAdmin(null)
                    // ...
                }

                // ...
            }
    }

    private fun updateUIAdmin(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(baseContext, "Login Admin Sukses", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isAdmin(user: FirebaseUser?, fromLogin: Boolean) {
        if (fromLogin) {
            Log.d("d", "Gawa:isAdminfromLogin")
            db.collection("users")
                .whereEqualTo("isAdmin", true)
                .whereEqualTo("uid", user?.uid.toString())
                .get()
                .addOnSuccessListener {
                    if (!it.documents.size.equals(0)) {
                        updateUIAdmin(user)
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        logoutUser()
                    }
                }
                .addOnFailureListener {
                    Log.w("d", "Gawa:Error getting documents: ", it)
                }
        } else {
            Log.d("d", "Gawa:isAdminNOTfromLogin")
            db.collection("users")
                .whereEqualTo("isAdmin", true)
                .whereEqualTo("uid", user?.uid.toString())
                .get()
                .addOnSuccessListener {
                    if (!it.documents.size.equals(0)) {
                        updateUIAdmin(user)
                    } else {
                        updateUI(user)
                    }
                }
                .addOnFailureListener {
                    Log.w("d", "Gawa:Error getting documents: ", it)
                }
        }

    }

    private fun isNotAdmin(user: FirebaseUser?) {
        Log.d("d", "Gawa:isNotAdmin")
        db.collection("users")
            .whereEqualTo("isAdmin", false)
            .whereEqualTo("uid", user?.uid.toString())
            .get()
            .addOnSuccessListener {
                if (!it.documents.size.equals(0)) {
                    updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    logoutUser()
                }
            }.addOnFailureListener {
                Log.w("d", "Gawa:Error getting documents: ", it)
            }
    }


    fun logoutUser() {
        Log.d("d", "Gawa:logout")
        Toast.makeText(baseContext, "Logout Sukses", Toast.LENGTH_SHORT).show()
        Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}