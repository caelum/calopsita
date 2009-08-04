#!/bin/sh

~/jetty/bin/jetty.sh stop

cd hsqldb
./hsqldb.sh

sleep 3
rm -rf ~/calopsita-lastBuild/*
unzip -o ~/calopsita-staging.war -d ~/calopsita-lastBuild
rm ~/calopsita-lastBuild/WEB-INF/lib/jstl*.jar ~/calopsita-lastBuild/WEB-INF/lib/standard*.jar
~/jetty/bin/jetty.sh start
