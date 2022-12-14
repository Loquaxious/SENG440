package com.example.a440tut2composemorsecode

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toner = ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
        val dotTime = 200
        val dashTime = 3 * dotTime

        setContent {
            Scaffold(
                topBar = { TopAppBar(title = { Text(getString(R.string.app_name)) } )}
            ) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .padding(all = 2.dp)
                    ) {
                        SoundButton(getString(R.string.dotButtonText)) {
                            toner.startTone(ToneGenerator.TONE_SUP_DIAL, dotTime)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(all = 2.dp)
                    ) {
                        SoundButton(getString(R.string.dashButtonText)) {
                            toner.startTone(ToneGenerator.TONE_SUP_DIAL, dashTime)
                        }
                    }
                }

            }
            }
    }


    @Composable
    fun SoundButton(label: String, onClick: () -> Unit) {
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = onClick) {
            Text(
                text = label,
            )
        }
    }


}