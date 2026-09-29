# AGA CoStays Branch Service API Samples

Base URL: http://localhost:8083

## List active cities
GET /api/cities

## Get city
GET /api/cities/1

## Create city
POST /api/root-admin/cities
Authorization: Bearer <ROOT_ADMIN_TOKEN>
Content-Type: application/json

{
  "cityName": "Hyderabad",
  "state": "Telangana",
  "country": "India"
}

## List hotel branches
GET /api/hotel-branches

## Get branches by city
GET /api/hotel-branches/city/1

## Create hotel branch
POST /api/root-admin/hotel-branches
Authorization: Bearer <ROOT_ADMIN_TOKEN>

{
  "cityId": 1,
  "branchName": "AGA CoStays Indiranagar",
  "address": "Indiranagar, Bengaluru",
  "landmark": "Near Metro",
  "latitude": 12.9784,
  "longitude": 77.6408,
  "phone": "0801234567",
  "email": "indiranagar@agacostays.com",
  "description": "Premium city branch"
}

## Upload branch photo
POST /api/root-admin/hotel-branches/1/photos
Authorization: Bearer <ROOT_ADMIN_OR_MANAGER_TOKEN>
Content-Type: multipart/form-data

file=<image>
caption=Front elevation
photoType=EXTERIOR
primaryPhoto=true

## List branch photos
GET /api/hotel-branches/1/photos

## Add receptionist contact
POST /api/manager/hotel-branches/1/receptionist-contacts
Authorization: Bearer <MANAGER_TOKEN>

{
  "staffId": 12,
  "phone": "9876543210",
  "alternatePhone": "9123456789",
  "email": "reception@agacostays.com",
  "shift": "MORNING",
  "availableFrom": "08:00:00",
  "availableTo": "16:00:00",
  "purpose": "Front desk"
}

## Get branch contacts
GET /api/hotel-branches/1/contacts

## Add emergency contact
POST /api/manager/hotel-branches/1/emergency-contacts
Authorization: Bearer <MANAGER_TOKEN>

{
  "contactName": "Branch Security",
  "phone": "9999999999",
  "alternatePhone": "9888888888",
  "email": "security@agacostays.com",
  "purpose": "SECURITY"
}

## Get emergency contacts
GET /api/hotel-branches/1/emergency-contacts

## Root admin complete branch details
GET /api/root-admin/hotel-branches/1/complete-details
Authorization: Bearer <ROOT_ADMIN_TOKEN>
