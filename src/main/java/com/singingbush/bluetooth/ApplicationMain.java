/*$Id$*/
package com.singingbush.bluetooth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

/**
 * Entry point for the application from the terminal
 */
public class ApplicationMain {

    public static final Logger LOG = LogManager.getLogger(ApplicationMain.class);

    public static void main(final String[] args) {
        LOG.info("started..");

        BluetoothServer server = new BluetoothServer();
        server.start();
    }

}
