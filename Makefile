.PHONY:all

all:
	make build
	make exec

build:ktPyString.kt
	kotlinc ktPyString.kt -include-runtime -d sample.jar

exec:sample.jar
	java -jar sample.jar
