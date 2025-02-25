package com.example.cgv.service;

import com.example.cgv.dto.MovieInfo;
import com.example.cgv.entity.Movie;
import com.example.cgv.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CgvMovieService {

    private static final String CGV_MOVIE_URL = "http://www.cgv.co.kr/movies/";
    private final MovieRepository movieRepository;

    // 크롤링 후 DB에 저장 + 반환(크롤링 결과)
    public List<MovieInfo> crawlAndSaveMovies() {
        List<MovieInfo> infoList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(CGV_MOVIE_URL)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements liElements = document.select("div.sect-movie-chart ol > li");
            for (Element li : liElements) {
                String title = li.select(".box-contents strong.title").text();
                String rawRate = li.select(".score .percent span").text();

                Double bookingRate = null;
                if (rawRate.endsWith("%")) {
                    rawRate = rawRate.substring(0, rawRate.length() - 1);
                    bookingRate = Double.parseDouble(rawRate);
                }

                String openDate = li.select(".txt-info strong").text();
                openDate = openDate.replace(" 개봉", "");

                MovieInfo info = new MovieInfo(title, bookingRate, openDate);
                infoList.add(info);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1) infoList -> Entity 변환
        List<Movie> entityList = new ArrayList<>();
        for (MovieInfo info : infoList) {
            Movie movie = new Movie();
            movie.setTitle(info.getTitle());
            movie.setBookingRate(info.getBookingRate());
            movie.setOpenDate(info.getOpenDate());
            entityList.add(movie);
        }

        // 2) DB 저장
        movieRepository.saveAll(entityList);

        // 3) 크롤링 결과를 반환
        return infoList;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMovieByRateDesc() {
        return movieRepository.findAllByOrderByBookingRateDesc();
    }
}

