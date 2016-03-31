#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  echo "=== $host ==="
  scp ../id_dsa ../id_dsa.pub root@$host:/root/
  ssh root@$host '
    set -e -x
    mkdir -p /home/hdfs/.ssh
    cp id_dsa id_dsa.pub /home/hdfs/.ssh/
    cat /home/hdfs/.ssh/id_dsa.pub >> /home/hdfs/.ssh/authorized_keys
    chown -R hdfs:hdfs /home/hdfs/.ssh
    chmod 0600 /home/hdfs/.ssh/authorized_keys
  '
done
