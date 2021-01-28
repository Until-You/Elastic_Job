package cn.layfolk.elasticjob._02config;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zookeeper的注册
 * @Author Daimao
 * @Date 2021/1/27
 * @desc
 */
@Configuration
public class RegistryCenterConfig {

    @Bean(initMethod = "init")
    public static CoordinatorRegistryCenter createRegistryCenter(@Value("${elastic-job.zookeeper-url}") String zookeeperUrl,
                                                                 @Value("${elastic-job.namespace}") String namespace) {
        //配置一下zookeeper地址
        // 参数1:地址      2:节点名(一般项目名)
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(zookeeperUrl, namespace);
        //设置zookeeper的超时时间
        zookeeperConfiguration.setSessionTimeoutMilliseconds(100);
        //创建注册中心配置
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        //初始化
        //regCenter.init();
        return regCenter;
    }

}
