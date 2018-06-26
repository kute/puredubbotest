# puredubbotest

1、first start zk

- 127.0.0.1:2181
- 127.0.0.1:2182
- 127.0.0.1:2183

2、 then start provider(8282)

cd provider
mvn tomcat7:run

3、 finally start consumer(8283)

cd consumer
mvn tomcat7:run

4、curl http://localhost:8283/api/v1/user/get/1024

