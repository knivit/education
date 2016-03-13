#/bin/bash
set -e -x

mode=$1
shift
hosts=$@
[ -z "$mode" ] && { echo "mode is empty"; exit 1; }
[ -z "$hosts" ] && hosts="cos7-01 cos7-02"

for host in $hosts; do
  if [ "$mode" = "start" ]; then
    "/cygdrive/c/Program Files/Oracle/VirtualBox/VBoxManage.exe" startvm $host
  else
    ssh root@$host shutdown now
  fi
done
