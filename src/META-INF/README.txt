In order to run AspectJ weaver:


Add javaagent to VM arguments (in eclipse: Run as -> Run configurations -> Arguments -> VM arguments)
-javaagent:lib/aspectjweaver-1.8.13.jar


or


Start javaagent from command line
java -javaagent:lib/aspectjweaver-1.8.13.jar