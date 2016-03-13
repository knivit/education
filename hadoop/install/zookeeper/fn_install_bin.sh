#/bin/bash
set -e -x

host=$1
[ -z "$host" ] && { echo "host is empty"; exit 1; }

scp ../../zookeeper-3.4.8.tar.gz root@$host:/opt/
ssh root@$host '
  set -e -x
  cd /opt
  tar -xf zookeeper-3.4.8.tar.gz
  chown -R root:root zookeeper-3.4.8
  rm zookeeper-3.4.8.tar.gz
'
