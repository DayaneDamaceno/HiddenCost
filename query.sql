CREATE DATABASE hiddenCost;
CREATE TABLE INGREDIENTES
(
    id_ingrediente INTEGER identity(1,1) PRIMARY KEY NOT NULL,
    preco DECIMAL(10,2),
    peso DECIMAL(10,2),
    unidade_de_medida varchar(50) NOT NULL,
    nome VARCHAR(70) NOT NULL
)
CREATE TABLE USUARIOS
(
    id_usuario INTEGER identity(1,1) PRIMARY KEY NOT NULL,
    nome VARCHAR(50) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    email VARCHAR(60) NOT NULL
)
CREATE TABLE PRODUTOS
(
    id_produto INTEGER identity(1,1) PRIMARY KEY NOT NULL,
    id_usuario INTEGER NOT NULL,
    nome VARCHAR(30) NOT NULL,
    peso DECIMAL(10,2) NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT FK_produto_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIOS (id_usuario)
)
CREATE TABLE PRODUTOS_INGREDIENTES
(
    id_produto_ingrediente INTEGER identity(1,1) PRIMARY KEY NOT NULL,
    id_produto INTEGER NOT NULL,
    id_ingrediente INTEGER NOT NULL,
    medida DECIMAL(10,2) NOT NULL,
    CONSTRAINT FK_produto_ingrediente FOREIGN KEY (id_produto) REFERENCES PRODUTOS (id_produto) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ingrediente FOREIGN KEY (id_ingrediente) REFERENCES INGREDIENTES (id_ingrediente) ON DELETE CASCADE ON UPDATE CASCADE
)
CREATE TABLE PERFIS
(
    id_perfil INTEGER identity(1,1) PRIMARY KEY NOT NULL,
    id_usuario INTEGER NOT NULL,
    tipo varchar(50) NOT NULL, -- ADMIN, COMUM
    CONSTRAINT FK_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIOS(id_usuario)
)
CREATE TABLE EQUIPAMENTOS
(
    id_equipamento INTEGER identity(1,1) PRIMARY KEY NOT NULL,
    nome varchar(50) NOT NULL,
    tipo varchar(50) NOT NULL, -- UTENSILIOS, ELETRODOMESTICO
    marca VARCHAR(20) NOT NULL,
    consumoWatt DECIMAL(10,2) NOT NULL
)
CREATE TABLE PRODUTOS_EQUIPAMENTOS
(
    id_produto_equipamento INTEGER identity(1,1) PRIMARY KEY NOT NULL,
    id_produto INTEGER NOT NULL,
    id_equipamento INTEGER NOT NULL,
    tempo_de_uso INTEGER NOT NULL, --em minutos
    CONSTRAINT FK_produto_equipamento FOREIGN KEY (id_produto) REFERENCES PRODUTOS (id_produto) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_equipamento FOREIGN KEY (id_equipamento) REFERENCES EQUIPAMENTOS (id_equipamento) ON DELETE CASCADE ON UPDATE CASCADE
)

insert into INGREDIENTES (preco, peso, unidade_de_medida, nome) values (12.5, 23, 'GRAMAS','Farinha')
insert into INGREDIENTES (preco, peso, unidade_de_medida, nome) values (9.5, 23, 'UNIDADE','Outro ingrediente')
insert into EQUIPAMENTOS (nome, tipo, marca, consumoWatt) values ('Batedeira','UTENSILIOS', 'Brastemp', 12.9)
insert into EQUIPAMENTOS (nome, tipo, marca, consumoWatt) values ('Outro equipamento','UTENSILIOS', 'Marca', 12.9)
insert into USUARIOS (nome, email, senha) values ('dayane', 'day@gmail.com', 'Dayane@08642ts')
insert into PRODUTOS (id_usuario, nome, peso, preco_unitario) values (1, 'Browni', 40, 5.9)
insert into PRODUTOS_EQUIPAMENTOS (id_produto, id_equipamento, tempo_de_uso) values (1,2, 10)
insert into PRODUTOS_EQUIPAMENTOS (id_produto, id_equipamento, tempo_de_uso) values (1,1, 10)
insert into PRODUTOS_INGREDIENTES (id_produto, id_ingrediente, medida) values (1,2, 10)
