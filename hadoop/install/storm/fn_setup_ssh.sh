#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp ../id_dsa ../id_dsa.pub root@$host:/root/
  ssh root@$host '
    set -e -x
    mkdir -p /home/storm/.ssh
    cp id_dsa id_dsa.pub /home/storm/.ssh/
    cat /home/storm/.ssh/id_dsa.pub >> /home/storm/.ssh/authorized_keys
    chown -R storm:storm /home/storm/.ssh
    chmod 0600 /home/storm/.ssh/authorized_keys
  '
done
