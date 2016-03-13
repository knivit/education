#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp -r -q ../../configs/storm/* root@$host:/opt/apache-storm-0.10.0/conf
  scp ../../configs/profile/storm.sh root@$host:/etc/profile.d/
done
