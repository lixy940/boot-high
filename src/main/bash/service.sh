#!/bin/sh
## Linux下简单启动脚本
basepath=$(cd `dirname $0`;pwd)
## 启动
start(){
    pid=`ps -ef | grep boot-high-0.0.1-SNAPSHOT.jar | grep -v grep |awk '{print $2}'`
    if [ $pid ]; then
        echo :App  is  running pid=$pid
        exit -1
    fi
    nohup java -jar $basepath/../boot-high-0.0.1-SNAPSHOT.jar --spring.config.location=$basepath/../config/application.properties --logging.config=$basepath/../config/logback-spring.xml >/dev/null 2>&1 &
    echo $!>$basepath/pid
    tail -f $basepath/../logs/boot-high.log
  }

## 停止
stop(){
    echo "stop project..."
    echo "kill pid `cat $basepath/pid`"
    kill -9 `cat $basepath/pid`
    rm -rf $basepath/pid
    echo "stop project end..."
}

restart(){
  stop
  start
}


case $1 in
  start)
   start
  ;;
  stop)
   stop
  ;;
  restart)
     restart
  ;;
  *)
   echo "Usage: {start|stop|restart}"
  ;;
esac
exit 0

