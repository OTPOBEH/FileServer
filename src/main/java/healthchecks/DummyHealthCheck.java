package healthchecks;

import com.codahale.metrics.health.HealthCheck;

public class DummyHealthCheck extends HealthCheck
{
    @Override
    protected Result check()
    {
        if(true){
        return Result.healthy();
    }
        return Result.unhealthy("Not healthy");
    }
}