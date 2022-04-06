package com.example.instagram_clone.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.ProgressDialogLayoutBinding

open class BaseActivity : AppCompatActivity() {

    private var dialog:Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showProgressDialog(message:String="Please wait..."){
        val dialogBinding = ProgressDialogLayoutBinding.inflate(layoutInflater)
        dialog = Dialog(this)
        dialog?.setContentView(R.layout.progress_dialog_layout)
        dialogBinding.tvDialog.text = message
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    fun hideProgressDialog(){
        dialog?.hide()
        dialog = null
    }
}