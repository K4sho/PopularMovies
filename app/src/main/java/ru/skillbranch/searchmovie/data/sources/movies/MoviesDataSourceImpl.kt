package ru.skillbranch.searchmovie.data.sources.movies

import ru.skillbranch.searchmovie.data.dto.ActorDto
import ru.skillbranch.searchmovie.data.dto.CategoryDto
import ru.skillbranch.searchmovie.data.dto.MovieDto

class MoviesDataSourceImpl: IMoviesDataSource {
    override fun getMovies() = listOf(
        MovieDto(
            id = 1,
            title = "Гнев человеческий",
            description = "Эйч — загадочный и холодный на вид джентльмен, но внутри него пылает жажда справедливости. Преследуя...",
            rateScore = 3,
            ageLimit = 18,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5JP9X5tCZ6qz7DYMabLmrQirlWh.jpg",
            genre = CategoryDto("Боевик"),
            releaseDate = "22.04.21",
            actors = listOf(
                ActorDto("Джейсон Стейтем","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lldeQ91GwIVff43JBrpdbAAeYWj.jpg"),
                ActorDto("Холт Маккэллани","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8NvOcP35qv5UHWEdpqAvQrKnQQz.jpg"),
                ActorDto("Джош Харнетт","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/y3UF4biXITMMH8qutq7K7YLjvu5.jpg"))
        ),
        MovieDto(
            id = 2,
            title = "Мортал Комбат",
            description = "Боец смешанных единоборств Коул Янг не раз соглашался проиграть за деньги. Он не знает о своем наследии...",
            rateScore = 5,
            ageLimit = 18,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/pMIixvHwsD5RZxbvgsDSNkpKy0R.jpg",
            genre = CategoryDto("Боевик"),
            releaseDate = "08.04.21",
            actors = listOf(
                ActorDto("Lewis Tan","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lkW8gh20BuwzHecXqYH1eRVuWpb.jpg"),
                ActorDto("Jessica McNamee","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aAfaMEEqD8syHv5bLi5B3sccrM2.jpg"),
                ActorDto("Josh Lawson","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/Am9vM77uZd9bGODugwmWtOfzx6E.jpg"))
        ),
        MovieDto(
            id = 3,
            title = "Упс... Приплыли!",
            description = "От Великого потопа зверей спас ковчег. Но спустя полгода скитаний они готовы сбежать с него куда угодно...",
            rateScore = 5,
            ageLimit = 6,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/546RNYy9Wi5wgboQ7EtD6i0DY5D.jpg",
            genre = CategoryDto("Мультик"),
            releaseDate = "24.09.20",
            actors = listOf(
                ActorDto("Tara Flynn","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/17gBs4aux2NcnMvf3DK5UKUFttn.jpg"),
                ActorDto("Ava Connolly","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/o8uE77C4wQHYHJW6En192kjxJGd.jpg"),
                ActorDto("Mary Murray","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1ZRP9IfehCSx5OeBQQDcVPvKYD0.jpg"))
        ),
        MovieDto(
            id = 4,
            title = "The Box",
            description = "Уличный музыкант знакомится с музыкальным продюсером, и они вдвоём отправляются в путешествие...",
            rateScore = 4,
            ageLimit = 12,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fq3DSw74fAodrbLiSv0BW1Ya4Ae.jpg",
            genre = CategoryDto("Драма"),
            releaseDate = "13.05.21",
            actors = listOf(
                ActorDto("Chanyeol","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qqvTuk4CTvS1IE47CUozhcHVahz.jpg"),
                ActorDto("Jo Dal-hwan","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/jpEPPXmVC3EDMqrDQDYyXEMYlah.jpg"),
                ActorDto("Gaeko","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fDO7vJVRkZOOY1GtQMJzf4N136q.jpg"))
        ),
        MovieDto(
            id = 5,
            title = "Сага о Дэнни Эрнандесе",
            description = "Tekashi69 или Сикснайн — знаменитый бруклинский рэпер с радужными волосами — прогремел...",
            rateScore = 2,
            ageLimit = 18,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5xXGQLVtTAExHY92DHD9ewGmKxf.jpg",
            genre = CategoryDto("Музыка"),
            releaseDate = "29.04.21",
            actors = listOf(
                ActorDto("6ix9ine","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xAlvyeC9zLbygGMxmmyTHymwuZP.jpg"),
                ActorDto("Jo Dal-hwan","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/jpEPPXmVC3EDMqrDQDYyXEMYlah.jpg"),
                ActorDto("Gaeko","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fDO7vJVRkZOOY1GtQMJzf4N136q.jpg"))
        ),
        MovieDto(
            id = 6,
            title = "Пчелка Майя",
            description = "Когда упрямая пчелка Майя и ее лучший друг Вилли спасают принцессу-муравьишку, начинается сказочное...",
            rateScore = 4,
            ageLimit = 0,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xltjMeLlxywym14NEizl0metO10.jpg",
            genre = CategoryDto("Мультик"),
            releaseDate = "07.01.21",
            actors = listOf(
                ActorDto("Benson Jack Anthony","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aVfEldX1ksEMrx45yNBAf9MAIDZ.jpg"),
                ActorDto("Frances Berry","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qCp0psD5qzguABpRxWmMuC04kcl.jpg"),
                ActorDto("Christian Charisiou","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8OpoYvO1QqBYRAp1LxxUIiRdQG0.jpg"))
        ),
        MovieDto(
            id = 7,
            title = "Круэлла",
            description = "Невероятно одаренная мошенница по имени Эстелла решает сделать себе имя в мире моды.",
            rateScore = 4,
            ageLimit = 12,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/hUfyYGP9Xf6cHF9y44JXJV3NxZM.jpg",
            genre = CategoryDto("Комедия"),
            releaseDate = "03.06.21",
            actors = listOf(
                ActorDto("Emma Stone","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2hwXbPW2ffnXUe1Um0WXHG0cTwb.jpg"),
                ActorDto("Emma Thompson","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xr8Ki3CIqweWWqS5q0kUYdiK6oQ.jpg"),
                ActorDto("Joel Fry","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4nEKEWJpaTHncCTv6zeP98V0qGI.jpg"))
        ),
        MovieDto(
            id = 8,
            title = "Чёрная вдова",
            description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
            rateScore = 3,
            ageLimit = 16,
            imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mbtN6V6y5kdawvAkzqN4ohi576a.jpg",
            genre = CategoryDto("Боевик"),
            releaseDate = "08.07.21",
            actors = listOf(
                ActorDto("Scarlett Johansson","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg"),
                ActorDto("Florence Pugh","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/75PvULemW8BvheSKtPMoBBsvPLh.jpg"),
                ActorDto("Rachel Weisz","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3QbFXeiUzXUVUrJ7fdiCn7A7ReW.jpg"))
        )
    )
}