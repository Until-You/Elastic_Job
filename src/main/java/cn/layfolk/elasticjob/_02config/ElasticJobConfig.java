package cn.layfolk.elasticjob._02config;

import cn.layfolk.elasticjob._03job.FileDataflowJob;
import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * job调度器配置
 * @Author Daimao
 * @Date 2021/1/27
 * @desc
 */
@Configuration
public class ElasticJobConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CoordinatorRegistryCenter registryCenter;


    private LiteJobConfiguration createJobConfig(Class<? extends ElasticJob> clazz, //任务类的字节码
                                                 String cron, //cron表达式
                                                 int shrdingTotalCount, //分片个数
                                                 String shardingParamter, //分片参数
                                                 Boolean isDataflowType  //是否是DataflowJob类型
    ) {
        JobCoreConfiguration.Builder builder = JobCoreConfiguration.newBuilder(clazz.getSimpleName(), cron, shrdingTotalCount);
        if (!StringUtils.isEmpty(shardingParamter)){
            //如果带了分片参数，需要设置进去
            builder.shardingItemParameters(shardingParamter);
        }

        JobTypeConfiguration jobTypeConfiguration = null;
        if (isDataflowType){
            //是dataflowjob类型
            jobTypeConfiguration = new DataflowJobConfiguration(builder.build(), clazz.getCanonicalName(),true);
        }else{
            //不是dataflowjob类型
            //定义SIMPLE类型配置
            jobTypeConfiguration =  new SimpleJobConfiguration(builder.build(), clazz.getCanonicalName());
        }

        //定义Lite作业根配置
        //overwrite(true) 代码的配置可以覆盖zookeeper配置
        //如果没有配置，第一次配置的分片参数是1，后续修改这个值，不会覆盖zookeeper配置的，任务即使代码修改成3，始终都是按1来执行。
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(jobTypeConfiguration).overwrite(true).build();
        return simpleJobRootConfig;

    }

//    @Bean(initMethod = "init")
//    public SpringJobScheduler initMyElasticJobBean(MyElasticJob myElasticJob){
            //自己的job,注册中心，job的配置
//        return new SpringJobScheduler(myElasticJob, registryCenter, createJobConfig(myElasticJob.getClass(),"0/5 * * * * ?",1, null));
//    }

    //多分片，多线程处理
//    @Bean(initMethod = "init")
//    public SpringJobScheduler initFileCustomJobBean(FileCustomJob fileCustomJob){
//        return new SpringJobScheduler(fileCustomJob, registryCenter, createJobConfig(fileCustomJob.getClass(),"0 0/1 * * * ?",4, "0=text,1=image,2=radio,3=vedio"));
//    }

    //dataflowJob   多次抓取数据
    @Bean(initMethod = "init")
    public SpringJobScheduler initFileDataflowJobBean(FileDataflowJob fileDataflowJob){

        //增加任务事件追踪配置
        JobEventRdbConfiguration jobEventRdbConfiguration = new JobEventRdbConfiguration(dataSource);
        return new SpringJobScheduler(fileDataflowJob, registryCenter, createJobConfig(fileDataflowJob.getClass(),"0 0/1 * * * ?",1, null,true),jobEventRdbConfiguration);
    }

}
