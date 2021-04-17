select n.name, cc.name from person as n join company as cc on n.company_id = cc.id
where n.company_id != 5;

select max(k.counted) from
(select foo.name, count(foo.name) as counted from
(select n.name from company as n left join person as nn on n.id = nn.company_id) as foo
group by foo.name order by foo.name) as k;