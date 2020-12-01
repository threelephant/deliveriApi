@REM Maven Wrapper script for Windows
@echo off
setlocal

set MAVEN_PROJECTDIR=.
set WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

if exist "..\.mvn\wrapper\maven-wrapper.jar" set MAVEN_PROJECTDIR=..

if not exist "%MAVEN_PROJECTDIR%\%WRAPPER_JAR%" (
  for /f "tokens=2 delims==" %%a in ('findstr /r "wrapperUrl" "%MAVEN_PROJECTDIR%\.mvn\wrapper\maven-wrapper.properties" 2^>nul') do set MAVEN_WRAPPER_URL=%%a
  if "%MAVEN_WRAPPER_URL%"=="" (
    echo Error: Could not find wrapperUrl in maven-wrapper.properties
    exit /b 1
  )
  echo Downloading Maven Wrapper...
  mkdir "%MAVEN_PROJECTDIR%\.mvn\wrapper" 2>nul
  powershell -Command "Invoke-WebRequest -Uri '%MAVEN_WRAPPER_URL%' -OutFile '%MAVEN_PROJECTDIR%\%WRAPPER_JAR%' -UseBasicParsing"
  if errorlevel 1 (
    echo Download failed
    exit /b 1
  )
)

set JAVA_CMD=java
if not "%JAVA_HOME%"=="" set JAVA_CMD=%JAVA_HOME%\bin\java

"%JAVA_CMD%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTDIR%" -classpath "%MAVEN_PROJECTDIR%\%WRAPPER_JAR%" %WRAPPER_LAUNCHER% %*
endlocal & exit /b %ERRORLEVEL%
