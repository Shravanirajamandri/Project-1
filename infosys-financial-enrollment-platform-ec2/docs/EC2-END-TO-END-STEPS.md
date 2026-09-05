# End-to-end EC2 lab

## 1. Launch
Use Amazon Linux 2023, at least t3.medium for a practical all-container lab, an existing key pair, and enough disk for images. In a short lab, keep the instance only for the session.

## 2. Install
```bash
chmod +x scripts/ec2-install-amazon-linux-2023.sh
./scripts/ec2-install-amazon-linux-2023.sh
newgrp docker
```
Verify `java -version` and `mvn -version` both show Java 21. If Maven still uses Java 17, run:
```bash
export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto.x86_64
export PATH=$JAVA_HOME/bin:$PATH
mvn -version
```

## 3. Build and start
```bash
./scripts/build-all.sh
docker compose up -d

docker compose ps
```

## 4. Test
Set `host=<EC2 public IP>` in Postman. The main entry point is `POST http://<EC2-IP>:8081/api/accounts`.
Example request:
```json
{"customerId":"CUST1001","accountId":"ACC5001","accountType":"Investment","status":"OPEN","assetValue":500000,"currentTier":"GOLD"}
```
The ingestion service returns a processing ID and stores status/request data in Redis.

For direct component testing, call ports 8082-8087 using the included Postman collection.

## 5. Verify Redis
```bash
docker exec -it financial-redis redis-cli
KEYS *
GET account:<processing-id>
HGETALL account-data:<processing-id>
TTL account:<processing-id>
```
Interview explanation: Redis is used as a fast transient status/cache layer. A TTL prevents old processing status from accumulating indefinitely.

## 6. Verify PostgreSQL
```bash
docker exec -it financial-postgres psql -U financial_user -d financial_enrollment
SELECT * FROM enrollments;
```
Interview explanation: PostgreSQL is the durable source of truth for enrollment records; Redis is not used as the durable enrollment database.

## 7. Troubleshooting
```bash
docker compose logs -f account-ingestion-service
docker compose logs -f enrollment-service
curl http://<EC2-IP>:8081/actuator/health
curl http://<EC2-IP>:8085/actuator/health
```
If a Java 21 compile error says `release version 21 not supported`, check `mvn -version`; Maven must be running on Java 21.

## 8. Kubernetes option
Build and push each service image to ECR, replace `REPLACE_ECR_IMAGE` in `k8s/*.yaml`, then:
```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml -f k8s/secret.yaml -f k8s/postgres-init-configmap.yaml
kubectl apply -f k8s/postgres.yaml -f k8s/redis.yaml
kubectl apply -f k8s/account-ingestion-service.yaml -f k8s/data-validation-service.yaml -f k8s/customer-eligibility-service.yaml -f k8s/account-segmentation-service.yaml -f k8s/enrollment-service.yaml -f k8s/data-transformation-service.yaml -f k8s/transaction-notification-service.yaml
kubectl get pods -n financial-platform
kubectl get svc -n financial-platform
```
For a quick lab test without a load balancer, use `kubectl port-forward`.

## 9. Terraform option
From an environment with AWS credentials and Terraform installed:
```bash
cd terraform
cp terraform.tfvars.example terraform.tfvars
# edit key_name
terraform init
terraform plan
terraform apply
```
The module-free Terraform creates an EC2 instance, security group, and uses the default VPC/subnet. The SG is intentionally broad for a temporary lab; restrict CIDRs in real environments.

## 10. Close the lab
Docker-only cleanup:
```bash
./scripts/stop-all.sh
docker compose down
```
Full data cleanup:
```bash
docker compose down -v
```
Terraform-created EC2 cleanup:
```bash
cd terraform
terraform destroy
```
Never use `down -v` or `terraform destroy` unless you are finished and want the data/infrastructure removed.
