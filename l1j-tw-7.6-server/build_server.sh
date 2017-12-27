#!/bin/bash

L1JDB_ACCOUNT=$1
L1JDB_PASSWORD=$2
L1JDB_HOST=$(ip -4 addr show docker0 | grep -Po 'inet \K[\d.]+')

docker build -t l1jtw --build-arg L1JDB_HOST=$L1JDB_HOST --build-arg L1JDB_ACCOUNT=$L1JDB_ACCOUNT --build-arg L1JDB_PASSWORD=$L1JDB_PASSWORD .
