#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp -r -q ../../configs/hadoop/* root@$host:/opt/hadoop-2.7.2/etc/hadoop/
  scp ../../configs/profile/hadoop.sh root@$host:/etc/profile.d/
done
