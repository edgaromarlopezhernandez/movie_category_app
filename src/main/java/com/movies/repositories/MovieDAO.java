package com.movies.repositories;

import com.movies.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDAO extends JpaRepository<Movie, Long> {
    @Query(value = "select * from movies m group by m.release_year", nativeQuery = true)
    public Page<Movie> findAllMoviesGroupedByReleaseYear(Pageable pageable);

    @Query(value = "select count(*) from movies m where YEAR(m.release_year) = :year", nativeQuery = true)
    public Long countBySpecificReleaseYear(Integer year);

    @Query(value = "select * from movies m where YEAR(m.release_year) = :year group by m.release_year", nativeQuery = true)
    public Page<Movie> findAllMoviesGroupedBySpecificReleaseYear(Pageable pageable, Integer year);
}