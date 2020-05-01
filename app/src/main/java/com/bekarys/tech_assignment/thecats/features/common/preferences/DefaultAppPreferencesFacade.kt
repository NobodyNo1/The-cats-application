package com.bekarys.tech_assignment.thecats.features.common.preferences

import com.bekarys.tech_assignment.thecats.BuildConfig

class DefaultAppPreferencesFacade(
    private val appPreferences: AppPreferences
) {
    fun setup() {
        if (appPreferences.getApiKey().isEmpty())
            appPreferences.setApiKey(BuildConfig.API_KEY)
        if (appPreferences.getCatServiceBaseUrl().isEmpty())
            appPreferences.setCatServiceBaseUrl(BuildConfig.BASE_URL)
    }
}