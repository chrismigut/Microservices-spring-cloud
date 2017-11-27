
## Creating two mircoservices to talk to each other. ##
Service             | Type   | Port | Resource
--------            |------  |------|----------

Currency-Exchange   |service | 8000 | /currency-exchange/from/{from}/to/{to}
Currency-Conversion |service | 8100 | /currency-converter/from/{from}/to/{to}/quantity/{quantity}
Eureka 		    |naming  | 8761 |

#### 1. Create Currency-Exchange Service ####
* application.properties
* controller
* bean/dao
* modifiy pom.xml for jpa and h2 depencies

* data.sql file: insert statements
  * had issues with h2 database, had to create a bean in application for h2 to create tables

#### 2. Create Currency-Conversion-Service ####
* application.properties
* controller
* bean/dao
 
#### 3. Create RestTemplate in Currency-Conversion-Service ####


* RestTemplate maps the return obj from currency-exchange service to obj used by currency-conversion service
  * this method will be replaced later, right now RestTemplate knows the address of currency-exhange service
  ```java
  Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
 		
		ResponseEntity<CurrencyConversion> reponseEntity = new RestTemplate().getForEntity(
									"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
									CurrencyConversion.class, 
									uriVariables);
		
		CurrencyConversion response = reponseEntity.getBody();
 ```
#### 4. Setup Feign to replace RestTemplate ####
* Add Feign depency in currency-conversion pom.xml
* EnableFeignClient in application main
* Create a Feign Proxy interface
```java
//name is the service we are going to call
@FeignClient(name="currency-exchange-service", url="localhost:8000")
public interface CurrencyExchangeServiceProxy {
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(@PathVariable("from") String from,@PathVariable("to") String to);
}
```

#### 5. Ribbon - load balancing with help of Feign ####
* Add Ribbon depency in currency-conversion service pom.xml
* Enable Ribbon on the Feign proxy, @RibbonClient(...)
* In currency-conversion-service application.properties, set the following
currency-exchange-service.ribbon.listOfServers=http://localhost:8000, http://localhost:8001

#### 6. Eureka Naming Server - with Ribbon
* Euraka allows us to add new servers for a service to be spin up without having to tell/edit other services to be aware of them.
We no longer have to hard code those servers. A new server would register its self to the naming server. Any service looking to use another service, will ask the naming server if there is an instance of that service and proved the location of that service. 
* @EnableEurekaServer on application main
* application.properties: 
``` java
spring.application.name=netflix-eureka-naming-server
server.port=8761

euraka.client.register-with-euraka=false
euraka.client.fetch-register=false
```
* Add the Eureka depency in currency-conversion pom.xml
* @EnableDiscoveryClient on application main
* To register currency-conversion service to eureka, in application.properties add the following
```java
eureka.client.service-url.default-zone=http://localhost:8761/eureka
```
* Run the currency-conversion service, the service will now be register with Eureka

#### 7. Service_A calling Service_B with the use of Eureka ####
* At this point the following services are registered with Eureka: currency-conversion, currency-exchange, limits-service
* We want currency conversion service to call currency-exchange service with Eureka than hard coding the servers of exchange in conversion application.properties
* Remove the following line from currency-conversion service application.properties
```java
currency-exchange-service.ribbon.listOfServers=http://localhost:8000, http://localhost:8001
```
* Start up the difference services
* Currency-conversion service will now be able to locate currency-exchange service
