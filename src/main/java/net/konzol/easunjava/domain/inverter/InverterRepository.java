package net.konzol.easunjava.domain.inverter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InverterRepository extends JpaRepository<Inverter, Long> {

    Optional<Inverter> findByPortNumber(Integer portNumber);
}
