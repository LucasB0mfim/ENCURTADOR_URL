package br.com.lbomfim.encurtador.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lbomfim.encurtador.entities.URL;
import br.com.lbomfim.encurtador.service.URLService;

@RestController
@RequestMapping("/api")
public class URLController {

    @Autowired
    private URLService urlService;

    @PostMapping("/encurtar")
    public ResponseEntity<URL> encurtarURL(@RequestBody Map<String, String> request) {
        String originalURL = request.get("urlOriginal");
        URL shortenedUrl = urlService.encurtarURL(originalURL);
        return ResponseEntity.ok(shortenedUrl);
    }

    @GetMapping("/{shortURL}")
    public ResponseEntity<Void> redirecionarParaOriginalURL(@PathVariable String shortURL) {
        String originalURL = urlService.buscarOriginalURL(shortURL);
        return originalURL != null ?
                ResponseEntity.status(302).header("Location", originalURL).build() :
                ResponseEntity.notFound().build();
    }
}