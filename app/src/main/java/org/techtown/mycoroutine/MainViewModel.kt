package org.techtown.mycoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.techtown.mycoroutine.MainActivity.Companion.TAG

class MainViewModel : ViewModel() {
    private var count = 0

    val countFlow: Flow<Int> = flow {
        while (true) {
            count++
            emit(count)
            Log.d(TAG, "count: $count")
            delay(2000)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        viewModelScope.launch {
//            countFlow.collect {
//                delay(4000)
//                Log.d(TAG, "collect: $it")
//            }

            countFlow.collectLatest {
                delay(4000)
                Log.d(TAG, "collect: $it")
            }
        }
    }

    fun factorial(k: Int): Int {
        tailrec fun factTail(m: Int, n: Int): Int =
            if (m == 0) n else factTail(m - 1, m * n)
        return factTail(k, 1)
    }
}