package com.mkassa.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkassa.app.data.local.entity.CategoryEntity
import com.mkassa.app.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories.asStateFlow()

    private val _createCategoryState = MutableStateFlow<CreateCategoryState>(CreateCategoryState.Idle)
    val createCategoryState: StateFlow<CreateCategoryState> = _createCategoryState

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllActiveCategories().collect { categories ->
                _categories.value = categories
            }
        }
    }

    fun createCategory(name: String, description: String = "", icon: String = "") {
        viewModelScope.launch {
            _createCategoryState.value = CreateCategoryState.Loading
            try {
                val category = CategoryEntity(
                    name = name,
                    description = description,
                    icon = icon
                )
                categoryRepository.createCategory(category)
                _createCategoryState.value = CreateCategoryState.Success
                loadCategories()
            } catch (e: Exception) {
                _createCategoryState.value = CreateCategoryState.Error(e.message ?: "Xato sodir bo'ldi")
            }
        }
    }

    fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            try {
                categoryRepository.updateCategory(category)
                loadCategories()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                categoryRepository.deleteCategoryById(categoryId)
                loadCategories()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

sealed class CreateCategoryState {
    object Idle : CreateCategoryState()
    object Loading : CreateCategoryState()
    object Success : CreateCategoryState()
    data class Error(val message: String) : CreateCategoryState()
}
