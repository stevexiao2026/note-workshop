USE note_workshop;
-- 1. 创建用户表 user
CREATE TABLE `user` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键',
                        `username` VARCHAR(50) NOT NULL COMMENT '用户名，唯一',
                        `password` VARCHAR(100) NOT NULL COMMENT '密码（加密后存储）',
                        `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
                        `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
                        `role` VARCHAR(20) DEFAULT 'user' COMMENT '角色：user普通用户，admin管理员',
                        `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 创建笔记表 note
CREATE TABLE `note` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '笔记ID，主键',
                        `user_id` BIGINT NOT NULL COMMENT '所属用户ID',
                        `title` VARCHAR(100) NOT NULL COMMENT '笔记标题',
                        `content` TEXT COMMENT '笔记内容',
                        `is_public` TINYINT DEFAULT 0 COMMENT '是否公开：0私有，1公开',
                        `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        KEY `idx_user_id` (`user_id`) -- 建立索引，以后按用户查笔记速度更快
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记表';

-- 3. 创建笔记图片表 note_image
CREATE TABLE `note_image` (
                              `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '图片ID，主键',
                              `note_id` BIGINT NOT NULL COMMENT '所属笔记ID',
                              `image_url` VARCHAR(500) NOT NULL COMMENT '图片URL地址',
                              `sort_order` INT NOT NULL DEFAULT 0 COMMENT '图片排序序号（同一笔记内，从0开始）',
                              `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`),
                              KEY `idx_note_id` (`note_id`) -- 按笔记ID查图片
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记图片表';

-- 4. 创建点赞表 note_like
CREATE TABLE `note_like` (
                             `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '点赞ID，主键',
                             `user_id` BIGINT NOT NULL COMMENT '点赞的用户ID',
                             `note_id` BIGINT NOT NULL COMMENT '被点赞的笔记ID',
                             `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_user_note` (`user_id`, `note_id`), -- 同一用户同一笔记只能点一次赞
                             KEY `idx_note_id` (`note_id`)                     -- 按笔记ID查点赞列表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

-- 5. 创建评论表 comment
CREATE TABLE `comment` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID，主键',
                           `note_id` BIGINT NOT NULL COMMENT '所属笔记ID',
                           `user_id` BIGINT NOT NULL COMMENT '评论者用户ID',
                           `parent_id` BIGINT DEFAULT NULL COMMENT '父评论ID，NULL表示顶级评论',
                           `reply_to_user_id` BIGINT DEFAULT NULL COMMENT '被回复的目标用户ID，NULL表示顶级评论',
                           `content` VARCHAR(1000) NOT NULL COMMENT '评论内容',
                           `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`),
                           KEY `idx_note_id` (`note_id`),   -- 按笔记查评论
                           KEY `idx_parent_id` (`parent_id`) -- 按父评论查回复列表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';