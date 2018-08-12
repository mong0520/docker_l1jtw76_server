DB_ACCOUNT ?= root
DB_PASSWORD ?= emanager
DB_HOST ?= 172.17.0.1

build:
	docker build -t l1jtw --build-arg L1JDB_HOST=$(DB_HOST) --build-arg L1JDB_ACCOUNT=$(DB_ACCOUNT) --build-arg L1JDB_PASSWORD=$(DB_PASSWORD) .

run:
	docker run -d --rm -p 2000:2000 -it l1jtw

run_debug:
	docker run --rm -v $(PWD):/mnt -p 2000:2000 -p 80:80 -it l1jtw /bin/bash

release:
	$(shell aws ecr get-login --no-include-email --region ap-northeast-1)
	docker tag l1jtw:latest 640110695382.dkr.ecr.ap-northeast-1.amazonaws.com/poc:latest
	docker push 640110695382.dkr.ecr.ap-northeast-1.amazonaws.com/poc:latest


