package br.com.savestudents.debug_mode.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.savestudents.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        handleSubmit()
    }

    private fun handleSubmit() {
        binding.buttonSubmit.setOnClickListener {
            val email = binding.emailEditText.editText().text.toString().trim()
            val password = binding.passwordEditText.editText().text.toString().trim()
            if (email.isNotBlank() && password.isNotBlank()) {
                handleLoginAuthentication(email, password)
            } else {
                Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun handleLoginAuthentication(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(this) { response ->
                startActivity(Intent(applicationContext, CreateSubjectActivity::class.java))
            }.addOnFailureListener { exception ->
                binding.emailEditText.setError("Email incorreto, tente novamente.")
                binding.passwordEditText.setError("Passoword incorreto, tente novamente.")
            }
    }

}