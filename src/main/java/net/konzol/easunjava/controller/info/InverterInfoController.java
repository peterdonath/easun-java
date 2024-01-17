package net.konzol.easunjava.controller.info;

import lombok.RequiredArgsConstructor;
import net.konzol.easunjava.application.inverter.DeviceStatus;
import net.konzol.easunjava.application.inverter.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class InverterInfoController {

    private final StatusService statusService;

    @GetMapping("/info")
    public ResponseEntity<List<DeviceStatusDto>> getDeviceInfo() {
        List<DeviceStatusDto> deviceStatusDtoList =
                statusService.getDeviceStatusList()
                .stream()
                .map(DeviceStatusDto::of)
                .toList();

        return ResponseEntity.ok(deviceStatusDtoList);
    }
}
