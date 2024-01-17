package net.konzol.easunjava.domain.statistics;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Long inverterId;

    private Double solarPower;

    private Double batteryCharged;

    private Double batteryDischarged;

    private Double activeConsumption;

}
