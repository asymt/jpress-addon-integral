
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

