#!/bin/bash
set -euo pipefail
docker compose stop account-ingestion-service data-validation-service customer-eligibility-service account-segmentation-service enrollment-service data-transformation-service transaction-notification-service || true
