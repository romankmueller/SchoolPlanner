CREATE TABLE [dbo].[subject]
(
	[id] UNIQUEIDENTIFIER NOT NULL,
	[accountid] UNIQUEIDENTIFIER NULL,
	[code] NVARCHAR(10) NOT NULL,
	[caption] NVARCHAR(50) NOT NULL,
	[moddate] DATETIME NOT NULL,
	[moduser] NVARCHAR(100) NOT NULL,
	CONSTRAINT [pk_subject] PRIMARY KEY ([id]),
	CONSTRAINT [fk_subject_account] FOREIGN KEY ([accountid]) REFERENCES [dbo].[account] ([id])
)
