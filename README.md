# SSLSocket
An SSL socket server and client in JAVA

## Installation
```shell script
git clone https://github.com/williamswhy/SSLSocket.git
```

## Usage
### Compile & prepare files
```shell script
mkdir bin
javac -d bin src/client/*.java
javac -d bin src/server/*.java

```
### Start server & client
server:
```shell script
java server.SocketListener
```
client:
```shell script
java client.SocketClient [address] [port] [message]
```
### Generate SSL
Create a server keystore file:
```shell script
keytool -genkey -keystore sslserverkeys -keyalg RSA
```
Export the key as a cert:
```shell script
keytool -export -keystore sslserverkeys -file cert.cer -keyalg RSA
```
Add the cert to the trust store of the client:
```shell script
keytool -import -keystore sslclienttrust -file cert.cer -keyalg RSA
```