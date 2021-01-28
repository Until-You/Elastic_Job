# Elastic_Job

基于quartz的elastic_job ，xxl_job

elastic_job由当当开源

### 认识 Elastic-Job：

　　任务调度高级需求,Quartz 的不足：

1. 作业只能通过 DB 抢占随机负载，无法协调
2. 任务不能分片——单个任务数据太多了跑不完，消耗线程，负载不均
3. 作业日志可视化监控、统计

### 1.任务类型(Job)

任务类型分为：SimpleJob、DataflowJob、ScriptJob  代码里只有前两种

SimpleJob: 简单任务，一次性执行完任务

DataflowJob: 用于处理数据流，数据量大的场景，分批次处理。

### 2.JobConfig

配置job类型，调度器Schedule配置，触发规则等

### 3.注册中心配置(zookeeper)

每个job都是挂载在zk上的。

### 4.持久化等



参考：https://www.cnblogs.com/wuzhenzhao/p/13299497.html