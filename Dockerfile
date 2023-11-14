docker run --platform linux/amd64 
-p 3306:3306 
--name [컨테이너 이름] 
-e MYSQL_ROOT_PASSWORD=[루트 유저 비밀번호] 
-e MYSQL_DATABASE=[데이터베이스 이름] 
-e MYSQL_PASSWORD=[비밀번호] 
-d mysql
