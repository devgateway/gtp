
update category p
set market_type_id=m.id
from category m
where p.dtype='ProductType'
  and p.name in ('cereals', 'vegetables', 'fruits', 'livestock')
  and m.dtype='MarketType'
  and m.name='rural-market';

update category p
set market_type_id=m.id
from category m
where p.dtype='ProductType'
  and p.name='fresh-fish'
  and m.dtype='MarketType'
  and m.name='fishing-dock';

update category p
set market_type_id=m.id
from category m
where p.dtype='ProductType'
  and p.name='processed-fish'
  and m.dtype='MarketType'
  and m.name='transformation-place';
