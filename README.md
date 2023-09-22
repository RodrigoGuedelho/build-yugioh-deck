# Build Your Deck  Back-End



## Sobre

API(Application Programming Interface) Que fornece todo o controle de login e criação de Decks de Yugioh. Com esse serviço é possível criar um perfil e montar varios decks.



## Requisitos de software 

![java version](https://img.shields.io/badge/java-^17-blue) ![maven version](https://img.shields.io/badge/maven-3.0.6-red) ![docker version](https://img.shields.io/badge/docker-20.10.17-blue)



## Procedimentos de execução sem docker



Clone o projeto

Via ssh

```bash
git clone https://github.com/RodrigoGuedelho/build-yugioh-deck.git
```



Com o projeto clonado, acesse o diretório e instale todas as dependências necessárias

```bash
cd cloudplus_dashboard_back
mvn clean install 
```



Inicie a aplicação

```bash
./mvnw spring-boot:run
```



<img src="imgs/springStart.png" style="zoom: 150%;" />





### Procedimentos de execução com docker



Para gerar o build da aplicação: 

```bash
mvn clean install
```



Após o build, para iniciar o contâiner, execute:

```bash
docker-compose build 
```



Para iniciar o contâiner em modo background, execute:

```
docker-compose up -d 
```
