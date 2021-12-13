* Goal:

Host: MYSQL server installed

Guest: docker with L1JTW 3.80C server running

[Host]
1. Create a new database 'l1jtw' and import data, existing tables and database 'l1jtw' will be dropped
> mysql -u root -p < db/l1jtw.sql

1.1. Change mysql permission
```
use mysql;
update user set host='%';
flush privileges;
```

Note: /etc/mysql/mysql.conf.d/mysqld.cnf to #bind-address for allow docker container to access host mysql

2. Build dockerfile
> make build DB_ACCOUNT=your_db_account DB_PASSWORD=your_db_password
> make run
