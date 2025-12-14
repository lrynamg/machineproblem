package com.example.machineproblem

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        var btnJoinClub : Button = findViewById(R.id.btnJoin)
        var txtFname : EditText = findViewById(R.id.txtFname)
        var txtLname : EditText = findViewById(R.id.txtLname)
        var txtEmail : EditText = findViewById(R.id.txtEmail)
        var txtDrink : EditText = findViewById(R.id.txtDrink)

        btnJoinClub.setOnClickListener {

            var fname = txtFname.text.toString().trim()
            var lname = txtLname.text.toString().trim()
            var email = txtEmail.text.toString().trim()
            var drink = txtDrink.text.toString().trim()

            //variable for all true validation
            var validInput = true

            //fname and lname validation

            if(fname.isEmpty()){
                txtFname.error = "First name is required!"
                validInput = false
            } else if(fname.length >=20){
                txtFname.error = "Must be less than 30 characters."
                validInput = false
            } else if (!fname.matches(Regex("^[a-zA-Z\\s]+$"))){
                txtFname.error = "Only letters allowed/Capitalize first letter"
                validInput = false
            }

            if(lname.isEmpty()){
                txtLname.error = "Last name is required!"
                validInput = false
            } else if(lname.length >=20){
                txtLname.error = "Must be less than 30 characters."
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
            } else if(drink.length >=30){
                txtDrink.error = "Must be less than 30 characters."
                validInput = false
            }else if (!drink.matches(Regex("^[a-zA-Z\\s]+$"))){
                txtDrink.error = "Only letters allowed/Capitalize first letter"
                validInput = false
            }

            //submission if valid

            if (validInput){
                // Show confirmation dialog
                showConfirmationDialog(fname, lname, email, drink)
            }
        }
    }

    private fun showConfirmationDialog(firstName: String, lastName: String, email: String, faveDrink: String) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Registration")
            .setMessage("Are you sure you want to save your spot at the cafe?\n\nName: $firstName $lastName\nEmail: $email\nFavorite Drink: $faveDrink")
            .setPositiveButton("Yes, Save My Spot!") { dialog, _ ->
                dialog.dismiss()
                saveToFirebase(firstName, lastName, email, faveDrink)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun saveToFirebase(firstName: String, lastName: String, email: String, faveDrink: String) {
        // Show loading dialog
        val loadingDialog = AlertDialog.Builder(this)
            .setTitle("Saving...")
            .setMessage("Please wait while we reserve your spot...")
            .setCancelable(false)
            .create()
        loadingDialog.show()

        // Create a user data object
        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "favoriteDrink" to faveDrink,
            "timestamp" to System.currentTimeMillis()
        )

        // Save to Firestore
        firestore.collection("registrations")
            .add(userData)
            .addOnSuccessListener { documentReference ->
                loadingDialog.dismiss()

                // Show success dialog
                AlertDialog.Builder(this)
                    .setTitle("Success! ☕")
                    .setMessage("Your spot at the study cafe has been reserved!\n\nWelcome, $firstName!")
                    .setPositiveButton("Continue") { dialog, _ ->
                        dialog.dismiss()

                        // Navigate to SecondActivity
                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("FirstName", firstName)
                        intent.putExtra("LastName", lastName)
                        intent.putExtra("Email", email)
                        intent.putExtra("FaveDrink", faveDrink)
                        startActivity(intent)

                        // Clear the form
                        findViewById<EditText>(R.id.txtFname).text.clear()
                        findViewById<EditText>(R.id.txtLname).text.clear()
                        findViewById<EditText>(R.id.txtEmail).text.clear()
                        findViewById<EditText>(R.id.txtDrink).text.clear()
                    }
                    .setCancelable(false)
                    .show()
            }
            .addOnFailureListener { e ->
                loadingDialog.dismiss()

                // Show error dialog
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Failed to save registration:\n${e.message}\n\nPlease try again.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
    }
}