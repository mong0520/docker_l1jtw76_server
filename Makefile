DB_ACCOUNT ?= root
DB_PASSWORD ?= emanager
DB_HOST ?= 172.17.0.1

build:
	@docker build -t l1jtw --build-arg L1JDB_HOST=$(DB_HOST) --build-arg L1JDB_ACCOUNT=$(DB_ACCOUNT) --build-arg L1JDB_PASSWORD=$(DB_PASSWORD) .

run:
	@docker run -d --rm -p 2000:2000 -p 23:23 -it l1jtw

run_debug:
	@docker run --rm -v $(PWD):/mnt -p 2000:2000 -p 80:80 -it l1jtw /bin/bash

release:
	$(shell aws ecr get-login --no-include-email --region ap-northeast-1)
	@docker tag l1jtw:latest 640110695382.dkr.ecr.ap-northeast-1.amazonaws.com/poc:latest
	@docker push 640110695382.dkr.ecr.ap-northeast-1.amazonaws.com/poc:latest
	@aws ecs register-task-definition --family first-run-task-definition --container-definitions "[{\"memoryReservation\":512,\"environment\":[],\"name\":\"l1jtw\",\"links\":[],\"mountPoints\":[],\"image\":\"640110695382.dkr.ecr.ap-northeast-1.amazonaws.com\/poc:latest\",\"logConfiguration\":{\"logDriver\":\"awslogs\",\"options\":{\"awslogs-region\":\"ap-northeast-1\",\"awslogs-stream-prefix\":\"ecs\",\"awslogs-group\":\"\/ecs\/first-run-task-definition\"}},\"essential\":true,\"portMappings\":[{\"protocol\":\"tcp\",\"containerPort\":2000,\"hostPort\":2000}],\"entryPoint\":[],\"command\":[],\"cpu\":0,\"volumesFrom\":[]}]" --requires-compatibilities FARGATE --network-mode awsvpc --execution-role-arn arn:aws:iam::640110695382:role/ecsTaskExecutionRole --cpu 1024 --memory 1024

deploy:
	@aws ecs update-service --cluster neil-poc --servic l1jtw-service --task-definition first-run-task-definition --desired-count 1 --network-configuration "awsvpcConfiguration={subnets=[subnet-33ebd445,subnet-28681770],securityGroups=[sg-07f6bec33de7a1662],assignPublicIp=ENABLED}"
