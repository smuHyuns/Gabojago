# Base image
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# SSH 클라이언트 및 MySQL 클라이언트 설치
RUN apt-get update && apt-get install -y openssh-client default-mysql-client && rm -rf /var/lib/apt/lists/*

# PEM 파일 저장 및 권한 설정
RUN mkdir -p /tmp/ssh && chmod 700 /tmp/ssh
COPY ssh-keys/ec2_key_pair.pem /tmp/ssh/ec2_key_pair.pem
RUN chmod 600 /tmp/ssh/ec2_key_pair.pem

# ARG 및 ENV 설정
ARG SSH_HOST
ENV SSH_HOST=${SSH_HOST}

# known_hosts 파일 설정
RUN ssh-keyscan -H ${SSH_HOST} >> /tmp/ssh/known_hosts

# 애플리케이션 JAR 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 추가 환경 변수 설정
ARG RDS_ENDPOINT
ARG USER
ENV RDS_ENDPOINT=${RDS_ENDPOINT}
ENV USER=${USER}

# 노출 포트
EXPOSE 8080

# SSH 터널링 실행 및 애플리케이션 실행
CMD ["sh", "-c", "ssh -o StrictHostKeyChecking=no -i /tmp/ssh/ec2_key_pair.pem -N -L 3308:$RDS_ENDPOINT:3306 $USER@$SSH_HOST & java -jar app.jar"]
