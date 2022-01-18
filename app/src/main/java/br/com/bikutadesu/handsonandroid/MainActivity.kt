package br.com.bikutadesu.handsonandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.bikutadesu.handsonandroid.databinding.ActivityMainBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.btnSignIn.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                binding.lblLoginError.setText("Você precisa preencher todos os campos, amigo.")
            } else {
                AuthenticateUser(email, password)
            }

        }

        binding.lblSignup.setOnClickListener {
            GoToSignUpScreen()
        }
    }

    private fun AuthenticateUser(email: String, password: String) {
        val error = binding.lblLoginError

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Logado com sucesso, bombom!", Toast.LENGTH_LONG).show()
                GoToRootScreen()
            }
        }.addOnFailureListener {
            when (it) {
                is FirebaseAuthInvalidCredentialsException -> error.setText("Opa bombom, ta errado esse login aí!")
                is FirebaseNetworkException -> error.setText("Opa bombom, você esta sem conexão com a internet :c")
                else -> error.setText("Opa bombom, nós tivemos um erro aqui, tenta de novo por favor :c")
            }
        }
    }

    private fun GoToRootScreen() {
        val intent: Intent = Intent(this, RootActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun GoToSignUpScreen() {
        val intent: Intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}