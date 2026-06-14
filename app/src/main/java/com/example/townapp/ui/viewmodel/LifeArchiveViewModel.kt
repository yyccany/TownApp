package com.example.townapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.data.database.TownDatabase
import com.example.townapp.data.database.entity.LifeArchiveEntity
import com.example.townapp.data.repository.LifeArchiveRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 人生存档 ViewModel
 */
class LifeArchiveViewModel(application: Application) : AndroidViewModel(application) {

    private val database = TownDatabase.getDatabase(application)
    private val repository = LifeArchiveRepository(database.lifeArchiveDao())

    private val _archives = MutableStateFlow<List<LifeArchiveEntity>>(emptyList())
    val archives: StateFlow<List<LifeArchiveEntity>> = _archives.asStateFlow()

    private val _archiveCount = MutableStateFlow(0)
    val archiveCount: StateFlow<Int> = _archiveCount.asStateFlow()

    init {
        loadArchives()
        loadCount()
    }

    private fun loadArchives() {
        viewModelScope.launch {
            repository.getAllArchives().collect { archives ->
                _archives.value = archives
            }
        }
    }

    private fun loadCount() {
        viewModelScope.launch {
            repository.getArchiveCount().collect { count ->
                _archiveCount.value = count
            }
        }
    }

    fun createArchive(content: String, emoji: String?, mood: String?) {
        viewModelScope.launch {
            repository.createArchive(content, emoji, mood)
        }
    }

    fun updateArchive(id: Long, content: String, emoji: String?, mood: String?) {
        viewModelScope.launch {
            repository.updateArchive(id, content, emoji, mood)
        }
    }

    fun deleteArchive(id: Long) {
        viewModelScope.launch {
            repository.deleteArchive(id)
        }
    }
}
