#/bin/bash
set -e -x

mode=$1
[ -z "$mode" ] && { echo "mode is empty"; exit 1; }

ssh hdfs@cos7-01 /opt/hadoop-2.7.2/sbin/${mode}-dfs.sh
