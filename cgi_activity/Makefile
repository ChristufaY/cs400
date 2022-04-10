build:
	javac -source 1.7 -target 1.7 -cp . CSVTOHTML.java

run: build
	java -cp . CSVTOHTML
	
upload: build
	./upload

clean:
	rm *.class
