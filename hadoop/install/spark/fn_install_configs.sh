#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp -r -q ../../configs/spark/* root@$host:/opt/spark-1.6.1/conf
  scp ../../configs/profile/spark.sh root@$host:/etc/profile.d/
done
