create table tblclient(
 	pkid bigserial not null,
  	lockversion bigint not null,
  	txtname varchar(500) not null,	
  	fklogoid bigint default null,
  	txtaddress varchar(500) not null,
  	txtarea varchar(500) default null,
  	fkstateid bigint not null,
  	fkcityid bigint not null,
	txtpincode varchar(6) not null,
  	fkcreateby bigint default null,
  	datecreate bigint not null,
  	fkupdateby bigint default null,
  	dateupdate bigint default null,
  	isactive boolean not null default true,
  	fkactchangeby bigint default null,
  	dateactchange bigint default null,
	primary key(pkid),
  	unique(txtname),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_fkcreateby check (fkcreateby >= 0),
  	constraint positive_fkupdateby check (fkupdateby >= 0),
  	constraint positive_fkactchangeby check (fkactchangeby >= 0),
  	constraint positive_fkstateid check (fkstateid >= 0),
  	constraint positive_fkcityid check (fkcityid >= 0),
  	constraint positive_fklogoid check (fklogoid > 0),
  	constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
  	constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid),
  	constraint foreign_fkactchangeby foreign key(fkactchangeby) references tbluser(pkid),
  	constraint foreign_fkstateid foreign key(fkstateid) references tblstate(pkid),
  	constraint foreign_fkcityid foreign key(fkcityid) references tblcity(pkid),
  	constraint foreign_fklogoid foreign key(fklogoid) references tblfile(pkid)
);
create index index_tblclient_fkcreateby on tblclient(fkcreateby);
create index index_tblclient_fkupdateby on tblclient(fkupdateby);
create index index_tblclient_fkactchangeby on tblclient(fkactchangeby);
create index index_tblclient_isactive on tblclient(isactive);
create index index_tblclient_fkstateid on tblclient(fkstateid);
create index index_tblclient_fkcityid on tblclient(fkcityid);
create index index_tblclient_fklogoid on tblclient(fklogoid);

create table tbllocation(
 	pkid bigserial not null,
  	lockversion bigint not null,
  	txtname varchar(500) not null,	
  	fkclientid bigint not null,
  	txtaddress varchar(500) not null,
  	txtarea varchar(500) default null,
  	fkstateid bigint not null,
  	fkcityid bigint not null,
	txtpincode varchar(6) not null,
	txtlatitude varchar(20) default null,
	txtlongitude varchar(20) default null,
	txtaltitude varchar(20) default null,
  	fkcreateby bigint default null,
  	datecreate bigint not null,
  	fkupdateby bigint default null,
  	dateupdate bigint default null,
  	isactive boolean not null default true,
  	fkactchangeby bigint default null,
  	dateactchange bigint default null,
	primary key(pkid),
  	unique(txtname),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_fkcreateby check (fkcreateby >= 0),
  	constraint positive_fkupdateby check (fkupdateby >= 0),
  	constraint positive_fkactchangeby check (fkactchangeby >= 0),
  	constraint positive_fkstateid check (fkstateid >= 0),
  	constraint positive_fkcityid check (fkcityid >= 0),
  	constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
  	constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid),
  	constraint foreign_fkactchangeby foreign key(fkactchangeby) references tbluser(pkid),
  	constraint foreign_fkstateid foreign key(fkstateid) references tblstate(pkid),
  	constraint foreign_fkcityid foreign key(fkcityid) references tblcity(pkid),
  	constraint foreign_fkclientid foreign key(fkclientid) references tblclient(pkid)
);
create index index_tbllocation_fkcreateby on tbllocation(fkcreateby);
create index index_tbllocation_fkupdateby on tbllocation(fkupdateby);
create index index_tbllocation_fkactchangeby on tbllocation(fkactchangeby);
create index index_tbllocation_isactive on tbllocation(isactive);
create index index_tbllocation_fkstateid on tbllocation(fkstateid);
create index index_tbllocation_fkcityid on tbllocation(fkcityid);
create index index_tbllocation_fkclientid on tbllocation(fkclientid);

create table tblclientuser(
	fkuserid bigint not null,
	fkclientid bigint not null,
  	primary key(fkuserid, fkclientid),
  	constraint positive_fkuserid check (fkuserid > 0),
  	constraint positive_fkclientid check (fkclientid > 0),
  	constraint foreign_fkuserid foreign key(fkuserid) references tbluser(pkid),
  	constraint foreign_fkclientid foreign key(fkclientid) references tblclient(pkid)
);
create index index_tblclientuser_fkuserid on tblclientuser(fkuserid);
create index index_tblclientuser_fkclientid on tblclientuser(fkclientid);

drop table tblresponsemessage;

create table tblresponsemessage(
	enumcode smallint not null,
	enumlanguage varchar(30) not null,
	txtmessage varchar(1000) null,  
	primary key(enumcode, enumlanguage),
	constraint positive_code check (enumcode > 0)
);

create table tblmachine(
	pkid bigserial not null,
	lockversion bigint not null,
  	txtmachineid varchar(10) not null, 	
  	txtmachinename varchar(100) not null,
  	fkcreateby bigint default null,
	datecreate bigint not null,
	fkupdateby bigint default null,
	dateupdate bigint default null,
	
  	primary key(pkid),
  	unique(txtmachineid),
  	constraint positive_pkid check(pkid > 0),
  	constraint positive_lockversion check(lockversion >= 0),
	constraint positive_fkcreateby check (fkcreateby>0),
	constraint positive_fkupdateby check (fkupdateby>0),
  	constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
	constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid)
);
create index index_tblmachine_txtmachineid on tblmachine(txtmachineid);
create index index_tblmachine_fkcreateby on tblmachine(fkcreateby);
create index index_tblmachine_fkupdateby on tblmachine(fkupdateby);

CREATE SEQUENCE uniqueMachineIdSequece START 1;

ALTER table tbllocation add column fkmachineid bigint not null,
add constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid);
create index index_tbllocation_fkmachineid on tbllocation(fkmachineid);

create table tbldatatable(
 	pkid bigserial not null,
  	lockversion bigint not null,
  	txtbarcode text not null,	
  	txtmaterial text default null,
  	txtdescription text default null,
  	txtvolumn decimal(10,2) default null,
  	txtitemweight decimal(10,2) default null,
  	txtitemredeemvalue decimal(10,2) default null,
  	txtdataacquisition text default null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_txtvolumn check (txtvolumn >= 0),
  	constraint positive_txtitemweight check (txtitemweight >= 0),
  	constraint positive_txtitemredeemvalue check (txtitemredeemvalue >= 0)
);

alter table tblmachine add column txtuniquetoken varchar(100) default null;
create index index_tblmachine_txtuniquetoken on tblmachine(txtuniquetoken);

alter table tblmachine add column txtjwttoken varchar(500) default null;
create index index_tblmachine_txtjwttoken on tblmachine(txtjwttoken);

alter table tbldatatable drop column txtmaterial;

alter table tbldatatable add column enummaterial smallint default null;
create index index_tbldatatable_enummaterial on tbldatatable(enummaterial);

create table tbltransaction(
 	pkid bigserial not null,
  	lockversion bigint not null,
  	fkdatatableid bigint default null,	
  	fkmachineid bigint default null,
	txttransactionid text default null,
	enumstatus smallint default null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_fkdatatableid check (fkdatatableid > 0),
  	constraint positive_fkmachineid check (fkmachineid > 0),
  	constraint foreign_fkdatatableid foreign key(fkdatatableid) references tbldatatable(pkid),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid)
);
create index index_tbltransaction_fkdatatableid on tbltransaction(fkdatatableid);
create index index_tbltransaction_fkmachineid on tbltransaction(fkmachineid);
create index index_tbltransaction_enumstatus on tbltransaction(enumstatus);

/*****09/10/2021****/
alter table tbltransaction drop column fkdatatableid;
alter table tbltransaction add column dateupdate bigint default null;
alter table tbltransaction add column txtbarcode text default null;
alter table tbltransaction drop column lockversion;

alter table tbltransaction add column txtreason varchar(256) default null;

/*****13/10/2021****/
DROP TABLE tbltransaction;

create table tbltransaction(
 	pkid bigserial not null,
 	txttransactionid text not null,
	patbottlecount bigint default 0,
	patbottlevalue decimal(10,2) default 0,
	alubottlecount bigint default 0,
	alubottlevalue decimal(10,2) default 0,
	glassbottlecount bigint default 0,
	glassbottlevalue decimal(10,2) default 0,
	totalvalue decimal(10,2) default 0,
	datestart bigint default null,
	dateend bigint default null,
  	fkmachineid bigint not null,	
 	txtbarcode text default null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_patbottlecount check (patbottlecount >= 0),
  	constraint positive_patbottlevalue check (patbottlevalue >= 0),
  	constraint positive_alubottlecount check (alubottlecount >= 0),
  	constraint positive_alubottlevalue check (alubottlevalue >= 0),
  	constraint positive_glassbottlecount check (glassbottlecount >= 0),
  	constraint positive_glassbottlevalue check (glassbottlevalue >= 0),
  	constraint positive_totalvalue check (totalvalue >= 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid)
);
	create index index_tbltransaction_fkmachineid on tbltransaction(fkmachineid);
	
create table tbltransactionlog(
 	pkid bigserial not null,
  	fktransactionid bigint default null,
	enummaterial smallint default null,
	txtreason varchar(256) default null,
	txtbarcode text default null,
	enumstatus smallint default null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_fktransactionid check (fktransactionid > 0),
  	constraint foreign_fktransactionid foreign key(fktransactionid) references tbltransaction(pkid)
);
create index index_tbltransactionlog_enummaterial on tbltransactionlog(enummaterial);
create index index_tbltransactionlog_enumstatus on tbltransactionlog(enumstatus);

/*****14/10/2021****/
ALTER TABLE tbltransactionlog
    ALTER COLUMN fktransactionid SET NOT NULL;

ALTER TABLE tbltransactionlog
    ALTER COLUMN txtbarcode SET NOT NULL;

ALTER TABLE tbltransactionlog
    ALTER COLUMN enumstatus SET NOT NULL;
    
/*****16/10/2021****/
ALTER table tbltransaction add column dateupdate bigint default null;

/*****21/10/2021****/
alter table tbltransaction add column fkbarcodefileid bigint NULL,
ADD constraint positive_fkbarcodefileid check(fkbarcodefileid >= 0),
ADD constraint foreign_fkbarcodefileid foreign key(fkbarcodefileid) rebooleanferences tblfile(pkid);
create index index_tbltransaction_fkqrfileid on tbltransaction(fkbarcodefileid);    

/****17/11/2021*****/
ALTER TABLE tbllocation
  DROP COLUMN fkmachineid;
  
  /****18/11/2021*****/
alter table tblmachine rename column txtmachineid to txtserialnumber;
ALTER TABLE tblmachine ADD UNIQUE (txtserialnumber);

ALTER TABLE tblmachine
    ALTER COLUMN txtserialnumber TYPE character varying(20) COLLATE pg_catalog."default";

/****26/11/2021*****/
ALTER TABLE tblmachine DROP COLUMN txtjwttoken;

INSERT INTO tblsystemsetting (txtkey, txtvalue, enumdatatype)
VALUES ('MACHINE_JWT_TOKEN_EXPIRY_TIME_IN_DAY', 1, 1);
  
/****29/11/2021*****/        
DROP table tblclient;

create table tblcustomer(
 	pkid bigserial not null,
  	lockversion bigint not null,
  	txtname varchar(500) not null,
  	fklogoid bigint default null,
  	txtaddress varchar(500) not null,
  	txtarea varchar(500) default null,
  	fkcountryid bigint not null,
  	fkstateid bigint not null,
  	fkcityid bigint not null,
	txtpincode varchar(6) not null,
  	fkcreateby bigint default null,
  	datecreate bigint not null,
  	fkupdateby bigint default null,
  	dateupdate bigint default null,
  	isactive boolean not null default true,
  	fkactchangeby bigint default null,
  	dateactchange bigint default null,
	primary key(pkid),
  	unique(txtname),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_lockversion check(lockversion >= 0),
  	constraint positive_fkcreateby check (fkcreateby >= 0),
  	constraint positive_fkupdateby check (fkupdateby >= 0),
  	constraint positive_fkactchangeby check (fkactchangeby >= 0),
   	constraint positive_fkcountryid check (fkcountryid >= 0),
  	constraint positive_fkstateid check (fkstateid >= 0),
  	constraint positive_fkcityid check (fkcityid >= 0),
  	constraint positive_fklogoid check (fklogoid > 0),
  	constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid),
  	constraint foreign_fkupdateby foreign key(fkupdateby) references tbluser(pkid),
  	constraint foreign_fkactchangeby foreign key(fkactchangeby) references tbluser(pkid),
  	constraint foreign_fkcountryid foreign key(fkcountryid) references tblcountry(pkid),
  	constraint foreign_fkstateid foreign key(fkstateid) references tblstate(pkid),
  	constraint foreign_fkcityid foreign key(fkcityid) references tblcity(pkid),
  	constraint foreign_fklogoid foreign key(fklogoid) references tblfile(pkid)
);
create index index_tblcustomer_fkcreateby on tblcustomer(fkcreateby);
create index index_tblcustomer_fkupdateby on tblcustomer(fkupdateby);
create index index_tblcustomer_fkactchangeby on tblcustomer(fkactchangeby);
create index index_tblcustomer_isactive on tblcustomer(isactive);
create index index_tblcustomer_fkcountryid on tblcustomer(fkcountryid);
create index index_tblcustomer_fkstateid on tblcustomer(fkstateid);
create index index_tblcustomer_fkcityid on tblcustomer(fkcityid);
create index index_tblcustomer_fklogoid on tblcustomer(fklogoid);

DROP table tblclientuser;
create table tblcustomeruser(
	fkuserid bigint not null,
	fkcustomerid bigint not null,
  	primary key(fkuserid, fkcustomerid),
  	constraint positive_fkuserid check (fkuserid > 0),
  	constraint positive_fkcustomerid check (fkcustomerid > 0),
  	constraint foreign_fkuserid foreign key(fkuserid) references tbluser(pkid),
  	constraint foreign_fkcustomerid foreign key(fkcustomerid) references tblcustomer(pkid)
);
create index index_tblcustomeruser_fkuserid on tblcustomeruser(fkuserid);
create index index_tblcustomeruser_fkcustomerid on tblcustomeruser(fkcustomerid);

ALTER TABLE tbluser
RENAME COLUMN isclientadmin TO iscustomeradmin;
create index index_tbluser_iscustomeradmin on tbluser(iscustomeradmin);

UPDATE tblrole
SET txtname = 'Customer Admin'
WHERE pkid =2;

ALTER TABLE tblmachine
ADD column fkcustomerid bigint default null,
ADD column fklocationid bigint default null,
ADD column enummachinestatus smallint default 1,
ADD constraint positive_fkcustomerid check(fkcustomerid >= 0),
ADD constraint positive_fklocationid check(fklocationid >= 0),
ADD constraint positive_enummachinestatus check (enummachinestatus > 0),
ADD constraint foreign_fkcustomerid foreign key(fkcustomerid) references tblcustomer(pkid),
ADD constraint foreign_fklocationid foreign key(fklocationid) references tbllocation(pkid);
create index index_tblmachine_fkcustomerid on tblmachine(fkcustomerid);
create index index_tblmachine_fklocationid on tblmachine(fklocationid);

ALTER TABLE tbllocation
DROP COLUMN fkclientid;

ALTER TABLE tbllocation
ADD column fkcustomerid bigint default null,
ADD constraint positive_fkcustomerid check(fkcustomerid >= 0),
ADD constraint foreign_fkcustomerid foreign key(fkcustomerid) references tblcustomer(pkid);
create index index_tbllocation_fkcustomerid on tbllocation(fkcustomerid);

/****06/12/2021*****/
alter table tblmachine rename column enummachinestatus to enummachineactivitystatus;

alter table tblmachine add column enummachinedevelopmentstatus smallint default null;
create index index_tblmachine_enummachinedevelopmentstatus on tblmachine(enummachinedevelopmentstatus);

alter table tblmachine add column txtmpin varchar(6) default null;

alter table tblmachine drop CONSTRAINT tblmachine_txtmachineid_key;
create index index_tblmachine_txtserialnumber on tblmachine(txtserialnumber);

/****16/12/2021*****/

ALTER TABLE tblmachine ALTER COLUMN enummachineactivitystatus SET DEFAULT 3;
ALTER TABLE tblmachine ALTER COLUMN enummachinedevelopmentstatus SET DEFAULT 1;

/****17/12/21******/

alter table tblcustomer drop column txtaddress;
alter table tblcustomer drop column txtarea;
alter table tblcustomer drop column fkcountryid;
alter table tblcustomer drop column fkstateid;
alter table tblcustomer drop column fkcityid;
alter table tblcustomer drop column txtpincode;
alter table tblcustomer drop CONSTRAINT tblcustomer_txtname_key;

/****21/12/21****/

ALTER TABLE tblmachine ALTER COLUMN enummachinedevelopmentstatus SET NOT NULL;
ALTER TABLE tblmachine ALTER COLUMN enummachineactivitystatus SET NOT NULL;

/****27/12/21****/

ALTER TABLE tbluser drop column txtcountrycode;
delete from tblresponsemessage where enumcode = 2096;
delete from tblresponsemessage where enumcode = 2097;

/****31/12/21****/

ALTER TABLE tbllocation
ADD column fkcountryid bigint default null,
ADD constraint positive_fkcountryid check(fkcountryid >= 0),
ADD constraint foreign_fkcountryid foreign key(fkcountryid) references tblcountry(pkid);
create index index_tbllocation_fkcountryid on tbllocation(fkcountryid);

create table tblerror(
 	pkid bigserial not null,
 	txterrorname text not null,
	resolvedate bigint default null,
	createdate bigint not null,
	isresolve boolean default null,
  	fkmachineid bigint not null,	
  	txterrorid text default null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_resolvedate check (resolvedate >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid)
);
	create index index_tblerror_fkmachineid on tblerror(fkmachineid);

create table tblerrorlog(
	pkid bigserial not null,
	fkerrorid bigint default null,
	fkmachineid bigint not null,
	enummachineactivitystatus smallint not null,
	createdate bigint not null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_enummachineactivitystatus check(enummachineactivitystatus >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint foreign_fkerrorid foreign key(fkerrorid) references tblerror(pkid),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid)
);
	create index index_tblerrorlog_fkerrorid on tblerrorlog(fkerrorid);
	create index index_tblerrorlog_fkmachineid on tblerrorlog(fkmachineid);

ALTER TABLE tblmachine

ADD COLUMN patbottlepercentage decimal(10,2) default 0,
ADD COLUMN alubottlepercentage decimal(10,2) default 0,
ADD COLUMN glassbottlepercentage decimal(10,2) default 0,
ADD	constraint positive_patbottlepercentage check (patbottlepercentage >= 0),
ADD	constraint positive_alubottlepercentage check (alubottlepercentage >= 0),
ADD	constraint positive_glassbottlepercentage check (glassbottlepercentage >= 0)

alter table tblmachine rename column txtserialnumber to txtmachineid;
ALTER TABLE tblmachine ADD UNIQUE (txtmachineid);

ALTER TABLE tbltransaction ADD COLUMN isoffline boolean default false;

create table tblbarcodetemplate(
	pkid bigserial not null,
	txtname text not null,
	totallength bigint not null,
	currentlength bigint default 0,
	enumstatus smallint default 1,
	datecreate bigint not null,
	fkcreateby bigint default null,
	primary key(pkid),
	unique(name),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_totallength check(totallength >= 0),
  	constraint positive_currentlength check(currentlength >= 0),
    constraint positive_enumstatus check(enumstatus >= 0),
  	constraint positive_fkcreateby check (fkcreateby>0),
  	constraint foreign_fkcreateby foreign key(fkcreateby) references tbluser(pkid)
);
create index index_tblbarcodetemplate_fkcreateby on tblbarcodetemplate(fkcreateby);

create table tblbarcodestructure(
	pkid bigserial not null,
	fkbarcodetemplateid bigint not null,
	enumbarcodetype smallint not null,
    numberlength bigint not null,
    txtvalue text default null,
	enumdynamicvalue smallint default null,
	txtindex text default null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_enumbarcodetype check(enumbarcodetype >= 0),
  	constraint positive_enumdynamicvalue check(enumdynamicvalue >= 0),
  	constraint foreign_fkbarcodetemplateid foreign key(fkbarcodetemplateid) references tblbarcodetemplate(pkid)
);
	create index index_tblbarcodestructure_fkbarcodetemplateid on tblbarcodestructure(fkbarcodetemplateid);
	
ALTER TABLE tbllocation	ADD COLUMN txtbranchnumber varchar(4) default null;
	
ALTER TABLE tblmachine ADD COLUMN txtbranchmachinenumber varchar(2) default null;

ALTER TABLE IF EXISTS public.tbllocation ALTER COLUMN txtaddress DROP NOT NULL;
    
ALTER TABLE tblmachine 
	ADD column fkbarcodetemplateid bigint default null,
 	ADD	constraint foreign_fkbarcodetemplateid foreign key(fkbarcodetemplateid) references tblbarcodetemplate(pkid);
	create index index_tblmachine_fkbarcodetemplateid on tblmachine(fkbarcodetemplateid);
	
ALTER TABLE IF EXISTS public.tblbarcodetemplate DROP COLUMN IF EXISTS enumstatus;
	
ALTER TABLE tblbarcodetemplate
ADD COLUMN iscompleted boolean not null default false

ALTER TABLE tblbarcodestructure ADD COLUMN txtendvalue text default null;

ALTER TABLE tblbarcodestructure ADD COLUMN txtfieldname text default null;

ALTER TABLE tblmachine ADD COLUMN enumbinfullstatus smallint default 1,
add	constraint positive_enumbinfullstatus check(enumbinfullstatus >= 0)

CREATE TABLE tblchangelocation(
	pkid bigserial not null,
	fkmachineid bigint not null,
	fkoldcustomerid bigint not null,
	fkoldlocationid bigint not null,
	fkoldbarcodetemplateid bigint not null,
	fkcustomerid bigint default null,
	fklocationid bigint default null,
	fkbarcodetemplateid bigint default null,
	requestdate bigint not null,
	enumstatus smallint default null,/* 1.pending 2.accepted 3. rejected 4.sync in process*/
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_enumstatus check(enumstatus >= 0),
	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid),
	constraint foreign_fkoldcustomerid foreign key(fkoldcustomerid) references tblcustomer(pkid),
	constraint foreign_fkoldlocationid foreign key(fkoldbranchid) references tbllocation(pkid),
	constraint foreign_fkoldbarcodetemplateid foreign key(fkoldbarcodetemplateid) references tblbarcodetemplate(pkid),
	constraint foreign_fkcustomerid foreign key(fkcustomerid) references tblcustomer(pkid),
	constraint foreign_fklocationid foreign key(fkbranchid) references tbllocation(pkid),
	constraint foreign_fkbarcodetemplateid foreign key(fkbarcodetemplateid) references tblbarcodetemplate(pkid)
);
	create index index_tblchangelocation_fkmachineid on tblchangelocation(fkmachineid);
	create index index_tblchangelocation_fkoldcustomerid on tblchangelocation(fkoldcustomerid);
	create index index_tblchangelocation_fkoldbranchid on tblchangelocation(fkoldbranchid);
	create index index_tblchangelocation_fkoldbarcodetemplateid on tblchangelocation(fkoldbarcodetemplateid);
	create index index_tblchangelocation_fkcustomerid on tblchangelocation(fkcustomerid);
	create index index_tblchangelocation_fkbranchid on tblchangelocation(fkbranchid);
	create index index_tblchangelocation_fkbarcodetemplateid on tblchangelocation(fkbarcodetemplateid);

ALTER TABLE tblmachine
ADD COLUMN isbarcodechanged boolean not null default false;

ALTER TABLE tblmachine
ADD COLUMN isrequestrejected boolean default null;

ALTER TABLE tbltransaction
	ADD column fklocationid bigint default null,
 	ADD	constraint foreign_fklocationid foreign key(fklocationid) references tbllocation(pkid);
	create index index_tbltransaction_fklocationid on tbltransaction(fklocationid);
	
	update tbltransaction set fklocationid = 3 where fkmachineid=1;
	update tbltransaction set fklocationid = 62 where fkmachineid=16;

ALTER TABLE tblerror
	ADD column fklocationid bigint default null,
 	ADD	constraint foreign_fklocationid foreign key(fklocationid) references tbllocation(pkid);
	create index index_tblerror_fklocationid on tblerror(fklocationid);
	
	update tblerror set fklocationid = 3 where fkmachineid=1;
	update tblerror set fklocationid = 62 where fkmachineid=16;
	
ALTER TABLE tblchangelocation
ADD COLUMN newbranchmachinenumber varchar(2) default null;

ALTER TABLE tblchangelocation
ADD COLUMN oldbranchmachinenumber varchar(2) default null;

ALTER TABLE tblchangelocation
ADD COLUMN responsedate bigint default null;

/****06/10/2022*****/

update tbltransaction set fklocationid=38 where fkmachineid=2;

ALTER TABLE tblmachine
ADD column enummachinetype smallint default 1,
ADD constraint positive_enummachinetype check (enummachinetype > 0);

ALTER TABLE IF EXISTS public.tblmachine DROP COLUMN IF EXISTS txtmachinename;

ALTER TABLE public.tbllocation
    ALTER COLUMN txtpincode TYPE character varying(7) COLLATE pg_catalog."default";
    
update tblerror set fklocationid=71 where fkmachineid=1;

ALTER TABLE tblcustomer
ADD CONSTRAINT constraint_name UNIQUE (txtname);

ALTER TABLE IF EXISTS public.tbllocation DROP CONSTRAINT IF EXISTS tbllocation_txtname_key;

create table tblreport(
 	pkid bigserial not null,
 	fkmachineid bigint not null,	
 	fklocationid bigint default null,
	createdate bigint not null,
	patbottlecount bigint default 0,
	alubottlecount bigint default 0,
	glassbottlecount bigint default 0,
	totalbottlecount bigint default 0,
	totalvalue decimal(10,2) default 0,

	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid),
  	constraint foreign_fklocationid foreign key(fklocationid) references tbllocation(pkid)
);
	create index index_tblreport_fkmachineid on tblreport(fkmachineid);
	create index index_tblreport_fklocationid on tblreport(fklocationid);	
	
	ALTER TABLE tbltransaction 
		ADD COLUMN isfetch boolean default false;
		
	ALTER TABLE tblreport 
		ADD COLUMN bigpatbottlecount bigint default 0,
		ADD COLUMN smallpatbottlecount bigint default 0,
		ADD COLUMN bigglassbottlecount bigint default 0,
		ADD COLUMN smallglassbottlecount bigint default 0;
		
ALTER TABLE public.tbluser
    ALTER COLUMN txtpincode TYPE character varying(7) COLLATE pg_catalog."default";
    
    ALter TABLE tbltransactionlog
    	ADD COLUMN VOLUMN decimal(10,2) default 0;
    	
delete from tbltransactionlog where fktransactionid in (select pkid from tbltransaction where txtbarcode IS null)

delete from tbltransaction where txtbarcode IS null;
    	
update tbltransactionlog set volumn =100 where volumn=0.00 and enumstatus=1;

ALTER TABLE tbltransactionlog ADD COLUMN fkimageid bigint default null,
ADD constraint foreign_fkimageid foreign key(fkimageid) references tblfile(pkid);

create index index_tbltransactionlog_fkimageid on tbltransactionlog(fkimageid);	

ALTER TABLE public.tbluser
    ALTER COLUMN txtpincode TYPE character varying(7) COLLATE pg_catalog."default";
    
    ALTER TABLE IF EXISTS public.tbluser DROP CONSTRAINT IF EXISTS tbluser_txtemail_key;
    
    ALTER TABLE IF EXISTS public.tbllocation DROP CONSTRAINT IF EXISTS tbllocation_txtname_key;

create table tblmachinelog(
 	pkid bigserial not null,
 	fkmachineid bigint not null,	
 	fklocationid bigint default null,
	createdate bigint not null,
	resetdate bigint not null,
	enummaterial smallint not null,
	materialcount bigint not null,
	ishardreset boolean default null,
	hardResetdate bigint default null,

	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint positive_resetdate check (resetdate >= 0),
  	constraint positive_enummaterial check (enummaterial > 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid),
  	constraint foreign_fklocationid foreign key(fklocationid) references tbllocation(pkid)
);
	create index index_tblmachinelog_fkmachineid on tblmachinelog(fkmachineid);
	create index index_tblmachinelog_fklocationid on tblmachinelog(fklocationid);
	
	
ALTER TABLE tblmachine
	ADD COLUMN patbottlecount bigint default 0,
	ADD COLUMN glassbottlecount bigint default 0,
	ADD COLUMN alubottlecount bigint default 0;

create table tblprintlog(
 	pkid bigserial not null,
 	fkmachineid bigint not null,	
 	fklocationid bigint default null,
	createdate bigint not null,
	resetdate bigint not null,
	printcount bigint not null,

	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint positive_resetdate check (resetdate >= 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid),
  	constraint foreign_fklocationid foreign key(fklocationid) references tbllocation(pkid)
);
	create index index_tblprintlog_fkmachineid on tblprintlog(fkmachineid);
	create index index_tblprintlog_fklocationid on tblprintlog(fklocationid);
	
create table tblmachinecapacity(
 	pkid bigserial not null,
 	fkmachineid bigint not null,
 	plasticcapacity bigint not null,
 	glasscapacity bigint not null,
 	aluminiumcapacity bigint not null,
 	printcapacity bigint not null,
 	maxtransaction bigint not null,
 	maxautocleaning bigint not null,
	createdate bigint not null,

	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint positive_plasticcapacity check (plasticcapacity >= 0),
  	constraint positive_glasscapacity check (glasscapacity >= 0),
  	constraint positive_aluminiumcapacity check (aluminiumcapacity >= 0),
  	constraint positive_printcapacity check (printcapacity >= 0),
  	constraint positive_maxtransaction check (maxtransaction >= 0),
  	constraint positive_maxautocleaning check (maxautocleaning >= 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid)
);
	create index index_tblmachinecapacity_fkmachineid on tblmachinecapacity(fkmachineid);
		
	alter table tblmachine add column fkmachinecapacityid bigint default null,
	add constraint foreign_fkmachinecapacityid foreign key(fkmachinecapacityid) references tblmachinecapacity(pkid);
	create index index_tblmachine_fkmachinecapacityid on tblmachine(fkmachinecapacityid);
	
ALTER TABLE IF EXISTS public.tblrole DROP COLUMN IF EXISTS enumtype;

update tblrole set txtname='Super admin' where pkid=1;
update tblrole set txtname='Admin' where pkid=2;
insert into tblrole (pkid, lockversion, txtname, fkcreateby, datecreate, isactive, isarchive) values (
3, 0, 'Customer Master Admin', 1, (select extract(epoch from now())::bigint), true, false);

delete from tblrolemoduleright where fkroleid=4;
update tbluserrole set fkroleid=3 where fkroleid=4;
delete from tblrole where pkid=4;

alter SEQUENCE tblrole_pkid_seq restart with 4;

ALTER TABLE IF EXISTS public.tblrole
    ADD COLUMN iscustomerrole boolean NOT NULL DEFAULT false;

create table tblmqttconfiguration(
 	pkid bigserial not null,
 	fkmachineid bigint not null,
 	mqttbridgehostname text not null,
 	mqttbridgeport text not null,
 	projectid text not null,
 	cloudregion text not null,
 	registryid text not null,
 	gatewayid text not null,
 	privatekeyfilepath text not null,
 	algorithm text not null,
 	deviceid text not null,
 	messagetype text not null,
	createdate bigint not null,

	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid)
);
	create index index_tblmqttconfiguration_fkmachineid on tblmqttconfiguration(fkmachineid);
	
	alter table tblmachine add column fkmqttconfigurationid bigint default null,
	add constraint foreign_fkmqttconfigurationid foreign key(fkmqttconfigurationid) references tblmqttconfiguration(pkid);
	create index index_tblmachine_fkmqttconfigurationid on tblmachine(fkmqttconfigurationid);

/*24/08/2023*/
create table tblplcconfiguration(
 	pkid bigserial not null,
 	fkmachineid bigint not null,
 	plcipaddress text not null,
 	plcaddresscdoorfrequency bigint not null,
 	plcaddressfcfrequency bigint not null,
 	barcodescannerresponsewait bigint not null,
 	plcaddressplasticshreddercurrent bigint not null,
 	plcaddressglassshreddercurrent bigint not null,
 	plcaddressaluminiumshreddercurrent bigint not null,
 	plcaddressbcfrequency bigint not null,
	objectdetectfirstresponsewait bigint not null,
	plcaddressslidingconveyorfrequency bigint not null,
	createdate bigint not null,

	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_createdate check (createdate >= 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid),
  	constraint positive_plcaddresscdoorfrequency check (plcaddresscdoorfrequency >= 0),
  	constraint positive_plcaddressfcfrequency check (plcaddressfcfrequency >= 0),
  	constraint positive_barcodescannerresponsewait check (barcodescannerresponsewait >= 0),
  	constraint positive_plcaddressplasticshreddercurrent check (plcaddressplasticshreddercurrent >= 0),
  	constraint positive_plcaddressglassshreddercurrent check (plcaddressglassshreddercurrent >= 0),
  	constraint positive_plcaddressaluminiumshreddercurrent check (plcaddressaluminiumshreddercurrent >= 0),
    constraint positive_plcaddressbcfrequency check (plcaddressbcfrequency >= 0),
  	constraint positive_objectdetectfirstresponsewait check (objectdetectfirstresponsewait >= 0),
  	constraint positive_plcaddressslidingconveyorfrequency check (plcaddressslidingconveyorfrequency >= 0)
);
create index index_tblplcconfiguration_fkmachineid on tblplcconfiguration(fkmachineid);	
alter table tblmachine add column fkplcconfigurationid bigint default null,
add constraint foreign_fkplcconfigurationid foreign key(fkplcconfigurationid) references tblplcconfiguration(pkid);
create index index_tblmachine_fkplcconfigurationid on tblmachine(fkplcconfigurationid);
	
CREATE TABLE tblbarcodemachinefile (
	pkid bigserial NOT NULL,
	noofmachineassigned int2 NULL,
	totalnoofbarcodes int2 NULL,
	plastic int8 NULL,
	glass int8 NULL,
	alluminium int8 NULL,
	txtfilestatus varchar(50) NULL DEFAULT NULL::character varying,
	fkfilemodelid int8 NULL,
	CONSTRAINT positive_pkid CHECK ((pkid >= 0)),
	CONSTRAINT tblbarcodemachinefile_pkey PRIMARY KEY (pkid),
	CONSTRAINT tblbarcodemachinefile_fk FOREIGN KEY (fkfilemodelid) REFERENCES public.tblfile(pkid)
);
CREATE INDEX index_tblbarcodemachinefile_fkfilemodelid ON public.tblbarcodemachinefile USING btree (fkfilemodelid);

CREATE TABLE tblbarcodemachineitem (
	pkid bigserial NOT NULL,
	txtbarcodename varchar(500) NOT NULL,
	materialtype int2 NULL,
	txtmaterial varchar(100) NULL,
	fkbarcodefileid int8 NULL,
	itemvolume numeric(10, 2) NULL,
	itemweight numeric(10, 2) NULL,
	itemvalue numeric(10, 2) NULL,
	CONSTRAINT positive_pkid CHECK ((pkid >= 0)),
	CONSTRAINT tblbarcodemachineitem_pkey PRIMARY KEY (pkid),
	CONSTRAINT tblbarcodemachineitem_fk FOREIGN KEY (fkbarcodefileid) REFERENCES public.tblbarcodemachinefile(pkid)
);
ALTER table tblmachine add column fkmachinebarcodefilemodelid bigint default null, 
add CONSTRAINT tblmachine_fkmachinebarcodefilemodelid FOREIGN KEY (fkmachinebarcodefilemodelid) REFERENCES tblbarcodemachinefile(pkid);

ALTER table tblmqttconfiguration 
add column publickeyfilepath text default null,
add column derkeyfilepath text default null;

/****11/12/2022**Logistic***/
CREATE TABLE tblpickuproute (
	pkid bigserial NOT NULL,
	pickuprouteno smallint not null,
	txtarea text not null,
	txtpickuproutename text not null,
	txtcomment text null,
	CONSTRAINT positive_pkid CHECK ((pkid >= 0)),
	CONSTRAINT tblpickuproute_pkey PRIMARY KEY (pkid)
);

ALTER table tblpickuproute
add column pickuproutecreatedate int8 null;

ALTER TABLE tbllocation
ADD COLUMN fkpickuprouteid BIGINT DEFAULT NULL,
ADD COLUMN positionroute SMALLINT DEFAULT NULL,
ADD COLUMN workduringweekends BOOLEAN DEFAULT NULL,
ADD COLUMN pickupeveryday BOOLEAN DEFAULT NULL,
ADD COLUMN numberofglasstanks SMALLINT DEFAULT NULL,
ADD COLUMN glassfullnesspercentageforpickup SMALLINT DEFAULT NULL,
ADD CONSTRAINT tbllocation_fkpickuprouteid
  FOREIGN KEY (fkpickuprouteid) REFERENCES tblpickuproute(pkid);

ALter TABLE tbltransactionlog
    	ADD COLUMN weight decimal(10,2) default 0;

ALter TABLE tbltransaction
    	ADD COLUMN weight decimal(10,2) default 0;
          
 ALTER table tblchangelocation add column fkmachinebarcodefilemodelid bigint default null, 
add CONSTRAINT tblmachine_fkmachinebarcodefilemodelid FOREIGN KEY (fkmachinebarcodefilemodelid) REFERENCES tblbarcodemachinefile(pkid);

ALTER table tblchangelocation add column fkoldmachinebarcodefileid bigint default null, 
add CONSTRAINT tblmachine_fkoldmachinebarcodefileid FOREIGN KEY (fkoldmachinebarcodefileid) REFERENCES tblbarcodemachinefile(pkid);
         
ALTER TABLE IF EXISTS public.tblmachine
    RENAME isbarcodechanged TO isbarcodetemplatechanged;
    
ALTER TABLE tblmachine
ADD COLUMN isbarcodechanged boolean not null default false;

ALter TABLE tbltransactionlog
    	ADD COLUMN weight decimal(10,2) default 0;

ALter TABLE tbltransaction
    	ADD COLUMN weight decimal(10,2) default 0;
    	
ALTER TABLE tblmachine
    ADD COLUMN ispasswordchanged boolean NOT NULL DEFAULT false;
    
ALter TABLE tblmachine
    	ADD COLUMN password varchar(20) default null;

Alter TABLE tblmachine
	ADD COLUMN islogochanged boolean NOT NULL DEFAULT false;
	
Alter TABLE tblmachine
	ADD COLUMN iscapacitychanged boolean NOT NULL DEFAULT false;

/****1/2/2024**system-specification-detail***/
CREATE TABLE tblsystemspecificationdetail (
	pkid bigserial NOT NULL,
	fkmachineid bigint not null,
	anydeskid text not null,
	txtanydeskpassword text not null,
	txtwindowsactivationkey text null,
	txtwindowsproductionkey text null,
	txtwindowspassword text null,
	dateOfCreated bigint not null,
    dateOfUpdated bigint not null,
	CONSTRAINT positive_pkid CHECK ((pkid >= 0)),
	CONSTRAINT tblsystemspecificationdetail_pkey PRIMARY KEY (pkid),
	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid)
);
create index index_tblsystemspecificationdetail_fkmachineid on tblsystemspecificationdetail(fkmachineid);


/****12/1/2023****/
/****new complete query, if used above one then_alter_table****/
CREATE TABLE tblbarcodemachinefile (
	pkid bigserial NOT NULL,
	noofmachineassigned int8 NULL,
	totalnoofbarcodes int8 NULL,
	plastic int8 NULL,
	glass int8 NULL,
	alluminium int8 NULL,
	txtfilestatus varchar(50) NULL DEFAULT NULL::character varying,
	CONSTRAINT positive_pkid CHECK ((pkid >= 0)),
	CONSTRAINT tblbarcodemachinefile_pkey PRIMARY KEY (pkid)
);
/**** ALTER_TABLE_tbl_barcode_machine_file ****/
    
ALTER TABLE IF EXISTS public.tblbarcodemachinefile DROP COLUMN IF EXISTS fkfilemodelid;
ALTER TABLE public.tblbarcodemachinefile ALTER COLUMN noofmachineassigned TYPE bigint;
ALTER TABLE public.tblbarcodemachinefile ALTER COLUMN totalnoofbarcodes TYPE bigint;
ALTER TABLE public.tblbarcodemachinefile ADD COLUMN txtbarcodefilename varchar(500) default null;

CREATE TABLE tbldailypickuplogs (
	pkid bigserial NOT NULL,
	fkpickuprouteid bigint not null,
	generateplandate bigint not null,
	numberofpatbottle bigint not null,
	numberofalubottle bigint not null,
	numberofglassbottle bigint not null,
	totalweight decimal(10,2) default 0,
	totalnumberofpickup bigint not null,
	primary key(pkid),
  	constraint positive_pkid check(pkid >= 0),
  	constraint positive_generateplandate check (generateplandate >= 0),
  	constraint foreign_fkpickuprouteid foreign key(fkpickuprouteid) references tblpickuproute(pkid)
);


ALTER TABLE tblmachine 
ADD column isactive boolean not null default true,
ADD column fkactchangeby bigint default null,
ADD column dateactchange bigint default null,
ADD constraint positive_fkactchangeby check (fkactchangeby >= 0),
ADD constraint foreign_fkactchangeby foreign key(fkactchangeby) references tbluser(pkid);
create index index_tblmachine_fkactchangeby on tblmachine(fkactchangeby);
create index index_tblmachine_isactive on tblmachine(isactive);

create table tblmachinematerials(
	fkmachineid bigint not null,
	material int not null,
  	primary key(fkmachineid, material),
  	constraint positive_material check (material > 0),
  	constraint positive_fkmachineid check (fkmachineid > 0),
  	constraint foreign_fkmachineid foreign key(fkmachineid) references tblmachine(pkid),
);