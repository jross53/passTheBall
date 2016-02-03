#players table: playerId, name, teamId, rank PK: teamId,playerId
#CREATE TABLE passtheball.players
#(
#playerId int not null,
#name varchar(100) not null,
#teamId int not null,
#rank smallint,
#efficiency float(5),
#primary key (playerId, teamId),
#foreign key(teamId) references teams(teamId)
#)





#teams table: teamId, name PK: teamId
#CREATE TABLE passtheball.teams
#(
#teamId int not null,
#name varchar(50) not null,
#primary key (teamId)
#)