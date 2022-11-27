package com.prodroid.sampleclass

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        final var PER_REQ_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCheckPer.setOnClickListener {
            if(checkPer()){
                Toast.makeText(this, "Permissions Granted!!", Toast.LENGTH_SHORT).show()
                yourTaskBasedOnPer()
            } else {
                RequestPer()
            }
        }

        btnReqPer.setOnClickListener {
            RequestPer()
        }
    }

    fun RequestPer(){
        requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, READ_CONTACTS), PER_REQ_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode== PER_REQ_CODE){

            var resLoc = grantResults[0]==PackageManager.PERMISSION_GRANTED
            var resContact = grantResults[1]==PackageManager.PERMISSION_GRANTED

            if(resLoc && resContact){
                Toast.makeText(this, "Permnission Granted!!", Toast.LENGTH_SHORT).show()
                yourTaskBasedOnPer()
            } else {
                showDialog()
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }


        }



    }

    fun checkPer() : Boolean{

        val resLocation = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
        val resContact = ContextCompat.checkSelfPermission(this, READ_CONTACTS)

        return resLocation==PackageManager.PERMISSION_GRANTED && resContact==PackageManager.PERMISSION_GRANTED
    }

    fun yourTaskBasedOnPer(){
        startActivity(Intent(this, ImplicitIntentPassing::class.java))
    }

    fun showDialog(){
        AlertDialog.Builder(this)
            .setTitle("Allow Permission")
            .setMessage("We need this permission for this feature to be enabled!!")
            .setPositiveButton("Request Permissions"
            ) { p0, p1 -> RequestPer() }
            .setNegativeButton("Cancel"){a,b->

            }.create().show()
    }
}