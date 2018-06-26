#!/bin/sh

basepath=$(cd `dirname $0`; pwd)

deploy_provider(){
    cd $basepath/../provider
    mvn tomcat7:run
    echo "dubbo-provider run in localhost:8282"
}

deploy_consumer(){
    cd $basepath/../consumer
    mvn tomcat7:run
    echo "dubbo-consumer run in localhost:8283"
}

deploy_provider

deploy_consumer