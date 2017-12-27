#!/bin/bash

mysql -h ${L1JDB_HOST} -u ${L1JDB_ACCOUNT} -p${L1JDB_PASSWORD} < l1jdb.sql
