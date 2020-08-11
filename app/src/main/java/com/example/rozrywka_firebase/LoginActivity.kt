package com.example.rozrywka_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)


        login_ok.setOnClickListener() {
            loginUser()
        }


        login_create.setOnClickListener() {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }


    public override fun onStart() {
        val currentUser = auth.currentUser
        updateUI(currentUser)
        super.onStart()
    }





    private fun updateUI(currentUser: FirebaseUser?) {

        if(currentUser != null) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
        }
    }




    fun loginUser() {

        if(login_email.text.toString().isEmpty()) {
            Toast.makeText(this, "Podaj swój e-mail", Toast.LENGTH_SHORT).show()
            return
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(login_email.text.toString()).matches()) {
            Toast.makeText(this, "Adres e-mail jest niepoprawny", Toast.LENGTH_SHORT).show()
            return
        }


        if(login_password.text.toString().isEmpty()) {
            Toast.makeText(this, "Podaj swoje hasło", Toast.LENGTH_SHORT).show()
            return
        }


        login_progressBar.isVisible = true

        auth.signInWithEmailAndPassword(login_email.text.toString(), login_password.text.toString())
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                }
                else {
                    login_progressBar.isVisible = false
                    Toast.makeText(baseContext, "Nie udało się zalogować", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
}