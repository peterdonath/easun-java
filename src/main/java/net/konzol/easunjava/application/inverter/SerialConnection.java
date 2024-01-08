package net.konzol.easunjava.application.inverter;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class SerialConnection {

    private final SerialPort comPort;

    private SerialConnection() {
        comPort = SerialPort.getCommPorts()[0];
        comPort.setBaudRate(2400);
        comPort.openPort();
        MessageListener listener = new MessageListener();
        comPort.addDataListener(listener);
    }

    public void sendBytes(byte[] bytes) {
        comPort.writeBytes(bytes, bytes.length);
    }

    private final class MessageListener implements SerialPortMessageListener {
        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public byte[] getMessageDelimiter() {
            return new byte[]{(byte) 0x23};
        }

        @Override
        public boolean delimiterIndicatesEndOfMessage() {
            return true;
        }

        @Override
        public void serialEvent(SerialPortEvent event) {
            byte[] delimitedMessage = event.getReceivedData();
            String message = new String(delimitedMessage, StandardCharsets.UTF_8);
            log.debug("Received the following delimited message: {}", message);
        }
    }
}
