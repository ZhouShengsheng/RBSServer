/**
 *	RBS fulltext search.
 */

alter table Room
	add fulltext(building, `number`);

select *
	from Room
    where concat(building, `number`) like '%none%';

select *
	from Room
    where concat(building, `number`) like '%软件楼102%'
    order by building desc, `number`;