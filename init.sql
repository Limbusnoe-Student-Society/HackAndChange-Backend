CREATE DATABASE auth_db;
CREATE DATABASE course_db;
CREATE DATABASE homework_db;
CREATE DATABASE journal_db;
CREATE DATABASE communication_db;

ALTER DATABASE auth_db OWNER TO ${DB_USERNAME:-app_user};

ALTER DATABASE course_db OWNER TO ${DB_USERNAME:-app_user};
ALTER DATABASE homework_db OWNER TO ${DB_USERNAME:-app_user};
ALTER DATABASE journal_db OWNER TO ${DB_USERNAME:-app_user};
ALTER DATABASE communication_db OWNER TO ${DB_USERNAME:-app_user};

GRANT ALL PRIVILEGES ON DATABASE auth_db TO ${DB_USERNAME:-app_user};
GRANT ALL PRIVILEGES ON DATABASE course_db TO ${DB_USERNAME:-app_user};
GRANT ALL PRIVILEGES ON DATABASE homework_db TO ${DB_USERNAME:-app_user};
GRANT ALL PRIVILEGES ON DATABASE journal_db TO ${DB_USERNAME:-app_user};
GRANT ALL PRIVILEGES ON DATABASE communication_db TO ${DB_USERNAME:-app_user};

create table roles
(
    id   bigserial
        primary key,
    name varchar(255) not null
);
ALTER TABLE roles owner to ${DB_USERNAME:-app_user};
INSERT INTO roles VALUES (1, 'ROLE_USER');

create table courses
(
    course_id   uuid         not null
        primary key,
    description varchar(255) not null,
    title       varchar(255) not null
);

alter table courses
    owner to ${DB_USERNAME:-app_user};

create table course_modules
(
    module_id uuid         not null
        constraint "course-modules_pkey"
            primary key,
    ordering  integer      not null,
    title     varchar(255) not null,
    course_id uuid
        constraint fk650ca2wyybhe4jeelcfav3uao
            references courses
);

alter table course_modules
    owner to ${DB_USERNAME:-app_user};

create table course_lessons
(
    lesson_id uuid         not null
        constraint "course-lessons_pkey"
            primary key,
    ordering  integer      not null,
    title     varchar(255) not null,
    module_id uuid
        constraint fkn1i8wa9sbw9e56guldce1voe
            references course_modules
);

alter table course_lessons
    owner to ${DB_USERNAME:-app_user};

create table course_pages
(
    page_id   uuid         not null
        constraint "course-pages_pkey"
            primary key,
    content   varchar(255),
    ordering  integer      not null,
    title     varchar(255) not null,
    type      varchar(255) not null
        constraint "course-pages_type_check"
            check ((type)::text = ANY
                   ((ARRAY ['TEXT'::character varying, 'VIDEO'::character varying, 'QUIZ'::character varying])::text[])),
    video_url varchar(255),
    lesson_id uuid
        constraint fkg8tigf8uvnyme5pendpsu7ndv
            references course_lessons
);

alter table course_pages
    owner to ${DB_USERNAME:-app_user};