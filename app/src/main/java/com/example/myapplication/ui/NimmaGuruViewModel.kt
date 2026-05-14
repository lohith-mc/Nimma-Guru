package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Appreciation
import com.example.myapplication.data.Guru
import com.example.myapplication.data.GuruRepository
import com.example.myapplication.data.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NimmaGuruViewModel : ViewModel() {
    private val repository = GuruRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedSkill = MutableStateFlow<String?>(null)
    val selectedSkill: StateFlow<String?> = _selectedSkill

    val gurus: StateFlow<List<Guru>> = repository.getGurus()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filteredGurus: StateFlow<List<Guru>> = combine(gurus, _searchQuery, _selectedSkill) { gurus, query, skill ->
        gurus.filter { guru ->
            (guru.village.contains(query, ignoreCase = true) || guru.name.contains(query, ignoreCase = true)) &&
            (skill == null || guru.skills.contains(skill))
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val topGurus: StateFlow<List<Guru>> = gurus.combine(MutableStateFlow(Unit)) { gurus, _ ->
        gurus.sortedByDescending { it.thankYouCount }.take(5)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val sessions: StateFlow<List<Session>> = repository.getSessions()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSkillSelected(skill: String?) {
        _selectedSkill.value = if (_selectedSkill.value == skill) null else skill
    }

    fun saveGuruProfile(guru: Guru) {
        viewModelScope.launch {
            repository.addGuru(guru)
        }
    }

    fun postAppreciation(appreciation: Appreciation) {
        viewModelScope.launch {
            repository.postAppreciation(appreciation)
        }
    }

    fun getAppreciations(guruId: String) = repository.getAppreciations(guruId)
}
