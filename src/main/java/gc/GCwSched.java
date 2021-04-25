package gc;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Random;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class GCwSched {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        JobDetail job = newJob(GCwSched.MemFiller.class)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(3)
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
        Thread.sleep(5000);
    }

    public static class MemFiller implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            for (int i = 0; i < 100000; i++) {
                Random rd = new Random();
                System.out.println(rd.nextLong());
            }
        }
    }
}
