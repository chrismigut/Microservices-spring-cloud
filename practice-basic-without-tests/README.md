
## Discription ##
Create a mircoservice that pulls configurations from configuation server.
Configurations are saved to a local git repo.


Service        |  Type      | Port
-------------  |  ----      | -------
limits-service |mircoservice| 8080
config-server  |configuation| 8888


## Order of creation: ##
1. Create limit-service (return hardcoded values to customers of the service)
  1. Controller
  2. Bean
  3. application.properties
  4. @ConfigurationProperties("limits-service") on Configuration.class
  
2. Create configuation server
  1. Create local git repo
  2. application.properties
    * set spring.cloud.config.server.git.uri: {git repo path}
  3. @EnableConfigServer on startup
  
3. Mircoservice pulls from configuration server
  1. limit-service
    * application.properties renamed to bootstap.properties
    * bootstap.properties: spring.cloud.config.uri: {url of configuration server}
    * bootstap.properties: spring.profiles.active: {env to display}  

