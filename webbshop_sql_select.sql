
create view q1 as select distinct customer.name
from customer
join customerorder on customer.id = customerorder.customerid
join orderinfo on customerorder.id = orderinfo.customerorderid
join shoe on orderinfo.shoeid = shoe.id
join shoeinfo on shoe.id = shoeinfo.shoeid
join category on shoeinfo.categoryid = category.id
where shoe.brand = 'Ecco'
and shoe.color = 'Black'
and shoe.size = 38
and category.name = 'Sandals';


create view q2 as select category.name, sum(shoe.quantity) as quantity
from category
join shoeinfo on category.id = shoeinfo.categoryid
join shoe on shoe.id = shoeinfo.shoeid
group by category.name;


create view q3 as select customer.name, sum(shoe.price) as total_payment
from customer
join customerorder on customer.id = customerorder.customerid
join orderinfo on customerorder.id = orderinfo.customerorderid
join shoe on orderinfo.shoeid = shoe.id
group by customer.name
order by sum(shoe.price) desc;

create view q4 as select customer.city, sum(shoe.price) as total_payment
from customer
join customerorder on customer.id = customerorder.customerid
join orderinfo on customerorder.id = orderinfo.customerorderid
join shoe on orderinfo.shoeid = shoe.id
group by customer.city
having sum(shoe.price) > 1000;


create view q5 as select shoe.id as shoe_id, count(orderinfo.shoeid) as times_sold
from shoe
join orderinfo on shoe.id = orderinfo.shoeid
group by shoe.id
order by times_sold desc
limit 5;


create view q6 as select monthname(customerorder.orderdate) as '2026', sum(shoe.price) as 'Total Sales'
from customerorder
join orderinfo on customerorder.id = orderinfo.customerorderid
join shoe on orderinfo.shoeid = shoe.id
where year(customerorder.orderdate) = 2026
group by monthname(customerorder.orderdate)
order by sum(shoe.price) desc;