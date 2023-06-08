INSERT INTO SYS_USER (USER_ID, PASSWORD, USER_NAME, BRANCH_ID, DEP_ID, EMAIL, ENABLED, ROLE_ID, TOKEN, LAST_LOGIN_IP, LAST_LOGIN_TIME) VALUES
('kenny', '$2a$10$8BVNZ8bgcNaHBbarQCiqxexfji6n1QKJkpJygEO3gfgLA/S/Uxr92', 'KennyLu', '', '', 'KennyLu@gmail.com', true, 'cc874d79-a3b3-4016-8cc3-327309473503', '', '', current_timestamp),
('steve', '$2a$10$8BVNZ8bgcNaHBbarQCiqxexfji6n1QKJkpJygEO3gfgLA/S/Uxr92', 'SteveWang', '', '', 'SteveWang@gmail.com', true, '288dcdbd-261c-44ee-a669-37d8a8e455b9', '', '', current_timestamp),
('Mary', '$2a$10$UJBLnDzsIkgN8lkJZUUlaeUMifCuwkpKO1l.k9kKNMOapOxEEI8lq', 'MaryLee', '', '', 'MaryLee@gmail.com', true, '288dcdbd-261c-44ee-a669-37d8a8e455b9', '', '', current_timestamp);
INSERT INTO SYS_ROLE (ROLE_ID, ROLE_NAME, ROLE_DESC, FUNCTIONS) VALUES
('cc874d79-a3b3-4016-8cc3-327309473503', 'Teller', 'Bank counter staff', '{"folder.system":[],"system.users":["q","m"],"system.roles":["q","m"],"folder.fisc":[],"fisc.banks":["q","m"]}'),
('288dcdbd-261c-44ee-a669-37d8a8e455b9', 'Supervisor', 'Bank supervisor', '{"folder.system":[],"system.users":["q","m"],"system.roles":["q","m"],"system.permissions":["q","m"]}');
INSERT INTO SYS_FUNC (FUNC_ID, FUNC_NAME, FUNC_URL, PARENT_ID, ORDER_NO, PERMISSION) VALUES
('folder.system', 'System', '#', 'root', 1, '[]'),
('system.users', 'User Setting', '/user', 'folder.system', 1, '["q", "m"]'),
('system.roles', 'Group Setting', '/role', 'folder.system', 2, '["q", "m"]'),
('system.permissions', 'Permission Setting', '/permission', 'folder.system', 3, '["q", "m"]'),
('folder.fisc', 'FISC', '#', 'root', 2, '[]'),
('fisc.banks', 'Bank Setting', '/bank', 'folder.fisc', 1, '["q", "m"]');
INSERT INTO FISC_BANK (BANK_id,BANK_name,BANK_address,BANK_tel,enabled) VALUES
('007','First Bank','No. 30, Section 1, Chongqing South Road, Zhongzheng District, Taipei City','02-21811111',true),
('108','Sunny Bank','No. 90, Section 1, Shipai Road, Beitou District, Taipei City','02-77366689',true),
('806','Yuanta Bank','No. 66, Section 1, Dunhua South Road, Songshan District, Taipei City','02-21821988',true);
