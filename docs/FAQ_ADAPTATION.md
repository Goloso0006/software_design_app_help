# Adaptación del módulo FAQ al modelo actual

## Objetivo
Permitir que un administrador publique una pregunta/respuesta en una sección global, de forma **anónima**.

## Modelo propuesto

### `TicketFaq`
Entidad pública de FAQ con:
- `question : String`
- `answer : String`

Relaciones internas:
- `ticket : Ticket`
- `selectedComment : SupportComment`

> La vista pública solo expone `question` y `answer`.

### Origen de la información
- **Question**: se toma de `Ticket.title`; si está vacío, se usa `Ticket.description`.
- **Answer**: se toma de `SupportComment.description`.

## Servicio

### `FAQService.publishToFAQ(ticket : Ticket, bestResponse : SupportComment) : TicketFaq`

Flujo:
1. Valida que el ticket exista.
2. Valida que la respuesta seleccionada pertenezca al mismo ticket.
3. Construye o actualiza el `TicketFaq`.
4. Guarda el FAQ como entrada pública y anónima.

## Adaptación respecto al diagrama original
- `Commentary` se adaptó al modelo actual como `SupportComment`.
- El FAQ no guarda el autor visible públicamente.
- El vínculo `Ticket -> SupportComment` queda interno para validación y auditoría.

## Nota sobre permisos
- La acción debe ejecutarse desde un flujo de administrador.
- Existe una sobrecarga segura en `FAQService` que acepta `admin : Profile` para validar el rol `ADMINISTRATOR`.

