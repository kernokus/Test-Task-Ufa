package com.example.second_portfolio_proj.module



import android.content.Context
import androidx.room.Room
import com.example.second_portfolio_proj.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule (_context:Context) {
    private var context=_context


    @Singleton
    @Provides
    fun provideRoomDatabase(ctx:Context): AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java, "db").build()
    }

    @Provides
    fun providesUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
}