create table tblmodule(
 	pkid smallint not null,
 	txtname varchar(100) not null,
 	primary key (pkid),
 	unique (txtname),
 	constraint positive_pkid check(pkid > 0)
);

create table tblrights(
 	pkid smallint not null,
 	txtname varchar(100) not null,
 	primary key (pkid),
 	unique (txtname),
 	constraint positive_pkid check(pkid > 0)
);
insert into tblrights values(1,'Add');
insert into tblrights values(2,'Update');
insert into tblrights values(3,'View');
insert into tblrights values(4,'Delete');
insert into tblrights values(5,'Activation');
insert into tblrights values(6,'List');
insert into tblrights values(7,'Download');
insert into tblrights values(8,'File Upload');
insert into tblrights values(9,'Import');

create table tblfile (
 	pkid bigserial not null,
 	txtfileid VARCHAR(64) not null,
 	txtname VARCHAR(200) not null,
 	fkmoduleid smallint not null,
 	dateupload bigint not null,
 	ispublic boolean not null default false,
	txtoriginalname varchar(200) default null,
	txtcompressname varchar(200) default null,
	txtpath varchar(5000) not null,
	txtcompresspath varchar(5000) default null,
	fkmoduleobjectid bigint default null,
 	primary key(pkid),
 	unique (txtfileid),
 	constraint positive_fkmoduleid check (fkmoduleid > 0),
 	constraint positive_pkid check(pkid > 0),
 	constraint fk_fkmoduleid foreign key(fkmoduleid) references tblmodule(pkid)
);
create index index_tblfile_dateupload on tblfile(dateupload);
create index index_tblfile_ispublic on tblfile(ispublic);
create index index_tblfile_fkmoduleid on tblfile(fkmoduleid);
create index index_tblfile_fkmoduleobjectid on tblfile(fkmoduleobjectid);

create table tbluser(
	pkid bigserial not null,
  	lockversion bigint not null,
  	txtname varchar(100) not null,
  	txtemail varchar(100) default null,
  	txtcountrycode varchar(4) default null,
  	txtmobile varchar(15) default null,
  	txtverificationtoken varchar(64) not null,
  	isverificationtokenused boolean not null default false,
  	txtresetpasswordtoken varchar(64) default null,
  	isresetpasswordtokenused boolean not null default false,
  	dateresetpassword bigint default null,
  	txttwofactortoken varchar(16) default null,
  	istwofactortokenused boolean not null default false,
  	datetwofactortoken bigint default null, 
  	hasloggedin boolean not null default false,
  	txtverificationotp varchar(6) not null,
  	isverificationotpused boolean not null default false,
	isclientadmin boolean not null default false,
	fkprofilepicid bigint default null,
	txtaddress varchar(1000) default null,
	txtlandmark varchar(1000) default null,
	txtpincode varchar(6) default null,
	fkcountryid bigint default null,
  	fkstateid bigint default null,
  	txtstate varchar(100) default null,
  	fkcityid bigint default null,
  	txtcity varchar(100) default null,
	istemppassword boolean not null default false,
  	fkcreateby bigint default null,
  	datecreate bigint not null,
  	fkupdateby bigint default null,
  	dateupdate bigint default null,
  	isactive boolean not null default false,
  	fkactchangeby bigint default null,
  	dateactchange bigint default null,
  	isarchive boolean not null default false,
  	fkarchiveby bigint default null,
  	datearchive bigint default null,
  	primary key(pkid),
  	unique(txtemail),
  	unique(txtmobile),
  	unique(txtemail, txtmobile),
  	unique(txtverificationtoken),
  	unique(txtresetpasswordtoken),
  	constraint positive_pkid check(pkid > 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_fkcreateby check (fkcreateby > 0),
  	constraint positive_fkupdateby check (fkupdateby > 0),
  	constraint positive_fkactchangeby check (fkactchangeby > 0),
  	constraint positive_fkarchiveby check (fkarchiveby > 0),
  	constraint positive_fkcountryid check (fkcountryid > 0),
  	constraint positive_fkstateid check (fkstateid > 0),
  	constraint positive_fkcityid check (fkcityid > 0),
  	constraint positive_fkprofilepicid check (fkprofilepicid > 0),
  	constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
  	constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid),
  	constraint foreign_fkactchangeby foreign key(fkactchangeby) references tbluser(pkid),
  	constraint foreign_fkarchiveby foreign key(fkarchiveby) references tbluser(pkid),
  	constraint foreign_fkcountryid foreign key(fkcountryid) references tblcountry(pkid),
  	constraint foreign_fkstateid foreign key(fkstateid) references tblstate(pkid),
  	constraint foreign_fkcityid foreign key(fkcityid) references tblcity(pkid),
  	constraint foreign_fkprofilepicid foreign key(fkprofilepicid) references tblfile(pkid)
);

create index index_tbluser_fkcreateby on tbluser(fkcreateby);
create index index_tbluser_fkupdateby on tbluser(fkupdateby);
create index index_tbluser_fkactchangeby on tbluser(fkactchangeby);
create index index_tbluser_fkarchiveby on tbluser(fkarchiveby);
create index index_tbluser_isarchive on tbluser(isarchive);
create index index_tbluser_isactive on tbluser(isactive);
create index index_tbluser_isverificationotpused on tbluser(isverificationotpused);
create index index_tbluser_hasloggedin on tbluser(hasloggedin);
create index index_tbluser_fkcountryid on tbluser(fkcountryid);
create index index_tbluser_fkstateid on tbluser(fkstateid);
create index index_tbluser_fkcityid on tbluser(fkcityid);
create index index_tbluser_isclientadmin on tbluser(isclientadmin);
create index index_tbluser_fkprofilepicid on tbluser(fkprofilepicid);

insert into tbluser (
pkid, lockversion, txtname, txtemail, txtcountrycode, txtmobile, txtverificationtoken, isverificationtokenused, 
hasloggedin, txtverificationotp, isverificationotpused, fkcreateby, datecreate, 
isactive, isarchive) VALUES (1, 1, 'Master Admin', 'admin@endlos.in', '91','7149253237', 'verificationtoken',
true, false,'123456',true, 1, (select extract(epoch from now())::bigint), true, false);

alter SEQUENCE tbluser_pkid_seq restart with 2;

create table tblusersearch(
	fkuserid bigserial not null,
  	txtsearchparam text default null,
  	primary key(fkuserid),
  	constraint positive_pkid check(fkuserid > 0),
  	constraint foreign_fkuserid foreign key(fkuserid) references tbluser(pkid)
);
create index index_tblusersearch_txtsearchparam on tblusersearch(txtsearchparam);

insert into tblusersearch values (1, (select to_tsvector('simple',coalesce(tu.txtname,'')  
|| ' ' || coalesce(tu.txtemail,'') || ' ' || coalesce(tu.txtmobile,''))
from tbluser tu where tu.pkid = 1));

create table tblrole(
 	pkid bigserial not null,
  	lockversion bigint not null,
  	txtname varchar(30) not null,
  	txtdescription varchar(256) default null,
	enumtype smallint default null,
  	fkcreateby bigint default null,
  	datecreate bigint not null,
  	fkupdateby bigint default null,
  	dateupdate bigint default null,
  	isactive boolean not null default true,
  	fkactchangeby bigint default null,
  	dateactchange bigint default null,
  	isarchive boolean not null default false,
  	fkarchiveby bigint default null,
  	datearchive bigint default null,
	primary key(pkid),
  	constraint positive_pkid check(pkid > 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_fkcreateby check (fkcreateby > 0),
  	constraint positive_fkupdateby check (fkupdateby > 0),
  	constraint positive_fkactchangeby check (fkactchangeby > 0),
  	constraint positive_fkarchiveby check (fkarchiveby > 0),
  	constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
  	constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid),
  	constraint foreign_fkactchangeby foreign key(fkactchangeby) references tbluser(pkid),
  	constraint foreign_fkarchiveby foreign key(fkarchiveby) references tbluser(pkid)
);

create index index_tblrole_fkcreateby on tblrole(fkcreateby);
create index index_tblrole_fkupdateby on tblrole(fkupdateby);
create index index_tblrole_fkactchangeby on tblrole(fkactchangeby);
create index index_tblrole_fkarchiveby on tblrole(fkarchiveby);
create index index_tblrole_isarchive on tblrole(isarchive);
create index index_tblrole_isactive on tblrole(isactive);
create index index_tblrole_enumtype on tblrole(enumtype);

insert into tblrole (pkid, lockversion, txtname, enumtype, fkcreateby, datecreate, isactive, isarchive) values (
1, 0, 'Master Admin', 1, 1, (select extract(epoch from now())::bigint), true, false);
insert into tblrole (pkid, lockversion, txtname, enumtype, fkcreateby, datecreate, isactive, isarchive) values (
2, 0, 'Client Admin', 2, 1, (select extract(epoch from now())::bigint), true, false);

alter SEQUENCE tblrole_pkid_seq restart with 4;

create table tblrolemoduleright(
	fkroleid bigint not null,
	fkmoduleid bigint not null,
	fkrightsid bigint not null,
	primary key(fkroleid, fkmoduleid, fkrightsid),
	constraint positive_fkroleid check (fkroleid > 0),
	constraint positive_fkmoduleid check (fkmoduleid > 0),
	constraint positive_fkrightsid check (fkrightsid > 0),
  	constraint foreign_fkroleid foreign key(fkroleid) references tblrole(pkid),
  	constraint foreign_fkmoduleid foreign key(fkmoduleid) references tblmodule(pkid),
  	constraint foreign_fkrightsid foreign key(fkrightsid) references tblrights(pkid)
);

create table tbluserrole(
	fkuserid bigint not null,
	fkroleid bigint not null,
  	primary key(fkuserid, fkroleid),
  	constraint positive_fkuserid check (fkuserid > 0),
  	constraint positive_fkroleid check (fkroleid > 0),
  	constraint foreign_fkuserid foreign key(fkuserid) references tbluser(pkid),
  	constraint foreign_fkroleid foreign key(fkroleid) references tblrole(pkid)
);

insert into tbluserrole values(1,1);

create table tbluserpassword(
	pkid bigserial not null,
	fkuserid bigint not null,
	txtpassword varchar(1000) not null,
	datecreate bigint not null,
	primary key(pkid),
	constraint positive_pkid check (pkid > 0),
	constraint positive_fkuserid check (fkuserid > 0),
  	constraint foreign_fkuserid foreign key(fkuserid) references tbluser(pkid)
);
create index index_tbluserpassword_fkuserid on tbluserpassword(fkuserid);
create index index_tbluserpassword_datecreate on tbluserpassword(datecreate);
insert into tbluserpassword(fkuserid, txtpassword, datecreate) 
values(1,'$2a$11$PfPlHJ5HEUTPXqMqKyCbWObcXGs8G6QHKBLbbegoIzEUiQx5fyQQa',(select extract(epoch from now())::bigint));

create table tblsystemsetting(
	txtkey varchar(100) not null,
	txtvalue varchar(100) not null,
	enumdatatype int not null,
	primary key(txtkey),
	constraint positive_enumdatatype check(enumdatatype > 0)
);

insert into tblsystemsetting(txtkey, txtvalue, enumdatatype) values
('DEFAULT_FILE_PATH','/endlos-api', 2),
('TWO_FACTOR_AUTHENTICATION_ENABLED','0', 8),
('DEVICE_COOKIE_TIME_IN_SECONDS','31536000', 1),
('SESSION_INACTIVE_TIME_IN_MINUTES','360', 1),
('MAX_ALLOWED_DEVICE','10',1),
('DEFAULT_TIME_ZONE_ID','282',1),
('RESET_PASSWORD_TOKEN_VALID_MINUTES','1440', 1),
('PASSWORD_USED_VALIDATION_ENABLED','0', 8),
('MAX_PASSWORD_STORE_COUNT_PER_USER','5', 1),
('RESET_PASSWORD_SESSION_VALID_MINUTES','30', 1),
('CAPTCHA_IMAGE_PATH','/endlos-api/captcha', 2),
('VERIFICATION_THROUGH_OTP','1', 8),
('DEFAULT_PASSWORD_CHANGE_REQUIRED','0', 1),
('PASSWORD_EXPIRATION_MAX_AGE_NEEDED','0', 1),
('PASSWORD_EXPIRATION_MAX_AGE_DAYS','365', 1),
('PASSWORD_GENERATION_MIN_LENGTH','8', 1),
('PASSWORD_GENERATION_MAX_LENGTH','16', 1),
('PASSWORD_GENERATION_MIN_LOWER_CASE_ALPHABETS','0', 1),
('PASSWORD_GENERATION_MIN_UPPER_CASE_ALPHABETS','0', 1),
('PASSWORD_GENERATION_MIN_SPECIAL_CHARACTERS','0', 1),
('PASSWORD_GENERATION_MIN_NUMERICS','0', 1),
('URL','https://alumni.ccamp.res.in', 2);

create table tblemailaccount (
  pkid bigserial not null,
  lockversion bigint not null,
  txtname varchar(100) not null,
  txthost varchar(500) not null,
  intport bigint null default 25,
  txtusername varchar(100) not null,
  txtpassword varchar(500)  not null,
  txtreplytoemail varchar(100)  not null,
  txtemailfrom varchar(500)  not null,
  intrateperhour bigint  default null,
  intupdaterateperhour bigint default null,
  intrateperday bigint  default null,
  intupdaterateperday bigint  default null,
  enumauthmethod smallint not null default 0/*0=plain, 1=demo, 2=cram md5*/,
  enumauthsecurity smallint not null default 0/*0=none, 1=use ssl, 2=use tls*/,
  inttimeout bigint not null default 60000,
  fkcreateby bigint not null,
  datecreate bigint not null,
  fkupdateby bigint default null,
  dateupdate bigint default null,
  isactive boolean not null default true,
  fkactchangeby bigint default null,
  dateactchange bigint default null,
  isarchive boolean not null default false,
  fkarchiveby bigint default null,
  datearchive bigint default null,
  primary key (pkid),
  constraint positive_pkid check(pkid > 0),
  constraint positive_lockversion check(lockversion >= 0),
  constraint positive_fkcreateby check (fkcreateby > 0),
  constraint positive_fkupdateby check (fkupdateby > 0),
  constraint positive_fkactchangeby check (fkactchangeby > 0),
  constraint positive_fkarchiveby check (fkarchiveby > 0),
  constraint positive_enumauthmethod check (enumauthmethod >= 0),
  constraint positive_enumauthsecurity check (enumauthsecurity >= 0),
  constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
  constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid),
  constraint foreign_fkactchangeby foreign key(fkactchangeby) references tbluser(pkid),
  constraint foreign_fkarchiveby foreign key(fkarchiveby) references tbluser(pkid)
);

create index index_tblemailaccount_fkcreateby on tblemailaccount(fkcreateby);
create index index_tblemailaccount_fkupdateby on tblemailaccount(fkupdateby);
create index index_tblemailaccount_fkactchangeby on tblemailaccount(fkactchangeby);
create index index_tblemailaccount_fkarchiveby on tblemailaccount(fkarchiveby);
create index index_tblemailaccount_isarchive on tblemailaccount(isarchive);
create index index_tblemailaccount_isactive on tblemailaccount(isactive);

create table tblnotification(
	pkid smallint not null,
	txtname varchar(100) not null,
	isemail boolean not null default false,
	ispush boolean not null default false,
	primary key(pkid),
	unique(txtname),
	constraint positive_pkid check(pkid > 0)
);
create index index_tblnotification_isemail on tblnotification(isemail);	
create index index_tblnotification_ispush on tblnotification(ispush);	

create table tblemailcontent (
  pkid bigserial not null,
  lockversion bigint  not null,
  fkemailaccountid bigint not null,
  txtsubject varchar(1000) not null,
  txtcontent text not null,
  txtemailcc text default null,
  txtemailBcc text default null,
  fknotificationid smallint not null,
  fkcreateby bigint default null,
  datecreate bigint not null,
  fkupdateby bigint default null,
  dateupdate bigint default null,
  primary key (pkid),
  unique(fknotificationid),
  constraint positive_pkid check(pkid > 0),
  constraint positive_lockversion check(lockversion >= 0),
  constraint positive_fkcreateby check (fkcreateby > 0),
  constraint positive_fkupdateby check (fkupdateby > 0),
  constraint positive_fkemailaccountid check(fkemailaccountid > 0),
  constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
  constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid),
  constraint foreign_fkemailaccountid foreign key(fkemailaccountid) references tblemailaccount(pkid),
  constraint foreign_fknotificationid foreign key(fknotificationid) references tblnotification(pkid)
);

create index index_tblemailcontent_fkcreateby on tblemailcontent(fkcreateby);
create index index_tblemailcontent_fkupdateby on tblemailcontent(fkupdateby);
create index index_tblemailcontent_fkemailaccountid on tblemailcontent(fkemailaccountid);
create index index_tblemailcontent_fknotificationid on tblemailcontent(fknotificationid);

create table tbltransactionemail (
  	pkid bigserial not null,
  	lockversion bigint not null,
  	fkemailaccountid bigint  not null,
  	txtemailto text not null,
  	txtemailcc text default null,
  	txtemailbcc text default null,
  	txtsubject varchar(1000) not null,
  	txtbody text not null,
  	enumstatus smallint not null default 0/*0=new, 1=inprocess, 2=failed, 3=sent*/,
  	numberretrycount bigint not null default 0,
  	txtattachmentpath text default null,
  	txterror text default null,
  	datesend bigint default null,
  	datesent bigint default null,
  	primary key (pkid),
  	constraint positive_pkid check(pkid > 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_enumstatus check (enumstatus >= 0),
  	constraint positive_fkemailaccountid check(fkemailaccountid > 0),
  	constraint foreign_fkemailaccountid foreign key(fkemailaccountid) references tblemailaccount(pkid)
);

create index index_tbltransactionemail_fkemailaccountid on tbltransactionemail(fkemailaccountid);
create index index_tbltransactionemail_enumstatus on tbltransactionemail(enumstatus);
create index index_tbltransactionemail_numberretrycount on tbltransactionemail(numberretrycount);
create index index_tbltransactionemail_datesend on tbltransactionemail(datesend);

create table tblresponsemessage(
	pkid bigserial not null,
	enumcode smallint not null,
	enumlanguage smallint not null,
	txtmessage varchar(1000) null,  
	primary key(pkid),
	unique(enumcode,enumlanguage),
	constraint positive_pkid check(pkid > 0),
	constraint positive_enumcode check(enumcode > 0),
	constraint positive_enumlanguage check(enumlanguage > 0)
);
create index index_tblresponsemessage_enumcode on tblresponsemessage(enumcode);
create index index_tblresponsemessage_enumlanguage on tblresponsemessage(enumlanguage);

insert into tblsystemsetting(txtkey, txtvalue, enumdatatype) values
('LOCALE_SUPPORTED','en-US', 2);

alter table tblsystemsetting ALTER COLUMN txtvalue TYPE varchar(500);

insert into tblsystemsetting(txtkey, txtvalue, enumdatatype) values
('SECRET_KEY_FOR_GENERATE_JWT_TOKEN','c8b28e2fcf5f4f8a88f808d8cbfffeebc8b28e2fcf5f4f8a88f808d8cbfffeebc8b28e2fcf5f4f8a88f808d8cbfffeeb', 2),
('ACCESS_JWT_TOKEN_EXPIRY_TIME_IN_DAY','1', 1),
('REFRESH_JWT_TOKEN_EXPIRY_TIME_IN_DAY','5', 1),
('REGISTRATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES','60', 1),
('RESET_PASSWORD_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES','30', 1),
('ACTIVATION_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES','15', 1),
('CAPTCHA_JWT_TOKEN_EXPIRY_TIME_IN_MINUTES','5', 1),
('GENERATE_REFRESH_TOKEN_TIME_IN_MINUTES','10', 1),
('PASSWORD_GENERATION_SYNTAX_CHECKING_ENABLED','0', 1),
('UNLOCK_ACCOUNT_TIME_IN_HOURS','1', 1),
('TWO_FACTOR_TOKEN_EXPIRY_TIME_IN_MINUTES','10', 1),
('FIRST_LOGIN_TOKEN_EXPIRY_TIME_IN_MINUTES','5', 1);

create table tbltokenblacklist(
	pkid bigserial not null,
	fkuserid bigint not null,
	txtjwttoken varchar(500) not null,
	primary key(pkid),
	constraint positive_pkid check (pkid > 0),
	constraint positive_fkuserid check (fkuserid > 0),
  	constraint foreign_fkuserid foreign key(fkuserid) references tbluser(pkid)
);
create index index_tbltokenblacklist_fkuserid on tbltokenblacklist(fkuserid);
create index index_tbltokenblacklist_txtjwttoken on tbltokenblacklist(txtjwttoken);

alter table tbluser add column txtuniquetoken varchar(100) default null;
create index index_tbluser_txtuniquetoken on tbluser(txtuniquetoken);
update tbluser set txtuniquetoken = 'NTfwZvev' where pkid = 1;

create table tblusersession(
	pkid bigserial not null,
	fkuserid bigint not null,
	datelastlogin bigint not null,
	txtbrowser varchar(100) default null,
	txtoperatingsystem varchar(500) default null,
	txtipaddress varchar(50) default null,
	txtdevicecookie varchar(100) not null,
	datedevicecookie bigint not null,
	primary key(pkid),
	constraint positive_pkid check (pkid > 0),
	constraint positive_fkuserid check (fkuserid > 0),
	constraint positive_datelastlogin check (datelastlogin > 0),
	constraint positive_datedevicecookie check (datedevicecookie > 0),
  	constraint foreign_fkuserid foreign key(fkuserid) references tbluser(pkid)
);

create index index_tblusersession_fkuserid on tblusersession(fkuserid);
create index index_tblusersession_datelastlogin on tblusersession(datelastlogin);
create index index_tblusersession_datedevicecookie on tblusersession(datedevicecookie);
create index index_tblusersession_txtbrowser on tblusersession(txtbrowser);
create index index_tblusersession_txtoperatingsystem on tblusersession(txtoperatingsystem);