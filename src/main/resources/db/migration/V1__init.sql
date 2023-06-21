CREATE TABLE users (
    id serial,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled boolean NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE authorities (
    email varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,

    CONSTRAINT authorities_idx UNIQUE (email, authority),

    CONSTRAINT authorities_ibfk_1
    FOREIGN KEY (email)
    REFERENCES users (email)
);

CREATE TABLE links (
   id serial,
   suffix VARCHAR(10) NOT NULL UNIQUE,
   redirect_url VARCHAR NOT NULL,
   enabled boolean NOT NULL,
   created_at TIMESTAMP NOT NULL,
   disable_date TIMESTAMP NOT NULL,
   delete_date TIMESTAMP NOT NULL,
   user_id INTEGER,

   FOREIGN KEY(user_id) REFERENCES users (id)
);