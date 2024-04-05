package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public ReviewDTO insert(@Valid ReviewDTO dto) {
        Review entity = new Review();
        copyDtoToEntity(dto, entity);
        entity.setUser(authService.authenticated());
        entity = repository.save(entity);
        return new ReviewDTO(entity);

    }
    private void copyDtoToEntity(ReviewDTO dto, Review entity) {

        entity.setText(dto.getText());
        MovieCardDTO movieCardDTO = new MovieCardDTO();
        movieCardDTO.setId(dto.getMovieId());
        Movie movie = movieRepository.getReferenceById(movieCardDTO.getId());
        entity.setMovie(movie);
    }
}
