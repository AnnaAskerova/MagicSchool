-- liquibase formatted sql
-- changeset anna:1
create index student_name_index ON student (name);

-- changeset anna:2
create index faculty_name_color_index ON faculty (name, color);
