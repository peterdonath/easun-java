package net.konzol.easunjava.controller.info;

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
    public ResponseEntity<DeviceStatusDto> getDeviceInfo() {
        DeviceStatus deviceStatus = statusService.getDeviceStatus();

        return ResponseEntity.ok(DeviceStatusDto.of(deviceStatus));
    }
}
