package xyz.zix.spider.repo.ds;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.SneakyThrows;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MySqlDs {

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/spider?characterEncoding=utf8&useSSL=false");
        dataSource.setUsername("spider");
        dataSource.setPassword("123");
        return dataSource;
    }

    @Bean
    public DataSource ktDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/kt?characterEncoding=utf8&useSSL=false");
        dataSource.setUsername("spider");
        dataSource.setPassword("123");
        return dataSource;
    }


    @Bean
    @SneakyThrows
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
        return init(dataSource);
    }

    @Bean
    @SneakyThrows
    public SqlSessionFactory ktSqlSessionFactory(DataSource ktDataSource) {
        return init(ktDataSource);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("xyz.zix.spider.repo.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean
    public MapperScannerConfigurer ktMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("xyz.zix.spider.repo.kt.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("ktSqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @SneakyThrows
    private  SqlSessionFactory init(DataSource dataSource) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(SqlLog.class);
        factoryBean.setConfiguration(configuration);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        factoryBean.setPlugins(interceptor);
        return factoryBean.getObject();
    }


}
