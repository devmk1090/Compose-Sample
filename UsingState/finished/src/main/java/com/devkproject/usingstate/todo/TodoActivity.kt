package com.devkproject.usingstate.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.devkproject.usingstate.ui.theme.UsingStateTheme

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsingStateTheme {
                Surface {

                }
            }
        }
    }
}