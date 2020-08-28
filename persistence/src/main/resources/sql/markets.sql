ALTER TABLE market DROP CONSTRAINT IF EXISTS  ukki8r2jl4st0ensxmjqjd1air9;

DELETE FROM product_price;

DELETE FROM market;

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.7264, -17.4486, 127, 'Castors', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Dakar';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.6842, -17.4578, 127, 'Gueule tapée', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Dakar';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.6842, -17.4578, 127, 'Tilène', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Dakar';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.7425, -17.3794, 127, 'Thiaroye', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Pikine';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.7007, -16.4591, 1, 'Bambey', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Bambey';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.6628, -16.3925, 127, 'Diourbel', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Diourbel';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.8833, -16.2167, 64, 'Ndindy', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Diourbel';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.8500, -15.8833, 2, 'Keur i. Yacine', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Mbacké';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.8500, -15.8833, 127, 'Touba', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Mbacké';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.4667, -16.3000, 8, 'Diakhao', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Fatick';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.3581, -16.5858, 127, 'Fatick', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Fatick';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.4972, -16.2342, 32, 'Gossas', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Gossas';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.5333, -15.7667, 1, 'Mbar', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Gossas';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.9833, -16.2667, 32, 'Passy', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Foundiougne';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.1284, -15.8819, 8, 'Diamagadio', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Birkilane';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.1284, -15.8819, 64, 'Mbirkilane', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Birkilane';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.9833, -15.3333, 2, 'Dioli mandakh', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kaffrine';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.1217, -15.6931, 127, 'Kaffrine', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kaffrine';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.9167, -15.6833, 4, 'Mabo', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kaffrine';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.9833, -14.8000, 127, 'Koungheul', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Koungheul';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.6804, -16.5013, 8, 'Missirah', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Koungheul';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.1825, -16.2533, 127, 'Kaolack', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kaolack';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.9186, -15.9261, 1, 'Ndoffane', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kaolack';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.8000, -16.1667, 1, 'Ndrame escale', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Nioro du Rip';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.7000, -15.8333, 16, 'Porokhane', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Nioro du Rip';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.4833, -11.9500, 127, 'Kédougou', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kédougou';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.8555, -12.3506, 8, 'Mako', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kédougou';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.6342, -12.8204, 127, 'Salemata', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Salemata';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.7000, -14.4000, 4, 'Diaobé', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Vélingara';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.8833, -14.9500, 127, 'Kolda', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kolda';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.7658, -15.1075, 8, 'Sare yoba', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kolda';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.6743, -16.0303, 1, 'Gouille mbeuth', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Louga';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.6187, -16.2244, 127, 'Louga', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Louga';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.1028, -15.9642, 8, 'Ndiagne', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Louga';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.2758, -16.1739, 4, 'Sagatta', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kébémer';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.2821, -12.9611, 64, 'Orkodiere', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Kanel';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.6000, -13.3167, 127, 'Ourossogui', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Matam';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.6000, -13.3167, 2, 'Thiodaye', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Matam';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.7081, -15.5569, 127, 'Sédhiou', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Sédhiou';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.7081, -15.5569, 32, 'Touba mouride', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Bounkiling';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.7081, -15.5569, 8, 'Sare alkaly', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Bounkiling';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.9139, -16.2599, 16, 'Mpal', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Saint-Louis';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 16.0246, -16.4895, 127, 'St louis', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Saint-Louis';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.9024, -12.4594, 127, 'Bakel', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Bakel';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.1833, -14.4667, 64, 'Kouthiaba', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Koumpentoum';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.8167, -14.4500, 1, 'Mereto', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Koumpentoum';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 13.7539, -13.7586, 127, 'Tambacounda', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Tambacounda';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.5667, -16.7833, 8, 'Mbafaye', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Thiès';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.8167, -16.6667, 32, 'Touba toul', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Thiès';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.8342, -17.1061, 127, 'Thiès', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Thiès';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 15.0301, -16.2502, 1, 'Thilmakha', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Tivaouane';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.8103, -16.2264, 127, 'Bignona', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Bignona';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12.5833, -16.2719, 127, 'St maur', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='rural-market' AND d.name='Ziguinchor';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.1970, -16.8728, 127, 'Joal', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='fishing-dock' AND d.name='Mbour';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.4087, -16.9727, 127, 'Mbour', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='fishing-dock' AND d.name='Mbour';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.7172, -17.4326, 127, 'Dakar-Hann', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='fishing-dock' AND d.name='Dakar';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 12, -12, 127, 'Joal khelcom tanneba', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='transformation-place' AND d.name='Mbour';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.3852, -16.9507, 127, 'Mballing', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='transformation-place' AND d.name='Mbour';

INSERT INTO market(id, latitude, longitude, market_days, name, department_id, type_id)
SELECT nextval('hibernate_sequence'), 14.7573, -17.3826, 127, 'Thiaroye', d.id, mt.id
FROM department d, category mt
WHERE mt.dtype='MarketType' AND mt.name='transformation-place' AND d.name='Pikine';
