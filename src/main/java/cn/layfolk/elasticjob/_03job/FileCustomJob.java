package cn.layfolk.elasticjob._03job;

import cn.layfolk.elasticjob.entity.TFileCustom;
import cn.layfolk.elasticjob.mapper.TFileCustomMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @Author Daimao
 * @Date 2021/1/27
 * @desc
 */
@Component
public class FileCustomJob implements SimpleJob {
    @Autowired
    private TFileCustomMapper fileCustomMapper;

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        //通过分片上下文参数
        //System.out.println("getJobName-->"+shardingContext.getJobName());
        //System.out.println("getJobParameter-->"+shardingContext.getJobParameter());
        System.out.println("getShardingParameter-->"+shardingContext.getShardingParameter());
        //System.out.println("getTaskId-->"+shardingContext.getTaskId());
        System.out.println("getShardingItem-->"+shardingContext.getShardingItem());
        //System.out.println("getShardingTotalCount-->"+shardingContext.getShardingTotalCount());//4
        //System.out.println("ThreadId-->"+Thread.currentThread().getId());//4
        System.out.println("=======================================================");
        dowork(shardingContext.getShardingParameter());
        System.out.println("--------------------------------------------------------");
    }

    private void dowork(String shardingParameter) {

        QueryWrapper<TFileCustom> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(TFileCustom::getBackedUp, 0)
                .eq(TFileCustom::getType, shardingParameter);
        List<TFileCustom> tFileCustoms = fileCustomMapper.selectList(wrapper);
        System.out.println("类型为："+shardingParameter+",这次需要处理的文件个数:"+tFileCustoms.size());
        tFileCustoms.stream().peek(fileCustom->{
            backup(fileCustom);
        }).collect(Collectors.toList());

    }

    private void backup(TFileCustom fileCustom) {
        try {
            //模拟备份操作，比较耗时，需要1s
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("此次备份文件id："+fileCustom.getId()+",类型:"+fileCustom.getType());

        //修改文件的backedUp属性，修改备份为1
        fileCustom.setBackedUp(1);
        fileCustomMapper.updateById(fileCustom);
    }
}
