# rapidpm-microservice-examples

[![Join the chat at https://gitter.im/RapidPM/rapidpm-microservice-examples](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/RapidPM/rapidpm-microservice-examples?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Here you will find a few examples how to use the rapidpm-microservice project/framework.

build:
+ master:
[![Build Status](https://travis-ci.org/RapidPM/rapidpm-microservice-examples.svg?branch=master)](https://travis-ci.org/RapidPM/rapidpm-microservice-examples)
+ develop:
[![Build Status](https://travis-ci.org/RapidPM/rapidpm-microservice-examples.svg?branch=develop)](https://travis-ci.org/RapidPM/rapidpm-microservice-examples)

branch
master:
[![Dependency Status](https://www.versioneye.com/user/projects/55a3a45e3239390021000540/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55a3a45e3239390021000540)

develop:
[![Dependency Status](https://www.versioneye.com/user/projects/55a3a44f32393900180005b2/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55a3a44f32393900180005b2)


# Activating Kotlin
If you want to use Kotlin you have to activated it in your pom.xml.
The compile plugin defined in the rapidpm-dependencies is prepared to
handle the live-cycle. Additionally you have to add the dep into your pom.xml

```
      <plugin>
        <artifactId>kotlin-maven-plugin</artifactId>
        <groupId>org.jetbrains.kotlin</groupId>
        <version>${kotlin.version}</version>

        <executions>
          <execution>
            <id>compile</id>
            <phase>process-sources</phase>
            <goals>
              <goal>compile</goal>
            </goals>
          </execution>

          <execution>
            <id>test-compile</id>
            <phase>process-test-sources</phase>
            <goals>
              <goal>test-compile</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```
and

```
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-stdlib</artifactId>
    </dependency>
```









##example001
[![Dependency Status](https://www.versioneye.com/user/projects/55bb2961653762001a0019bb/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55bb2961653762001a0019bb)
here we could see how to create a REST and a Servlet Service.

##example002
[![Dependency Status](https://www.versioneye.com/user/projects/55bb23976537620020001996/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55bb23976537620020001996)
An example how to create e REST Service and using Kotlin for the data model.

##example003
Show an empty Service

##example004

##example005
[![Dependency Status](https://www.versioneye.com/user/projects/55bb2399653762002000199b/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55bb2399653762002000199b)
