DELETE FROM [dbo].[lesson]
DELETE FROM [dbo].[room]
DELETE FROM [dbo].[teacher]
DELETE FROM [dbo].[subject]
DELETE FROM [dbo].[day]
GO

--INSERT INTO [dbo].[account] ([id], [account], [password], [email], [moddate], [moduser]) VALUES ('FDB44E7C-AB09-4DF2-9CA9-C42D001E2957', 'test', 'test', '', GETDATE(), 'Administrator')
GO

INSERT INTO [dbo].[day] ([id], [order], [code], [caption], [moddate], [moduser]) VALUES ('49FA1196-56A6-45E1-92D5-869C192CB66C', 0, 'MO', 'Montag', GETDATE(), 'Administrator')
INSERT INTO [dbo].[day] ([id], [order], [code], [caption], [moddate], [moduser]) VALUES ('CA3FCFCA-6E35-40B8-97FF-60E65CA50CF9', 0, 'DI', 'Dienstag', GETDATE(), 'Administrator')
INSERT INTO [dbo].[day] ([id], [order], [code], [caption], [moddate], [moduser]) VALUES ('B67A4084-89E4-4C97-8F39-D03EF058A24F', 0, 'MI', 'Mittwoch', GETDATE(), 'Administrator')
INSERT INTO [dbo].[day] ([id], [order], [code], [caption], [moddate], [moduser]) VALUES ('399C467A-E8A6-421B-B25A-54C61CA55306', 0, 'DO', 'Donnerstag', GETDATE(), 'Administrator')
INSERT INTO [dbo].[day] ([id], [order], [code], [caption], [moddate], [moduser]) VALUES ('CC9CBC0A-D4A7-4D29-BA18-EC1705ADBA2F', 0, 'FR', 'Freitag', GETDATE(), 'Administrator')
INSERT INTO [dbo].[day] ([id], [order], [code], [caption], [moddate], [moduser]) VALUES ('F8999FC6-E89B-41BE-9899-126C64AFCF25', 0, 'SA', 'Samstag', GETDATE(), 'Administrator')
INSERT INTO [dbo].[day] ([id], [order], [code], [caption], [moddate], [moduser]) VALUES ('BF25DEBA-69BC-4679-BF08-4B0DD770D039', 0, 'SO', 'Sonntag', GETDATE(), 'Administrator')
GO

INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('52F5F8AB-E7CB-40B8-A5CE-63E16FF34B2F', NULL, 'IPT', 'IPT', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('C0176378-A9C3-4676-A51D-E3043005D64A', NULL, 'IKA', 'Information/Kommunikation/Administration', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('374D0590-1FC6-4C52-B496-8311A64DD206', NULL, 'D', 'Deutsch', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('550E80FE-C8C1-4EA4-BD92-BD6B1796FE09', NULL, 'M', 'Mathematik', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('2F63A1C7-A57B-4D08-93EF-3D28F8B66C8D', NULL, 'Nw', 'Naturwissenschaft', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('D3B18F40-3E3B-43D4-B998-45EDCC88F826', NULL, 'InfF', 'Informatik Applikationsentwicklung', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('8563871C-DBBA-4564-88B2-054D186DBD05', NULL, 'WR', 'Wirtschaft und Recht', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('B29AB759-6D2E-469F-8B33-3C3A59BECF35', NULL, 'E', 'Englisch', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('0B78C504-BDCB-47E6-8D83-8FDEA6D7D9A4', NULL, 'Rw', 'Rechnungswesen', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('6B59068B-164D-4FC7-9CB4-8B80C83D1EB2', NULL, 'G', 'Geschichte', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('3DF73627-860B-4911-8A22-C35C844F0A59', NULL, 'Gg', 'Geografie', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('D7A84986-25AA-46E8-8182-853F858C3984', NULL, 'TuF', 'Turnen Freifach', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('B391405F-0B41-43D1-9CBB-26508CDAE69C', NULL, 'Tu', 'Turnen', GETDATE(), 'Administrator')
INSERT INTO [dbo].[subject] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('B177A1FC-CCA5-4A3F-BCCD-81E3AE205830', NULL, 'It', 'Italienisch', GETDATE(), 'Administrator')
GO

INSERT INTO [dbo].[teacher] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('FA917544-6622-43FD-BB47-FA9648D56618', NULL, 'Mr', 'Müller Roman', GETDATE(), 'Administrator')
GO


INSERT INTO [dbo].[room] ([id], [accountid], [code], [caption], [moddate], [moduser]) VALUES ('03BE371D-F6F2-4B34-B78B-BEFFB3746716', NULL, '18', 'Zimmer 18', GETDATE(), 'Administrator')
GO


INSERT INTO [dbo].[lesson] ([id], [accountid], [dayid], [fromdate], [todate], [subjectid], [teacherid], [roomid], [remark], [moddate], [moduser]) VALUES ('9C3C05F9-F201-40F0-A2EE-F25335EF335E', 'FDB44E7C-AB09-4DF2-9CA9-C42D001E2957', '49FA1196-56A6-45E1-92D5-869C192CB66C', '2015-09-28T15:20:00', '2015-09-28T17:45:00', 'D3B18F40-3E3B-43D4-B998-45EDCC88F826', 'FA917544-6622-43FD-BB47-FA9648D56618', '03BE371D-F6F2-4B34-B78B-BEFFB3746716', 'Jupii Java', GETDATE(), 'Administrator')
GO