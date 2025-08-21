# Resmi bir Java Runtime imajını temel olarak kullanın
FROM openjdk:21-jdk-slim

# Docker imajı içindeki çalışma dizinini belirleyin
WORKDIR /app

# Proje JAR dosyasını kopyalayın.
# 'target/demo-0.0.1-SNAPSHOT.jar' kısmını kendi projenizin JAR dosya yolu ve adıyla değiştirin.
# Genellikle Spring Boot projesinin JAR dosyası "target" dizini altında bulunur ve adı "proje-adi-versiyon.jar" formatındadır.
COPY target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Uygulamanızın varsayılan portunu açın (örneğin, Spring Boot için 8080)
EXPOSE 8080

# Uygulamayı çalıştırın
CMD ["java", "-jar", "app.jar"]