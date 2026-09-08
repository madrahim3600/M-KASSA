package com.mkassa.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkassa.app.data.local.dao.*
import com.mkassa.app.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        CategoryEntity::class,
        ProductEntity::class,
        TableEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        EmployeeEntity::class,
        ProductStockEntity::class,
        TransactionEntity::class,
        ReportEntity::class,
        ReceiptEntity::class,
        SettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MKassaDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun tableDao(): TableDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun productStockDao(): ProductStockDao
    abstract fun transactionDao(): TransactionDao
    abstract fun reportDao(): ReportDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun settingsDao(): SettingsDao
}
