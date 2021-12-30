#!/bin/bash
EXEC_JAR=$(ls target | grep .jar$)
java -jar "target/$EXEC_JAR"