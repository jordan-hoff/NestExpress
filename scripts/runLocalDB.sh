#!/bin/bash

echo "*********************************************************************"
echo "*                  Building Local SQL Database                       *"
echo "*********************************************************************"
echo ""
docker run -e 'ACCEPT_EULA=Y' -e "SA_PASSWORD=SEI_Persevere2019!" -p 1433:1433 --name campus-store-db -d mcr.microsoft.com/mssql/server:2019-CU1-ubuntu-16.04
echo ""
echo ""
echo ""
echo "Do the following commands:"
echo "        docker exec -it campus-store-db /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "SEI_Persevere2019!""
echo "        create database SEI_Persevere"
echo "        go"
echo "        exit"