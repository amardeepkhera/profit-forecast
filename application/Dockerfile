FROM gradle:8.5-jdk21 AS BUILDER

RUN mkdir /app
COPY gradle /app/gradle
COPY src /app/src
COPY build.gradle /app
COPY settings.gradle /app

RUN --mount=type=cache,target=/home/gradle/.gradle \
  cd /app \
  && gradle build -x test \
  && java -Djarmode=layertools -jar build/libs/*.jar extract

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=BUILDER /app/dependencies ./
COPY --from=BUILDER /app/spring-boot-loader ./
COPY --from=BUILDER /app/snapshot-dependencies ./
COPY --from=BUILDER /app/application ./

ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]
