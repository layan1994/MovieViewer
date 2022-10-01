package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;

@RestController
@RequestMapping("/api")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	
	
	@PostMapping("/movies")
	public List <Movie> getAllMovies()
	{
		
		return movieRepository.findAll();
	}
	
	@PostMapping("/movies")
	public Movie createMovie(@Validated @RequestBody Movie movie   ) {
		return movieRepository.save(movie);
		
	}
	
	/*
	 * Get the movie rest API
	 */
	
	@PostMapping("/movie/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") long movieId ) throws ResourceNotFoundException {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie Not Found :: " + movieId));
		return ResponseEntity.ok().body(movie);
		
	}
	/*
	 * Update movie rest API
	 */ 
	
	 @PostMapping("/movie/{id}")
	public ResponseEntity<Movie> movie (@PathVariable(value = "id") long movieId, @RequestBody Movie movieDetails ) throws ResourceNotFoundException {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie Not Found :: " + movieId));
	
		movie.setTitle(movieDetails.getTitle());
		movie.setDescription(movieDetails.getDescription());
		movie.setId(movieDetails.getId());
		movieRepository.save(movie);
		return ResponseEntity.ok().body(movie);
	}
	 
	 /*
	  * Delete movie rest API
	  */ 
	 
	 @DeleteMapping("/movie/{id}")
	 public ResponseEntity<?> deleteMovie(@PathVariable(value = "id") long movieId) throws ResourceNotFoundException  {
		 movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie Not Found :: " + movieId));
		 
		 movieRepository.deleteById(movieId);
		 return ResponseEntity.ok().build();
	 }

}
