# Overview

#### Requirements

- Docker: https://docs.docker.com/get-docker/
- Docker Compose: https://docs.docker.com/compose/install/
- Disk Space: under 7GB
- RAM and CPU: no special requirements (2GB RAM)


# Startup
+ Go to the `nifi-docker` folder
+ Start and build the Container using docker compose:
  + `docker compose up --build`
+ Wait until the application has started
+ The Nifi Web-Interface including the data-flow at: `https://localhost:8443/nifi`
  + login data:
    + user: `admin`
    + pw: `test12345678`
+ For the conversion you first have to start the existing Nifi `Controller services`

#### Stopping container
- stop container: `ctrl+c`  (`docker compose down`)

# Other Info
+ all data is persisted in local folder `./data/`
+ Files can be shared via the `shared_folder` between the container and your machine

#### Trouble shooting

- if container does not start (warning: this might delete component configuration!):

    1. stop container: `ctrl+c`, `docker compose down -v`
    2. remove current configuration: `rm data/conf/* -rf`
    3. copy `./backup/conf` to `./data/conf`: `cp ./backup/conf/* ./data/conf/ -rf`

    2. `docker compose up --force-recreate --build`
