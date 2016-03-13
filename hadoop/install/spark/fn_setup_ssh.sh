#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp ../id_dsa ../id_dsa.pub root@$host:/root/
  ssh root@$host '
    set -e -x
    mkdir -p /home/spark/.ssh
    cp id_dsa id_dsa.pub /home/spark/.ssh/
    cat /home/spark/.ssh/id_dsa.pub >> /home/spark/.ssh/authorized_keys
    chown -R spark:spark /home/spark/.ssh
    chmod 0600 /home/spark/.ssh/authorized_keys
  '
done
