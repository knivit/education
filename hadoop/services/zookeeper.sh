#/bin/bash
set -e -x

mode=$1
[ -z "$mode" ] && { echo "mode is empty"; exit 1; }

ssh zookeeper@cos7-01 /opt/zookeeper-3.4.8/bin/zkServer.sh $mode
