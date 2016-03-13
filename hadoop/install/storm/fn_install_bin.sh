#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp ../../apache-storm-0.10.0.tar.gz root@$host:/opt/
  ssh root@$host '
    set -e -x
    cd /opt
    tar -xf apache-storm-0.10.0.tar.gz
    chown -R root:root apache-storm-0.10.0
    rm apache-storm-0.10.0.tar.gz
  '
done
