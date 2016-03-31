#/bin/bash
set -e -x

source ../../env.sh

hosts=$@
[ -z "$hosts" ] && hosts="$DEFAULT_HOSTS"

for host in $hosts; do
  echo "=== $host ==="
  scp -r -q ../../configs/ignite/* root@$host:/opt/apache-ignite-fabric-1.5.0/config
  scp ../../configs/profile/ignite.sh root@$host:/etc/profile.d/
done
