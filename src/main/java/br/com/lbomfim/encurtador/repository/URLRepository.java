package br.com.lbomfim.encurtador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lbomfim.encurtador.entities.URL;

public interface URLRepository extends JpaRepository<URL, Long> {
    Optional<URL> findByShortURL(String shortURL);
}