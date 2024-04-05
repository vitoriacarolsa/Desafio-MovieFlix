package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findMoviesByGenre(Long genreId, Pageable pageable) {
        return movieRepository.findMoviesByGenre(genreId, pageable);
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO searchById(Long id) {
        Movie entity = movieRepository.searchById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MovieDetailsDTO(entity);
    }

    @Transactional
    public List<ReviewDTO> findReviewsMovieId(Long idMovie) {
        Optional<Movie> movieOptional = movieRepository.findById(idMovie);
        Movie movie = movieOptional.orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));
        return movie.getReviews().stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
    }


}
