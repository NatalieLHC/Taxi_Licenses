package com.example.taxis.repository;

import com.example.taxis.entity.RequestLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository  extends JpaRepository<RequestLogs, Integer> {

}
