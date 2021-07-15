
# Simple File Web Server

This is a RESTful API for Uploading/Downloading/Deleteing a File.
It is based on [Dropwizard](https://www.dropwizard.io) framework.

The file resource path is ```.../v1/files```.

Instructions for running the API can be found at the bottom of this file.

## Endpoint Examples

### @PUT (HTTP 200)

```/v1/files/```

Use form-data with a key ```file``` and a linked file of type ``` *.txt```  or ``` *.csv```(configurable via ```configuration.yml```, see below).

Example: ```http://localhost:8080/v1/files``` 

Response entity:
```json
{
    "filePath": "E:\\Java\\Repos\\FileServer\\Test.txt"
}
```

### @GET (HTTP 200)

```/v1/files/{filename}```

Example for a file ```Test.txt``` below:

Example: ```http://localhost:8080/v1/files/Test.txt```

Response entity:
```json
{
    "fileContent": "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
}
```

### @DELETE (HTTP 204)

```/v1/files/{filename}```

Example for a file ```Test.txt``` below:

Example: ```http://localhost:8080/v1/files/Test.txt```

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

## Usage
The application is listening at port ```8080```.
There is a config file under ```config/configuration.yml``` containing the following configurable paramethers:
```yaml
# Project root folder directory.(Can be changed to any other valid directory)
defaultFolderPath: .\\

# Uppercase comma-separated file extensions (according to the user's entitlements)
supportedFileExtensions: TXT,CSV
```

## Application Start 

### Starting the API using IDE (e.g. IntelliJ)

You can start the application with the following program paramethers

```bash
server config\configuration.yml
```

The main method is in the ```FileServerApplication.java``` class.

### Starting the API .jar using command:

Build project with Maven to assemble one fat ```FileServer-1.0-SNAPSHOT-jar-with-dependencies.jar```

Command: 
```java -jar "FileServer-1.0-SNAPSHOT-jar-with-dependencies.jar" server {path to configuration.yml}```

If the ```configuration.yml``` file is in the same folder as the .jar the command would be:

```java -jar "FileServer-1.0-SNAPSHOT-jar-with-dependencies.jar" server configuration.yml```