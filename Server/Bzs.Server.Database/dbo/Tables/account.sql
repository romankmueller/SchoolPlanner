CREATE TABLE [dbo].[account]
(
	[id] UNIQUEIDENTIFIER NOT NULL,
	[account] NVARCHAR(100) NOT NULL,
	[password] NVARCHAR(100) NOT NULL,
	[email] NVARCHAR(MAX) NOT NULL,
	[moddate] DATETIME NOT NULL,
	[moduser] NVARCHAR(100) NOT NULL,
	CONSTRAINT [pk_account] PRIMARY KEY ([id]),
	CONSTRAINT [uq_account_account] UNIQUE ([account])
)
