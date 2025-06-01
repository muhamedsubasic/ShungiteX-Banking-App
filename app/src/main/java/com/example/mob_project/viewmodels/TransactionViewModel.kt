package com.example.mob_project.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mob_project.model.Transaction
import com.example.mob_project.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactions = mutableStateOf<List<Transaction>>(emptyList())
    val transactions: State<List<Transaction>> = _transactions

    init {
        repository.getAllTransactions()
            .onEach { list ->
                _transactions.value = list
            }
            .launchIn(viewModelScope)
    }
}
