/*$Id$*/

package com.singingbush.bluetooth;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;

public class BluetoothServer {

    public void start() {
        final LocalDevice localDevice;

        try {
            final String stack = LocalDevice.getProperty("bluecove.stack");
            System.out.println(stack);
            // If automatic Bluetooth Stack detection is not enough Java System property "bluecove.stack" can be used to force desired Stack Initialization.
            // Values "widcomm", "bluesoleil" or "winsock". By default winsock is selected if available.
            // Another property "bluecove.stack.first" is used optimize stack detection.
            // If -Dbluecove.stack.first=widcomm then widcomm (bluecove.dll) stack is loaded first and if not available then BlueCove will switch to winsock. By default intelbth.dll is loaded first.
            localDevice = LocalDevice.getLocalDevice();

            //final boolean discoverable = LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
            // General/Unlimited Inquiry Access Code (GIAC)

            System.out.printf("\nLocal Device: %s", localDevice);
        } catch (final BluetoothStateException e) {
            e.printStackTrace();
        }
    }

}
