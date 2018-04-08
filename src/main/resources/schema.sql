
CREATE TABLE IF NOT EXISTS MEAL
(
  ID                 int  AUTO_INCREMENT PRIMARY KEY,
  TEXT               text,
  DATE               DATE default NOW(),
  NUMBER_OF_CALORIES int default 0 not null
);