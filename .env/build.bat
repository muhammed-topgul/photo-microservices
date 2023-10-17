set PROJECT_DIR=%~dp0\..

:::::::::::::::::::::::::::::Discovery Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\discovery-service
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/discovery-service
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Account Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\account-service
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/account-service
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::User Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\user-service
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/user-service
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::API Gateway Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\api-gateway
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/api-gateway
call cd %PROJECT_DIR%