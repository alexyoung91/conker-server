if [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    javac -cp "lib/servlet-api-2.5.jar:lib/jetty-all-9.0.4.v20130625.jar:lib/mysql-connector-java-5.1.28-bin.jar" src/co/conker/* -d bin
else
	javac -cp "lib/servlet-api-2.5.jar;lib/jetty-all-9.0.4.v20130625.jar;lib/mysql-connector-java-5.1.28-bin.jar" src/co/conker/* -d bin
fi
