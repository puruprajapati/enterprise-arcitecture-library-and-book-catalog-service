package library.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import library.config.ApplicationProperties;
import library.service.impl.CustomerScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleTaskService implements SchedulingConfigurer {
  @Autowired
  private CustomerScheduleServiceImpl customerScheduleService;

  @Autowired
  private ApplicationProperties applicationProperties;

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    //taskRegistrar.setScheduler(poolScheduler());
    taskRegistrar.addTriggerTask(new Runnable() {
      @Override
      public void run() {
        // Do not put @Scheduled annotation above this method, we don't need it anymore.
        customerScheduleService.printOutstandingAmountPerCustomer();
      }
    }, new Trigger() {
      @Override
      public Date nextExecutionTime(TriggerContext triggerContext) {
        Calendar nextExecutionTime = new GregorianCalendar();
        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
        nextExecutionTime.add(Calendar.MILLISECOND, getNewExecutionTime());
        return nextExecutionTime.getTime();
      }
    });
  }

  private int getNewExecutionTime() {
    //Load Your execution time from database or property file
    return (applicationProperties.getOutstandingFeePrintTime() * 1000);
  }

  @Bean
  public TaskScheduler poolScheduler() {
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
    scheduler.setPoolSize(1);
    scheduler.initialize();
    return scheduler;
  }
}
