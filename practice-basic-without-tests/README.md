Create a mircoservice (limits-service) that pulls configurations from configuation server (configurations are saved to a local git repo)

Order of creation:
1. Create limit-service (return hardcoded values to customers of the service)
  a. Controller
  b. Bean
  c. application.properties
  
2. Create configuation server
  a. Create local git repo
  b. application.properties
    - set spring.cloud.config.server.git.uri: {git repo path}
  c. @EnableConfigServer on startup
  
3. Mircoservice pulls from configuration server
  a. limit-service
    - application.properties renamed to bootstap.properties
    - bootstap.properties: spring.cloud.config.uri: {url of configuration server}
    - bootstap.properties: spring.profiles.active: {env to display}  
