package cn.layfolk.elasticjob._01demo;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.time.LocalDateTime;

/**
 * @Author Daimao
 * @Date 2021/1/27
 * @desc
 */
public class MyElasticJob implements SimpleJob {

    //会根据cron表达式执行
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("定时任务执行了。"+ LocalDateTime.now());
    }
}
