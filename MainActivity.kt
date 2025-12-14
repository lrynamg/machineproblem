package com.example.machineproblem

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btnJoinClub : Button = findViewById(R.id.btnJoin);
        var txtFname : EditText = findViewById(R.id.txtFname);
        var txtLname : EditText = findViewById(R.id.txtLname);
        var txtEmail : EditText = findViewById(R.id.txtEmail);
        var txtDrink : EditText = findViewById(R.id.txtDrink);

        btnJoinClub.setOnClickListener {

            var fname = txtFname.text.toString().trim()
            var lname = txtLname.text.toString().trim()
            var email = txtEmail.text.toString().trim()
            var drink = txtDrink.text.toString().trim()

            //variable for all true validation
            var validInput = true

            //fname and lname validation possible change to nn

            if(fname.isEmpty()){
                txtFname.error = "First name is required!"
                validInput = false
            } else if(fname.length >=20){
                txtFname.error = "Must be less than 30 characters." +
                        ""
                validInput = false
            } else if (!fname.matches(Regex("^[a-zA-Z\\s]+$"))){
                txtFname.error = "Only letters allowed/Capitalize first letter"
                validInput = false
            }

            if(lname.isEmpty()){
                txtLname.error = "Last name is required!"
                validInput = false
            } else if(fname.length >=20){
                txtFname.error = "Must be less than 30 characters."
                validInput = false
            }else if (!lname.matches(Regex("^[a-zA-Z\\s]+$"))){
                txtLname.error = "Only letters allowed/Capitalize first letter"
                validInput = false
            }

            //email validation

            if (email.isEmpty()){
                txtEmail.error = "Email is required!"
                validInput = false
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                txtEmail.error = "Invalid email format"
                validInput = false
            }

            //fave drink validation
            if(drink.isEmpty()){
                txtDrink.error = "Please input your favorite drink"
                validInput = false
            } else if(fname.length >=30){
                txtFname.error = "Must be less than 30 characters."
                validInput = false
            }else if (!drink.matches(Regex("^[a-zA-Z\\s]+$"))){
                txtDrink.error = "Only letters allowed/Capitalize first letter"
                validInput = false
            }

            //submission if valid

            if (validInput){
                var intent = Intent(this, SecondActivity::class.java);
                intent.putExtra("FirstName", txtFname.text.toString());
                intent.putExtra("LastName", txtLname.text.toString());
                intent.putExtra("Email", txtEmail.text.toString());
                intent.putExtra("FaveDrink", txtDrink.text.toString());
                startActivity(intent);
            }
        };
    }
}



