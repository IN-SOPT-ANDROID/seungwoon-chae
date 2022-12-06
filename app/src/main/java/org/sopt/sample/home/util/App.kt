package org.sopt.sample.home.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.edit
import java.lang.reflect.Array.set

object AuthLocalStorage {
    private const val PREFERENCE_NAME = "auth"
    private lateinit var preference: SharedPreferences
    private const val TOKEN = "USER_TOKEN"

    fun init(context: Context) {
        preference = context.getSharedPreferences("${context.packageName}_${PREFERENCE_NAME}", Context.MODE_PRIVATE)
    }

    var userToken: String
        get() = preference.getString(TOKEN, "") ?: ""
        set(value) = preference.edit { putString(TOKEN, value) } // true면 데이터를 동기로 처리
}

//object AuthLocalStorage {
//    private const val PREFERENCE_NAME = "auth"
//    private lateinit var preference: SharedPreference
//    private const val TOKEN = "USER_TOKEN"
//
//    fun init(context: Context) {
//        preference = if (BuildConfig.DEBUG) context.getSharedPreference("${context.packageName}_${PREFERENCE_NAME}", Context.MODE_PRIVATE)
//        else EncryptedSharedPreferences.create(
//            FILE_NAME,
//            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }
//
//    // Boolean 데이터인 경우 putBoolean,
//    var userToken: String=
//        set(value) = preference.edit { putString(TOKEN, value) }
//        get() = dataStore.getString("USER_TOKEN", "") ?: ""
//}

class App: Application(){

    override fun onCreate(){
        super.onCreate()
        AuthLocalStorage.init(this)
        AuthLocalStorage.userToken = "누누"
        val value = AuthLocalStorage.userToken
    }
}