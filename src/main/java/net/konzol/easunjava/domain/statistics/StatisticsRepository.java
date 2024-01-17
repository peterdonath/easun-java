package net.konzol.easunjava.domain.statistics;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Optional<Statistics> findByDate(Date date);
}
