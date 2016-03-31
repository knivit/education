#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp ../id_dsa ../id_dsa.pub root@$host:/root/
  ssh root@$host '
    set -e -x
    mkdir -p /home/ignite/.ssh
    cp id_dsa id_dsa.pub /home/ignite/.ssh/
    cat /home/ignite/.ssh/id_dsa.pub >> /home/ignite/.ssh/authorized_keys
    chown -R ignite:ignite /home/ignite/.ssh
    chmod 0600 /home/ignite/.ssh/authorized_keys
  '
done
