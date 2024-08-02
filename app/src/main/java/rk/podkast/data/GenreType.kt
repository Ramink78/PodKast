package rk.podkast.data

import androidx.annotation.StringRes
import rk.podkast.R

enum class GenreType(@StringRes val nameRes: Int) {
    PODCASTSERIES_ARTS(R.string.genre_art),
    PODCASTSERIES_ARTS_BOOKS(R.string.genre_art_books),
    PODCASTSERIES_ARTS_FASHION_AND_BEAUTY(R.string.genre_fashion),
    PODCASTSERIES_ARTS_FOOD(R.string.genre_food),
    PODCASTSERIES_BUSINESS(R.string.genre_business),
    PODCASTSERIES_COMEDY_STANDUP(R.string.genre_comedy),
    PODCASTSERIES_EDUCATION(R.string.genre_education),
    PODCASTSERIES_SPORTS(R.string.genre_sports),
    PODCASTSERIES_TECHNOLOGY(R.string.genre_technology),
    PODCASTSERIES_FICTION_DRAMA(R.string.genre_drama),
    PODCASTSERIES_GOVERNMENT(R.string.genre_government),
    PODCASTSERIES_HISTORY(R.string.genre_history),
    PODCASTSERIES_HEALTH_AND_FITNESS(R.string.genre_fitness),
    PODCASTSERIES_KIDS_AND_FAMILY(R.string.genre_family),
    PODCASTSERIES_LEISURE_GAMES(R.string.genre_games),
    PODCASTSERIES_NEWS(R.string.genre_news),
    PODCASTSERIES_MUSIC(R.string.genre_music),
    PODCASTSERIES_SCIENCE(R.string.genre_science),
    PODCASTSERIES_TV_AND_FILM(R.string.genre_film)
}
