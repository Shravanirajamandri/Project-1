# Infosys Financial Enrollment Platform - EC2 Training Project

A complete interview-oriented modernization/training implementation of a financial account enrollment flow. It contains seven Spring Boot microservices, PostgreSQL durable storage, Redis transient caching/status, Docker Compose, Kubernetes manifests, Terraform for a lab EC2 instance, Jenkinsfile, and a Postman collection.

> This is a training implementation based on the logical architecture discussed in the project sessions. It is not confidential Infosys production source code.

## Architecture
Account Management System -> Account Ingestion -> Data Validation -> Customer Eligibility -> Account Segmentation -> Enrollment -> Data Transformation -> Transaction Notification -> Transaction Processing System

## Services
- account-ingestion-service : 8081
- data-validation-service : 8082
- customer-eligibility-service : 8083
- account-segmentation-service : 8084
- enrollment-service : 8085
- data-transformation-service : 8086
- transaction-notification-service : 8087

## Redis use case
The ingestion service creates a processing ID and stores the incoming request/status in Redis with a TTL. This gives a fast transient status lookup without querying PostgreSQL for every status request. PostgreSQL remains the durable system of record for completed enrollment records.

## Database
PostgreSQL database: financial_enrollment
User: financial_user
Password: financial_pass
Table: enrollments

## Quick start on Amazon Linux 2023 EC2
```bash
sudo dnf update -y
sudo dnf install -y java-21-amazon-corretto java-21-amazon-corretto-devel maven git unzip docker
sudo systemctl enable --now docker
sudo usermod -aG docker $USER
newgrp docker
export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto.x86_64
export PATH=$JAVA_HOME/bin:$PATH
java -version
mvn -version

git clone <your-repository-url>
cd infosys-financial-enrollment-platform-ec2
./scripts/build-all.sh
docker compose up -d postgres redis
./scripts/run-all.sh
```

Then import `postman/financial-enrollment-platform.postman_collection.json` and call POST `/api/accounts` on `http://<EC2_PUBLIC_IP>:8081`.

## Important lab cleanup
```bash
./scripts/stop-all.sh
docker compose down
# Use docker compose down -v only when you intentionally want to delete PostgreSQL/Redis volumes.
```
