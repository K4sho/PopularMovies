package ru.skillbranch.searchmovie.data.dto

data class MovieDto(
    val title: String, // Название фильма
    val description: String, // Описание фильма
    val rateScore: Int, // Рэйтинг
    val ageLimit: Int, // Возрастное ограничение
    val imageUrl: String, // Баннер
    val genre: CategoryDto, // Жанр
    val releaseDate: String, // Дата релиза
    val actors: List<ActorDto> // Список актеров
)