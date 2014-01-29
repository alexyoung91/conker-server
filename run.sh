if [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    java -cp ".:bin/:lib/javax.servlet-3.0.0.v201112011016.jar:lib/jetty-all-9.0.4.v20130625.jar:lib/mysql-connector-java-5.1.28-bin.jar" co.conker.ConkerServer
else
	java -cp ".;bin/;lib/javax.servlet-3.0.0.v201112011016.jar;lib/jetty-all-9.0.4.v20130625.jar;lib/mysql-connector-java-5.1.28-bin.jar" co.conker.ConkerServer
fi
