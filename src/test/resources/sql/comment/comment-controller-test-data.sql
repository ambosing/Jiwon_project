insert into `category` (`category_id`, `name`, `deleted_date`)
values (1, 'category1', null);
insert into `users`(user_no, user_id, name, password)
values (1, 'ambosing1', 'jiwon1', 'password1');
insert into `post` (`post_id`, `title`, `content`, `view`, `category_id`, `user_no`)
values (1, 'title1', 'content1', 0, 1, 1);
insert into `post` (`post_id`, `title`, `content`, `view`, `category_id`, `user_no`)
values (2, 'title2', 'content2', 0, 1, 1);
insert into `comment` (`comment_id`, `content`, `post_id`, `user_no`, `deleted_date`)
values (1, 'comment1', 1, 1, null);
insert into `comment` (`comment_id`, `content`, `post_id`, `user_no`, `deleted_date`)
values (2, 'comment2', 2, 1, null);

