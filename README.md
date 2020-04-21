# SEI-Campus_Store

This project has been wrote as a requirement for CS-451 Software Engineering Project I & II.  This project is a display of the Pensacola Christian College campus store.  All requirements are decided by the students along with all design and features. This project is a Spring Boot project using Maven.  The front-end is developed with Vue.js framework with the back-end using the Spring Boot framework.  This project was built with two different frameworks in attempt to allow for the front-end to be changed out at any point.

# Motivation

This project is a Senior Platform project for Pensacola Christian College CS degree.  Students are given a list of potential projects to develop.  Once several projects have been selected, students are assigned to a project in a team setting.  All requirements are developed by the student and are built from the ground up.

# Project Compiling
1.  Running the server
    *  ./scripts/run.sh server
2.  Running with local database
    *  ./scripts/run.sh local
3.  Building a local database with docker
    *  ./scripts/runLocalDB.sh
4.  Running Vue server
    *  ./scripts/runVue.sh

# Local DB Access
*  Database URL      - jdbc:sqlserver://localhost:1433;databaseName=SEI_Persevere
*  Database Username - SA
*  Database Password - SEI_Persevere2019!