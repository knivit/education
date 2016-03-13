#/bin/bash
set -e -x

mode=$1
[ -z "$mode" ] && { echo "mode is empty"; exit 1; }

ssh spark@cos7-01 /opt/spark-1.6.1/sbin/${mode}-all.sh
