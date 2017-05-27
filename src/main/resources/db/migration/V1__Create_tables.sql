CREATE TABLE IF NOT EXISTS items(
    id serial NOT NULL PRIMARY KEY,
    name varchar(200) NOT NULL,
    price integer NOT NULL
);

CREATE TABLE IF NOT EXISTS orders(
    id serial NOT NULL PRIMARY KEY,
    data varchar(200) NOT NULL
);

CREATE table IF NOT EXISTS order_item(
  id serial NOT NULL PRIMARY KEY,
  id_order integer not null,
  id_item integer not null,
  FOREIGN KEY (id_order) REFERENCES orders(id),
  FOREIGN KEY (id_item) REFERENCES items(id)
);