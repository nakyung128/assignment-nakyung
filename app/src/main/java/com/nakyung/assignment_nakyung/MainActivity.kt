package com.nakyung.assignment_nakyung

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nakyung.assignment_nakyung.ui.main.MainScreen
import com.nakyung.assignment_nakyung.ui.theme.AssignmentnakyungTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentnakyungTheme {
                MainScreen()
            }
        }
    }
}
