#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

source ../../env.sh

for host in $hosts; do
  echo "=== $host ==="
  scp $DISTR_PATH/spark-1.6.1-bin-2.7.2.tgz root@$host:/opt/
  ssh root@$host '
    set -e -x
    cd /opt
    rm -r -f spark-1.6.1
    tar -xf spark-1.6.1-bin-2.7.2.tgz
    chown -R root:root spark-1.6.1-bin-2.7.2
    mv spark-1.6.1-bin-2.7.2 spark-1.6.1
    rm spark-1.6.1-bin-2.7.2.tgz
  '
done
