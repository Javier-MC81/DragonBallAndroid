package com.jmoreno.dragonballandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class SecondActivityViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<SecondActivityViewModel.UiState>(SecondActivityViewModel.UiState.Idle)
    val uiState : StateFlow<SecondActivityViewModel.UiState> = _uiState

    fun downloadListOfHeroes(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/heros/all"
            val body = FormBody.Builder()
                .add("name", "")
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()

            response.body?.let { responseBody ->
                val gson = Gson()
                try {
                    val personajeDtoArray = gson.fromJson(responseBody.string(), Array<PersonajeDTO>::class.java)
                    _uiState.value = UiState.OnListReceived(personajeDtoArray.toList().map { PersonajeDTO(it.favorite,it.name,it.id,it.photo,it.description) })
                } catch(ex: Exception ) {
                    _uiState.value = UiState.Error("Something went wrong in the response")
                }
            } ?: run { _uiState.value = UiState.Error("Something went wrong in the request") }
        }
    }
    sealed class UiState {
        object Idle : UiState()
        data class Error(val error: String) : UiState()
        data class OnListReceived(val heroeList: List<PersonajeDTO>) : UiState()


    }
}