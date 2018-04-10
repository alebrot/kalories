DROP TABLE IF EXISTS USER_MEALS;
DROP TABLE IF EXISTS CALORIES_PER_USER;
DROP TABLE IF EXISTS MEAL;
DROP TABLE IF EXISTS USER;

CREATE TABLE MEAL
(
  ID                 INT AUTO_INCREMENT PRIMARY KEY,
  TEXT               TEXT,
  DATE               TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  NUMBER_OF_CALORIES INT default 0                       NOT NULL
);

CREATE TABLE USER
(
  ID         INT AUTO_INCREMENT PRIMARY KEY,
  FIRST_NAME VARCHAR(255) NOT NULL,
  LAST_NAME  VARCHAR(255) NOT NULL
);

CREATE TABLE CALORIES_PER_USER
(
  ID                 INT AUTO_INCREMENT PRIMARY KEY,
  USER_ID            INT NOT NULL,
  NUMBER_OF_CALORIES INT NOT NULL,

  CONSTRAINT CALORIES_PER_USER_USER_ID_FK
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT USER_ID_U UNIQUE (USER_ID)

);

CREATE TABLE USER_MEALS
(
  ID          INT AUTO_INCREMENT PRIMARY KEY,
  USER_ID     INT                                 NOT NULL,
  MEAL_ID     INT                                 NOT NULL,
  UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

  CONSTRAINT USER_MEALS_USER_ID_FK
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT USER_MEALS_MEAL_ID_FK
  FOREIGN KEY (MEAL_ID) REFERENCES MEAL (ID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT USER_ID_MEAL_ID_U UNIQUE (USER_ID, MEAL_ID)

);
