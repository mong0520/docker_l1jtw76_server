#!/bin/bash


PATH=.:/usr/local/bin:/usr/bin:/bin:/usr/games:/usr/local/jdk1.7.0/bin
export PATH
python health-check.py &
java -Xmx512m -Xincgc -jar l1jserver.jar
