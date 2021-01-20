Vet Clinic
===
## Get started
First of all clone the repository:
```bash
git clone git@github.com:jmysliv/vet-clinic.git
cd vet-clinic
```
### Run the app using docker compose
1. Build jar file:
```bash
./gradlew build
```
2. Run docker compose:
```bash
docker-compose up
```
### Run the app using gradle
1. Setup database:
```bash
docker-compose --file db.yaml up
```
2. Run the app:
```bash
./gradlew bootrun
```

App will be available at https://localhost:8080

## REST API
### REST paths
+ [/doctors GET](#listing-all-doctors)
+ [/doctors/{id} GET](#getting-doctor)
+ [/doctors POST](#adding-new-doctor)
+ [/customers POST](#adding-new-customer)
+ [/slots GET](#fetching-slots-list)
+ [/slots/{id} GET](#fetching-slot-by-id)
+ [/slots POST](#adding-new-slot)
+ [/slots/doctor/{id}/date/{date} GET](#fetching-slots-by-doctor-and-date)
+ [/appointments POST](#making-an-appointment)
+ [/appointments DELETE]()

### Listing all doctors
```/doctors GET```
 
 Example response:
 ```JSON
{
    "status": "OK",
    "message": "Fetched doctors list",
    "data": [
        {
            "id": 1,
            "name": "name"
        }
    ]
}
 ```

### Getting doctor
```/doctors/{id} GET```

Example response:
 ```JSON
{
    "status": "OK",
    "message": "Fetched doctor",
    "data": {
        "id": 1,
        "name": "name"
    }
}
 ```

### Adding new doctor:
```/doctors POST```

```JSON
{
    "name": "name"
}
```
Example response:
 ```JSON
{
    "status": "CREATED",
    "message": "New doctor added",
    "data": {
        "id": 4,
        "name": "name"
    }
}
 ```

### Adding new customer:
```/customers POST```

```JSON
{
    "pin": "1234",
    "name": "name"
}
```
Example response:
 ```JSON
{
    "status": "CREATED",
    "message": "New customer added",
    "data": {
        "id": "0904",
        "name": "name"
    }
}
 ```

 ### Fetching slots list
 ```/slots GET```

Example response:
 ```JSON
{
    "status": "OK",
    "message": "Fetched slots list",
    "data": [
        {
            "id": 2,
            "dateTime": "2020-02-02T20:20:20",
            "doctor": {
                "id": 1,
                "name": "name"
            },
            "appointment": null
        }
    ]
}
 ```

 ### Fetching slot by id
 ```/slots/{id} GET```


Example response:
 ```JSON
{
    "status": "OK",
    "message": "Fetched slot",
    "data": {
        "id": 2,
        "dateTime": "2020-02-02T20:20:20",
        "doctor": {
            "id": 1,
            "name": "name"
        },
        "appointment": null
    }
}
 ```

 ### Adding new slot
 ```/slots POST```

 ```JSON
 {
    "doctorId": 1,
    "dateTime": "2021-01-01T10:10:10"
}
 ```
 
Example response:
 ```JSON
{
    "status": "CREATED",
    "message": "New slot added",
    "data": {
        "id": 5,
        "dateTime": "2021-01-01T10:10:10",
        "doctor": {
            "id": 1,
            "name": "name"
        },
        "appointment": null
    }
}
 ```

### Fetching slots by doctor and date
```/slots/doctor/{id}/date/{date} GET```

Example response
```JSON
{
    "status": "OK",
    "message": "Fetched slots",
    "data": [
        {
            "id": 5,
            "dateTime": "2021-01-01T10:10:10",
            "doctor": {
                "id": 1,
                "name": "name"
            },
            "appointment": null
        }
    ]
}
```

### Making an appointment
```/appointments POST```

```JSON
{
    "customerId": "0904",
    "pin": "1234",
    "slotId": 5
}
```

Example response
```JSON
{
    "status": "CREATED",
    "message": "You have made an appointment",
    "data": {
        "id": 5,
        "dateTime": "2021-01-01T10:10:10",
        "doctor": {
            "id": 1,
            "name": "name"
        },
        "appointment": {
            "customer": {
                "id": "0904",
                "name": "name"
            }
        }
    }
}
```

### Cancelling an appointment
```/appointments DELETE```

```JSON
{
    "customerId": "0904",
    "pin": "1234",
    "slotId": 5
}
```

Example respone:
```JSON
{
    "status": "ACCEPTED",
    "message": "You have cancelled an appointment",
    "data": {
        "id": 5,
        "dateTime": "2021-01-01T10:10:10",
        "doctor": {
            "id": 1,
            "name": "doktor"
        },
        "appointment": null
    }
}
```