FROM eclipse-temurin:21.0.3_9-jdk-jammy as build

COPY . .
RUN ./gradlew installDist --no-daemon

FROM eclipse-temurin:21.0.3_9-jdk-jammy as runtime

WORKDIR /app

COPY --from=build /build/install/blog/bin/blog bin/blog
COPY --from=build /build/install/blog/lib/ lib/

ENTRYPOINT ["bin/blog"]
