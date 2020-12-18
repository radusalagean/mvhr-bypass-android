package com.radusalagean.mvhrbypass.haptic

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class HapticFeedbackManager(
    private val vibrator: Vibrator
) {

    private val shortFeedbackTiming = longArrayOf(0, 12)

    fun shortFeedback() {
        executeCommand(shortFeedbackTiming)
    }

    private fun executeCommand(timings: LongArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(timings, -1))
        } else {
            vibrator.vibrate(timings, -1)
        }
    }
}