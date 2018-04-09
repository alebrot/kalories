
CREATE TABLE IF NOT EXISTS MEAL
(
  ID                 INT  AUTO_INCREMENT PRIMARY KEY,
  TEXT               TEXT,
  DATE               TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  NUMBER_OF_CALORIES INT default 0 NOT NULL
);