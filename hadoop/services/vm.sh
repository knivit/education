#/bin/bash

# set -e : Commented, as "shutdown now" returns non-zero exit code
set -x

source ../env.sh

mode=$1
shift
hosts=$@
[ -z "$mode" ] && { echo "mode is empty"; exit 1; }
[ -z "$hosts" ] && hosts="$DEFAULT_HOSTS"

for host in $hosts; do
  if [ "$mode" = "start" ]; then
    if [ "$host" = "cos7-03" ]; then
      # Ubuntu host
      ssh egor@ubun-01 "VBoxHeadless --startvm $host" &
    else
      # Windows host
      "/cygdrive/c/Program Files/Oracle/VirtualBox/VBoxHeadless.exe" --startvm $host &
    fi
  else
    ssh root@$host shutdown now
  fi
done
