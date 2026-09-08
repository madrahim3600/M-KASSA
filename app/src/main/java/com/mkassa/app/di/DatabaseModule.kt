package com.mkassa.app.di

import android.content.Context
import androidx.room.Room
import com.mkassa.app.data.local.MKassaDatabase
import com.mkassa.app.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMKassaDatabase(
        @ApplicationContext context: Context
    ): MKassaDatabase {
        return Room.databaseBuilder(
            context,
            MKassaDatabase::class.java,
            "mkassa_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: MKassaDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun provideCategoryDao(database: MKassaDatabase): CategoryDao = database.categoryDao()

    @Singleton
    @Provides
    fun provideProductDao(database: MKassaDatabase): ProductDao = database.productDao()

    @Singleton
    @Provides
    fun provideTableDao(database: MKassaDatabase): TableDao = database.tableDao()

    @Singleton
    @Provides
    fun provideOrderDao(database: MKassaDatabase): OrderDao = database.orderDao()

    @Singleton
    @Provides
    fun provideOrderItemDao(database: MKassaDatabase): OrderItemDao = database.orderItemDao()

    @Singleton
    @Provides
    fun provideEmployeeDao(database: MKassaDatabase): EmployeeDao = database.employeeDao()

    @Singleton
    @Provides
    fun provideProductStockDao(database: MKassaDatabase): ProductStockDao = database.productStockDao()

    @Singleton
    @Provides
    fun provideTransactionDao(database: MKassaDatabase): TransactionDao = database.transactionDao()

    @Singleton
    @Provides
    fun provideReportDao(database: MKassaDatabase): ReportDao = database.reportDao()

    @Singleton
    @Provides
    fun provideReceiptDao(database: MKassaDatabase): ReceiptDao = database.receiptDao()

    @Singleton
    @Provides
    fun provideSettingsDao(database: MKassaDatabase): SettingsDao = database.settingsDao()
}
