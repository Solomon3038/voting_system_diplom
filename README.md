# voting_system_diplom

### Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

**The task is:**

1. Build a voting system for deciding where to have lunch.

2. 2 types of users: admin and regular users

3. Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)

4. Menu changes each day (admins do the updates)

5. Users can vote on which restaurant they want to have lunch at

6. Only one vote counted per user

7. If user votes again the same day:

8. If it is before 11:00 we assume that he changed his mind.

9. If it is after 11:00 then it is too late, vote can't be changed

10. Each restaurant provides new menu each day.

11. As a result, provide a link to github repository.

12. It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

## Project documentation

``note, that price type is integer``

**Authorization:**

``ROLE_ADMIN`` **name:** admin.one@gmail.com, **password:** admin

``ROLE_USER`` **name:** user.one@ukr.net, **password:** password

| Description | Authorization | Method | URL | Success status | Error status |
|:---         |:---           |:---    |:--- |:---         |:---            |
| **Get all restaurants with menus on current date**| not required | GET | /restaurants | 200 | - | 
<details>
  <summary>Curl:</summary><p>
  
```
curl -X GET \
  http://localhost:8080/restaurants \
  -H 'Host: localhost:8080' 
```
</p></details>
<details>
  <summary>Content:</summary><p>
  
```
  [
      {
          "id": 2,
          "name": "Gastro",
          "address": "проспект Миру, 10, Рівне, Рівненська область, 33013",
          "menus": [
              {
                  "id": 5,
                  "registered": "2020-02-10",
                  "dishes": [
                      {
                          "id": 12,
                          "name": "Карпаччо з лосося",
                          "price": 9998,
                          "new": false
                      }
                  ],
                  "new": false
              }
          ],
          "new": false
      },
      {
          "id": 1,
          "name": "Manhattan-skybar",
          "address": "вулиця Соборна, 112, Рівне, Рівненська область, 33000",
          "menus": [
              {
                  "id": 4,
                  "registered": "2020-02-10",
                  "dishes": [
                      {
                          "id": 11,
                          "name": "Салат з тигровими креветками під кисло-солодким соусом",
                          "price": 14600,
                          "new": false
                      },
                      {
                          "id": 10,
                          "name": "Червоний борщ",
                          "price": 3800,
                          "new": false
                      },
                      {
                          "id": 9,
                          "name": "Шатобріан",
                          "price": 9900,
                          "new": false
                      }
                  ],
                  "new": false
              }
          ],
          "new": false
      },
      {
          "id": 3,
          "name": "Vinograd",
          "address": "вулиця Видумка, 2 Б, Рівне, Рівненська область, 33023",
          "menus": [
              {
                  "id": 6,
                  "registered": "2020-02-10",
                  "dishes": [
                      {
                          "id": 15,
                          "name": "Курча тапака",
                          "price": 7000,
                          "new": false
                      },
                      {
                          "id": 13,
                          "name": "Салат цезар",
                          "price": 11050,
                          "new": false
                      },
                      {
                          "id": 14,
                          "name": "Хінкалі з баранини",
                          "price": 9700,
                          "new": false
                      }
                  ],
                  "new": false
              }
          ],
          "new": false
      }
  ]
```
</p></details>

<table>
      <tr>
      <td><b> Get all restaurants </b></td>
      <td> ROLE_ADMIN </td>
      <td> GET </td>
      <td> /admin/restaurants </td>
      <td> 200 </td>
      <td> 401 </td>
      </tr>
</table>

<details>
  <summary>Curl:</summary><p>

```
curl -X GET \
  http://localhost:8080/admin/restaurants \
  -H 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
  -H 'Host: localhost:8080'  
```
</p></details>
<details>
  <summary>Content:</summary><p>
  
```
[
    {
        "id": 4,
        "name": "Closed",
        "address": "вулиця Грушевського, 120, Рівне, Рівненська область, 33000",
        "new": false
    },
    {
        "id": 2,
        "name": "Gastro",
        "address": "проспект Миру, 10, Рівне, Рівненська область, 33013",
        "new": false
    },
    {
        "id": 1,
        "name": "Manhattan-skybar",
        "address": "вулиця Соборна, 112, Рівне, Рівненська область, 33000",
        "new": false
    },
    {
        "id": 3,
        "name": "Vinograd",
        "address": "вулиця Видумка, 2 Б, Рівне, Рівненська область, 33023",
        "new": false
    }
]
```
</p></details>

<table>
      <tr>
      <td><b> Get restaurant </b></td>
      <td> ROLE_ADMIN </td>
      <td> GET </td>
      <td> /admin/restaurants/1 </td>
      <td> 200 </td>
      <td> 401 </td>
      </tr>
</table>

<details>
  <summary>Curl:</summary><p>

```
curl -X GET \
  http://localhost:8080/admin/restaurants/1 \
  -H 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
  -H 'Host: localhost:8080' 
```
</p></details>
<details>
  <summary>Content:</summary><p>
  
```
{
    "id": 1,
    "name": "Manhattan-skybar",
    "address": "вулиця Соборна, 112, Рівне, Рівненська область, 33000",
    "new": false
}
```
</p></details>

#### Create restaurant

### `POST /admin/restaurants`

Authorization: ROLE_ADMIN, name: admin.one@gmail.com, password: admin

Success response status:  **201**

<details>
  <summary>Data params:</summary><p>
  
```
{
    "name": "New Restaurant",
    "address": "new address"
}
```
 </p></details>

Curl:

```
curl -X POST \
  http://localhost:8080/admin/restaurants/ \
  -H 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8080' \
  -H 'cache-control: no-cache' \
  -d '    {
        "name": "New Restaurant",
        "address": "new address"
    }'
```
<details>
  <summary>Content:</summary><p>
  
```
{
    "id": 5,
    "name": "New Restaurant",
    "address": "new address",
    "menus": null,
    "new": false
}
```
</p></details>

Error response status:

#### Update restaurant

### `PUT /admin/restaurants/1`

Authorization: ROLE_ADMIN, name: admin.one@gmail.com, password: admin

Success response status:  **204**

<details>
  <summary>Data params:</summary><p>
  
```
{
    "id": 1,
    "name": "Manhattan-skybar updated",
    "address": "вулиця Гагаріна, 67, Рівне, Рівненська область, 33022"
}
```
 </p></details>

Curl:

```
curl -X PUT \
  http://localhost:8080/admin/restaurants/1 \
  -H 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8080' \
  -d '{
    "id": 1,
    "name": "Manhattan-skybar updated",
    "address": "вулиця Гагаріна, 67, Рівне, Рівненська область, 33022"
}'
```

#### Create restaurant with menu and dishes

### `POST /admin/restaurants/full`

Authorization: ROLE_ADMIN, name: admin.one@gmail.com, password: admin

Success response status:  **201**

<details>
  <summary>Data params:</summary><p>
  
```
{
    "name": "New Restaurant",
    "address": "New Address",
    "menus": [
        {
            "dishes": [
                {
                    "name": "new dish 1",
                    "price": 12000
                },
                {
                    "name": "new dish 2",
                    "price": 13000
                }
            ]
        }
    ]
}
```
 </p></details>

Curl:

```
curl -X POST \
  http://localhost:8080/admin/restaurants/full \
  -H 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8080' \
  -d '{
        "name": "New Restaurant",
        "address": "New Address",
        "menus": [
            {
                "dishes": [
                    {
                        "name": "new dish 1",
                        "price": 12000
                    },
                     {
                        "name": "new dish 2",
                        "price": 13000
                    }
                ]
            }
        ]
    }'
```
<details>
  <summary>Content:</summary><p>
  
```
{
    "id": 5,
    "name": "New Restaurant",
    "address": "New Address",
    "menus": [
        {
            "id": 7,
            "registered": "2020-02-11",
            "dishes": [
                {
                    "id": 16,
                    "name": "new dish 1",
                    "price": 12000,
                    "new": false
                },
                {
                    "id": 17,
                    "name": "new dish 2",
                    "price": 13000,
                    "new": false
                }
            ],
            "new": false
        }
    ],
    "new": false
}
```
</p></details>

Error response status:

#### Get all menus with dishes for restaurant

### `GET admin/restaurants/1/menus`

Authorization: ROLE_ADMIN, name: admin.one@gmail.com, password: admin

Success response status:  **200**

Curl:

```
curl -X GET \
  http://localhost:8080/admin/restaurants/1/menus \
  -H 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
  -H 'Host: localhost:8080'
```
<details>
  <summary>Content:</summary><p>
  
```
[
    {
        "id": 4,
        "registered": "2020-02-10",
        "dishes": [
            {
                "id": 11,
                "name": "Салат з тигровими креветками під кисло-солодким соусом",
                "price": 14600,
                "new": false
            },
            {
                "id": 10,
                "name": "Червоний борщ",
                "price": 3800,
                "new": false
            },
            {
                "id": 9,
                "name": "Шатобріан",
                "price": 9900,
                "new": false
            }
        ],
        "new": false
    },
    {
        "id": 1,
        "registered": "2020-01-01",
        "dishes": [
            {
                "id": 3,
                "name": "Салат з тигровими креветками під кисло-солодким соусом",
                "price": 14600,
                "new": false
            },
            {
                "id": 2,
                "name": "Червоний борщ",
                "price": 3800,
                "new": false
            },
            {
                "id": 1,
                "name": "Шатобріан",
                "price": 9900,
                "new": false
            }
        ],
        "new": false
    }
]
```
</p></details>

Error response status: **401**

#### Get one menu with dishes for restaurant

### `GET admin/restaurants/1/menus/1`

Authorization: ROLE_ADMIN, name: admin.one@gmail.com, password: admin

Success response status:  **200**

Curl:

```
curl -X GET \
  http://localhost:8080/admin/restaurants/1/menus/1 \
  -H 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
  -H 'Host: localhost:8080'
```
<details>
  <summary>Content:</summary><p>
  
```
{
    "id": 1,
    "registered": "2020-01-01",
    "dishes": [
        {
            "id": 3,
            "name": "Салат з тигровими креветками під кисло-солодким соусом",
            "price": 14600,
            "new": false
        },
        {
            "id": 2,
            "name": "Червоний борщ",
            "price": 3800,
            "new": false
        },
        {
            "id": 1,
            "name": "Шатобріан",
            "price": 9900,
            "new": false
        }
    ],
    "new": false
}
```
</p></details>

Error response status: **401**