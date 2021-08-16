@GetCurrentWeatherData
Feature: Get Current weather data

  @GetCurrentWeatherDataByCityName
  Scenario Outline: Get current weather data for one location by city name
    Given a rest api "openweathermap"
    And query parameters
      | q     | <cityName>                       |
      | appid | c23450d3edb4a26fff4ab4296ee0577f |
    When user requests GET
    Then verify the status code is 200
    And verify that the response body contains
      | element         | matcher | value   | type  |
      | coord.lon       | equals  | -0.1257 | float |
      | coord.lat       | equals  | 51.5085 | float |
      | main.temp       | !isNull | 51.5085 | float |
      | main.feels_like | !isNull | 51.5085 | float |
      | main.temp_min   | !isNull | 51.5085 | float |
      | main.temp_max   | !isNull | 51.5085 | float |
      | main.pressure   | !isNull | 51.5085 | float |
      | main.humidity   | !isNull | 51.5085 | float |
    And verify that the response header contains
      | Content-Type | application/json; charset=utf-8 |

    Examples: 
      | cityName |
      | London   |

  @GetCurrentWeatherDataByCityNamewithIncorrectappid
  Scenario Outline: Get current weather data with incorrect appid
    Given a rest api "openweathermap"
    And query parameters
      | q     | <cityName> |
      | appid | <appid>    |
    When user requests GET
    Then verify the status code is 401
    And verify that the response body contains
      | element | matcher | value                                                                             | type |
      | cod     | equals  |                                                                               401 | int  |
      | message | equals  | Invalid API key. Please see http://openweathermap.org/faq#error401 for more info. | str  |

    Examples: 
      | cityName | appid                        |
      | London   | c23450d3edb4a26fff4ab6ee0577 |
      | London   |                              |

  @GetCurrentWeatherDataByCityNamewithIncorrectaLocation
  Scenario Outline: Get current weather data with incorrect location
    Given a rest api "openweathermap"
    And query parameters
      | q     | <cityName>                       |
      | appid | c23450d3edb4a26fff4ab4296ee0577f |
    When user requests GET
    Then verify the status code is <statuscode>
    And verify that the response body contains
      | element | matcher | value          | type |
      | cod     | equals  | <errorCode>    | str  |
      | message | equals  | <errorMessage> | str  |

    Examples: 
      | cityName | errorCode | errorMessage       | statuscode |
      |          |       400 | Nothing to geocode |        400 |
      | Lo       |       404 | city not found     |        404 |

  @GetCurrentWeatherDataByCityNamewithmodes
  Scenario Outline: Get current weather data with differnt modes
    Given a rest api "openweathermap"
    And query parameters
      | q     | London                           |
      | appid | c23450d3edb4a26fff4ab4296ee0577f |
      | mode  | <mode>                           |
    When user requests GET
    Then verify the status code is 200
    And verify that the response header contains
      | Content-Type | <content-type> |

    Examples: 
      | mode | content-type                   |
      | html | text/html; charset=utf-8       |
      | xml  | application/xml; charset=utf-8 |
