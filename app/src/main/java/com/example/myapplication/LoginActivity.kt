package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.security.PasswordHasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val createUserBtn = findViewById<Button>(R.id.createUserBtn)

        // If someone is already logged in, you could skip to MainActivity here if you want.
        // lifecycleScope.launch { db.userDao().getCurrentLoggedIn()?.let { goHome() } }

        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim().lowercase()
            val pass = passwordInput.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                toast("Please enter email and password"); return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) { db.userDao().getByEmail(email) }
                if (user == null) {
                    toast("No account found for that email")
                    return@launch
                }

                val ok = user.passwordHash == PasswordHasher.sha256(pass)
                if (!ok) {
                    toast("Incorrect password")
                    return@launch
                }

                withContext(Dispatchers.IO) {
                    db.userDao().clearLoggedIn()
                    db.userDao().setLoggedIn(user.id)
                }

                toast("Login successful")
                goHome()
            }
        }

        createUserBtn.setOnClickListener {
            startActivity(Intent(this, CreateUserActivity::class.java))
        }
    }

    private fun goHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
