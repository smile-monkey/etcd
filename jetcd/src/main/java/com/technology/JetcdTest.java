package com.technology;

import io.etcd.jetcd.*;
import io.etcd.jetcd.cluster.Member;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.lease.LeaseTimeToLiveResponse;
import io.etcd.jetcd.maintenance.AlarmMember;
import io.etcd.jetcd.maintenance.StatusResponse;
import io.etcd.jetcd.options.LeaseOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JetcdTest {
    public static void main(String[] args) {
//        KeyValueClient KV客户端 ，Etcd 作为一个 key - value 存储系统，Key - Value 客户端是最基本的客户端，进行 put、get、delete 操作。
//        testKeyValueClient();
//        Lease（租约）客户端，用于创建租约、续约、解约，以及检索租约的详情，如租约绑定的键值等信息。
//        testLeaseClient();
//        Watch监听客户端，可为 key 或者目录（前缀机制）创建 Watcher，Watcher 可以监听 key 的事件（put、delete 等），如果事件发生，可以通知客户端，客户端采取某些措施。
//        testWatchClient();
//        集群客户端，集群节点数量为大于 3 的奇数，支持获取集群成员列表、增加成员、删除成员等操作。
//        testClusterClient();
//        Maintenance客户端，Etcd 本质上是一个 key - value 存储系统，在一系列的 put、get、delete 及 compact 操作后，集群节点可能出现键空间不足等告警，通过 Maintenance
//        客户端，可以进行告警查询、告警解除、整理压缩碎片等操作。
//        testMaintenanceClient();
    }

}
