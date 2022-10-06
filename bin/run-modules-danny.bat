@echo off
echo; ===run ruoyi-modules-danny========

cd %~dp0
cd ../ruoyi-modules/ruoyi-modules-danny/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar ruoyi-modules-danny.jar

cd bin
pause