create table vtype (
                       vtname char(20) not null,
                       features char(50),
                       wrate integer,
                       drate integer,
                       hrate integer,
                       wirate integer,
                       dirate integer,
                       hirate integer,
                       krate integer,
                       primary key (vtname)
);
grant select on vtype to public;

create table vehicles (
                          vlicense char(20) not null,
                          make char(20) not null,
                          model char(20) not null,
                          year integer not null,
                          colour char(10),
                          odometer integer,
                          status char(20) not null,
                          vtname char(20) not null,
                          location char(20) not null,
                          city char(20) not null,
                          primary key (vlicense),
                          foreign key (vtname) references vtype ON DELETE CASCADE
);
grant select on vehicles to public;

create table reservation (
                             confNo integer not null,
                             vtname char(20) not null,
                             dlicense char(20) not null,
                             fromDateTime timestamp,
                             toDateTime timestamp,
                             primary key (confNo),
                             foreign key (vtname) references vtype ON DELETE CASCADE
);
grant select on reservation to public;

create table rent (
    rid integer not null,
    confNo integer not null,
    vlicense char(20) not null,
    dlicense char(20) not null,
    fromDateTime timestamp,
    toDateTime timestamp,
    odometer integer,
    cardName char(20),
    cardNo char(20),
    expDate char(4),
    primary key (rid),
    foreign key (confNo) references RESERVATION,
    foreign key (vlicense) references VEHICLES on delete cascade
);
grant select on rent to public;