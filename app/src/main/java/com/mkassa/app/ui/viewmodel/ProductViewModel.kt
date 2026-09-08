package com.mkassa.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkassa.app.data.local.entity.ProductEntity
import com.mkassa.app.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products: StateFlow<List<ProductEntity>> = _products.asStateFlow()

    private val _lowStockProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val lowStockProducts: StateFlow<List<ProductEntity>> = _lowStockProducts.asStateFlow()

    private val _createProductState = MutableStateFlow<CreateProductState>(CreateProductState.Idle)
    val createProductState: StateFlow<CreateProductState> = _createProductState

    init {
        loadProducts()
        loadLowStockProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            productRepository.getAllActiveProducts().collect { products ->
                _products.value = products
            }
        }
    }

    fun loadLowStockProducts() {
        viewModelScope.launch {
            productRepository.getLowStockProducts().collect { products ->
                _lowStockProducts.value = products
            }
        }
    }

    fun createProduct(
        categoryId: Int,
        name: String,
        costPrice: Double,
        sellingPrice: Double,
        unit: String,
        quantity: Double,
        description: String = ""
    ) {
        viewModelScope.launch {
            _createProductState.value = CreateProductState.Loading
            try {
                val product = ProductEntity(
                    categoryId = categoryId,
                    name = name,
                    costPrice = costPrice,
                    sellingPrice = sellingPrice,
                    unit = unit,
                    quantity = quantity,
                    description = description
                )
                productRepository.createProduct(product)
                _createProductState.value = CreateProductState.Success
                loadProducts()
            } catch (e: Exception) {
                _createProductState.value = CreateProductState.Error(e.message ?: "Xato sodir bo'ldi")
            }
        }
    }

    fun updateProduct(product: ProductEntity) {
        viewModelScope.launch {
            try {
                productRepository.updateProduct(product)
                loadProducts()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            try {
                productRepository.deleteProductById(productId)
                loadProducts()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

sealed class CreateProductState {
    object Idle : CreateProductState()
    object Loading : CreateProductState()
    object Success : CreateProductState()
    data class Error(val message: String) : CreateProductState()
}
