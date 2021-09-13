FROM openjdk:11
COPY . /StocksPrice
WORKDIR /StocksPrice
RUN ./gradlew build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/StocksPrice.jar"]