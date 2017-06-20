#!/bin/bash

L1JDB_HOST=$1
L1JDB_ACCOUNT=$2
L1JDB_PASSWORD=$3

docker build -t l1jtw --build-arg L1JDB_HOST=$1 --build-arg L1JDB_NAME=l1jdb --build-arg L1JDB_ACCOUNT=$2 --build-arg L1JDB_PASSWORD=$3 .
