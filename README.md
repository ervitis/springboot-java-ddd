# Objectives

## DOING

- Use mongodb and test with it: using complex query:
  - aggregations
  - left joins
  - criteria builder

### Resources

## TODO

- Testing
- Abstract scheduler api
- Update readme with folder structure and architecture design

## DONE

- Secure public API and make public API between services
- GRPC & abstract http client -> [example](https://github.com/ervitis/grpc-spring-boot-reactive/blob/master/server-api-aggregator/src/main/java/com/orange/poc/grpc/validator/domain/nif/impl/NifValidator.java)
- Custom roles using jwt scope claim
- Improve swagger doc with the returned responses
- Complete API endpoints
- Quartz scheduler on testing should run with embedded mongodb
- Replace white error pages
- Secure endpoints using roles
- Use jwt
- RestTemplate / WebClient with retry and backoff and circuit breaker
- Use zipkin / sleuth or other opentracing system
- Scheduler with quartz and use it with mongodb connection
- OpenFeign <-> no compatible with opentracing jaeger, several bugs reported from February
- Use mapper interface
- Create middleware for different views and concatenate them
