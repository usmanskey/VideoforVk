package akhmedoff.usman.videoforvk.data.repository

import akhmedoff.usman.videoforvk.BuildConfig
import akhmedoff.usman.videoforvk.data.api.VkApi
import akhmedoff.usman.videoforvk.data.local.UserSettings
import akhmedoff.usman.videoforvk.model.User
import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

class UserRepository(
    private val userSettings: UserSettings,
    private val api: VkApi
) {

    var isLogged = userSettings.isLogged

    fun saveToken(token: String) = userSettings.saveToken(token)

    fun auth(
        username: String,
        password: String,
        captchaSid: String? = null,
        captchaKey: String? = null,
        code: String? = null
    ) = api.auth(
        "https://oauth.vk.com/token?",
        BuildConfig.VK_APP_ID,
        BuildConfig.VK_APP_KEY,
        username,
        password,
        "friends,video,wall,offline,groups,status",
        code = code,
        captchaSid = captchaSid,
        captchaKey = captchaKey

    )

    fun getUsers(users_id: String? = null): LiveData<List<User>> {
        val usersLiveData = api.getUsers(users_id)

        return Transformations.map(usersLiveData, Function {
            return@Function when {
                it.isSuccessfull -> it.response
                else -> listOf()
            }
        })
    }
}