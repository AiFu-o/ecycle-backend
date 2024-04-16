package com.ecycle.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description TODO
 */
@Configuration
@ComponentScan("com.ecycle.common")
public class AutoConfiguration {

}
