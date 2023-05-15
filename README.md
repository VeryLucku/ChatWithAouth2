# ChatWithAouth2
Simple project with two services: authorization service to get JWT token via authorization code which you get using autentification with OpenID and chat restapi service using Spring framwork (Spring boot, Spring MVC, Spring security) and tools to work with PostgreSql (driver and JDBC starter, without using spring data to remember sql requests) that provides operations to work with chats, messagies from it and participiants. JWT token contain information about username and scopes, which define opperations that user can do.

The api have these opportunities:
  1. Create, delete chats
  2. Create, delete messages into chats
  3. Join, left chat and change chat role by owner (three roles are available: OWNER, ADMIN, MEMBER)
  4. Delete people from chat by admins or owners
