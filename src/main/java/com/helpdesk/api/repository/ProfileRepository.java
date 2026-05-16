package com.helpdesk.api.repository;

import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.entry.ProfileRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para la entidad Profile
 * Proporciona operaciones CRUD y búsquedas personalizadas por rol, nombre y email
 * 
 * Métodos heredados de MongoRepository:
 * - save(Profile) : Crear o actualizar un perfil
 * - deleteById(String) : Eliminar un perfil por su ID
 * - findById(String) : Obtener un perfil específico
 * - findAll() : Obtener todos los perfiles
 * - delete(Profile) : Eliminar un perfil completo
 * - existsById(String) : Verificar si existe un perfil
 * - count() : Contar total de perfiles
 */
@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    /**
     * Buscar un perfil por email (búsqueda única)
     * @param email : Email del perfil
     * @return Optional con el perfil si existe, vacío si no
     */
    Optional<Profile> findByEmail(String email);

    /**
     * Buscar todos los perfiles con un rol específico
     * @param role : Rol del perfil (USER, ADMINISTRATOR, SUPPORT_AGENT)
     * @return Lista de perfiles con el rol especificado
     */
    List<Profile> findByRole(ProfileRoles role);

    /**
     * Buscar perfiles por nombre
     * @param name : Nombre del perfil (pueden haber múltiples coincidencias)
     * @return Lista de perfiles que coinciden con el nombre
     */
    List<Profile> findByName(String name);

    /**
     * Buscar perfiles por apellido
     * @param lastName : Apellido del perfil (pueden haber múltiples coincidencias)
     * @return Lista de perfiles que coinciden con el apellido
     */
    List<Profile> findByLastName(String lastName);

    /**
     * Buscar perfiles por nombre Y apellido (búsqueda combinada)
     * @param name : Nombre del perfil
     * @param lastName : Apellido del perfil
     * @return Lista de perfiles que coinciden con nombre y apellido
     */
    List<Profile> findByNameAndLastName(String name, String lastName);

    /**
     * Buscar perfiles por rol Y nombre (búsqueda combinada)
     * @param role : Rol del perfil
     * @param name : Nombre del perfil
     * @return Lista de perfiles que cumplen ambas condiciones
     */
    List<Profile> findByRoleAndName(ProfileRoles role, String name);

    /**
     * Buscar perfiles por rol Y apellido (búsqueda combinada)
     * @param role : Rol del perfil
     * @param lastName : Apellido del perfil
     * @return Lista de perfiles que cumplen ambas condiciones
     */
    List<Profile> findByRoleAndLastName(ProfileRoles role, String lastName);

    /**
     * Verificar si existe un perfil con un email específico
     * @param email : Email del perfil
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);

    /**
     * Contar perfiles por rol
     * @param role : Rol del perfil
     * @return Cantidad de perfiles con ese rol
     */
    long countByRole(ProfileRoles role);

    /**
     * Contar perfiles por nombre
     * @param name : Nombre del perfil
     * @return Cantidad de perfiles con ese nombre
     */
    long countByName(String name);
}

