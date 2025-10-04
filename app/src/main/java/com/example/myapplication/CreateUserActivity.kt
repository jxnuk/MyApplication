package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.data.UserEntity
import com.example.myapplication.security.PasswordHasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateUserActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val confirmPasswordInput = findViewById<EditText>(R.id.confirmPasswordInput)
        val createAccountBtn = findViewById<Button>(R.id.createAccountBtn)
        val loginRedirect = findViewById<TextView>(R.id.loginRedirect)

        createAccountBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim().lowercase()
            val pass = passwordInput.text.toString()
            val confirm = confirmPasswordInput.text.toString()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                toast("Please fill all fields"); return@setOnClickListener
            }
            if (pass != confirm) {
                toast("Passwords do not match"); return@setOnClickListener
            }

            lifecycleScope.launch {
                val exists = withContext(Dispatchers.IO) { db.userDao().getByEmail(email) != null }
                if (exists) {
                    toast("Email already in use")
                    return@launch
                }

                val hash = PasswordHasher.sha256(pass)
                val user = UserEntity(name = name, email = email, passwordHash = hash)
                val userId = withContext(Dispatchers.IO) { db.userDao().insert(user) }

                // Optional: auto-login the newly created user
                withContext(Dispatchers.IO) {
                    db.userDao().clearLoggedIn()
                    db.userDao().setLoggedIn(userId)
                }

                toast("Account created")
                startActivity(Intent(this@CreateUserActivity, LoginActivity::class.java))
                finish()
            }
        }

        loginRedirect.setOnClickListener { finish() }
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
