package net.konzol.easunjava.application.inverter;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import net.konzol.easunjava.domain.inverter.InverterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class SerialConnectionService {

    private final InverterRepository inverterRepository;

    private final ApplicationEventPublisher eventPublisher;

    private List<SerialConnection> connections;

    private SerialConnectionService(@Autowired InverterRepository inverterRepository,
                                    @Autowired ApplicationEventPublisher eventPublisher) {
        this.inverterRepository = inverterRepository;
        this.eventPublisher = eventPublisher;
        this.connections = new ArrayList<>();
    }

    public void sendBytes(byte[] bytes) {

        if (connections.size() == 0) {
            this.initializeConnections();
        }
        connections.forEach(connection -> connection.sendBytes(bytes));
    }

    private void initializeConnections() {
        Arrays.stream(SerialPort.getCommPorts())
                .forEach(port ->
                {
                    log.info("Port name: {}, baudrate: {}, parity: {}, databits: {}, stopbits: {}, description: {}",
                            port.getDescriptivePortName(),
                            port.getBaudRate(),
                            port.getParity(),
                            port.getNumDataBits(),
                            port.getNumStopBits(),
                            port.getPortDescription());
                });

        inverterRepository.findAll().forEach(inverter -> {
            log.info("Starting serial connection to serial port: {}", inverter.getPortNumber());

            SerialConnection serialConnection = new SerialConnection(eventPublisher, inverter.getPortNumber());
            connections.add(serialConnection);
        });
    }

    @PreDestroy
    public void closeConnections() {
        connections.forEach(SerialConnection::closeConnection);
    }
}
