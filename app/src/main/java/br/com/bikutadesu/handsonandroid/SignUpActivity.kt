package br.com.bikutadesu.handsonandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.bikutadesu.handsonandroid.databinding.ActivityMainBinding
import br.com.bikutadesu.handsonandroid.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpButton = binding.btnSignUp

        supportActionBar!!.hide()

        signUpButton.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val name = binding.txtName.text.toString()
            val password = binding.txtPassword.text.toString()
            val repeatPassword = binding.txtRepeatPassword.text.toString()
            val errorMessage = binding.lblSignupError

            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || repeatPassword.isEmpty()) {
                errorMessage.setText("Opa bombom, você precisa preencher todos os campos!")
            } else {
                RegisterUser(binding.txtName, binding.txtEmail, binding.txtPassword, binding.txtRepeatPassword, errorMessage)
                GoToRootScreen()
            }

        }
    }

    private fun GoToRootScreen() {
        val intent: Intent = Intent(this, RootActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun RegisterUser(name: EditText, email: EditText, password: EditText, repeatPassword: EditText, errorMessage: TextView) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnSuccessListener {
            Toast.makeText(this, "Cadastrado com sucesso bombom!", Toast.LENGTH_SHORT).show()
            name.setText("")
            email.setText("")
            password.setText("")
            repeatPassword.setText("")
            errorMessage.setText("")
        }.addOnFailureListener {
            val error = it

            when (error){
                is FirebaseAuthWeakPasswordException -> errorMessage.setText("A senha deve possuir pelo menos 6 caracteres!")
                is FirebaseAuthUserCollisionException -> errorMessage.setText("Usuário já cadastrado!")
                is FirebaseNetworkException -> errorMessage.setText("Sem conexã com a internet!")
                else -> errorMessage.setText(it.message.toString())
            }
        }
    }


}