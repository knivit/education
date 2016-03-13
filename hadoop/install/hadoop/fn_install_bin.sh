#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp ../../hadoop-2.7.2.tar.gz root@$host:/opt/
  ssh root@$host '
    set -e -x
    cd /opt
    tar -xf hadoop-2.7.2.tar.gz
    chown -R root:root hadoop-2.7.2
    rm hadoop-2.7.2.tar.gz
  '
done
