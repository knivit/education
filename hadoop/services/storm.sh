#/bin/bash
set -e -x

mode=$1
[ -z "$mode" ] && { echo "mode is empty"; exit 1; }

ssh storm@cos7-01 /opt/apache-storm-0.10.0/bin/storm nimbus &
ssh storm@cos7-01 /opt/apache-storm-0.10.0/bin/storm supervisor &
ssh storm@cos7-02 /opt/apache-storm-0.10.0/bin/storm supervisor &
