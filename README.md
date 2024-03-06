# easun-java
Manage easun inverter with java

## The Plan

My plan is to hook up a raspberry-pi to the easun 5000VA MPPT hybrid inverter with USB and get some info from the device.

Spring-Boot application - java 17 using graalvm

## Execution

### Architecture

#### DB

The application can be run with H2 in-memory database and in fact I was running it during the testing phase with it.

Postgres is also supported and after some testing I started using postgres.

#### Inverter mapping

Inverter mapping is stored in database, and for the time being is inserted by hand, port number is the index of the serial port when you list all the serial ports available.

#### Serial connection

This type of inverter has a USB port on the bottom which is a standard, cheap CH340 serial chip.

* Baudrate: 2400
* parity: 0

I had troubles maintaining the connection for long time, and because data flow is on demand I decided to open the connection whenever I
want to request data from the inverter and close it after the data was received (no matter if it is readable or not).