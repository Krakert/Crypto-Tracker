package com.krakert.tracker.models

import com.google.gson.annotations.SerializedName

data class Links(
    val homepage: List<String>? = null,
    @SerializedName("blockchain_site")
    val blockchainSite: List<String?>? = null,
    @SerializedName("official_forum_url")
    val officialForumUrl: List<String>? = null,
    @SerializedName("chat_url")
    val chatUrl: List<String>? = null,
    @SerializedName("announcement_url")
    val announcementUrl: List<String>? = null,
    @SerializedName("twitter_screen_name")
    val twitterScreenName: String? = null,
    @SerializedName("facebook_username")
    val facebookUsername: String? = null,
    @SerializedName("bitcointalk_thread_identifier")
    val bitcointalkThreadIdentifier: String? = null,
    @SerializedName("telegram_channel_identifier")
    val telegramChannelIdentifier: String? = null,
    @SerializedName("subreddit_url")
    val subredditUrl: String? = null,
    @SerializedName("repos_url")
    val reposUrl: ReposUrl? = null
)


data class ReposUrl(
    val github: List<String>,
    val bitbucket: List<String>
)