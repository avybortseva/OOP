#!/bin/bash

# Удаляем предыдущую сборку и создаем директории
rm -rf build
mkdir -p build/classes build/docs build/jar build/test-classes
# Компиляция основного кода
javac -d build/classes src/main/java/ru/nsu/g/a/vybortseva/sort/*.java
# Компиляция тестов
javac -d build/test-classes -cp "build/classes:lib/*" src/test/java/ru/nsu/g/a/vybortseva/sort/*.java
# Генерация документации
javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.g.a.vybortseva.sort
# Создание JAR-файла
jar -cf build/jar/sort.jar -C build/classes .
# Запуск тестов
java -cp "build/test-classes:build/classes:lib/*" org.junit.platform.console.ConsoleLauncher -c ru.nsu.g.a.vybortseva.sort.SortTest
