default: all

run:
	java -cp lib/bcprov-ext-jdk15on-158.jar:build Main

all: clean
	mkdir -p build
	javac -cp lib/bcprov-ext-jdk15on-158.jar -d build src/*.java

clean:
	rm -f build/*.class