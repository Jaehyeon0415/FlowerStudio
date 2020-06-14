package com.jaehyeon.flowerstudio.controller

import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


// 네이버 기계번역 (Papago SMT) API 예제
object TranslationAPI {
    @JvmStatic
    fun translation(context: String) : String {
        val clientId = "tI2gWZCF7wz5BKASQJcF" //애플리케이션 클라이언트 아이디값";
        val clientSecret = "ZgUyfVWPlM" //애플리케이션 클라이언트 시크릿값";
        val apiURL = "https://openapi.naver.com/v1/papago/n2mt"
        val text: String
        text = try {
            URLEncoder.encode(context, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("인코딩 실패", e)
        }
        val requestHeaders: MutableMap<String, String> = HashMap()
        requestHeaders["X-Naver-Client-Id"] = clientId
        requestHeaders["X-Naver-Client-Secret"] = clientSecret
        val responseBody = post(apiURL, requestHeaders, text)
        println(responseBody)
        return try{
            val obj = JSONObject(JSONObject(JSONObject(responseBody).getString("message")).getString("result")).getString("translatedText")
            obj
        } catch (e: Exception) {
            println(e)
            "null"
        }
    }

    private fun post(
        apiUrl: String,
        requestHeaders: Map<String, String>,
        text: String
    ): String {
        val con = connect(apiUrl)
        val postParams =
            "source=en&target=ko&text=$text" // 원본언어: 영어 (en) -> 목적언어: 한국어 (ko)
        return try {
            con.requestMethod = "POST"
            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }
            con.doOutput = true
            DataOutputStream(con.outputStream).use { wr ->
                wr.write(postParams.toByteArray())
                wr.flush()
            }
            val responseCode = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                readBody(con.inputStream)
            } else {  // 에러 응답
                readBody(con.errorStream)
            }
        } catch (e: IOException) {
            throw RuntimeException("API 요청과 응답 실패", e)
        } finally {
            con.disconnect()
        }
    }

    private fun connect(apiUrl: String): HttpURLConnection {
        return try {
            val url = URL(apiUrl)
            url.openConnection() as HttpURLConnection
        } catch (e: MalformedURLException) {
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl", e)
        } catch (e: IOException) {
            throw RuntimeException("연결이 실패했습니다. : $apiUrl", e)
        }
    }

    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)
        try {
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()
                var line: String?
                while (lineReader.readLine().also { line = it } != null) {
                    responseBody.append(line)
                }
                return responseBody.toString()
            }
        } catch (e: IOException) {
            throw RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }
    }
}