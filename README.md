# QuickWeather API documentation
This repository contains the documentation for QuickWeather’s API.

## Introduction
This API returns current weather by given geographical coordinates.

## Overview
The API endpoint /currentWeather accepts a geographical coordinate and responds with a JSON.

## Error Codes
In case an error occurs, for example a URL coordinate parameter is not correctly specified, a JSON error object is returned with a HTTP 400 status code.

###  Weather

Example request:

```
GET /currentWeather?latitude=49.8125&longitude=115 HTTP/1.1
Content-Type: application/json
Accept: application/json
Accept-Charset: utf-8
```
With the following parameters:
| Parameter       | Type     | Required?  | Description                                     |
| -------------   |----------|------------|-------------------------------------------------|
| `latitude`     | float   | required   | Geographical WGS84 coordinate of the location latitude.   |
| `longitude`     | float   | required   | Geographical WGS84 coordinate of the location longitude.   |

The response is a Weather object.

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
| latitude   | float  | 	Geographical WGS84 coordinate of the location latitude                            |
| longitude | float  | 	Geographical WGS84 coordinate of the location longitude                            |
| temperature    | float  | Air temperature in Celsium degrees          |
| windspeed     | float  | Windspeed in minutes per second              |
| winddirection | float | Winddirection in degrees                      |
| description | string  | Weather condition                               |
| time | string  | Time of weather measurement in GMT+0 timezone   |

Possible errors:

| Error code     | Description                                                  |
|----------------|--------------------------------------------------------------|
| 400 BedRequest | The `latitude` or `longitude` are invalid .|
