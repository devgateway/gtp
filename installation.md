# Installation Document

This is an example how to install GTP on a Linux with a systemd.

We are closely following the spring boot executable/fat jar documentation which provides an extremely nice way to run a jar as a linux system service. This works for both the old System V and the new systemd. You can find [the full Spring documentation here] (http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#deployment-service).

## VPS (virtual server) requirements

- CPU: 4 cores
- Memory: 8GB
- Disk: 128GB SSD

## Software Prerequisites

- Operating system: Ubuntu Server x64 16.04 LTS, Debian 10, CentOS 8 or upper
- PostgreSQL 11.7+
- OpenJDK 8

## Install dependencies

### Install PostgreSQL

PostgreSQL installation steps for Ubuntu: https://www.postgresql.org/download/linux/ubuntu/
Use postgresql-11.

Add DB user and database. Use or update credentials from [persistence application.properties](./persistence/src/main/java/org/devgateway/toolkit/persistence/application.properties).

### Install OpenJDK 8

`$ sudo apt install openjdk-8-jdk`


## Create user gtp
First add a service user for the program, we're not going to run the app with root:

`$ sudo useradd -r gtp`

## Download and compile the open source code from github.com

## Copy application jar

### (Option A) Copy the .jar from artifactory

http://artifactory.ampdev.net/artifactory/ad3-anacim-releases/org/devgateway/ad3/anacim/forms/

### (Option B) Generate the .jar using Maven
#### Install Maven

`$ sudo apt install maven`

### Get the source code

```
$ su - gtp
$ git clone https://github.com/devgateway/ad3-anacim.git
```

### Compile the code

```
$ cd ad3-anacim
$ git checkout master
$ mvn -Dmaven.javadoc.skip=true -Dmaven.test.skip=true install
```

### Copy artifact and config to startup locatinon

Copy jar and application properties from the forms to /opt/gtp.
```
$ cd ~
$ cp forms/target/forms-*-SNAPSHOT.jar /opt/gtp/gtp.jar
$ cp forms/src/main/java/org/devgateway/toolkit/forms/application.properties /opt/gtp/application.properties
```

### Edit configuration file application.properties

At the very list update:
```
ad3.password.recovery.baseUrl=<your website's URL>
logging.path=/var/log/gtp
```
You may also want to customize other properties from persistence by combining them under the same /otp/gtp/application.properties file.

Logs will be found in /var/log/gtp/spring.log. Log files will be rolled over when they reach size of 10MB.

Make sure user gtp can read anything in `/opt/gtp` folder and has write access to `/var/log/gtp` folder.

## Create systemd unit for GTP

Create /etc/system.d/system/gtp.service with the following contents:
```
[Unit]
Description=gtp
After=syslog.target network-online.target
BindsTo=postgresql.service

[Service]
User=gtp
ExecStart=/opt/gtp/gtp.jar
SuccessExitStatus=143
EnvironmentFile=/etc/default/gtp

[Install]
WantedBy=multi-user.target
```

Create environment variables file for AD3 at `/etc/default/gtp` and enable productionTons mode for Apache Wicket:
```
JAVA_OPTS="-Dwicket.configuration=deployment"
```

Here we can also change memory limits for the java process.

## Verify and Start the server

Start the service with:

`sudo systemctl start gtp`

Now you can verify that service is running with:
 
`curl -I http://localhost:8080/`

To stop the service one can use:

`sudo systemctl stop gtp`

Since we're running GTP as systemd service, standard output is most likely being redirected to journal.
To see the standard output:

`sudo journalctl -u gtp`

or to see the service output

`sudo journalctl -u gtp.service`

