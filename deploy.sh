#!/bin/sh

killall -15 java

cd hsqldb
java -cp hsqldb.jar org.hsqldb.Server -database.0 file:calopsita -dbname.0 calopsita -port 9005 &

sleep 3
rm -rf ~/calopsita/*
unzip -o ~/calopsita-staging.war -d ~/calopsita
rm ~/calopsita/WEB-INF/lib/jstl*.jar ~/calopsita/WEB-INF/lib/standard*.jar
~/jetty/bin/jetty.sh start
