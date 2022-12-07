FROM adoptopenjdk/openjdk11

ARG JAR_FILE
ADD target/${JAR_FILE} /dissect-cf/simulator.jar

ENTRYPOINT ["java", "-jar", "/dissect-cf/simulator.jar"]
