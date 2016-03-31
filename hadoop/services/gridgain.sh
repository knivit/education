#/bin/bash
set -e -x

source ../env.sh

mode=$1
shift
hosts=$@

[ -z "$mode" ] && { echo "mode is empty"; exit 1; }
[ -z "$hosts" ] && hosts="$DEFAULT_HOSTS"

for host in $hosts; do
  if [ "$mode" = "start" ]; then
    ssh gridgain@${host} /opt/gridgain-7.5.8/bin/ignite.sh &
  else
    ssh gridgain@${host} 'pid=`ps -C java -o pid=`; kill $pid'
  fi
done
