package com.example.foodinfo.core.utils

import com.example.foodinfo.core.utils.LaunchStrategy.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


/**
 * Wrapper for [CoroutineScope.launch] that stores [Job] to properly handle each [launch] calls depending on
 * provided [launchStrategy].
 */
class CoroutineLauncher(
    private val scope: CoroutineScope,
    private val context: CoroutineContext,
    private val launchStrategy: LaunchStrategy
) {
    private var currentJob: Job? = null

    fun launch(runnable: suspend CoroutineScope.() -> Unit) {
        when (launchStrategy) {
            CANCEL   -> {
                currentJob?.cancel()
                currentJob = scope.launch(context, CoroutineStart.LAZY, runnable)
            }
            IGNORE   -> {
                if (currentJob == null || !currentJob!!.isActive) {
                    currentJob = scope.launch(context, CoroutineStart.LAZY, runnable)
                }
            }
            PARALLEL -> {
                currentJob = scope.launch(context, CoroutineStart.LAZY, runnable)
            }
        }
        currentJob?.start()
    }

    fun release() {
        currentJob?.cancel()
        currentJob = null
    }
}

/**
 * Enum of all possible launch strategies for [CoroutineLauncher] that controls launch behavior depending on
 * [Job] state from previous launch call.
 *
 * * [CANCEL] -- Cancels previous [Job] and starts new one.
 * * [IGNORE] -- Does nothing if previous [Job] state is [Job.isActive].
 * * [PARALLEL] -- Launches new coroutine regardless of previous [Job] state.
 */
enum class LaunchStrategy {
    /**
     * Cancels previous [Job] and starts new one.
     */
    CANCEL,

    /**
     * Does nothing if previous [Job] state is [Job.isActive].
     */
    IGNORE,

    /**
     * Launches new coroutine regardless of previous [Job] state.
     */
    PARALLEL
}