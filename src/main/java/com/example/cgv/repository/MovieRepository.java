package com.example.cgv.repository;

import com.example.cgv.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m ORDER BY m.bookingRate DESC")
    List<Movie> findAllByOrderByBookingRateDesc();
}
