package com.vetcare;

import com.vetcare.model.Especie;
import com.vetcare.repository.EspecieRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VetcareApplication {

    public static void main(String[] args) {
        SpringApplication.run(VetcareApplication.class, args);
    }

    @Bean
    CommandLineRunner cargarEspecies(EspecieRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Especie perro = new Especie();
                perro.setNombre("Canino");
                perro.setDescripcion("Perros");
                repo.save(perro);

                Especie gato = new Especie();
                gato.setNombre("Felino");
                gato.setDescripcion("Gatos");
                repo.save(gato);
            }
        };
    }
}
