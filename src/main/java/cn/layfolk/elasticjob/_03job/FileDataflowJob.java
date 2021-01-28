package cn.layfolk.elasticjob._03job;

import cn.layfolk.elasticjob.entity.TFileCustom;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * dataflowJob 多次抓取数据  simplejob是一次性抓取，数据量大，选这个
 * @Author Daimao
 * @Date 2021/1/28
 * @desc
 */
@Component
public class FileDataflowJob implements DataflowJob<TFileCustom> {

    @Autowired
//  private TFileCustomMapper fileCustomMapper;
    private BaseMapper baseMapper;

    //抓取数据
    @Override
    public List<TFileCustom> fetchData(ShardingContext shardingContext) {
        QueryWrapper<TFileCustom> wrapper = new QueryWrapper<>();
        String sql = "limit 0 ,"+"2";
        wrapper.lambda().eq(TFileCustom::getBackedUp,0).last(sql);
        List list = baseMapper.selectList(wrapper);
        System.out.println("抓取到的数据个数="+list.size());
        return list;
    }

    //处理数据
    @Override
    public void processData(ShardingContext shardingContext, List<TFileCustom> list) {
        list.stream().peek(this::backup).collect(Collectors.toList());
    }


    private void backup(TFileCustom fileCustom) {
        try {
            //模拟备份操作，比较耗时，需要1s
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("此次备份文件id："+fileCustom.getId()+",类型:"+fileCustom.getType());

        //修改文件的backedUp属性，修改备份1
        fileCustom.setBackedUp(1);
        baseMapper.updateById(fileCustom);
    }
}
