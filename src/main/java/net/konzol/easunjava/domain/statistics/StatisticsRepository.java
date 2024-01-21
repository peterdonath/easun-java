package net.konzol.easunjava.domain.statistics;

import net.konzol.easunjava.domain.inverter.Inverter;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    List<Statistics> findByDate(Date date);

    Optional<Statistics> findByDateAndInverter(Date date, Inverter inverter);
}
