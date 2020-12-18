package com.radusalagean.mvhrbypass.persistence.sharedprefs

import android.content.SharedPreferences

class SharedPreferencesRepository(private val sharedPreferences: SharedPreferences) {

    fun getLastValidAddress(): String? {
        return sharedPreferences.getString(SharedPreferencesConstants.PREF_LAST_VALID_ADDRESS, null)
    }

    fun setLastValidAddress(address: String) {
        sharedPreferences.edit()
            .putString(SharedPreferencesConstants.PREF_LAST_VALID_ADDRESS, address)
            .apply()
    }
}