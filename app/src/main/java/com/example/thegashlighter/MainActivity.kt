package com.example.thegashlighter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.thegashlighter.ui.theme.TheGashlighterTheme

import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val notificationHelper = NotificationHelper(this)
        notificationHelper.requestNotificationPermission(this)

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(3, TimeUnit.HOURS)
            .setInitialDelay((1..180).random().toLong(), TimeUnit.MINUTES) // Random initial delay
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "gaslighter_notification_work",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        setContent {
            TheGashlighterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheGashlighterTheme {
        Greeting("Android")
    }
}