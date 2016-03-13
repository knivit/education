#/bin/bash
set -e -x

host=$1
hdfs_user=$2
[ -z "$host" ] && { echo "host is empty"; exit 1; }
[ -z "$hdfs_user" ] && { echo "hdfs_user is empty"; exit 1; }

ssh root@$host '
  set -e -x
  sudo -u hdfs hadoop fs -mkdir -p /user/$hdfs_user
  sudo -u hdfs hadoop fs -chown -R tsoft:hadoop /user/$hdfs_user
'
