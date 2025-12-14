package com.example.machineproblem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*var labelUser : TextView = findViewById(R.id.lblFname); nanggaling sa second act
        labelUser.setText(getIntent().getStringExtra("FirstName"));*/

        var fname = intent.getStringExtra("FirstName")
        var lname = intent.getStringExtra("LastName")
        var drink = intent.getStringExtra("FaveDrink")
        var email = intent.getStringExtra("Email")

        var message = "Welcome to the Study Café, ${fname + " " + lname}! " +
                "A warm cup of $drink is waiting for you while you focus on your studies. " +
                "Check your inbox at $email for café updates!"

        var txtMessage : TextView = findViewById(R.id.welcomeMessage)
        txtMessage.text = message

        var btnBack : Button = findViewById(R.id.btnBack);

        btnBack.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

        var  btnStudy : Button = findViewById(R.id.btnStudy);

        btnStudy.setOnClickListener {
            var intent = Intent(this, GroupPage::class.java);
            startActivity(intent);
        }

    }
}