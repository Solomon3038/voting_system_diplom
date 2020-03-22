# voting_system_diplom

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0dbc929729a7430aa89705795eda5ac8)](https://app.codacy.com/manual/gagarina6794/voting_system_diplom?utm_source=github.com&utm_medium=referral&utm_content=gagarina6794/voting_system_diplom&utm_campaign=Badge_Grade_Dashboard)
<a href="https://travis-ci.org/gagarina6794/voting_system_diplom" rel="nofollow"><img src="https://camo.githubusercontent.com/36aef9dad8478b5ff55a66261aabc1cfbf4b1880/68747470733a2f2f7472617669732d63692e6f72672f4a617661576562696e61722f746f706a6176612e7376673f6272616e63683d6d6173746572" alt="Build Status" data-canonical-src="https://api.travis-ci.org/gagarina6794/voting_system_diplom.svg?branch=master" style="max-width:100%;"></a>

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
        <td><b> Get vote for restaurant by date </b></td>
        <td> ROLE_USER </td>
        <td> GET </td>
        <td> /restaurants/3/votes?date=2020-01-01 </td>
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
curl --location --request GET 'http://localhost:8080/restaurants/3/votes?date=2020-01-01' \
--header 'Authorization: Basic dXNlci5vbmVAdWtyLm5ldDpwYXNzd29yZA=='
```
</p></details>
    </td>
    <td></td>
    <td>
<details>
  <summary>Content:</summary><p>
  
```
{
    "id": 2,
    "date": "2020-01-01",
    "userId": 1,
    "restaurantId": 3,
    "new": false
}
```
</p></details>
    </td>
    <td></td>
    <td></td>
    </tr>
 <tr>
          <td><b> Create vote </b></td>
          <td> ROLE_USER </td>
          <td> POST </td>
          <td> /restaurants/3/votes </td>
          <td> 201 Created </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 408 Request Timeout </p>
          <p> 409 Conflict </p>      
          </td>
      </tr>
<tr>
<td>
<details>
  <summary>Data params:</summary><p>
  
```
"2022-02-20"

if data is not exist vote will be create on current date
```
</p></details>
</td>
<td>
<details>
  <summary>Curl:</summary><p>
  
```
curl --location --request POST 'http://localhost:8080/restaurants/3/votes' \
--header 'Authorization: Basic dXNlci5vbmVAdWtyLm5ldDpwYXNzd29yZA==' \
--header 'Content-Type: application/json' \
--data-raw '"2022-02-20"
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
    "id": 4,
    "date": "2022-02-20",
    "userId": 1,
    "restaurantId": 3,
    "new": false
}
```
</p></details>
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Update vote </b></td>
          <td> ROLE_USER </td>
          <td> PUT </td>
          <td> /restaurants/1/votes </td>
          <td> 204 No Content </td>
          <td>
          <p> 401 Unauthorized </p>
          <p> 403 Forbidden </p>
          <p> 408 Request Timeout </p>
          <p> 409 Conflict </p>
          <p> 422 Unprocessable Entity </p>
          </td>
      </tr>
<tr>
<td>
<details>
  <summary>Data params:</summary><p>
  
```
"2020-01-01T11:00:00"
```
</p></details>
</td>
<td>
<details>
  <summary>Curl:</summary><p>

```
curl --location --request PUT 'http://localhost:8080/restaurants/1/votes' \
--header 'Authorization: Basic dXNlci5vbmVAdWtyLm5ldDpwYXNzd29yZA==' \
--header 'Content-Type: application/json' \
--data-raw '"2020-01-01T11:00:00"'
```
</p></details>
</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
    <tr>
        <td><b> Get all restaurants with menu items on date</b></td>
        <td> not required </td>
        <td> GET </td>
        <td> /restaurants?date=2019-06-01 </td>
        <td> 200 OK</td>
        <td></td>
    </tr>
      <tr>
      <td>(if parameter date not exists returns menu items on current date)</td>
      <td>
<details>
  <summary>Curl:</summary><p>
  
```
curl --location --request GET 'http://localhost:8080/restaurants?date=2019-06-01' 
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
        "menuItemDishNameTos": [
            {
                "id": 4,
                "date": "2019-06-01",
                "price": 9998,
                "dishName": "Карпаччо з лосося",
                "new": false
            },
            {
                "id": 5,
                "date": "2019-06-01",
                "price": 11050,
                "dishName": "Салат цезар",
                "new": false
            },
            {
                "id": 6,
                "date": "2019-06-01",
                "price": 9700,
                "dishName": "Хінкалі з баранини",
                "new": false
            }
        ],
        "new": false
    },
    {
        "id": 1,
        "name": "Manhattan-skybar",
        "address": "вулиця Соборна, 112, Рівне, Рівненська область, 33000",
        "menuItemDishNameTos": [
            {
                "id": 3,
                "date": "2019-06-01",
                "price": 14600,
                "dishName": "Салат з тигровими креветками під кисло-солодким соусом",
                "new": false
            },
            {
                "id": 2,
                "date": "2019-06-01",
                "price": 3800,
                "dishName": "Червоний борщ",
                "new": false
            },
            {
                "id": 1,
                "date": "2019-06-01",
                "price": 9000,
                "dishName": "Шатобріан",
                "new": false
            }
        ],
        "new": false
    },
    {
        "id": 3,
        "name": "Vinograd",
        "address": "вулиця Видумка, 2 Б, Рівне, Рівненська область, 33023",
        "menuItemDishNameTos": [
            {
                "id": 8,
                "date": "2019-06-01",
                "price": 7000,
                "dishName": "Курча тапака",
                "new": false
            },
            {
                "id": 9,
                "date": "2019-06-01",
                "price": 30000,
                "dishName": "Торт",
                "new": false
            },
            {
                "id": 7,
                "date": "2019-06-01",
                "price": 8500,
                "dishName": "Шашлик із телятини",
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
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "New Restaurant",
    "address": "new address"
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
    "address": "new address",
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
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--header 'Content-Type: application/json' \
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
          <td><b> Get menu items for restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/restaurants/1/menu-items </td>
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
curl --location --request GET 'http://localhost:8080/admin/restaurants/1/menu-items' \
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
        "id": 11,
        "date": "2020-03-22",
        "price": 3800,
        "dishName": "Червоний борщ",
        "new": false
    },
    {
        "id": 10,
        "date": "2020-03-22",
        "price": 9000,
        "dishName": "Шатобріан",
        "new": false
    },
    {
        "id": 3,
        "date": "2019-06-01",
        "price": 14600,
        "dishName": "Салат з тигровими креветками під кисло-солодким соусом",
        "new": false
    },
    {
        "id": 2,
        "date": "2019-06-01",
        "price": 3800,
        "dishName": "Червоний борщ",
        "new": false
    },
    {
        "id": 1,
        "date": "2019-06-01",
        "price": 9000,
        "dishName": "Шатобріан",
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
          <td><b> Get menu item for restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/restaurants/1/menu-items/1 </td>
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
curl --location --request GET 'http://localhost:8080/admin/restaurants/1/menu-items/1' \
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
    "date": "2019-06-01",
    "price": 9000,
    "dishName": "Шатобріан",
    "new": false
}
```
  </p></details>
</td>
<td></td>
<td></td>
</tr>
      <tr>
          <td><b> Create menu item </b></td>
          <td> ROLE_ADMIN </td>
          <td> POST </td>
          <td> /admin/restaurants/1/menu-items </td>
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
    "date": "2020-03-22",
    "price": 9000,
    "dishId": 3
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request POST 'http://localhost:8080/admin/restaurants/1/menu-items' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2020-03-22",
    "price": 9000,
    "dishId": 3
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
    "id": 15,
    "date": "2020-03-22",
    "price": 9000,
    "dishName": "Салат з тигровими креветками під кисло-солодким соусом",
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
          <td> /admin/restaurants/1/menu-items/2 </td>
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
    "id": 2,
    "date": "2020-03-21",
    "price": 230,
    "dishId": 1
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request PUT 'http://localhost:8080/admin/restaurants/1/menu-items/2' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 2,
    "date": "2020-03-21",
    "price": 230,
    "dishId": 1
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
          <td><b> Get all dishes for restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/restaurants/1/dishes </td>
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
curl --location --request GET 'http://localhost:8080/admin/restaurants/1/dishes' \
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
        "new": false
    },
    {
        "id": 2,
        "name": "Червоний борщ",
        "new": false
    },
    {
        "id": 1,
        "name": "Шатобріан",
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
          <td><b> Get one dish for restaurant </b></td>
          <td> ROLE_ADMIN </td>
          <td> GET </td>
          <td> /admin/restaurants/1/dishes/1 </td>
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
curl --location --request GET 'http://localhost:8080/admin/restaurants/1/dishes/1' \
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
          <td> /admin/restaurants/1/dishes </td>
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
    "name": "Суп"
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request POST 'http://localhost:8080/admin/restaurants/1/dishes' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Суп"
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
    "id": 10,
    "name": "Суп",
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
          <td> /admin/restaurants/1/dishes/3 </td>
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
    "name": "Суп"
}
  ```
  </p></details>
</td>
<td>
  <details>
    <summary>Curl:</summary><p>
    
```
curl --location --request PUT 'http://localhost:8080/admin/restaurants/1/dishes/3' \
--header 'Authorization: Basic YWRtaW4ub25lQGdtYWlsLmNvbTphZG1pbg==' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 3,
    "name": "Суп"
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
</table>