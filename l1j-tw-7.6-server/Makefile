IMAGE_NAME ?= l1jtw
L1JDB_HOST ?= localhost
L1JDB_ACCOUNT ?= l1jtw
L1JDB_PASSWORD ?= P@ssw0rd


build:
	@docker build -t $(IMAGE_NAME) \
	--build-arg L1JDB_HOST=$(L1JDB_HOST) --build-arg L1JDB_ACCOUNT=$(L1JDB_ACCOUNT) --build-arg L1JDB_PASSWORD=$(L1JDB_PASSWORD) \
	.

run:
	@docker run --rm \
	  -it $(IMAGE_NAME) \
	  /bin/bash


