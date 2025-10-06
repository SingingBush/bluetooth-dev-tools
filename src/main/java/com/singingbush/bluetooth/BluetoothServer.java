package com.singingbush.bluetooth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.bluetooth.*;
import java.io.IOException;

public class BluetoothServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BluetoothServer.class);

    final Object inquiryCompletedLock = new Object();

    @Override
    public void run() {
        try {
            final String stack = LocalDevice.getProperty("bluecove.stack"); // "winsock" (Microsoft), "widcomm" (Broadcom), "bluesoleil"

            log.debug("Bluecove is using the {} stack", stack);

            // If automatic Bluetooth Stack detection is not enough Java System property "bluecove.stack" can be used to force desired Stack Initialization.
            // Values "widcomm", "bluesoleil" or "winsock". By default winsock is selected if available.
            // Another property "bluecove.stack.first" is used optimize stack detection.
            // If -Dbluecove.stack.first=widcomm then widcomm (bluecove.dll) stack is loaded first and if not available then BlueCove will switch to winsock. By default intelbth.dll is loaded first.
            final LocalDevice localDevice = LocalDevice.getLocalDevice();

            //final boolean discoverable = localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            // General/Unlimited Inquiry Access Code (GIAC)

            log.info("Local Bluetooth Device: {}:{}, class {}", localDevice.getFriendlyName(), localDevice.getBluetoothAddress(), localDevice.getDeviceClass());

            final MyDiscoveryListener listener = new MyDiscoveryListener();


            synchronized (inquiryCompletedLock) {
                final boolean started = localDevice.getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);

                if (started) {
                    log.debug("waiting for device inquiry to complete...");
                    inquiryCompletedLock.wait();
                }
            }

        } catch (final BluetoothStateException | InterruptedException e) {
            log.error("There was a problem getting bluetooth working", e);
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
                log.error("Unable to get device name", e);
            }
            log.debug("Device discovered address:{}, name:{}", address, name);
        }

        @Override
        public void servicesDiscovered(int transID, ServiceRecord[] serviceRecords) {
            log.debug("servicesDiscovered transID:{}, serviceRecords:{}", transID, serviceRecords);
        }

        @Override
        public void serviceSearchCompleted(int transID, int respCode) {
            log.debug("serviceSearchCompleted transID:{}, respCode:{}", transID, respCode);
        }

        @Override
        public void inquiryCompleted(int discType) {
            // int should be 0 to 7:
//            int INQUIRY_COMPLETED = 0;
//            int INQUIRY_TERMINATED = 5;
//            int INQUIRY_ERROR = 7;
//            int SERVICE_SEARCH_COMPLETED = 1;
//            int SERVICE_SEARCH_TERMINATED = 2;
//            int SERVICE_SEARCH_ERROR = 3;
//            int SERVICE_SEARCH_NO_RECORDS = 4;
//            int SERVICE_SEARCH_DEVICE_NOT_REACHABLE = 6;
            switch (discType) {
                case DiscoveryListener.INQUIRY_COMPLETED:
                    log.info("inquiry complete");
                    break;
                case DiscoveryListener.INQUIRY_TERMINATED:
                    log.info("inquiry terminated");
                    break;
                case DiscoveryListener.INQUIRY_ERROR:
                    log.error("inquiry error");
                    break;
            }
            synchronized (inquiryCompletedLock) {
                inquiryCompletedLock.notifyAll();
            }
        }
    }

}
