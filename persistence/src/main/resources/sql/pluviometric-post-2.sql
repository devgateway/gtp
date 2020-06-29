
-- move posts to correct departments
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Bakel' and p.label='Kidira';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Bignona' and p.label='Diouloulou';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Bignona' and p.label='Sindian';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Dagana' and p.label='Richard-Toll';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Diourbel' and p.label='Diourbel';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Goudomp' and p.label='Diatacounda';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Guinguinéo' and p.label='Guinguinéo';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Guédiawaye' and p.label='Guédiawaye';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Kanel' and p.label='Semme';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Kolda' and p.label='Kolda';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Koumpentoum' and p.label='Koumpentoum';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Koungheul' and p.label='Koungheul';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Kébémer' and p.label='Kébémer';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Kédougou' and p.label='Kédougou';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Linguère' and p.label='Déaly';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Matam' and p.label='Thilogne';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Ranérou' and p.label='Ranerou';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Rufisque' and p.label='Diamniadio';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Rufisque' and p.label='Rufisque';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Saint-Louis' and p.label='Saint Louis';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Sédhiou' and p.label='Sédhiou';
update pluviometric_post p
set department_id=d.id
from department d
where d.name='Thiès' and p.label='Thiès';

-- delete Tivouane
delete from rainfall
where pluviometric_post_rainfall_id in (
    select pr.id
    from pluviometric_post p
        join pluviometric_post_rainfall pr on p.id = pr.pluviometric_post_id
    where p.label='Tivouane');

delete from pluviometric_post_rainfall
where pluviometric_post_id in (select id from pluviometric_post where label='Tivouane');

delete from rain_season_pluviometric_post_reference_start
where pluviometric_post_id in (select id from pluviometric_post where label='Tivouane');

delete from rain_level_month_reference
where rain_level_pluviometric_post_reference_id in (
    SELECT r.id
    from rain_level_pluviometric_post_reference r
    join pluviometric_post p on r.pluviometric_post_id = p.id
    where p.label='Tivouane');

delete from rain_level_pluviometric_post_reference
where pluviometric_post_id in (select id from pluviometric_post where label='Tivouane');

delete from pluviometric_post where label='Tivouane';

Fixed department for the following rainfall stations: Kidira, Diouloulou, Sindian, Richard-Toll, Diourbel, Diatacounda, Guinguinéo, Guédiawaye, Semme, Kolda, Koumpentoum, Koungheul, Kébémer, Kédougou, Déaly, Thilogne, Ranerou, Diamniadio, Rufisque, Saint Louis, Sédhiou, Thiès.

Also removed Tivouane station.