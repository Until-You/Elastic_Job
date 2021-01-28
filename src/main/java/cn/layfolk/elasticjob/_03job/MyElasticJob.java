package cn.layfolk.elasticjob._03job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author Daimao
 * @Date 2021/1/27
 * @desc
 */
@Component
public class MyElasticJob implements SimpleJob {

    //会根据cron表达式执行
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("springboot定时任务执行了。"+ LocalDateTime.now());
    }
}
