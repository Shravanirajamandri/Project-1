#!/bin/bash
set -euo pipefail
export JAVA_HOME=${JAVA_HOME:-/usr/lib/jvm/java-21-amazon-corretto.x86_64}
export PATH="$JAVA_HOME/bin:$PATH"
for d in services/*; do echo "Building $d"; (cd "$d" && mvn clean package -DskipTests); done
