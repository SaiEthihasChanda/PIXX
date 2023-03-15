package com.example.gdsc

import android.Manifest
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import java.io.File
import android.net.Uri
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build


import android.provider.MediaStore

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gdsc.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var pickedPhoto: Uri? = null
    var pickedBitMap: Bitmap? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        var file = File(filesDir, "password.txt")
        if (!file.exists()){
            password = "password";


        }
        else{
            password = File(filesDir,"password.txt").readText()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.visibility = View.INVISIBLE
        binding.changepass.visibility = View.INVISIBLE
        binding.setpass.visibility = View.INVISIBLE
        binding.confirmpass.visibility = View.INVISIBLE
        binding.imageView.visibility = View.INVISIBLE
        binding.textView2.visibility = View.INVISIBLE

        binding.back.setOnClickListener{
            binding.back.visibility = View.INVISIBLE
            binding.imageView.visibility = View.INVISIBLE
            binding.change.visibility = View.VISIBLE
            binding.button.visibility = View.VISIBLE
            binding.changepass.visibility = View.INVISIBLE
            binding.confirmpass.visibility = View.INVISIBLE
            binding.setpass.visibility = View.INVISIBLE
            binding.textView2.visibility = View.INVISIBLE
            binding.editTextTextPersonName.visibility = View.VISIBLE
            binding.editTextTextPassword.visibility = View.VISIBLE
            binding.imageView.visibility = View.INVISIBLE
            binding.confirmpass.setText("")
            binding.changepass.setText("")



        }


        binding.button.setOnClickListener{
            var file = File(filesDir, "password.txt")
            if (!file.exists()){
                password = "password";


            }
            else{
                password = File(filesDir,"password.txt").readText()
            }
            if (((binding.editTextTextPersonName.text.toString()) == "admin")&&(binding.editTextTextPassword.text.toString()) == password)
            {
                //binding.button.setText("it works!")


                Toast.makeText(this, "login succesfull", Toast.LENGTH_SHORT).show()
                binding.back.visibility = View.VISIBLE
                binding.textView2.visibility = View.VISIBLE
                binding.editTextTextPersonName.visibility = View.INVISIBLE
                binding.editTextTextPassword.visibility = View.INVISIBLE
                binding.button.visibility = View.INVISIBLE
                binding.change.visibility = View.INVISIBLE
                binding.imageView.visibility = View.VISIBLE
                binding.editTextTextPassword.setText("")
                binding.editTextTextPersonName.setText("")

            }
        }
        binding.change.setOnClickListener{
            binding.editTextTextPersonName.visibility = View.INVISIBLE
            binding.editTextTextPassword.visibility = View.INVISIBLE
            binding.changepass.visibility = View.VISIBLE
            binding.setpass.visibility = View.VISIBLE
            binding.confirmpass.visibility = View.VISIBLE

            binding.change.visibility = View.INVISIBLE
            binding.button.visibility = View.INVISIBLE
            binding.back.visibility = View.VISIBLE
            //File(getFilesDir(),"password.txt").writeText("pass")
        }
        binding.changepass.setOnClickListener{
            if((binding.confirmpass.text.toString()).equals(binding.changepass.text.toString()))
            {
                File(getFilesDir(),"password.txt").writeText(binding.changepass.text.toString())
                Toast.makeText(this, "password changed succesfully", Toast.LENGTH_SHORT).show()
                binding.editTextTextPersonName.visibility = View.VISIBLE
                binding.editTextTextPassword.visibility = View.VISIBLE
                binding.changepass.visibility = View.INVISIBLE
                binding.setpass.visibility = View.INVISIBLE
                binding.change.visibility = View.VISIBLE
                binding.button.visibility = View.VISIBLE
                binding.confirmpass.visibility = View.INVISIBLE
                binding.back.visibility = View.INVISIBLE
                binding.confirmpass.setText("")
                binding.changepass.setText("")
                binding.editTextTextPassword.setText("")
                binding.editTextTextPersonName.setText("")

            }


        }

    }
    fun pickPhoto(view: View){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) { // izin alınmadıysa
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
        } else {
            val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntext,2)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galeriIntext = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                binding.textView2.visibility = View.INVISIBLE
                startActivityForResult(galeriIntext,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            pickedPhoto = data.data
            if (pickedPhoto != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver,pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(pickedBitMap)
                }
                else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver,pickedPhoto)
                    imageView.setImageBitmap(pickedBitMap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
