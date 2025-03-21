CREATE TABLE cd_collection(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL
);

CREATE TABLE cd_tracks(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    cd_id INTEGER NOT NULL,
    track_name VARCHAR(255) NOT NULL,
    duration VARCHAR(100) NOT NULL,
    FOREIGN KEY (cd_id) REFERENCES cd_collection(id) ON DELETE CASCADE
);