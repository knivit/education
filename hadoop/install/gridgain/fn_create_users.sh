#/bin/bash
set -e -x

source ../../env.sh

hosts=$@
[ -z "$hosts" ] && hosts="$DEFAULT_HOSTS"

for host in $hosts; do
  echo "=== $host ==="
  ssh root@$host '
    set -e -x
    useradd -m -U -G hadoop,users gridgain
  '
done
