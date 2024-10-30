package br.com.lbomfim.encurtador.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lbomfim.encurtador.entities.URL;
import br.com.lbomfim.encurtador.repository.URLRepository;

@Service
public class URLService {

    @Autowired
    private URLRepository urlRepository;

    public URL encurtarURL(String originalURL) {
        String shortURL = gerarURLCurta();
        LocalDateTime dataExpiracao = LocalDateTime.now().plusDays(7); // Define expiração em 7 dias
        URL url = new URL(null, originalURL, shortURL, dataExpiracao);
        return urlRepository.save(url);
    }

    public String buscarOriginalURL(String shortURL) {
        Optional<URL> url = urlRepository.findByShortURL(shortURL);
        return url.map(URL::getOriginalURL).orElse(null);
    }

    private String gerarURLCurta() {
        Random random = new Random();
        int leftLimit = 48; // Número '0'
        int rightLimit = 122; // Letra 'z'
        int targetStringLength = 6;
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // Apenas números e letras
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}