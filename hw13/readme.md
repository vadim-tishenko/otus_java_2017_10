#ДЗ-13: WAR

* Собрать war для приложения из ДЗ-12. 
* Создавать кэш и DBService как Spring beans, передавать (inject) их в сервлеты. 
* Запустить веб приложение во внешнем веб сервере.

сборка:
cd hw11
mvw clean install


cd hw12
mvw clean install

cd hw13
mvn clean war:war

запуск:
cd hw13
mvn jetty:run-war
http://localhost:8000
логин admin, пароль admin