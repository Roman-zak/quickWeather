# QuickWeather API documentation
This repository contains the documentation for QuickWeather’s API.

## Introduction
This API returns current weather by given geographical coordinates.

## Overview
The API endpoint /currentWeather accepts a geographical coordinate and responds with a JSON.

## Error Codes
In case an error occurs, for example a URL coordinate parameter is not correctly specified, a JSON error object is returned with a HTTP 400 status code.
### 3.1. Weather

```
GET https://api.medium.com/v1/me
```

Example request:

```
GET /currentWeather?latitude=49.8125&longitude=115 HTTP/1.1
Content-Type: application/json
Accept: application/json
Accept-Charset: utf-8
```

The response is a Weather object within a data envelope.

Example response:

```
HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
{
    "coordinates": {
        "latitude": 45.24,
        "longitude": 10.0
    },
    "temperature": 16.2,
    "windspeed": 12.7,
    "winddirection": 115.0,
    "description": "Overcast",
    "time": "2023-04-12T12:00:00"
}
```

Where a Weather object is:

| Field   | Type    | Description                                         |
|---------|---------|-----------------------------------------------------|
| latitude   | decimal  | Coordinates latitude                            |
| longitude | decimal  | Coordinates longitude                            |
| temperature    | decimal  | Air temperature in Celsium degrees          |
| windspeed     | decimal  | Windspeed in minutes per second              |
| winddirection | decimal | Winddirection in degrees                      |
| description | string  | Weather condition                               |
| time | string  | Returned weather time of measurement in GMT+0 timezone |

Possible errors:

| Error code     | Description                                                  |
|----------------|--------------------------------------------------------------|
| 400 BedRequest | The `latitude` or `longitude` is invalid or has been revoked.|