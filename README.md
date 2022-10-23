# Casino Project
This project is part of an assignment for the
course Backend Programming 2 at the
University of Applied Sciences Utrecht.

## Technologies used
* Java
* Spring
* Hibernate


## Project setup with Docker
Although not supported on every system,
Development is easiest with [Docker](https://docs.docker.com/desktop/). 
If Docker is installed, 
you can start the database by executing
`docker-compose up` from the commandline 
(or `docker-compose start` to run it in the background), 
while the current directory is the root of this project.
Docker will then start a PostgreSQL image with
the configuration stated in the `docker-compose.yml`
and in `development/db`.

This creates an admin user with the username and password `admin`
and `admin` and a user, password and database for the application,
all called `bep2-huland-casino`.

## Booting
First, make sure the database is set up, started and reachable.
Start the application via your IDE by running the `CasinoApplication`

