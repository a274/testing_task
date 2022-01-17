package ru.a274.reportdirapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.a274.reportdirapp.model.db.Report;

@Repository
public interface ReportRepo extends JpaRepository<Report, String> {
}