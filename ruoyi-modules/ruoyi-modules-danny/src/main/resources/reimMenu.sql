-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报销申请', '1166', '1', 'reim', 'danny/reim/index', 1, 0, 'C', '0', '0', 'danny:reim:list', '#', 'admin', sysdate(), '', null, '报销申请菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报销申请查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'danny:reim:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报销申请新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'danny:reim:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报销申请修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'danny:reim:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报销申请删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'danny:reim:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报销申请导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'danny:reim:export',       '#', 'admin', sysdate(), '', null, '');