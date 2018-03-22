---
id: server.dev-maven
title: "Developing extension with maven"
date: "2017-10-20"
order: 3500
hide: false
draft: false
---

All Invest plugins are JAR files. The easiest way to construct thes is with Maven.

## Setting up the project

We will call our plugin `invest-myplugin`.

Create a new maven project, either using yourIDE or manually. You will have at least the structure:

``` 
pom.xml
src/
  main/
    java/
    resources/
  test/
    java/
    resources/
```

## Setting up the pom

Firstly for the pom, lets add the version information as a property which takes it easier to manage:

```xml
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
    <maven.compiler.target>${java.version}</maven.compiler.target>
    <maven.compiler.source>${java.version}</maven.compiler.source>

    <!-- Dependencies as per Invest version -->
    <invest-java.version>1.0-SNAPSHOT</invest-java.version>
  </properties>
```

Invest uses Spring Boot under the covers, and in order to help manage dependency versions Invest provides a BOM. We'll add that to the pom:

```xml
  <dependencyManagement>
    <dependencies>

      <!-- Invest -->
      <dependency>
        <groupId>io.committed.invest</groupId>
        <artifactId>invest-dependencies</artifactId>
        <version>${invest-java.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
```

Whilst we don't generally use non-production versions of Spring, you can add these repositories to ensure that you can pick the any version of Invest without worrying about the underlying Spring versions we are using (at least from a dependency resolution perspective).

```xml
   <repositories>
    <repository>
      <id>spring-snapshots</id>
      <name>Spring Snapshots</name>
      <url>https://repo.spring.io/snapshot</url>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
    </repository>
    <repository>
      <id>spring-milestones</id>
      <name>Spring Milestones</name>
      <url>https://repo.spring.io/milestone</url>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>
  </repositories>

  <pluginRepositories>
    <pluginRepository>
      <id>spring-snapshots</id>
      <name>Spring Snapshots</name>
      <url>https://repo.spring.io/snapshot</url>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
    </pluginRepository>
    <pluginRepository>
      <id>spring-milestones</id>
      <name>Spring Milestones</name>
      <url>https://repo.spring.io/milestone</url>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </pluginRepository>
  </pluginRepositories>
```

Finally, in the `pom.xml` create a new dependency on the `io.committed.invest:invest-plugin`:

```xml
  <dependencies>
    <dependency>
      <groupId>io.committed.invest</groupId>
      <artifactId>invest-plugin</artifactId>
    </dependency>
  </dependencies>
```