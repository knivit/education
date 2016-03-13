#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

./fn_install_bin.sh $hosts
./fn_install_configs.sh $hosts
./fn_create_users.sh $hosts
./fn_setup_ssh.sh $hosts
