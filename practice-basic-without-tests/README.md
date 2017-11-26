
## Discription ##
Create a mircoservice that pulls configurations from configuation server.
Configurations are saved to a local git repo.


Service        |  Type      | Port   | Resource   |
-------------  |  ----      | -------|-------     |
limits-service |mircoservice| 8080   | /limits
config-server  |configuation| 8888   |....


## Order of creation: ##
* ### Create limit-service (return hardcoded values to customers of the service) ###
  * Controller
  * Bean
  * application.properties
  * @ConfigurationProperties("limits-service") on Configuration.class
  
* ### Create configuation server ###
  * Create local git repo
  * application.properties
    * set spring.cloud.config.server.git.uri: {git repo path}
  * @EnableConfigServer on startup
  
* ### Mircoservice pulls from configuration server ###
  * limit-service
    * application.properties renamed to bootstap.properties
    * bootstap.properties: spring.cloud.config.uri: {url of configuration server}
    * bootstap.properties: spring.profiles.active: {env to display}  

