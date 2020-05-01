package com.bekarys.tech_assignment.thecats.features.common.network

import com.bekarys.tech_assignment.thecats.features.common.exceptions.customexceptions.ConnectionException
import com.bekarys.tech_assignment.thecats.features.common.exceptions.customexceptions.EmptyResponseBodyException
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import okhttp3.ResponseBody
import java.io.IOException

typealias RetrofitCall<T> = retrofit2.Call<T>
typealias OkHttpCall = okhttp3.Call

private const val EMPTY_JSON: String = "{}"

class RequestManager(
    private val connectionManager: NetworkStateManager
) {

    fun <T> execute(
        executable: () -> RetrofitCall<T>
    ): ResponseData<T> {
        if (!connectionManager.isOnline())
            return ResponseData.Fail(ConnectionException.NoConnection)

        try {
            val response = executable().execute()
            if (!response.isSuccessful) {

                return ResponseData.Fail(
                    IllegalStateException()
                )
            }

            val data = response.body() ?: return ResponseData.Fail(
                EmptyResponseBodyException(
                    ""
                )
            )
            return ResponseData.Success(
                data
            )
        } catch (ex: IOException) {
            return ResponseData.Fail(ex)
        }
    }

    fun executeRaw(
        executable: () -> OkHttpCall
    ): ResponseData<ResponseBody> {
        if (!connectionManager.isOnline())
            return ResponseData.Fail(ConnectionException.NoConnection)

        try {
            val response = executable().execute()
            if (!response.isSuccessful) {

                return ResponseData.Fail(
                    IllegalStateException()
                )
            }

            val data = response.body() ?: return ResponseData.Fail(
                EmptyResponseBodyException(
                    ""
                )
            )
            return ResponseData.Success(
                data
            )
        } catch (ex: Exception) {

            return ResponseData.Fail(ex)
        }
    }
}
