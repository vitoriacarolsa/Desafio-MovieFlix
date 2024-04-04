package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT new com.devsuperior.movieflix.dto.MovieCardDTO(m.id, m.title, m.subTitle, m.year, m.imgUrl) FROM Movie m WHERE :genreId IS NULL OR m.genre.id = :genreId ORDER BY m.title")
    Page<MovieCardDTO> findMoviesByGenre(Long genreId, Pageable pageable);

    @Query("SELECT DISTINCT m FROM Movie m JOIN FETCH m.genre WHERE m.id = :id")
    Optional<Movie> searchById(@Param("id") Long id);

}



