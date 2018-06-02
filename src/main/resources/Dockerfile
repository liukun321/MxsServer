FROM java:8
ADD mxsServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 18066
EXPOSE 8060
#ENTRYPOINT 用于标明一个镜像作为容器运行时，最后要执行的程序或命令
ENTRYPOINT ["java","-jar","/app.jar"]
ADD resume.pdf resume.pdf
