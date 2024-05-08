package net.konzol.easunjava.controller.jkbms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.konzol.easunjava.application.bms.BmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BmsController {

  private final BmsService bmsService;

  @PostMapping("/jkbms-data")
  public ResponseEntity<?> updateBmsData(@RequestBody JkBmsData jkBmsData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    log.info("Bms Data received: {}", mapper.writeValueAsString(jkBmsData));

    bmsService.updateBmsData(jkBmsData);

    return ResponseEntity.ok().build();
  }
}
