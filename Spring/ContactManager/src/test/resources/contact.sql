DROP TABLE IF EXISTS public.contacts;

CREATE TABLE public.contacts (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255),
    surname varchar(255),
    phone_number varchar(255) UNIQUE,
    email varchar(255)
);

INSERT INTO public.contacts(id, name, surname, phone_number, email)
VALUES (1000, 'Ivan', 'Ivanov', '1234567', 'iivanov@gmail.com');

INSERT INTO public.contacts(id, name, surname, phone_number, email)
VALUES (2000, 'Maria', 'Ivanova', '7654321', 'mivanova@gmail.com');