# easun-java
Manage easun inverter with java

## The Plan

My plan is to hook up a raspberry-pi to the easun 5000VA MPPT hybrid inverter with USB and get some info from the device.

Spring-Boot application - java 17 using graalvm

### Inverter details

The inverter is a clone of the Axpert PIP-4048MS, but in theory the PIP-5048MS has the same protocol.

More info about the inverter: [Aeva forum](https://forums.aeva.asn.au/viewtopic.php?p=53691#p53691)

<p>Currently I have 2 of these, and I found out I need a third one for 3 phase setup.<br>
Unfortunately I was not able to source the same I already have, but I've found a Daxtromn Power one which looks the same, and has the same parameters.</p>

webshop link: [daxtromn-power.com](https://daxtromn-power.com/products/mppt-5000w-solar-inverter-max-pv-array-power-4000w-48v-dc-max-80a-charge-built-in-parallel-purse-sine-wave)

So right now I have:

* 2 inverters with firmware: 52.30
* 1 inverter with firmware: 74.20

The do not work together obviously, why would they.

My plan is to flash firmwares all to 73.00e

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