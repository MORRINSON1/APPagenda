create database DBAPPAgendaJava;
use DBAPPAgendaJava;

create table TUsuario
(
codigoUsuario char(15) not null,
nombre varchar(30) not null,
apellidoPaterno varchar(20) not null,
apellidoMaterno varchar(20) not null,
correoElectronico varchar(30) not null,
contrasenia varchar(700) not null,
fechaNacimiento date not null,
sexo bool not null,
telefono varchar(20) not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
primary key(codigoUsuario)
);

create table TUsuarioAmigo
(
codigoUsuarioAmigo char(15) not null,
codigoUsuario char(15) not null,
nombre varchar(30) not null,
apellidoPaterno varchar(20) not null,
apellidoMaterno varchar(20) not null,
correoElectronico varchar(30) not null,
contrasenia varchar(700) not null,
fechaNacimiento date not null,
sexo bool not null,
telefono varchar(20) not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
foreign key(codigoUsuario) references TUsuario(codigoUsuario)
on update cascade on delete cascade,
primary key(codigoUsuarioAmigo)
);

create table TUsuarioAmigoTelefono
(
codigoUsuarioAmigoTelefono char(15) not null,
codigoUsuarioAmigo char(15) not null,
descripcion text not null,
telefono varchar(20) not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
foreign key(codigoUsuarioAmigo) references TUsuarioAmigo(codigoUsuarioAmigo)
on update cascade on delete cascade,
primary key(codigoTUsuarioAmigoTelefono)
);

create table TUnidadMedida
(
codigoUnidadMedida char(15) not null,
nombre varchar(30) not null,
descripcion text not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
primary key (codigoUnidadMedida)
);

create table TActividad
(
codigoActividad char(15) not null,
codigoUsuario char(15) not null,
nombre varchar(700) not null,
descripcion text not null,
fechaInicio datetime not null,
fechaFin datetime not null,
presupuestoTotal decimal(8, 2) not null,
estado char(1) not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
foreign key(codigoUsuario) references TUsuario(codigoUsuario)
on update cascade on delete cascade,
primary key(codigoActividad)
);

create table TActividadPresupuesto
(
codigoActividadPresupuesto char(15) not null,
codigoActividad char(15) not null,
codigoUnidadMedida char(15) not null,
descripcion text not null,
precioUnitario decimal(8, 2) not null,
cantidad float not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
foreign key(codigoActividad) references TActividad(codigoActividad)
on update cascade on delete cascade,
foreign key(codigoUnidadMedida) references TUnidadMedida(codigoUnidadMedida)
on update cascade on delete cascade,
primary key(codigoActividadPresupuesto)
);

create table TActividadParticipante
(
codigoActividadParticipante char(15) not null,
codigoActividad char(15) not null,
codigoUsuarioAmigo char(15) not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
foreign key(codigoActividad) references TActividad(codigoActividad)
on update cascade on delete cascade,
foreign key(codigoUsuarioAmigo) references TUsuarioAmigo(codigoUsuarioAmigo)
on update cascade on delete cascade,
primary key(codigoActividadParticipante)
);

create table TActividadComentario
(
codigoActividadComentario char(15) not null,
codigoActividad char(15) not null,
codigoUsuarioAmigo char(15) not null,
comentario text not null,
fechaRegistro timestamp not null default current_timestamp,
fechaModificacion timestamp not null default current_timestamp on update current_timestamp,
foreign key(codigoActividad) references TActividad(codigoActividad)
on update cascade on delete cascade,
foreign key(codigoUsuarioAmigo) references TUsuarioAmigo(codigoUsuarioAmigo)
on update cascade on delete cascade,
primary key(codigoActividadComentario)
);