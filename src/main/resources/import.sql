
-- ============================================
-- POPULAR TABELA USUARIOS (2 registros)
-- ============================================

-- A senha para o usuário 'admin' é 'admin' e para o usuário 'user' é 'user', ambas hasheadas com BCrypt.

INSERT INTO usuarios (usuario, senha, papel) VALUES
('admin', '$2a$12$K0elzLLr.i9Ygrwt.1a1zeEF9Sn/s9EtwiPWhmBmBn/xwdD7thpT2', 'Admin'),
('user', '$2a$12$r5kF9v0QW2Tynyxeh5gAfeUYjNiRGhWWqsg7SpOx7k08dN/8KVcVG', 'User');


-- ============================================
-- POPULAR TABELA PRODUTO (30 registros)
-- ============================================

INSERT INTO produto (nome, tipo, rentabilidade, risco) VALUES
('CDB Caixa Liquidez Diária',           'CDB',     0.10, 'Baixo'),
('CDB Caixa 2026',                      'CDB',     0.12, 'Baixo'),
('CDB Banco XP 2027',                   'CDB',     0.13, 'Baixo'),
('LCA Agrícola Prime',                  'LCA',     0.11, 'Baixo'),
('LCI Imobiliária Caixa',               'LCI',     0.10, 'Baixo'),
('Tesouro Selic 2029',                  'Tesouro', 0.09, 'Baixo'),
('Tesouro IPCA+ 2035',                  'Tesouro', 0.14, 'Médio'),
('Tesouro Prefixado 2028',              'Tesouro', 0.12, 'Médio'),
('Fundo Renda Fixa Caixa',              'Fundo',   0.11, 'Baixo'),
('Fundo Multimercado Alpha',            'Fundo',   0.16, 'Médio'),
('Fundo Ações Brasil',                  'Fundo',   0.22, 'Alto'),
('ETF BOVA11',                          'ETF',     0.18, 'Alto'),
('ETF SMALL11',                         'ETF',     0.20, 'Alto'),
('CRI Imobiliário Premium',             'CRI',     0.15, 'Médio'),
('CRA Agronegócio XP',                  'CRA',     0.17, 'Médio'),
('Debênture Incentivada Vale',          'Debênture',0.13,'Médio'),
('Debênture Energias Verdes',           'Debênture',0.14,'Médio'),
('FIIs - HGLG11',                       'FII',     0.12, 'Médio'),
('FIIs - KNRI11',                       'FII',     0.11, 'Médio'),
('Fundo Criptomoedas BTC',              'Fundo',   0.35, 'Alto'),
('Fundo Tech Innovation',               'Fundo',   0.25, 'Alto'),
('Fundo ESG Sustentável',               'Fundo',   0.14, 'Médio'),
('Fundo Cambial Dólar',                 'Fundo',   0.13, 'Médio'),
('Tesouro IPCA+ 2045',                  'Tesouro', 0.15, 'Médio'),
('CDB Médio Prazo Banco BMG',           'CDB',     0.14, 'Baixo'),
('LCA Agronegócio XPTO',                'LCA',     0.12, 'Baixo'),
('Fundo Private Agressivo',             'Fundo',   0.28, 'Alto'),
('ETF NASD11 - Nasdaq Index',           'ETF',     0.23, 'Alto'),
('ETF Ouro GOLD11',                     'ETF',     0.12, 'Médio'),
('CDB Digital Banco Inter',             'CDB',     0.13, 'Baixo');

-- ============================================
-- POPULAR TABELA INVESTIMENTO (30 registros)
-- ============================================

INSERT INTO investimento (cliente_id, tipo, valor, rentabilidade, data) VALUES
(1, 'CDB', 5000.00, 0.12, '2025-01-05'),
(3, 'Tesouro Direto', 3200.00, 0.10, '2025-02-10'),
(2, 'Fundo Multimercado', 4500.00, 0.15, '2025-03-12'),
(1, 'LCI', 6000.00, 0.11, '2025-01-18'),
(1, 'LCA', 7000.00, 0.10, '2025-04-08'),
(4, 'CDB', 3500.00, 0.13, '2025-02-22'),
(4, 'Fundo Renda Fixa', 2800.00, 0.08, '2025-03-01'),
(1, 'CDB', 9000.00, 0.12, '2025-01-29'),
(5, 'Tesouro IPCA+', 7500.00, 0.09, '2025-02-14'),
(1, 'LCI', 4200.00, 0.11, '2025-04-03'),

(2, 'Fundo Ações', 5000.00, 0.18, '2025-03-16'),
(3, 'Tesouro Selic', 3000.00, 0.09, '2025-01-20'),
(1, 'CDB', 6500.00, 0.12, '2025-02-07'),
(2, 'Fundo Multimercado', 4800.00, 0.14, '2025-03-25'),
(1, 'LCA', 5500.00, 0.10, '2025-04-11'),
(1, 'CDB', 4500.00, 0.13, '2025-05-02'),
(4, 'Fundo Renda Fixa', 3300.00, 0.07, '2025-01-28'),
(5, 'CDB', 8800.00, 0.12, '2025-03-04'),
(5, 'Tesouro Direto', 7200.00, 0.10, '2025-02-18'),
(5, 'LCI', 5100.00, 0.11, '2025-05-09'),

(2, 'Fundo Ações', 7000.00, 0.19, '2025-03-30'),
(4, 'Tesouro Selic', 2600.00, 0.09, '2025-04-19'),
(1, 'CDB', 8200.00, 0.13, '2025-02-02'),
(2, 'Fundo Multimercado', 5400.00, 0.15, '2025-03-06'),
(1, 'LCA', 6000.00, 0.10, '2025-01-12'),
(5, 'CDB', 9000.00, 0.12, '2025-03-27'),
(5, 'Tesouro IPCA+', 7800.00, 0.09, '2025-02-25'),
(4, 'LCI', 6200.00, 0.11, '2025-04-06'),
(4, 'CDB', 8300.00, 0.12, '2025-01-17'),
(2, 'Fundo Ações', 9100.00, 0.20, '2025-03-21');
