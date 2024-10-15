package com.jpatrick.mystore.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jpatrick.mystore.data.model.Entity.TokenEntity
import com.jpatrick.mystore.data.model.dao.TokenDao

@Database(entities = [TokenEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Abstract method để cung cấp DAO
    abstract fun tokenDao(): TokenDao

    companion object {
        // Singleton để đảm bảo chỉ có một instance của database tồn tại
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Nếu instance đã tồn tại, trả về instance đó
            return INSTANCE ?: synchronized(this) {
                // Nếu chưa có instance nào, tạo mới database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Phục hồi cơ sở dữ liệu nếu cấu trúc thay đổi
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}