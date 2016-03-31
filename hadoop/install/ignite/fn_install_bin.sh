#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

source ../../env.sh

for host in $hosts; do
  echo "=== $host ==="
  scp $DISTR_PATH/apache-ignite-fabric-1.5.0.final-bin.zip root@$host:/opt/
  ssh root@$host '
    set -e -x
    cd /opt
    unzip -q apache-ignite-fabric-1.5.0.final-bin.zip
    mv apache-ignite-fabric-1.5.0.final-bin apache-ignite-fabric-1.5.0
    chown -R root:root apache-ignite-fabric-1.5.0
    rm apache-ignite-fabric-1.5.0.final-bin.zip
  '
done
