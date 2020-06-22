package com.hotelgis.admin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hotelgis.LoginActivity
import com.hotelgis.R
import kotlinx.android.synthetic.main.activity_register_user.*

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        // Initialize Firebase Auth
        auth = Firebase.auth
        // Access a Cloud Firestore instance from your Activity
        db = Firebase.firestore

        toolbar.title = "Register User"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnAddDataUser.setOnClickListener {
            if (edtUserPassword.text.equals(edtUserRetypePassword.text))
                registerUser(edtUserEmail.text.toString(), edtUserPassword.text.toString())
            else
                Toast.makeText(baseContext, "Konfirmasi Password Berbeda", Toast.LENGTH_SHORT).show()
        }
    }

    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("d", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    addUserToFireStore(email, password, user?.uid.toString())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("d", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }

    fun addUserToFireStore(email: String, password: String, uId: String) {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "uid" to uId,
            "username" to edtUserName.text.toString(),
            "email" to email,
            "no_tlp" to edtUserNoTelp.text.toString(),
            "password" to password
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("d", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("d", "Error adding document", e)
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}