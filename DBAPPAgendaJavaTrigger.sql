delimiter $$
create trigger trggBeforeInsertTUsuario before insert on TUsuario FOR EACH ROW
begin
set @ultimoCodigo=(select max(codigoUsuario) from TUsuario);
if @ultimoCodigo is null then
	set @ultimoCodigo="USUARIOX0000000";
end if;
set @parteTexto=mid(@ultimoCodigo, 1, 8);
set @parteNumerica=mid(@ultimoCodigo, 9, 7)+1;
set @longitudNumero=(select length(@parteNumerica));
set @codigoNumerico=concat(repeat('0', 7-@longitudNumero), @parteNumerica);
set @codigo=concat(@parteTexto, @codigoNumerico);
set NEW.codigoUsuario=(select @codigo);
end
$$

delimiter $$
create trigger trggBeforeInsertTUsuarioAmigo before insert on TUsuarioAmigo FOR EACH ROW
begin
set @ultimoCodigo=(select max(codigoUsuarioAmigo) from TUsuarioAmigo);
if @ultimoCodigo is null then
	set @ultimoCodigo="USUARIOA0000000";
end if;
set @parteTexto=mid(@ultimoCodigo, 1, 8);
set @parteNumerica=mid(@ultimoCodigo, 9, 7)+1;
set @longitudNumero=(select length(@parteNumerica));
set @codigoNumerico=concat(repeat('0', 7-@longitudNumero), @parteNumerica);
set @codigo=concat(@parteTexto, @codigoNumerico);
set NEW.codigoUsuarioAmigo=(select @codigo);
end
$$