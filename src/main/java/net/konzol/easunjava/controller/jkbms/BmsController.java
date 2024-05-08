package net.konzol.easunjava.controller.jkbms;

import lombok.RequiredArgsConstructor;
import net.konzol.easunjava.application.metrics.InverterMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BmsController {

  private final InverterMetrics inverterMetrics;

  @PostMapping("/jkbms-data")
  public ResponseEntity<?> updateBmsData(JkBmsData jkBmsData) {
    inverterMetrics.updateJkBmsMetrics(jkBmsData);

    return ResponseEntity.ok().build();
  }
}
