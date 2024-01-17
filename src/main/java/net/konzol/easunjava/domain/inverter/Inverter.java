package net.konzol.easunjava.domain.inverter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Inverter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String serialNumber;

    private Integer portNumber;
}
