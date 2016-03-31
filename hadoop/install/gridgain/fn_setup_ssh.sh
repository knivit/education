#/bin/bash
set -e -x

source ../../env.sh

hosts=$@
[ -z "$hosts" ] && hosts="$DEFAULT_HOSTS"

for host in $hosts; do
  echo "=== $host ==="
  scp ../id_dsa ../id_dsa.pub root@$host:/root/
  ssh root@$host '
    set -e -x
    mkdir -p /home/gridgain/.ssh
    cp id_dsa id_dsa.pub /home/gridgain/.ssh/
    cat /home/gridgain/.ssh/id_dsa.pub >> /home/gridgain/.ssh/authorized_keys
    chown -R gridgain:gridgain /home/gridgain/.ssh
    chmod 0600 /home/gridgain/.ssh/authorized_keys
  '
done
