W celu uruchomienia:

1. JDK17,
2. W IntelliJ, w opcjach konfiguracji zaznaczyć `Add dependencies with "provided" scope to classpath`,
3. Przy pierwszym uruchomieniu z nową strukturą bazy danych, wywołaj skrypt `data.sql`, a następnie w pliku `application.properties` zmień `spring.jpa.hibernate.ddl-auto`
   na `validate`
