package com.prodroid.sampleclass

import android.Manifest
import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_implicit_intent_passing.*

class ImplicitIntentPassing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit_intent_passing)

        btnDial.setOnClickListener {
            if(checkPer()) {
                callPhone()
            } else {
                RequestPer()
            }
        }

        btnEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("abc@gmail.com", "xyz@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WallCano App Review")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "The app was amazing to experience..")
            startActivity(emailIntent)

        }

        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "plain/text"
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)

            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        btnMsg.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:+918000000000")
            sendIntent.putExtra("sms_body", "My Message here")
            startActivity(sendIntent)
        }

    }

    private fun callPhone() {
        val clIntent = Intent(Intent.ACTION_CALL)
        clIntent.data = Uri.parse("tel:+918000000000")
        startActivity(clIntent)
    }

    fun checkPer() : Boolean{

        val resLocation = ContextCompat.checkSelfPermission(this,
            CALL_PHONE
        )
        return resLocation== PackageManager.PERMISSION_GRANTED
    }

    fun RequestPer(){
        requestPermissions(arrayOf(
            CALL_PHONE
        ), MainActivity.PER_REQ_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode== MainActivity.PER_REQ_CODE){

            val resCall = grantResults[0]==PackageManager.PERMISSION_GRANTED
            if(resCall){
                Toast.makeText(this, "Permnission Granted!!", Toast.LENGTH_SHORT).show()
                callPhone()
            } else {
                //showDialog()
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }


        }



    }
}