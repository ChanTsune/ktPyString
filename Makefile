BULID_DIR = bin

.PHONY:all

all:
	make build
	make exec

build:ktPyString.kt
	mkdir -p $(BULID_DIR)
	kotlinc ktPyString.kt -include-runtime -d $(BULID_DIR)/sample.jar

exec:$(BULID_DIR)/sample.jar
	java -jar $(BULID_DIR)/sample.jar
