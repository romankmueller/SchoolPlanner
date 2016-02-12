CREATE TABLE [dbo].[lesson]
(
	[id] UNIQUEIDENTIFIER NOT NULL,
	[accountid] UNIQUEIDENTIFIER NOT NULL,
	[dayid] UNIQUEIDENTIFIER NOT NULL,
	[fromdate] DATETIME NOT NULL,
	[todate] DATETIME NOT NULL,
	[subjectid] UNIQUEIDENTIFIER NOT NULL,
	[teacherid] UNIQUEIDENTIFIER NULL,
	[roomid] UNIQUEIDENTIFIER NULL,
	[remark] NVARCHAR(MAX) NOT NULL,
	[moddate] DATETIME NOT NULL,
	[moduser] NVARCHAR(100) NOT NULL,
	CONSTRAINT [pk_lesson] PRIMARY KEY ([id]),
	CONSTRAINT [fk_lesson_account] FOREIGN KEY ([accountid]) REFERENCES [dbo].[account] ([id]),
	CONSTRAINT [fk_lesson_day] FOREIGN KEY ([dayid]) REFERENCES [dbo].[day] ([id]),
	CONSTRAINT [fk_lesson_subject] FOREIGN KEY ([subjectid]) REFERENCES [dbo].[subject] ([id]),
	CONSTRAINT [fk_lesson_teacher] FOREIGN KEY ([teacherid]) REFERENCES [dbo].[teacher] ([id]),
	CONSTRAINT [fk_lesson_room] FOREIGN KEY ([roomid]) REFERENCES [dbo].[room] ([id])
)
