package healthchecks;

import com.codahale.metrics.health.HealthCheck;

import java.nio.file.Files;
import java.nio.file.Paths;

// This is a dummy health check
public class FileServerHealthCheck extends HealthCheck
{

    private  String defaultFIlePath;

    public FileServerHealthCheck(String defaultFIlePath) {
        this.defaultFIlePath = defaultFIlePath;
    }

    @Override
    protected Result check()
    {
        Files.exists(Paths.get(defaultFIlePath));
        if(Files.exists(Paths.get(defaultFIlePath))){
        return Result.healthy();
    }
        return Result.unhealthy("Not healthy");
    }
}