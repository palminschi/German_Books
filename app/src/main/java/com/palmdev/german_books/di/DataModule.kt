package com.palmdev.german_books.di

import android.content.Context
import androidx.room.Room
import com.palmdev.german_books.data.database.BooksDao
import com.palmdev.german_books.data.database.BooksDatabase
import com.palmdev.german_books.data.database.ReadBooksDao
import com.palmdev.german_books.data.database.ReadBooksDatabase
import com.palmdev.german_books.data.database.SavedVideoDao
import com.palmdev.german_books.data.database.SavedVideoDatabase
import com.palmdev.german_books.data.database.SavedWordsDao
import com.palmdev.german_books.data.database.SavedWordsDatabase
import com.palmdev.german_books.data.database.VideoInfoDao
import com.palmdev.german_books.data.database.VideoInfoDatabase
import com.palmdev.german_books.data.database.WordsCategoriesDao
import com.palmdev.german_books.data.database.WordsCategoriesDatabase
import com.palmdev.german_books.data.database.WordsDao
import com.palmdev.german_books.data.database.WordsDatabase
import com.palmdev.german_books.data.network.SubtitlesFirebaseStorage
import com.palmdev.german_books.data.repository.BooksRepositoryImpl
import com.palmdev.german_books.data.repository.SavedVideoRepositoryImpl
import com.palmdev.german_books.data.repository.SavedWordsRepositoryImpl
import com.palmdev.german_books.data.repository.UserRepositoryImpl
import com.palmdev.german_books.data.repository.VideoRepositoryImpl
import com.palmdev.german_books.data.repository.WordsCategoriesRepositoryImpl
import com.palmdev.german_books.data.repository.WordsRepositoryImpl
import com.palmdev.german_books.data.storage.BooksStorage
import com.palmdev.german_books.data.storage.DailyStreakManager
import com.palmdev.german_books.data.storage.ToDoTasksStorage
import com.palmdev.german_books.data.storage.UserStorage
import com.palmdev.german_books.data.storage.VideoWatchedManager
import com.palmdev.german_books.domain.repository.BooksRepository
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.repository.VideoRepository
import com.palmdev.german_books.domain.repository.WordsCategoriesRepository
import com.palmdev.german_books.domain.repository.WordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDailyStreakManager(@ApplicationContext context: Context): DailyStreakManager {
        return DailyStreakManager(context)
    }

    @Singleton
    @Provides
    fun provideToDoTasksStorage(@ApplicationContext context: Context): ToDoTasksStorage {
        return ToDoTasksStorage(context)
    }

    @Singleton
    @Provides
    fun provideUserStorage(@ApplicationContext context: Context): UserStorage {
        return UserStorage(context)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userStorage: UserStorage,
        videosWatchedManager: VideoWatchedManager,
        dailyStreakManager: DailyStreakManager
    ): UserRepository {
        return UserRepositoryImpl(userStorage, videosWatchedManager, dailyStreakManager)
    }

    @Singleton
    @Provides
    fun provideVideosWatched(@ApplicationContext context: Context): VideoWatchedManager {
        return VideoWatchedManager(context)
    }

    @Singleton
    @Provides
    fun provideSavedWordsDatabase(@ApplicationContext context: Context): SavedWordsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SavedWordsDatabase::class.java,
            "saved_words_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSavedWordsDao(database: SavedWordsDatabase): SavedWordsDao {
        return database.getSavedWordsDao()
    }

    @Singleton
    @Provides
    fun provideSavedVideoDatabase(@ApplicationContext context: Context): SavedVideoDatabase {
        return Room.databaseBuilder(
            context,
            SavedVideoDatabase::class.java,
            "saved_video_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSavedVideoDao(database: SavedVideoDatabase): SavedVideoDao {
        return database.getDao()
    }

    @Singleton
    @Provides
    fun provideVideoInfoDatabase(@ApplicationContext context: Context): VideoInfoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            VideoInfoDatabase::class.java,
            "video_database"
        )
            .fallbackToDestructiveMigration()
            .createFromAsset("video_database.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideVideoInfoDao(database: VideoInfoDatabase): VideoInfoDao {
        return database.getDao()
    }

    @Singleton
    @Provides
    fun provideWordsDatabase(@ApplicationContext context: Context): WordsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WordsDatabase::class.java,
            "new_words_database"
        )
            .fallbackToDestructiveMigration()
            .createFromAsset("words_database.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideWordsDao(database: WordsDatabase): WordsDao {
        return database.getDao()
    }

    @Singleton
    @Provides
    fun provideWordsCategoriesDatabase(@ApplicationContext context: Context): WordsCategoriesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WordsCategoriesDatabase::class.java,
            "words_categories_database"
        )
            .createFromAsset("words_categories_database.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideWordsCategoriesDao(database: WordsCategoriesDatabase): WordsCategoriesDao {
        return database.getDao()
    }

    @Singleton
    @Provides
    fun provideSavedWordsRepository(savedWordsDao: SavedWordsDao): SavedWordsRepository {
        return SavedWordsRepositoryImpl(savedWordsDao)
    }

    @Singleton
    @Provides
    fun provideVideoRepository(
        subtitlesFirebaseStorage: SubtitlesFirebaseStorage,
        videoInfoDao: VideoInfoDao
    ): VideoRepository {
        return VideoRepositoryImpl(subtitlesFirebaseStorage, videoInfoDao)
    }

    @Singleton
    @Provides
    fun provideSavedVideoRepository(savedVideoDao: SavedVideoDao): SavedVideoRepository {
        return SavedVideoRepositoryImpl(savedVideoDao)
    }

    @Singleton
    @Provides
    fun provideWordsRepository(
        wordsDao: WordsDao,
        userRepository: UserRepository
    ): WordsRepository {
        return WordsRepositoryImpl(wordsDao, userRepository)
    }

    @Singleton
    @Provides
    fun provideWordsCategoriesRepository(
        wordsCategoriesDao: WordsCategoriesDao,
        @ApplicationContext context: Context,
    ): WordsCategoriesRepository {
        return WordsCategoriesRepositoryImpl(wordsCategoriesDao, context)
    }

    @Singleton
    @Provides
    fun provideBooksDao(database: BooksDatabase): BooksDao {
        return database.getBooksDao()
    }

    @Singleton
    @Provides
    fun provideBooksDatabase(@ApplicationContext context: Context): BooksDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BooksDatabase::class.java,
            "books_database"
        )
            .fallbackToDestructiveMigration()
            .createFromAsset("books_database.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideReadBooksDao(database: ReadBooksDatabase): ReadBooksDao {
        return database.getDao()
    }

    @Singleton
    @Provides
    fun provideReadBooksDatabase(@ApplicationContext context: Context): ReadBooksDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ReadBooksDatabase::class.java,
            "read_books_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideBooksStorage(@ApplicationContext context: Context): BooksStorage {
        return BooksStorage(context)
    }

    @Singleton
    @Provides
    fun provideBooksRepository(
        booksDao: BooksDao,
        readBooksDao: ReadBooksDao,
        booksStorage: BooksStorage
    ): BooksRepository {
        return BooksRepositoryImpl(booksDao, readBooksDao, booksStorage)
    }

}