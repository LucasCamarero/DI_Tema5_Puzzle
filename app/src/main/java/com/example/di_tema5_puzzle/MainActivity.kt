package com.example.di_tema5_puzzle

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
import com.example.di_tema5_puzzle.ui.theme.DI_Tema5_PuzzleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DI_Tema5_PuzzleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //Puzzle1(modifier = Modifier.padding(innerPadding))
                    //Puzzle2(modifier = Modifier.padding(innerPadding))
                    //Puzzle3(modifier = Modifier.padding(innerPadding))
                    Puzzle4(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}