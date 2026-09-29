package com.agacostays.analytics.repository;
import com.agacostays.analytics.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReportRepository extends JpaRepository<Report,Long>{}
