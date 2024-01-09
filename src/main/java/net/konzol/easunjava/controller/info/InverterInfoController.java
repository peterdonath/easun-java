package net.konzol.easunjava.controller.info;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import net.konzol.easunjava.application.inverter.DeviceStatus;
import net.konzol.easunjava.application.inverter.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class InverterInfoController {

    private final StatusService statusService;

    @GetMapping("/info")
    public ResponseEntity<String> getDeviceInfo() throws JsonProcessingException {
        DeviceStatus deviceStatus = statusService.getDeviceStatus();

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = mapper.writeValueAsString(DeviceStatusDto.of(deviceStatus));

        return ResponseEntity.ok(prettyJson);
    }
}
