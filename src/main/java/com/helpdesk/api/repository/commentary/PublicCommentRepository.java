package com.helpdesk.api.repository.commentary;

import com.helpdesk.api.model.commentary.PublicComment;
import com.helpdesk.api.model.enums.commentary.CommentStatus;
import com.helpdesk.api.model.entry.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para la entidad PublicComment
 * Hereda operaciones comunes de CommentRepository
 * Proporciona búsquedas específicas para comentarios públicos
 * 
 * PublicComment incluye:
 * - likes : Contador de "likes" (likes = 0 por defecto)
 * - isReported : Indicador de reporte (isReported = false por defecto)
 * - status : Estado del comentario (ACTIVE, HIDDEN, ARCHIVED)
 * 
 * Métodos heredados de CommentRepository:
 * - save(PublicComment) : Guardar comentario público
 * - deleteById(String) : Eliminar por ID
 * - findById(String) : Obtener comentario específico
 * - findAll() : Obtener todos
 * - findByAuthor(Profile) : Comentarios de un autor
 * - findByCreateDateBetween(...) : Rango de fechas
 * - countByAuthor(Profile) : Contar por autor
 */
@Repository
public interface PublicCommentRepository extends CommentRepository {

    /**
     * Buscar comentarios públicos por estado
     * @param status : Estado del comentario (ACTIVE, HIDDEN, ARCHIVED)
     * @return Lista de comentarios con ese estado
     */
    List<PublicComment> findByStatus(CommentStatus status);

    /**
     * Buscar comentarios públicos por estado Y autor (búsqueda combinada)
     * @param status : Estado del comentario
     * @param author : Perfil del autor
     * @return Lista de comentarios que cumplen ambas condiciones
     */
    List<PublicComment> findByStatusAndAuthor(CommentStatus status, Profile author);

    /**
     * Buscar comentarios públicos que han sido reportados
     * @param isReported : true para reportados, false para no reportados
     * @return Lista de comentarios reportados o no reportados
     */
    List<PublicComment> findByIsReported(boolean isReported);

    /**
     * Buscar comentarios públicos con más de X likes
     * @param likes : Cantidad mínima de likes
     * @return Lista de comentarios con más likes que el especificado
     */
    List<PublicComment> findByLikesGreaterThan(int likes);

    /**
     * Buscar comentarios públicos con más de X likes Y con un estado específico
     * @param likes : Cantidad mínima de likes
     * @param status : Estado del comentario
     * @return Lista de comentarios populares con ese estado
     */
    List<PublicComment> findByLikesGreaterThanAndStatus(int likes, CommentStatus status);

    /**
     * Buscar comentarios públicos por estado Y si fueron reportados (búsqueda combinada)
     * @param status : Estado del comentario
     * @param isReported : true para reportados, false para no reportados
     * @return Lista de comentarios que cumplen ambas condiciones
     */
    List<PublicComment> findByStatusAndIsReported(CommentStatus status, boolean isReported);

    /**
     * Contar comentarios públicos por estado
     * @param status : Estado del comentario
     * @return Cantidad de comentarios con ese estado
     */
    long countByStatus(CommentStatus status);

    /**
     * Contar comentarios públicos que han sido reportados
     * @param isReported : true para reportados, false para no reportados
     * @return Cantidad de comentarios reportados o no reportados
     */
    long countByIsReported(boolean isReported);

    /**
     * Contar comentarios públicos por estado Y si fueron reportados
     * @param status : Estado del comentario
     * @param isReported : true para reportados, false para no reportados
     * @return Cantidad de comentarios que cumplen ambas condiciones
     */
    long countByStatusAndIsReported(CommentStatus status, boolean isReported);

    /**
     * Verificar si existe un comentario público que ha sido reportado
     * @return true si existe al menos uno reportado, false en caso contrario
     */
    boolean existsByIsReportedTrue();

    /**
     * Obtener el comentario público más popular (con más likes)
     * @return Lista ordenada por likes descending (una sola posición)
     */
    List<PublicComment> findByOrderByLikesDesc();
}

