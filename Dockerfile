FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=build/libs/wishlist-0.0.1-SNAPSHOT.jar
ENV PORT 8080
EXPOSE $PORT
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]