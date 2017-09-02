package scheduler.controller;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import scheduler.quartz.factory.SchedulerJobFactory;
import scheduler.quartz.factory.SchedulerTriggerFactory;
import scheduler.quartz.service.SchedulerJobService;


@RestController
public class SchedController {

    @Autowired
    private SchedulerFactoryBean schedFactory;

    @Autowired
    SchedulerJobFactory quartzJobFactory;

    @Autowired
    SchedulerTriggerFactory sampleJobTrigger;

    @Autowired
    SchedulerJobService schedulerJobService;


    @RequestMapping(path = "/schedule", method = RequestMethod.GET)
    public String schedule() {
        String scheduled = "some Jobs scheduled";
        try {
              JobDataMap jMap = new JobDataMap();
                jMap.put("key1", "value1");
                jMap.put("key2", "value2");

                schedulerJobService.scheduleJobWithDataMap(jMap, "0/7 * * * * ?","jobThree","triggerThree");
            JobDataMap jMap2 = new JobDataMap();
              jMap2.put("key1", "value21");
              jMap2.put("key2", "value22");

          schedulerJobService.scheduleJobWithDataMap(jMap2, "0/8 * * * * ?","jobFour","triggerFour");

        } catch (Exception e) {
            scheduled = "Could not schedule job. " + e.getMessage();
        }
        return scheduled;
    }

    @RequestMapping(path = "/unschedule/{jobName}/{triggerName}", method = RequestMethod.GET)
    public String unschedule(@PathVariable("jobName") String jobName, @PathVariable("triggerName") String triggerName) {
        String scheduled = "Job unscheduled";
        TriggerKey tkey = new TriggerKey(triggerName);
        JobKey jkey = new JobKey(jobName);
        try {
            schedFactory.getScheduler().checkExists(jkey);
            schedFactory.getScheduler().unscheduleJob(tkey);
            schedFactory.getScheduler().deleteJob(jkey);
        } catch (SchedulerException e) {
            scheduled = "Error while unscheduling " + e.getMessage();
        }
        return scheduled;
    }
}