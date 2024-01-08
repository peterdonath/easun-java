package net.konzol.easunjava.controller;

import lombok.RequiredArgsConstructor;
import net.konzol.easunjava.application.inverter.SerialConnection;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class InverterInfoController {

    private final SerialConnection serialConnection;

    @PostMapping("/info")
    public ResponseEntity<?> sendInfoRequest() {
        serialConnection.sendBytes(HexUtils.fromHexString("5150494753B7A90D"));

        return ResponseEntity.ok().build();
    }
}
