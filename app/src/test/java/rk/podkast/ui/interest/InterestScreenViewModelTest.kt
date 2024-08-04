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
import rk.podkast.data.database.entity.Genre
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
    fun interestViewModel_InitialState_emptyInterestListAndAllOfGenres() {
        val expected = InterestScreenUiState(fakeRepo.getAllGenres(), listOf())
        val result = viewModel.uiState.value
        assertEquals(expected, result)
    }

    @Test
    fun interestViewModel_TogglingOnGivenGenre_TheGenreShouldToggledOn() {
        val expected = InterestScreenUiState(
            fakeRepo.getAllGenres(),
            listOf(Genre(GenreType.PODCASTSERIES_ARTS))
        ).interestGenres
        viewModel.toggleGenre(GenreType.PODCASTSERIES_ARTS)
        assertEquals(expected, viewModel.uiState.value.interestGenres)
    }

    @Test
    fun interestViewModel_TogglingOffGivenGenre_TheGenreShouldToggledOff() {
        val interestedGenres = listOf(
            Genre(GenreType.PODCASTSERIES_ARTS), Genre(GenreType.PODCASTSERIES_GOVERNMENT)
        )
        fakeRepo.addGenresToInterestForTest(interestedGenres)
        // expect to toggled of art `GenreType.PODCASTSERIES_ARTS`
        val expected = InterestScreenUiState(
            fakeRepo.getAllGenres(),
            listOf(Genre(GenreType.PODCASTSERIES_GOVERNMENT))
        ).interestGenres
        viewModel.toggleGenre(GenreType.PODCASTSERIES_ARTS)
        assertEquals(expected, viewModel.uiState.value.interestGenres)
    }
}