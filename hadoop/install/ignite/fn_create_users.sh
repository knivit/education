#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  ssh root@$host '
    set -e -x
    useradd -m -U -G hadoop,users ignite
  '
done
