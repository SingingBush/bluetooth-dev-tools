Bluetooth Dev Tools
===================

*work in progress* - Java app for watching data being sent from bluetooth devices


The project uses Gradle for building, simply run `./gradlew build`

### On Linux

On Linux you'll need _Bluez_ to be installed.

#### Fedora:

```
sudo dnf install bluez-libs-devel
```

Also, check you have the bluetooth daemon running:

```
systemctl status bluetooth.service
```

#### Ubuntu:

```
sudo apt-get install libbluetooth-dev
```
