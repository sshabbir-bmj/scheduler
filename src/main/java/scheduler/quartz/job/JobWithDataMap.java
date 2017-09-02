package scheduler.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * scheduled cron job
 */

@DisallowConcurrentExecution
public class JobWithDataMap extends  QuartzJobBean {

    private static final Logger LOG = LoggerFactory.getLogger(JobWithDataMap.class);

    private String key1;
    private String key2;

    public void setKey1(String key1)
    {
        this.key1 = key1;
    }

    public void setKey2(String key2)
    {
        this.key2 = key2;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobContext) throws JobExecutionException
    {
        LOG.info("Running JobWithDataMap | next fire {}", jobContext.getTrigger().getNextFireTime());
        LOG.info("Running JobWithDataMap | Key1 = {}, Key2 = {}", key1, key2 );
    }

}
