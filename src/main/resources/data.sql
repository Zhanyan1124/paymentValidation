INSERT INTO account (BALANCE, CURRENCY) VALUES (10.50, 'USD');
INSERT INTO account (BALANCE, CURRENCY) VALUES (250.75, 'EUR');
INSERT INTO account (BALANCE, CURRENCY) VALUES (500.00, 'GBP');
INSERT INTO account (BALANCE, CURRENCY) VALUES (0.00, 'MYR');

INSERT INTO conversion_rate (from_cur, to_cur, rate) VALUES
 ('MYR', 'SGD', 0.31),
 ('MYR', 'USD', 0.22),
 ('MYR', 'GBP', 0.17),
 ('MYR', 'EUR', 0.20),

 ('SGD', 'MYR', 3.23),
 ('SGD', 'USD', 0.71),
 ('SGD', 'GBP', 0.55),
 ('SGD', 'EUR', 0.65),

 ('USD', 'MYR', 4.55),
 ('USD', 'SGD', 1.41),
 ('USD', 'GBP', 0.78),
 ('USD', 'EUR', 0.92),

 ('GBP', 'MYR', 5.85),
 ('GBP', 'SGD', 1.83),
 ('GBP', 'USD', 1.28),
 ('GBP', 'EUR', 1.18),

 ('EUR', 'MYR', 5.03),
 ('EUR', 'SGD', 1.54),
 ('EUR', 'USD', 1.09),
 ('EUR', 'GBP', 0.85);