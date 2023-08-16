package com.palmdev.german_books.di

import android.content.Context
import com.palmdev.german_books.data.storage.ToDoTasksStorage
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.repository.VideoRepository
import com.palmdev.german_books.domain.usecases.CheckSubscriptionUseCase
import com.palmdev.german_books.domain.usecases.video.GetVideoByIdUseCase
import com.palmdev.german_books.domain.usecases.video.UpdateVideoIsFavoriteUseCase
import com.palmdev.german_books.domain.usecases.words.GetSavedWordsCountUseCase
import com.palmdev.german_books.domain.usecases.words.GetWordNextTimeRepetitionUseCase
import com.palmdev.german_books.domain.usecases.words.SaveNewWordsUseCase
import com.palmdev.german_books.domain.usecases.words.UpdateSavedWordTimeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideCheckSubscription(
        @ApplicationContext context: Context,
        userRepository: UserRepository
    ): CheckSubscriptionUseCase {
        return CheckSubscriptionUseCase(context, userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveNewWordsUseCase(
        savedWordsRepository: SavedWordsRepository,
        toDoTasksStorage: ToDoTasksStorage
    ): SaveNewWordsUseCase {
        return SaveNewWordsUseCase(savedWordsRepository, toDoTasksStorage)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateSavedWordUseCase(savedWordsRepository: SavedWordsRepository): UpdateSavedWordTimeUseCase {
        return UpdateSavedWordTimeUseCase(savedWordsRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSavedWordsCountUseCase(savedWordsRepository: SavedWordsRepository): GetSavedWordsCountUseCase {
        return GetSavedWordsCountUseCase(savedWordsRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetWordNextRepetitionTimeUseCase(): GetWordNextTimeRepetitionUseCase {
        return GetWordNextTimeRepetitionUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideGetVideoByIdUseCase(
        savedVideoRepository: SavedVideoRepository,
        videoRepository: VideoRepository,
        userRepository: UserRepository
    ): GetVideoByIdUseCase {
        return GetVideoByIdUseCase(savedVideoRepository, videoRepository, userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateVideoIsFavoriteUseCase(savedVideoRepository: SavedVideoRepository): UpdateVideoIsFavoriteUseCase {
        return UpdateVideoIsFavoriteUseCase(savedVideoRepository)
    }
}