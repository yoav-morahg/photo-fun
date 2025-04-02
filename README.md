# Photo Fun
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)](#)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=fff)](#)
[![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)](#)
## Description
A Photo Album API which allows you to upload photos to a database and manage them.
- Allows uploading images to a database
- Allows for archiving/unarchiving images
- Allows for retrieval of all images, all unarchived images, all archived images, or a single image.
- Allows for preview of an image file without saving it.

## Getting Started

- This is a Spring Boot server application configured to run with a PostgreSQL datsbase.

- It can be started by running the ``MyPhotoAlbumApplication`` class.

- Before running the code, you must:
  - Create a PostgreSQL database called ``my_photo_album_db`` (you may configure a different name in the ``application.properties``file.)
  - The database must use the default ``postgres`` user and the ``postgres1`` password. (you may configure these differently in the ``application.properties``file.)
  - In order to generate the database tables, you must modify a couple of properties in the ``application.properties``file.

    - ``spring.jpa.generate-ddl=true``
    - ``spring.jpa.hibernate.ddl-auto=create``
  
  - MAKE SURE TO SET THESE BACK TO THESE VALUES AFTER THE FIRST RUN IF YOU DON'T WANT YOUR DATABASE RECREATED ON EVERY RUN!!!!
  -
      - ``spring.jpa.generate-ddl=false``
      - ``spring.jpa.hibernate.ddl-auto=validate``
  - The server currently runs on port ``9000``. (you may configure a different port in the ``application.properties``file.)

## Future Enhancements

I am working on this app in my spare time so it is currently not where I want it to be. I plan to:
 
   - Refactor some of the code (there are comments on this in the code)
   - Add Spring Security to allow for different users and require credentials.
   - Allow users to create multiple personal albums using any of the photos in their gallery.
   - Add some more unit tests. I generally code via TDD but this was something that I was playing with which turned into a project. I plan to fill in current testing gaps and work via TDD on future development.
   - Add Swagger to generate API documentation.

*Copyright &copy; 2025 Yoav Morahg*
