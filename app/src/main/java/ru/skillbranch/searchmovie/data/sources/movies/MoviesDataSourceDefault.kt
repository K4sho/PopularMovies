package ru.skillbranch.searchmovie.data.sources.movies

import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MovieActorCrossRef
import ru.skillbranch.searchmovie.data.dto.CategoryDto


class MoviesDataSourceDefault {
    fun getMovies() = listOf(
        Movie(
            movieId = 1,
            title = "Гнев человеческий",
            description = "Эйч — загадочный и холодный на вид джентльмен, но внутри него пылает жажда справедливости. Преследуя...",
            rateScore = 3,
            ageLimit = 18,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5JP9X5tCZ6qz7DYMabLmrQirlWh.jpg",
            genre = CategoryDto("Боевик"),
            releaseDate = "22.04.21",
        ),
        Movie(
            movieId = 2,
            title = "Мортал Комбат",
            description = "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии...",
            rateScore = 5,
            ageLimit = 18,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pMIixvHwsD5RZxbvgsDSNkpKy0R.jpg",
            genre = CategoryDto("Боевик"),
            releaseDate = "08.04.21",
        ),
        Movie(
            movieId = 3,
            title = "Упс... Приплыли!",
            description = "От Великого потопа зверей спас ковчег. Но спустя полгода скитаний они готовы сбежать с него куда угодно...",
            rateScore = 5,
            ageLimit = 6,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/546RNYy9Wi5wgboQ7EtD6i0DY5D.jpg",
            genre = CategoryDto("Мультик"),
            releaseDate = "24.09.20",
        ),
        Movie(
            movieId = 4,
            title = "The Box",
            description = "Уличный музыкант знакомится с музыкальным продюсером, и они вдвоём отправляются в путешествие...",
            rateScore = 4,
            ageLimit = 12,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fq3DSw74fAodrbLiSv0BW1Ya4Ae.jpg",
            genre = CategoryDto("Драма"),
            releaseDate = "13.05.21",
        ),
        Movie(
            movieId = 5,
            title = "Сага о Дэнни Эрнандесе",
            description = "Tekashi69 или Сикснайн — знаменитый бруклинский рэпер с радужными волосами — прогремел...",
            rateScore = 2,
            ageLimit = 18,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5xXGQLVtTAExHY92DHD9ewGmKxf.jpg",
            genre = CategoryDto("Музыка"),
            releaseDate = "29.04.21",
        ),
        Movie(
            movieId = 6,
            title = "Пчелка Майя",
            description = "Когда упрямая пчелка Майя и ее лучший друг Вилли спасают принцессу-муравьишку, начинается сказочное...",
            rateScore = 4,
            ageLimit = 0,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xltjMeLlxywym14NEizl0metO10.jpg",
            genre = CategoryDto("Мультик"),
            releaseDate = "07.01.21",
        ),
        Movie(
            movieId = 7,
            title = "Круэлла",
            description = "Невероятно одаренная мошенница по имени Эстелла решает сделать себе имя в мире моды.",
            rateScore = 4,
            ageLimit = 12,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/hUfyYGP9Xf6cHF9y44JXJV3NxZM.jpg",
            genre = CategoryDto("Комедия"),
            releaseDate = "03.06.21",
        ),
        Movie(
            movieId = 8,
            title = "Чёрная вдова",
            description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
            rateScore = 3,
            ageLimit = 16,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mbtN6V6y5kdawvAkzqN4ohi576a.jpg",
            genre = CategoryDto("Боевик"),
            releaseDate = "08.07.21",
        )
    )

    fun getMoviesAndActors() = listOf(
        MovieActorCrossRef(
            movieId = 1,
            actorId = 1
        ),
        MovieActorCrossRef(
            movieId = 1,
            actorId = 2
        ),
        MovieActorCrossRef(
            movieId = 1,
            actorId = 3
        ),
        MovieActorCrossRef(
            movieId = 2,
            actorId = 4
        ),
        MovieActorCrossRef(
            movieId = 2,
            actorId = 5
        ),
        MovieActorCrossRef(
            movieId = 2,
            actorId = 6
        ),
        MovieActorCrossRef(
            movieId = 3,
            actorId = 7
        ),
        MovieActorCrossRef(
            movieId = 3,
            actorId = 8
        ),
        MovieActorCrossRef(
            movieId = 3,
            actorId = 9
        ),
        MovieActorCrossRef(
            movieId = 4,
            actorId = 10
        ),
        MovieActorCrossRef(
            movieId = 4,
            actorId = 11
        ),
        MovieActorCrossRef(
            movieId = 4,
            actorId = 12
        ),
        MovieActorCrossRef(
            movieId = 5,
            actorId = 13
        ),
        MovieActorCrossRef(
            movieId = 5,
            actorId = 11
        ),
        MovieActorCrossRef(
            movieId = 5,
            actorId = 12
        ),
        MovieActorCrossRef(
            movieId = 6,
            actorId = 16
        ),
        MovieActorCrossRef(
            movieId = 6,
            actorId = 17
        ),
        MovieActorCrossRef(
            movieId = 6,
            actorId = 18
        ),
        MovieActorCrossRef(
            movieId = 7,
            actorId = 19
        ),
        MovieActorCrossRef(
            movieId = 7,
            actorId = 20
        ),
        MovieActorCrossRef(
            movieId = 7,
            actorId = 21
        ),
        MovieActorCrossRef(
            movieId = 8,
            actorId = 22
        ),
        MovieActorCrossRef(
            movieId = 8,
            actorId = 23
        ),
        MovieActorCrossRef(
            movieId = 8,
            actorId = 24
        ),
    )
}