/* 관리자 계정 등록 */
INSERT INTO ACCOUNT (ID, CREATED_BY_ID, CREATED_DATE_TIME, UPDATED_BY_ID, UPDATED_DATE_TIME, ADDRESS, ADDRESS_DETAIL,
                     BIRTHDAY, GENDER, IS_DELETE, NAME, PHONE, ROAD_ADDRESS, ROAD_ADDRESS_DETAIL, ROLE, STATE, USER_ID,
                     USER_PASSWORD, ZIP)
VALUES (1,NULL ,CURRENT_TIMESTAMP,NULL,CURRENT_TIMESTAMP,NULL,NULL,NULL,'M',false,'관리자','010-1234-5678',NULL,NULL,'ADMIN','T','admin','$2a$10$u.A8yzxMfhitV0xyAoSuoOcqjEVwA1j2YTQZqjkzNoje.Ilc7Ey6q',NULL);

/* 기본 메뉴 등록 */
INSERT INTO MENU (ID, CREATED_BY_ID, CREATED_DATE_TIME, UPDATED_BY_ID, UPDATED_DATE_TIME, IS_DELETED, IS_USED, MENU_LINK,
                  MENU_NAME, MENU_TYPE, UPPER_MENU_ID, IS_ROOT, ORDER_NUM)
VALUES (1,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/main','Root','LINK',NULL,true,1)
     ,(2,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/main','시스템관리','LINK',1,false,2)
     ,(3,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/main','대시보드','LINK',2,false,3)
     ,(4,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'','사이트관리','LINK',1,false,7)
     ,(5,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'','통계 관리','LINK',1,false,9)
     ,(6,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/menu/list','메뉴 관리','LINK',2,false,4)
     ,(7,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/member/admin-list','관리자 관리','LINK',2,false,5)
     ,(8,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/member/user-list','사용자 관리','LINK',2,false,6)
     ,(9,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'','게시판 관리','LINK',4,false,8)
     ,(10,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'','접속 통계 관리','LINK',5,false,10)
     ,(11,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/code/code-group/list','공통코드관리','LINK',2,false,11)
     ,(12,1,CURRENT_TIMESTAMP,1,CURRENT_TIMESTAMP,false,true,'/cms/contents-manage/list','콘텐츠 관리','LINK',4,false,12);

/* 시퀀스 업데이트 */
ALTER SEQUENCE account_id_seq restart with 2;
ALTER SEQUENCE menu_id_seq restart with 13;

