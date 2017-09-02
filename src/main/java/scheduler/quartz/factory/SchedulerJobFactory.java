package scheduler.quartz.factory;

import org.quartz.JobDataMap;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerJobFactory
{

    public JobDetailFactoryBean job(Class<? extends QuartzJobBean> jobClass, JobDataMap jobMap, String jobName) {
        JobDetailFactoryBean jBean = createJobDetail(jobClass);
        jBean.setJobDataMap(jobMap);
        jBean.setBeanName(jobName);
        jBean.afterPropertiesSet();
        return jBean;
    }

    private JobDetailFactoryBean createJobDetail(Class<? extends QuartzJobBean> jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

}