#!/bin/bash
set -e
sudo dnf update -y
sudo dnf install -y java-21-amazon-corretto java-21-amazon-corretto-devel maven git unzip docker
sudo systemctl enable --now docker
sudo usermod -aG docker "$USER" || true
cat <<EOF | sudo tee /etc/profile.d/java21.sh
export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto.x86_64
export PATH=\$JAVA_HOME/bin:\$PATH
EOF
source /etc/profile.d/java21.sh
java -version
mvn -version
