Веб-приложения для ведения журнала проведённых конференций компании. 
На конференцию назначается ответственный, отделение компании, и участники конференции. 
Добавление рабочих, отделений компании не предусмотрено (для проверки добавлять мануально в базу данных)

# Сборка проекта через Maven
```mvn package```

# Создание MySQL БД
По адресу localhost:3306, username: root, password: root выполнить:
```CREATE SCHEMA `conferencejournal` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; ```

# Запуск 
```java -jar target/conferencejour-jar-with-dependencies.jar```

В браузере по адресу localhost:8080 расположится веб-приложение.
