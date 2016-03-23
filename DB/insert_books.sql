-- create DB with HyperSQL
-- :web_portal/DB$ java -jar ../WebContent/WEB-INF/lib/hsqldb.jar --rcFile sqltool.rc web_portal web_portal.sql

INSERT INTO Book	(DateAdded,				Author,		ISBN,	Pages, 	PublicationDate, 		Publisher, Review,	Summary, 	Title)
			VALUES	('2016-03-01 14:38:00',	'Gama',	0,		0, 		'1995-02-01 14:32:00',	'Addison-Wesley',		'', 'Design Patterns', 	'Design Patterns' );

			
COMMIT;
