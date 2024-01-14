#!/bin/bash

ROOT_PATH="/home/ec2-user"
JAR="$ROOT_PATH/boriPaw-0.0.1-SNAPSHOT.jar"

APP_LOG="$ROOT_PATH/boriPaw-0.0.1-SNAPSHOT.jar.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

NOW=$(date +%c)

echo "[$NOW] $JAR 복사" >> $START_LOG
cp $ROOT_PATH/build/libs/boriPaw-0.0.1-SNAPSHOT.jar $JAR

echo "[$NOW] > $JAR 실행" >> $START_LOG
nohup nohup java -jar -Dspring.profiles.active=product -Dspring.config.location=file:/home/ec2-user/application.yml $JAR > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG