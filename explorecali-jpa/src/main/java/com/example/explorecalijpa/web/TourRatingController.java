package com.example.explorecalijpa.web;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.explorecalijpa.business.TourRatingService;
import com.example.explorecalijpa.model.TourRating;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Tour Rating Controller
 *
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
  private TourRatingService tourRatingService;

  public TourRatingController(TourRatingService tourRatingService) {
    this.tourRatingService = tourRatingService;
  }

  /**
   * Create a Tour Rating
   * 
   * @param tourId
   * @param ratingDto
   */
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public void createTourRating(@PathVariable(value = "tourId") int tourId,
                               @RequestBody @Valid RatingDto ratingDto) {
    tourRatingService.createNew(tourId, ratingDto.getCustomerId(), 
    ratingDto.getScore(), ratingDto.getComment()); 
  }

  /**
   * Get Tour Ratings for a Tour
   * 
   * @param tourId
   * @return list of Tour Ratings
   */
  @GetMapping()
  public List<RatingDto> getAllRatingsForTour(@PathVariable( value = "tourId") int tourId) {
    List<TourRating> tourRatings = tourRatingService.lookupRatings(tourId);  
    return tourRatings.stream().map(RatingDto::new).toList();
  }

  /**
   * Get average Tour Rating for a Tour
   * 
   * @param tourId
   * @return double representing tour average rating
   */
  @GetMapping("/average")
  public Map<String, Double> getAvgRatingForTour(@PathVariable( value = "tourId") int tourId) {
      return Map.of("Average", tourRatingService.getAverageScore(tourId));
  }
  
  /**
   * Update score and comment of a Tour Rating
   * 
   * @param tourId    int
   * @param ratingDto RatingDto
   * @return The modified Rating DTO
   */
  @PutMapping()
  public RatingDto updateRating(@PathVariable(value = "tourId") int tourId,
                                @RequestBody @Valid RatingDto ratingDto) {
    return new RatingDto(tourRatingService.update(tourId, 
    ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment()));
  }

  /**
   * Change score or comment of a Tour Rating
   * 
   * @param tourId     int
   * @param customerId Integer
   * @return Updated Rating DTO
   */
  @PatchMapping()
  public RatingDto patchUpdateRating(@PathVariable(value = "tourId") int tourId,
                                     @RequestBody @Valid RatingDto ratingDto) {
    return new RatingDto(tourRatingService.updateSome(tourId, 
        ratingDto.getCustomerId(), Optional.ofNullable(
        ratingDto.getScore()), Optional.ofNullable(ratingDto.getComment())));
  }

  /**
   * Delete a Tour Rating
   * 
   * @param tourId     int
   * @param customerId Integer
   */
  @DeleteMapping("/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRating(@PathVariable(value="tourId") int tourId,
                          @PathVariable(value="customerId") Integer customerId) {
    tourRatingService.delete(tourId, customerId);
  }
}
