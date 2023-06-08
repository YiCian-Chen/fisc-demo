INSERT INTO SYS_USER (USER_ID, PASSWORD, USER_NAME, BRANCH_ID, DEP_ID, EMAIL, ENABLED, ROLE_ID, TOKEN, LAST_LOGIN_IP, LAST_LOGIN_TIME) VALUES
('kenny', '$2a$10$8BVNZ8bgcNaHBbarQCiqxexfji6n1QKJkpJygEO3gfgLA/S/Uxr92', 'KennyLu', '', '', '', true, 'cc874d79-a3b3-4016-8cc3-327309473503', '', '', current_timestamp),
('steve', '$2a$10$8BVNZ8bgcNaHBbarQCiqxexfji6n1QKJkpJygEO3gfgLA/S/Uxr92', 'SteveWang', '', '', '', true, '288dcdbd-261c-44ee-a669-37d8a8e455b9', '', '', current_timestamp),
('Mary', '$2a$10$UJBLnDzsIkgN8lkJZUUlaeUMifCuwkpKO1l.k9kKNMOapOxEEI8lq', 'MaryLee', '', '', '', true, '288dcdbd-261c-44ee-a669-37d8a8e455b9', '', '', current_timestamp);
INSERT INTO SYS_ROLE (ROLE_ID, ROLE_NAME, ROLE_DESC, FUNCTIONS) VALUES
-- 櫃員 行庫櫃台人員(登打)
-- 主管 行庫主管(放行)
('cc874d79-a3b3-4016-8cc3-327309473503', 'Teller', 'Bank counter staff', '{"folder.system":[],"system.users":["q","m"],"system.roles":["q","m"],"folder.fisc":[],"fisc.banks":["q","m"]}'),
('288dcdbd-261c-44ee-a669-37d8a8e455b9', 'Supervisor', 'Bank supervisor', '{"folder.system":[],"system.users":["q","m"],"system.roles":["q","m"],"system.permissions":["q","m"]}');
INSERT INTO SYS_FUNC (FUNC_ID, FUNC_NAME, FUNC_URL, PARENT_ID, ORDER_NO, PERMISSION) VALUES
('folder.system', 'System', '#', 'root', 1, '[]'),
('system.users', 'User Setting', '/user', 'folder.system', 1, '["q", "m"]'),
('system.roles', 'Group Setting', '/role', 'folder.system', 2, '["q", "m"]'),
('system.permissions', 'Permission Setting', '/permission', 'folder.system', 3, '["q", "m"]'),
('folder.fisc', 'FISC', '#', 'root', 2, '[]'),
('fisc.banks', 'Bank Setting', '/bank', 'folder.fisc', 1, '["q", "m"]');
-- ('folder.system', '系統設定', '#', 'root', 1, '[]'),
-- ('system.users', '使用者設定', '/web/system/users', 'folder.system', 1, '["q", "m"]'),
-- ('system.roles', '群組設定', '/web/system/roles', 'folder.system', 2, '["q", "m"]'),
-- ('system.permissions', '權限設定', '/web/system/permission', 'folder.system', 3, '["q", "m"]'),
-- ('folder.fisc', '財金類別', '#', 'root', 2, '[]'),
-- ('fisc.notice', '對財金發送訊息', '/web/fisc/notice', 'folder.fisc', 1, '["q", "m"]'),
-- ('fisc.bank', '銀行行庫設定', '/web/fisc/banks', 'folder.fisc', 2, '["q", "m"]');
INSERT INTO FISC_BANK (BANKCODE,BANKNAME,TELZONE,TELNO,UPDATEDATE) VALUES
    ('007','First Bank','02','21811111','20200512'),
    ('108','Sunny Bank','02','77366689','20200625'),
    ('806','Yuanta Bank','02','21821988','20210107');
