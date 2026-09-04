package com.example.grihacare

import android.content.Context

data class UserProfile(
    val name: String = "",
    val mobile: String = "",
    val email: String = "",
    val city: String = "",
    val occupation: String = "",
    val budget: String = "",
    val preferredStay: String = ""
)

class UserProfileStore(
    context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "grihacare_profile",
            Context.MODE_PRIVATE
        )

    fun save(profile: UserProfile) {

        prefs.edit()
            .putString("name", profile.name)
            .putString("mobile", profile.mobile)
            .putString("email", profile.email)
            .putString("city", profile.city)
            .putString("occupation", profile.occupation)
            .putString("budget", profile.budget)
            .putString(
                "preferredStay",
                profile.preferredStay
            )
            .apply()
    }

    fun load(): UserProfile {

        return UserProfile(
            name =
                prefs.getString(
                    "name",
                    ""
                ).orEmpty(),

            mobile =
                prefs.getString(
                    "mobile",
                    ""
                ).orEmpty(),

            email =
                prefs.getString(
                    "email",
                    ""
                ).orEmpty(),

            city =
                prefs.getString(
                    "city",
                    ""
                ).orEmpty(),

            occupation =
                prefs.getString(
                    "occupation",
                    ""
                ).orEmpty(),

            budget =
                prefs.getString(
                    "budget",
                    ""
                ).orEmpty(),

            preferredStay =
                prefs.getString(
                    "preferredStay",
                    ""
                ).orEmpty()
        )
    }
}