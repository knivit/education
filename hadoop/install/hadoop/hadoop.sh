#/bin/bash
set -e -x

hosts=$@
[ -z "$hosts" ] && { echo "hosts is empty"; exit 1; }

./fn_create_dirs.sh $hosts
./fn_install_bin.sh $hosts
./fn_install_configs.sh $hosts
./fn_create_users.sh $hosts
./fn_setup_ssh.sh $hosts

# Only on NameNode host, only on install
ssh root@$1 'sudo -u hdfs /opt/hadoop-2.7.2/bin/hdfs namenode -format tsoft-cluster'

echo "Hadoop installed, run fn_create_hdfs_users.sh to create HDFS users' home dir"
