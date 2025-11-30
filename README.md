# Educational Platform - Commit to Learn

## 🚀 Технологический стек
- **Backend**: Java 17 + Spring Boot 3.x
- **Frontend**: HTML5 + CSS3 + Thymeleaf + JavaScript
- **Database**: PostgreSQL 15
- **Containerization**: Docker + Docker Compose
- **Build Tool**: Maven

## 📦 Предварительные требования
- Java 17 или выше
- PostgreSQL 15
- Maven 3.8+
- Docker (опционально)

## 🛠️ Быстрый старт

### Локальная установка
1. Клонировать репозиторий
2. Настроить базу данных:
```sql
CREATE DATABASE educational_platform;
CREATE USER platform_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE educational_platform TO platform_user;
```
3. Настройка application.properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/educational_platform
spring.datasource.username=platform_user
spring.datasource.password=password
```

4. Запуск приложения:
```bash
mvn spring-boot:run
```

### Запуск через Docker

```bash
docker-compose up -d
```

## 🗂️ Структура проекта

```text
src/
├── main/
│   ├── java/
│   │   └── com/educationalplatform/
│   │       ├── controller/     # REST контроллеры
│   │       ├── service/        # Бизнес-логика
│   │       ├── repository/     # Data access layer
│   │       ├── entity/         # JPA сущности
│   │       └── config/         # Конфигурации
│   └── resources/
│       ├── templates/          # Thymeleaf шаблоны
│       ├── static/             # CSS, JS, изображения
│       └── application.properties
```

## 🔧 API Endpoints

|Метод|URL|Описание|
|---|---|---|
|GET|/api/courses|Список курсов|
|POST|/api/assignments/{id}/submit|Отправка ДЗ|
|GET|/api/grades|Журнал успеваемости|

## 👥 Роли пользователей

- **Студент**: изучение материалов, отправка ДЗ, просмотр оценок
- **Преподаватель**: создание курсов, проверка работ, выставление оценок