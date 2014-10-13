/*$Id$*/

package com.singingbush.bluetooth;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;

public class BluetoothServer {

    public void start() {
        final LocalDevice localDevice;

        try {
            localDevice = LocalDevice.getLocalDevice();

            //final boolean discoverable = LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
            // General/Unlimited Inquiry Access Code (GIAC)

            System.out.printf("\nLocal Device: %s", localDevice);
        } catch (final BluetoothStateException e) {
            e.printStackTrace();
        }
    }

}
