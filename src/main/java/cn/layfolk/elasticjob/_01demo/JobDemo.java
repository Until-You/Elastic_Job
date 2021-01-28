package cn.layfolk.elasticjob._01demo;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @Author Daimao
 * @Date 2021/1/27
 * @desc
 */
public class JobDemo {

    public static void main(String[] args) {

        //创建JobScheduler  调度器
        // 参数1:注册中心zookeeper配置
        // 参数2:任务相关配置
        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
    }

    // 启动zookeeper
    private static CoordinatorRegistryCenter createRegistryCenter() {
        //配置一下zookeeper地址               地址      节点名(一般项目名)
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration("localhost:2181", "elastic-job-demo");
        //设置zookeeper的超时时间
        zookeeperConfiguration.setSessionTimeoutMilliseconds(100);
        //创建注册中心配置
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        //初始化
        regCenter.init();
        return regCenter;
    }

    private static LiteJobConfiguration createJobConfiguration() {
        //参数 1.作业名称
        //参数 2.cron表达式
        //参数 3.分片数
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("MyJob", "0/5 * * * * ?", 1).build();
        //定义SIMPLE类型配置
        //第一个参数：作业配置
        //第二个参数：任务类的全限定名
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MyElasticJob.class.getCanonicalName());
        //定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
        return simpleJobRootConfig;

    }

}
