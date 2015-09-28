CREATE TABLE [dbo].[subject]
(
	[id] UNIQUEIDENTIFIER NOT NULL,
	[code] NVARCHAR(10) NOT NULL,
	[caption] NVARCHAR(50) NOT NULL,
	[moddate] DATETIME NOT NULL,
	[moduser] NVARCHAR(100) NOT NULL,
	CONSTRAINT [pk_subject] PRIMARY KEY ([id])
)
