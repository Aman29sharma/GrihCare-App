package com.example.grihacare.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class ChatMessage(
    val role: String,
    val content: String
)

class ClaudeRepository {

    private val client =
        OkHttpClient.Builder()
            .connectTimeout(
                10,
                TimeUnit.SECONDS
            )
            .readTimeout(
                45,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                20,
                TimeUnit.SECONDS
            )
            .build()

    private val baseUrl =
        "http://10.0.2.2:5000"

    suspend fun sendMessage(
        message: String,
        history: List<ChatMessage>
    ): Result<String> =
        withContext(Dispatchers.IO) {

            try {

                val json =
                    JSONObject()

                json.put(
                    "message",
                    message
                )

                val historyArray =
                    JSONArray()

                history
                    .takeLast(10)
                    .forEach { item ->

                        val obj =
                            JSONObject()

                        obj.put(
                            "role",
                            item.role
                        )

                        obj.put(
                            "content",
                            item.content
                        )

                        historyArray.put(obj)
                    }

                json.put(
                    "history",
                    historyArray
                )

                val requestBody =
                    json.toString()
                        .toRequestBody(
                            "application/json"
                                .toMediaType()
                        )

                val request =
                    Request.Builder()
                        .url(
                            "$baseUrl/api/chat"
                        )
                        .post(
                            requestBody
                        )
                        .addHeader(
                            "Content-Type",
                            "application/json"
                        )
                        .build()

                client
                    .newCall(request)
                    .execute()
                    .use { response ->

                        val text =
                            response.body
                                ?.string()
                                .orEmpty()

                        if (
                            !response.isSuccessful
                        ) {

                            return@withContext Result
                                .failure(
                                    Exception(
                                        "HTTP ${response.code}"
                                    )
                                )
                        }

                        val result =
                            JSONObject(text)

                        if (
                            result.optBoolean(
                                "success"
                            )
                        ) {

                            Result.success(
                                result.optString(
                                    "reply"
                                )
                            )

                        } else {

                            Result.failure(
                                Exception(
                                    result.optString(
                                        "message",
                                        "AI failed"
                                    )
                                )
                            )
                        }
                    }

            } catch (e: Exception) {

                Result.success(
                    localFallback(
                        message
                    )
                )
            }
        }

    private fun localFallback(
        message: String
    ): String {

        val text =
            message.lowercase()

        return when {

            text.contains("pg") &&
                    text.contains("pune") -> {

                """
                🏠 Best match: WorkNest PG

                📍 Pune - Hinjawadi
                💰 ₹9,500/month
                ⭐ AI Match: 98%
                🍽 Breakfast + Dinner
                📶 Wi-Fi
                🅿️ Parking

                This fits a professional looking
                for an affordable monthly PG.

                Want single or double occupancy?
                """.trimIndent()
            }

            text.contains("pg") &&
                    text.contains("gwalior") -> {

                """
                🏠 Student Hub PG

                📍 Gwalior - University Area
                💰 ₹7,000/month
                🍽 Breakfast + Dinner
                📶 Wi-Fi
                🅿️ Parking
                ⭐ AI Match: 97%

                Good fit for students looking
                for a budget-friendly stay.
                """.trimIndent()
            }

            text.contains("maid") -> {

                """
                👩 Priya Sharma

                Professional Maid
                📍 Andheri
                💰 ₹8,000/month
                ⭐ 4.9/5
                🛡 TrustScore: 94/100
                💼 6 years experience
                ✓ Demo KYC verified

                Complete the Demo KYC flow before hiring.
                """.trimIndent()
            }

            text.contains("cook") -> {

                """
                🍳 Anita Verma

                Home Cook
                📍 Powai
                💰 ₹9,500/month
                ⭐ 4.8/5
                🛡 TrustScore: 91/100
                💼 5 years experience
                ✓ Demo KYC verified
                """.trimIndent()
            }

            text.contains("driver") -> {

                """
                🚗 Raj Kumar

                Driver
                📍 Bandra
                💰 ₹18,000/month
                ⭐ 4.9/5
                🛡 TrustScore: 96/100
                💼 8 years experience
                ✓ Demo KYC verified
                """.trimIndent()
            }

            text.contains("room") -> {

                """
                🛏 Cozy Private Room

                📍 Gwalior - City Centre
                💰 ₹8,500/month
                ✨ Furnished
                📶 Wi-Fi
                🅿️ Parking
                🚿 Attached bathroom
                ⭐ AI Match: 95%

                Tell me your city and budget
                for a better match.
                """.trimIndent()
            }

            else -> {

                """
                Hi 👋 I'm GrihaCare AI.

                I can help with:

                🏠 PGs
                🛏 Private Rooms
                🏢 1RKs
                👩 Maids
                🍳 Home Cooks
                🚗 Drivers
                ⚡ Electricians
                🔧 Plumbers
                🛡 TrustScore

                Try:
                "PG under ₹10k in Pune"
                """.trimIndent()
            }
        }
    }
}