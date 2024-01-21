package net.konzol.easunjava.application.inverter;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.nio.charset.StandardCharsets;

@Slf4j
public class SerialConnection implements Runnable {

    private SerialPort comPort;

    private final Integer portNumber;
    private final ApplicationEventPublisher eventPublisher;

    public SerialConnection(ApplicationEventPublisher eventPublisher, Integer portNumber) {

        this.eventPublisher = eventPublisher;
        this.portNumber = portNumber;

        log.info("Serial Thread started: {}", portNumber);

        comPort = SerialPort.getCommPorts()[portNumber];
        comPort.setBaudRate(2400);
        comPort.setParity(0);
        comPort.openPort();
        MessageListener listener = new MessageListener();
        comPort.addDataListener(listener);
    }

    public void sendBytes(byte[] bytes) {
        comPort.writeBytes(bytes, bytes.length);
    }

    @Override
    public void run() {

        while (true) {

        }
    }

    private final class MessageListener implements SerialPortMessageListener {
        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public byte[] getMessageDelimiter() {
            return new byte[]{(byte) 0x0D};
        }

        @Override
        public boolean delimiterIndicatesEndOfMessage() {
            return true;
        }

        @Override
        public void serialEvent(SerialPortEvent event) {
            byte[] delimitedMessage = event.getReceivedData();
            String message = new String(delimitedMessage, StandardCharsets.UTF_8);
            log.info("Received the following delimited message: {}", message);
            eventPublisher.publishEvent(new SerialMessageEvent(portNumber, message));
        }
    }
}
