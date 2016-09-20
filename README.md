# PaycodeWS
Rest web service with authentication 

service methods :
1.    public User checklogin(String login,String passw);
2.    public Balance updateBalance(Balance balance);
3.    public User createUser(User user);
4.    public void createUserLog(UserLog userLog);

database procedures :

### create_User procedure
<code>
DELIMITER @@
DROP PROCEDURE create_User @@
CREATE PROCEDURE scanpay.create_User
(in p_name          VARCHAR(90),
                           in p_surname       VARCHAR(120),
                           in p_part          VARCHAR(120),
                           in p_mob           VARCHAR(90),
                           in p_email         VARCHAR(190),
                           in p_passw         VARCHAR(190),
                           OUT  p_user_id        INT)
begin 
INSERT INTO scanpay.USERS (NAME,
                            SURNAME,
                            PATRONYMIC,
                            MOBILE,
                            EMAIL,
                            NOTE,
                            CREATE_DATE,
                            STATUS)
              VALUES (p_name,
                      p_surname,
                      p_part,
                      p_mob,
                      p_email,
                      '',
                      now(),
                      'a');
              set p_user_id= LAST_INSERT_ID()  ;

         IF p_user_id IS NOT NULL
         THEN
            INSERT INTO scanpay.USER_PASSWORD (
                                       USER_ID,
                                       PASSWORD,
                                       CREATE_DATE,
                                       EXPIRE_DATE,
                                       NOTE,
                                       STATUS)
                 VALUES   (p_user_id,
                                p_passw,
                                now(),
                                now() + 300,
                                '',
                                'a');
         END IF;
end @@ 
DELIMITER ; 
</code>
### update_balance procedure
<code>
DELIMITER @@
DROP PROCEDURE update_balance @@
CREATE PROCEDURE scanpay.update_balance
( in  p_user_id        INT,
                           in  p_amount        INT,
                           out  p_balance        INT, 
                           OUT  p_status        INT)
begin 

select (BALANCE - p_amount) from  scanpay.USERS
              where  id=p_user_id into p_balance;

         IF (p_balance < 0)
         THEN
             set   p_status = 1;  
         ELSE  
update scanpay.USERS set BALANCE=p_balance    where id=p_user_id;
            set   p_status = 2;  
         END IF;
end @@ 
DELIMITER ; 
</code>

### user_logger procedure
<code>
DELIMITER @@
DROP PROCEDURE user_logger @@
CREATE PROCEDURE scanpay.user_logger
(in p_email          VARCHAR(200),
                           in p_device_mac       VARCHAR(120),
                           in p_amount          float,
                           in p_device_id           VARCHAR(90),
                           in p_note         VARCHAR(500))
begin 
 INSERT INTO scanpay.USERLOGS (  email, device_mac, amount, device_id, note, status) 
	VALUES (  p_email, p_device_mac, p_amount, p_device_id,p_note, 'a');
end @@ 
DELIMITER ; 

</code>
