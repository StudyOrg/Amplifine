-- FIRED
create or replace trigger trg_workers_fired_insert before insert on WORKERS for each row
begin
	if (:new.fired is not null) then
		raise_application_error(-20001, 'invalid fired date: fired must be null on insert');
	end if;
end;
/
create or replace trigger trg_workers_fired_update before update on WORKERS for each row
begin
	if (:new.fired is not null) then
		if (:new.enter is not null) then
			if(to_date(:new.fired, 'DD.MM.YY') < to_date(:new.enter, 'DD.MM.YY')) then
				raise_application_error(-20002, 'invalid fired date: fired date less than enter date');
			end if;
		else
			if (to_date(:new.fired, 'DD.MM.YY') < to_date(:old.enter, 'DD.MM.YY')) then
				raise_application_error(-20002, 'invalid fired date: fired date less than enter date');
			end if;
		end if;
	else
		if (:old.fired is not null) then
			if (:new.enter is not null) then
				if(to_date(:old.fired, 'DD.MM.YY') < to_date(:new.enter, 'DD.MM.YY')) then
					raise_application_error(-20002, 'invalid fired date: fired date less than enter date');
				end if;
			end if;
		end if;
	end if;
end;
/
-- PRICES
create or replace trigger trg_goods_retail_price before insert or update on GOODS for each row
begin
	if (:new.retail_price <= 0) then
		raise_application_error(-20003, 'invalid retail price: price less 0');
	elsif (:new.retail_price is null) then
		raise_application_error(-20003, 'invalid retail price: price = null');
	end if;
end;
/
create or replace trigger trg_unitsupplies_pprice before insert or update on UNITSUPPLIES for each row
begin
	if (:new.purchasing_price <= 0) then
		raise_application_error(-20004, 'invalid purchase price: price less 0');
	elsif (:new.purchasing_price is null) then
		raise_application_error(-20004, 'invalid purchase price: price = null');
	end if;
end;
/
create or replace trigger trg_unitsales_retail_price before insert or update on UNITSALES for each row
begin
	if (:new.full_retail_price < 0) then
		raise_application_error(-20005, 'invalid retail price: price less 0');
	elsif (:new.full_retail_price is null) then
		raise_application_error(-20005, 'invalid retail price: price = null');
	end if;
end;
/
--AMOUNT
create or replace trigger trg_unitsupplies_amount before insert or update on UNITSUPPLIES for each row
begin
	if (:new.amount <= 0) then
		raise_application_error(-20006, 'invalid amount: amount less 1');
	elsif (:new.amount is null) then
		raise_application_error(-20006, 'invalid amount: amount = null');
	end if;
end;
/
create or replace trigger trg_unitsales_amount before insert or update on UNITSALES for each row
begin
	if (:new.amount <= 0) then
		raise_application_error(-20007, 'invalid amount: amount less 1');
	elsif (:new.amount is null) then
		raise_application_error(-20007, 'invalid amount: amount = null');
	end if;
end;
/
create or replace trigger trg_goods_amount before insert or update on GOODS for each row
begin
	if (:new.amount < 0) then
		raise_application_error(-20008, 'invalid amount: amount less 0');
	elsif (:new.amount is null) then
		raise_application_error(-20008, 'invalid amount: amount = null');
	end if;
end;
/
--PARRENT SELF
create or replace trigger trg_manufacturer_parent before update on MANUFACTURERS for each row
begin
	if (:new.parent_manufacturer is not null) then
		if (:new.parent_manufacturer = :old.id) then
			raise_application_error(-20009, 'invalid parrent manufacturer');
		end if;
	end if;
end;
/
--BIRTHDAY CHECK
create or replace trigger trg_birthday_worker before insert or update on WORKERS for each row
declare
	min_year integer;
begin
	min_year := to_number(extract(year from sysdate)) - 20;

	if (extract(year from :new.birthday) > min_year) then
		raise_application_error(-20010, 'invalid bithday: workers birth year must be 1995 or elder');
	end if;
end;
/
--START FINISH WORKINGDAYS
create or replace trigger trg_workingday before insert or update on WORKINGDAYS for each row
begin
	if (:new.time_start is not null) then
		if (:new.time_finish is not null) then
			if (:new.time_start > :new.time_finish) then
				raise_application_error(-20011, 'Hour start working day bigger than finish hour');
			end if;
		else
			if (:new.time_start > :old.time_finish) then
				raise_application_error(-20011, 'Hour start working day bigger than finish hour');
			end if;
		end if;
	else
		if (:new.time_finish is not null) then
			if (:old.time_start > :new.time_finish) then
				raise_application_error(-20011, 'Hour start working day bigger than finish hour');
			end if;
		end if;
	end if;
end;
/
