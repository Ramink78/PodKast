package rk.podkast.ui.interest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import rk.podkast.data.GenreType
import rk.podkast.data.database.entity.GenreEntity
import rk.podkast.data.repository.FakeGenreRepository

@OptIn(ExperimentalCoroutinesApi::class)
class InterestScreenViewModelTest {
    lateinit var viewModel: InterestScreenViewModel
    lateinit var fakeRepo: FakeGenreRepository

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        fakeRepo = FakeGenreRepository()
        viewModel = InterestScreenViewModel(fakeRepo)
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun interestViewModel_InitialState_emptyInterestListAndAllNotInterestedGenres() {
        val expected = InterestScreenUiState(
            interestGenreEntities = emptyList(),
            notInterestedGenreEntities = fakeRepo.getAllGenres()
        )
        val result = viewModel.uiState.value
        assertEquals(expected, result)
    }

    @Test
    fun interestViewModel_interestAGenre_RemoveFromInterestedAndAddToNotInterested() {
        val genreEntityToInterest = GenreEntity(GenreType.PODCASTSERIES_GOVERNMENT)
        viewModel.interest(genreEntityToInterest)
        val expected = InterestScreenUiState(
            interestGenreEntities = listOf(genreEntityToInterest),
            notInterestedGenreEntities = fakeRepo.getAllGenres().filterNot { it == genreEntityToInterest }
        )
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun notInterestAGenre_TheGenreRemoveFromNotInterestedAndAddToInterested() {
        val genreEntityToNotInterest = GenreEntity(GenreType.PODCASTSERIES_GOVERNMENT)
        val interestedGenreEntities = listOf(genreEntityToNotInterest, GenreEntity(GenreType.PODCASTSERIES_ARTS))
        fakeRepo.addGenresToInterestForTest(interestedGenreEntities)
        viewModel.notInterest(genreEntityToNotInterest)
        val expected = InterestScreenUiState(
            interestGenreEntities = listOf(GenreEntity(GenreType.PODCASTSERIES_ARTS)),
            notInterestedGenreEntities = fakeRepo.getAllGenres()
        )
        assert(!expected.interestGenreEntities.contains(genreEntityToNotInterest))
        assert(expected.notInterestedGenreEntities.contains(genreEntityToNotInterest))
    }
}