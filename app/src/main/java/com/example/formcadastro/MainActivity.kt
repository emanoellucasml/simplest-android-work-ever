package com.example.formcadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.formcadastro.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        sharedPreferences = Preferences(this@MainActivity)
        setContentView(this.binding.root)
        this.setup()
    }

    fun setup(){
        this.setListeners()
        updateUi()
    }

    fun setListeners(){
        this.binding.btnEnviar.setOnClickListener {
            if(!sharedPreferences.getStoredBoolean(STRING_WORK_SCHEDULED)){
                sharedPreferences.storeBoolean(STRING_WORK_SCHEDULED, true)
                scheduleWork()
                Snackbar.make(it, getString(R.string.work_scheduled), Snackbar.LENGTH_LONG).show()
                Log.d("worker", "agendado!")
            }else{
                Snackbar.make(it, getString(R.string.work_already_scheduled), Snackbar.LENGTH_LONG).show()
            }

            updateUi()
        }
    }

    fun updateUi(){
        val isWorkScheduled = sharedPreferences.getStoredBoolean(STRING_WORK_SCHEDULED)
        val isWorkExecuted  = sharedPreferences.getStoredBoolean(STRING_WORK_EXECUTED)
        if(isWorkScheduled){
            binding.scheduled.text = composeText(binding.scheduled.text.toString(), getString(R.string.yes))
            if(isWorkExecuted){
                binding.executed.text = composeText(binding.executed.text.toString(), getString(R.string.yes))
            }else{
                binding.executed.text = composeText(binding.executed.text.toString(), getString(R.string.no))
            }
        }else{
            binding.scheduled.text = composeText(binding.scheduled.text.toString(), getString(R.string.no))
            binding.executed.text = composeText(binding.executed.text.toString(), getString(R.string.no))
        }
    }

    fun composeText(input: String, piece: String): String{
        val questionIndex = input.indexOf(getString(R.string.interrogation))
        if (questionIndex == -1) {
            return input
        }
        return input.substring(0, questionIndex + 1) + " $piece"
    }

    fun scheduleWork(){
        val compressionWork = OneTimeWorkRequestBuilder<MyWorker>().build()

        WorkManager.getInstance(this@MainActivity)
            .enqueue(compressionWork)
    }


    companion object{
        val STRING_WORK_SCHEDULED: String = "String_work@com.example.formcadastro_SCHEDULED"
        val STRING_WORK_EXECUTED: String = "String_work@com.example.formcadastro_EXECUTED"
    }
}