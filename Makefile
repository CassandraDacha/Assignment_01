
JC=/usr/bin/javac
J=java
JFLAG=-g

.SUFFIXES: .java .class

.java.class:
	$(JC) -d ./bin/ -cp ./bin $<


CLASSES=  ./src/Vector.class ./src/CloudData.class  ./src/SumArray1.class ./src/SumArray2.class ./src/SumAll.class 

classes: $(CLASSES: .java=.class)
	
	
default: classes


clean:
	rm bin/*.class 
	
docs:
	javadoc -d ./doc/ -cp ./doc  ./src/Vector.java ./src/CloudData.java ./src/SumArray1.java ./src/SumArray2.java ./src/SumAll.java 
	 
	
			
	
