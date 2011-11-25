# Use with PHP/Java Bridge

If you want to use jasperreports with PHP/Java Bridge, follow these steps :

 1. Install _php-java-bridge_ package (from repo.ada-consult.com repository)
 1. Edit the file /etc/php-java-bridge/php-java-bridge.conf to set the EXTDIRS variable to this value: _/usr/share/jasperreports:/usr/share/java_
 1. Restart PHP/Java Bridge: `sudo /etc/init.d/php-java-bridge restart`

Optionnaly, you can install the _libmysql-java_ package to have access to the MySQL Connector for Java.
