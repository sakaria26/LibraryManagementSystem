-- we don't know how to generate root <with-no-name> (class Root) :(
grant audit_abort_exempt, firewall_exempt, select, system_user on *.* to 'mysql.infoschema'@localhost;

grant audit_abort_exempt, authentication_policy_admin, backup_admin, clone_admin, connection_admin, firewall_exempt, persist_ro_variables_admin, session_variables_admin, shutdown, super, system_user, system_variables_admin on *.* to 'mysql.session'@localhost;

grant audit_abort_exempt, firewall_exempt, system_user on *.* to 'mysql.sys'@localhost;

grant alter, alter routine, application_password_admin, audit_abort_exempt, audit_admin, authentication_policy_admin, backup_admin, binlog_admin, binlog_encryption_admin, clone_admin, connection_admin, create, create role, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, drop role, encryption_key_admin, event, execute, file, firewall_exempt, flush_optimizer_costs, flush_status, flush_tables, flush_user_resources, group_replication_admin, group_replication_stream, index, innodb_redo_log_archive, innodb_redo_log_enable, insert, lock tables, passwordless_user_admin, persist_ro_variables_admin, process, references, reload, replication client, replication slave, replication_applier, replication_slave_admin, resource_group_admin, resource_group_user, role_admin, select, sensitive_variables_observer, service_connection_admin, session_variables_admin, set_user_id, show databases, show view, show_routine, shutdown, super, system_user, system_variables_admin, table_encryption_admin, trigger, update, xa_recover_admin, grant option on *.* to root@localhost;

create table librarian
(
    librarianid       varchar(15)  not null
        primary key,
    librarianname     varchar(255) not null,
    librariansurname  varchar(255) not null,
    librarianemail    varchar(255) not null,
    librarianpassword varchar(20)  not null,
    role              varchar(15)  not null
);

create index idx_Librarian
    on librarian (librarianid);

create table media
(
    mediaid         varchar(15)  not null
        primary key,
    title           varchar(255) not null,
    publicationyear year         not null,
    type            varchar(10)  not null
);

create table book
(
    bookid          varchar(15)                   not null,
    title           varchar(255)                  not null,
    author          varchar(255)                  not null,
    publicationyear year                          not null,
    genre           varchar(50)                   not null,
    price           decimal(8, 2)                 not null,
    description     varchar(255)                  not null,
    cover           mediumblob                    not null,
    pagecount       int                           not null,
    status          varchar(20) default 'inhouse' not null,
    constraint bookid
        unique (bookid),
    constraint bookid
        foreign key (bookid) references media (mediaid)
);

create index idx_Book
    on book (bookid);

create table ebook
(
    ebookid         varchar(15)                   not null,
    ebooktitle      varchar(255)                  not null,
    author          varchar(255)                  not null,
    publicationyear year                          not null,
    genre           varchar(50)                   not null,
    description     varchar(255)                  not null,
    cover           mediumblob                    not null,
    document        mediumblob                    not null,
    pagecount       int                           not null,
    format          varchar(6)                    not null,
    status          varchar(20) default 'inhouse' not null,
    constraint ebookid
        unique (ebookid),
    constraint ebookid
        foreign key (ebookid) references media (mediaid)
);

create index idx_EBook
    on ebook (ebookid);

create table journal
(
    journalid       varchar(15)                      not null,
    title           varchar(255)                     not null,
    author          varchar(255)                     not null,
    publicationyear year                             not null,
    areaofstudy     varchar(255)                     not null,
    citations       int                              not null,
    status          varchar(20) default 'incomplete' not null,
    volume          int                              not null,
    issue           int                              not null,
    document        mediumblob                       not null,
    cover           mediumblob                       not null,
    constraint journalid
        unique (journalid),
    constraint journalid
        foreign key (journalid) references media (mediaid)
);

create index idx_Journal
    on journal (journalid);

create index idx_Media
    on media (mediaid);

create table member
(
    memberid         varchar(15)                   not null
        primary key,
    membername       varchar(255)                  not null,
    membersurname    varchar(255)                  not null,
    memberemail      varchar(255)                  not null,
    memberpassword   varchar(255)                  not null,
    phonenumber      varchar(15)                   not null,
    noofissues       int         default 0         not null,
    membershipstatus varchar(15) default 'pending' not null
);

create table issue
(
    issueid      varchar(255)                  not null
        primary key,
    memberid     varchar(15)                   not null,
    mediaid      varchar(15)                   not null,
    issuedate    date                          not null,
    periodindays int                           not null,
    returndate   date                          not null,
    fine         int         default 0         not null,
    status       varchar(20) default 'pending' null,
    finepaid     tinyint(1)  default 0         not null,
    constraint mediaid
        foreign key (mediaid) references media (mediaid),
    constraint memberid
        foreign key (memberid) references member (memberid)
);

create index idx_Issue
    on issue (issueid);

create index idx_Member
    on member (memberid);

create
    definer = root@localhost procedure sp_AddBook(IN thebookid varchar(15), IN thebooktitle varchar(250),
                                                  IN thebookauthor varchar(100), IN thepublicationyear year,
                                                  IN thegenre varchar(50), IN theprice decimal(10, 2),
                                                  IN thedescription varchar(250), IN thecover mediumblob,
                                                  IN thepagecount int, OUT wassuccessful tinyint(1))
begin
    declare numberofrowsbefore int;
    declare numberofrowsafter int;

    select count(*) into numberofrowsbefore from book;

    insert into media
    values
        (thebookid, thebooktitle, thepublicationyear, 'book');
    insert into book
    (bookid, title, author, publicationyear, genre, price, description, cover, pagecount)
    values
        (thebookid,
         thebooktitle,
         thebookauthor,
         thepublicationyear,
         thegenre,
         theprice,
         thedescription,
         thecover,
         thepagecount);

    select count(*) into numberofrowsafter from book;

    if(numberofrowsafter>numberofrowsbefore)
    then
        set wassuccessful = true;
    else
        set wassuccessful = false;
    end if;
end;

create
    definer = root@localhost procedure sp_AddEbook(IN theebookid varchar(15), IN theebooktitle varchar(250),
                                                   IN theebookauthor varchar(100), IN thepublicationyear year,
                                                   IN thegenre varchar(50), IN thedescription varchar(250),
                                                   IN thecover mediumblob, IN thedocument mediumblob,
                                                   IN thepagecount int, IN theformat varchar(10),
                                                   OUT wassuccessful tinyint(1))
begin
    declare numberofrowsbefore int;
    declare numberofrowsafter int;

    select count(*) into numberofrowsbefore from ebook;

    insert into media
    values
        (theebookid, theebooktitle, thepublicationyear, 'ebook');

    insert into ebook
    (ebookid, ebooktitle, author, publicationyear, genre, description, cover, document, pagecount, format)
    values
        (theebookid,
         theebooktitle,
         theebookauthor,
         thepublicationyear,
         thegenre,
         thedescription,
         thecover,
         thedocument,
         thepagecount,
         theformat);


    select count(*) into numberofrowsafter from ebook;

    if(numberofrowsbefore<numberofrowsafter)
    then
        begin
            set wassuccessful = true;
        end;
    else
        begin
            set wassuccessful = false;
        end;
    end if;
end;

create
    definer = root@localhost procedure sp_AddJournal(IN thejournalid varchar(15), IN thejournaltitle varchar(250),
                                                     IN thejournalauthor varchar(100), IN thepublicationyear year,
                                                     IN theareaofstudy varchar(100), IN thecitations int,
                                                     IN thevolume int, IN theissue int, IN thedocument mediumblob,
                                                     IN thecover mediumblob, OUT wassuccessful tinyint(1))
begin
    declare numberofrowsbefore int;
    declare numberofrowsafter int;

    select count(*) into numberofrowsbefore from journal;

    insert into media
    values
        (thejournalid, thejournaltitle, thepublicationyear, 'journal');
    insert into journal
    (journalid, title, author, publicationyear, areaofstudy, citations, volume, issue, document, cover)
    values
        (thejournalid,
         thejournaltitle,
         thejournalauthor,
         thepublicationyear,
         theareaofstudy,
         thecitations,
         thevolume,
         theissue, thedocument, thecover);

    select count(*) into numberofrowsafter from journal;

    if(numberofrowsbefore<numberofrowsafter)
    then
        begin
            set wassuccessful = true;
        end;
    else
        begin
            set wassuccessful = false;
        end;
    end if;
end;

create
    definer = root@localhost procedure sp_AddLibrarian(IN thelibrarianid varchar(15), IN thelibrarianname varchar(50),
                                                       IN thelibrariansurname varchar(50),
                                                       IN thelibrarianemail varchar(250),
                                                       IN thelibrarianpassword varchar(50), IN therole varchar(20))
begin
    INSERT INTO librarian
    VALUES (thelibrarianid, thelibrarianname, thelibrariansurname, thelibrarianemail, thelibrarianpassword, therole);
end;

create
    definer = root@localhost procedure sp_AddMember(IN thememberid varchar(15))
begin
    update member
    set membershipstatus = 'member'
    where memberid = thememberid;
end;

create
    definer = root@localhost procedure sp_ChangeMemberPassword(IN thememberid varchar(15), IN thepassword varchar(50))
begin
    update member
    set memberpassword = thepassword
    where memberid = thememberid;
end;

create
    definer = root@localhost procedure sp_CreateIssue(IN themediaid varchar(15), IN thememberid varchar(15),
                                                      IN theperiod int)
begin
    declare theissuedate date;
    declare thereturndate date;
    declare theissueid varchar(15);
    declare numberofrows int;

    select count(*) into numberofrows from issue;
    set theissuedate = curdate();
    set thereturndate = date_add(theissuedate, interval theperiod day);
    set theissueid = concat('iss-', month(theissuedate),'-',numberofrows+1);

    insert into issue
    (issueid, memberid, mediaid, issuedate, periodindays, returndate)
        value
        (theissueid, thememberid, themediaid, theissuedate, theperiod, thereturndate);

    update book
    set status = 'pendingborrow'
    where bookid = themediaid;
end;

create
    definer = root@localhost procedure sp_DeleteBook(IN thebookid varchar(15))
begin
    delete from book where bookid = thebookid;
end;

create
    definer = root@localhost procedure sp_DeleteEBook(IN theebookid varchar(15))
begin
    delete from ebook where ebookid = theebookid;
end;

create
    definer = root@localhost procedure sp_DeleteJournal(IN thejournalid varchar(15))
begin
    delete from journal where journalid = thejournalid;
end;

create
    definer = root@localhost procedure sp_DeleteLibrarian(IN thelibrarianid varchar(15))
begin
    DELETE FROM librarian WHERE (librarianid = thelibrarianid);
end;

create
    definer = root@localhost procedure sp_DeleteMember(IN thememberid varchar(15))
begin
    update member
    set membershipstatus = 'deleted'
    where memberid = thememberid;
end;

create
    definer = root@localhost procedure sp_IssueMedia(IN theissueid varchar(15))
begin
    declare theissuedate date;
    declare theperiod int;
    declare thereturndate date;
    declare themediaid varchar(15);

    set theissuedate = curdate();
    select periodindays into theperiod from issue where issueid = theissueid;
    set thereturndate = date_add(theissuedate, interval theperiod day);
    select mediaid into themediaid from issue;

    update issue
    set issuedate = theissuedate, returndate = thereturndate, status = 'issued'
    where issueid = theissueid;

    update book
    set status = 'issued'
    where bookid = themediaid;
end;

create
    definer = root@localhost procedure sp_Login(IN userid varchar(15), IN userpassword varchar(50),
                                                OUT usertype varchar(20))
begin
    if exists(select * from member where memberid = userid and memberpassword = userpassword and membershipstatus = 'member')
    then
        set usertype = 'member';
    else if exists(select * from librarian where librarianid = userid and librarianpassword = userpassword)
    then
        select role into usertype from librarian where librarianid = userid and librarianpassword = userpassword;
    else
        set usertype = 'nonexistent';
    end if;
    end if;
end;

create
    definer = root@localhost procedure sp_MemberIssues(IN thememberid varchar(15))
begin
    select issueid , title  , periodindays , returndate
    from media m
             right join issue i on m.mediaid = i.mediaid where m.mediaid = i.mediaid and i.memberid = thememberid;
end;

create
    definer = root@localhost procedure sp_MemberSignUp(IN thememberID varchar(20), IN thememberName varchar(100),
                                                       IN thememberSurname varchar(100), IN thememberEmail varchar(100),
                                                       IN thememberPhoneNumber varchar(15))
begin
    declare randomNumber int;
    set randomNumber = FLOOR(RAND()*(9999-1000+1)+1000);
    insert into member
    (memberid, membername, membersurname, memberemail, memberpassword, phonenumber)
    values
        (thememberID, thememberName, thememberSurname, thememberEmail, randomNumber, thememberPhoneNumber);
end;

create
    definer = root@localhost procedure sp_PayFine(IN thisissueID varchar(15))
begin
    update issue
    set finepaid = true
    where issueid = thisissueID ;
end;

create
    definer = root@localhost procedure sp_ReturnMedia(IN theissueid varchar(15), OUT thefine int)
begin
    declare thereturndate date;
    declare currentdate date;
    declare themediaid varchar(15);
    declare thememberid varchar(15);

    select returndate, mediaid, memberid into thereturndate, themediaid, thememberid from issue where issueid = theissueid;
    set currentdate = curdate();
    set thefine = 50*datediff(currentdate, thereturndate)+1;

    if  (thefine < 0)
    then
        set thefine = 0;
    end if;
    update issue
    set fine = thefine, status = 'returned'
    where issueid = theissueid;

    update member
    set noofissues = noofissues-1
    where memberid = thememberid;

    update book
    set status = 'inhouse'
    where bookid = themediaid;

end;

create
    definer = root@localhost procedure sp_SearchBook(IN thesearchcriteria varchar(50), OUT thebookname varchar(100),
                                                     OUT thebookauthor varchar(250), OUT thepublicationyear year,
                                                     OUT thegenre varchar(50), OUT thepagecount int,
                                                     OUT thecover mediumblob, OUT thedescription varchar(250))
begin
    select title,
           author,
           publicationyear,
           genre,
           pagecount,
           cover,
           description into thebookname,
        thebookauthor,
        thepublicationyear,
        thegenre,
        thepagecount,
        thecover,
        thedescription from book
    where bookid like concat('%', thesearchcriteria, '%')
       or title like concat('%', thesearchcriteria, '%')
       or author like concat('%', thesearchcriteria, '%')
       or publicationyear like concat('%', thesearchcriteria, '%')
       or genre like concat('%', thesearchcriteria, '%')
       or price like concat('%', thesearchcriteria, '%')
       or description like concat('%', thesearchcriteria, '%')
       or pagecount like concat('%', thesearchcriteria, '%')
       or status like concat('%', thesearchcriteria, '%');
end;

create
    definer = root@localhost procedure sp_UpdateLibrarian(IN thelibrarianid varchar(15), IN thelibrarianpassword varchar(50))
begin
    UPDATE librarian
    SET librarianpassword = thelibrarianpassword
    WHERE librarianid = thelibrarianid;
end;

create
    definer = root@localhost procedure sp_ViewMedia(IN themediaid varchar(15), IN themediatype varchar(15))
begin
    if (themediatype = 'book')
    then
        select * from book where bookid = themediaid;

    else  if (themediatype = 'ebook')
    then
        select * from ebook where ebookid = themediaid;

    else  if (themediatype = 'journal')
    then
        select * from journal where journalid = themediaid;


    end if;
    end if;
    end if;
end;

create
    definer = root@localhost procedure sp_ViewReturn()
begin
    select *
    from issue;
end;

