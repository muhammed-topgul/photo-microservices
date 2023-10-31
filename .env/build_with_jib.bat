set PROJECT_DIR=%~dp0\..

:::::::::::::::::::::::::::::Account Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\account-service
call mvn clean
call mvn install
call mvn jib:dockerBuild
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Album Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\album-service
call mvn clean
call mvn install
call mvn jib:dockerBuild
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::API Gateway Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\api-gateway
call mvn clean
call mvn install
call mvn jib:dockerBuild
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Config Server::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\config-server
call mvn clean
call mvn install
call mvn jib:dockerBuild
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Discovery Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\discovery-service
call mvn clean
call mvn install
call mvn jib:dockerBuild
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::User Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\user-service
call mvn clean
call mvn install
call mvn jib:dockerBuild
call cd %PROJECT_DIR%

pause