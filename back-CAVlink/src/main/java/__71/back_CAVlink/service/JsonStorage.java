package __71.back_CAVlink.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * File: JsonStorage.java
 * Servicio: Maneja lectura y escritura eficiente del archivo cavlink.json
 * Funcionalidad: Provee operaciones seguras con lock para usuarios, productos y pedidos
 */
@Service
public class JsonStorage {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);
    private File dbFile;

    @PostConstruct
    public void init() throws IOException {
        // Localización del archivo en resources/data/cavlink.json
        dbFile = Paths.get("src", "main", "resources", "data", "cavlink.json").toFile();
        if (!dbFile.exists()) {
            // Crear estructura inicial si no existe
            rwLock.writeLock().lock();
            try {
                mapper.writeValue(dbFile, new RootData());
            } finally { rwLock.writeLock().unlock(); }
        }
    }

    // Clase interna que representa la raíz del JSON
    public static class RootData {
        public List<__71.back_CAVlink.model.Usuario> usuarios = new ArrayList<>();
        public List<__71.back_CAVlink.model.Producto> productos = new ArrayList<>();
        public List<__71.back_CAVlink.model.Pedido> pedidos = new ArrayList<>();
    }

    public RootData readAll() throws IOException {
        rwLock.readLock().lock();
        try {
            return mapper.readValue(dbFile, RootData.class);
        } finally { rwLock.readLock().unlock(); }
    }

    public void writeAll(RootData data) throws IOException {
        rwLock.writeLock().lock();
        try {
            mapper.writeValue(dbFile, data);
        } finally { rwLock.writeLock().unlock(); }
    }
}
