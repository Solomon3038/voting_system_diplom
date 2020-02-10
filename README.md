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

8. If it is before 11:00 we asume that he changed his mind.

9. If it is after 11:00 then it is too late, vote can't be changed

10. Each restaurant provides new menu each day.

11. As a result, provide a link to github repository.

12. It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

## Project documentation

### Get all restaurants with menu on current date

**URL:**  /restaurants

**Method:** GET

**Authorization:** not required 

**Success response status:** 200

<details>
  <summary>Content</summary><p>
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

