
JC=/usr/bin/javac
J=java
JFLAG=-g

.SUFFIXES: .java .class

.java.class:
	$(JC) -d ./bin/ -cp ./bin $<


CLASSES=  ./src/TerrainArray.class ./src/Main.class  ./src/SumArray1.class ./src/SumArray2.class

classes: $(CLASSES: .java=.class)
	
	
default: classes


clean:
	rm bin/*.class 
	
docs:
	javadoc -d ./doc/ -cp ./doc  ./src/Vector.java ./src/TerrainArray.java ./src/SumArray1.java ./src/SumArray2.java ./src/Main.java 
	 
	
			
	
