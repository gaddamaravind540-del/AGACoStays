# AGA CoStays Branch Service

Application: `branch-service`
Base package: `com.agacostays.branch`
Database: `branch_db`
Port: `8083`

## Responsibility

The source defines this service for cities, hotel branches, branch photos, receptionist contacts and emergency contacts. Its workflow is:

City/branch request -> validation -> branch/photo persistence -> contact retrieval -> public response -> Kafka update event.

## Source API set implemented

- POST /api/root-admin/cities
- GET /api/cities
- GET /api/cities/{cityId}
- PUT /api/root-admin/cities/{cityId}
- DELETE /api/root-admin/cities/{cityId}
- POST /api/root-admin/hotel-branches
- GET /api/hotel-branches
- GET /api/hotel-branches/{branchId}
- GET /api/hotel-branches/city/{cityId}
- PUT /api/root-admin/hotel-branches/{branchId}
- PUT /api/root-admin/hotel-branches/{branchId}/status
- POST /api/root-admin/hotel-branches/{branchId}/photos
- GET /api/hotel-branches/{branchId}/photos
- DELETE /api/root-admin/hotel-branches/{branchId}/photos/{photoId}
- POST /api/manager/hotel-branches/{branchId}/receptionist-contacts
- GET /api/hotel-branches/{branchId}/contacts
- POST /api/manager/hotel-branches/{branchId}/emergency-contacts

The final source document also exposes convenience public paths:
- GET /api/hotel-branches/{branchId}/receptionists
- GET /api/hotel-branches/{branchId}/emergency-contacts
- GET /api/root-admin/hotel-branches
- GET /api/root-admin/hotel-branches/{branchId}/complete-details

## Implementation notes

The source specifies the database field names and service package structure. The exact Java types, storage implementation, local file URL format, JWT parsing details and some enum values are implementation choices.

The source database specification names `hotel_branch_photos`, `receptionist_contacts`, and `emergency_contacts`; the response models are kept as DTOs rather than entities.
