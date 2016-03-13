#/bin/bash
set -e -x

hosts=$1
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  ssh root@$host '
    set -e -x
    mkdir /u01
    chmod -R a+rw /u01
  '
done
