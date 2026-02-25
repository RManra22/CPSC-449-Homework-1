CPSC 449 - Spring 2026 - Homework 1 
Name: Roberto Manra


PUT endpoint (update book)
http://localhost:8082/api/books/3
update book 
```JSON: 
{
  "title": "Hello World",
  "author": "Roberto Manra",
  "price": 22.22
}
```
Updating: 
<img width="1918" height="1034" alt="image" src="https://github.com/user-attachments/assets/8a46300f-b635-4369-a811-a65161f273fc" />
Results: 
<img width="1919" height="1031" alt="image" src="https://github.com/user-attachments/assets/2d8c7c3c-b4b6-48db-b1ee-22a55940efcc" />

PATCH endpoint (partial update):
http://localhost:8082/api/books/3
update book 
```JSON: 
{
  "title": "Hello from the Patch endpoint!"
}
```
Patching: 
<img width="1919" height="1034" alt="image" src="https://github.com/user-attachments/assets/4148ead8-5685-4b9f-849c-09353caf1625" />

Results: 
<img width="1919" height="1032" alt="image" src="https://github.com/user-attachments/assets/c15b7e8a-e3e8-4b03-b61d-2c5f171efd1f" />

DELETE endpoint (remove book)
http://localhost:8082/api/books/3

Deleting: 
<img width="1919" height="1033" alt="image" src="https://github.com/user-attachments/assets/9784c33e-a282-4bda-b3dd-8d694348a27d" />

Results: 
<img width="1919" height="1031" alt="image" src="https://github.com/user-attachments/assets/33e0cccb-dc84-4ff4-8473-486823a9a06a" />

GET endpoint with pagination
// http://localhost:8082/api/books/pagination?page=1&size=5

Results: 
<img width="1919" height="1032" alt="image" src="https://github.com/user-attachments/assets/98e42ebf-0f98-4621-b53f-bae9f3fdd0e2" />

Advanced GET endpoint with filtering, sorting, and pagination combined in the valid order
// http://localhost:8082/api/books/advance?page=1&size=5&sortBy=author&minPrice=20.00

Results: 
<img width="1919" height="1034" alt="image" src="https://github.com/user-attachments/assets/c371d805-a6e9-4e72-ae6c-e0f3a7f26aa4" />



