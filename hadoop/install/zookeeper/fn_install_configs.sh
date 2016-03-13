#/bin/bash
set -e -x

host=$1
[ -z "$host" ] && { echo "host is empty"; exit 1; }

scp -r -q ../../configs/zookeeper/* root@$host:/opt/zookeeper-3.4.8/conf/
scp ../../configs/profile/zookeeper.sh root@$host:/etc/profile.d/
