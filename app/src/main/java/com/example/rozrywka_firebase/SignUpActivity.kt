package com.example.rozrywka_firebase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)


        signUp_ok.setOnClickListener() {
            signUpUser()
        }
    }




    fun signUpUser() {

        if(signUp_email.text.toString().isEmpty()) {
            Toast.makeText(this, "Podaj swój e-mail", Toast.LENGTH_SHORT).show()
            return
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(signUp_email.text.toString()).matches()) {
            Toast.makeText(this, "Adres e-mail jest niepoprawny", Toast.LENGTH_SHORT).show()
            return
        }


        if(signUp_password.text.toString().isEmpty()) {
            Toast.makeText(this, "Podaj swoje hasło", Toast.LENGTH_SHORT).show()
            return
        }


        signUp_progressBar.isVisible = true



        auth.createUserWithEmailAndPassword(signUp_email.text.toString(), signUp_password.text.toString())
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "Konto zostało utworzone", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else {
                    signUp_progressBar.isVisible = false
                    Toast.makeText(baseContext, "Nie udało się utworzyć konta", Toast.LENGTH_SHORT).show()
                }
            }

    }
}