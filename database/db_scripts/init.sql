USE master
GO

IF NOT EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'SalesExercise'
)
CREATE DATABASE SalesExercise
GO

USE SalesExercise
GO
CREATE TABLE [dbo].[product] (
    [id]     INT           IDENTITY (1, 1) NOT NULL,
    [brand]  NVARCHAR (50) NULL,
    [family] NVARCHAR (50) NULL,
    [model]  NVARCHAR (50)   NULL,
    CONSTRAINT [PK_product] PRIMARY KEY CLUSTERED ([id] ASC)
);

GO
CREATE NONCLUSTERED INDEX [Index_product_family]
    ON [dbo].[product]([family] ASC);
GO
CREATE NONCLUSTERED INDEX [Index_product_brand]
    ON [dbo].[product]([brand] ASC);

GO

CREATE TABLE [dbo].[sales] (
    [id]                 INT IDENTITY (1, 1) NOT NULL,
    [product_id]         INT NOT NULL,
    [year]               INT NOT NULL,
    [month]              INT NOT NULL,
    [units_sold]         INT NOT NULL,
    [total_dollar_sales] INT NOT NULL,
    CONSTRAINT [PK_sales] PRIMARY KEY CLUSTERED ([id] ASC),
    CONSTRAINT [FK_sales_product] FOREIGN KEY ([product_id]) REFERENCES [dbo].[product] ([id])
);


GO
CREATE NONCLUSTERED INDEX [Index_sales_date]
    ON [dbo].[sales]([year] ASC, [month] ASC);

GO
CREATE UNIQUE NONCLUSTERED INDEX [uq_id_sales_prod_date]
    ON [dbo].[sales]([product_id] ASC, [year] ASC, [month] ASC);
    
GO

set identity_insert dbo.product on;

insert dbo.product ([id],[brand],[family],[model])
select 1,N'Apple',N'iPhone',N'15' UNION ALL
select 2,N'Apple',N'iPhone',N'15 Pro' UNION ALL
select 3,N'Google',N'Pixel',N'8 Pro' UNION ALL
select 4,N'Google',N'Pixel',N'7' UNION ALL
select 5,N'Samsung',N'Galaxy Z',N'Fold5' UNION ALL
select 6,N'Samsung',N'Galaxy Z',N'Flip5' UNION ALL
select 7,N'Nokia',N'abc',N'1' UNION ALL
select 8,N'Nokia',N'def',N'2';


set identity_insert dbo.product off;

set identity_insert dbo.sales on;

insert dbo.sales ([id],[product_id],[year],[month],[units_sold],[total_dollar_sales])
select 1,1,2023,10,234,432900 UNION ALL
select 2,1,2023,11,310,557690 UNION ALL
select 3,1,2023,9,176,343200 UNION ALL
select 4,1,2023,8,221,430950 UNION ALL
select 5,2,2023,6,134,288100 UNION ALL
select 6,2,2023,7,254,546100 UNION ALL
select 7,2,2023,8,226,474600 UNION ALL
select 8,2,2023,9,249,522900 UNION ALL
select 9,2,2023,10,279,5571950 UNION ALL
select 10,2,2023,11,315,661185 UNION ALL
select 11,3,2023,8,335,626450 UNION ALL
select 12,3,2023,9,299,559130 UNION ALL
select 13,3,2023,10,237,414750 UNION ALL
select 14,3,2023,11,144,244800 UNION ALL
select 15,4,2023,8,68,57800 UNION ALL
select 16,4,2023,9,77,65450 UNION ALL
select 17,4,2023,10,71,58930 UNION ALL
select 18,4,2023,11,82,65600 UNION ALL
select 19,5,2023,8,123,221277 UNION ALL
select 20,5,2023,9,118,212282 UNION ALL
select 21,5,2023,10,107,184040 UNION ALL
select 22,5,2023,11,98,166600 UNION ALL
select 23,6,2023,8,78,144300 UNION ALL
select 24,6,2023,9,89,164650 UNION ALL
select 25,6,2023,10,104,187200 UNION ALL
select 26,6,2023,11,118,212400 UNION ALL
select 27,6,2022,10,99,153100 UNION ALL
select 28,6,2022,11,32,56781 UNION ALL
select 29,1,2022,10,100,210521 UNION ALL
select 30,1,2022,11,200,429221 UNION ALL
select 31,6,2023,12,244,439200 UNION ALL
select 32,1,2023,12,350,629650 UNION ALL
select 33,2,2023,12,349,732551 UNION ALL
select 34,7,2024,1,10,500 UNION ALL
select 35,7,2024,2,5,300 UNION ALL
select 36,8,2024,1,30,300 UNION ALL
select 37,8,2024,2,50,1500;


set identity_insert dbo.sales on;
