
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

The main method is in the ```FileServerApplication.java``` class.

## Endpoint Examples

### @PUT (HTTP 200)
Use form-data with a key ```file``` and a linked file of type ``` *.txt```  or ``` *.csv``` .

Address: ```http://localhost:8080/v1/files```

### @GET (HTTP 200)
Address: ```http://localhost:8080/v1/files?file=Test.txt```

### @DELETE (HTTP 204)
Address: ```http://localhost:8080/v1/files?file=Test.txt```

## Health Check Endpoint
Checks if the default folder is reachable.

Address: ```http://localhost:8081/healthcheck```
