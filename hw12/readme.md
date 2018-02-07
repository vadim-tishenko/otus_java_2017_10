ДЗ-12: Веб сервер
===

Встроить веб сервер в приложение из ДЗ-11.
--- 
Сделать админскую страницу, 
на которой админ должен авторизоваться
и получить доступ к параметрам и состоянию кэша.

компиляция: 
cd hw10
mvn clean install
cd ../hw11
mvn clean install
cd ../hw12
mvn clean package

запуск:
cd hw12
java -jar target/server.jar 
http://localhost:8888