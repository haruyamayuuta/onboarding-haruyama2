select tp.name
from test.users as tu
inner join test.pets as tp
on tu.id=tp.user_id and tp.user_id=2;