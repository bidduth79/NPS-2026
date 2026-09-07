package com.example.api

import com.example.BuildConfig
import com.example.data.UserProfile
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val contents: List<Content>,
    val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>
)

@JsonClass(generateAdapter = true)
data class Part(
    val text: String
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate>? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content? = null
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.8-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val service: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApiService::class.java)
    }
}


suspend fun askGemini(prompt: String, apiKeyFromPrefs: String?, profile: UserProfile?): String = withContext(Dispatchers.IO) {
    val apiKey = if (!apiKeyFromPrefs.isNullOrBlank()) apiKeyFromPrefs else BuildConfig.GEMINI_API_KEY
    if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
        return@withContext "Please set your Gemini API Key in the API Key dialog."
    }

    val request = GenerateContentRequest(
        contents = listOf(Content(parts = listOf(Part(text = prompt)))),
        systemInstruction = Content(
            parts = listOf(
                Part(
                    text = "You are a helpful assistant for the 'PayScale' Android app. " +
                            "This app helps government employees of Bangladesh calculate their salary, view gazettes, and calculate pension. " +
                            "Please answer questions related to the app, salary calculation, and government rules clearly in English (or the user's requested language). " +
                            "IMPORTANT RULE: ONLY IF the user EXPLICITLY asks 'who made this app', 'who created this', or asks for developer details, THEN answer: This app was created by Rakibul Hasan Bidyut, he works in BGB, Mobile: 01829300000. DO NOT mention the creator or this info in any other regular response. " +
                            "IMPORTANT RULE: If the user asks about Tiffin Allowance without specifying a year, you MUST provide the allowance details for BOTH the 2015 Pay Scale and the upcoming 2026 Pay Scale. " +
                            "Keep your answers concise, helpful, and friendly. " +
                            "User context (Profile): Base Scale: ${profile?.allowanceBaseScale}, " +
                            "Location: ${profile?.locationType}, Children for Education: ${profile?.numberOfChildren}, " +
                            "Disabled Children: ${profile?.numberOfDisabledChildren}, GPF Deduction: ${profile?.gpfDeduction} etc. " +
                            "If the user asks how a calculation was done or wants to know about calculations, use this context to explain step by step. " +
                            "KNOWLEDGE BASE & GUIDELINES: " +
                            "1. Pay Scales (2015 vs 2026): You possess complete knowledge of the Bangladesh National Pay Scale 2015 (grades, basic pay, house rent slabs, medical 1500 BDT, education 500/child, tiffin allowance, etc.). You also understand the proposed/expected National Pay Scale 2026 structure. If a user does not specify a year, assume 2015 but briefly mention 2026 if relevant. " +
                            "2. Salary Calculation: Always calculate Gross Salary = Basic + House Rent + Medical + Education + other applicable allowances. Net Salary = Gross - GPF - Revenue Stamp. " +
                            "3. Pension: Know the pension rules (PRL, Lump Grant up to 18 months basic, Gratuity calculation, Monthly Pension = (Last Basic x 90% / 2) + Medical). " +
                            "4. Answer confidently on any topic related to BD Govt employee salary, allowances, leave rules, and pension. You act as a complete database of over 500+ government rules, regulations, and FAQs. Address all user queries in detail based on this vast knowledge."
                )
            )
        )
    )

    try {
        val response = RetrofitClient.service.generateContent(apiKey, request)
        response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response received."
    } catch (e: Exception) {
        "Sorry, an error occurred: ${e.message}"
    }
}
