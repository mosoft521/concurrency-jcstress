# concurrency-jcstress
test unsafe publication
# prerequisites
Maven >= 3.1 require
Java >= Java8
jcstress 1.0-SNAPSHOT must be avaliable in local repo:
```
 $ hg clone http://hg.openjdk.java.net/code-tools/jcstress/ jcstress
 $ cd jcstress/
 $ mvn clean install -pl jcstress-core -am
```
# build
mvn clean package
# run
java -jar target\concurrent-jcstress.jar
