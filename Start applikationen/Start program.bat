@echo off

start java -jar Sagsstyring-1.jar 
ping 127.0.0.1 -n 6 > nul
start http://localhost:8080
exit