package com.helpdesk.api.repository.commentary;

import com.helpdesk.api.model.commentary.SupportComment;
import com.helpdesk.api.model.enums.commentary.CommentStatus;
import com.helpdesk.api.model.entry.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para la entidad SupportComment
 * Hereda operaciones comunes de CommentRepository
 * Proporciona búsquedas específicas para comentarios de soporte
 * 
 * SupportComment incluye:
 * - isVisibleToUser : Indicador de visibilidad para el usuario final (true = visible, false = interno/oculto)
 * - status : Estado del comentario (heredado de Comment)
 * - author : Autor del comentario (heredado de Comment)
 * - description : Descripción (heredado de Comment)
 * - createDate : Fecha de creación (heredado de Comment)
 * 
 * Métodos heredados de CommentRepository:
 * - save(SupportComment) : Guardar comentario de soporte
 * - deleteById(String) : Eliminar por ID
 * - findById(String) : Obtener comentario específico
 * - findAll() : Obtener todos
 * - findByAuthor(Profile) : Comentarios de un autor
 * - findByCreateDateBetween(...) : Rango de fechas
 * - countByAuthor(Profile) : Contar por autor
 */
@Repository
public interface SupportCommentRepository extends CommentRepository {

    /**
     * Buscar comentarios de soporte por visibilidad al usuario
     * @param isVisibleToUser : true para visibles, false para internos (solo admin/support)
     * @return Lista de comentarios visibles o invisibles según el parámetro
     */
    List<SupportComment> findByIsVisibleToUser(boolean isVisibleToUser);

    /**
     * Buscar comentarios de soporte visibles al usuario (público)
     * @return Lista de comentarios visibles al usuario final
     */
    List<SupportComment> findByIsVisibleToUserTrue();

    /**
     * Buscar comentarios de soporte NO visibles al usuario (internos/admin)
     * @return Lista de comentarios internos (solo para admin/support)
     */
    List<SupportComment> findByIsVisibleToUserFalse();

    /**
     * Buscar comentarios de soporte por visibilidad Y estado (búsqueda combinada)
     * @param isVisibleToUser : true para visibles, false para internos
     * @param status : Estado del comentario (ACTIVE, HIDDEN, ARCHIVED)
     * @return Lista de comentarios que cumplen ambas condiciones
     */
    List<SupportComment> findByIsVisibleToUserAndStatus(boolean isVisibleToUser, CommentStatus status);

    /**
     * Buscar comentarios de soporte por visibilidad Y autor (búsqueda combinada)
     * @param isVisibleToUser : true para visibles, false para internos
     * @param author : Perfil del autor
     * @return Lista de comentarios que cumplen ambas condiciones
     */
    List<SupportComment> findByIsVisibleToUserAndAuthor(boolean isVisibleToUser, Profile author);

    /**
     * Buscar comentarios de soporte por visibilidad, estado Y autor (búsqueda triple)
     * @param isVisibleToUser : true para visibles, false para internos
     * @param status : Estado del comentario
     * @param author : Perfil del autor
     * @return Lista de comentarios que cumplen las tres condiciones
     */
    List<SupportComment> findByIsVisibleToUserAndStatusAndAuthor(boolean isVisibleToUser, CommentStatus status, Profile author);

    /**
     * Contar comentarios de soporte por visibilidad
     * @param isVisibleToUser : true para visibles, false para internos
     * @return Cantidad de comentarios visibles o internos
     */
    long countByIsVisibleToUser(boolean isVisibleToUser);

    /**
     * Contar comentarios de soporte por visibilidad Y estado
     * @param isVisibleToUser : true para visibles, false para internos
     * @param status : Estado del comentario
     * @return Cantidad de comentarios que cumplen ambas condiciones
     */
    long countByIsVisibleToUserAndStatus(boolean isVisibleToUser, CommentStatus status);

    /**
     * Contar comentarios de soporte por visibilidad Y autor
     * @param isVisibleToUser : true para visibles, false para internos
     * @param author : Perfil del autor
     * @return Cantidad de comentarios que cumplen ambas condiciones
     */
    long countByIsVisibleToUserAndAuthor(boolean isVisibleToUser, Profile author);

    /**
     * Verificar si existe al menos un comentario interno (no visible al usuario)
     * @return true si existe al menos uno interno, false en caso contrario
     */
    boolean existsByIsVisibleToUserFalse();

    /**
     * Contar total de comentarios internos (no visibles para el usuario final)
     * @return Cantidad total de comentarios internos
     */
    long countByIsVisibleToUserFalse();

    /**
     * Contar total de comentarios visibles para el usuario final
     * @return Cantidad total de comentarios públicos/visibles
     */
    long countByIsVisibleToUserTrue();
}

