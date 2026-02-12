-- drop procedure AddToCart;
delimiter //
create procedure AddToCart(
in c_id int, in s_id int
)
begin

	declare co_id int;
    
    declare exit handler for sqlexception
    begin
    rollback;
    resignal;
    end;
    
    start transaction;
    
    if not exists (select * from shoe where id = s_id) then
    rollback;
    signal sqlstate '45000'
    set message_text = 'Invalid choice, shoe does not exist';
    end if;
    
    select id into co_id from customerorder where customerid = c_id and status = 'ACTIVE' limit 1;
    
    if co_id is not null then
    insert into orderinfo (shoeid, customerorderid)
    values (s_id, co_id);
    
    else
    
    insert into customerorder (orderdate, customerid, status)
    values (curdate(), c_id, 'ACTIVE');
    insert into orderinfo (shoeid, customerorderid)
    values (s_id, last_insert_id());
    end if;
    
    update shoe set quantity = quantity -1 where id = s_id
    and quantity > 0;
    
    if row_count() = 0 then
    rollback;
    signal sqlstate '45000'
    set message_text = 'Shoe is out of stock';
    end if;
    
    commit;
    
end //
delimiter ;

-- Trigger that denies orders that are PAID to have products added to them
-- drop trigger before_insert_paid;
delimiter //
create trigger before_insert_paid
before insert on orderinfo
for each row
begin
	if exists (select * from customerorder where id = new.customerorderid
    and status = 'PAID') then
    signal sqlstate '45000'
    set message_text = 'Cannot add products to a paid order';
    end if;
end//
delimiter ;

-- drop procedure payOrder;
delimiter //
create procedure payOrder(in c_id int)
begin
    update customerorder set status = 'PAID'
    where customerid = c_id
    and status = 'ACTIVE';
end//
delimiter ;

-- drop trigger after_update_shoe;
delimiter //
create trigger after_update_shoe
after update on shoe
for each row
begin
	if old.quantity > 0 and new.quantity = 0 then
    insert into OutOfStock (shoeid, dateoutofstock)
    values (new.id, now());
    end if;
end//
delimiter ;

select * from q1;
select * from q2;
select * from q3;
select * from q4;
select * from q5;
select * from q6;

select * from shoe;
select * from category;
select * from shoeinfo;
select * from orderinfo;
select * from customerorder;
select * from customer;
select * from outofstock;