#!/bin/bash

RUN_PROJECT=$PWD/backend/target/ROOT.jar
DEPLOY_PROJECT=$PWD/backend/target/ROOT.war

LOCAL_LIQUIBASE_URL="jdbc:sqlserver://localhost:1433;databaseName=SEI_Persevere"
LOCAL_LIQUIBASE_USERNAME="SA"
LOCAL_LIQUIBASE_PASSWORD="SEI_Persevere2019!"

if [[ $1 == "local"  ]]; then
    echo "*********************************************************************"
    echo "*                     Running Local Maven Build                     *"
    echo "*********************************************************************"
    echo ""
    mvn clean install -DskipTests=true -Denv.skipDeploy=true -Denv.skipFrontEnd=false\
    -Denv.liquibase_url=$LOCAL_LIQUIBASE_URL \
    -Denv.liquibase_username=$LOCAL_LIQUIBASE_USERNAME \
    -Denv.liquibase_password=$LOCAL_LIQUIBASE_PASSWORD \
    -Denv.product_build="jar"
    java -jar "$RUN_PROJECT"

elif [[ $1 = "debug"  ]]; then
    echo "*********************************************************************"
    echo "*                Running Maven Build with Debugging                 *"
    echo "*********************************************************************"
    echo ""
    mvn clean install -Dliquibase.should.run=false -DskipTests=true -X
    java -jar "$RUN_PROJECT"

elif [[ $1 = "server"  ]]; then
    echo "*********************************************************************"
    echo "*                      Running Lab Maven Build                      *"
    echo "*********************************************************************"
    echo ""
    java -jar "$RUN_PROJECT"

else
    echo "Invalid command entered."
fi
