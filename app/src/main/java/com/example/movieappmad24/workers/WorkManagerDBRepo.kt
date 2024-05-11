package com.example.movieappmad24.workers


import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager


class WorkManagerDBRepo (context: Context) {

    private val workManager = WorkManager.getInstance(context)
     fun seedDatabase() {

        val seedWorkRequestBuilder = OneTimeWorkRequestBuilder<DBWorker>()


        workManager.enqueue(seedWorkRequestBuilder.build())
    }
}