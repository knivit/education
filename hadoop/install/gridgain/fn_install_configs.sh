#/bin/bash
set -e -x

source ../../env.sh

hosts=$@
[ -z "$hosts" ] && hosts="$DEFAULT_HOSTS"

for host in $hosts; do
  echo "=== $host ==="
  scp -r -q ../../configs/gridgain/* root@$host:/opt/gridgain-7.5.8/config
  scp ../../configs/profile/gridgain.sh root@$host:/etc/profile.d/
done
