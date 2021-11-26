package com.scheduler.Jobs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
//Disable during test
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class Scheduler {

}
