CREATE TABLE [dbo].[room]
(
	[id] UNIQUEIDENTIFIER NOT NULL,
	[accountid] UNIQUEIDENTIFIER NULL,
	[code] NVARCHAR(10) NOT NULL,
	[caption] NVARCHAR(50) NOT NULL,
	[moddate] DATETIME NOT NULL,
	[moduser] NVARCHAR(100) NOT NULL,
	CONSTRAINT [pk_room] PRIMARY KEY ([id]),
	CONSTRAINT [fk_room_account] FOREIGN KEY ([accountid]) REFERENCES [dbo].[account] ([id])
)
