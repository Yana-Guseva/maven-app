CREATE TABLE IF NOT EXISTS DOG(
  id UUID primary key,
  name varchar(100) not null,
  dateOfBirth timestamp,
  height double precision not null,
  weight double precision not null);