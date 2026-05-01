package com.example.letssopt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class MainUiState(
    val selectedTabIndex: Int = 0
)

class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set

    fun selectTab(index: Int) {
        uiState = uiState.copy(selectedTabIndex = index)
    }
}
