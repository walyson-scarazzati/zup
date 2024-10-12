<h1>Technologies</h1>
<ul>
  <li>Spring Boot 2.7.12</li>
  <li>Spring Openapi</li>
  <li>MySQL</li>
  <li>Docker</li>
  <li>Github Actions with DevSecOps SonarCloud, Snyk and ZAP Scan</li>
</ul>

<h1> Accessing project based images from dockerhub </h1>

- 1º step ```docker network create mynetwork```
- 2º step ```docker run -d --name mysqldb8zup -p 3306:3306 --network mynetwork walysonsilva/mysqldb8zup```
- 3º step ```docker run -d --name zup-app -p 8080:8080 --network mynetwork walysonsilva/zup-app:0.0.1-SNAPSHOT```
- 4º step access project: http://localhost:8080/swagger-ui/index.html#/Pessoas/listarPessoas

![image](https://github.com/user-attachments/assets/0c8f9a7b-2478-48bb-927a-911b104f6616)

 
<h1> Accessing the environment locally</h1>

- Run the command ```docker-compose up -d``` in the docker folder
  
![image](https://github.com/user-attachments/assets/0d52dfab-e966-4dca-8c89-01261ce9d9f9)

 
- To run, just right-click on the project run as > Spring Boot app.
<img src="https://github.com/walyson-scarazzati/Spotmusic/assets/53382989/3671ec7c-662b-4868-aa85-3ab1b619fa1b" alt="Descrição da Imagem" width="400" height="500" />

- http://localhost:8081/pessoas/listar

- http://localhost:8081/swagger-ui.html#/Pessoa_API
- 
![image](https://github.com/user-attachments/assets/98aa9978-acd9-4f55-be9d-19598f1072bf)


<h1> Accessing the environment via Docker</h1>

- http://localhost:8080/swagger-ui.html#/Pessoa_API
![image](https://github.com/walyson-scarazzati/zup/assets/53382989/8b343e06-daa2-4c31-b125-772b38a96388)


- http://localhost:8080/pessoas/listar
  ![image](https://github.com/walyson-scarazzati/zup/assets/53382989/6a7915d7-a6e5-4425-8d31-4cff4715271c)
