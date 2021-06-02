For event 5, we need to add dependency in pom.xml file which locates in Project Files
Add in between </properties> and </project>
Here's the code :
<dependencies>
        <dependency>
            <groupId>org.jgrapht</groupId>
            <artifactId>jgrapht-core</artifactId>
            <version>1.5.1</version>
        </dependency>
</dependencies>
