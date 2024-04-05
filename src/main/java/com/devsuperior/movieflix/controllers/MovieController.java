package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    @GetMapping()
    public ResponseEntity<Page<MovieCardDTO>> findByGenre(@RequestParam(required = false) Long genreId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<MovieCardDTO> movies = movieService.findMoviesByGenre(genreId, PageRequest.of(page, size));
        return ResponseEntity.ok().body(movies);
    }

    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDTO> searchById(@PathVariable Long id) {
        MovieDetailsDTO dto = movieService.searchById(id);
        return ResponseEntity.ok().body(dto);
    }
    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    @GetMapping("/{idMovie}/reviews")
    public ResponseEntity<List<ReviewDTO>> findReviewsMovieId(@PathVariable Long idMovie) {
        List<ReviewDTO> reviews = movieService.findReviewsMovieId(idMovie);
        return ResponseEntity.ok().body(reviews);
    }

}
