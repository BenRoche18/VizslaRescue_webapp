INSERT IGNORE INTO roles (role) 
VALUES ('ROLE_USER'),('ROLE_ADMIN');

INSERT IGNORE INTO users (username, password, active) 
VALUES ('user','$2a$10$dQR/IYOY94DTtNZc8gP5IulcLCXWrcB3F.k4PFT7pUmrrIHeySw3.',1),('admin','$2a$10$2ZFk/PPGKL.s4nGAqJVlu.DO4OxXo029HZKe6TANNxTh1zxztLvQW',1);

INSERT IGNORE INTO user_role (username, role)
VALUES ('user','ROLE_USER'),('admin','ROLE_USER'),('admin','ROLE_ADMIN');