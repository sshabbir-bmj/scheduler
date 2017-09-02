package scheduler.quartz.service;

import java.text.ParseException;
import java.util.Date;
import javax.annotation.PostConstruct;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import scheduler.quartz.factory.SchedulerJobFactory;
import scheduler.quartz.factory.SchedulerTriggerFactory;
import scheduler.quartz.job.JobWithDataMap;


@Service
public class SchedulerJobService
{
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    SchedulerJobFactory schedulerJobFactory;

    @Autowired
    SchedulerTriggerFactory schedulerTriggerFactory;

    @PostConstruct
    public void setupJobs() throws ParseException, SchedulerException {

        JobDataMap jMap = new JobDataMap();
        jMap.put("key1", "value1");
        jMap.put("key2", "value2");
        JobDataMap jMap2 = new JobDataMap();
        jMap2.put("key1", "value21");
        jMap2.put("key2", "value22");

        scheduleJobWithDataMap(jMap, "0/10 * * * * ?", "jobOne", "triggerOne");
        scheduleJobWithDataMap(jMap2, "0/8 * * * * ?", "jobTwo", "triggerTwo");
    }


    public Date scheduleJobWithDataMap(JobDataMap jMap, String frequency, String jobName, String triggerName)
            throws ParseException, SchedulerException {

        JobDetailFactoryBean jdfb = schedulerJobFactory.job(JobWithDataMap.class, jMap, jobName);
        CronTriggerFactoryBean stfb = schedulerTriggerFactory.jobTrigger(jdfb.getObject(), frequency, triggerName);

        return schedulerFactoryBean.getScheduler().scheduleJob(jdfb.getObject(), stfb.getObject());

    }
}
