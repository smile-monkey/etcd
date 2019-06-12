# etcd
etcd相关测试

ETCD_VER=v3.3.12

# choose either URL
GOOGLE_URL=https://storage.googleapis.com/etcd
GITHUB_URL=https://github.com/etcd-io/etcd/releases/download
DOWNLOAD_URL=${GITHUB_URL}
UNZIP_PATH=/tmp/etcd-download

rm -f /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz
rm -rf ${UNZIP_PATH} && mkdir -p ${UNZIP_PATH}

curl -L ${DOWNLOAD_URL}/${ETCD_VER}/etcd-${ETCD_VER}-linux-amd64.tar.gz -o /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz
tar xzvf /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz -C ${UNZIP_PATH} --strip-components=1
rm -f /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz

${UNZIP_PATH}/etcd --version
ETCDCTL_API=3 ${UNZIP_PATH}/etcdctl version

# write,read to etcd
ETCDCTL_API=3 /usr/local/src/etcd-v3.3.12-linux-amd64/etcdctl --endpoints=localhost:2379 put foo bar
ETCDCTL_API=3 /usr/local/src/etcd-v3.3.12-linux-amd64/etcdctl --endpoints=localhost:2379 get foo

################################################################################################
## 环境变量
# vi /etc/profile 
# export ETCDCTL_API=3
# source /etc/profile

cp -r /tmp/etcd-download /usr/local/src/etcd-v3.3.12-linux-amd64


################################################################################################
## 创建集群启动脚本
touch /usr/lib/systemd/system/etcd.service
echo '
[Unit]
Description=Etcd Server
After=network.target
After=network-online.target
Wants=network-online.target


[Service]
Type=notify
WorkingDirectory=/usr/local/src/etcd-v3.3.12-linux-amd64
EnvironmentFile=-/usr/local/src/etcd-v3.3.12-linux-amd64/etcd.conf
# set GOMAXPROCS to number of processors
ExecStart=/usr/local/src/etcd-v3.3.12-linux-amd64/etcd \
	--data-dir=data.etcd --name infra0 \
	--initial-advertise-peer-urls http://192.168.2.9:2380 --listen-peer-urls http://192.168.2.9:2380 \
	--advertise-client-urls http://192.168.2.9:2379,http://127.0.0.1:2379 --listen-client-urls http://192.168.2.9:2379 \
	--initial-cluster infra0=http://192.168.2.9:2380 \
	--initial-cluster-state new --initial-cluster-token etcd-cluster1 
Restart=on-failure
LimitNOFILE=65536


[Install]
WantedBy=multi-user.target
' > /usr/lib/systemd/system/etcd.service

systemctl daemon-reload
systemctl enable etcd
systemctl start etcd

ETCDCTL_API=3 /usr/local/src/etcd-v3.3.12-linux-amd64/etcdctl --endpoints=localhost:2379 member list


/usr/bin/kube-apiserver --logtostderr=true --v=0 --etcd-servers=http://192.168.2.30:2379,http://192.168.2.31:2379 --insecure-bind-address=127.0.0.1 --allow-privileged=false --service-cluster-ip-range=172.16.0.0/24 --admission-control=NamespaceLifecycle,NamespaceExists,LimitRanger,SecurityContextDeny,ServiceAccount,ResourceQuota ?service-node-port-range=1-65535 --insecure-bind-address=0.0.0.0


