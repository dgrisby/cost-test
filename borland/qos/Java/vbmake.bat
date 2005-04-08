@echo off
rem Makefile

if "%1"=="" goto all
if "%1"=="clean" goto clean
goto usage

:all
echo Building the bank example ...
call idl2java Bank.idl
vbjc Client.java
vbjc Server.java
vbjc ForwardServer.java
goto end

:clean
rd /s /q Bank
del /q *.class
goto end

:usage
echo Usage: vbmake [clean]

:end
