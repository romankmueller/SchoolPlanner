CREATE TABLE [dbo].[day]
(
	[id] UNIQUEIDENTIFIER NOT NULL,
	[order] INT NOT NULL,
	[code] NVARCHAR(5) NOT NULL,
	[caption] NVARCHAR(50) NOT NULL,
	[moddate] DATETIME NOT NULL,
	[moduser] NVARCHAR(100) NOT NULL,
	CONSTRAINT [pk_day] PRIMARY KEY ([id])
)
