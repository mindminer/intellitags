/** 表结构 ********************************************************
  拥有两个字段，第一个字段记录标签
  第二个字段记录与之关联的标签
*/
-- tags ---------------------------------------------------------
DROP TABLE  tags;
CREATE TABLE tags(
    name varchar not null unique primary key,
    relatives hstore
);

DROP TABLE  files;
CREATE TABLE files(
    name varchar not null unique primary key,
    tags hstore
);


/** 函数 ********************************************************
  1) 随机生成 N 条标签
  2) 遍历每条标签，并随机为它匹配 n 条标签做为关联标签，存放在 retatives 字段中
*/
-- 生成随机字符串做为 name --
create or replace function random_string(integer) returns text as
$BODY$
        select array_to_string(array(select substring('0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' FROM (ceil(random()*62))::int FOR 1) FROM generate_series(1, $1)), '');
$BODY$ language sql volatile;

-- 生成 quantity 条随机 tag --
CREATE OR REPLACE FUNCTION generate_tags(quantity integer) RETURNS integer AS $BODY$
    DECLARE
        ret integer;
        sql_insert varchar := 'INSERT INTO tags(name) VALUES(random_string(8))';
    BEGIN
        ret := generate_record(sql_insert, quantity);
        return ret;
    END;
$BODY$ LANGUAGE plpgsql VOLATILE;

-- 生成 quantity 条随机 文件 --
CREATE OR REPLACE FUNCTION generate_files(quantity integer) RETURNS integer AS $BODY$
    DECLARE
        ret integer;
        sql_insert varchar := 'INSERT INTO files(name) VALUES(''img://''||random_string(12))';
    BEGIN
        ret := generate_record(sql_insert, quantity);
        return ret;
    END;
$BODY$ LANGUAGE plpgsql VOLATILE;

-- 插入 quantity 条随机记录 --
CREATE OR REPLACE FUNCTION generate_record(sql_insert varchar, quantity integer) RETURNS integer AS $BODY$
    DECLARE
    BEGIN
        RAISE NOTICE 'Quantity here is %', quantity;  -- Prints 30

        FOR count IN 1..quantity LOOP
            -- INSERT INTO tags(name) VALUES(random_string(8));
            EXECUTE sql_insert;
        END LOOP;

        RAISE NOTICE 'Generation is done!';  -- Finish!
        RETURN quantity;
    END;
$BODY$ LANGUAGE plpgsql VOLATILE;

-- 更新记录 --
create or replace function update_keywords(num integer) returns void as $BODY$
    DECLARE
        r RECORD;
        sql_select_name text := 'SELECT name FROM files';
        sql_update_relatives text := 'UPDATE files SET tags = hstore($1) WHERE name = $2';
        relatives varchar;

        lines integer = 0;
    BEGIN
        FOR r IN EXECUTE sql_select_name LOOP
            relatives := generate_relatives(r.name, num);
            EXECUTE sql_update_relatives USING relatives, r.name;
            -- lines = lines +1;
            -- raise notice '%', lines;
        END LOOP;

        RAISE NOTICE 'Update is done!';  -- Finish!
    END;
$BODY$ language plpgsql volatile;

-- 生成随机关系 --
create or replace function generate_relatives(name varchar, num integer) returns varchar as $BODY$
    declare
        r RECORD;
        sql_select_relatives text := 'select * from tags order by random() limit $1';    -- 随机捡取 num 条!
        relatives varchar = '';
        appending varchar;
    begin
        FOR r IN EXECUTE sql_select_relatives USING num LOOP
            if r.name <> name then
                appending = r.name || '=>1';
                relatives = concat(relatives, appending);
            end if;
        END LOOP;
        return relatives;
    end;
$BODY$ language plpgsql volatile;

-- 合并两个字符串，如果第一个参数不为空，用“逗号”相连，否则返回第二个参数 --
CREATE OR REPLACE FUNCTION concat(text, text) RETURNS text AS $BODY$
    DECLARE
        t text;
    BEGIN
        IF character_length($1) > 0 THEN
            t = $1 || ',' || $2;
        ELSE
            t = $2;
        END IF;
    RETURN t;
    END;
$BODY$ LANGUAGE 'plpgsql' VOLATILE;


-- 功能函数：随机输入n个标签，返回的是 table 类型，具有两个字段：n,r，这个临时表的字段可以被直接引用。 --
CREATE OR REPLACE FUNCTION get_relatives(condition_num integer) RETURNS TABLE(
    n varchar,
    r hstore
) AS $BODY$
    DECLARE
        conditions varchar[];
    BEGIN
        conditions := (select array_agg(x.name) from (select name from tags order by random() limit condition_num) x);
        RAISE NOTICE '%', conditions;
        RETURN QUERY select * from files where tags ?& conditions;
    END;
$BODY$ LANGUAGE 'plpgsql' VOLATILE;

-- 功能函数：随机输入n个标签，调用 get_relatives 函数，然后 distinct 做去重操作，将结果集封装成 array 返回。 --
CREATE OR REPLACE FUNCTION get_dist_relatives(condition_num integer) RETURNS varchar[] as $BODY$
    DECLARE
        tags varchar[];
    BEGIN
        tags := (select array_agg(k)    -- distinct 做去重操作， array_agg 将结果集分装成 array。
            from (
              select distinct skeys(r) as k from get_relatives(condition_num)   -- 调用 get_relatives 函数
            ) as keys_table);
        return tags;
    END;
$BODY$ LANGUAGE 'plpgsql' VOLATILE;
