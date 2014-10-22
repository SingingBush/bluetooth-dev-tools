/*$Id$*/

package com.singingbush.bluetooth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.bluetooth.*;
import java.io.IOException;

public class BluetoothServer {

    private static final Logger LOG = LoggerFactory.getLogger(BluetoothServer.class);

    public void start() {
        final LocalDevice localDevice;

        try {
            final String stack = LocalDevice.getProperty("bluecove.stack"); // "winsock" (Microsoft), "widcomm" (Broadcom), "bluesoleil"
//            System.out.println("stack: " + stack);
            LOG.debug("Bluecove is using the {} stack", stack);

            // If automatic Bluetooth Stack detection is not enough Java System property "bluecove.stack" can be used to force desired Stack Initialization.
            // Values "widcomm", "bluesoleil" or "winsock". By default winsock is selected if available.
            // Another property "bluecove.stack.first" is used optimize stack detection.
            // If -Dbluecove.stack.first=widcomm then widcomm (bluecove.dll) stack is loaded first and if not available then BlueCove will switch to winsock. By default intelbth.dll is loaded first.
            localDevice = LocalDevice.getLocalDevice();

            //final boolean discoverable = LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
            // General/Unlimited Inquiry Access Code (GIAC)

            //System.out.printf("Local Bluetooth Device:\t%s:%s\n", localDevice.getFriendlyName(), localDevice.getBluetoothAddress());
            LOG.debug("Local Bluetooth Device: {}:{}", localDevice.getFriendlyName(), localDevice.getBluetoothAddress());

            final MyDiscoveryListener listener = new MyDiscoveryListener();
            final boolean started = localDevice.getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);

        } catch (final BluetoothStateException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("LocalCanBeFinal")
    private class MyDiscoveryListener implements DiscoveryListener {
        @Override
        public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
            String address = remoteDevice.getBluetoothAddress();
            String name = null;
            try {
                name = remoteDevice.getFriendlyName(true);
            } catch (IOException e) {
                LOG.error("Unable to get device name", e);
            }
            LOG.debug("Device discovered {} : {}", address, name);
        }

        @Override
        public void servicesDiscovered(int i, ServiceRecord[] serviceRecords) {

        }

        @Override
        public void serviceSearchCompleted(int i, int i1) {

        }

        @Override
        public void inquiryCompleted(int i) {

        }
    }

}
