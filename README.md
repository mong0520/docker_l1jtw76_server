* Goal:
Host: MYSQL server installed
Guest: docker with L1JTW7.6C server running

[Host]
1. Create a new database 'l1jdb' and import data, existing tables and database 'l1jdb' will be dropped
> mysql -u root -p < db/l1jdb.sql
Note: /etc/mysql/mysql.conf.d/mysqld.cnf to #bind-address for allow docker container to access host mysql

2. Build dockerfile
> ./build_server.sh <db_host> <db_account> <db_password>

3. Start the docker image as Lineage 7.6c game server.
> ./start_docker.sh
