package com.example.demo.dto;

// Bu sınıf, frontend'den gelen proje oluşturma isteğinin JSON yapısını temsil eder.
// Sadece projenin temel alanlarını içerir, ilişkili koleksiyonları (örn. workers) içermez.
public class ProjectCreateRequest {

    private String name;
    private String description;
    private String project_status; // Frontend'den gelen JSON'daki anahtar isimleriyle eşleşmeli

    // Varsayılan boş kurucu metot (Jackson için gereklidir)
    public ProjectCreateRequest() {}

    // Tüm alanları alan kurucu metot (isteğe bağlı, kolaylık sağlar)
    public ProjectCreateRequest(String name, String description, String project_status) {
        this.name = name;
        this.description = description;
        this.project_status = project_status;
    }

    // --- GETTER METOTLARI ---
    // Bu metotlar, Controller'da 'request.getName()' gibi çağrılar yapıldığında
    // objenin içindeki 'name' alanının değerini döndürmesini sağlar.
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProject_status() {
        return project_status;
    }

    // --- SETTER METOTLARI ---
    // Bu metotlar, Jackson'ın gelen JSON verisini objenin ilgili alanlarına atamasını sağlar.
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    // Debugging ve loglama için objenin okunabilir bir temsilini sağlar.
    @Override
    public String toString() {
        return "ProjectCreateRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", project_status='" + project_status + '\'' +
                '}';
    }
}
