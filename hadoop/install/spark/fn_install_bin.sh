#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp ../../spark-1.6.1-bin-hadoop2.6.tgz root@$host:/opt/
  ssh root@$host '
    set -e -x
    cd /opt
    tar -xf spark-1.6.1-bin-hadoop2.6.tgz
    chown -R root:root spark-1.6.1-bin-hadoop2.6
    rm spark-1.6.1-bin-hadoop2.6.tgz
    mv spark-1.6.1-bin-hadoop2.6 spark-1.6.1
  '
done
