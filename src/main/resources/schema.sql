CREATE SEQUENCE IF NOT EXISTS meals_meal_id_seq START 1;
CREATE SEQUENCE IF NOT EXISTS ingredients_ingredient_id_seq START 1;

CREATE TABLE IF NOT EXISTS meals (
    meal_id INTEGER PRIMARY KEY,
    category VARCHAR(1024),
    meal VARCHAR(1024) NOT NULL
);
CREATE TABLE IF NOT EXISTS ingredients (
    ingredient VARCHAR(1024),
    ingredient_id INTEGER PRIMARY KEY,
    meal_id INTEGER REFERENCES meals(meal_id)
);
CREATE TABLE IF NOT EXISTS plan (
    day_of_week VARCHAR(16) NOT NULL,
    meal_category VARCHAR(16) NOT NULL,
    meal_id INTEGER REFERENCES meals(meal_id),
    PRIMARY KEY (day_of_week, meal_category)
);
CREATE SEQUENCE IF NOT EXISTS users_user_id_seq START 1;
CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
);
ALTER TABLE meals
ADD COLUMN user_id INTEGER REFERENCES users(user_id);