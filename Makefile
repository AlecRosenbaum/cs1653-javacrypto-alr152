default: all

run:
	java -cp lib:build Main

all: clean
	mkdir -p build
	javac -d build src/*.java

clean:
	rm -f build/*.class