package org.sopt.sample.home

import android.content.Context
import android.content.SharedPreferences
import org.sopt.sample.LoginActivity

object LoginSharedPreferences { // 자동 로그인 기능 구현을 위한 SharePreference 객체, 접근의 편의성을 위해 최상위 객체 object로 선언함
    private const val MY_ACCOUNT : String = "account"

    fun setUserId(context: LoginActivity, input: String) { // 유저 아이디 저장
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.commit()
    }

    fun getUserId(context: Context): String { // 저장된 유저 아이디 반환
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }

    fun setUserPw(context: LoginActivity, input: String) { // 유저 비밀번호 저장
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PW", input)
        editor.commit()
    }

    fun getUserPw(context: Context): String { // 저장된 유저 비밀번호 반환
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PW", "").toString()
    }

    fun clearNowUser(context: Context) { // 로그아웃 기능이 추가될 시 사용할 예정, 자동로그인 상황을 초기화
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

}