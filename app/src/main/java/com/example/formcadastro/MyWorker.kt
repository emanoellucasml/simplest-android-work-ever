package com.example.formcadastro

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    val sharedPreferences = Preferences(context)

    override fun doWork(): Result {
        Log.d("worker", "executado!")
        sharedPreferences.storeBoolean(MainActivity.STRING_WORK_EXECUTED, true)
        return Result.success()
    }
}