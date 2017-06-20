[Host]
1. Create a new database 'l1jdb' and import data
> mysql -u root -p < db/l1jdb.sql

2. Build dockerfile
> ./build.sh <db_host> <db_account> <db_password>

3. Start the docker image as Lineage 7.6c game server.
> ./start.sh 
