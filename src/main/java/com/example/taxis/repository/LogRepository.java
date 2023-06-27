package com.example.taxis.repository;

import com.example.taxis.entity.RequestLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface LogRepository  extends JpaRepository<RequestLogs, Integer> {
    @Query ("select COUNT(l) from RequestLogs l where DATE (l.RequestDate) = :date")
    int getLogCountForDay(@Param("date") LocalDate date);

}
