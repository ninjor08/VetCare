package com.vetcare.controller;

import com.vetcare.model.Usuario;
import com.vetcare.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class UsuarioController {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarios, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.encoder = encoder;
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String guardar(@ModelAttribute Usuario usuario) {

        if (usuarios.findByEmail(usuario.getEmail()) != null) {
            return "redirect:/registro?error";
        }

        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuarios.save(usuario);

        return "redirect:/login";
    }
}
