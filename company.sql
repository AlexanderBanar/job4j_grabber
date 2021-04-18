select n.name, cc.name from person as n join company as cc on n.company_id = cc.id
where n.company_id != 5;

select cc.name as company_name, count(p.company_id) as max_personnel
from person p join company cc on p.company_id = cc.id
group by company_name order by max_personnel desc limit 1;