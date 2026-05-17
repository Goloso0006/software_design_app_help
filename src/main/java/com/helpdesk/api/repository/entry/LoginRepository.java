package com.helpdesk.api.repository.entry;

import com.helpdesk.api.model.entry.Login;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository para la entidad Login
 * Proporciona operaciones CRUD para credenciales de usuario
 * 
 * Métodos heredados de MongoRepository:
 * - save(Login) : Crear o actualizar un login
 * - deleteById(String) : Eliminar un login por su ID
 * - findById(String) : Obtener un login específico
 * - findAll() : Obtener todos los logins
 * - delete(Login) : Eliminar un login completo
 * - existsById(String) : Verificar si existe un login
 * - count() : Contar total de logins
 */
@Repository
public interface LoginRepository extends MongoRepository<Login, String> {

    /**
     * Buscar un login por username (búsqueda única)
     * @param username : Nombre de usuario
     * @return Optional con el login si existe, vacío si no
     */
    java.util.Optional<Login> findByUsername(String username);
}