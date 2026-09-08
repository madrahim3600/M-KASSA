package com.mkassa.app.di

import com.mkassa.app.data.local.dao.*
import com.mkassa.app.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository =
        UserRepository(userDao)

    @Singleton
    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository =
        CategoryRepository(categoryDao)

    @Singleton
    @Provides
    fun provideProductRepository(productDao: ProductDao): ProductRepository =
        ProductRepository(productDao)

    @Singleton
    @Provides
    fun provideTableRepository(tableDao: TableDao): TableRepository =
        TableRepository(tableDao)

    @Singleton
    @Provides
    fun provideOrderRepository(
        orderDao: OrderDao,
        orderItemDao: OrderItemDao
    ): OrderRepository = OrderRepository(orderDao, orderItemDao)

    @Singleton
    @Provides
    fun provideEmployeeRepository(employeeDao: EmployeeDao): EmployeeRepository =
        EmployeeRepository(employeeDao)
}
