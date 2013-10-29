<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd" version="1.0">

    <persistence-unit name="darempredou" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <!-- $package.name$ -->
$package.entities:{entity|
        <class>$defaultPackageName$.domain.$entity.name$</class>
}$

        <properties>
            <property name="javax.persistence.jdbc.driver" binaryValue="org.postgresql.Driver"/>
            <property name="javax.persistence.jdbc.url" binaryValue="jdbc:postgresql://localhost:5432/darempredoù"/>
            <property name="javax.persistence.jdbc.user" binaryValue="darempredoù"/>
            <property name="javax.persistence.jdbc.password" binaryValue="darempredoù"/>

            <property name="hibernate.dialect" binaryValue="org.hibernate.dialect.ProgressDialect" />
        </properties>
    </persistence-unit>
</persistence>