# Meeting Planner
### lancer le ficher docker compose
### excuter le sql qui dans le fichier outils.sql

### Recherche de créneau pour une réunion

- **Endpoint :** `GET /api/v1/reunions/getCreneau/{id}`
- **Description :** Cette API permet de rechercher un créneau horaire disponible pour une réunion en fonction de son identifiant.
- **Paramètres :** `id` - Identifiant de la réunion.
- **Réponse :** Retourne un objet ResponseEntity contenant une optionnelle (Optional) représentant la réunion trouvée.

### Enregistrement d'une nouvelle réunion

- **Endpoint :** `POST /api/v1/reunions`
- **Description :** Cette API permet d'enregistrer une nouvelle réunion en utilisant un objet ReunionDTO.
- **Entrée :** Accepte un objet ReunionDTO dans le corps de la requête.
- **Réponse :** Retourne un objet ResponseEntity contenant la réunion enregistrée, avec un statut 201 CREATED en cas de succès. En cas d'échec, retourne un statut 400 BAD REQUEST.

## Exemples d'utilisation

### Recherche de créneau pour une réunion

```http
GET /api/v1/reunions/getCreneau/1



POST /api/v1/reunions
Content-Type: application/json

{
  "sujet": "  sujet ",
  "nombrePersonne": 10,
  "heureDebut": "2024-01-01T10:00:00",
  "heureFin": "2024-01-01T11:00:00",
  "type": "VC"
}
