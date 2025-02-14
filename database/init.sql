CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    year INT NOT NULL
);

INSERT INTO books (title, author, year) VALUES
('Книга 1', 'Алексей Варламов', 2025),
('Книга 2', 'Федор Бондарь', 2024),
('Книга 3', 'Дмитрий Дорофеев', 2023);
