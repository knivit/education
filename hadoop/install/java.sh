#/bin/bash
set -e -x

hosts=$1
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

source ../env.sh

for host in $hosts; do
  scp $DISTR_PATH/jdk-8u73-linux-x64.rpm root@$host:/opt/
  ssh root@$host '
    set -e -x
    cd /opt
    rpm -ivh jdk-8u73-linux-x64.rpm
    rm jdk-8u73-linux-x64.rpm
  '

  scp ../configs/profile/java.sh root@$host:/etc/profile.d/
done
