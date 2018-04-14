package com.singingbush.bluetooth;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the application from the terminal
 */
public class ApplicationMain {

    private static final Logger log = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) {
        log.info("Starting Bluetooth server");
        BluetoothServer server = new BluetoothServer();
        server.start();
    }

}
