FROM finetglazer/alpinejdk17
COPY target/http.jar app.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]