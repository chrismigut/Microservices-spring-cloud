## Creating two mircoservices to talk to each other. ##

Service             | Type   | Port | Resource
--------            |------  |------|----------
Currency-Exchange   |service | 8000 | /currency-exchange/from/{from}/to/{to}
Currency-Conversion |service | 8100 | /currency-converter/from/{from}/to/{to}/quantity/{quantity}

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
