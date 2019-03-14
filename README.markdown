Roboscratch
======

Development
------
Cкачать maven 

	https://github.com/apache/maven/releases/tag/maven-3.6.0

Maven устанавливается просто копированием в нужную директорию — никакого инсталлера нет.
Как и в случае с большинством консольных утилит для использования достаточно добавить директорию maven/bin в переменную окружения PATH.

То есть, если maven находится в d:/soft/maven, то в PATH надо добавить d:/soft/maven/bin
Ещё для работы maven потребует переменную JAVA_HOME, которая указывает на JDK. Если JDK находится в C:/Program Files/Java/jdk1.8.0_05, то именно такое значение нужно поместить в JAVA_HOME. Добавлять bin в конец не нужно.

Выполнить из директории

	git clone https://github.com/taweili/openblocks.git && cd openblocks && mvn install &&cd .. && rd /s /q openblocks

После чего проект должен скомпилится в netbeans (тыкнуть на молоточек с щеточкой сверху)

(если нет)
----------
попробовать:
	
	mvn validate
	mvn clean package
	mvn install 
