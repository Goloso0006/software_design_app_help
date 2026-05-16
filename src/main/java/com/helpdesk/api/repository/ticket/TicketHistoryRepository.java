package com.helpdesk.api.repository.ticket;

import com.helpdesk.api.model.ticket.TicketHistory;
import com.helpdesk.api.model.enums.ticket.TicketHistoryAction;
import com.helpdesk.api.model.entry.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para la entidad TicketHistory
 * Proporciona operaciones CRUD y búsquedas para auditoría y trazabilidad de tickets
 * 
 * TicketHistory registra:
 * - date : Fecha y hora de la acción (se genera automáticamente con now())
 * - action : Tipo de acción realizada (COMMENT_ADDED, PRIORITY_CHANGED, AGENT_ASSIGNED, etc.)
 * - description : Descripción detallada de la acción
 * - performedBy : Perfil del usuario que realizó la acción
 * 
 * Métodos heredados de MongoRepository:
 * - save(TicketHistory) : Registrar una acción
 * - deleteById(String) : Eliminar un registro de historial
 * - findById(String) : Obtener un registro específico
 * - findAll() : Obtener todos los registros
 * - delete(TicketHistory) : Eliminar un registro completo
 * - existsById(String) : Verificar si existe un registro
 * - count() : Contar total de registros
 */
@Repository
public interface TicketHistoryRepository extends MongoRepository<TicketHistory, String> {

    /**
     * Buscar todo el historial de un ticket específico
     * @param ticketId : ID del ticket
     * @return Lista de acciones realizadas en ese ticket (ordenadas por fecha)
     */
    List<TicketHistory> findByTicketId(String ticketId);

    /**
     * Buscar acciones de un tipo específico
     * @param action : Tipo de acción (COMMENT_ADDED, PRIORITY_CHANGED, AGENT_ASSIGNED, etc.)
     * @return Lista de registros con ese tipo de acción
     */
    List<TicketHistory> findByAction(TicketHistoryAction action);

    /**
     * Buscar todas las acciones realizadas por un usuario específico
     * @param performedBy : Perfil del usuario que realizó las acciones
     * @return Lista de acciones realizadas por ese usuario
     */
    List<TicketHistory> findByPerformedBy(Profile performedBy);

    /**
     * Buscar acciones en un rango de fechas
     * @param startDate : Fecha inicial
     * @param endDate : Fecha final
     * @return Lista de acciones realizadas entre esas fechas
     */
    List<TicketHistory> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Buscar acciones de un tipo Y en un ticket específico (búsqueda combinada)
     * @param ticketId : ID del ticket
     * @param action : Tipo de acción
     * @return Lista de acciones que cumplen ambas condiciones
     */
    List<TicketHistory> findByTicketIdAndAction(String ticketId, TicketHistoryAction action);

    /**
     * Buscar acciones de un tipo Y realizadas por un usuario (búsqueda combinada)
     * @param action : Tipo de acción
     * @param performedBy : Perfil del usuario
     * @return Lista de acciones que cumplen ambas condiciones
     */
    List<TicketHistory> findByActionAndPerformedBy(TicketHistoryAction action, Profile performedBy);

    /**
     * Buscar acciones de un ticket Y realizadas por un usuario (búsqueda combinada)
     * @param ticketId : ID del ticket
     * @param performedBy : Perfil del usuario
     * @return Lista de acciones que cumplen ambas condiciones
     */
    List<TicketHistory> findByTicketIdAndPerformedBy(String ticketId, Profile performedBy);

    /**
     * Buscar acciones de un ticket, tipo Y usuario específico (búsqueda triple)
     * @param ticketId : ID del ticket
     * @param action : Tipo de acción
     * @param performedBy : Perfil del usuario
     * @return Lista de acciones que cumplen las tres condiciones
     */
    List<TicketHistory> findByTicketIdAndActionAndPerformedBy(String ticketId, TicketHistoryAction action, Profile performedBy);

    /**
     * Contar acciones en un ticket específico
     * @param ticketId : ID del ticket
     * @return Cantidad total de acciones en ese ticket
     */
    long countByTicketId(String ticketId);

    /**
     * Contar acciones de un tipo específico
     * @param action : Tipo de acción
     * @return Cantidad total de acciones de ese tipo
     */
    long countByAction(TicketHistoryAction action);

    /**
     * Contar acciones realizadas por un usuario específico
     * @param performedBy : Perfil del usuario
     * @return Cantidad total de acciones realizadas por ese usuario
     */
    long countByPerformedBy(Profile performedBy);

    /**
     * Contar acciones de un tipo en un ticket específico
     * @param ticketId : ID del ticket
     * @param action : Tipo de acción
     * @return Cantidad de acciones que cumplen ambas condiciones
     */
    long countByTicketIdAndAction(String ticketId, TicketHistoryAction action);

    /**
     * Contar acciones realizadas por un usuario en un ticket específico
     * @param ticketId : ID del ticket
     * @param performedBy : Perfil del usuario
     * @return Cantidad de acciones que cumplen ambas condiciones
     */
    long countByTicketIdAndPerformedBy(String ticketId, Profile performedBy);

    /**
     * Verificar si existe historial para un ticket
     * @param ticketId : ID del ticket
     * @return true si existen registros, false si no hay historial
     */
    boolean existsByTicketId(String ticketId);

    /**
     * Obtener la acción más reciente de un ticket
     * @param ticketId : ID del ticket
     * @return Lista con la última acción (para obtener el primero)
     */
    List<TicketHistory> findByTicketIdOrderByDateDesc(String ticketId);
}

