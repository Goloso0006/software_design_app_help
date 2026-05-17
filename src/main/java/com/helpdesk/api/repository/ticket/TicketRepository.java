package com.helpdesk.api.repository.ticket;

import com.helpdesk.api.model.ticket.Ticket;
import com.helpdesk.api.model.entry.Profile;
import com.helpdesk.api.model.enums.ticket.TicketCategories;
import com.helpdesk.api.model.enums.ticket.TicketPriorities;
import com.helpdesk.api.model.enums.ticket.TicketStates;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para la entidad Ticket
 * Proporciona operaciones CRUD y búsquedas personalizadas
 * 
 * Métodos heredados de MongoRepository:
 * - save(Ticket) : Crear o actualizar un ticket
 * - deleteById(String) : Eliminar un ticket por su ID
 * - findById(String) : Obtener un ticket específico
 * - findAll() : Obtener todos los tickets
 * - delete(Ticket) : Eliminar un ticket completo
 * - existsById(String) : Verificar si existe un ticket
 * - count() : Contar total de tickets
 */
@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    /**
     * Buscar tickets por estado
     * @param state : Estado del ticket (OPEN, IN_PROGRESS, CLOSED)
     * @return Lista de tickets con el estado especificado
     */
    List<Ticket> findByState(TicketStates state);

    /**
     * Buscar tickets por prioridad
     * @param priority : Prioridad del ticket (LOW, MEDIUM, HIGH)
     * @return Lista de tickets con la prioridad especificada
     */
    List<Ticket> findByPriority(TicketPriorities priority);

    /**
     * Buscar tickets por categoría
     * @param category : Categoría del ticket (ACADEMIC, DISCIPLINARY, CONCERN)
     * @return Lista de tickets con la categoría especificada
     */
    List<Ticket> findByCategory(TicketCategories category);

    /**
     * Buscar tickets por estado Y prioridad (búsqueda combinada)
     * @param state : Estado del ticket
     * @param priority : Prioridad del ticket
     * @return Lista de tickets que cumplen ambas condiciones
     */
    List<Ticket> findByStateAndPriority(TicketStates state, TicketPriorities priority);

    /**
     * Buscar tickets por estado Y categoría (búsqueda combinada)
     * @param state : Estado del ticket
     * @param category : Categoría del ticket
     * @return Lista de tickets que cumplen ambas condiciones
     */
    List<Ticket> findByStateAndCategory(TicketStates state, TicketCategories category);

    /**
     * Buscar tickets por prioridad Y categoría (búsqueda combinada)
     * @param priority : Prioridad del ticket
     * @param category : Categoría del ticket
     * @return Lista de tickets que cumplen ambas condiciones
     */
    List<Ticket> findByPriorityAndCategory(TicketPriorities priority, TicketCategories category);

    /**
     * Contar tickets por estado
     * @param state : Estado del ticket
     * @return Cantidad de tickets en ese estado
     */
    long countByState(TicketStates state);

    /**
     * Contar tickets por prioridad
     * @param priority : Prioridad del ticket
     * @return Cantidad de tickets con esa prioridad
     */
    long countByPriority(TicketPriorities priority);

    /**
     * Verificar si existe un ticket con un título específico
     * @param title : Título del ticket
     * @return true si existe, false en caso contrario
     */
    boolean existsByTitle(String title);

    /**
     * Buscar tickets creados por un perfil específico
     * @param createdBy : Perfil creador
     * @return Lista de tickets creados por ese perfil
     */
    List<Ticket> findByCreatedBy(Profile createdBy);
}