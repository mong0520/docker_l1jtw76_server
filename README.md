[Host]
1. Create a new database 'l1jdb' and import data, existing tables and database 'l1jdb' will be dropped
> mysql -u root -p < db/l1jdb.sql

2. Build dockerfile
> ./build_server.sh <db_host> <db_account> <db_password>

3. Start the docker image as Lineage 7.6c game server.
> ./start_docker.sh
