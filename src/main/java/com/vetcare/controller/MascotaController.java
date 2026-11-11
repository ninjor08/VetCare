
package com.vetcare.controller;

import com.vetcare.model.Mascota;
import com.vetcare.repository.MascotaRepository;
import com.vetcare.repository.EspecieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MascotaController {

    private final MascotaRepository mascotas;
    private final EspecieRepository especies;

    public MascotaController(MascotaRepository mascotas, EspecieRepository especies) {
        this.mascotas = mascotas;
        this.especies = especies;
    }

    @GetMapping("/mascotas")
    public String listar(Model model) {
        model.addAttribute("mascotas", mascotas.findAll());
        return "mascotas";
    }

    @GetMapping("/mascotas/nueva")
    public String nueva(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("especies", especies.findAll());
        return "formulario";
    }

    @PostMapping("/mascotas/guardar")
    public String guardar(@ModelAttribute Mascota mascota,
                          @RequestParam Long especieId) {

        System.out.println("ID recibido: " + mascota.getId());

        mascota.setEspecie(especies.findById(especieId).orElseThrow());

        if (mascota.getId() != null) {
            Mascota existente = mascotas.findById(mascota.getId()).orElseThrow();

            existente.setNombre(mascota.getNombre());
            existente.setEdad(mascota.getEdad());
            existente.setRaza(mascota.getRaza());
            existente.setEspecie(mascota.getEspecie());

            mascotas.save(existente);
        } else {
            mascotas.save(mascota);
        }

        return "redirect:/mascotas";
    }

    @GetMapping("/mascotas/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotas.findById(id).orElseThrow());
        model.addAttribute("especies", especies.findAll());
        return "formulario";
    }

    @PostMapping("/mascotas/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        mascotas.deleteById(id);
        return "redirect:/mascotas";
    }
}
