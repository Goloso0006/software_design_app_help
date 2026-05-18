package com.helpdesk.api.repository.commentary;

import com.helpdesk.api.model.commentary.Comment;
import com.helpdesk.api.model.entry.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository abstracto para la entidad Comment
 * Proporciona operaciones CRUD y búsquedas comunes para todos los tipos de comentarios
 * 
 * Esta interfaz es la base para:
 * - PublicCommentRepository : Comentarios públicos del usuario
 * - SupportCommentRepository : Comentarios de soporte (internos/públicos)
 * 
 * Métodos heredados de MongoRepository:
 * - save(Comment) : Guardar un comentario
 * - deleteById(String) : Eliminar un comentario por su ID
 * - findById(String) : Obtener un comentario específico
 * - findAll() : Obtener todos los comentarios
 * - delete(Comment) : Eliminar un comentario completo
 * - existsById(String) : Verificar si existe un comentario
 * - count() : Contar total de comentarios
 */
@Repository
public interface CommentRepository<T extends Comment> extends MongoRepository<T, String> {

    /**
     * Buscar todos los comentarios de un autor específico
     * @param author : Perfil del autor
     * @return Lista de comentarios creados por ese autor
     */
    List<T> findByAuthor(Profile author);

    /**
     * Buscar comentarios creados en una fecha específica
     * @param createDate : Fecha de creación
     * @return Lista de comentarios creados en esa fecha
     */
    List<T> findByCreateDate(LocalDateTime createDate);

    /**
     * Buscar comentarios creados en un rango de fechas
     * @param startDate : Fecha inicial
     * @param endDate : Fecha final
     * @return Lista de comentarios creados entre esas fechas
     */
    List<T> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Contar comentarios de un autor específico
     * @param author : Perfil del autor
     * @return Cantidad de comentarios del autor
     */
    long countByAuthor(Profile author);

    /**
     * Verificar si existe un comentario con una descripción específica
     * @param description : Descripción del comentario
     * @return true si existe, false en caso contrario
     */
    boolean existsByDescription(String description);
}

