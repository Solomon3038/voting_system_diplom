# voting_system_diplom

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0dbc929729a7430aa89705795eda5ac8)](https://app.codacy.com/manual/gagarina6794/voting_system_diplom?utm_source=github.com&utm_medium=referral&utm_content=gagarina6794/voting_system_diplom&utm_campaign=Badge_Grade_Dashboard)

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
<table>
    <tr>
        <th>Description</th>
        <th>Authorization</th>
        <th>Method</th>
        <th>URL</th>
        <th>Success status</th>
        <th>Error status</th>
    </tr>
 <tr>
          <td><b> Vote </b></td>
          <td> ROLE_USER </td>
          <td> PUT </td>
          <td> /restaurants/1/votes </td>
          <td> 204 No Content </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 409 Conflict </p>
          <p> 408 Request Timeout </p>
          </td>
 </tr>
<tr>
<td>
</td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request PUT 'http://localhost:8080/restaurants/1/votes' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlci5vbmVAdWtyLm5ldDpwYXNzd29yZA=='
```
</p></details>
</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
    <tr>
        <td><b> Get all restaurants with menus on current date </b></td>
        <td> not required </td>
        <td> GET </td>
        <td> /restaurants </td>
        <td> 200 OK</td>
        <td></td>
    </tr>
      <tr>
      <td></td>
      <td>
<details>
  <summary>Curl:</summary><p>
  
```
curl --location --request GET 'http://localhost:8080/restaurants' \
--header 'Content-Type: application/json' 
```
</p></details>
    </td>
    <td></td>
    <td>
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
    </td>
    <td></td>
    <td></td>
    </tr>
    <tr>
        <td><b> Get all restaurants </b></td>
        <td> ROLE_ADMIN </td>
        <td> GET </td>
        <td> /admin/restaurants </td>
        <td> 200 OK</td>
        <td> 
           <p> 401 Unauthorized </p>
           <p> 403 Forbidden </p>
        </td>
    </tr>
    <tr>
    <td></td>
    <td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request GET 'http://localhost:8080/admin/restaurants' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg=='
```
</p></details>
    </td>
    <td></td>
    <td>
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
    </td>
    <td></td>
    <td></td>
    </tr>
      <tr>
          <td><b> Get restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/restaurants/1 </td>
          <td> 200 OK</td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
           </td>
      </tr>
<tr>
<td></td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request GET 'http://localhost:8080/admin/restaurants/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg=='
```
</p></details>
</td>
<td></td>
<td>
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
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Create restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> POST </td>
          <td> /admin/restaurants </td>
          <td> 201 Created </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td>
<details>
  <summary>Data params:</summary><p>
  
```
{
    "name": "New Restaurant",
    "address": "new address"
}
```
</p></details>
</td>
<td>
<details>
  <summary>Curl:</summary><p>
  
```
curl --location --request POST 'http://localhost:8080/admin/restaurants' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
    "name": "New Restaurant",
    "address": "new address"
}
'
```
</p></details>
</td>
<td></td>
<td>
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
</td>
<td></td>
<td></td>
</tr>
<tr>
          <td><b> Create restaurant with menu </b></td>
          <td> ROLE_ADMIN </td>
          <td> POST </td>
          <td> /admin/restaurants </td>
          <td> 201 Created </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td>
<details>
  <summary>Data params:</summary><p>
  
```
{
    "name": "New Restaurant",
    "address": "New Address",
    "menus": [
        {}
    ]
}
```
 </p></details>
 </td>
 <td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request POST 'http://localhost:8080/admin/restaurants' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
    "name": "New Restaurant",
    "address": "New Address",
    "menus": [
        {}
    ]
}
'
```
 </p></details>
 </td>
 <td></td>
 <td>
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
            "registered": "2020-02-17",
            "dishes": [],
            "new": false
        }
    ],
    "new": false
}
```
</p></details>
</td>
<td></td>
<td></td>
</tr>
<tr>
          <td><b> Create restaurant with menu and dishes </b></td>
          <td> ROLE_ADMIN </td>
          <td> POST </td>
          <td> /admin/restaurants </td>
          <td> 201 Created </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td>
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
 </td>
 <td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request POST 'http://localhost:8080/admin/restaurants' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
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
 </p></details>
 </td>
 <td></td>
 <td>
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
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Update restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> PUT </td>
          <td> /admin/restaurants/1 </td>
          <td> 204 No Content </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td>
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
</td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request PUT 'http://localhost:8080/admin/restaurants/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
    "id": 1,
    "name": "Manhattan-skybar updated",
    "address": "вулиця Гагаріна, 67, Рівне, Рівненська область, 33022"
}'
```
</p></details>
</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Get all menus with dishes for restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/restaurants/1/menus </td>
          <td> 200 OK</td>
          <td>
               <p> 401 Unauthorized </p>
               <p> 403 Forbidden </p>
          </td>
      </tr>
<tr>
<td></td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request GET 'http://localhost:8080/admin/restaurants/1/menus' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg=='
```
</p></details>
</td>
<td></td>
<td>
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
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Get one menu with dishes for restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/restaurants/1/menus/1 </td>
          <td> 200 OK</td>
          <td> 
              <p> 401 Unauthorized </p>
              <p> 403 Forbidden </p>
              <p> 422 Unprocessable Entity </p>
          </td>
      </tr>
<tr>
<td></td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request GET 'http://localhost:8080/admin/restaurants/1/menus/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg=='
```
</p></details>
</td>
<td></td>
<td>
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
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Create menu </b></td>
          <td> ROLE_ADMIN </td>
          <td> POST </td>
          <td> /admin/restaurants/4/menus </td>
          <td> 201 Created </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td> 
  <details>
    <summary>Data params:</summary><p>
    
  ```
//create on current date
{}

//create on setted date
{
	"registered": "2020-02-12"
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request POST 'http://localhost:8080/admin/restaurants/1/menus' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
	"registered": "2020-02-12"
}'
```
  </p></details>
</td>
<td></td>
<td>
  <details>
    <summary>Content:</summary><p>
    
```
{
    "id": 7,
    "registered": "2020-02-12",
    "new": false
}
```
  </p></details>
</td>
<td></td>
<td></td>
</tr>
<tr>
          <td><b> Create menu with dishes </b></td>
          <td> ROLE_ADMIN </td>
          <td> POST </td>
          <td> /admin/restaurants/4/menus </td>
          <td> 201 Created </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td> 
  <details>
    <summary>Data params:</summary><p>
    
  ```
//create on current date
{
    "dishes": [
        {
            "name": "Салат з тигровими креветками під кисло-солодким соусом",
            "price": 14600
        },
        {
            "name": "Червоний борщ",
            "price": 3800
        },
        {
            "name": "Шатобріан",
            "price": 9900
        }
    ]
}

//create on setted date
{
    "registered": "2020-03-01",
    "dishes": [
        {
            "name": "Салат з тигровими креветками під кисло-солодким соусом",
            "price": 14600
        },
        {
            "name": "Червоний борщ",
            "price": 3800
        },
        {
            "name": "Шатобріан",
            "price": 9900
        }
    ]
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request POST 'http://localhost:8080/admin/restaurants/4/menus' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
    "dishes": [
        {
            "name": "Салат з тигровими креветками під кисло-солодким соусом",
            "price": 14600
        },
        {
            "name": "Червоний борщ",
            "price": 3800
        },
        {
            "name": "Шатобріан",
            "price": 9900
        }
    ]
}'
```
  </p></details>
</td>
<td></td>
<td>
  <details>
    <summary>Content:</summary><p>
    
```
{
    "id": 7,
    "registered": "2020-02-17",
    "dishes": [
        {
            "id": 16,
            "name": "Салат з тигровими креветками під кисло-солодким соусом",
            "price": 14600,
            "new": false
        },
        {
            "id": 17,
            "name": "Червоний борщ",
            "price": 3800,
            "new": false
        },
        {
            "id": 18,
            "name": "Шатобріан",
            "price": 9900,
            "new": false
        }
    ],
    "new": false
}
```
  </p></details>
</td>
<td></td>
<td></td>
</tr>
<tr>
          <td><b> Update menu </b></td>
          <td> ROLE_ADMIN </td>
          <td> PUT </td>
          <td> /admin/restaurants/1/menus/1 </td>
          <td> 204 No Content </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td> 
  <details>
    <summary>Data params:</summary><p>
    
  ```
{
	"registered": "2019-05-12"
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request PUT 'http://localhost:8080/admin/restaurants/1/menus/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
	"registered": "2019-05-12"
}'
```
  </p></details>
</td>
<td></td>
<td>
</td>
<td></td>
<td></td>
</tr>
 <tr>
          <td><b> Get all dishes for menu </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> admin/menus/1/dishes </td>
          <td> 200 OK</td>
          <td> 
            <p> 401 Unauthorized </p>
            <p> 403 Forbidden </p>       
           </td>
      </tr>
<tr>
<td></td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request GET 'http://localhost:8080/admin/menus/1/dishes' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg=='
```
</p></details>
</td>
<td></td>
<td>
<details>
  <summary>Content:</summary><p>
  
```
[
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
]
```
</p></details>
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Get one dish for menu </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/menus/1/dishes/1 </td>
          <td> 200 OK</td>
          <td> 
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          </td>
      </tr>
<tr>
<td></td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request GET 'http://localhost:8080/admin/menus/1/dishes/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg=='
```
</p></details>
</td>
<td></td>
<td>
<details>
  <summary>Content:</summary><p>
  
```
{
    "id": 1,
    "name": "Шатобріан",
    "price": 9900,
    "new": false
}
```
  </p></details>
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Create dish </b></td>
          <td> ROLE_ADMIN </td>
          <td> POST </td>
          <td> /admin/menus/1/dishes </td>
          <td> 201 Created </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td> 
  <details>
    <summary>Data params:</summary><p>
    
  ```
{
    "name": "New Dish",
    "price": 10500
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request POST 'http://localhost:8080/admin/menus/1/dishes' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
    "name": "New Dish",
    "price": 10500
}'
```
  </p></details>
</td>
<td></td>
<td>
  <details>
    <summary>Content:</summary><p>
    
```
{
    "id": 16,
    "name": "New Dish",
    "price": 10500,
    "new": false
}
```
  </p></details>
</td>
<td></td>
<td></td>
</tr>
<tr>
          <td><b> Update dish </b></td>
          <td> ROLE_ADMIN </td>
          <td> PUT </td>
          <td> /admin/menus/1/dishes/3 </td>
          <td> 204 No Content </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          <p> 409 Conflict </p>
          </td>
      </tr>
<tr>
<td> 
  <details>
    <summary>Data params:</summary><p>
    
  ```
{
    "id": 3,
    "name": "Updated Dish",
    "price": 14500
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request PUT 'http://localhost:8080/admin/menus/1/dishes/3' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--data-raw '{
    "id": 3,
    "name": "Updated Dish",
    "price": 14500
}'
```
  </p></details>
</td>
<td></td>
<td>
</td>
<td></td>
<td></td>
</tr>
<tr>
          <td><b> Delete dish </b></td>
          <td> ROLE_ADMIN </td>
          <td> DELETE </td>
          <td> /admin/menus/1/dishes/1 </td>
          <td> 204 No Content </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 422 Unprocessable Entity </p>
          </td>
      </tr>
<tr>
<td> 
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request DELETE 'http://localhost:8080/admin/menus/1/dishes/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg=='
```
  </p></details>
</td>
<td></td>
<td>
</td>
<td></td>
<td></td>
</tr>
</table>