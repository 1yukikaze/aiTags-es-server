package com.yukikaze.webuiesdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class threadConfig {
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        //int core = Runtime.getRuntime().availableProcessors();
        int core = 2;
        //设置核心线程数
        executor.setCorePoolSize(core);
        //设置最大线程数
        executor.setMaxPoolSize(core * 2 + 1);
        //除核心线程外的线程存活时间
        executor.setKeepAliveSeconds(3);
        //线程阻塞队列容量 允许最高20人排队
        executor.setQueueCapacity(20);
        //线程名称前缀
        executor.setThreadNamePrefix("thread-execute");
        //设置拒绝策略 超过最大阻塞队列将抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;

    }
}
