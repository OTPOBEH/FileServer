
# Simple File Web Server

This is a RESTful API for Uploading/Downloading/Deleteing a File.
It is based on [Dropwizard](https://www.dropwizard.io) framework.

## Application Start

You can start the application with the following program paramethers

```bash
server config\configuration.yml
```

## Usage
The application is listening at port ```8080```.
There is a config file under ```config/configuration.yml``` containing the following configurable paramethers:
```yaml
defaultFolderPath: .\\ 
supportedFileExtensions: TXT,CSV
```

The file resource path is ```.../v1/files```.

The main method is in the ```FileServerApplication.java``` class.

## Endpoint Examples

### @PUT (HTTP 200)
Use form-data with a key ```file``` and a linked file of type ``` *.txt```  or ``` *.csv``` .

Address: ```http://localhost:8080/v1/files``` 

Response entity:
```json
{
    "filePath": "E:\\Java\\Repos\\FileServer\\Test.txt"
}
```

### @GET (HTTP 200)
Example for a file ```Test.txt``` below:

Address: ```http://localhost:8080/v1/files/Test.txt```

Response entity:
```json
{
    "fileContent": "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
}
```

### @DELETE (HTTP 204)
Example for a file ```Test.txt``` below:

Address: ```http://localhost:8080/v1/files/Test.txt```

## Health Check Endpoint
Checks if the default folder is reachable.

Address: ```http://localhost:8081/healthcheck```

Response entity:
```json
{
    "APIHealthCheck": {
        "healthy": true,
        "duration": 1,
        "timestamp": "2021-07-11T00:59:48.741+03:00"
    },
    "deadlocks": {
        "healthy": true,
        "duration": 0,
        "timestamp": "2021-07-11T00:59:48.740+03:00"
    }
}
```
