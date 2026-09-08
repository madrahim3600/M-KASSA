package com.mkassa.app.data.local.dao

import androidx.room.*
import com.mkassa.app.data.local.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity): Long

    @Update
    suspend fun updateReport(report: ReportEntity)

    @Query("SELECT * FROM daily_reports WHERE reportDate = :date")
    fun getReportByDate(date: Long): Flow<ReportEntity?>

    @Query("SELECT * FROM daily_reports ORDER BY reportDate DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    @Query("SELECT * FROM daily_reports WHERE reportDate BETWEEN :startDate AND :endDate ORDER BY reportDate DESC")
    fun getReportsByDateRange(startDate: Long, endDate: Long): Flow<List<ReportEntity>>
}
