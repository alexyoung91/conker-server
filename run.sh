java -cp "bin:lib/servlet-api-2.5.jar:lib/jetty-all-9.0.4.v20130625.jar:lib/mysql-connector-java-5.1.28-bin.jar" co.conker.Server

if [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    java -cp "bin:lib/servlet-api-2.5.jar:lib/jetty-all-9.0.4.v20130625.jar:lib/mysql-connector-java-5.1.28-bin.jar" co.conker.Server
else
	java -cp "bin;lib/servlet-api-2.5.jar;lib/jetty-all-9.0.4.v20130625.jar;lib/mysql-connector-java-5.1.28-bin.jar" co.conker.Server
fi
