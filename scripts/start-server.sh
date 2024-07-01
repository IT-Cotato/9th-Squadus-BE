#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"
docker stop squadus-server || true
docker rm squadus-server || true
docker pull 992382743841.dkr.ecr.ap-northeast-2.amazonaws.com/squadus-server:latest
docker run -d --name squadus-server -p 8080:8080 992382743841.dkr.ecr.ap-northeast-2.amazonaws.com/squadus-server:latest
echo "--------------- 서버 배포 끝 -----------------"