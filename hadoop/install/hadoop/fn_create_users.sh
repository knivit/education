#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

for host in $hosts; do
  ssh root@$host '
    set -e -x
    useradd -m -U -G users hadoop
    useradd -m -G hadoop hdfs
    useradd -m -G hadoop yarn
  '
done
