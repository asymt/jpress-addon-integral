
DROP TABLE IF EXISTS integral_details;
CREATE TABLE integral_details(
                                 `id` INT(11) NOT NULL AUTO_INCREMENT  COMMENT '主键ID' ,
                                 `user_id` INT(11) NOT NULL   COMMENT '用户ID' ,
                                 `integral` INT(11) NOT NULL   COMMENT '积分值' ,
                                 `remark` VARCHAR(255)    COMMENT '备注' ,
                                 `created` DATETIME NOT NULL  DEFAULT now() COMMENT '创建时间' ,
                                 `expire` DATETIME    COMMENT '过期时间' ,
                                 `type` tinyINT(2)    COMMENT '积分类型(1：新增积分，2：消耗积分，3：过期积分)' ,
                                 PRIMARY KEY (id)
)  COMMENT = '积分明细表' ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE INDEX id_index ON integral_details(id);

DROP TABLE IF EXISTS integral_availables;
CREATE TABLE integral_availables(
                                    `id` INT(11) NOT NULL AUTO_INCREMENT  COMMENT '主键ID' ,
                                    `user_id` INT(11) NOT NULL   COMMENT '用户ID' ,
                                    `integral` INT(11) NOT NULL   COMMENT '积分值' ,
                                    `created` DATETIME NOT NULL  DEFAULT now() COMMENT '创建时间' ,
                                    `expire` DATETIME    COMMENT '过期时间' ,
                                    PRIMARY KEY (id)
)  COMMENT = '可用积分表' ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE INDEX id_index ON integral_availables(id);

DROP TABLE IF EXISTS user_integral;
CREATE TABLE user_integral(
                              `user_id` INT(11) NOT NULL   COMMENT '用户ID' ,
                              `integral` INT(11) NOT NULL  DEFAULT 0 COMMENT '积分总额',
                              primary key (user_id)
)  COMMENT = '积分总额表' ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS user_article_collect;
CREATE TABLE user_article_collect(
                                     `user_id` INT(11) NOT NULL   COMMENT '用户ID' ,
                                     `article_id` INT(11) NOT NULL   COMMENT '文章ID' ,
                                     PRIMARY KEY (user_id,article_id)
)  COMMENT = '文章收藏表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS user_resource_download;
CREATE TABLE user_resource_download(
                                       `user_id` INT(11) NOT NULL   COMMENT '用户ID' ,
                                       `article_id` INT(11) NOT NULL   COMMENT '文章ID' ,
                                       `count` INT(11) NOT NULL  DEFAULT 1 COMMENT '下载次数' ,
                                       PRIMARY KEY (user_id,article_id)
)  COMMENT = '资源下载记录表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS user_article_like;
CREATE TABLE user_article_like(
                                  `user_id` INT(11) NOT NULL   COMMENT '用户ID' ,
                                  `article_id` INT(11) NOT NULL   COMMENT '文章ID' ,
                                  PRIMARY KEY (user_id,article_id)
)  COMMENT = '文章赞赏表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS material_extend;
CREATE TABLE material_extend(
                                `article_id` INT(11) NOT NULL unique  COMMENT '文章ID' ,
                                `source` tinyINT(2) NOT NULL   COMMENT '素材来源,1：原创，2：转载' ,
                                `download_count` INT(11) NOT NULL  DEFAULT 0 COMMENT '下载量' ,
                                `url` VARCHAR(300)    COMMENT '素材下载地址'
)  COMMENT = '素材扩展'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS slide;
CREATE TABLE slide(
                      `id` INT(11) NOT NULL AUTO_INCREMENT  COMMENT '主键ID' ,
                      `order_no` INT(11) NOT NULL  DEFAULT 0 COMMENT '排序号' ,
                      `img` VARCHAR(500) NOT NULL   COMMENT '图片路径' ,
                      `target_url` VARCHAR(500) NOT NULL   COMMENT '跳转链接' ,
                      `created` DATETIME NOT NULL   COMMENT '创建日期' ,
                      PRIMARY KEY (id)
)  COMMENT = '幻灯片'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

