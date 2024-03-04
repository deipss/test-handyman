package me.deipss.test.handyman.config;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableAsync(proxyTargetClass = true)
@Slf4j
public class ThreadConfig {

    public static final String SCHEDULING_THREAD_POOL_EXECUTOR = "schedulingThreadPoolExecutor";
    public static final String WORK_THREAD_POOL_EXECUTOR = "workThreadPoolExecutor";

    public ThreadConfig() {
        log.info("ThreadConfig 无参构造");
    }

    @PostConstruct
    public void init(){
        log.info("ThreadConfig PostConstruct()");
    }

    @Bean(SCHEDULING_THREAD_POOL_EXECUTOR)
    public ThreadPoolExecutor scheduling() {
        log.info("开始SCHEDULING_THREAD_POOL_EXECUTOR线程池配置");
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-scheduling-thread" + "-%d")
                .setDaemon(false).build();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1, 32, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(128),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!executor.isShutdown()) {
                List<Runnable> runnableList = executor.shutdownNow();
                log.warn("task-scheduling-thread losing {}", runnableList.size());
            }
            log.info("task-scheduling-thread close");
        }));

        return executor;
    }

    @Bean(WORK_THREAD_POOL_EXECUTOR)
    public ThreadPoolExecutor executor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-work-thread" + "-%d")
                .setDaemon(false).build();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() << 1, 32, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(256),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!executor.isShutdown()) {
                List<Runnable> runnableList = executor.shutdownNow();
                log.warn("task-execute-thread losing {}", runnableList.size());
            }
            log.info("task-execute-thread close");
        }));

        return executor;
    }
}
