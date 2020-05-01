package com.bekarys.tech_assignment.thecats.features.common.downloadmanager

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment


class DownloadManagerController(
    private val downloadManager: DownloadManager
) {


    fun startDownload(url: String, filename: String) {
        val uri: Uri = Uri.parse(url)
        val req = DownloadManager.Request(uri)
        req.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI
                    or DownloadManager.Request.NETWORK_MOBILE
        ).setAllowedOverRoaming(false)
            .setTitle("Downloading")
            .setDescription("The ${filename} is downloading...")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                filename
            )
        downloadManager.enqueue(req)
    }
}