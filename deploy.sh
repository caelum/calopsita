#!/bin/sh

killall -15 java

cd hsqldb
nohup java -cp hsqldb.jar org.hsqldb.Server -database.0 file:calopsita -dbname.0 calopsita -port 9005 &

sleep 3
rm -rf ~/calopsita-lastBuild/*
unzip -o ~/calopsita-staging.war -d ~/calopsita-lastBuild
rm ~/calopsita-lastBuild/WEB-INF/lib/jstl*.jar ~/calopsita-lastBuild/WEB-INF/lib/standard*.jar
~/jetty/bin/jetty.sh start
