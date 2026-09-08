package com.mkassa.app.data.repository

import com.mkassa.app.data.local.dao.CategoryDao
import com.mkassa.app.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    fun getAllActiveCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllActiveCategories()

    fun getCategoryById(id: Int): Flow<CategoryEntity?> = categoryDao.getCategoryById(id)

    suspend fun createCategory(category: CategoryEntity): Long = categoryDao.insertCategory(category)

    suspend fun updateCategory(category: CategoryEntity) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.deleteCategory(category)

    suspend fun deleteCategoryById(id: Int) = categoryDao.deleteCategoryById(id)
}
