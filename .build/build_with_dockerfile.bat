set PROJECT_DIR=%~dp0\..

:::::::::::::::::::::::::::::Token Library::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\token-lib
call mvn clean
call mvn install
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Account Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\account-service
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/account-service --force-rm=true
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Album Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\album-service
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/album-service --force-rm=true
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::API Gateway Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\api-gateway
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/api-gateway --force-rm=true
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Config Server::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\config-server
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/config-service --force-rm=true
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::Discovery Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\discovery-service
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/discovery-service --force-rm=true
call cd %PROJECT_DIR%

:::::::::::::::::::::::::::::User Service::::::::::::::::::::::::::::::
call cd %PROJECT_DIR%\user-service
call mvn clean
call mvn install
call docker build --no-cache . -t mtopgul/user-service --force-rm=true
call cd %PROJECT_DIR%

pause