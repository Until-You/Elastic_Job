package cn.layfolk.elasticjob;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;


//代码生成器
public class LayfolkCode {

    public static void main(String[] args) {
        // 需要构建一个 代码自动生成器对象        
        AutoGenerator mpg = new AutoGenerator(); // 配置策略
        // 1、全局配置        
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//当前用户目录
        gc.setOutputDir(projectPath + "/src/main/java");//输出目录
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖        
        gc.setServiceName("%sService"); // 去Service的I前缀        
        gc.setIdType(IdType.ID_WORKER);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);
        //2、设置数据源        
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/elastic-job-demo?useSSL=false&useUnicode=true&AllowPublicKeyRetrieval=True&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("admin");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        //3、包的配置        
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName("blog");
        pc.setParent("cn.layfolk.elasticjob");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
        //4、策略配置        
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("t_file_custom"); // 设置要映射的表名        
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); // 自动lombok；
        strategy.setLogicDeleteFieldName("deleted");// 自动填充配置        
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);
        // 乐观锁      
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true); // localhost:8080/hello_id_2        
        mpg.setStrategy(strategy);
        mpg.execute(); //执行
    }
}
