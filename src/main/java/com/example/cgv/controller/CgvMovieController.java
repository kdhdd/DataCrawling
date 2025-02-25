package com.example.cgv.controller;

import com.example.cgv.entity.Movie;
import com.example.cgv.service.CgvMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cgv")
@RequiredArgsConstructor
public class CgvMovieController {
    private final CgvMovieService cgvMovieService;

    // POST로 크롤링 후 DB 저장
    @PostMapping("/crawl")
    public String crawlMovies() {
        cgvMovieService.crawlAndSaveMovies();
        return "크롤링한 영화 정보를 DB에 저장했습니다.";
    }

    // GET으로 DB 조회
    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return cgvMovieService.getAllMovies();
    }

    @GetMapping("/movies/rate-desc")
    public List<Movie> getMoviesInDesc() {
        return cgvMovieService.getMovieByRateDesc();
    }
}

