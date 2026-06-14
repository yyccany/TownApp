package com.example.townapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.data.database.TownDatabase
import com.example.townapp.data.database.entity.AppConfigEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModeViewModel(private val database: TownDatabase) : ViewModel() {
    private val _isChildMode = MutableStateFlow(true)
    val isChildMode: StateFlow<Boolean> = _isChildMode.asStateFlow()

    private val _defaultRole = MutableStateFlow("taffi")
    val defaultRole: StateFlow<String> = _defaultRole.asStateFlow()

    private val _eventFrequency = MutableStateFlow(2)
    val eventFrequency: StateFlow<Int> = _eventFrequency.asStateFlow()

    init {
        loadConfig()
    }

    private fun loadConfig() {
        viewModelScope.launch {
            val config = withContext(Dispatchers.IO) {
                database.appConfigDao().getLatest()
            }
            config?.let {
                _isChildMode.value = it.isChildMode
                _defaultRole.value = it.defaultRole
                _eventFrequency.value = it.eventFrequency
            }
        }
    }

    fun toggleMode() {
        _isChildMode.value = !_isChildMode.value
        saveConfig()
    }

    fun setChildMode(isChild: Boolean) {
        _isChildMode.value = isChild
        saveConfig()
    }

    fun setDefaultRole(roleId: String) {
        _defaultRole.value = roleId
        saveConfig()
    }

    fun setEventFrequency(frequency: Int) {
        _eventFrequency.value = frequency.coerceIn(1, 3)
        saveConfig()
    }

    private fun saveConfig() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val existing = database.appConfigDao().getLatest()
                val config = AppConfigEntity(
                    id = existing?.id ?: 0,
                    isChildMode = _isChildMode.value,
                    defaultRole = _defaultRole.value,
                    eventFrequency = _eventFrequency.value,
                    viewType = existing?.viewType ?: 1
                )
                if (existing != null) {
                    database.appConfigDao().update(config)
                } else {
                    database.appConfigDao().insert(config)
                }
            }
        }
    }
}