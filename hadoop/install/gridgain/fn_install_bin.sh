#/bin/bash
set -e -x

source ../../env.sh

hosts=$@
[ -z "$hosts" ] && hosts="$DEFAULT_HOSTS"

source ../../env.sh

for host in $hosts; do
  echo "=== $host ==="
  scp $DISTR_PATH/gridgain-enterprise-fabric-7.5.8.zip root@$host:/opt/
  ssh root@$host '
    set -e -x
    cd /opt
    unzip -q gridgain-enterprise-fabric-7.5.8.zip
    mv gridgain-enterprise-fabric-7.5.8 gridgain-7.5.8
    chown -R root:root gridgain-7.5.8
    rm gridgain-enterprise-fabric-7.5.8.zip
  '
done
