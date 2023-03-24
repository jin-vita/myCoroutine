package org.techtown.mycoroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import org.techtown.mycoroutine.ui.theme.MyCoroutineTheme
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    companion object { const val TAG = "MainActivity" }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCoroutineTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            // 직렬 처리
            val time: Long = measureTimeMillis {
                val result1: String = network1()
                val result2 = network2()
                Log.d(TAG, "직렬 결과1 : $result1")
                Log.d(TAG, "직렬 결과2 : $result2")
            }
            Log.d(TAG, "직렬 시간 : $time")

            // 병렬 처리
            val time2: Long = measureTimeMillis {
                val result1: Deferred<String> = async { network1() }
                val result2 = async { network2() }
                Log.d(TAG, "병렬 결과1 : ${result1.await()}")
                Log.d(TAG, "병렬 결과2 : ${result2.await()}")
            }
            Log.d(TAG, "병렬 시간 : $time2")

        }

    }

    private suspend fun network1(): String {
        delay(3000)
        return "result 1"
    }

    private suspend fun network2(): String {
        delay(2000)
        return "result 2"
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCoroutineTheme {
        Greeting("Android")
    }
}